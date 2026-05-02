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
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.crafting.recipe.TraderRecipeInput;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities.Item;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TraderJob extends SpiritJob {
    public static final String DROPPED_BY_TRADER = "occultism:dropped_by_trader";

    /**
     * The current ticks in the conversion, will convert once it reaches timeToConvert
     */
    protected int conversionTimer;
    protected int timeToConvert;
    protected int maxTradesPerRound;

    protected PickupItemsGoal pickupItemsGoal;

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();
    protected List<RecipeHolder<SpiritTradeRecipe>> currentRecipe = List.of();
    protected List<WeightedRecipeResult> possibleResults;
    private ResourceHandler<ItemResource> handlerBelow = null;
    private BlockState cachedStateBelow = null;

    public TraderJob(SpiritEntity entity, Supplier<Integer> timeToConvert, Supplier<Integer> maxTradesPerRound) {
        super(entity);
        this.timeToConvert = timeToConvert.get();
        this.maxTradesPerRound = maxTradesPerRound.get();
    }

    //region Getter / Setter

    /**
     * The time to convert in seconds
     */
    public int getTimeToConvert() {
        return this.timeToConvert;
    }

    public void setTimeToConvert(int timeToConvert) {
        this.timeToConvert = timeToConvert;
    }

    /**
     * The max amount of trades to perform before the time to convert has to elapse again.
     *
     * @param trades the amount of trades to perform.
     */
    public void setMaxTradesPerRound(int trades) {
        this.maxTradesPerRound = trades;
    }
    //endregion Getter / Setter

    @Override
    public void onInit() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.itemsToPickUp = StreamSupport.stream(((ServerLevel) this.entity.level()).recipeAccess().getRecipes().spliterator(), false)
                .filter(recipe -> recipe.value() instanceof SpiritTradeRecipe)
                .map(recipe -> (RecipeHolder<SpiritTradeRecipe>) recipe)
                .filter(
                        recipe -> {
                            //we filter by trader id
                            String recipeTrader = recipe.value().getTrader();
                            return recipeTrader.equals(this.getFactoryID().toString());
                        }
                )
                .flatMap(recipe -> recipe.value().getIngredients().stream()).collect(Collectors.toList());
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getItemInHand(InteractionHand.MAIN_HAND);
        var recipeInput = new TraderRecipeInput(handHeld, this.getFactoryID().toString());
        Level level = this.entity.level();
        if (this.currentRecipe.isEmpty() && !handHeld.isEmpty()) {
            this.currentRecipe = ((ServerLevel) level).recipeAccess().getRecipes().stream()
                    .filter(r -> r.value() instanceof SpiritTradeRecipe && ((SpiritTradeRecipe) r.value()).matches(recipeInput, level))
                    .map(r -> (RecipeHolder<SpiritTradeRecipe>) r)
                    .filter(r -> r.value().getTrader() == null || r.value().getTrader().equals(this.getFactoryID().toString()))
                    .collect(Collectors.toList());
            this.conversionTimer = 0;

            if (!this.currentRecipe.isEmpty()) {
                //play crushing sound
                level.playSound(null, this.entity.blockPosition(), OccultismSounds.START_RITUAL.get(),
                        SoundSource.NEUTRAL, 1f, 1 + 0.5f * this.entity.getRandom().nextFloat());
                this.possibleResults = this.currentRecipe.stream().map(r -> r.value().getWeightedResult()).collect(Collectors.toList());
            } else {
                //if no recipe is found, drop hand held item as we can't process it
                this.entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                ItemEntity droppedItem = this.entity.spawnAtLocation((ServerLevel) level, handHeld);
                if (droppedItem != null) {
                    droppedItem.addTag(DROPPED_BY_TRADER);
                }
            }
        }
        if (!this.currentRecipe.isEmpty()) {
            if (handHeld.isEmpty() || !this.currentRecipe.get(0).value().matches(recipeInput, level)) {
                //Reset cached recipe if it no longer matches
                this.currentRecipe = List.of();
            } else {
                //advance conversion
                this.conversionTimer++;

                //show particle effect while crushing
                if (level.getGameTime() % 10 == 0) {
                    Vec3 pos = this.entity.position();
                    ((ServerLevel) level)
                            .sendParticles(ParticleTypes.PORTAL, pos.x + level.getRandom().nextGaussian() / 3,
                                    pos.y + 0.5, pos.z + level.getRandom().nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                                    0.0);
                }

                //every two seconds, play another crushing sound
                if (this.conversionTimer % 40 == 0) {
                    level.playSound(null, this.entity.blockPosition(), OccultismSounds.POOF.get(),
                            SoundSource.NEUTRAL, 1f,
                            1 + 0.5f * this.entity.getRandom().nextFloat());
                }

                if (this.conversionTimer >= this.timeToConvert) {
                    this.conversionTimer = 0;

                    int a = Math.min(this.maxTradesPerRound, handHeld.getCount());
                    for (int i = 0; i < a; i++) {
                        var result = WeightedRandom.getRandomItem(this.entity.getRandom(), this.possibleResults, WeightedRecipeResult::weight);
                        //Important: copy the result, don't use it raw!
                        result.ifPresent(r -> {
                            ItemStack finalResult = r.getStack().copy();
                            finalResult.setCount(finalResult.getCount());
                            ItemStack inputCopy = handHeld.copy();
                            handHeld.shrink(1);

                            this.entity.setItemInHand(InteractionHand.MAIN_HAND, handHeld);

                            this.onConvert(inputCopy, finalResult);
                            var event = new TraderJobEvent(this.entity, inputCopy, finalResult);
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
                                        droppedItem.addTag(DROPPED_BY_TRADER);
                                    }
                                }
                            }
                        });
                    }

                    //Don't reset recipe here, keep it cached
                }
            }
        }
        super.update();
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, Provider provider) {
        compound.putInt("timeToConvert", this.timeToConvert);
        compound.putInt("conversionTimer", this.conversionTimer);
        compound.putInt("maxTradesPerRound", this.maxTradesPerRound);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, Provider provider) {
        super.readJobFromNBT(compound, provider);
        this.timeToConvert = compound.getIntOr("timeToConvert", 0);
        this.conversionTimer = compound.getIntOr("conversionTimer", 0);
        this.maxTradesPerRound = compound.getIntOr("maxTradesPerRound", 0);
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        if (entity.entityTags().contains(DROPPED_BY_TRADER)
                && entity.getAge() < Occultism.SERVER_CONFIG.spiritJobs.traderResultPickupDelay.get())
            return false; //cannot pick up items a trader (most likely *this* one) dropped util delay elapsed.

        ItemStack stack = entity.getItem();
        return !stack.isEmpty() && this.itemsToPickUp.stream().anyMatch(i -> i.test(stack));
    }

    @Override
    public List<Ingredient> getItemsToPickUp() {
        return this.itemsToPickUp;
    }

    /**
     * Called when a conversion trade was successful.
     *
     * @param input  the input item.
     * @param output the output item.
     */
    public void onConvert(ItemStack input, ItemStack output) {

    }

    public void updateBelowBlock() {
        this.cachedStateBelow = this.entity.level().getBlockState(this.entity.blockPosition().below(2));
        this.handlerBelow = this.entity.level().getCapability(Item.BLOCK,
                this.entity.blockPosition().below(2), this.cachedStateBelow, null, Direction.UP);
    }

    public static class TraderJobEvent extends ItemProcessingJobEvent {
        public TraderJobEvent(Entity entity, ItemStack input, ItemStack result) {
            super(entity, input, result);
        }
    }
}
