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

package com.github.klikli_dev.occultism.common.item.spirit;

import com.github.klikli_dev.occultism.common.tile.DimensionalMineshaftBlockEntity;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public class MinerSpiritItem extends Item {

    protected static Field maxDamageField =
            ObfuscationReflectionHelper.findField(Item.class, "field_77699_b");

    //region Fields
    private final Supplier<Integer>  maxMiningTime;
    private final Supplier<Integer> rollsPerOperation;
    private final Supplier<Integer> maxDamage;
    private boolean hasInitializedMaxDamage;
    //endregion Fields

    //region Initialization
    public MinerSpiritItem(Properties properties, Supplier<Integer> maxMiningTime, Supplier<Integer>  rollsPerOperation, Supplier<Integer> maxDamage) {
        super(properties);
        this.maxMiningTime = maxMiningTime;
        this.rollsPerOperation = rollsPerOperation;
        this.maxDamage = maxDamage;
        this.hasInitializedMaxDamage = false;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                               TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if(!this.hasInitializedMaxDamage){
            this.hasInitializedMaxDamage = true;
            try {
                maxDamageField.setInt(this, this.maxDamage.get());
            } catch (IllegalAccessException ignored) {

            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        super.onCraftedBy(stack, worldIn, playerIn);
        if(!this.hasInitializedMaxDamage){
            this.hasInitializedMaxDamage = true;
            try {
                maxDamageField.setInt(this, this.maxDamage.get());
            } catch (IllegalAccessException ignored) {

            }
        }
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.MAX_MINING_TIME_TAG, this.maxMiningTime.get());
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.ROLLS_PER_OPERATION_TAG, this.rollsPerOperation.get());
    }
    //endregion Overrides
}
