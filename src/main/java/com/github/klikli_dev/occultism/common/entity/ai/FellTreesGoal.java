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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.network.MessageSelectBlock;
import com.github.klikli_dev.occultism.network.OccultismPacketHandler;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class FellTreesGoal extends Goal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    protected int breakingTime;
    protected int previousBreakProgress;
    //endregion Fields

    //region Initialization
    public FellTreesGoal(SpiritEntity entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean shouldExecute() {
        if (!this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
            return false; //if already holding an item we need to first store it.
        }
        this.resetTarget();
        return this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        //only continue execution if a tree is available and entity is not carrying anything.
        return this.targetBlock != null && this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.targetBlock = null;
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.targetBlock != null) {
            if(!this.entity.getNavigator().setPath(this.entity.getNavigator().getPathToPos(this.targetBlock, 0), 1.0f)){
                RayTraceContext context = new RayTraceContext(this.entity.getEyePosition(0), Math3DUtil.center(this.targetBlock), RayTraceContext.BlockMode.COLLIDER,
                        RayTraceContext.FluidMode.NONE, this.entity);
                BlockRayTraceResult result = this.entity.world.rayTraceBlocks(context);
                if (result.getType() != BlockRayTraceResult.Type.MISS) {
                    BlockPos pos = result.getPos().offset(result.getFace());
                    if(Occultism.DEBUG.debugAI) {
                        OccultismPackets.sendToTracking(this.entity, new MessageSelectBlock(pos, 5000, 0x00ff00));
                    }
                    this.entity.getNavigator().setPath(this.entity.getNavigator().getPathToPos(pos, 0), 1.0f);
                }
            }
            if(Occultism.DEBUG.debugAI){
                OccultismPackets.sendToTracking(this.entity, new MessageSelectBlock(this.targetBlock, 5000, 0xffffff));
            }


            if (isLog(this.entity.world, this.targetBlock)) {
                double distance = this.entity.getPositionVector().distanceTo(Math3DUtil.center(this.targetBlock));
                if (distance < 2.5F) {
                    //start breaking when close
                    if (distance < 0.6F) {
                        //Stop moving if very close
                        this.entity.setMotion(0, 0, 0);
                        this.entity.getNavigator().clearPath();
                    }

                    this.updateBreakBlock();
                }
            }
            else if(isLeaf(this.entity.world, this.targetBlock)){
                this.entity.world.removeBlock(this.targetBlock, false);
            }
            else {
                this.resetTask();
            }
        }
    }
    //endregion Overrides

    //region Static Methods
    public static final boolean isLog(World world, BlockPos pos) {
        return BlockTags.LOGS.contains(world.getBlockState(pos).getBlock());
    }

    public static final boolean isLeaf(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof LeavesBlock || BlockTags.LEAVES.contains(block);
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

    private void resetTarget() {
         World world = this.entity.world;
        List<BlockPos> allBlocks = new ArrayList<>();
        BlockPos workAreaCenter = this.entity.getWorkAreaCenter();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        List<BlockPos> searchBlocks = BlockPos.getAllInBox(
                workAreaCenter.add(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                workAreaCenter.add(workAreaSize, workAreaSize / 2, workAreaSize)).map(BlockPos::toImmutable).collect(
                Collectors.toList());
        for (BlockPos pos : searchBlocks) {
            if (isLog(world, pos)) {

                //find top of tree
                BlockPos topOfTree = new BlockPos(pos);
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
            allBlocks = allBlocks.stream().distinct().sorted(this.targetSorter).collect(Collectors.toList());
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
            for (BlockPos pos : BlockPos.getAllInBox(log.add(-4, -4, -4), log.add(4, 0, 4)).map(BlockPos::toImmutable).collect(
                    Collectors.toList())) {
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
            for (BlockPos pos : BlockPos.getAllInBox(base.add(-8, 0, -8), base.add(8, 2, 8))
                                        .map(BlockPos::toImmutable).collect(Collectors.toList())) {
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
