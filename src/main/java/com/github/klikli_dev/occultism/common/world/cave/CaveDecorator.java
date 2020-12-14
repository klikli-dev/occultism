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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

import java.util.Random;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public abstract class CaveDecorator implements ICaveDecorator {

    //region Fields
    public static final ITag<Block> CAVE_WALL_BLOCKS = new BlockTags.Wrapper(modLoc("cave_wall_blocks"));

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
    public void finalPass(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                          CaveDecoratordata data) {
        data.floorBlocks.forEach(blockPos -> this.finalFloorPass(world, generator, rand, blockPos));
        data.ceilingBlocks.forEach(blockPos -> this.finalCeilingPass(world, generator, rand, blockPos));
        data.wallBlocks.keySet().forEach(blockPos -> this.finalWallPass(world, generator, rand, blockPos));
        data.insideBlocks.forEach(blockPos -> this.finalInsidePass(world, generator, rand, blockPos));
    }

    @Override
    public void fill(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                     BlockPos pos, CaveDecoratordata data) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlockHardness(world, pos) == -1 || world.canBlockSeeSky(pos))
            return;

        if (this.isFloor(world, pos, state)) {
            data.floorBlocks.add(pos);
            this.fillFloor(world, generator, rand, pos, state);
        }
        else if (this.isCeiling(world, pos, state)) {
            data.ceilingBlocks.add(pos);
            this.fillCeiling(world, generator, rand, pos, state);
        }
        else if (this.isWall(world, pos, state)) {
            data.wallBlocks.put(pos, this.getBorderDirection(world, pos));
            this.fillWall(world, generator, rand, pos, state);
        }
        else if (this.isInside(state)) {
            data.insideBlocks.add(pos);
            this.fillInside(world, generator, rand, pos, state);
        }
    }
    //endregion Overrides

    //region Methods
    public void fillFloor(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                          BlockPos pos, BlockState state) {
        if (this.floorState != null) {
            world.setBlockState(pos, this.floorState, 2);
        }
    }

    public void fillCeiling(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                            BlockPos pos, BlockState state) {
        if (this.ceilingState != null)
            world.setBlockState(pos, this.ceilingState, 2);
    }

    public void fillWall(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                         BlockPos pos, BlockState state) {
        if (this.wallState != null)
            world.setBlockState(pos, this.wallState, 2);
    }

    public void fillInside(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                           BlockPos pos, BlockState state) {
        //world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

    public void finalFloorPass(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                               BlockPos pos) {
    }

    public void finalCeilingPass(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                                 BlockPos pos) {
    }

    public void finalWallPass(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                              BlockPos pos) {
    }

    public void finalInsidePass(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                                BlockPos pos) {
    }

    public boolean isFloor(IWorld world, BlockPos pos, BlockState state) {
        if (!state.isOpaqueCube(world, pos))
            return false;

        BlockPos upPos = pos.up();
        return world.isAirBlock(upPos) || world.getBlockState(upPos).getMaterial().isReplaceable();
    }

    public boolean isCeiling(IWorld world, BlockPos pos, BlockState state) {
        if (!state.isOpaqueCube(world, pos))
            return false;

        BlockPos downPos = pos.down();
        return world.isAirBlock(downPos); // || world.getBlockState(downPos).getBlock().isReplaceable(world, downPos);
    }

    public boolean isWall(IWorld world, BlockPos pos, BlockState state) {
        if (!state.isOpaqueCube(world, pos) || !this.isStone(state))
            return false;

        return this.isBorder(world, pos);
    }

    public Direction getBorderDirection(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.offset(facing);
            BlockState stateAt = world.getBlockState(offsetPos);

            if (state != stateAt && world.isAirBlock(offsetPos) || stateAt.getMaterial().isReplaceable())
                return facing;
        }

        return null;
    }

    public boolean isBorder(IWorld world, BlockPos pos) {
        return this.getBorderDirection(world, pos) != null;
    }

    public boolean isInside(BlockState state) {
        return this.isStone(state);
    }

    public boolean isStone(BlockState state) {
        if (state != null) {
            return state.getBlock().isIn(CAVE_WALL_BLOCKS);
        }
        return false;
    }
    //endregion Methods

}
