package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import java.util.List;

public class PickupItemBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double PICKUP_XZ_RANGE_SQUARE = 2.5;
    public static final double PICKUP_Y_RANGE = 16;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT), //we only pick up, if we can deposit
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        var jobItem = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return Math3DUtil.withinAxisDistances(entity.position(), jobItem.position(),
                PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE,
                PickupItemBehaviour.PICKUP_Y_RANGE,
                PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE)
                //also check if inserting would take anything from the entity stack -> means we have free slots
                && ItemHandlerHelper.insertItemStacked(
                entity.inventory.orElseThrow(ItemHandlerMissingException::new), jobItem.getItem(), true).getCount() <
                jobItem.getItem().getCount();
    }

    protected void start(E entity) {
        var jobItem = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);

        BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(jobItem, false));
        ItemStack duplicate = jobItem.getItem().copy();
        ItemStackHandler handler = entity.inventory.orElseThrow(ItemHandlerMissingException::new);
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
