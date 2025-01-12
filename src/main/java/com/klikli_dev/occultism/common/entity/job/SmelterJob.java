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
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SmelterJob extends SpiritJob {


    public static final String DROPPED_BY_SMELTER = "occultism:dropped_by_smelter";

    /**
     * The current ticks in the smelting, will smelt once it reaches smelting_time
     */
    protected int smeltingTimer;
    protected Supplier<Float> smeltingTimeMultiplier;

    protected Supplier<Integer> tier;

    protected Optional<RecipeHolder<SmeltingRecipe>> currentRecipe = Optional.empty();
    protected Optional<RecipeHolder<BlastingRecipe>> currentRecipeBlast = Optional.empty();
    protected Optional<RecipeHolder<SmokingRecipe>> currentRecipeSmoke = Optional.empty();
    protected Optional<RecipeHolder<CampfireCookingRecipe>> currentRecipeCamp = Optional.empty();
    protected PickupItemsGoal pickupItemsGoal;

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();


    public SmelterJob(SpiritEntity entity, Supplier<Float> smeltingTimeMultiplier) {
        super(entity);
        this.smeltingTimeMultiplier = smeltingTimeMultiplier;
    }

    @Override
    public void onInit() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.itemsToPickUp.addAll(this.entity.level().getRecipeManager().getAllRecipesFor(RecipeType.SMELTING).stream()
                .flatMap(recipe -> recipe.value().getIngredients().stream()).toList());
        this.itemsToPickUp.addAll(this.entity.level().getRecipeManager().getAllRecipesFor(RecipeType.BLASTING).stream()
                .flatMap(recipe -> recipe.value().getIngredients().stream()).toList());
        this.itemsToPickUp.addAll(this.entity.level().getRecipeManager().getAllRecipesFor(RecipeType.SMOKING).stream()
                .flatMap(recipe -> recipe.value().getIngredients().stream()).toList());
        this.itemsToPickUp.addAll(this.entity.level().getRecipeManager().getAllRecipesFor(RecipeType.CAMPFIRE_COOKING).stream()
                .flatMap(recipe -> recipe.value().getIngredients().stream()).toList());
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getItemInHand(InteractionHand.MAIN_HAND);
        var recipeInput = new SingleRecipeInput(handHeld);

        if (!handHeld.isEmpty() && this.currentRecipe.isEmpty()
                                && this.currentRecipeBlast.isEmpty()
                                && this.currentRecipeSmoke.isEmpty()
                                && this.currentRecipeCamp.isEmpty()) {

            this.currentRecipe = this.entity.level().getRecipeManager().getRecipeFor(RecipeType.SMELTING,
                    recipeInput, this.entity.level());
            if (this.currentRecipe.isEmpty()) {
                this.currentRecipeBlast = this.entity.level().getRecipeManager().getRecipeFor(RecipeType.BLASTING,
                        recipeInput, this.entity.level());
                if (this.currentRecipeBlast.isEmpty()) {
                    this.currentRecipeSmoke = this.entity.level().getRecipeManager().getRecipeFor(RecipeType.SMOKING,
                            recipeInput, this.entity.level());
                    if (this.currentRecipeSmoke.isEmpty()) {
                        this.currentRecipeCamp = this.entity.level().getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING,
                                recipeInput, this.entity.level());
                    }
                }
            }
            this.smeltingTimer = 0;

            if (this.currentRecipe.isPresent() || this.currentRecipeBlast.isPresent() || this.currentRecipeSmoke.isPresent() || this.currentRecipeCamp.isPresent()) {
                //play smelting sound
                this.entity.level()
                        .playSound(null, this.entity.blockPosition(), SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1f,
                                1 + 0.5f * this.entity.getRandom().nextFloat());
            } else {
                //if no recipe is found, drop hand held item as we can't process it
                this.entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                ItemEntity droppedItem = this.entity.spawnAtLocation(handHeld);
                if (droppedItem != null) {
                    droppedItem.addTag(DROPPED_BY_SMELTER);
                }
            }
        }
        if (this.currentRecipe.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipe.get().value().matches(recipeInput, this.entity.level())) {
                //Reset cached recipe if it no longer matches
                this.currentRecipe = Optional.empty();
            } else {
                commomTick();
                if (this.smeltingTimer >= this.currentRecipe.get().value().getCookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipe.get().value().assemble(recipeInput, this.entity.level().registryAccess());
                    commonFinish(handHeld, result);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeBlast.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeBlast.get().value().matches(recipeInput, this.entity.level())) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeBlast = Optional.empty();
            } else {
                commomTick();
                if (this.smeltingTimer >= this.currentRecipeBlast.get().value().getCookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeBlast.get().value().assemble(recipeInput, this.entity.level().registryAccess());
                    commonFinish(handHeld, result);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeSmoke.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeSmoke.get().value().matches(recipeInput, this.entity.level())) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeSmoke = Optional.empty();
            } else {
                commomTick();
                if (this.smeltingTimer >= this.currentRecipeSmoke.get().value().getCookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeSmoke.get().value().assemble(recipeInput, this.entity.level().registryAccess());
                    commonFinish(handHeld, result);
                    //Don't reset recipe here, keep it cached
                }
            }
        } else if (this.currentRecipeCamp.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipeCamp.get().value().matches(recipeInput, this.entity.level())) {
                //Reset cached recipe if it no longer matches
                this.currentRecipeCamp = Optional.empty();
            } else {
                commomTick();
                if (this.smeltingTimer >= this.currentRecipeCamp.get().value().getCookingTime() * this.smeltingTimeMultiplier.get()) {
                    this.smeltingTimer = 0;
                    ItemStack result = this.currentRecipeCamp.get().value().assemble(recipeInput, this.entity.level().registryAccess());
                    commonFinish(handHeld, result);
                    //Don't reset recipe here, keep it cached
                }
            }
        }
        super.update();
    }

    private void commomTick(){
        //advance conversion
        this.smeltingTimer++;

        //show particle effect while smelting
        if (this.entity.level().getGameTime() % 10 == 0) {
            Vec3 pos = this.entity.position();
            ((ServerLevel) this.entity.level())
                    .sendParticles(ParticleTypes.FLAME, pos.x + this.entity.level().random.nextGaussian() / 3,
                            pos.y + 0.5, pos.z + this.entity.level().random.nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                            0.0);
        }

        //every two seconds, play another smelting sound
        if (this.smeltingTimer % 40 == 0) {
            this.entity.level().playSound(null, this.entity.blockPosition(), SoundEvents.FIRE_AMBIENT,
                    SoundSource.NEUTRAL, 1f,
                    1 + 0.5f * this.entity.getRandom().nextFloat());
        }
    }

    private void commonFinish(ItemStack handHeld, ItemStack result){
        result.setCount((result.getCount()));
        ItemStack inputCopy = handHeld.copy();
        inputCopy.setCount(1);
        handHeld.shrink(1);

        this.onSmelt(inputCopy, result);
        var event = new SmelterJobEvent(this.entity, inputCopy, result);
        NeoForge.EVENT_BUS.post(event);
        if(!event.getResult().isEmpty()) {
            ItemEntity droppedItem = this.entity.spawnAtLocation(event.getResult());
            if (droppedItem != null) {
                droppedItem.addTag(DROPPED_BY_SMELTER);
            }
        }
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("conversionTimer", this.smeltingTimer);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, HolderLookup.Provider provider) {
        super.readJobFromNBT(compound, provider);
        this.smeltingTimer = compound.getInt("conversionTimer");
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        if (entity.getTags().contains(DROPPED_BY_SMELTER) && entity.getAge() <
                Occultism.SERVER_CONFIG.spiritJobs.smelterResultPickupDelay.get())
            return false; //cannot pick up items a smelter (most likely *this* one) dropped util delay elapsed.

        ItemStack stack = entity.getItem();
        return !stack.isEmpty() && this.itemsToPickUp.stream().anyMatch(i -> i.test(stack));
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

    public static class SmelterJobEvent extends EntityEvent {
        private ItemStack input;
        private ItemStack result;
        public SmelterJobEvent(Entity entity, ItemStack input, ItemStack result) {
            super(entity);
            this.input = input;
            this.result = result;
        }

        public ItemStack getInput() {
            return input;
        }

        public void setInput(ItemStack input) {
            this.input = input;
        }

        public ItemStack getResult() {
            return result;
        }

        public void setResult(ItemStack result) {
            this.result = result;
        }
    }
}
