package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Occultism.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_MINING_TIME = DATA_COMPONENTS.registerComponentType("max_mining_time", builder -> builder
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROLLS_PER_OPERATION = DATA_COMPONENTS.registerComponentType("rolls_per_operation", builder -> builder
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SortDirection>> SORT_DIRECTION = DATA_COMPONENTS.registerComponentType("sort_direction", builder -> builder
            .persistent(SortDirection.CODEC)
            .networkSynchronized(SortDirection.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SortType>> SORT_TYPE = DATA_COMPONENTS.registerComponentType("sort_type", builder -> builder
            .persistent(SortType.CODEC)
            .networkSynchronized(SortType.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> CRAFTING_MATRIX = DATA_COMPONENTS.registerComponentType("crafting_matrix", builder -> builder
            .persistent(CustomData.CODEC)
            .networkSynchronized(CustomData.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> ORDER_STACK = DATA_COMPONENTS.registerComponentType("order_stack", builder -> builder
            .persistent(CustomData.CODEC)
            .networkSynchronized(CustomData.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GlobalBlockPos>> LINKED_STORAGE_CONTROLLER = DATA_COMPONENTS.registerComponentType("linked_storage_controller", builder -> builder
            .persistent(GlobalBlockPos.CODEC)
            .networkSynchronized(GlobalBlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_SLOTS = DATA_COMPONENTS.registerComponentType("max_slots", builder -> builder
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .cacheEncoding()
    );

}
