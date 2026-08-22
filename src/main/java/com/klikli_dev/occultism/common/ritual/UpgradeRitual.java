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
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeRitual extends Ritual {

    public UpgradeRitual(RitualRecipe recipe) {
        super(recipe);
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity, @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, blockEntity, castingPlayer, activationItem);

        ItemStack copy = activationItem.copy();
        activationItem.shrink(1); //remove activation item.

        List<ItemStack> consumed = blockEntity.consumedIngredients;
        ItemStack baseStack = consumed.isEmpty() ? ItemStack.EMPTY : blockEntity.consumedIngredients.getFirst().copy();

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        ItemStack result = this.recipe.getResult().copy();

        //Only keep selected components
        DataComponentMap baseMap = baseStack.getComponents();
        baseMap.forEach(component -> {
            DataComponentType<?> type = component.type();
            if(type != DataComponents.DAMAGE
                    && type != DataComponents.UNBREAKABLE
                    && type != DataComponents.ENCHANTMENTS
                    && type != DataComponents.DYED_COLOR
                    && type != DataComponents.BUNDLE_CONTENTS
                    && type != DataComponents.POTION_CONTENTS
                    && type != DataComponents.POTION_DURATION_SCALE
                    && type != DataComponents.WRITABLE_BOOK_CONTENT
                    && type != DataComponents.WRITTEN_BOOK_CONTENT
                    && type != DataComponents.TRIM
                    && type != DataComponents.ENTITY_DATA
                    && type != DataComponents.BUCKET_ENTITY_DATA
                    && type != DataComponents.BLOCK_ENTITY_DATA
                    && type != DataComponents.LODESTONE_TRACKER
                    && type != DataComponents.BANNER_PATTERNS
                    && type != DataComponents.BASE_COLOR
                    && type != DataComponents.CONTAINER
                    && type != DataComponents.BLOCK_STATE
                    && type != DataComponents.BEES
                    && type != DataComponents.LOCK
                    && type != DataComponents.CONTAINER_LOOT
                    && type != OccultismDataComponents.OCCUPIED.get()
                    && type != OccultismDataComponents.FAMILIAR_TYPE.get()
                    && type != OccultismDataComponents.FAMILIAR_DATA.get()
                    && type != OccultismDataComponents.UNBREAKABLE.get()
                    && type != OccultismDataComponents.STORED_XP.get()
                    && type != OccultismDataComponents.LINKED_PLAYER_NAME.get()
                    && type != OccultismDataComponents.LINKED_PLAYER_UUID.get()
                    && type != OccultismDataComponents.STORAGE_CONTROLLER_CONTENTS.get()
                    && type != OccultismDataComponents.CRAFTING_MATRIX.get()) {
                baseStack.remove(type);
            }
        });
        baseMap = baseStack.getComponents();

        result.applyComponents(baseMap);
        if (result.has(OccultismDataComponents.SPIRIT_NAME))
            ItemNBTUtil.setBoundSpiritName(result, ItemNBTUtil.getBoundSpiritName(copy));
        this.dropResult(level, goldenBowlPosition, blockEntity, castingPlayer, result, true);
    }
}
