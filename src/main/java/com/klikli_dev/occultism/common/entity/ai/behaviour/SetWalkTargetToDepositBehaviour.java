package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.StorageUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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
 * Sets the WALK_TARGET memory based on the LAST_FELLED_TREE memory.
 */
public class SetWalkTargetToDepositBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return StorageUtil.getFirstFilledSlot(entity.inventory.orElseThrow(ItemHandlerMissingException::new)) != -1;
    }

    @Override
    protected void start(E entity) {
        var depositPos = BrainUtils.getMemory(entity, OccultismMemoryTypes.DEPOSIT_POSITION.get());

        if (entity.distanceToSqr(Vec3.atCenterOf(depositPos)) < DepositItemsBehaviour.DEPOSIT_ITEM_RANGE_SQUARE) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        } else {
            BlockPos walkPos = null;

            var unreachableWalkTargets = BrainUtils.memoryOrDefault(entity, OccultismMemoryTypes.UNREACHABLE_WALK_TARGETS.get(), HashSet::new);

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var pos = depositPos.relative(facing);
                if (entity.level().isEmptyBlock(pos) && !unreachableWalkTargets.contains(pos)) {
                    walkPos = pos;
                    break;
                }
            }

            if (walkPos != null) {
                BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(walkPos));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(walkPos, 1.0f, 1));

                if (Occultism.DEBUG.debugAI) {

                    Networking.sendToTracking(entity, new MessageSelectBlock(depositPos, 5000, OccultismConstants.Color.MAGENTA));
                    Networking.sendToTracking(entity, new MessageSelectBlock(walkPos, 5000, OccultismConstants.Color.GREEN));
                }

            } else {
                //If deposit is fully blocked -> that is on the player.

                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);

                if (Occultism.DEBUG.debugAI) {
                    Networking.sendToTracking(entity, new MessageSelectBlock(depositPos, 50000, OccultismConstants.Color.RED));
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
