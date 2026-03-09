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
import com.klikli_dev.occultism.crafting.recipe.CrystallizeRecipe;
import com.klikli_dev.occultism.crafting.recipe.TieredSingleRecipeInput;
import com.klikli_dev.occultism.registry.OccultismRecipes;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CrystallizerJob extends SpiritJob {


    public static final String DROPPED_BY_CRYSTALLIZER = "occultism:dropped_by_crystallizer";

    /**
     * The current ticks in the crystallizer, will crystallize once it reaches crystallize_time * crystallizeTimeMultiplier
     */
    protected int crystallizeTimer;
    protected Supplier<Float> crystallizeTimeMultiplier;
    protected Supplier<Float> outputMultiplier;
    protected Supplier<Integer> tier;
    protected Supplier<Integer> operationCount;

    protected Optional<RecipeHolder<CrystallizeRecipe>> currentRecipe = Optional.empty();
    protected PickupItemsGoal pickupItemsGoal;

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();


    public CrystallizerJob(SpiritEntity entity, Supplier<Float> crystallizeTimeMultiplier, Supplier<Float> outputMultiplier, Supplier<Integer> operationCount, Supplier<Integer> tier) {
        super(entity);
        this.crystallizeTimeMultiplier = crystallizeTimeMultiplier;
        this.outputMultiplier = outputMultiplier;
        this.tier = tier;
        this.operationCount = operationCount;
    }

    @Override
    public void onInit() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.itemsToPickUp = this.entity.level().getRecipeManager().getAllRecipesFor(OccultismRecipes.CRYSTALLIZE_TYPE.get()).stream()
                .filter(
                        recipe -> {
                            //we filter by tier, but only if the recipe has an "active" min and max tier set = min/max >= -1
                            int minTier = recipe.value().getMinTier();
                            int maxTier = recipe.value().getMaxTier();
                            int currentTier = this.tier.get();
                            return (minTier < 0 || minTier <= currentTier) && (maxTier < 0 || maxTier >= currentTier);
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
        var recipeInput = new TieredSingleRecipeInput(handHeld, this.tier.get());

        if (!this.currentRecipe.isPresent() && !handHeld.isEmpty()) {
            this.currentRecipe = this.entity.level().getRecipeManager().getRecipeFor(OccultismRecipes.CRYSTALLIZE_TYPE.get(),
                    recipeInput, this.entity.level());
            this.crystallizeTimer = 0;

            if (this.currentRecipe.isPresent()) {
                //play crystallize sound
                this.entity.level()
                        .playSound(null, this.entity.blockPosition(), SoundEvents.AMETHYST_CLUSTER_STEP, SoundSource.NEUTRAL, 1f,
                                1 + 0.5f * this.entity.getRandom().nextFloat());
            } else {
                //if no recipe is found, drop hand held item as we can't process it
                this.entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                ItemEntity droppedItem = this.entity.spawnAtLocation(handHeld);
                if (droppedItem != null) {
                    droppedItem.addTag(DROPPED_BY_CRYSTALLIZER);
                }
            }
        }
        if (this.currentRecipe.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipe.get().value().matches(recipeInput, this.entity.level())) {
                //Reset cached recipe if it no longer matches
                this.currentRecipe = Optional.empty();
            } else {
                //advance conversion
                this.crystallizeTimer++;

                //show particle effect while crystallize
                if (this.entity.level().getGameTime() % 10 == 0) {
                    Vec3 pos = this.entity.position();
                    ((ServerLevel) this.entity.level())
                            .sendParticles(ParticleTypes.WITCH, pos.x + this.entity.level().random.nextGaussian() / 3,
                                    pos.y + 0.5, pos.z + this.entity.level().random.nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                                    0.0);
                }

                //every two seconds, play another crystallize sound
                if (this.crystallizeTimer % 40 == 0) {
                    this.entity.level().playSound(null, this.entity.blockPosition(), SoundEvents.AMETHYST_CLUSTER_STEP,
                            SoundSource.NEUTRAL, 1f,
                            1 + 0.5f * this.entity.getRandom().nextFloat());
                }

                if (this.crystallizeTimer >= this.currentRecipe.get().value().getCrystallizeTime() * this.crystallizeTimeMultiplier.get()) {
                    this.crystallizeTimer = 0;

                    ItemStack result = this.currentRecipe.get().value().assemble(recipeInput, this.entity.level().registryAccess());
                    //make sure to ignore output multiplier on recipes that set that flag.
                    //prevents e.g. 1x ingot -> 3x dust -> 3x ingot -> 9x dust ...
                    float outputMultiplier = this.outputMultiplier.get();
                    if (this.currentRecipe.get().value().getIgnoreCrystallizeMultiplier())
                        outputMultiplier = 1;
                    int a = Math.min(this.operationCount.get(), handHeld.getCount());
                    result.setCount((int) (result.getCount() * a * outputMultiplier));
                    ItemStack inputCopy = handHeld.copy();
                    inputCopy.setCount(a);
                    handHeld.shrink(a);

                    this.onCrystallize(inputCopy, result);
                    var event = new CrystallizerJobEvent(this.entity, inputCopy, result);
                    NeoForge.EVENT_BUS.post(event);
                    if(!event.getResult().isEmpty()) {
                        ItemEntity droppedItem = this.entity.spawnAtLocation(event.getResult());
                        if (droppedItem != null) {
                            droppedItem.addTag(DROPPED_BY_CRYSTALLIZER);
                        }
                    }
                    //Don't reset recipe here, keep it cached
                }
            }
        }
        super.update();
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("conversionTimer", this.crystallizeTimer);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, HolderLookup.Provider provider) {
        super.readJobFromNBT(compound, provider);
        this.crystallizeTimer = compound.getInt("conversionTimer");
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        if (entity.getTags().contains(DROPPED_BY_CRYSTALLIZER) && entity.getAge() <
                Occultism.SERVER_CONFIG.spiritJobs.crystallizerResultPickupDelay.get())
            return false; //cannot pick up items a crystallizer (most likely *this* one) dropped util delay elapsed.

        ItemStack stack = entity.getItem();
        return !stack.isEmpty() && this.itemsToPickUp.stream().anyMatch(i -> i.test(stack));
    }

    @Override
    public List<Ingredient> getItemsToPickUp() {
        return this.itemsToPickUp;
    }

    /**
     * Called when an item was crystallized
     *
     * @param input  the input item.
     * @param output the output item.
     */
    public void onCrystallize(ItemStack input, ItemStack output) {

    }

    public static class CrystallizerJobEvent extends ItemProcessingJobEvent {
        public CrystallizerJobEvent(Entity entity, ItemStack input, ItemStack result) {
            super(entity, input, result);
        }
    }
}
