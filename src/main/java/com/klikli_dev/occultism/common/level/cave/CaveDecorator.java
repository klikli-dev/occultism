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

import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

public abstract class CaveDecorator implements ICaveDecorator {

    public BlockState floorState;
    public BlockState ceilingState;
    public BlockState wallState;

    public CaveDecorator(BlockState floorState, BlockState ceilingState, BlockState wallState) {
        this.floorState = floorState;
        this.ceilingState = ceilingState;
        this.wallState = wallState;
    }

    @Override
    public void finalPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                          CaveDecoratordata data, MultiChunkFeatureConfig config) {
        data.floorBlocks.forEach(blockPos -> this.finalFloorPass(seedReader, generator, rand, blockPos, config));
        data.ceilingBlocks.forEach(blockPos -> this.finalCeilingPass(seedReader, generator, rand, blockPos, config));
        data.wallBlocks.keySet().forEach(blockPos -> this.finalWallPass(seedReader, generator, rand, blockPos, config));
        data.insideBlocks.forEach(blockPos -> this.finalInsidePass(seedReader, generator, rand, blockPos, config));
    }

    @Override
    public void fill(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                     BlockPos pos, CaveDecoratordata data, MultiChunkFeatureConfig config) {
        BlockState state = seedReader.getBlockState(pos);
        if (state.getDestroySpeed(seedReader, pos) == -1 || seedReader.canSeeSkyFromBelowWater(pos))
            return;

        if (this.isFloor(seedReader, pos, state)) {
            data.floorBlocks.add(pos);
            this.fillFloor(seedReader, generator, rand, pos, state, config);
        } else if (this.isCeiling(seedReader, pos, state)) {
            data.ceilingBlocks.add(pos);
            this.fillCeiling(seedReader, generator, rand, pos, state, config);
        } else if (this.isWall(seedReader, pos, state)) {
            data.wallBlocks.put(pos, this.getBorderDirection(seedReader, pos));
            this.fillWall(seedReader, generator, rand, pos, state, config);
        } else if (this.isInside(state)) {
            data.insideBlocks.add(pos);
            this.fillInside(seedReader, generator, rand, pos, state, config);
        }
    }

    public void fillFloor(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                          BlockPos pos, BlockState state, MultiChunkFeatureConfig config) {
        if (this.floorState != null) {
            seedReader.setBlock(pos, this.floorState, 2);
        }
    }

    public void fillCeiling(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                            BlockPos pos, BlockState state, MultiChunkFeatureConfig config) {
        if (this.ceilingState != null)
            seedReader.setBlock(pos, this.ceilingState, 2);
    }

    public void fillWall(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                         BlockPos pos, BlockState state, MultiChunkFeatureConfig config) {
        if (this.wallState != null)
            seedReader.setBlock(pos, this.wallState, 2);
    }

    public void fillInside(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                           BlockPos pos, BlockState state, MultiChunkFeatureConfig config) {
        //level.setBlockState(pos, Blocks.AIR.defaultBlockState(), 2);
    }

    public void finalFloorPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                               BlockPos pos, MultiChunkFeatureConfig config) {
    }

    public void finalCeilingPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                                 BlockPos pos, MultiChunkFeatureConfig config) {
    }

    public void finalWallPass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                              BlockPos pos, MultiChunkFeatureConfig config) {
    }

    public void finalInsidePass(WorldGenLevel seedReader, ChunkGenerator generator, RandomSource rand,
                                BlockPos pos, MultiChunkFeatureConfig config) {
    }

    public boolean isFloor(WorldGenLevel seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos))
            return false;

        BlockPos upPos = pos.above();
        return seedReader.isEmptyBlock(upPos) || seedReader.getBlockState(upPos).canBeReplaced();
    }

    public boolean isCeiling(WorldGenLevel seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos))
            return false;

        BlockPos downPos = pos.below();
        return seedReader.isEmptyBlock(downPos); // || level.getBlockState(downPos).getBlock().isReplaceable(level, downPos);
    }

    public boolean isWall(WorldGenLevel seedReader, BlockPos pos, BlockState state) {
        if (!state.isSolidRender(seedReader, pos) || !this.isStone(state))
            return false;

        return this.isBorder(seedReader, pos);
    }

    public Direction getBorderDirection(WorldGenLevel seedReader, BlockPos pos) {
        BlockState state = seedReader.getBlockState(pos);
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.relative(facing);
            BlockState stateAt = seedReader.getBlockState(offsetPos);

            if (state != stateAt && seedReader.isEmptyBlock(offsetPos) || stateAt.canBeReplaced())
                return facing;
        }

        return null;
    }

    public boolean isBorder(WorldGenLevel seedReader, BlockPos pos) {
        return this.getBorderDirection(seedReader, pos) != null;
    }

    public boolean isInside(BlockState state) {
        return this.isStone(state);
    }

    public boolean isStone(BlockState state) {
        if (state != null) {
            return state.is(OccultismTags.Blocks.CAVE_WALL_BLOCKS);
        }
        return false;
    }

}
