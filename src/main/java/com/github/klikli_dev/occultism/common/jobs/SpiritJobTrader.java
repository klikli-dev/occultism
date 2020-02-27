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

package com.github.klikli_dev.occultism.common.jobs;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIPickupItems;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritTrade;
import com.github.klikli_dev.occultism.network.MessageParticle;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

public class SpiritJobTrader extends SpiritJob {

    //region Fields
    /**
     * The current ticks in the conversion, will convert once it reaches timeToConvert
     */
    protected int conversionTimer;
    protected int timeToConvert = 5;

    protected SpiritAIPickupItems aiPickupItems;

    protected SpiritTrade trade;
    protected int maxTradesPerRound = 4;
    //endregion Fields


    //region Initialization
    public SpiritJobTrader(EntitySpirit entity) {
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
        IRecipe recipe = ForgeRegistries.RECIPES.getValue(recipeId);
        if (recipe instanceof SpiritTrade)
            this.trade = (SpiritTrade) recipe;
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
        this.entity.targetTasks.addTask(1, this.aiPickupItems = new SpiritAIPickupItems(this.entity));
    }

    @Override
    public void cleanup() {
        this.entity.targetTasks.removeTask(this.aiPickupItems);
    }

    @Override
    public void update() {
        ItemStack handHeld = this.entity.getHeldItem(Hand.MAIN_HAND);
        if (this.trade != null && this.trade.isValid(handHeld)) {
            if (this.entity.world.getTotalWorldTime() % 10 == 0) {
                //show particle effect while converting
                Vec3d pos = this.entity.getPositionVector();
                Occultism.network.sendToDimension(new MessageParticle(EnumParticleTypes.PORTAL,
                                pos.x + 0.5 + this.entity.world.rand.nextGaussian() / 3, pos.y + 0.5,
                                pos.z + 0.5 + this.entity.world.rand.nextGaussian() / 3),
                        this.entity.world.provider.getDimension());
            }
            if (this.entity.world.getTotalWorldTime() % 20 == 0) {
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
        compound.setInteger("timeToConvert", this.timeToConvert);
        compound.setInteger("conversionTimer", this.conversionTimer);
        compound.setInteger("maxTradesPerRound", this.maxTradesPerRound);
        if (this.trade != null)
            compound.setString("spiritTradeId", this.trade.getRegistryName().toString());
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        super.readJobFromNBT(compound);
        this.timeToConvert = compound.getInteger("timeToConvert");
        this.conversionTimer = compound.getInteger("conversionTimer");
        this.maxTradesPerRound = compound.getInteger("maxTradesPerRound");
        if (compound.hasKey("spiritTradeId")) {
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
