/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, McJty
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

package com.klikli_dev.occultism.datagen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.klikli_dev.occultism.common.block.RainbowGlyphBlock;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 * and https://github.com/Tutorials-By-Kaupenjoe
 */
public class StandardBlockStateProvider extends BlockStateProvider {

    public StandardBlockStateProvider(PackOutput gen,
                                      ExistingFileHelper exFileHelper) {
        super(gen, Occultism.MODID, exFileHelper);
    }

    private void registerCubesBlockAndItem() {
        Block[] blocks = {
                OccultismBlocks.CHISELED_OTHERROCK_BRICKS.get(),
                OccultismBlocks.CHISELED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERROCK_BRICKS.get(),
                OccultismBlocks.CRACKED_OTHERSTONE_BRICKS.get(),
                OccultismBlocks.IESNIUM_BLOCK.get(),
                OccultismBlocks.IESNIUM_ORE.get(),
                OccultismBlocks.OTHERCOBBLEROCK.get(),
                OccultismBlocks.OTHERCOBBLESTONE.get(),
                OccultismBlocks.OTHERPLANKS.get(),
                OccultismBlocks.OTHERROCK.get(),
                OccultismBlocks.OTHERSTONE.get(),
                OccultismBlocks.OTHERROCK_BRICKS.get(),
                OccultismBlocks.OTHERSTONE_BRICKS.get(),
                OccultismBlocks.POLISHED_OTHERROCK.get(),
                OccultismBlocks.POLISHED_OTHERSTONE.get(),
                OccultismBlocks.RAW_IESNIUM_BLOCK.get(),
                OccultismBlocks.RAW_SILVER_BLOCK.get(),
                OccultismBlocks.SILVER_BLOCK.get(),
                OccultismBlocks.SILVER_ORE.get(),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get(),
                OccultismBlocks.TALLOW_BLOCK.get()
        };
        for (Block block : blocks){
            this.simpleBlockWithItem(block, cubeAll(block));
        }
    }

    private void registerDirectionalBlock() {

        DeferredBlock[] blocks = {
                OccultismBlocks.DARK_SACRIFICIAL_BOWL,
                OccultismBlocks.DARK_COPPER_SACRIFICIAL_BOWL,
                OccultismBlocks.DARK_SILVER_SACRIFICIAL_BOWL,
                OccultismBlocks.SACRIFICIAL_BOWL,
                OccultismBlocks.COPPER_SACRIFICIAL_BOWL,
                OccultismBlocks.SILVER_SACRIFICIAL_BOWL,
                OccultismBlocks.STORAGE_STABILIZER_TIER0,
                OccultismBlocks.STORAGE_STABILIZER_TIER1,
                OccultismBlocks.STORAGE_STABILIZER_TIER2,
                OccultismBlocks.STORAGE_STABILIZER_TIER3,
                OccultismBlocks.STORAGE_STABILIZER_TIER4,
                OccultismBlocks.STORAGE_STABILIZER_TIER5,
                OccultismBlocks.ENTITY_WORMHOLE,
                OccultismBlocks.STORAGE_STABILIZER_TIER0_DARK,
                OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK,
                OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK,
                OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK,
                OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK,
                OccultismBlocks.STORAGE_STABILIZER_TIER5_DARK,
                OccultismBlocks.ENTITY_WORMHOLE_DARK
        };
        for (DeferredBlock block : blocks){
            this.directionalBlock((Block) block.get(),
                    this.models().getExistingFile(this.modLoc(block.getKey().location().getPath())));
        }
    }

    @Override
    protected void registerStatesAndModels() {
        registerCubesBlockAndItem();
        registerDirectionalBlock();
        //Generate blockstates for the glyphs
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(block -> block instanceof ChalkGlyphBlock && !(block instanceof RainbowGlyphBlock))
                .forEach(this::generateGlyphBlockState);
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(block -> block instanceof RainbowGlyphBlock)
                .forEach(this::generateRainbowGlyphBlockState);
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller")));
        this.models().withExistingParent("item/storage_controller", this.modLoc("block/storage_controller"));
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER_DARK.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller_dark")));
        this.models().withExistingParent("item/storage_controller_dark", this.modLoc("block/storage_controller_dark"));
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller_base")));
        this.models().withExistingParent("item/storage_controller_base", this.modLoc("block/storage_controller_base"));
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller_base_dark")));
        this.models().withExistingParent("item/storage_controller_base_dark", this.modLoc("block/storage_controller_base_dark"));
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller_stabilized")));
        this.models().withExistingParent("item/storage_controller_stabilized", this.modLoc("block/storage_controller_stabilized"));
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER_STABILIZED_DARK.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller_stabilized_dark")));
        this.models().withExistingParent("item/storage_controller_stabilized_dark", this.modLoc("block/storage_controller_stabilized_dark"));
        this.generateStableWormholeState(OccultismBlocks.STABLE_WORMHOLE.get());
        this.generateStableWormholeStateDark(OccultismBlocks.STABLE_WORMHOLE_DARK.get());

        stairsBlock(((StairBlock) OccultismBlocks.OTHERPLANKS_STAIRS.get()), blockTexture(OccultismBlocks.OTHERPLANKS.get()));
        fenceBlock(((FenceBlock) OccultismBlocks.OTHERPLANKS_FENCE.get()), blockTexture(OccultismBlocks.OTHERPLANKS.get()));
        fenceGateBlock(((FenceGateBlock) OccultismBlocks.OTHERPLANKS_FENCE_GATE.get()), blockTexture(OccultismBlocks.OTHERPLANKS.get()));
        doorBlockWithRenderType(((DoorBlock) OccultismBlocks.OTHERPLANKS_DOOR.get()), modLoc("block/otherplanks_door_bottom"), modLoc("block/otherplanks_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) OccultismBlocks.OTHERPLANKS_TRAPDOOR.get()), modLoc("block/otherplanks_trapdoor"), true, "cutout");
        pressurePlateBlock(((PressurePlateBlock) OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.get()), blockTexture(OccultismBlocks.OTHERPLANKS.get()));
        buttonBlock(((ButtonBlock) OccultismBlocks.OTHERPLANKS_BUTTON.get()), blockTexture(OccultismBlocks.OTHERPLANKS.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERSTONE_STAIRS.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERSTONE_SLAB.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERSTONE_WALL.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()));
        pressurePlateBlock(((PressurePlateBlock) OccultismBlocks.OTHERSTONE_PRESSURE_PLATE.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()));
        buttonBlock(((ButtonBlock) OccultismBlocks.OTHERSTONE_BUTTON.get()), blockTexture(OccultismBlocks.OTHERSTONE.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERCOBBLESTONE_STAIRS.get()), blockTexture(OccultismBlocks.OTHERCOBBLESTONE.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERCOBBLESTONE_SLAB.get()), blockTexture(OccultismBlocks.OTHERCOBBLESTONE.get()), blockTexture(OccultismBlocks.OTHERCOBBLESTONE.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERCOBBLESTONE_WALL.get()), blockTexture(OccultismBlocks.OTHERCOBBLESTONE.get()));

        stairsBlock(((StairBlock) OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.get()), blockTexture(OccultismBlocks.POLISHED_OTHERSTONE.get()));
        slabBlock(((SlabBlock) OccultismBlocks.POLISHED_OTHERSTONE_SLAB.get()), blockTexture(OccultismBlocks.POLISHED_OTHERSTONE.get()), blockTexture(OccultismBlocks.POLISHED_OTHERSTONE.get()));
        wallBlock(((WallBlock) OccultismBlocks.POLISHED_OTHERSTONE_WALL.get()), blockTexture(OccultismBlocks.POLISHED_OTHERSTONE.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.get()), blockTexture(OccultismBlocks.OTHERSTONE_BRICKS.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERSTONE_BRICKS_SLAB.get()), blockTexture(OccultismBlocks.OTHERSTONE_BRICKS.get()), blockTexture(OccultismBlocks.OTHERSTONE_BRICKS.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERSTONE_BRICKS_WALL.get()), blockTexture(OccultismBlocks.OTHERSTONE_BRICKS.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERROCK_STAIRS.get()), blockTexture(OccultismBlocks.OTHERROCK.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERROCK_SLAB.get()), blockTexture(OccultismBlocks.OTHERROCK.get()), blockTexture(OccultismBlocks.OTHERROCK.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERROCK_WALL.get()), blockTexture(OccultismBlocks.OTHERROCK.get()));
        pressurePlateBlock(((PressurePlateBlock) OccultismBlocks.OTHERROCK_PRESSURE_PLATE.get()), blockTexture(OccultismBlocks.OTHERROCK.get()));
        buttonBlock(((ButtonBlock) OccultismBlocks.OTHERROCK_BUTTON.get()), blockTexture(OccultismBlocks.OTHERROCK.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERCOBBLEROCK_STAIRS.get()), blockTexture(OccultismBlocks.OTHERCOBBLEROCK.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERCOBBLEROCK_SLAB.get()), blockTexture(OccultismBlocks.OTHERCOBBLEROCK.get()), blockTexture(OccultismBlocks.OTHERCOBBLEROCK.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERCOBBLEROCK_WALL.get()), blockTexture(OccultismBlocks.OTHERCOBBLEROCK.get()));

        stairsBlock(((StairBlock) OccultismBlocks.POLISHED_OTHERROCK_STAIRS.get()), blockTexture(OccultismBlocks.POLISHED_OTHERROCK.get()));
        slabBlock(((SlabBlock) OccultismBlocks.POLISHED_OTHERROCK_SLAB.get()), blockTexture(OccultismBlocks.POLISHED_OTHERROCK.get()), blockTexture(OccultismBlocks.POLISHED_OTHERROCK.get()));
        wallBlock(((WallBlock) OccultismBlocks.POLISHED_OTHERROCK_WALL.get()), blockTexture(OccultismBlocks.POLISHED_OTHERROCK.get()));

        stairsBlock(((StairBlock) OccultismBlocks.OTHERROCK_BRICKS_STAIRS.get()), blockTexture(OccultismBlocks.OTHERROCK_BRICKS.get()));
        slabBlock(((SlabBlock) OccultismBlocks.OTHERROCK_BRICKS_SLAB.get()), blockTexture(OccultismBlocks.OTHERROCK_BRICKS.get()), blockTexture(OccultismBlocks.OTHERROCK_BRICKS.get()));
        wallBlock(((WallBlock) OccultismBlocks.OTHERROCK_BRICKS_WALL.get()), blockTexture(OccultismBlocks.OTHERROCK_BRICKS.get()));

        this.simpleBlock(OccultismBlocks.LARGE_CANDLE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_WHITE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_GRAY.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_BLACK.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_BROWN.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_RED.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_ORANGE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_YELLOW.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_LIME.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_GREEN.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_CYAN.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_BLUE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_PINK.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_MAGENTA.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
        this.simpleBlock(OccultismBlocks.LARGE_CANDLE_PURPLE.get(),
                this.models().getExistingFile(this.modLoc("block/large_candle_white")));
    }

    protected void generateStableWormholeState(Block block) {
        ModelFile.ExistingModelFile linkedModel = this.models().getExistingFile(this.modLoc("block/stable_wormhole"));
        ModelFile.ExistingModelFile unlinkedModel = this.models().getExistingFile(
                this.modLoc("block/stable_wormhole_unlinked"));
        this.getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(state.getValue(StableWormholeBlock.LINKED) ? linkedModel : unlinkedModel)
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 :
                                    (((int) dir.toYRot()) + 180) % 360)
                            .build();
                });
    }

    protected void generateStableWormholeStateDark(Block block) {
        ModelFile.ExistingModelFile linkedModel = this.models().getExistingFile(this.modLoc("block/stable_wormhole_dark"));
        ModelFile.ExistingModelFile unlinkedModel = this.models().getExistingFile(
                this.modLoc("block/stable_wormhole_dark_unlinked"));
        this.getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(state.getValue(StableWormholeBlock.LINKED) ? linkedModel : unlinkedModel)
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 :
                                    (((int) dir.toYRot()) + 180) % 360)
                            .build();
                });
    }

    protected void generateGlyphBlockState(Block block) {
        ModelFile.ExistingModelFile parent = this.models()
                .getExistingFile(this.modLoc("block/chalk_glyph/chalk_glyph"));
        this.getVariantBuilder(block)
                .forAllStates(state -> {
                    //this is called for every state combination
                    //create a child model for each glyph texture option
                    int sign = state.getValue(ChalkGlyphBlock.SIGN);
                    ModelFile subModel = this.models().getBuilder("block/chalk_glyph/" + sign).parent(parent)
                            .texture("texture", this.modLoc("block/chalk_glyph/" + sign));

                    return ConfiguredModel.builder()
                            //load the child model
                            .modelFile(subModel)
                            //
                            .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                                    .toYRot())
                            .build();
                });
    }
    protected void generateRainbowGlyphBlockState(Block block) {
        ModelFile.ExistingModelFile parent = this.models()
                .getExistingFile(this.modLoc("block/chalk_glyph/chalk_glyph"));
        this.getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    //this is called for every state combination
                    //create a child model for each glyph texture option
                    int sign = state.getValue(ChalkGlyphBlock.SIGN);
                    ModelFile subModel = this.models().getBuilder("block/chalk_glyph/" + sign).parent(parent)
                            .texture("texture", this.modLoc("block/chalk_glyph/" + sign));

                    return ConfiguredModel.builder()
                            //load the child model
                            .modelFile(subModel)
                            //
                            .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                                    .toYRot())
                            .build();
                }, RainbowGlyphBlock.COLOR);
    }
}
