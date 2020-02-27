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

import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SpiritAIFellTrees extends Goal {
    //region Fields
    protected final EntitySpirit entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    protected int breakingTime;
    protected int previousBreakProgress;
    //endregion Fields

    //region Initialization
    public SpiritAIFellTrees(EntitySpirit entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setMutexBits(1);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean shouldExecute() {
        if (!this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
            return false; //if already holding an item we need to first store it.
        }
        this.findTree();
        return this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        //only continue execution if a tree is available and entity is not carrying anything.
        return this.targetBlock != null && this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.findTree();
    }

    @Override
    public void updateTask() {
        if (this.targetBlock != null) {
            if (!this.entity.getNavigator().tryMoveToXYZ(this.targetBlock.getX() + 0.5D, this.targetBlock.getY(),
                    this.targetBlock.getZ() + 0.5D, 1D)) {

                BlockState targetBlockState = this.entity.world.getBlockState(this.targetBlock);
                RayTraceResult rayTrace = targetBlockState.collisionRayTrace(this.entity.world, this.targetBlock,
                        this.entity.getPositionVector(), Math3DUtil.getBlockCenter(this.targetBlock));

                if (rayTrace.typeOfHit != RayTraceResult.Type.MISS) {
                    BlockPos pos = rayTrace.getBlockPos().offset(rayTrace.sideHit);
                    this.entity.getNavigator()
                            .tryMoveToXYZ(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1D);
                }
            }

            if (isLog(this.entity.world, this.targetBlock)) {
                double distance = this.entity.getDistance(this.targetBlock.getX(), this.targetBlock.getY(),
                        this.targetBlock.getZ());
                if (distance < 2.5F) {
                    //start breaking when close
                    if (distance < 0.6F) {
                        //Stop moving if very close
                        this.entity.motionZ *= 0.0D;
                        this.entity.motionX *= 0.0D;
                        this.entity.getNavigator().clearPath();
                        this.entity.getMoveHelper().action = MovementController.Action.WAIT;
                    }

                    this.updateBreakBlock();
                }
            }
            else {
                this.targetBlock = null;
                this.resetTask();
            }
        }
    }
    //endregion Overrides

    //region Static Methods
    public static final boolean isLog(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isWood(world, pos);
    }

    public static final boolean isLeaf(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock().isLeaves(state, world, pos);
    }
    //endregion Static Methods

    //region Methods
    public void updateBreakBlock() {
        this.breakingTime++;
        this.entity.swingArm(Hand.MAIN_HAND);
        int i = (int) ((float) this.breakingTime / 160.0F * 10.0F);
        if (this.breakingTime % 10 == 0) {
            this.entity.playSound(SoundEvents.BLOCK_WOOD_HIT, 1, 1);
            this.entity.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1, 0.5F);
        }
        if (i != this.previousBreakProgress) {
            this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.targetBlock, i);
            this.previousBreakProgress = i;
        }
        if (this.breakingTime == 160) {
            this.entity.playSound(SoundEvents.BLOCK_WOOD_BREAK, 1, 1);
            this.breakingTime = 0;
            this.previousBreakProgress = -1;
            this.fellTree();
            this.targetBlock = null;
            this.resetTask();
        }

    }

    private void findTree() {
        World world = this.entity.world;
        List<BlockPos> allBlocks = new ArrayList<>();
        BlockPos workAreaCenter = this.entity.getWorkAreaCenter();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        Iterable<BlockPos> searchBlocks = BlockPos.getAllInBox(
                workAreaCenter.add(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                workAreaCenter.add(workAreaSize, workAreaSize / 2, workAreaSize));

        for (BlockPos pos : searchBlocks) {
            if (isLog(world, pos)) {

                //find top of tree
                BlockPos topOfTree = pos;
                while (!world.isAirBlock(topOfTree.up()) && topOfTree.getY() < world.getHeight()) {
                    topOfTree = topOfTree.up();
                }

                //find the stump of the tree
                if (isLeaf(world, topOfTree)) {
                    BlockPos logPos = this.getStump(topOfTree);
                    allBlocks.add(logPos);
                }
            }
        }
        //set closest log as target
        if (!allBlocks.isEmpty()) {
            allBlocks.sort(this.targetSorter);
            this.targetBlock = allBlocks.get(0);
        }
    }

    /**
     * Gets the stump for the given log.
     *
     * @param log the log
     * @return the stump block position.
     */
    private BlockPos getStump(BlockPos log) {
        if (log.getY() > 0) {
            //for all nearby logs and leaves, move one block down and recurse.
            for (BlockPos pos : BlockPos.getAllInBox(log.add(-4, -4, -4), log.add(4, 0, 4))) {
                if (isLog(this.entity.world, pos.down()) || isLeaf(this.entity.world, pos.down())) {
                    return this.getStump(pos.down());
                }
            }
        }
        return log;
    }

    private void fellTree() {
        World world = this.entity.world;
        BlockPos base = new BlockPos(this.targetBlock);
        Queue<BlockPos> queue = new LinkedList<BlockPos>();
        //iterate through tree and store logs
        while (isLog(world, base)) {
            if (!queue.contains(base)) {
                queue.add(base);
            }
            for (BlockPos pos : BlockPos.getAllInBox(base.add(-8, 0, -8), base.add(8, 2, 8))) {
                if (isLog(world, pos) && !queue.contains(pos)) {
                    if (isLog(world, pos.up()) && !isLog(world, base.up())) {
                        base = pos;
                    }
                    queue.add(pos);
                }
            }
            base = base.up();
        }
        //break all tree blocks
        while (!queue.isEmpty()) {
            BlockPos pop = queue.remove();
            world.destroyBlock(pop, true);
        }
    }
    //endregion Methods


}
