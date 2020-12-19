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

package com.github.klikli_dev.occultism.common.world.cave;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public class UndergroundGroveDecorator extends CaveDecorator {

    //region Fields
//    protected final NonNullLazy<ConfiguredFeature<TreeFeatureConfig, ?>> treeFeature =
    //            NonNullLazy.of(() -> Feature.NORMAL_TREE.withConfiguration(
    //                    OtherworldNaturalTree.OTHERWORLD_TREE_NATURAL_CONFIG.get()));
    //endregion Fields

    //region Initialization
    public UndergroundGroveDecorator() {
        super(Blocks.GRASS_BLOCK.getDefaultState(), null, OccultismBlocks.OTHERSTONE_NATURAL.get().getDefaultState());

    }
    //endregion Initialization

    //region Overrides

    @Override
    public void finalFloorPass(IWorld world, ChunkGenerator generator, Random rand,
                               BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK &&
            rand.nextFloat() < Occultism.CONFIG.worldGen.undergroundGroveGen.grassChance.get())
            world.setBlockState(pos.up(), Blocks.GRASS.getDefaultState(), 2);

        if (rand.nextFloat() < Occultism.CONFIG.worldGen.undergroundGroveGen.treeChance.get()) {
            //TODO: Place trees as intended in 1.16 - maybe load configured tree feature from registry
//            this.treeFeature.get().config.forcePlacement();
//            this.treeFeature.get().place(world, generator, rand, pos.up());
        }
    }

    @Override
    public void finalCeilingPass(IWorld world, ChunkGenerator generator, Random rand,
                                 BlockPos pos) {
        if (rand.nextFloat() < Occultism.CONFIG.worldGen.undergroundGroveGen.ceilingLightChance.get()) {
            world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);
        }
        super.finalCeilingPass(world, generator, rand, pos);
    }

    @Override
    public void finalWallPass(IWorld world, ChunkGenerator generator, Random rand,
                              BlockPos pos) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offset = pos.offset(facing);
            BlockPos up = offset.up();
            if (this.isCeiling(world, up, world.getBlockState(up)) &&
                rand.nextFloat() < Occultism.CONFIG.worldGen.undergroundGroveGen.vineChance.get()) {
                BlockState stateAt = world.getBlockState(offset);
                boolean spawnedVine = false;
                while (stateAt.getBlock().isAir(stateAt, world, offset) && offset.getY() > 0) {
                    world.setBlockState(offset,
                            Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(facing.getOpposite()), true),
                            2);
                    offset = offset.down();
                    stateAt = world.getBlockState(offset);
                    spawnedVine = true;
                }

                if (spawnedVine)
                    return;
            }
        }
    }

    //endregion Overrides
}
