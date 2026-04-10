package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ExtendedBehaviour<E extends LivingEntity> extends Behavior<E> {

    protected ExtendedBehaviour(List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements) {
        this(memoryRequirements, 1);
    }

    protected ExtendedBehaviour(List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements, int duration) {
        super(toMemoryMap(memoryRequirements), duration);
    }

    protected ExtendedBehaviour(List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements, int minDuration, int maxDuration) {
        super(toMemoryMap(memoryRequirements), minDuration, maxDuration);
    }

    private static Map<MemoryModuleType<?>, MemoryStatus> toMemoryMap(List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements) {
        Map<MemoryModuleType<?>, MemoryStatus> map = new LinkedHashMap<>();

        for (Pair<MemoryModuleType<?>, MemoryStatus> requirement : memoryRequirements) {
            map.put(requirement.getFirst(), requirement.getSecond());
        }

        return Map.copyOf(map);
    }

    @Override
    protected final void start(ServerLevel level, E entity, long gameTime) {
        this.start(entity);
    }

    protected void start(E entity) {
    }

    @Override
    protected final void tick(ServerLevel level, E entity, long gameTime) {
        this.tick(entity);
    }

    protected void tick(E entity) {
    }

    @Override
    protected final void stop(ServerLevel level, E entity, long gameTime) {
        this.stop(entity);
    }

    protected void stop(E entity) {
    }

    @Override
    protected final boolean canStillUse(ServerLevel level, E entity, long gameTime) {
        return this.shouldKeepRunning(entity);
    }

    protected boolean shouldKeepRunning(E entity) {
        return false;
    }
}
