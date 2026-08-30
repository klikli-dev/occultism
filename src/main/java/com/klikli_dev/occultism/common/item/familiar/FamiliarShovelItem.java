/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.klikli_dev.occultism.common.item.familiar;

import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.api.common.item.IOtherworldTool;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class FamiliarShovelItem extends ShovelItem implements FamiliarCurio, IOtherworldTool {

    public FamiliarShovelItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        super(material, attackDamageBaseline, attackSpeedBaseline, properties);
    }

    @Override
    public OtherworldBlockTier getHarvestTier(ItemStack stack) {
        return ItemNBTUtil.getOccupied(stack) ? OtherworldBlockTier.TWO : OtherworldBlockTier.ONE;
    }

    @Override
    public int getMaxStackSize(@NonNull ItemStack stack) {
        //force generation of a name if it does not exist yet.
        //this might get around loot tables caching the stack
        ItemNBTUtil.getBoundSpiritName(stack);
        return super.getMaxStackSize(stack);
    }

    @Override
    public boolean isFoil(@NonNull ItemStack pStack) {
        if (FMLEnvironment.getDist() == Dist.CLIENT)
            return DistHelper.isFoil(pStack);
        return false;
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemStack, @NonNull ServerLevel level,
                              @NonNull Entity owner, @Nullable EquipmentSlot slot) {
        familiarInventoryTick(itemStack, level, owner, slot);
    }

    @Override
    public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack stack, @NonNull Player playerIn,
                                                           @NonNull LivingEntity target, @NonNull InteractionHand hand) {
        return familiarInteractLivingEntity(stack, playerIn, target, hand);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() == null)
            return InteractionResult.FAIL;

        if (pContext.getPlayer().isShiftKeyDown()) {
            return super.useOn(pContext);
        } else {
            return familiarUseOn(pContext);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipComponents, pTooltipFlag);

        if (ItemNBTUtil.getOccupied(pStack) && FMLEnvironment.getDist() == Dist.CLIENT) {
            DistHelper.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        } else {
            pTooltipComponents.accept(Component.translatable(
                    pStack.getItem().getDescriptionId() + ".tooltip.empty"));
        }
    }
}
