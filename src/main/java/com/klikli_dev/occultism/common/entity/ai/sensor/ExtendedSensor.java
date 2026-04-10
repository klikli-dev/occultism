package com.klikli_dev.occultism.common.entity.ai.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.List;
import java.util.Set;

public abstract class ExtendedSensor<E extends LivingEntity> extends Sensor<E> {

    protected ExtendedSensor() {
        super();
    }

    protected ExtendedSensor(int scanRate) {
        super(scanRate);
    }

    public abstract List<MemoryModuleType<?>> memoriesUsed();

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.copyOf(this.memoriesUsed());
    }
}
