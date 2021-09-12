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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;

public class UndergroundGroveDecorator extends CaveDecorator {

    //region Initialization
    public UndergroundGroveDecorator() {
        super(Blocks.GRASS_BLOCK.defaultBlockState(), null, OccultismBlocks.OTHERSTONE_NATURAL.get().defaultBlockState());

    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finalFloorPass(WorldGenLevel seedReader, ChunkGenerator generator, Random rand,
                               BlockPos pos) {
        if (seedReader.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK &&
                rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.grassChance.get())
            seedReader.setBlock(pos.above(), Blocks.GRASS.defaultBlockState(), 2);

        if (rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.treeChance.get()) {
            ConfiguredFeature<TreeConfiguration, ?> treeFeature = WorldGenHandler.OTHERWORLD_TREE_NATURAL;
            treeFeature.place(seedReader, generator, rand, pos.above());
        }
    }

    @Override
    public void finalCeilingPass(WorldGenLevel seedReader, ChunkGenerator generator, Random rand,
                                 BlockPos pos) {
        if (rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.ceilingLightChance.get()) {
            seedReader.setBlock(pos, Blocks.GLOWSTONE.defaultBlockState(), 2);
        }
        super.finalCeilingPass(seedReader, generator, rand, pos);
    }

    @Override
    public void finalWallPass(WorldGenLevel seedReader, ChunkGenerator generator, Random rand,
                              BlockPos pos) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offset = pos.relative(facing);
            BlockPos up = offset.above();
            if (this.isCeiling(seedReader, up, seedReader.getBlockState(up)) &&
                    rand.nextFloat() < Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.vineChance.get()) {
                BlockState stateAt = seedReader.getBlockState(offset);
                boolean spawnedVine = false;
                while (stateAt.isAir() && offset.getY() > 0) {
                    seedReader.setBlock(offset,
                            Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(facing.getOpposite()), true),
                            2);
                    offset = offset.below();
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
