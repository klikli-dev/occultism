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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.TraderJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class SummonFoliotOtherstoneTraderRitual extends SummonSpiritRitual {

    //region Initialization
    public SummonFoliotOtherstoneTraderRitual() {
        super(null, OccultismRituals.SUMMON_FOLIOT_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                "summon_foliot_otherstone_trader", 30);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, BlockEntity, castingPlayer, activationItem);

        //consume activation item
        ItemStack activationItemCopy = activationItem.copy();
        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //set up the foliot entity
        SpiritEntity spirit = OccultismEntities.FOLIOT.get().create(level);
        this.prepareSpiritForSpawn(spirit, level, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(activationItemCopy));

        //set up the job
        TraderJob exchange = (TraderJob) OccultismSpiritJobs.TRADE_OTHERSTONE.get().create(spirit);
        exchange.setTradeRecipeId(new ResourceLocation(Occultism.MODID, "spirit_trade/stone_to_otherstone"));
        exchange.init();
        exchange.setTimeToConvert(15);
        exchange.setMaxTradesPerRound(4);
        spirit.setJob(exchange);
        spirit.setSpiritMaxAge(Occultism.SERVER_CONFIG.spiritJobs.tier1OtherstoneTraderMaxAgeSeconds.get());

        //notify players nearby and spawn
        this.spawnEntity(spirit, level);
    }
    //endregion Overrides
}
