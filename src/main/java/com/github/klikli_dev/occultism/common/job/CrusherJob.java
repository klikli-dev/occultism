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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.common.entity.ai.PickupItemsGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.github.klikli_dev.occultism.crafting.recipe.ItemStackFakeInventory;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrusherJob extends SpiritJob {

    //region Fields
    /**
     * The current ticks in the crushing, will crush once it reaches crushing_time * crushingTimeMultiplier
     */
    protected int crushingTimer;
    protected float crushingTimeMultiplier;

    protected Optional<CrushingRecipe> currentRecipe = Optional.empty();
    protected PickupItemsGoal pickupItemsGoal;

    protected List<Ingredient> itemsToPickUp = new ArrayList<>();
    //endregion Fields


    //region Initialization
    public CrusherJob(SpiritEntity entity, float crushingTimeMultiplier) {
        super(entity);
        this.crushingTimeMultiplier = crushingTimeMultiplier;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void init() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.itemsToPickUp = this.entity.world.getRecipeManager().getRecipes().stream()
                                     .filter(recipe -> recipe.getType() == OccultismRecipes.CRUSHING_TYPE.get())
                                     .flatMap(recipe -> recipe.getIngredients().stream()).collect(Collectors.toList());
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getHeldItem(Hand.MAIN_HAND);
        ItemStackFakeInventory fakeInventory = new ItemStackFakeInventory(handHeld);

        if (!this.currentRecipe.isPresent() && !handHeld.isEmpty()) {
            this.currentRecipe = this.entity.world.getRecipeManager().getRecipe(OccultismRecipes.CRUSHING_TYPE.get(),
                    fakeInventory, this.entity.world);
            this.crushingTimer = 0;
            //play crushing sound
            this.entity.world
                    .playSound(null, this.entity.getPosition(), OccultismSounds.CRUNCHING.get(), SoundCategory.NEUTRAL, 0.5f,
                            1 + 0.5f * this.entity.getRNG().nextFloat());
        }
        if (this.currentRecipe.isPresent()) {
            if (handHeld.isEmpty() || !this.currentRecipe.get().matches(fakeInventory, this.entity.world)) {
                //Reset if recipe no longer matches
                this.currentRecipe = Optional.empty();
            }
            else {
                //advance conversion
                this.crushingTimer++;

                //show particle effect while crushing
                if (this.entity.world.getGameTime() % 10 == 0) {
                    Vector3d pos = this.entity.getPositionVec();
                    ((ServerWorld) this.entity.world)
                            .spawnParticle(ParticleTypes.PORTAL, pos.x + this.entity.world.rand.nextGaussian() / 3,
                                    pos.y + 0.5, pos.z + this.entity.world.rand.nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                                    0.0);
                }

                //every two seconds, play another crushing sound
                if (this.crushingTimer % 40 == 0) {
                    this.entity.world.playSound(null, this.entity.getPosition(), OccultismSounds.CRUNCHING.get(),
                            SoundCategory.NEUTRAL, 0.5f,
                            1 + 0.5f * this.entity.getRNG().nextFloat());
                }

                if (this.crushingTimer >= this.currentRecipe.get().getCrushingTime() * this.crushingTimeMultiplier) {
                    this.crushingTimer = 0;

                    ItemStack result = this.currentRecipe.get().getCraftingResult(fakeInventory);
                    ItemStack inputCopy = handHeld.copy();
                    inputCopy.setCount(1);
                    handHeld.shrink(1);

                    this.onCrush(inputCopy, result);
                    this.entity.entityDropItem(result);

                    this.currentRecipe = Optional.empty();
                }
            }
        }
        super.update();
    }

    @Override
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        compound.putFloat("crushingTimeMultiplier", this.crushingTimeMultiplier);
        compound.putInt("conversionTimer", this.crushingTimer);
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        super.readJobFromNBT(compound);
        this.crushingTimeMultiplier = compound.getFloat("crushingTimeMultiplier");
        this.crushingTimer = compound.getInt("conversionTimer");
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return !stack.isEmpty() && this.itemsToPickUp.stream().anyMatch(i -> i.test(stack));
    }
    //endregion Overrides

    //region Methods

    /**
     * Called when an item was crushed
     *
     * @param input  the input item.
     * @param output the output item.
     */
    public void onCrush(ItemStack input, ItemStack output) {

    }
    //endregion Methods
}
