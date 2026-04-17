package com.klikli_dev.occultism.common.entity.ai.sensor;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.OccultismConstants.Color;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.ai.BlockSorter;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmlandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sets the NEAREST_CROP memory to the closest crop in the work area.
 */
public class NearestCropSensor<E extends SpiritEntity> extends ExtendedSensor<E> {

    public static final int DEFAULT_SCAN_RATE_TICKS = 20 * 5;
    public static final int RESCAN_EMPTY_WORK_AREA_AFTER_TICKS = 20 * 20;
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
            OccultismMemoryTypes.NEAREST_CROP.get(),
            OccultismMemoryTypes.NON_CROP.get(),
            OccultismMemoryTypes.NO_CROP_IN_WORK_AREA.get(),
            OccultismMemoryTypes.UNREACHABLE_CROPS.get(),
            OccultismMemoryTypes.WORK_AREA_CENTER.get(),
            OccultismMemoryTypes.WORK_AREA_SIZE.get()
    );

    public NearestCropSensor() {
        super(DEFAULT_SCAN_RATE_TICKS);
    }

    public static boolean isCropSoil(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof FarmlandBlock;
    }

    public static boolean isGrowthCrop(Level level, BlockPos pos) {
        var blockState = level.getBlockState(pos);
        return blockState.getBlock() instanceof CropBlock crop && crop.isMaxAge(blockState);
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull E entity) {

        //if we currently have a crop, exit
        //Will be removed by the fell crop behaviour
        if (BrainUtil.hasMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get()))
            return;

        //if work area is empty, exit. This memory expires on its own to allow rescan
        if (BrainUtil.hasMemory(entity, OccultismMemoryTypes.NO_CROP_IN_WORK_AREA.get()))
            return;

        var nonCrop = BrainUtil.memoryOrDefault(entity, OccultismMemoryTypes.NON_CROP.get(), HashSet::new);
        var unreachableCrops = BrainUtil.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_CROPS.get(), HashSet::new);
        var workAreaCenter = BrainUtil.getMemory(entity, OccultismMemoryTypes.WORK_AREA_CENTER.get());
        var workAreaSize = BrainUtil.getMemory(entity, OccultismMemoryTypes.WORK_AREA_SIZE.get());

        if (Occultism.DEBUG.debugAI) {
            for (var crop : unreachableCrops) {
                Networking.sendToTracking(entity, new MessageSelectBlock(crop, 10000, Color.ORANGE));
            }
            for (var crop : nonCrop) {
                Networking.sendToTracking(entity, new MessageSelectBlock(crop, 10000, Color.ORANGE));
            }
        }

        //get blocks in work area. We do /2 because we offset from the center
        var blocksInWorkArea = workAreaCenter != null && workAreaSize != null ? BlockPos.betweenClosedStream(
                workAreaCenter.offset(-workAreaSize / 2, -workAreaSize / 2, -workAreaSize / 2),
                workAreaCenter.offset(workAreaSize / 2, workAreaSize / 2, workAreaSize / 2)
        ).map(BlockPos::immutable) : BlockPos.betweenClosedStream(-1,-1,-1,1,1,1);

        //filter potential Roots
        List<BlockPos> potentialRoots = blocksInWorkArea
                .filter(pos -> isGrowthCrop(level, pos)
                        && isCropSoil(level, pos.below())
                        && !nonCrop.contains(pos)
                        && !unreachableCrops.contains(pos)
                )
                .collect(Collectors.toList());

        //TODO: refactor to search in increasing radius's? (manhattan distance helper might help, or "closest match"

        var foundCrop = false;
        if (!potentialRoots.isEmpty()) {
            potentialRoots.sort(new BlockSorter(entity));

            for (var potentialRoot : potentialRoots) {

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(potentialRoot, 5000, Color.WHITE));
                }

                //we only check if the Root is actually a crop one by one from closest to furthest to save perf.
                if (isGrowthCrop(level, potentialRoot)) {
                    //we have a crop, now we check if it is likely reachable
                    var isReachable = false;
                    for (Direction facing : Plane.HORIZONTAL) {
                        BlockPos pos = potentialRoot.relative(facing);
                        if (level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()) {
                            isReachable = true;
                            break;
                        }
                    }

                    if (isReachable) {
                        if (Occultism.DEBUG.debugAI) {
                            Networking.sendToTracking(entity, new MessageSelectBlock(potentialRoot, 50000, 0x800080));
                        }

                        BrainUtil.setMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get(), potentialRoot);
                        foundCrop = true;
                        break;
                    }
                } else {
                    //we have a Root, but it is not a crop, add it to the list of ignored Roots
                    nonCrop.add(potentialRoot);
                    BrainUtil.setMemory(entity, OccultismMemoryTypes.NON_CROP.get(), nonCrop);

                    if (Occultism.DEBUG.debugAI) {
                        Networking.sendToTracking(entity, new MessageSelectBlock(potentialRoot, 10000, 0xffff00));
                    }
                }
            }
        }

        if (!foundCrop) {
            BrainUtil.clearMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
            BrainUtil.setForgettableMemory(entity, OccultismMemoryTypes.NO_CROP_IN_WORK_AREA.get(), true, RESCAN_EMPTY_WORK_AREA_AFTER_TICKS);
        }
    }
}
