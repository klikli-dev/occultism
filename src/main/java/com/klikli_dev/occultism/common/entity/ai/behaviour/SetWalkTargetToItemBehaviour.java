package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.OccultismConstants.Color;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

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

    public SetWalkTargetToItemBehaviour() {
        super(MEMORY_REQUIREMENTS);
    }

    @Override
    protected void start(E entity) {
        var jobItem = BrainUtil.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        if (jobItem != null && jobItem.isAlive()) {
            if (Math3DUtil.withinAxisDistances(entity.position(), jobItem.position(),
                    PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE,
                    PickupItemBehaviour.PICKUP_Y_RANGE,
                    PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE)) {
                BrainUtil.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            } else {
                BrainUtil.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(jobItem, false));
                BrainUtil.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(jobItem, 1.0f, 0));

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(jobItem.blockPosition(), 5000, Color.GREEN));
                }
            }
        } else {
            BrainUtil.clearMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        }
    }
}
