package com.github.klikli_dev.occultism.common.entity.ai.sensor;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.registry.OccultismSensors;
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
    public static final int DEFAULT_SCAN_RATE_TICKS = 20 * 1;
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
        var workAreaCenter = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_CENTER.get());
        var workAreaSize = BrainUtils.getMemory(entity, OccultismMemoryTypes.WORK_AREA_SIZE.get());

        var aabb = new AABB(workAreaCenter.offset(-workAreaSize / 2, -workAreaSize / 2, -workAreaSize / 2),
                workAreaCenter.offset(workAreaSize / 2, workAreaSize / 2, workAreaSize / 2));

        BrainUtils.setMemory(entity, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, EntityRetrievalUtil.getNearestEntity(level,
                aabb, entity.position(), (obj) -> {
                    if (obj instanceof ItemEntity item) {
                        return this.predicate().test(item, entity);
                    }
                    return false;
                }));
    }

}