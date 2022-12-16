package com.github.klikli_dev.occultism.common.entity.ai.sensor;

import com.github.klikli_dev.occultism.common.entity.ai.BlockSorter;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.registry.OccultismSensors;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sets the NEAREST_TREE memory to the closest tree in the work area.
 */
public class NearestTreeSensor<E extends SpiritEntity> extends ExtendedSensor<E> {

    public static final int DEFAULT_SCAN_RATE_TICKS = 20 * 5;
    public static final int RESCAN_EMPTY_WORK_AREA_AFTER_TICKS = 20 * 60;
    public static final int RESET_NEAREST_TREE_AFTER_TICKS = 20 * 60 * 5;

    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
            OccultismMemoryTypes.NEAREST_TREE.get(),
            OccultismMemoryTypes.NON_TREE_LOGS.get()
    );

    public NearestTreeSensor() {
        this.setScanRate((entity) -> DEFAULT_SCAN_RATE_TICKS);
    }

    public static boolean isTreeSoil(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(OccultismTags.TREE_SOIL);
    }

    public static boolean isLog(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(BlockTags.LOGS);
    }

    public static boolean isLeaf(Level level, BlockPos pos) {
        var blockState = level.getBlockState(pos);
        return blockState.getBlock() instanceof LeavesBlock || blockState.is(BlockTags.LEAVES);
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return OccultismSensors.NEAREST_TREE.get();
    }

    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull E entity) {

        //if we currently have a tree, exit
        //this will time out after a while, but generally will be removed by the fell tree behaviour
        if (BrainUtils.hasMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get()))
            return;

        //if work area is empty, exit. This memory expires on its own to allow rescan
        if(BrainUtils.hasMemory(entity, OccultismMemoryTypes.NO_TREE_IN_WORK_AREA.get()))
            return;

        var ignoredTrees = BrainUtils.getMemory(entity, OccultismMemoryTypes.NON_TREE_LOGS.get());
        var workAreaCenter = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_CENTER.get());
        var workAreaSize = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_SIZE.get());

        //get blocks in work area, but only half height, we don't need full.
        var blocksInWorkArea = BlockPos.betweenClosedStream(
                workAreaCenter.offset(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                workAreaCenter.offset(workAreaSize, workAreaSize / 2, workAreaSize)
        ).map(BlockPos::immutable);

        //filter potential stumps
        List<BlockPos> potentialStumps = blocksInWorkArea.filter(pos -> isLog(level, pos) && isTreeSoil(level, pos.below()) && !ignoredTrees.contains(pos)).collect(Collectors.toList());

        var foundTree = false;
        if (!potentialStumps.isEmpty()) {
            potentialStumps.sort(new BlockSorter(entity));

            for (var potentialStump : potentialStumps) {
                //we only check if the stump is actually a tree one by one from closest to furthest to save perf.
                if (this.isTree(level, potentialStump)) {
                    //we have a tree, now we check if it is likely reachable
                    for (Direction facing : Direction.Plane.HORIZONTAL) {
                        BlockPos pos = potentialStump.relative(facing);
                        if (level.isEmptyBlock(pos)) {
                            BrainUtils.setForgettableMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get(), potentialStump, RESET_NEAREST_TREE_AFTER_TICKS);
                            foundTree = true;
                            break;
                        }
                    }
                } else {
                    //we have a stump, but it is not a tree, add it to the list of ignored stumps
                    ignoredTrees.add(potentialStump);
                    BrainUtils.setMemory(entity, OccultismMemoryTypes.NON_TREE_LOGS.get(), ignoredTrees);
                }
            }
        }

        if (!foundTree) {
            BrainUtils.clearMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
            BrainUtils.setForgettableMemory(entity, OccultismMemoryTypes.NO_TREE_IN_WORK_AREA.get(), true, RESCAN_EMPTY_WORK_AREA_AFTER_TICKS);
        }
    }

    private boolean isTree(Level level, BlockPos potentialStump) {
        if (isLog(level, potentialStump)) {

            //find top of tree
            BlockPos topOfTree = new BlockPos(potentialStump);
            while (!level.isEmptyBlock(topOfTree.above()) && topOfTree.getY() < level.getMaxBuildHeight()) {
                topOfTree = topOfTree.above();
            }

            //find the stump of the tree
            if (isLeaf(level, topOfTree)) {
                BlockPos logPos = this.getStump(level, topOfTree);
                return isLog(level, logPos);
            }
        }
        return false;
    }

    private BlockPos getStump(Level level, BlockPos log) {
        //for all nearby logs and leaves, move one block down and recurse.
        for (BlockPos pos : BlockPos.betweenClosedStream(log.offset(-4, -4, -4), log.offset(4, 0, 4)).map(BlockPos::immutable).toList()) {
            if (isLog(level, pos.below()) || isLeaf(level, pos.below())) {
                return this.getStump(level, pos.below());
            }
        }
        return log;
    }
}
