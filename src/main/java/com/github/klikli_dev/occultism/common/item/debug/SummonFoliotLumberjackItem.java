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

package com.github.klikli_dev.occultism.common.item.debug;

import com.github.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.item.Item.Properties;

public class SummonFoliotLumberjackItem extends Item {

    //region Initialization
    public SummonFoliotLumberjackItem(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if(!context.getLevel().isClientSide){
            FoliotEntity spirit = OccultismEntities.FOLIOT.get().create(context.getLevel());
            spirit.finalizeSpawn((ServerWorld) context.getLevel(), context.getLevel().getCurrentDifficultyAt(context.getClickedPos()),
                    SpawnReason.SPAWN_EGG, null, null);
            spirit.tame(context.getPlayer());
            spirit.setPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1.0f, context.getClickedPos().getZ());
            spirit.setCustomName(new StringTextComponent("Testspirit Lumberjack"));

            //set up the job
            SpiritJob lumberjack = OccultismSpiritJobs.LUMBERJACK.get().create(spirit);
            lumberjack.init();
            spirit.setJob(lumberjack);

            //notify players nearby and spawn
            for (ServerPlayerEntity player : context.getLevel().getEntitiesOfClass(ServerPlayerEntity.class,
                    spirit.getBoundingBox().inflate(50)))
                CriteriaTriggers.SUMMONED_ENTITY.trigger(player, spirit);
            context.getLevel().addFreshEntity(spirit);
        }
        return ActionResultType.SUCCESS;
    }

    //endregion Overrides
}
