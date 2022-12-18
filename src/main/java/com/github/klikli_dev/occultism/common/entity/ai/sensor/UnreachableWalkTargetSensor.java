package com.github.klikli_dev.occultism.common.entity.ai.sensor;

import com.github.klikli_dev.occultism.registry.OccultismMemoryTypes;
import com.github.klikli_dev.occultism.registry.OccultismSensors;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class UnreachableWalkTargetSensor<E extends LivingEntity> extends ExtendedSensor<E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.WALK_TARGET,
            OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get()
    );
    private long lastUnpathableTime = 0L;

    public UnreachableWalkTargetSensor() {
    }

    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    public SensorType<? extends ExtendedSensor<?>> type() {
        return OccultismSensors.UNREACHABLE_WALK_TARGET.get();
    }

    protected void doTick(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();

        var walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);
        if (walkTarget == null) {
            this.resetState(brain);
        } else {
            Long unpathableTime = BrainUtils.getMemory(brain, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            if (unpathableTime == null) {
                this.resetState(brain);
            } else {
                if (this.lastUnpathableTime == 0L) {
                    this.lastUnpathableTime = unpathableTime;
                } else if (this.lastUnpathableTime == unpathableTime) {
                    BrainUtils.clearMemory(brain, OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get());
                } else if (this.lastUnpathableTime < unpathableTime) {
                    this.lastUnpathableTime = unpathableTime;
                    BrainUtils.setMemory(brain, OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get(), walkTarget.getTarget().currentBlockPosition().getY() > entity.getEyeY());
                }

            }
        }
    }

    private void resetState(Brain<?> brain) {
        if (this.lastUnpathableTime > 0L) {
            BrainUtils.clearMemory(brain, OccultismMemoryTypes.WALK_TARGET_UNREACHABLE.get());
        }

        this.lastUnpathableTime = 0L;
    }
}
