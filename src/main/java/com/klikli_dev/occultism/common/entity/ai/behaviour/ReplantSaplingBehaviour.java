package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

public class ReplantSaplingBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double REPLANT_RANGE_SQUARE = Math.pow(3.5, 2);

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(OccultismMemoryTypes.LAST_FELLED_TREE.get(), MemoryStatus.VALUE_PRESENT)
    );

    public ReplantSaplingBehaviour() {
        super(MEMORY_REQUIREMENTS);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var treePos = BrainUtil.getMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get()).getFirst();
        var dist = entity.distanceToSqr(Vec3.atCenterOf(treePos));
        return ItemTransferUtil.getFirstMatchingSlot(entity.inventory, ItemTags.SAPLINGS) != -1
                && dist <= ReplantSaplingBehaviour.REPLANT_RANGE_SQUARE;
    }

    protected void start(E entity) {
        var lastFelledTreeList = BrainUtil.getMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());
        var lastFelledTree = lastFelledTreeList.getFirst();

        if (entity.level().isEmptyBlock(lastFelledTree)) {
            BrainUtil.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(lastFelledTree));

            if (entity.level().getBlockState(lastFelledTree.below()).is(BlockTags.DIRT)) {
                var handler = entity.inventory;
                int slot = ItemTransferUtil.getFirstMatchingSlot(handler, ItemTags.SAPLINGS);
                if (slot != -1) {
                    ItemStack sapling = handler.getResource(slot).toStack();
                    if (sapling.getItem() instanceof BlockItem saplingBlockItem) {
                        entity.level().setBlockAndUpdate(lastFelledTree, saplingBlockItem.getBlock().defaultBlockState());
                        sapling.shrink(1);
                        try (var tx = Transaction.openRoot()) {
                            handler.set(slot, ItemResource.of(sapling), sapling.getCount());
                            tx.commit();
                        }
                    }
                }
            }
        }
        lastFelledTreeList.removeFirst();
        if (lastFelledTreeList.isEmpty()) {
            BrainUtil.clearMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get());
        } else {
            BrainUtil.setMemory(entity, OccultismMemoryTypes.LAST_FELLED_TREE.get(), lastFelledTreeList);
        }
    }
}
