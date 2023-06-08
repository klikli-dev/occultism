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

package com.klikli_dev.occultism.common.item.spirit;

import com.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MinerSpiritItem extends Item {

    private final Supplier<Integer> maxMiningTime;
    private final Supplier<Integer> rollsPerOperation;
    private final Supplier<Integer> maxDamage;

    public MinerSpiritItem(Properties properties, Supplier<Integer> maxMiningTime, Supplier<Integer> rollsPerOperation, Supplier<Integer> maxDamage) {
        super(properties);
        this.maxMiningTime = maxMiningTime;
        this.rollsPerOperation = rollsPerOperation;
        this.maxDamage = maxDamage;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        super.onCraftedBy(stack, worldIn, playerIn);
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.MAX_MINING_TIME_TAG, this.maxMiningTime.get());
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.ROLLS_PER_OPERATION_TAG, this.rollsPerOperation.get());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(stack))));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.maxDamage.get();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.MAX_MINING_TIME_TAG, this.maxMiningTime.get());
        stack.getOrCreateTag().putInt(DimensionalMineshaftBlockEntity.ROLLS_PER_OPERATION_TAG, this.rollsPerOperation.get());
        return super.initCapabilities(stack, nbt);
    }
}
