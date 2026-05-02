package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants.Color;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

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

    public SetWalkTargetToTreeBehaviour() {
        super(MEMORY_REQUIREMENTS);
    }

    @Override
    protected void start(E entity) {
        var treePos = BrainUtil.getMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
        if (entity.distanceToSqr(Vec3.atCenterOf(treePos)) < FellTreeBehaviour.FELL_TREE_RANGE_SQUARE) {
            BrainUtil.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            BrainUtil.clearMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get());
        } else {
            BlockPos walkPos = null;

            var unreachableWalkTargets = BrainUtil.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), HashSet::new);

            for (Direction facing : Plane.HORIZONTAL) {
                var pos = treePos.relative(facing);
                if (entity.level().isEmptyBlock(pos) && !unreachableWalkTargets.contains(pos)) {
                    walkPos = pos;
                    break;
                }
            }

            if (walkPos != null) {
                BrainUtil.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(walkPos));
                BrainUtil.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(walkPos, 1.0f, 1));
                BrainUtil.setMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get(), new WalkTarget(walkPos, 1.0f, 1));

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(treePos, 5000, Color.MAGENTA));
                    Networking.sendToTracking(entity, new MessageSelectBlock(walkPos, 5000, Color.GREEN));
                }

            } else {
                var unreachableTrees = BrainUtil.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_TREES.get(), HashSet::new);
                unreachableTrees.add(treePos);
                BrainUtil.setForgettableMemory(entity, OccultismMemoryTypes.UNREACHABLE_TREES.get(), unreachableTrees, FORGET_UNREACHABLE_TREES_AFTER_TICKS);

                BrainUtil.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                BrainUtil.clearMemory(entity, OccultismMemoryTypes.LAST_TREE_WALK_TARGET.get());
                BrainUtil.clearMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(treePos, 50000, Color.RED));
                }
            }
        }
    }
}
