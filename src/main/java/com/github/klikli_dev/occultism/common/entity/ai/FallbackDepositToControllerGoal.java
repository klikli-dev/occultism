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

package com.github.klikli_dev.occultism.common.entity.ai;

import com.github.klikli_dev.occultism.api.common.blockentity.IStorageControllerProxy;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.ManageMachineJob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * If there is a handheld item and no deposit location, it will try to deposit in a storage controller.
 */
public class FallbackDepositToControllerGoal extends PausableGoal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected ManageMachineJob job;
    protected int retries = 0;
    //endregion Fields

    //region Initialization
    public FallbackDepositToControllerGoal(SpiritEntity entity, ManageMachineJob job) {
        this.entity = entity;
        this.job = job;
        this.targetSorter = new BlockSorter(entity);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }
    //endregion Initialization


    //region Overrides
    @Override
    public boolean canUse() {
        //do not use if there is a target to attack
        if (this.entity.getTarget() != null) {
            return false;
        }

        if (this.job.getStorageController() == null)
            return false;

        //if we are holding something but have no deposit location we can execute this
        return !this.isPaused() && !this.entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() &&
                !this.entity.getDepositPosition().isPresent();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void tick() {
        BlockEntity storageProxy = this.findClosestStorageProxy();
        if (storageProxy != null) {
            this.entity.setDepositPosition(storageProxy.getBlockPos());
            this.entity.setDepositFacing(Direction.UP);
        } else {
            //keep track of retries to wait increasing amounts of time up to 5 min
            if (this.retries <= 60)
                this.retries++;

            //wait before trying again.
            this.pause(this.retries * 10000L);
        }

    }
    //endregion Overrides

    //region Methods

    protected BlockEntity findClosestStorageProxy() {
        Level level = this.entity.level;
        List<BlockPos> allBlocks = new ArrayList<>();
        if(this.job.getManagedMachine() == null)
            return null;

        BlockPos machinePosition = this.job.getManagedMachine().insertGlobalPos.getPos();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        List<BlockPos> searchBlocks = BlockPos.betweenClosedStream(
                        machinePosition.offset(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                        machinePosition.offset(workAreaSize, workAreaSize / 2, workAreaSize)).map(BlockPos::immutable)
                .collect(Collectors.toList());

        for (BlockPos pos : searchBlocks) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof IStorageControllerProxy proxy) {
                if (proxy.getLinkedStorageControllerPosition() != null &&
                        proxy.getLinkedStorageControllerPosition().equals(this.job.getStorageControllerPosition()))
                    allBlocks.add(pos);
            }
        }

        //set closest log as target
        if (!allBlocks.isEmpty()) {
            allBlocks.sort(this.targetSorter);
            return level.getBlockEntity(allBlocks.get(0));
        }
        return null;
    }

    //endregion Methods
}
