package com.github.klikli_dev.occultism.common.entity.ai.behaviour;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class PickupJobItemBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double PICKUP_RANGE_SQUARE = Math.pow(2.5, 2);

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var jobItem = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        var dist = entity.distanceToSqr(jobItem);
        return dist <= PickupJobItemBehaviour.PICKUP_RANGE_SQUARE
                //also check if inserting would take anything from the entity stack -> means we have free slots
                && ItemHandlerHelper.insertItemStacked(
                entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new), jobItem.getItem(), true).getCount() <
                jobItem.getItem().getCount();
    }

    protected void start(E entity) {
        var jobItem = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);

        ItemStack duplicate = jobItem.getItem().copy();
        ItemStackHandler handler = entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        if (ItemHandlerHelper.insertItemStacked(handler, duplicate, true).getCount() < duplicate.getCount()) {
            ItemStack remaining = ItemHandlerHelper.insertItemStacked(handler, duplicate, false);
            jobItem.getItem().setCount(remaining.getCount());
        }

        BrainUtils.clearMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
