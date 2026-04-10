package com.klikli_dev.occultism.common.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public final class BrainUtil {

    private BrainUtil() {
    }

    public static <U> void clearMemory(Brain<?> brain, MemoryModuleType<U> memoryType) {
        brain.eraseMemory(memoryType);
    }

    public static <E extends LivingEntity, U> void clearMemory(E entity, MemoryModuleType<U> memoryType) {
        clearMemory(entity.getBrain(), memoryType);
    }

    @Nullable
    public static <U> U getMemory(Brain<?> brain, MemoryModuleType<U> memoryType) {
        return brain.getMemory(memoryType).orElse(null);
    }

    @Nullable
    public static <E extends LivingEntity, U> U getMemory(E entity, MemoryModuleType<U> memoryType) {
        return getMemory(entity.getBrain(), memoryType);
    }

    public static boolean hasMemory(Brain<?> brain, MemoryModuleType<?> memoryType) {
        return brain.hasMemoryValue(memoryType);
    }

    public static <E extends LivingEntity> boolean hasMemory(E entity, MemoryModuleType<?> memoryType) {
        return hasMemory(entity.getBrain(), memoryType);
    }

    public static <U> U memoryOrDefault(Brain<?> brain, MemoryModuleType<U> memoryType, Supplier<U> defaultSupplier) {
        return brain.getMemory(memoryType).orElseGet(defaultSupplier);
    }

    public static <E extends LivingEntity, U> U memoryOrDefault(E entity, MemoryModuleType<U> memoryType, Supplier<U> defaultSupplier) {
        return memoryOrDefault(entity.getBrain(), memoryType, defaultSupplier);
    }

    public static <U> void setForgettableMemory(Brain<?> brain, MemoryModuleType<U> memoryType, U value, long expiryTicks) {
        brain.setMemoryWithExpiry(memoryType, value, expiryTicks);
    }

    public static <E extends LivingEntity, U> void setForgettableMemory(E entity, MemoryModuleType<U> memoryType, U value, long expiryTicks) {
        setForgettableMemory(entity.getBrain(), memoryType, value, expiryTicks);
    }

    public static <U> void setMemory(Brain<?> brain, MemoryModuleType<U> memoryType, @Nullable U value) {
        brain.setMemory(memoryType, value);
    }

    public static <E extends LivingEntity, U> void setMemory(E entity, MemoryModuleType<U> memoryType, @Nullable U value) {
        setMemory(entity.getBrain(), memoryType, value);
    }
}
