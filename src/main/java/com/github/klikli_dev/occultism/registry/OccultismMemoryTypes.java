package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.data.WorkAreaSize;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class OccultismMemoryTypes {

    public static DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, Occultism.MODID);
    public static final Supplier<MemoryModuleType<BlockPos>> NEAREST_TREE = MEMORY_MODULE_TYPES.register("nearest_tree", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<BlockPos>> LAST_FELLED_TREE = MEMORY_MODULE_TYPES.register("last_felled_tree", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<Boolean>> NO_TREE_IN_WORK_AREA = MEMORY_MODULE_TYPES.register("no_tree_in_work_area", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Set<BlockPos>>> NON_TREE_LOGS = MEMORY_MODULE_TYPES.register("non_tree_logs", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<BlockPos>> WORK_AREA_CENTER = MEMORY_MODULE_TYPES.register("work_area_center", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<Integer>> WORK_AREA_SIZE = MEMORY_MODULE_TYPES.register("work_area_size", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<Boolean>> WALK_TARGET_UNREACHABLE = MEMORY_MODULE_TYPES.register("walk_target_unreachable", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<Set<BlockPos>>> UNREACHABLE_WALK_TARGETS = MEMORY_MODULE_TYPES.register("unreachable_walk_targets", () -> new MemoryModuleType<>(Optional.empty()));

    public static final Supplier<MemoryModuleType<Set<BlockPos>>> UNREACHABLE_TREES = MEMORY_MODULE_TYPES.register("unreachable_trees", () -> new MemoryModuleType<>(Optional.empty()));
}
