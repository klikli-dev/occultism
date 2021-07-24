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

package com.github.klikli_dev.occultism.common.level.cave;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.level.WorldGenHandler;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.level.ISeedReader;
import net.minecraft.level.gen.ChunkGenerator;
import net.minecraft.level.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.level.gen.feature.ConfiguredFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.Random;

public class UndergroundGroveDecorator extends CaveDecorator {

    //region Initialization
    public UndergroundGroveDecorator() {
        super(Blocks.GRASS_BLOCK.getDefaultState(), null, OccultismBlocks.OTHERSTONE_NATURAL.get().getDefaultState());

    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finalFloorPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                               BlockPos pos) {
        if (seedReader.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK &&
            rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.grassChance.get())
            seedReader.setBlockState(pos.above(), Blocks.GRASS.getDefaultState(), 2);

        if (rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.treeChance.get()) {
            ConfiguredFeature<BaseTreeFeatureConfig, ?> treeFeature = WorldGenHandler.OTHERWORLD_TREE_NATURAL;
            treeFeature.config.forcePlacement();
            treeFeature.generate(seedReader, generator, rand, pos.up());
        }
    }

    @Override
    public void finalCeilingPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                                 BlockPos pos) {
        if (rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.ceilingLightChance.get()) {
            seedReader.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);
        }
        super.finalCeilingPass(seedReader, generator, rand, pos);
    }

    @Override
    public void finalWallPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                              BlockPos pos) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offset = pos.offset(facing);
            BlockPos up = offset.up();
            if (this.isCeiling(seedReader, up, seedReader.getBlockState(up)) &&
                rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.vineChance.get()) {
                BlockState stateAt = seedReader.getBlockState(offset);
                boolean spawnedVine = false;
                while (stateAt.getBlock().isAir(stateAt, seedReader, offset) && offset.getY() > 0) {
                    seedReader.setBlockState(offset,
                            Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(facing.getOpposite()), true),
                            2);
                    offset = offset.down();
                    stateAt = seedReader.getBlockState(offset);
                    spawnedVine = true;
                }

                if (spawnedVine)
                    return;
            }
        }
    }

    //endregion Overrides
}
