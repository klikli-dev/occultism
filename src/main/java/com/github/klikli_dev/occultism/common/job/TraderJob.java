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
import com.github.klikli_dev.occultism.crafting.recipe.SpiritTrade;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TraderJob extends SpiritJob {

    //region Fields
    /**
     * The current ticks in the conversion, will convert once it reaches timeToConvert
     */
    protected int conversionTimer;
    protected int timeToConvert = 5;

    protected PickupItemsGoal pickupItemsGoal;

    protected SpiritTrade trade;
    protected int maxTradesPerRound = 4;
    //endregion Fields


    //region Initialization
    public TraderJob(SpiritEntity entity) {
        super(entity);
    }
    //endregion Initialization

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
     * Sets the id of the trading recipe. Recipe needs to be instanceof SpiritTrade
     *
     * @param recipeId the resource location for the recipe.
     */
    public void setTradeRecipeId(ResourceLocation recipeId) {
        this.trade = null;
        Optional<? extends IRecipe<?>> recipe = this.entity.world.getRecipeManager().getRecipe(recipeId);
        recipe.ifPresent(r -> {
            if (r instanceof SpiritTrade)
                this.trade = (SpiritTrade) r;
        });
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

    //region Overrides
    @Override
    public void init() {
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getHeldItem(Hand.MAIN_HAND);
        if (this.trade != null && this.trade.isValid(handHeld)) {
            if (this.entity.world.getGameTime() % 10 == 0) {
                //show particle effect while converting
                Vec3d pos = this.entity.getPositionVector();
                ((ServerWorld) this.entity.world)
                        .spawnParticle(ParticleTypes.PORTAL, pos.x + 0.5 + this.entity.world.rand.nextGaussian() / 3,
                                pos.y + 0.5, pos.z + 0.5 + this.entity.world.rand.nextGaussian() / 3, 1, 0.0, 0.0, 0.0,
                                0.0);
            }
            if (this.entity.world.getGameTime() % 20 == 0) {
                this.conversionTimer++;
            }
            if (this.conversionTimer >= this.getTimeToConvert()) {
                this.conversionTimer = 0;

                List<ItemStack> input = Arrays.asList(handHeld);
                int resultCount = 0;
                while (this.trade.isValid(input) && resultCount < this.maxTradesPerRound) {
                    input = this.trade.consume(input);
                    resultCount++;
                }

                if (input.isEmpty()) {
                    this.entity.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
                else {
                    this.entity.setHeldItem(Hand.MAIN_HAND, input.get(0));
                }


                ItemStack converted = this.trade.getRecipeOutput().copy();
                converted.setCount(resultCount);

                if (resultCount > 0) {
                    this.entity.entityDropItem(converted, 0.0f);
                    this.onConvert(resultCount);
                }
            }
        }
        else {
            this.conversionTimer = 0;
        }
        super.update();
    }

    @Override
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        compound.putInt("timeToConvert", this.timeToConvert);
        compound.putInt("conversionTimer", this.conversionTimer);
        compound.putInt("maxTradesPerRound", this.maxTradesPerRound);
        if (this.trade != null)
            compound.putString("spiritTradeId", this.trade.getId().toString());
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        super.readJobFromNBT(compound);
        this.timeToConvert = compound.getInt("timeToConvert");
        this.conversionTimer = compound.getInt("conversionTimer");
        this.maxTradesPerRound = compound.getInt("maxTradesPerRound");
        if (compound.contains("spiritTradeId")) {
            this.setTradeRecipeId(new ResourceLocation(compound.getString("spiritTradeId")));
        }
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.STONE);
    }
    //endregion Overrides

    //region Methods

    /**
     * Called when a conversion trade was successful.
     *
     * @param count the amount of items converted.
     */
    public void onConvert(int count) {

    }
    //endregion Methods
}
