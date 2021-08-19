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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import com.github.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Based on https://github.com/McJty/YouTubeModding14
 */
public class StandardBlockStateProvider extends BlockStateProvider {

    //region Initialization
    public StandardBlockStateProvider(DataGenerator gen,
                                      ExistingFileHelper exFileHelper) {
        super(gen, Occultism.MODID, exFileHelper);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void registerStatesAndModels() {
        //Generate blockstates for the glyphs
        OccultismBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(block -> block instanceof ChalkGlyphBlock)
                .forEach(this::generateGlyphBlockState);
        this.simpleBlock(OccultismBlocks.STORAGE_CONTROLLER.get(),
                this.models().getExistingFile(this.modLoc("block/storage_controller")));
        this.models().withExistingParent("item/storage_controller", this.modLoc("block/storage_controller"));
        this.generateStableWormholeState(OccultismBlocks.STABLE_WORMHOLE.get());
        this.directionalBlock(OccultismBlocks.STORAGE_STABILIZER_TIER1.get(),
                this.models().getExistingFile(this.modLoc("block/storage_stabilizer_tier1")));
        this.directionalBlock(OccultismBlocks.STORAGE_STABILIZER_TIER2.get(),
                this.models().getExistingFile(this.modLoc("block/storage_stabilizer_tier2")));
        this.directionalBlock(OccultismBlocks.STORAGE_STABILIZER_TIER3.get(),
                this.models().getExistingFile(this.modLoc("block/storage_stabilizer_tier3")));
        this.directionalBlock(OccultismBlocks.STORAGE_STABILIZER_TIER4.get(),
                this.models().getExistingFile(this.modLoc("block/storage_stabilizer_tier4")));
    }
    //endregion Overrides

    //region Methods

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
    //endregion Methods
}
