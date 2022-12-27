package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.network.MessageSelectBlock;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.HashSet;
import java.util.List;

public class HandleUnreachableTreeBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    public static final int FORGET_UNREACHABLE_WALK_TARGETS_AFTER_TICKS = 20 * 60 * 5;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.NEAREST_TREE.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected void start(E entity) {
        if (BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get(), () -> false)) {
            var unreachableWalkTargets = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), HashSet::new);
            var walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);
            if (walkTarget != null && walkTarget.getTarget() instanceof BlockPosTracker) {
                unreachableWalkTargets.add(walkTarget.getTarget().currentBlockPosition());

                BrainUtils.setForgettableMemory(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), unreachableWalkTargets, FORGET_UNREACHABLE_WALK_TARGETS_AFTER_TICKS);

                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                BrainUtils.clearMemory(entity, OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get());

                if (Occultism.DEBUG.debugAI) {
                    OccultismPackets.sendToTracking(entity, new MessageSelectBlock(walkTarget.getTarget().currentBlockPosition(), 50000, 0xFF0000));
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
