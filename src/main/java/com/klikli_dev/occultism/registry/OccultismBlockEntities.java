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

import java.util.function.Supplier;

public class OccultismBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Occultism.MODID);

    public static final Supplier<BlockEntityType<StorageControllerBlockEntity>> STORAGE_CONTROLLER = BLOCK_ENTITIES.register(
            "storage_controller", () -> BlockEntityType.Builder.of(StorageControllerBlockEntity::new,
                    OccultismBlocks.STORAGE_CONTROLLER.get(), OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get()).build(null));

    public static final Supplier<BlockEntityType<StableWormholeBlockEntity>> STABLE_WORMHOLE = BLOCK_ENTITIES.register(
            "stable_wormhole", () -> BlockEntityType.Builder.of(StableWormholeBlockEntity::new,
                    OccultismBlocks.STABLE_WORMHOLE.get()).build(null));

    public static final Supplier<BlockEntityType<SacrificialBowlBlockEntity>> SACRIFICIAL_BOWL = BLOCK_ENTITIES.register(
            "sacrificial_bowl", () -> BlockEntityType.Builder.of(SacrificialBowlBlockEntity::new,
                    OccultismBlocks.SACRIFICIAL_BOWL.get(), OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(), OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get()).build(null));

    public static final Supplier<BlockEntityType<GoldenSacrificialBowlBlockEntity>> GOLDEN_SACRIFICIAL_BOWL =
            BLOCK_ENTITIES.register(
                    "golden_sacrificial_bowl", () -> BlockEntityType.Builder.of(GoldenSacrificialBowlBlockEntity::new,
                            OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get(), OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get(), OccultismBlocks.ELDRITCH_CHALICE.get()).build(null));

    public static final Supplier<BlockEntityType<DimensionalMineshaftBlockEntity>> DIMENSIONAL_MINESHAFT =
            BLOCK_ENTITIES.register(
                    "dimensional_mineshaft", () -> BlockEntityType.Builder.of(DimensionalMineshaftBlockEntity::new,
                            OccultismBlocks.DIMENSIONAL_MINESHAFT.get()).build(null));

    public static final Supplier<BlockEntityType<OtherSignBlockEntity>> OTHERPLANKS_SIGN =
            BLOCK_ENTITIES.register("otheplanks_sign", () ->
                    BlockEntityType.Builder.of(OtherSignBlockEntity::new,
                            OccultismBlocks.OTHERPLANKS_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_SIGN.get()).build(null));

    public static final Supplier<BlockEntityType<OtherHangingSignBlockEntity>> OTHERPLANKS_HANGING_SIGN =
            BLOCK_ENTITIES.register("otherplanks_hanging_sign", () ->
                    BlockEntityType.Builder.of(OtherHangingSignBlockEntity::new,
                            OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(), OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get()).build(null));
}
