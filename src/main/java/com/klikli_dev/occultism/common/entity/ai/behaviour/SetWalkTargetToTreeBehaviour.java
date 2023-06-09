package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.MessageSelectBlock;
import com.klikli_dev.occultism.network.OccultismPackets;
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
 * Sets the WALK_TARGET memory based on the NEAREST_TREE memory.
 */
public class SetWalkTargetToTreeBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    public static final int FORGET_UNREACHABLE_TREES_AFTER_TICKS = 20 * 60 * 5;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get(), MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.NEAREST_TREE.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.UNREACHABLE_TREES.get(), MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), MemoryStatus.REGISTERED)
    );

    @Override
    protected void start(E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
        if (entity.distanceToSqr(Vec3.atCenterOf(treePos)) < FellTreeBehaviour.FELL_TREE_RANGE_SQUARE) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            BrainUtils.clearMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get());
        } else {
            BlockPos walkPos = null;

            var unreachableWalkTargets = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), HashSet::new);

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var pos = treePos.relative(facing);
                if (entity.level().isEmptyBlock(pos) && !unreachableWalkTargets.contains(pos)) {
                    walkPos = pos;
                    break;
                }
            }

            if (walkPos != null) {
                BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(walkPos));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(walkPos, 1.0f, 1));
                BrainUtils.setMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get(), new WalkTarget(walkPos, 1.0f, 1));

                if (Occultism.DEBUG.debugAI) {
                    OccultismPackets.sendToTracking(entity, new MessageSelectBlock(treePos, 5000, OccultismConstants.Color.MAGENTA));
                    OccultismPackets.sendToTracking(entity, new MessageSelectBlock(walkPos, 5000, OccultismConstants.Color.GREEN));
                }

            } else {
                var unreachableTrees = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_TREES.get(), HashSet::new);
                unreachableTrees.add(treePos);
                BrainUtils.setForgettableMemory(entity, OccultismMemoryTypes.UNREACHABLE_TREES.get(), unreachableTrees, FORGET_UNREACHABLE_TREES_AFTER_TICKS);

                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                BrainUtils.clearMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get());
                BrainUtils.clearMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());

                if (Occultism.DEBUG.debugAI) {
                    OccultismPackets.sendToTracking(entity, new MessageSelectBlock(treePos, 50000, OccultismConstants.Color.RED));
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
