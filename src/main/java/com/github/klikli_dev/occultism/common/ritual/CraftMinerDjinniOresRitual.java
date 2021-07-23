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

package com.github.klikli_dev.occultism.common.ritual;

import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.level.Level;
import net.minecraft.level.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

public class CraftMinerDjinniOresRitual extends Ritual {

    //region Initialization
    public CraftMinerDjinniOresRitual() {
        super(OccultismRituals.CRAFT_DJINNI_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()),
                "craft_miner_djinni_ores", 60);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, BlockEntity, castingPlayer, activationItem);
        ItemStack copy = activationItem.copy();
        activationItem.shrink(1); //remove activation item.

        ((ServerWorld) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        ItemStack result = new ItemStack(OccultismItems.MINER_DJINNI_ORES.get());

        //sets up nbt configuration for miner
        result.getItem().onCreated(result, level, castingPlayer);

        //copy over spirit name
        ItemNBTUtil.setBoundSpiritName(result, ItemNBTUtil.getBoundSpiritName(copy));

       this.dropResult(level, goldenBowlPosition, BlockEntity, castingPlayer, result);
    }
    //endregion Overrides
}
