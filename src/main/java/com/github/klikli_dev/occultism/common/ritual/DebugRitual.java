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

import com.github.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.github.klikli_dev.occultism.common.job.TraderJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class DebugRitual extends Ritual {

    //region Initialization
    public DebugRitual() {
        super(OccultismRituals.DEBUG_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                5);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {

        //set up the foliot entity
        FoliotEntity foliot = OccultismEntities.FOLIOT.get().create(level);
        this.prepareSpiritForSpawn(foliot, level, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(activationItem));

        activationItem.shrink(1); //remove original activation item from storage.

        foliot.setSpiritMaxAge(60);
        TraderJob trader = (TraderJob) OccultismSpiritJobs.TRADE_OTHERSTONE.get().create(foliot);
        trader.init();
        trader.setTradeRecipeId(modLoc("spirit_trade/test"));
        foliot.setJob(trader);

        //notify players nearby and spawn
        for (ServerPlayer player : level.getEntitiesWithinAABB(ServerPlayer.class,
                foliot.getBoundingBox().grow(50)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, foliot);

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        level.addEntity(foliot);
    }
    //endregion Overrides
}
