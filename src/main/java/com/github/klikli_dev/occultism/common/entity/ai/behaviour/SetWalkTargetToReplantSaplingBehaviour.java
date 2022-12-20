package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.network.MessageSelectBlock;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.util.StorageUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import com.klikli_dev.forgebrainlib.api.core.behaviour.ExtendedBehaviour;
import com.klikli_dev.forgebrainlib.util.BrainUtils;

import java.util.List;

/**
 * Sets the WALK_TARGET memory based on the LAST_FELLED_TREE memory.
 */
public class SetWalkTargetToReplantSaplingBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.LAST_FELLED_TREE.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return StorageUtil.getFirstMatchingSlot(entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new), ItemTags.SAPLINGS) != -1;
    }

    @Override
    protected void start(E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());
        if (entity.distanceToSqr(Vec3.atCenterOf(treePos)) < ReplantSaplingBehaviour.REPLANT_RANGE_SQUARE) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        } else {
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(treePos));
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(treePos, 1.0f, 0));

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
