package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.network.MessageSelectBlock;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

/**
 * Sets the WALK_TARGET memory based on the NEAREST_TREE memory.
 */
public class SetWalkToTreeTargetBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.NEAREST_TREE.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected void start(E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.NEAREST_TREE.get());
        if (entity.distanceToSqr(Vec3.atCenterOf(treePos)) < FellTreeBehaviour.FELL_TREE_RANGE) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        } else {

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var pos = treePos.relative(facing);
                if (entity.getLevel().isEmptyBlock(pos)) {
                    treePos = pos;
                    break;
                }
            }

            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(treePos));
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(treePos, 1.0f, 2));

            if (Occultism.DEBUG.debugAI) {
                OccultismPackets.sendToTracking(entity, new MessageSelectBlock(treePos, 5000, 0x00ff00));
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
