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

import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public abstract class CaveDecorator implements ICaveDecorator {
    //region Fields
    public BlockState floorState;
    public BlockState ceilingState;
    public BlockState wallState;
    //endregion Fields

    //region Initialization

    public CaveDecorator(BlockState floorState, BlockState ceilingState, BlockState wallState) {
        this.floorState = floorState;
        this.ceilingState = ceilingState;
        this.wallState = wallState;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void finalPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                          CaveDecoratordata data) {
        data.floorBlocks.forEach(blockPos -> this.finalFloorPass(seedReader, generator, rand, blockPos));
        data.ceilingBlocks.forEach(blockPos -> this.finalCeilingPass(seedReader, generator, rand, blockPos));
        data.wallBlocks.keySet().forEach(blockPos -> this.finalWallPass(seedReader, generator, rand, blockPos));
        data.insideBlocks.forEach(blockPos -> this.finalInsidePass(seedReader, generator, rand, blockPos));
    }

    @Override
    public void fill(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                     BlockPos pos, CaveDecoratordata data) {
        BlockState state = seedReader.getBlockState(pos);
        if (state.getDestroySpeed(seedReader, pos) == -1 || seedReader.canSeeSkyFromBelowWater(pos))
            return;

        if (this.isFloor(seedReader, pos, state)) {
            data.floorBlocks.add(pos);
            this.fillFloor(seedReader, generator, rand, pos, state);
        } else if (this.isCeiling(seedReader, pos, state)) {
            data.ceilingBlocks.add(pos);
            this.fillCeiling(seedReader, generator, rand, pos, state);
        } else if (this.isWall(seedReader, pos, state)) {
            data.wallBlocks.put(pos, this.getBorderDirection(seedReader, pos));
            this.fillWall(seedReader, generator, rand, pos, state);
        } else if (this.isInside(state)) {
            data.insideBlocks.add(pos);
            this.fillInside(seedReader, generator, rand, pos, state);
        }
    }
    //endregion Overrides

    //region Methods
    public void fillFloor(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                          BlockPos pos, BlockState state) {
        if (this.floorState != null) {
            seedReader.setBlock(pos, this.floorState, 2);
        }
    }

    public void fillCeiling(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                            BlockPos pos, BlockState state) {
        if (this.ceilingState != null)
            seedReader.setBlock(pos, this.ceilingState, 2);
    }

    public void fillWall(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                         BlockPos pos, BlockState state) {
        if (this.wallState != null)
            seedReader.setBlock(pos, this.wallState, 2);
    }

    public void fillInside(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                           BlockPos pos, BlockState state) {
        //world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

    public void finalFloorPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                               BlockPos pos) {
    }

    public void finalCeilingPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                                 BlockPos pos) {
    }

    public void finalWallPass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                              BlockPos pos) {
    }

    public void finalInsidePass(ISeedReader seedReader, ChunkGenerator generator, Random rand,
                                BlockPos pos) {
    }

    public boolean isFloor(ISeedReader seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos))
            return false;

        BlockPos upPos = pos.above();
        return seedReader.isEmptyBlock(upPos) || seedReader.getBlockState(upPos).getMaterial().isReplaceable();
    }

    public boolean isCeiling(ISeedReader seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos))
            return false;

        BlockPos downPos = pos.below();
        return seedReader.isEmptyBlock(downPos); // || world.getBlockState(downPos).getBlock().isReplaceable(world, downPos);
    }

    public boolean isWall(ISeedReader seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos) || !this.isStone(state))
            return false;

        return this.isBorder(seedReader, pos);
    }

    public Direction getBorderDirection(ISeedReader seedReader, BlockPos pos) {
        BlockState state = seedReader.getBlockState(pos);
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.relative(facing);
            BlockState stateAt = seedReader.getBlockState(offsetPos);

            if (state != stateAt && seedReader.isEmptyBlock(offsetPos) || stateAt.getMaterial().isReplaceable())
                return facing;
        }

        return null;
    }

    public boolean isBorder(ISeedReader seedReader, BlockPos pos) {
        return this.getBorderDirection(seedReader, pos) != null;
    }

    public boolean isInside(BlockState state) {
        return this.isStone(state);
    }

    public boolean isStone(BlockState state) {
        if (state != null) {
            return state.getBlock().is(OccultismTags.CAVE_WALL_BLOCKS);
        }
        return false;
    }
    //endregion Methods

}
