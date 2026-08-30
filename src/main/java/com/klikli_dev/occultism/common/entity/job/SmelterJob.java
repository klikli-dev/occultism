/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.common.entity.job;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.ai.goal.PickupItemsGoal;
import com.klikli_dev.occultism.common.entity.job.event.ItemProcessingJobEvent;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities.Item;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SmelterJob extends FilterableProcessingSpiritJob {


    public static final String DROPPED_BY_SMELTER = "occultism:dropped_by_smelter";

    /**
     * The current ticks in the smelting, will smelt once it reaches smelting_time
     */
    protected int smeltingTimer;
    protected Supplier<Float> smeltingTimeMultiplier;

    protected Supplier<Integer> tier;
    protected Supplier<Integer> operationCount;

    protected Optional<RecipeHolder<SmeltingRecipe>> currentRecipe = Optional.empty();
    protected Optional<RecipeHolder<BlastingRecipe>> currentRecipeBlast = Optional.empty();
    protected Optional<RecipeHolder<SmokingRecipe>> currentRecipeSmoke = Optional.empty();
    protected Optional<RecipeHolder<CampfireCookingRecipe>> currentRecipeCamp = Optional.empty();
    protected PickupItemsGoal pickupItemsGoal;

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();
    private ResourceHandler<ItemResource> handlerBelow = null;
    private BlockState cachedStateBelow = null;

    public SmelterJob(SpiritEntity entity, Supplier<Float> smeltingTimeMultiplier, Supplier<Integer> operationCount) {
        super(entity);
        this.smeltingTimeMultiplier = smeltingTimeMultiplier;
        this.operationCount = operationCount;
    }

    @Override
    public void onInit() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        Level level = this.entity.level();
        var allRecipes = ((ServerLevel) level).recipeAccess().getRecipes();
        for (var holder : allRecipes) {
            if (holder.value() instanceof AbstractCookingRecipe cookingRecipe) {
                this.itemsToPickUp.add(cookingRecipe.input());
            }
        }
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getItemInHand(InteractionHand.MAIN_HAND);
        var recipeInput = new SingleRecipeInput(handHeld);
        Level level = this.entity.level();

        if (!handHeld.isEmpty() && this.currentRecipe.isEmpty()
                && this.currentRecipeBlast.isEmpty()
                && this.currentRecipeSmoke.isEmpty()
                && this.currentRecipeCamp.isEmpty()) {

            this.currentRecipe = ((ServerLevel) level).recipeAccess().getRecipeFor(RecipeType.SMELTING,
                    recipeInput, level);
            if (this.currentRecipe.isEmpty()) {
                this.currentRecipeBlast = ((ServerLevel) level).recipeAccess().getRecipeFor(RecipeType.BLASTING,
                        recipeInput, level);
                if (this.currentRecipeBlast.isEmpty()) {
                    this.currentRecipeSmoke = ((ServerLevel) level).recipeAccess().getRecipeFor(RecipeType.SMOKING,
                            recipeInput, level);
                    if (this.currentRecipeSmoke.isEmpty()) {
                        this.currentRecipeCamp = ((ServerLevel) level).recipeAccess().getRecipeFor(RecipeType.CAMPFIRE_COOKING,
                                recipeInput, level);
                    }
                }
            }
            this.smeltingTimer = 0;

            if (this.currentRecipe.isPresent() || this.currentRecipeBlast.isPresent() || this.currentRecipeSmoke.isPresent() || this.currentRecipeCamp.isPresent()) {
                //play smelting sound
                level.playSound(null, this.entity.blockPosition(), SoundEvents.FIRE_AMBIENT,
                        SoundSource.NEUTRAL, 1f, 1 + 0.5f * this.entity.getRandom().nextFloat());
            } else {
                //if no recipe is found, drop hand held item as we can't process it
                this.entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                ItemEntity droppedItem = this.entity.spawnAtLocation((ServerLevel) level, handHeld);
                if (droppedItem != null) {
                    droppedItem.addTag(DROPPED_BY_SMELTER);
                }
            }
        }
        if (this.currentRecipe.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipe.get().value().matches(recipeInput, level)) {
                //Reset cached recipe if it no longer matches
                this.currentRecipe = Optional.empty();
            } else {
                this.commonTick();
                if (this.smeltingTimer >= this.currentRecipe.get().value().cookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipe.get().value().assemble(recipeInput);
                    this.commonFinish(handHeld, result, level);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeBlast.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeBlast.get().value().matches(recipeInput, level)) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeBlast = Optional.empty();
            } else {
                this.commonTick();
                if (this.smeltingTimer >= this.currentRecipeBlast.get().value().cookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeBlast.get().value().assemble(recipeInput);
                    this.commonFinish(handHeld, result, level);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeSmoke.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeSmoke.get().value().matches(recipeInput, level)) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeSmoke = Optional.empty();
            } else {
                this.commonTick();
                if (this.smeltingTimer >= this.currentRecipeSmoke.get().value().cookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeSmoke.get().value().assemble(recipeInput);
                    this.commonFinish(handHeld, result, level);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeCamp.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeCamp.get().value().matches(recipeInput, level)) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeCamp = Optional.empty();
            } else {
                this.commonTick();
                if (this.smeltingTimer >= this.currentRecipeCamp.get().value().cookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeCamp.get().value().assemble(recipeInput);
                    this.commonFinish(handHeld, result, level);
                    //Don't reset recipe here, keep it cached
                }
            }
        }
        super.update();
    }

    private void commonTick() {
        Level level = this.entity.level();
        //advance conversion
        this.smeltingTimer++;

        //show particle effect while smelting
        if (level.getGameTime() % 10 == 0) {
            Vec3 pos = this.entity.position();
            ((ServerLevel) level)
                    .sendParticles(ParticleTypes.FLAME, pos.x + level.getRandom().nextGaussian() / 3,
                            pos.y + 0.5, pos.z + level.getRandom().nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                            0.0);
        }

        //every two seconds, play another smelting sound
        if (level.getGameTime() % 40 == 0) {
            level.playSound(null, this.entity.blockPosition(), SoundEvents.FIRE_AMBIENT,
                    SoundSource.NEUTRAL, 1f,
                    1 + 0.5f * this.entity.getRandom().nextFloat());
        }
    }

    private void commonFinish(ItemStack handHeld, ItemStack result, Level level) {
        int a = Math.min(this.operationCount.get(), handHeld.getCount());
        result.setCount((result.getCount() * a));
        ItemStack inputCopy = handHeld.copy();
        inputCopy.setCount(a);
        handHeld.shrink(a);

        this.entity.setItemInHand(InteractionHand.MAIN_HAND, handHeld);

        this.onSmelt(inputCopy, result);
        var event = new SmelterJobEvent(this.entity, inputCopy, result);
        NeoForge.EVENT_BUS.post(event);
        if (!event.getResult().isEmpty()) {
            boolean flag = true;
            if (level.getBlockState(this.entity.blockPosition().below()).is(OccultismBlocks.DIMENSIONAL_EXTRACTOR)) {
                if (this.cachedStateBelow != level.getBlockState(this.entity.blockPosition().below(2)))
                    this.updateBelowBlock();
                if (this.handlerBelow != null) {
                    ItemTransferUtil.insertItemStacked(this.handlerBelow, event.getResult(), false);
                    flag = false;
                }
            }
            if (flag) {
                ItemEntity droppedItem = this.entity.spawnAtLocation((ServerLevel) level, event.getResult());
                if (droppedItem != null) {
                    droppedItem.addTag(DROPPED_BY_SMELTER);
                }
            }
        }
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, Provider provider) {
        compound.putInt("conversionTimer", this.smeltingTimer);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, Provider provider) {
        super.readJobFromNBT(compound, provider);
        this.smeltingTimer = compound.getIntOr("conversionTimer", 0);
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        if (entity.entityTags().contains(DROPPED_BY_SMELTER) && entity.getAge() <
                Occultism.SERVER_CONFIG.spiritJobs.smelterResultPickupDelay.get())
            return false; //cannot pick up items a smelter (most likely *this* one) dropped util delay elapsed.

        return this.matchesPickupItem(entity, this.itemsToPickUp);
    }

    @Override
    public List<Ingredient> getItemsToPickUp() {
        return this.itemsToPickUp;
    }

    /**
     * Called when an item was smelted
     *
     * @param input  the input item.
     * @param output the output item.
     */
    public void onSmelt(ItemStack input, ItemStack output) {

    }

    public void updateBelowBlock() {
        this.cachedStateBelow = this.entity.level().getBlockState(this.entity.blockPosition().below(2));
        this.handlerBelow = this.entity.level().getCapability(Item.BLOCK,
                this.entity.blockPosition().below(2), this.cachedStateBelow, null, Direction.UP);
    }

    public static class SmelterJobEvent extends ItemProcessingJobEvent {
        public SmelterJobEvent(Entity entity, ItemStack input, ItemStack result) {
            super(entity, input, result);
        }
    }
}
