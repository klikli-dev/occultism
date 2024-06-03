package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SortType>> SORT_TYPE = DATA_COMPONENTS.registerComponentType("SORT_TYPE", builder -> builder
            .persistent(SortType.CODEC)
            .networkSynchronized(SortType.STREAM_CODEC)
            .cacheEncoding()
    );

}
