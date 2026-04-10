package com.klikli_dev.occultism.common.entity.ai.sensor;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.common.entity.ai.BrainUtil;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.List;

public class NearestJobItemSensor<E extends SpiritEntity> extends ExtendedSensor<E> {
    public static final int DEFAULT_SCAN_RATE_TICKS = 20;
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            OccultismMemoryTypes.WORK_AREA_CENTER.get(),
            OccultismMemoryTypes.WORK_AREA_SIZE.get()
    );
    private final BiPredicate<ItemEntity, E> predicate;

    public NearestJobItemSensor() {
        super(DEFAULT_SCAN_RATE_TICKS);

        this.predicate = (item, entity) -> {
            return entity.canPickupItem(item) && entity.hasLineOfSight(item);
        };
    }

    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    protected void doTick(ServerLevel level, E entity) {

        //exit if we already have a desired item, to avoid switching back and forth if we lose LoS during movement
        if (BrainUtil.hasMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)) {
            var nearestEntity = BrainUtil.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
            if (Occultism.DEBUG.debugAI) {
                Networking.sendToTracking(entity, new MessageSelectBlock(nearestEntity.blockPosition(), 5000, OccultismConstants.Color.GREEN));
            }
            return;
        }

        var workAreaCenter = BrainUtil.getMemory(entity, OccultismMemoryTypes.WORK_AREA_CENTER.get());
        var workAreaSize = BrainUtil.getMemory(entity, OccultismMemoryTypes.WORK_AREA_SIZE.get());

        if (workAreaCenter == null || workAreaSize == null) {
            BrainUtil.clearMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
            return;
        }

        if (Occultism.DEBUG.debugAI) {
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter, 5000, OccultismConstants.Color.BLUE));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(workAreaSize / 2, workAreaSize / 2, workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(-workAreaSize / 2, -workAreaSize / 2, -workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(workAreaSize / 2, workAreaSize / 2, -workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(-workAreaSize / 2, -workAreaSize / 2, workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(workAreaSize / 2, -workAreaSize / 2, workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(-workAreaSize / 2, workAreaSize / 2, -workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(-workAreaSize / 2, workAreaSize / 2, workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
            Networking.sendToTracking(entity, new MessageSelectBlock(workAreaCenter.offset(workAreaSize / 2, -workAreaSize / 2, -workAreaSize / 2), 5000, OccultismConstants.Color.CYAN));
        }

        var aabb = new AABB(workAreaCenter.getCenter().add(-workAreaSize / 2f, -workAreaSize / 2f, -workAreaSize / 2f),
                workAreaCenter.getCenter().add(workAreaSize / 2f, workAreaSize / 2f, workAreaSize / 2f));

        ItemEntity nearestEntity = level.getEntitiesOfClass(ItemEntity.class, aabb, item -> this.predicate.test(item, entity))
                .stream()
                .min(Comparator.comparingDouble(entity::distanceToSqr))
                .orElse(null);

        BrainUtil.setMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, nearestEntity);

        if (Occultism.DEBUG.debugAI && nearestEntity != null) {
            Networking.sendToTracking(entity, new MessageSelectBlock(nearestEntity.blockPosition(), 5000, OccultismConstants.Color.GREEN));
        }
    }

}
