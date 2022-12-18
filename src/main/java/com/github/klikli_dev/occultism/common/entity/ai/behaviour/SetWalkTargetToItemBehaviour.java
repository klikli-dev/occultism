package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.network.MessageSelectBlock;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

/**
 * Sets the WALK_TARGET memory based on the NEAREST_VISIBLE_WANTED_ITEM memory.
 */
public class SetWalkTargetToItemBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT), //we only pick up, if we can deposit
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected void start(E entity) {
        var jobItem = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        if (jobItem != null && jobItem.isAlive()) {
            if (entity.distanceToSqr(jobItem) < PickupItemBehaviour.PICKUP_RANGE_SQUARE) {
                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            } else {
                BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(jobItem, false));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(jobItem, 1.0f, 0));

                if (Occultism.DEBUG.debugAI) {
                    OccultismPackets.sendToTracking(entity, new MessageSelectBlock(jobItem.blockPosition(), 5000, 0x00ff00));
                }
            }
        } else {
            BrainUtils.clearMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
