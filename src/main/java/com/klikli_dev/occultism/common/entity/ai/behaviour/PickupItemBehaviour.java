package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PickupItemBehaviour<E extends SpiritEntity> extends ExtendedBehaviour<E> {
    public static final double PICKUP_XZ_RANGE_SQUARE = 3.5;
    public static final double PICKUP_Y_RANGE = 16;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT),
            Pair.of(OccultismMemoryTypes.DEPOSIT_POSITION.get(), MemoryStatus.VALUE_PRESENT), //we only pick up, if we can deposit
            Pair.of(OccultismMemoryTypes.DEPOSIT_FACING.get(), MemoryStatus.VALUE_PRESENT)
    );

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull E entity) {
        var jobItem = BrainUtil.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return Math3DUtil.withinAxisDistances(entity.position(), jobItem.position(),
                PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE,
                PickupItemBehaviour.PICKUP_Y_RANGE,
                PickupItemBehaviour.PICKUP_XZ_RANGE_SQUARE)
                //also check if inserting would take anything from the entity stack -> means we have free slots
                && ItemTransferUtil.insertItemStacked(
                entity.inventory, jobItem.getItem(), true).getCount() <
                jobItem.getItem().getCount();
    }

    protected void start(E entity) {
        var jobItem = BrainUtil.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);

        BrainUtil.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(jobItem, false));
        ItemStack duplicate = jobItem.getItem().copy();
        var handler = entity.inventory;
        if (ItemTransferUtil.insertItemStacked(handler, duplicate, true).getCount() < duplicate.getCount()) {
            ItemStack remaining = ItemTransferUtil.insertItemStacked(handler, duplicate, false);
            jobItem.getItem().setCount(remaining.getCount());
        }
        for (ItemEntity e : entity.level().getEntitiesOfClass(ItemEntity.class, jobItem.getBoundingBox().inflate(3), Entity::isAlive)) {
            if (ItemTransferUtil.insertItemStacked(handler, e.getItem().copy(), true).getCount() <= 64) {
                ItemStack remains = ItemTransferUtil.insertItemStacked(handler, e.getItem().copy(), false);
                e.getItem().setCount(remains.getCount());
            }
        }

        BrainUtil.clearMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
