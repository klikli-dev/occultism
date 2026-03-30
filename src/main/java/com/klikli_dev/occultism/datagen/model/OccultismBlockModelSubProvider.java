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
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;
import java.util.function.BiConsumer;

public class OccultismBlockModelSubProvider {

    private Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, path);
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private Identifier blockModel(Block block) {
        return modLoc("block/" + this.name(block));
    }

    private Identifier itemModel(Block block) {
        return modLoc("item/" + this.name(block));
    }

    public void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerSimpleBlocks(blockModels, itemModels);
        this.registerDirectionalBlocks(blockModels, itemModels);
        this.registerGlyphBlocks(blockModels, itemModels);
        this.registerStairsAndSlabs(blockModels, itemModels);
        this.registerFencesAndGates(blockModels, itemModels);
        this.registerPressurePlates(blockModels, itemModels);
        this.registerSpecialBlocks(blockModels, itemModels);
        this.registerCustomModelBlocks(blockModels, itemModels);
    }

    private void registerSimpleBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // NOTE: Blocks with custom model JSON (in src/main/resources) are excluded from this list.
        // Custom models include: DATURA (cross crop), SPIRIT_FIRE (custom fire elements),
        // SPIRIT_FIRE_COLOR/RAINBOW/VOID (fire variants), CHALK_GLYPH_* (flat plane with tint).
        Block[] cubeAll = {
                OccultismBlocks.OTHERSTONE.get(),
                OccultismBlocks.OTHERSTONE_BRICKS.get(),
                OccultismBlocks.OTHERSTONE_PEDESTAL.get(),
                OccultismBlocks.OTHERROCK.get(),
                OccultismBlocks.OTHERROCK_BRICKS.get(),
                OccultismBlocks.OTHERROCK_PEDESTAL.get(),
                OccultismBlocks.OTHERCOBBLESTONE.get(),
                OccultismBlocks.OTHERCOBBLEROCK.get(),
                OccultismBlocks.POLISHED_OTHERSTONE.get(),
                OccultismBlocks.POLISHED_OTHERROCK.get(),
                OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.OTHERPLANKS.get(),
                OccultismBlocks.OTHERWORLD_LEAVES.get(),
                OccultismBlocks.SILVER_BLOCK.get(),
                OccultismBlocks.RAW_SILVER_BLOCK.get(),
                OccultismBlocks.IESNIUM_BLOCK.get(),
                OccultismBlocks.RAW_IESNIUM_BLOCK.get(),
                OccultismBlocks.SILVER_ORE.get(),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get(),
                OccultismBlocks.IESNIUM_ORE.get(),
                OccultismBlocks.TALLOW_BLOCK.get(),
                OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get(),
                OccultismBlocks.DIMENSIONAL_MINESHAFT.get(),
                OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get(),
                OccultismBlocks.DIMENSIONAL_EXTRACTOR.get(),
                OccultismBlocks.SPIRIT_GRINDSTONE.get(),
                OccultismBlocks.SPIRIT_CAMPFIRE.get(),
                OccultismBlocks.SPIRIT_LANTERN.get(),
                // NOTE: SPIRIT_FIRE uses custom model JSON (src/main/resources)
                OccultismBlocks.LIGHTED_AIR.get(),
                OccultismBlocks.SACRIFICIAL_BOWL.get(),
                OccultismBlocks.COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_GOLDEN_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL.get(),
                OccultismBlocks.CELESTIAL_CHALICE.get(),
                OccultismBlocks.ELDRITCH_CHALICE.get(),
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
                OccultismBlocks.OTHERGLASS_NATURAL.get(),
                // natural / otherworld components
                OccultismBlocks.OTHERSTONE_NATURAL.get(),
                OccultismBlocks.OTHERROCK_NATURAL.get(),
                OccultismBlocks.OTHERGLASS.get(),
                OccultismBlocks.OTHERFLOWER.get(),
                OccultismBlocks.OTHERFLOWER_NATURAL.get(),
                OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get(),
                OccultismBlocks.IESNIUM_ORE_NATURAL.get(),
                // saplings
                OccultismBlocks.OTHERWORLD_SAPLING.get(),
                OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get(),
                // fences / doors / buttons / trapdoors (use trivial cube for now)
                OccultismBlocks.OTHERPLANKS_FENCE.get(),
                OccultismBlocks.OTHERPLANKS_DOOR.get(),
                OccultismBlocks.OTHERPLANKS_TRAPDOOR.get(),
                OccultismBlocks.OTHERPLANKS_BUTTON.get(),
                OccultismBlocks.OTHERSTONE_BUTTON.get(),
                OccultismBlocks.OTHERROCK_BUTTON.get(),
                // walls
                OccultismBlocks.OTHERSTONE_WALL.get(),
                OccultismBlocks.OTHERCOBBLESTONE_WALL.get(),
                OccultismBlocks.POLISHED_OTHERSTONE_WALL.get(),
                OccultismBlocks.OTHERSTONE_BRICKS_WALL.get(),
                OccultismBlocks.OTHERROCK_WALL.get(),
                OccultismBlocks.OTHERCOBBLEROCK_WALL.get(),
                OccultismBlocks.POLISHED_OTHERROCK_WALL.get(),
                OccultismBlocks.OTHERROCK_BRICKS_WALL.get(),
                // otherrock variants
                OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get(),
                // large candles (all color variants)
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
                // storage controller variants
                OccultismBlocks.STORAGE_CONTROLLER_BASE.get(),
                OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get(),
                OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get(),
                // wormholes / entity wormholes
                OccultismBlocks.ENTITY_WORMHOLE.get(),
                OccultismBlocks.ENTITY_WORMHOLE_DARK.get(),
                // anvil
                OccultismBlocks.IESNIUM_ANVIL.get(),
                // torches
                OccultismBlocks.SPIRIT_TORCH.get(),
                OccultismBlocks.SPIRIT_WALL_TORCH.get(),
                OccultismBlocks.LARGE_CANDLE.get(),
                OccultismBlocks.STORAGE_CONTROLLER.get(),
                OccultismBlocks.STORAGE_CONTROLLER_DARK.get(),
                // potted, signs, skulls
                OccultismBlocks.POTTED_OTHERFLOWER.get(),
                OccultismBlocks.OTHERPLANKS_SIGN.get(),
                OccultismBlocks.OTHERPLANKS_WALL_SIGN.get(),
                OccultismBlocks.OTHERPLANKS_HANGING_SIGN.get(),
                OccultismBlocks.OTHERPLANKS_WALL_HANGING_SIGN.get(),
                // NOTE: DATURA uses cross crop model JSON (src/main/resources)
                OccultismBlocks.SKELETON_SKULL_DUMMY.get(),
                OccultismBlocks.WITHER_SKELETON_SKULL_DUMMY.get(),
        };
        for (Block block : cubeAll) {
            blockModels.createTrivialCube(block);
        }
    }

    private void registerDirectionalBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.OTHERWORLD_LOG.get());
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.OTHERWORLD_WOOD.get());
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.STRIPPED_OTHERWORLD_LOG.get());
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.get());
        // natural variants (use pillar models too)
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.get());
        this.registerAxisAlignedPillar(blockModels, itemModels, OccultismBlocks.OTHERWORLD_LOG_NATURAL.get());
    }

    private void registerAxisAlignedPillar(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        if (block instanceof RotatedPillarBlock) {
            Material sideTexture = new Material(modLoc("block/" + this.name(block)));
            Material endTexture = new Material(modLoc("block/" + this.name(block) + "_top"));
            Identifier model = ModelTemplates.CUBE_COLUMN.create(
                    block,
                    TextureMapping.column(sideTexture, endTexture),
                    blockModels.modelOutput
            );
            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, blockModels.plainVariant(model))
                            .with(PropertyDispatch.modify(BlockStateProperties.AXIS)
                                    .select(Direction.Axis.Y, BlockModelGenerators.NOP)
                                    .select(Direction.Axis.Z, BlockModelGenerators.X_ROT_90)
                                    .select(Direction.Axis.X, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
                            )
            );
            this.registerParentedItemModel(blockModels, itemModels, block, model);
        } else {
            blockModels.createTrivialCube(block);
        }
    }

    private void registerGlyphBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
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
        // Glyph blocks have a SIGN property with 14 variants (0-13)
        // For now, use a simple model for all variants
        Identifier model = blockModel(block);
        this.emitParticleModel(blockModels.modelOutput, model, modLoc("block/chalk_glyph"));

        // Create blockstate - use same model for all SIGN variants
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, blockModels.plainVariant(model))
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
    }

    private void registerStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block stairs, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier straightModel = ModelTemplates.STAIRS_STRAIGHT.create(stairs, textures, blockModels.modelOutput);
        Identifier innerModel = ModelTemplates.STAIRS_INNER.create(stairs, textures, blockModels.modelOutput);
        Identifier outerModel = ModelTemplates.STAIRS_OUTER.create(stairs, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createStairs(stairs,
                        blockModels.plainVariant(innerModel),
                        blockModels.plainVariant(straightModel),
                        blockModels.plainVariant(outerModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, stairs, straightModel);
    }

    private void registerSlab(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier bottomModel = ModelTemplates.SLAB_BOTTOM.create(slab, textures, blockModels.modelOutput);
        Identifier topModel = ModelTemplates.SLAB_TOP.create(slab, textures, blockModels.modelOutput);
        Identifier fullModel = blockModel(parent);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createSlab(slab,
                        blockModels.plainVariant(bottomModel),
                        blockModels.plainVariant(topModel),
                        blockModels.plainVariant(fullModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, slab, bottomModel);
    }

    private void registerFencesAndGates(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.registerFenceGate(blockModels, itemModels, OccultismBlocks.OTHERPLANKS_FENCE_GATE.get(), OccultismBlocks.OTHERPLANKS.get());
    }

    private void registerFenceGate(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block fenceGate, Block parent) {
        TextureMapping textures = TextureMapping.cube(parent);
        Identifier closedModel = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, textures, blockModels.modelOutput);
        Identifier openModel = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, textures, blockModels.modelOutput);
        Identifier wallClosedModel = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, textures, blockModels.modelOutput);
        Identifier wallOpenModel = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, textures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFenceGate(fenceGate,
                        blockModels.plainVariant(openModel),
                        blockModels.plainVariant(closedModel),
                        blockModels.plainVariant(wallOpenModel),
                        blockModels.plainVariant(wallClosedModel),
                        false)
        );
        this.registerParentedItemModel(blockModels, itemModels, fenceGate, closedModel);
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
                        blockModels.plainVariant(upModel),
                        blockModels.plainVariant(downModel))
        );
        this.registerParentedItemModel(blockModels, itemModels, plate, upModel);
    }

    private void registerSpecialBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Stable Wormhole - has a LINKED property
        this.registerLinkedBlock(blockModels, itemModels, OccultismBlocks.STABLE_WORMHOLE.get());
        this.registerLinkedBlock(blockModels, itemModels, OccultismBlocks.STABLE_WORMHOLE_DARK.get());
    }

    private void registerLinkedBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier unlinkedModel = blockModel(block);
        Identifier linkedModel = modLoc("block/" + this.name(block) + "_linked");

        this.emitParentModel(blockModels.modelOutput, unlinkedModel, modLoc("block/" + this.name(block) + "_template"), Map.of(
                "texture", modLoc("block/" + this.name(block)),
                "particle", modLoc("block/" + this.name(block))
        ));
        this.emitParentModel(blockModels.modelOutput, linkedModel, modLoc("block/" + this.name(block) + "_linked_template"), Map.of(
                "texture", modLoc("block/" + this.name(block) + "_linked"),
                "particle", modLoc("block/" + this.name(block) + "_linked")
        ));

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(StableWormholeBlock.LINKED)
                                .select(false, blockModels.plainVariant(unlinkedModel))
                                .select(true, blockModels.plainVariant(linkedModel))
                        )
        );

        this.registerParentedItemModel(blockModels, itemModels, block, unlinkedModel);
    }

    /**
     * Registers blockstates for blocks that have custom model JSON files
     * in src/main/resources (not generated by datagen).
     * These blocks were removed from createTrivialCube to avoid overriding the custom models.
     * Item references are handled by OccultismItemModelSubProvider or createTrivialCube elsewhere.
     */
    private void registerCustomModelBlocks(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Blocks with custom model JSON that need blockstate generation only.
        // Item models are handled by OccultismItemModelSubProvider (datura) or
        // need block-parented item models (spirit_fire).
        Block[] blockstateOnly = {
                OccultismBlocks.DATURA.get(),
        };
        for (Block block : blockstateOnly) {
            Identifier model = blockModel(block);
            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, blockModels.plainVariant(model))
            );
        }

        // Blocks with custom model JSON that also need item reference (block-parented)
        Block[] withItemModel = {
                OccultismBlocks.SPIRIT_FIRE.get(),
        };
        for (Block block : withItemModel) {
            Identifier model = blockModel(block);
            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, blockModels.plainVariant(model))
            );
            this.registerParentedItemModel(blockModels, itemModels, block, model);
        }
    }

    private void registerParentedItemModel(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, Identifier parentModel) {
        Identifier itemModelId = itemModel(block);
        this.emitParentModel(itemModels.modelOutput, itemModelId, parentModel, Map.of());
        itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModelId));
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
