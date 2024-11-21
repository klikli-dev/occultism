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

package com.klikli_dev.occultism.common.level.cave;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class UndergroundGroveDecorator extends CaveDecorator {

    public static final ResourceLocation OTHERWORLD_TREE_NATURAL = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "otherworld_tree_natural");

    public UndergroundGroveDecorator() {
        super(Blocks.GRASS_BLOCK.defaultBlockState(), null, OccultismBlocks.OTHERSTONE_NATURAL.get().defaultBlockState());

    }

    @Override
    public void finalFloorPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                               BlockPos pos, MultiChunkFeatureConfig config) {
        if (seedReader.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK) {
            if (rand.nextFloat() < config.grassChance) {
                seedReader.setBlock(pos.above(), Blocks.SHORT_GRASS.defaultBlockState(), 2);
            } else if (rand.nextFloat() < config.flowerChance) {
                seedReader.setBlock(pos.above(), OccultismBlocks.OTHERFLOWER_NATURAL.get().defaultBlockState(), 2);
            }
        }

        if (rand.nextFloat() < config.treeChance) {
            config.otherworldTreeFeature.value().place(seedReader, generator, rand, pos.above());
        }
    }

    @Override
    public void finalCeilingPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                                 BlockPos pos, MultiChunkFeatureConfig config) {
        if (rand.nextFloat() < config.ceilingLightChance) {
            seedReader.setBlock(pos, Blocks.GLOWSTONE.defaultBlockState(), 2);
        }
        super.finalCeilingPass(seedReader, generator, rand, pos, config);
    }

    @Override
    public void finalWallPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                              BlockPos pos, MultiChunkFeatureConfig config) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offset = pos.relative(facing);
            BlockPos up = offset.above();
            if (this.isCeiling(seedReader, up, seedReader.getBlockState(up)) &&
                    rand.nextFloat() < config.vineChance) {
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
}
