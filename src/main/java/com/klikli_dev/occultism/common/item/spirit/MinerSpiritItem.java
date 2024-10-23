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
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MinerSpiritItem extends Item {

    private final Supplier<Integer> maxMiningTime;
    private final Supplier<Integer> rollsPerOperation;
    private final Supplier<Integer> maxDamage;
    private final int enchantmentValue;

    public MinerSpiritItem(Properties properties, Supplier<Integer> maxMiningTime, Supplier<Integer> rollsPerOperation, Supplier<Integer> maxDamage) {
        super(properties.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
        this.maxMiningTime = maxMiningTime;
        this.rollsPerOperation = rollsPerOperation;
        this.maxDamage = maxDamage;
        this.enchantmentValue = 9;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        super.onCraftedBy(stack, worldIn, playerIn);
        stack.set(OccultismDataComponents.MAX_MINING_TIME, this.maxMiningTime.get());
        stack.set(OccultismDataComponents.ROLLS_PER_OPERATION, this.rollsPerOperation.get());
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip",
                TextUtil.formatDemonName(ItemNBTUtil.getBoundSpiritName(pStack))));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.maxDamage.get();
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        //cannot use verifyTagAfterLoad as config is not available at that time
        if(!stack.has(OccultismDataComponents.MAX_MINING_TIME))
            stack.set(OccultismDataComponents.MAX_MINING_TIME, this.maxMiningTime.get());
        if(!stack.has(OccultismDataComponents.ROLLS_PER_OPERATION))
            stack.set(OccultismDataComponents.ROLLS_PER_OPERATION, this.rollsPerOperation.get());
        return super.getMaxStackSize(stack);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
}
