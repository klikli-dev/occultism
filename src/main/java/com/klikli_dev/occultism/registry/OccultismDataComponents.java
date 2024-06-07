package com.klikli_dev.occultism.registry;

import com.klikli_dev.modonomicon.util.Codecs;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.*;
import com.klikli_dev.occultism.util.OccultismExtraCodecs;
import com.klikli_dev.occultism.util.OccultismExtraStreamCodecs;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;

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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> OCCUPIED = DATA_COMPONENTS.registerComponentType("occupied", builder -> builder
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> FAMILIAR_DATA = DATA_COMPONENTS.registerComponentType("familiar_data", builder -> builder
            .persistent(CustomData.CODEC)
            .networkSynchronized(CustomData.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> FAMILIAR_TYPE = DATA_COMPONENTS.registerComponentType("FAMILIAR_TYPE", builder -> builder
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> DIVINATION_DISTANCE = DATA_COMPONENTS.registerComponentType("divination_distance", builder -> builder
            .persistent(Codec.FLOAT)
            .networkSynchronized(ByteBufCodecs.FLOAT)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> DIVINATION_POS = DATA_COMPONENTS.registerComponentType("divination_pos", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Block>>> DIVINATION_LINKED_BLOCK = DATA_COMPONENTS.registerComponentType("divination_linked_block", builder -> builder
            .persistent(BuiltInRegistries.BLOCK.holderByNameCodec())
            .networkSynchronized(ByteBufCodecs.holderRegistry(Registries.BLOCK))
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> SPIRIT_NAME = DATA_COMPONENTS.registerComponentType("spirit_name", builder -> builder
            .persistent(Codec.STRING)
            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ITEM_MODE = DATA_COMPONENTS.registerComponentType("item_mode", builder -> builder
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MachineReference>> MANAGED_MACHINE = DATA_COMPONENTS.registerComponentType("managed_machine", builder -> builder
            .persistent(MachineReference.CODEC)
            .networkSynchronized(MachineReference.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> DEPOSIT_POSITION = DATA_COMPONENTS.registerComponentType("deposit_position", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> DEPOSIT_ENTITY_UUID = DATA_COMPONENTS.registerComponentType("deposit_entity_uuid", builder -> builder
            .persistent(OccultismExtraCodecs.UUID)
            .networkSynchronized(OccultismExtraStreamCodecs.UUID)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DEPOSIT_ENTITY_NAME = DATA_COMPONENTS.registerComponentType("deposit_entity_name", builder -> builder
            .persistent(Codec.STRING)
            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Direction>> DEPOSIT_FACING = DATA_COMPONENTS.registerComponentType("deposit_facing", builder -> builder
            .persistent(Direction.CODEC)
            .networkSynchronized(Direction.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> DEPOSIT_POS = DATA_COMPONENTS.registerComponentType("deposit_pos", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Direction>> EXTRACT_FACING = DATA_COMPONENTS.registerComponentType("extract_facing", builder -> builder
            .persistent(Direction.CODEC)
            .networkSynchronized(Direction.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> EXTRACT_POS = DATA_COMPONENTS.registerComponentType("extract_pos", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> WORK_AREA_POS = DATA_COMPONENTS.registerComponentType("work_area_pos", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WorkAreaSize>> WORK_AREA_SIZE = DATA_COMPONENTS.registerComponentType("work_area_size", builder -> builder
            .persistent(WorkAreaSize.CODEC)
            .networkSynchronized(WorkAreaSize.STREAM_CODEC)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> SPIRIT_ENTITY_UUID = DATA_COMPONENTS.registerComponentType("spirit_entity_uuid", builder -> builder
            .persistent(OccultismExtraCodecs.UUID)
            .networkSynchronized(OccultismExtraStreamCodecs.UUID)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SPIRIT_DEAD = DATA_COMPONENTS.registerComponentType("spirit_dead", builder -> builder
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .cacheEncoding()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> SPIRIT_ENTITY_DATA = DATA_COMPONENTS.registerComponentType("spirit_entity_data", builder -> builder
            .persistent(CustomData.CODEC)
            .networkSynchronized(CustomData.STREAM_CODEC)
            .cacheEncoding()
    );
}
