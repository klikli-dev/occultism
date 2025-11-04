package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.HashSet;
import java.util.List;

/**
 * Sets the WALK_TARGET memory based on the NEAREST_CROP memory.
 */
public class SetWalkTargetToCropBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    public static final int FORGET_UNREACHABLE_CROPS_AFTER_TICKS = 20 * 60 * 5;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.LAST_CROP_WALK_TARGET.get(), MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.NEAREST_CROP.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.UNREACHABLE_CROPS.get(), MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), MemoryStatus.REGISTERED)
    );

    @Override
    protected void start(E entity) {
        var cropPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());
        if (entity.distanceToSqr(Vec3.atCenterOf(cropPos)) < HarvestCropBehaviour.HARVEST_CROP_RANGE_SQUARE) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            BrainUtils.clearMemory(entity, OccultismMemoryTypes.LAST_CROP_WALK_TARGET.get());
        } else {
            BlockPos walkPos = null;

            var unreachableWalkTargets = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), HashSet::new);

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var pos = cropPos.relative(facing);
                if (entity.level().isEmptyBlock(pos) && !unreachableWalkTargets.contains(pos)) {
                    walkPos = pos;
                    break;
                }
            }

            if (walkPos != null) {
                BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(walkPos));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(walkPos, 1.0f, 1));
                BrainUtils.setMemory(entity, OccultismMemoryTypes.LAST_CROP_WALK_TARGET.get(), new WalkTarget(walkPos, 1.0f, 1));

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(cropPos, 1000, OccultismConstants.Color.MAGENTA));
                    Networking.sendToTracking(entity, new MessageSelectBlock(walkPos, 1000, OccultismConstants.Color.GREEN));
                }

            } else {
                var unreachableCrops = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_CROPS.get(), HashSet::new);
                unreachableCrops.add(cropPos);
                BrainUtils.setForgettableMemory(entity, OccultismMemoryTypes.UNREACHABLE_CROPS.get(), unreachableCrops, FORGET_UNREACHABLE_CROPS_AFTER_TICKS);

                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                BrainUtils.clearMemory(entity, OccultismMemoryTypes.LAST_CROP_WALK_TARGET.get());
                BrainUtils.clearMemory(entity, OccultismMemoryTypes.NEAREST_CROP.get());

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(cropPos, 10000, OccultismConstants.Color.RED));
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
