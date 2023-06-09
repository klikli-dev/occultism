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

package com.klikli_dev.occultism.common.entity.ai.goal;

import com.klikli_dev.occultism.common.entity.ai.BlockSorter;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ReplantSaplingGoal extends Goal {
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos moveTarget = null;

    public ReplantSaplingGoal(SpiritEntity entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    /**
     * @return the position to move to to deposit the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.position(), Math3DUtil.center(this.moveTarget));
        return this.moveTarget.relative(Direction.fromYRot(angle).getOpposite());
    }

    @Override
    public boolean canUse() {
        //nothing to deposit in hand
        if (!this.entity.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.SAPLINGS)) {
            return false;
        }
//        if (!this.entity.getJob().map(j -> (LumberjackJob) j).map(j -> j.getLastFelledTree() != null).orElse(false))
//            return false;
        this.resetTarget();
        return this.moveTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
//        return this.moveTarget != null && !this.entity.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.SAPLINGS)
//                && !this.entity.getJob().map(j -> (LumberjackJob) j).map(j -> j.getLastFelledTree() != null).orElse(false);
    }

    public void stop() {
        this.entity.getNavigation().stop();
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.moveTarget != null) {
            float accessDistance = 4.5f;

            double distance = this.entity.position().distanceTo(Math3DUtil.center(this.moveTarget));

            if (distance < accessDistance) {
                //stop moving while planting
                this.entity.getNavigation().stop();

//                this.entity.getJob().map(j -> (LumberjackJob) j).map(LumberjackJob::getLastFelledTree).ifPresent(lastFelledTree -> {
//
//                    if (this.entity.level().isEmptyBlock(lastFelledTree)) {
//                        ItemStack sapling = this.entity.getItemInHand(InteractionHand.MAIN_HAND);
//                        if (sapling.getItem() instanceof BlockItem saplingBlockItem) {
//                            this.entity.level().setBlockAndUpdate(lastFelledTree, saplingBlockItem.getBlock().defaultBlockState());
//                            sapling.shrink(1);
//                        }
//                    }
//                });
//
//
//                //reset last felled tree
//                this.entity.getJob().map(j -> (LumberjackJob) j).ifPresent(j -> j.setLastFelledTree(null));

            } else {
                //continue moving
                BlockPos moveTarget = this.getMoveTarget();
                this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(moveTarget, 0), 1.0f);
            }
        } else {
            this.resetTarget(); //if there is no tile entity, recheck
        }
    }

    private void resetTarget() {
        this.moveTarget = null;
//        this.entity.getJob().map(j -> (LumberjackJob) j).map(LumberjackJob::getLastFelledTree)
//                .ifPresent(t -> this.moveTarget = t);
    }

}
