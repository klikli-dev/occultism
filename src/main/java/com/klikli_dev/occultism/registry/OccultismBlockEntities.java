/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class OccultismBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Occultism.MODID);

    public static final Supplier<BlockEntityType<StorageControllerBlockEntity>> STORAGE_CONTROLLER = BLOCK_ENTITIES.register(
            "storage_controller", () -> new BlockEntityType<>(StorageControllerBlockEntity::new,
                    Set.of(OccultismBlocks.STORAGE_CONTROLLER.get(), OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                            OccultismBlocks.STORAGE_CONTROLLER_DARK.get(), OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get())));

    public static final Supplier<BlockEntityType<StableWormholeBlockEntity>> STABLE_WORMHOLE = BLOCK_ENTITIES.register(
            "stable_wormhole", () -> new BlockEntityType<>(StableWormholeBlockEntity::new,
                    Set.of(OccultismBlocks.STABLE_WORMHOLE.get(), OccultismBlocks.STABLE_WORMHOLE_DARK.get())));

    public static final Supplier<BlockEntityType<SacrificialBowlBlockEntity>> SACRIFICIAL_BOWL = BLOCK_ENTITIES.register(
            "sacrificial_bowl", () -> new BlockEntityType<>(SacrificialBowlBlockEntity::new,
                    Set.of(OccultismBlocks.SACRIFICIAL_BOWL.get(), OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(), OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get(),
                            OccultismBlocks.DARK_SACRIFICIAL_BOWL.get(), OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get(), OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get())));

    public static final Supplier<BlockEntityType<RitualCatcherBlockEntity>> RITUAL_CATCHER = BLOCK_ENTITIES.register(
            "ritual_catcher", () -> new BlockEntityType<>(RitualCatcherBlockEntity::new,
                    Set.of(OccultismBlocks.RITUAL_CATCHER.get(), OccultismBlocks.RITUAL_CATCHER_DARK.get())));

    public static final Supplier<BlockEntityType<EntityWormholeBlockEntity>> ENTITY_WORMHOLE = BLOCK_ENTITIES.register(
            "entity_wormhole", () -> new BlockEntityType<>(EntityWormholeBlockEntity::new,
                    Set.of(OccultismBlocks.ENTITY_WORMHOLE.get(), OccultismBlocks.ENTITY_WORMHOLE_DARK.get())));

    public static final Supplier<BlockEntityType<GoldenSacrificialBowlBlockEntity>> GOLDEN_SACRIFICIAL_BOWL =
            BLOCK_ENTITIES.register(
                    "golden_sacrificial_bowl", () -> new BlockEntityType<>(GoldenSacrificialBowlBlockEntity::new,
                            Set.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get(), OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get(),
                                    OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get(), OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get(),
                                    OccultismBlocks.ELDRITCH_CHALICE.get(), OccultismBlocks.CELESTIAL_CHALICE.get())));

    public static final Supplier<BlockEntityType<DimensionalMineshaftBlockEntity>> DIMENSIONAL_MINESHAFT =
            BLOCK_ENTITIES.register(
                    "dimensional_mineshaft", () -> new BlockEntityType<>(DimensionalMineshaftBlockEntity::new,
                            Set.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get())));

    public static final Supplier<BlockEntityType<DimensionalBattlefieldBlockEntity>> DIMENSIONAL_BATTLEFIELD =
            BLOCK_ENTITIES.register(
                    "dimensional_battlefield", () -> new BlockEntityType<>(DimensionalBattlefieldBlockEntity::new,
                            Set.of(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get())));

    public static final Supplier<BlockEntityType<OtherSignBlockEntity>> OTHERPLANKS_SIGN =
            BLOCK_ENTITIES.register("otheplanks_sign", () ->
                    new BlockEntityType<>(OtherSignBlockEntity::new,
                            Set.of(OccultismBlocks.OTHERPLANKS_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_SIGN.get())));

    public static final Supplier<BlockEntityType<OtherHangingSignBlockEntity>> OTHERPLANKS_HANGING_SIGN =
            BLOCK_ENTITIES.register("otherplanks_hanging_sign", () ->
                    new BlockEntityType<>(OtherHangingSignBlockEntity::new,
                            Set.of(OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get())));
}
