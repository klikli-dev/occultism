/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.item;

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Item class to represent rituals as items with tooltip - enables JEI search for rituals
 */
public class DummyTooltipItem extends Item {

    public DummyTooltipItem(Properties properties) {
        super(properties);
    }

    public void performRitual(Level level, BlockPos pos, GoldenSacrificialBowlBlockEntity blockEntity,
                              @Nullable Player player, ItemStack activationItem) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        var ritualRecipe = serverLevel.recipeAccess().getRecipes()
                .stream()
                .filter(r -> r.value().getType() == OccultismRecipes.RITUAL_TYPE.get())
                .filter(r -> ((RitualRecipe) r.value()).getRitualDummy().getItem() == this)
                .findFirst();

        var serverPlayer = player instanceof ServerPlayer s ? s : null;
        ritualRecipe.ifPresent(r -> ((RitualRecipe) r.value()).getRitual().finish(level, pos, blockEntity, serverPlayer, activationItem));
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, TooltipDisplay pDisplay, Consumer<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pDisplay, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.accept(Component.translatable(pStack.getItem().getDescriptionId() + ".tooltip"));
    }
}
