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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SummonFoliotManageMachineRitual extends SummonSpiritRitual {

    //region Initialization
    public SummonFoliotManageMachineRitual() {
        super(OccultismItems.BOOK_OF_CALLING_FOLIOT_MANAGE_MACHINE.get(),
                OccultismRituals.SUMMON_FOLIOT_BASIC_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()),
                "summon_foliot_manage_machine", 60);
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {

        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);

        //prepare active book of calling
        ItemStack result = this.getBookOfCallingBound(activationItem);

        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //set up the foliot entity
        SpiritEntity spirit = OccultismEntities.FOLIOT.get().create(world);
        this.prepareSpiritForSpawn(spirit, world, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(result));

        //set up the job
        SpiritJob manageMachine = OccultismSpiritJobs.MANAGE_MACHINE.get().create(spirit);
        manageMachine.init();
        spirit.setJob(manageMachine);

        //notify players nearby and spawn
        this.spawnEntity(spirit, world);

        //set up the book of calling
        this.finishBookOfCallingSetup(result, spirit, castingPlayer);
    }
    //endregion Overrides
}
