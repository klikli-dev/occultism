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

package com.klikli_dev.occultism.common.ritual;

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UnbreakableRitual extends Ritual {

    public UnbreakableRitual(RitualRecipe recipe) {
        super(recipe);
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity, @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, blockEntity, castingPlayer, activationItem);

        ItemStack result = activationItem.copy();
        activationItem.shrink(1); //remove activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        result.setDamageValue(0);

        result.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        if (result.isEnchanted()) {
            EnchantmentHelper.updateEnchantments(result, p_330066_ -> p_330066_.removeIf(p_344368_ -> p_344368_.is(Enchantments.UNBREAKING)));
            EnchantmentHelper.updateEnchantments(result, p_330066_ -> p_330066_.removeIf(p_344368_ -> p_344368_.is(Enchantments.MENDING)));
        }
        result.set(DataComponents.RARITY, Rarity.EPIC);
        result.set(DataComponents.CUSTOM_NAME, Component.empty()
                .append(ChatFormatting.OBFUSCATED + "nice   " + ChatFormatting.RESET)
                .append(result.getHoverName())
                .append(ChatFormatting.OBFUSCATED + "   ecin" + ChatFormatting.RESET));

        this.dropResult(level, goldenBowlPosition, blockEntity, castingPlayer, result, true);
    }
}
