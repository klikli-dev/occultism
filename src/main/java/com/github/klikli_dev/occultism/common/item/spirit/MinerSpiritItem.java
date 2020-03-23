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

import com.github.klikli_dev.occultism.common.tile.DimensionalMineshaftTileEntity;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MinerSpiritItem extends Item {

    //region Fields
    private final int maxMiningTime;
    private final int rollsPerOperation;
    //endregion Fields

    //region Initialization
    public MinerSpiritItem(Properties properties, int maxMiningTime, int rollsPerOperation) {
        super(properties);
        this.maxMiningTime = maxMiningTime;
        this.rollsPerOperation = rollsPerOperation;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);

        stack.getOrCreateTag().putInt(DimensionalMineshaftTileEntity.MAX_MINING_TIME_TAG, this.maxMiningTime);
        stack.getOrCreateTag().putInt(DimensionalMineshaftTileEntity.ROLLS_PER_OPERATION_TAG, this.rollsPerOperation);
    }
    //endregion Overrides
}
