package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.StorageUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import var;
import java.util.List;

public class ReplantSaplingBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double REPLANT_RANGE_SQUARE = Math.pow(2.5, 2);

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.LAST_FELLED_TREE.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var treePos = BrainUtils.getMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());
        var dist = entity.distanceToSqr(Vec3.atCenterOf(treePos));
        return StorageUtil.getFirstMatchingSlot(entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new), ItemTags.SAPLINGS) != -1
                && dist <= ReplantSaplingBehaviour.REPLANT_RANGE_SQUARE;
    }

    protected void start(E entity) {
        var lastFelledTree = BrainUtils.getMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());

        if (entity.level().isEmptyBlock(lastFelledTree)) {
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(lastFelledTree));

            var handler = entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
            ItemStack sapling = handler.getStackInSlot(StorageUtil.getFirstMatchingSlot(handler, ItemTags.SAPLINGS));
            if (sapling.getItem() instanceof BlockItem saplingBlockItem) {
                entity.level().setBlockAndUpdate(lastFelledTree, saplingBlockItem.getBlock().defaultBlockState());
                sapling.shrink(1);
            }
        }

        BrainUtils.clearMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
