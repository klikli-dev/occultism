package com.klikli_dev.occultism.common.entity.ai.sensor;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.OccultismConstants;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.messages.MessageSelectBlock;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.klikli_dev.occultism.registry.OccultismSensors;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import java.util.List;

public class NearestJobItemSensor<E extends SpiritEntity> extends PredicateSensor<ItemEntity, E> {
    public static final int DEFAULT_SCAN_RATE_TICKS = 20;
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);

    public NearestJobItemSensor() {
        super((item, entity) -> {
            return entity.canPickupItem(item) && entity.hasLineOfSight(item);
        });

        this.setScanRate((entity) -> DEFAULT_SCAN_RATE_TICKS);
    }

    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    public SensorType<? extends ExtendedSensor<?>> type() {
        return OccultismSensors.NEAREST_JOB_ITEM.get();
    }

    protected void doTick(ServerLevel level, E entity) {

        //exit if we already have a desired item, to avoid switching back and forth if we lose LoS during movement
        if (BrainUtils.hasMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)) {
            var nearestEntity = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
            if (Occultism.DEBUG.debugAI) {
                Networking.sendToTracking(entity, new MessageSelectBlock(nearestEntity.blockPosition(), 5000, OccultismConstants.Color.GREEN));
            }
            return;
        }

        var workAreaCenter = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_CENTER.get());
        var workAreaSize = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_SIZE.get());

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

        ItemEntity nearestEntity = EntityRetrievalUtil.getNearestEntity(level,
                aabb, entity.position(), (obj) -> {
                    if (obj instanceof ItemEntity item) {
                        return this.predicate().test(item, entity);
                    }
                    return false;
                });

        BrainUtils.setMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, nearestEntity);

        if (Occultism.DEBUG.debugAI && nearestEntity != null) {
            Networking.sendToTracking(entity, new MessageSelectBlock(nearestEntity.blockPosition(), 5000, OccultismConstants.Color.GREEN));
        }
    }

}