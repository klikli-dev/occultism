/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.datagen.model;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.itemproperties.OtherworldBlockItemPropertyGetter;
import com.klikli_dev.occultism.client.itemproperties.StableWormholeBlockItemPropertyGetter;
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.block.EntityWormholeBlock;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.renderer.item.ConditionalItemModel.Unbaked;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SideChainPart;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class OccultismBlockModelSubProvider {

    public Stream<Block> getKnownBlocks() {
        return Stream.of(
                Stream.of(
                        OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get(),
                        OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get(),
                        OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get(),
                        OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get(),
                        OccultismBlocks.IESNIUM_BLOCK.get(),
                        OccultismBlocks.IESNIUM_ORE.get(),
                        OccultismBlocks.OTHERSTONE.get(),
                        OccultismBlocks.OTHERSTONE_BRICKS.get(),
                        OccultismBlocks.OTHERROCK.get(),
                        OccultismBlocks.OTHERROCK_BRICKS.get(),
                        OccultismBlocks.OTHERCOBBLESTONE.get(),
                        OccultismBlocks.OTHERCOBBLEROCK.get(),
                        OccultismBlocks.POLISHED_OTHERSTONE.get(),
                        OccultismBlocks.POLISHED_OTHERROCK.get(),
                        OccultismBlocks.OTHERPLANKS.get(),
                        OccultismBlocks.RAW_IESNIUM_BLOCK.get(),
                        OccultismBlocks.SILVER_ORE.get(),
                        OccultismBlocks.SILVER_ORE_DEEPSLATE.get(),
                        OccultismBlocks.TALLOW_BLOCK.get()
                ),
                Stream.of(
                        OccultismBlocks.RAW_SILVER_BLOCK.get(),
                        OccultismBlocks.SILVER_BLOCK.get(),
                        OccultismBlocks.SILVER_CHISELED_BLOCK.get(),
                        OccultismBlocks.SILVER_GRATE_BLOCK.get(),
                        OccultismBlocks.SILVER_CUT_BLOCK.get(),
                        OccultismBlocks.SILVER_CUT_STAIRS.get(),
                        OccultismBlocks.SILVER_CUT_SLAB.get(),
                        OccultismBlocks.SILVER_BARS_BLOCK.get(),
                        OccultismBlocks.SILVER_CHAIN_BLOCK.get(),
                        OccultismBlocks.SILVER_DOOR.get(),
                        OccultismBlocks.SILVER_TRAPDOOR.get(),
                        OccultismBlocks.SILVER_BULB.get()
                ),
                Stream.of(
                        OccultismBlocks.CHALK_GLYPH_WHITE.get(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get(),
                        OccultismBlocks.CHALK_GLYPH_GRAY.get(),
                        OccultismBlocks.CHALK_GLYPH_BLACK.get(),
                        OccultismBlocks.CHALK_GLYPH_BROWN.get(),
                        OccultismBlocks.CHALK_GLYPH_RED.get(),
                        OccultismBlocks.CHALK_GLYPH_ORANGE.get(),
                        OccultismBlocks.CHALK_GLYPH_YELLOW.get(),
                        OccultismBlocks.CHALK_GLYPH_LIME.get(),
                        OccultismBlocks.CHALK_GLYPH_GREEN.get(),
                        OccultismBlocks.CHALK_GLYPH_CYAN.get(),
                        OccultismBlocks.CHALK_GLYPH_BLUE.get(),
                        OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get(),
                        OccultismBlocks.CHALK_GLYPH_PINK.get(),
                        OccultismBlocks.CHALK_GLYPH_MAGENTA.get(),
                        OccultismBlocks.CHALK_GLYPH_PURPLE.get(),
                        OccultismBlocks.CHALK_GLYPH_RAINBOW.get(),
                        OccultismBlocks.CHALK_GLYPH_VOID.get()
                ),
                Stream.of(
                        OccultismBlocks.OTHERSTONE_STAIRS.get(),
                        OccultismBlocks.OTHERSTONE_SLAB.get(),
                        OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get(),
                        OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get(),
                        OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get(),
                        OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get(),
                        OccultismBlocks.OTHERROCK_STAIRS.get(),
                        OccultismBlocks.OTHERROCK_SLAB.get(),
                        OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get(),
                        OccultismBlocks.OTHERROCK_BRICKS_SLAB.get(),
                        OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get(),
                        OccultismBlocks.POLISHED_OTHERROCK_SLAB.get(),
                        OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get(),
                        OccultismBlocks.OTHERCOBBLESTONE_SLAB.get(),
                        OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get(),
                        OccultismBlocks.OTHERCOBBLEROCK_SLAB.get(),
                        OccultismBlocks.OTHERPLANKS_STAIRS.get(),
                        OccultismBlocks.OTHERPLANKS_SLAB.get()
                ),
                Stream.of(
                        OccultismBlocks.OTHERPLANKS_FENCE.get(),
                        OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(),
                        OccultismBlocks.OTHERPLANKS_DOOR.get(),
                        OccultismBlocks.OTHERPLANKS_TRAPDOOR.get(),
                        OccultismBlocks.OTHERPLANKS_BUTTON.get(),
                        OccultismBlocks.OTHERSTONE_BUTTON.get(),
                        OccultismBlocks.OTHERROCK_BUTTON.get(),
                        OccultismBlocks.OTHERSTONE_WALL.get(),
                        OccultismBlocks.OTHERCOBBLESTONE_WALL.get(),
                        OccultismBlocks.POLISHED_OTHERSTONE_WALL.get(),
                        OccultismBlocks.OTHERSTONE_BRICKS_WALL.get(),
                        OccultismBlocks.OTHERROCK_WALL.get(),
                        OccultismBlocks.OTHERCOBBLEROCK_WALL.get(),
                        OccultismBlocks.POLISHED_OTHERROCK_WALL.get(),
                        OccultismBlocks.OTHERROCK_BRICKS_WALL.get()
                ),
                Stream.of(
                        OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get(),
                        OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get(),
                        OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get()
                ),
                Stream.of(
                        OccultismBlocks.DARK_SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER0.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER1.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER2.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER3.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER4.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER5.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get(),
                        OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get(),
                        OccultismBlocks.ENTITY_WORMHOLE.get(),
                        OccultismBlocks.ENTITY_WORMHOLE_DARK.get(),
                        OccultismBlocks.STABLE_WORMHOLE.get(),
                        OccultismBlocks.STABLE_WORMHOLE_DARK.get(),
                        OccultismBlocks.RITUAL_CATCHER.get(),
                        OccultismBlocks.RITUAL_CATCHER_DARK.get()
                ),
                Stream.of(
                        OccultismBlocks.LARGE_CANDLE.get(),
                        OccultismBlocks.LARGE_CANDLE_WHITE.get(),
                        OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get(),
                        OccultismBlocks.LARGE_CANDLE_GRAY.get(),
                        OccultismBlocks.LARGE_CANDLE_BLACK.get(),
                        OccultismBlocks.LARGE_CANDLE_BROWN.get(),
                        OccultismBlocks.LARGE_CANDLE_RED.get(),
                        OccultismBlocks.LARGE_CANDLE_ORANGE.get(),
                        OccultismBlocks.LARGE_CANDLE_YELLOW.get(),
                        OccultismBlocks.LARGE_CANDLE_LIME.get(),
                        OccultismBlocks.LARGE_CANDLE_GREEN.get(),
                        OccultismBlocks.LARGE_CANDLE_CYAN.get(),
                        OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get(),
                        OccultismBlocks.LARGE_CANDLE_BLUE.get(),
                        OccultismBlocks.LARGE_CANDLE_PURPLE.get(),
                        OccultismBlocks.LARGE_CANDLE_MAGENTA.get(),
                        OccultismBlocks.LARGE_CANDLE_PINK.get(),
                        OccultismBlocks.STORAGE_CONTROLLER_BASE.get(),
                        OccultismBlocks.STORAGE_CONTROLLER.get(),
                        OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                        OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get(),
                        OccultismBlocks.STORAGE_CONTROLLER_DARK.get(),
                        OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get()
                )
        ).flatMap(stream -> stream);
    }

    private Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, path);
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private Identifier blockModel(Block block) {
        return this.modLoc("block/" + this.name(block));
    }

    private Identifier itemModel(Block block) {
        return this.modLoc("item/" + this.name(block));
    }

    public void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerSimpleBlocks(blockModels, itemModels);
        this.registerGlyphBlocks(blockModels, itemModels);
        this.registerStairsAndSlabs(blockModels, itemModels);
        this.registerFencesAndGates(blockModels, itemModels);
        this.registerPressurePlates(blockModels, itemModels);
        this.registerSpecialBlocks(blockModels, itemModels);
        this.registerCandleBlocks(blockModels);
        this.registerStorageControllerBlocks(blockModels, itemModels);
        this.registerManualBlockItemDefinitions(itemModels);
    }

    private void registerSimpleBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        Block[] cubeAll = {
                OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get(),
                OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.IESNIUM_BLOCK.get(),
                OccultismBlocks.IESNIUM_ORE.get(),
                OccultismBlocks.OTHERSTONE.get(),
                OccultismBlocks.OTHERSTONE_BRICKS.get(),
                OccultismBlocks.OTHERROCK.get(),
                OccultismBlocks.OTHERROCK_BRICKS.get(),
                OccultismBlocks.OTHERCOBBLESTONE.get(),
                OccultismBlocks.OTHERCOBBLEROCK.get(),
                OccultismBlocks.POLISHED_OTHERSTONE.get(),
                OccultismBlocks.POLISHED_OTHERROCK.get(),
                OccultismBlocks.OTHERPLANKS.get(),
                OccultismBlocks.SILVER_BLOCK.get(),
                OccultismBlocks.SILVER_CHISELED_BLOCK.get(),
                OccultismBlocks.SILVER_GRATE_BLOCK.get(),
                OccultismBlocks.SILVER_CUT_BLOCK.get(),
                OccultismBlocks.RAW_SILVER_BLOCK.get(),
                OccultismBlocks.RAW_IESNIUM_BLOCK.get(),
                OccultismBlocks.SILVER_ORE.get(),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get(),
                OccultismBlocks.TALLOW_BLOCK.get()
        };
        for (Block block : cubeAll) {
            blockModels.createTrivialCube(block);
            this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
        }
    }

    private void registerCandleBlocks(BlockModelGenerators blockModels) {
        Block[] candles = {
                OccultismBlocks.LARGE_CANDLE.get(),
                OccultismBlocks.LARGE_CANDLE_WHITE.get(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get(),
                OccultismBlocks.LARGE_CANDLE_GRAY.get(),
                OccultismBlocks.LARGE_CANDLE_BLACK.get(),
                OccultismBlocks.LARGE_CANDLE_BROWN.get(),
                OccultismBlocks.LARGE_CANDLE_RED.get(),
                OccultismBlocks.LARGE_CANDLE_ORANGE.get(),
                OccultismBlocks.LARGE_CANDLE_YELLOW.get(),
                OccultismBlocks.LARGE_CANDLE_LIME.get(),
                OccultismBlocks.LARGE_CANDLE_GREEN.get(),
                OccultismBlocks.LARGE_CANDLE_CYAN.get(),
                OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get(),
                OccultismBlocks.LARGE_CANDLE_BLUE.get(),
                OccultismBlocks.LARGE_CANDLE_PURPLE.get(),
                OccultismBlocks.LARGE_CANDLE_MAGENTA.get(),
                OccultismBlocks.LARGE_CANDLE_PINK.get()
        };
        for (Block block : candles) {
            this.registerSimpleModelBlock(blockModels, block);
        }
    }

    private void registerStorageControllerBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        Block[] blocks = {
                OccultismBlocks.STORAGE_CONTROLLER_BASE.get(),
                OccultismBlocks.STORAGE_CONTROLLER.get(),
                OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get(),
                OccultismBlocks.STORAGE_CONTROLLER_DARK.get(),
                OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get()
        };
        for (Block block : blocks) {
            this.registerSimpleModelBlock(blockModels, itemModels, block);
        }

        this.registerExistingItemModel(itemModels, OccultismBlocks.LARGE_CANDLE.asItem(), this.blockModel(OccultismBlocks.LARGE_CANDLE.get()));
    }

    private void registerManualBlockItemDefinitions(ItemModelGenerators itemModels) {
        Block[] blocksUsingBlockModels = {
                OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.CELESTIAL_CHALICE.get(),
                OccultismBlocks.ELDRITCH_CHALICE.get(),
                OccultismBlocks.DIMENSIONAL_MINESHAFT.get(),
                OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get(),
                OccultismBlocks.DIMENSIONAL_EXTRACTOR.get(),
                OccultismBlocks.OTHERGLASS_NATURAL.get(),
                OccultismBlocks.OTHERSTONE_PEDESTAL.get(),
                OccultismBlocks.OTHERROCK_PEDESTAL.get(),
                OccultismBlocks.OTHERWORLD_LEAVES.get(),
                OccultismBlocks.OTHERWORLD_LOG.get(),
                OccultismBlocks.OTHERWORLD_WOOD.get(),
                OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get(),
                OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get(),
                OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get(),
                OccultismBlocks.SPIRIT_GRINDSTONE.get()
        };
        for (Block block : blocksUsingBlockModels) {
            this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
        }

        Block[] blocksUsingItemModels = {
                OccultismBlocks.SPIRIT_FIRE.get(),
                OccultismBlocks.SPIRIT_TORCH.get(),
                OccultismBlocks.OTHERSTONE_NATURAL.get(),
                OccultismBlocks.OTHERROCK_NATURAL.get(),
                OccultismBlocks.OTHERFLOWER.get(),
                OccultismBlocks.OTHERFLOWER_NATURAL.get(),
                OccultismBlocks.OTHERWORLD_SAPLING.get(),
                OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(),
                OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get(),
                OccultismBlocks.OTHERWORLD_LOG_NATURAL.get(),
                OccultismBlocks.IESNIUM_ORE_NATURAL.get(),
                OccultismBlocks.IESNIUM_ANVIL.get()
        };
        for (Block block : blocksUsingItemModels) {
            this.registerExistingItemModel(itemModels, block.asItem(), this.itemModel(block));
        }

        this.registerConditionalOtherworldSapling(itemModels, OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get());
    }

    private void registerGlyphBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (int sign = 0; sign <= ChalkGlyphBlock.MAX_SIGN; sign++) {
            this.emitParentModel(
                    blockModels.modelOutput,
                    this.modLoc("block/chalk_glyph/" + sign),
                    this.modLoc("block/chalk_glyph/chalk_glyph"),
                    Map.of("texture", this.modLoc("block/chalk_glyph/" + sign))
            );
        }

        Block[] glyphBlocks = {
                OccultismBlocks.CHALK_GLYPH_WHITE.get(),
                OccultismBlocks.CHALK_GLYPH_LIGHT_GRAY.get(),
                OccultismBlocks.CHALK_GLYPH_GRAY.get(),
                OccultismBlocks.CHALK_GLYPH_BLACK.get(),
                OccultismBlocks.CHALK_GLYPH_BROWN.get(),
                OccultismBlocks.CHALK_GLYPH_RED.get(),
                OccultismBlocks.CHALK_GLYPH_ORANGE.get(),
                OccultismBlocks.CHALK_GLYPH_YELLOW.get(),
                OccultismBlocks.CHALK_GLYPH_LIME.get(),
                OccultismBlocks.CHALK_GLYPH_GREEN.get(),
                OccultismBlocks.CHALK_GLYPH_CYAN.get(),
                OccultismBlocks.CHALK_GLYPH_BLUE.get(),
                OccultismBlocks.CHALK_GLYPH_LIGHT_BLUE.get(),
                OccultismBlocks.CHALK_GLYPH_PINK.get(),
                OccultismBlocks.CHALK_GLYPH_MAGENTA.get(),
                OccultismBlocks.CHALK_GLYPH_PURPLE.get(),
                OccultismBlocks.CHALK_GLYPH_RAINBOW.get(),
                OccultismBlocks.CHALK_GLYPH_VOID.get(),
        };
        for (Block block : glyphBlocks) {
            this.registerGlyphBlock(blockModels, block);
        }
    }

    private void registerGlyphBlock(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, ChalkGlyphBlock.SIGN)
                                .generate((facing, sign) -> sign == 0 ?
                                        plainVariant(this.modLoc("block/chalk_glyph/preview")):
                                        plainVariant(this.modLoc("block/chalk_glyph/" + sign))
                                        .with(switch (facing) {
                                            case SOUTH -> BlockModelGenerators.NOP;
                                            case WEST -> Y_ROT_90;
                                            case NORTH -> BlockModelGenerators.Y_ROT_180;
                                            case EAST -> BlockModelGenerators.Y_ROT_270;
                                            default -> BlockModelGenerators.NOP;
                                        })))
        );
    }

    private void registerStairsAndSlabs(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERSTONE_STAIRS.get(), OccultismBlocks.OTHERSTONE.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERSTONE_SLAB.get(), OccultismBlocks.OTHERSTONE.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get(), OccultismBlocks.OTHERSTONE_BRICKS.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get(), OccultismBlocks.OTHERSTONE_BRICKS.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get(), OccultismBlocks.POLISHED_OTHERSTONE.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get(), OccultismBlocks.POLISHED_OTHERSTONE.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERROCK_STAIRS.get(), OccultismBlocks.OTHERROCK.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERROCK_SLAB.get(), OccultismBlocks.OTHERROCK.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get(), OccultismBlocks.OTHERROCK_BRICKS.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERROCK_BRICKS_SLAB.get(), OccultismBlocks.OTHERROCK_BRICKS.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get(), OccultismBlocks.POLISHED_OTHERROCK.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERROCK_SLAB.get(), OccultismBlocks.POLISHED_OTHERROCK.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get(), OccultismBlocks.OTHERCOBBLESTONE.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERCOBBLESTONE_SLAB.get(), OccultismBlocks.OTHERCOBBLESTONE.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get(), OccultismBlocks.OTHERCOBBLEROCK.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERCOBBLEROCK_SLAB.get(), OccultismBlocks.OTHERCOBBLEROCK.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_STAIRS.get(), OccultismBlocks.OTHERPLANKS.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_SLAB.get(), OccultismBlocks.OTHERPLANKS.get());
        this.registerStairs(blockModels, itemModels, OccultismBlocks.SILVER_CUT_STAIRS.get(), OccultismBlocks.SILVER_CUT_BLOCK.get());
        this.registerSlab(blockModels, itemModels, OccultismBlocks.SILVER_CUT_SLAB.get(), OccultismBlocks.SILVER_CUT_BLOCK.get());
    }

    private void registerStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block stairs, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier straightModel = ModelTemplates.STAIRS_STRAIGHT.create(stairs, textures, blockModels.modelOutput);
        Identifier innerModel = ModelTemplates.STAIRS_INNER.create(stairs, textures, blockModels.modelOutput);
        Identifier outerModel = ModelTemplates.STAIRS_OUTER.create(stairs, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createStairs(stairs,
                        plainVariant(innerModel),
                        plainVariant(straightModel),
                        plainVariant(outerModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, stairs, straightModel);
    }

    private void registerSlab(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier bottomModel = ModelTemplates.SLAB_BOTTOM.create(slab, textures, blockModels.modelOutput);
        Identifier topModel = ModelTemplates.SLAB_TOP.create(slab, textures, blockModels.modelOutput);
        Identifier fullModel = this.blockModel(parent);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createSlab(slab,
                        plainVariant(bottomModel),
                        plainVariant(topModel),
                        plainVariant(fullModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, slab, bottomModel);
    }

    private void registerFencesAndGates(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerFence(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_FENCE.get(), OccultismBlocks.OTHERPLANKS.get());
        this.registerFenceGate(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(), OccultismBlocks.OTHERPLANKS.get());
        this.registerDoor(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_DOOR.get());
        this.registerTrapdoor(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_TRAPDOOR.get());
        this.registerButton(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_BUTTON.get(), OccultismBlocks.OTHERPLANKS.get());
        this.registerButton(blockModels, itemModels, OccultismBlocks.OTHERSTONE_BUTTON.get(), OccultismBlocks.OTHERSTONE.get());
        this.registerButton(blockModels, itemModels, OccultismBlocks.OTHERROCK_BUTTON.get(), OccultismBlocks.OTHERROCK.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERSTONE_WALL.get(), OccultismBlocks.OTHERSTONE.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERCOBBLESTONE_WALL.get(), OccultismBlocks.OTHERCOBBLESTONE.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERSTONE_WALL.get(), OccultismBlocks.POLISHED_OTHERSTONE.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERSTONE_BRICKS_WALL.get(), OccultismBlocks.OTHERSTONE_BRICKS.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERROCK_WALL.get(), OccultismBlocks.OTHERROCK.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERCOBBLEROCK_WALL.get(), OccultismBlocks.OTHERCOBBLEROCK.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.POLISHED_OTHERROCK_WALL.get(), OccultismBlocks.POLISHED_OTHERROCK.get());
        this.registerWall(blockModels, itemModels, OccultismBlocks.OTHERROCK_BRICKS_WALL.get(), OccultismBlocks.OTHERROCK_BRICKS.get());

        this.registerDoor(blockModels, itemModels, OccultismBlocks.SILVER_DOOR.get());
        this.registerTrapdoor(blockModels, itemModels, OccultismBlocks.SILVER_TRAPDOOR.get());
        this.registerBars(blockModels, itemModels, OccultismBlocks.SILVER_BARS_BLOCK.get(), OccultismBlocks.SILVER_BARS_BLOCK.get());
        this.registerChain(blockModels, itemModels, OccultismBlocks.SILVER_CHAIN_BLOCK.get());
        this.registerBulb(blockModels, itemModels, OccultismBlocks.SILVER_BULB.get());
        this.createShelf(OccultismBlocks.OTHERPLANKS_SHELF.get(), OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get(), blockModels, itemModels);
    }

    private void registerFence(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block fence, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier postModel = ModelTemplates.FENCE_POST.create(fence, textures, blockModels.modelOutput);
        Identifier sideModel = ModelTemplates.FENCE_SIDE.create(fence, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFence(fence, plainVariant(postModel), plainVariant(sideModel))
        );
        this.registerExistingItemModel(itemModels, fence.asItem(), this.itemModel(fence));
    }

    private void registerBars(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block bars, Block parent) {
        TextureMapping textures = TextureMapping.bars(parent);
        MultiVariant postEnds = plainVariant(ModelTemplates.BARS_POST_ENDS.create(bars, textures, blockModels.modelOutput));
        MultiVariant post = plainVariant(ModelTemplates.BARS_POST.create(bars, textures, blockModels.modelOutput));
        MultiVariant cap = plainVariant(ModelTemplates.BARS_CAP.create(bars, textures, blockModels.modelOutput));
        MultiVariant capAlt = plainVariant(ModelTemplates.BARS_CAP_ALT.create(bars, textures, blockModels.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.BARS_POST_SIDE.create(bars, textures, blockModels.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.BARS_POST_SIDE_ALT.create(bars, textures, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(bars).with(postEnds).with(condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), post).with(condition().term(BlockStateProperties.NORTH, true).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), cap).with(condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, true).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), cap.with(Y_ROT_90)).with(condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, true).term(BlockStateProperties.WEST, false), capAlt).with(condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, true), capAlt.with(Y_ROT_90)).with(condition().term(BlockStateProperties.NORTH, true), side).with(condition().term(BlockStateProperties.EAST, true), side.with(Y_ROT_90)).with(condition().term(BlockStateProperties.SOUTH, true), sideAlt).with(condition().term(BlockStateProperties.WEST, true), sideAlt.with(Y_ROT_90)));

        this.registerExistingItemModel(itemModels, bars.asItem(), this.itemModel(bars));
    }

    private void registerChain(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block chain) {
        blockModels.blockStateOutput.accept(createAxisAlignedPillarBlock(chain,
                plainVariant(TexturedModel.CHAIN.create(chain, blockModels.modelOutput))));
        itemModels.generateFlatItem(chain.asItem(), ModelTemplates.FLAT_ITEM);
    }

    public void registerBulb(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block bulb) {
        MultiVariant baseModel = plainVariant(ModelTemplates.CUBE_ALL.create(bulb, TextureMapping.cube(bulb), blockModels.modelOutput));
        MultiVariant baseModelPowered = plainVariant(this.createSuffixedVariant(blockModels, bulb, "_powered", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        MultiVariant litModel = plainVariant(this.createSuffixedVariant(blockModels, bulb, "_lit", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        MultiVariant litModelPowered = plainVariant(this.createSuffixedVariant(blockModels, bulb, "_lit_powered", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        blockModels.blockStateOutput.accept(createCopperBulb(bulb, baseModel, litModel, baseModelPowered, litModelPowered));
        this.registerExistingItemModel(itemModels, bulb.asItem(), this.blockModel(bulb));
    }

    public Identifier createSuffixedVariant(BlockModelGenerators blockModels, Block block, String suffix, ModelTemplate template, Function<Material, TextureMapping> textureMapping) {
        return template.createWithSuffix(block, suffix, textureMapping.apply(TextureMapping.getBlockTexture(block, suffix)), blockModels.modelOutput);
    }

    private void registerFenceGate(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block fenceGate, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier closedModel = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, textures, blockModels.modelOutput);
        Identifier openModel = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, textures, blockModels.modelOutput);
        Identifier wallClosedModel = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, textures, blockModels.modelOutput);
        Identifier wallOpenModel = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFenceGate(fenceGate,
                        plainVariant(openModel),
                        plainVariant(closedModel),
                        plainVariant(wallOpenModel),
                        plainVariant(wallClosedModel),
                        false)
        );
        this.registerParentedItemModel(blockModels, itemModels, fenceGate, closedModel);
    }

    private void registerDoor(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block door) {
        TextureMapping textures = TextureMapping.door(door);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createDoor(
                        door,
                        plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(door, textures, blockModels.modelOutput)),
                        plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(door, textures, blockModels.modelOutput))
                )
        );
        this.registerExistingItemModel(itemModels, door.asItem(), this.itemModel(door));
    }

    private void registerTrapdoor(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block trapdoor) {
        TextureMapping textures = TextureMapping.defaultTexture(trapdoor);
        Identifier topModel = ModelTemplates.TRAPDOOR_TOP.create(trapdoor, textures, blockModels.modelOutput);
        Identifier bottomModel = ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoor, textures, blockModels.modelOutput);
        Identifier openModel = ModelTemplates.TRAPDOOR_OPEN.create(trapdoor, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createTrapdoor(
                        trapdoor,
                        plainVariant(topModel),
                        plainVariant(bottomModel),
                        plainVariant(openModel)
                )
        );
        this.registerExistingItemModel(itemModels, trapdoor.asItem(), this.itemModel(trapdoor));
    }

    private void registerButton(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block button, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier unpoweredModel = ModelTemplates.BUTTON.create(button, textures, blockModels.modelOutput);
        Identifier poweredModel = ModelTemplates.BUTTON_PRESSED.create(button, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createButton(
                        button,
                        plainVariant(unpoweredModel),
                        plainVariant(poweredModel)
                )
        );
        this.registerExistingItemModel(itemModels, button.asItem(), this.itemModel(button));
    }

    private void registerWall(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block wall, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier postModel = ModelTemplates.WALL_POST.create(wall, textures, blockModels.modelOutput);
        Identifier lowSideModel = ModelTemplates.WALL_LOW_SIDE.create(wall, textures, blockModels.modelOutput);
        Identifier tallSideModel = ModelTemplates.WALL_TALL_SIDE.create(wall, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createWall(
                        wall,
                        plainVariant(postModel),
                        plainVariant(lowSideModel),
                        plainVariant(tallSideModel)
                )
        );
        this.registerExistingItemModel(itemModels, wall.asItem(), this.itemModel(wall));
    }

    private void registerPressurePlates(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerPressurePlate(blockModels, itemModels, OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get(), OccultismBlocks.OTHERSTONE.get());
        this.registerPressurePlate(blockModels, itemModels, OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get(), OccultismBlocks.OTHERROCK.get());
        this.registerPressurePlate(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get(), OccultismBlocks.OTHERPLANKS.get());
    }

    private void registerPressurePlate(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block plate, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier upModel = ModelTemplates.PRESSURE_PLATE_UP.create(plate, textures, blockModels.modelOutput);
        Identifier downModel = ModelTemplates.PRESSURE_PLATE_DOWN.create(plate, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createPressurePlate(plate,
                        plainVariant(upModel),
                        plainVariant(downModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, plate, upModel);
    }

    public void createShelf(Block block, Block particle, BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.ALL, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle));
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_BODY, null, null, blockModels);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_UNPOWERED, false, null, blockModels);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_UNCONNECTED, true, SideChainPart.UNCONNECTED, blockModels);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_LEFT, true, SideChainPart.LEFT, blockModels);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_CENTER, true, SideChainPart.CENTER, blockModels);
        this.addShelfPart(block, mapping, generator, ModelTemplates.SHELF_RIGHT, true, SideChainPart.RIGHT, blockModels);
        blockModels.blockStateOutput.accept(generator);
        this.registerParentedItemModel(blockModels, itemModels, block, ModelTemplates.SHELF_INVENTORY.create(block, mapping, blockModels.modelOutput));
    }

    public void addShelfPart(Block block, TextureMapping mapping, MultiPartGenerator generator, ModelTemplate template,
            @Nullable Boolean isPowered, @Nullable SideChainPart sideChainPart, BlockModelGenerators blockModels) {
        MultiVariant variant = plainVariant(template.create(block, mapping, blockModels.modelOutput));
        forEachHorizontalDirection((direction, rotation) -> generator.with(shelfCondition(direction, isPowered, sideChainPart), variant.with(rotation)));
    }

    public static void forEachHorizontalDirection(BiConsumer<Direction, VariantMutator> consumer) {
        List.of(Pair.of(Direction.NORTH, NOP), Pair.of(Direction.EAST, Y_ROT_90), Pair.of(Direction.SOUTH, Y_ROT_180), Pair.of(Direction.WEST, Y_ROT_270))
                .forEach(pair -> {
                    Direction direction = pair.getFirst();
                    VariantMutator rotation = pair.getSecond();
                    consumer.accept(direction, rotation);
                });
    }

    public static Condition shelfCondition(Direction direction, @Nullable Boolean isPowered, @Nullable SideChainPart sideChainPart) {
        ConditionBuilder facing = condition(BlockStateProperties.HORIZONTAL_FACING, direction);
        if (isPowered == null) {
            return facing.build();
        } else {
            ConditionBuilder powered = condition(BlockStateProperties.POWERED, isPowered);
            return sideChainPart != null ? and(facing, powered, condition(BlockStateProperties.SIDE_CHAIN_PART, sideChainPart)) : and(facing, powered);
        }
    }

    private void registerSpecialBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        Block[] directionalBlocks = {
                OccultismBlocks.DARK_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.SACRIFICIAL_BOWL.get(),
                OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER0.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER1.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER2.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER3.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER4.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER5.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get(),
                OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK.get()
        };
        for (Block block : directionalBlocks) {
            this.registerFacingBlock(blockModels, itemModels, block);
        }

        this.registerDirectionalFrameBlock(blockModels, itemModels, OccultismBlocks.RITUAL_CATCHER.get());
        this.registerDirectionalFrameBlock(blockModels, itemModels, OccultismBlocks.RITUAL_CATCHER_DARK.get());
        this.registerDirectionalFrameBlock(blockModels, itemModels, OccultismBlocks.ENTITY_WORMHOLE.get());
        this.registerDirectionalFrameBlock(blockModels, itemModels, OccultismBlocks.ENTITY_WORMHOLE_DARK.get());
        this.registerLinkedBlock(blockModels, itemModels, OccultismBlocks.STABLE_WORMHOLE.get());
        this.registerLinkedBlock(blockModels, itemModels, OccultismBlocks.STABLE_WORMHOLE_DARK.get());
    }

    private void registerFacingBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier model = this.blockModel(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, plainVariant(model))
                        .with(PropertyDispatch.modify(BlockStateProperties.FACING)
                                .select(Direction.UP, BlockModelGenerators.NOP)
                                .select(Direction.DOWN, BlockModelGenerators.X_ROT_180)
                                .select(Direction.NORTH, BlockModelGenerators.X_ROT_90)
                                .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
                                .select(Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
                                .select(Direction.EAST, BlockModelGenerators.X_ROT_90.then(Y_ROT_90))
                        )
        );
        this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
    }


    private void registerDirectionalFrameBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier model = this.blockModel(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(BlockStateProperties.FACING)
                                .generate((facing) -> plainVariant(model).with(switch (facing) {
                                    case UP -> BlockModelGenerators.NOP;
                                    case DOWN -> BlockModelGenerators.X_ROT_180;
                                    case NORTH -> BlockModelGenerators.X_ROT_90;
                                    case SOUTH -> BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180);
                                    case WEST -> BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270);
                                    case EAST -> BlockModelGenerators.X_ROT_90.then(Y_ROT_90);
                                })))
        );
        this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
    }

    private void registerEntityWormholeBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier model = this.blockModel(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(EntityWormholeBlock.EXIT_ROTATION_X, EntityWormholeBlock.EXIT_ROTATION_Y, BlockStateProperties.FACING)
                                .generate((rotationX, rotationY, facing) -> plainVariant(model).with(switch (facing) {
                                    case UP -> BlockModelGenerators.NOP;
                                    case DOWN -> BlockModelGenerators.X_ROT_180;
                                    case NORTH -> BlockModelGenerators.X_ROT_90;
                                    case SOUTH -> BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180);
                                    case WEST -> BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270);
                                    case EAST -> BlockModelGenerators.X_ROT_90.then(Y_ROT_90);
                                })))
        );
        this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
    }

    private void registerLinkedBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier linkedModel = this.blockModel(block);
        Identifier unlinkedModel = this.modLoc("block/" + this.name(block) + "_unlinked");

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(BlockStateProperties.FACING, StableWormholeBlock.LINKED)
                                .generate((facing, linked) -> plainVariant(linked ? linkedModel : unlinkedModel)
                                        .with(switch (facing) {
                                            case UP -> BlockModelGenerators.NOP;
                                            case DOWN -> BlockModelGenerators.X_ROT_180;
                                            case NORTH -> BlockModelGenerators.X_ROT_90;
                                            case SOUTH ->
                                                    BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180);
                                            case WEST ->
                                                    BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270);
                                            case EAST ->
                                                    BlockModelGenerators.X_ROT_90.then(Y_ROT_90);
                                        })))
        );

        itemModels.itemModelOutput.accept(block.asItem(), new Unbaked(
                Optional.empty(),
                new StableWormholeBlockItemPropertyGetter(),
                ItemModelUtils.plainModel(this.modLoc("item/" + this.name(block) + "_linked")),
                ItemModelUtils.plainModel(this.modLoc("item/" + this.name(block) + "_unlinked"))
        ));
    }

    private void registerSimpleModelBlock(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, plainVariant(this.blockModel(block))));
    }

    private void registerSimpleModelBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        this.registerSimpleModelBlock(blockModels, block);
        this.registerExistingItemModel(itemModels, block.asItem(), this.blockModel(block));
    }

    private void registerParentedItemModel(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, Identifier parentModel) {
        Identifier itemModelId = this.itemModel(block);
        this.emitParentModel(itemModels.modelOutput, itemModelId, parentModel, Map.of());
        itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModelId));
    }

    private void registerExistingItemModel(ItemModelGenerators itemModels, Item item, Identifier modelLocation) {
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelLocation));
    }

    private void registerConditionalOtherworldSapling(ItemModelGenerators itemModels, Block block) {
        Identifier simulated = this.modLoc("item/otherworld_sapling_natural_simulated");
        Identifier inventory = this.modLoc("item/otherworld_sapling_natural_inventory");
        Identifier generatedParent = Identifier.fromNamespaceAndPath("minecraft", "item/generated");
        this.emitParentModel(itemModels.modelOutput, simulated, generatedParent, Map.of("layer0", Identifier.fromNamespaceAndPath("minecraft", "block/oak_sapling")));
        this.emitParentModel(itemModels.modelOutput, inventory, generatedParent, Map.of("layer0", this.modLoc("block/otherworld_sapling")));
        itemModels.itemModelOutput.accept(block.asItem(), new Unbaked(
                Optional.empty(),
                new OtherworldBlockItemPropertyGetter(),
                ItemModelUtils.plainModel(inventory),
                ItemModelUtils.plainModel(simulated)
        ));
    }

    private void emitParticleModel(BiConsumer<Identifier, ModelInstance> output, Identifier modelLocation, Identifier particleTexture) {
        this.emitParentModel(output, modelLocation, null, Map.of("particle", particleTexture));
    }

    private void emitParentModel(BiConsumer<Identifier, ModelInstance> output, Identifier modelLocation, Identifier parent, Map<String, Identifier> textures) {
        output.accept(modelLocation, () -> {
            JsonObject json = new JsonObject();
            if (parent != null) {
                json.addProperty("parent", parent.toString());
            }
            if (!textures.isEmpty()) {
                JsonObject textureJson = new JsonObject();
                textures.forEach((key, value) -> textureJson.addProperty(key, value.toString()));
                json.add("textures", textureJson);
            }
            return json;
        });
    }
}
