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

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CaveDecorator implements ICaveDecorator {

    //region Fields
    public IBlockState floorState;
    public IBlockState ceilingState;
    public IBlockState wallState;
    //endregion Fields

    //region Initialization

    public CaveDecorator(IBlockState floorState, IBlockState ceilingState, IBlockState wallState) {
        this.floorState = floorState;
        this.ceilingState = ceilingState;
        this.wallState = wallState;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void finalPass(World world, CaveDecoratordata data) {
        data.floorBlocks.forEach(blockPos -> this.finalFloorPass(world, blockPos));
        data.ceilingBlocks.forEach(blockPos -> this.finalCeilingPass(world, blockPos));
        data.wallBlocks.keySet().forEach(blockPos -> this.finalWallPass(world, blockPos));
        data.insideBlocks.forEach(blockPos -> this.finalInsidePass(world, blockPos));
    }

    @Override
    public void fill(World world, BlockPos pos, CaveDecoratordata data) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlockHardness(world, pos) == -1 || world.canBlockSeeSky(pos))
            return;

        if (this.isFloor(world, pos, state)) {
            data.floorBlocks.add(pos);
            this.fillFloor(world, pos, state);
        }
        else if (this.isCeiling(world, pos, state)) {
            data.ceilingBlocks.add(pos);
            this.fillCeiling(world, pos, state);
        }
        else if (this.isWall(world, pos, state)) {
            data.wallBlocks.put(pos, this.getBorderFacing(world, pos));
            this.fillWall(world, pos, state);
        }
        else if (this.isInside(state)) {
            data.insideBlocks.add(pos);
            this.fillInside(world, pos, state);
        }
    }
    //endregion Overrides

    //region Methods
    public void fillFloor(World world, BlockPos pos, IBlockState state) {
        if (this.floorState != null) {
            world.setBlockState(pos, this.floorState, 2);
        }
    }

    public void fillCeiling(World world, BlockPos pos, IBlockState state) {
        if (this.ceilingState != null)
            world.setBlockState(pos, this.ceilingState, 2);
    }

    public void fillWall(World world, BlockPos pos, IBlockState state) {
        if (this.wallState != null)
            world.setBlockState(pos, this.wallState, 2);
    }

    public void fillInside(World world, BlockPos pos, IBlockState state) {
        //world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

    public void finalFloorPass(World world, BlockPos pos) {
    }

    public void finalCeilingPass(World world, BlockPos pos) {
    }

    public void finalWallPass(World world, BlockPos pos) {
    }

    public void finalInsidePass(World world, BlockPos pos) {
    }

    public boolean isFloor(World world, BlockPos pos, IBlockState state) {
        if (!state.isFullBlock() || !state.isOpaqueCube())
            return false;

        BlockPos upPos = pos.up();
        return world.isAirBlock(upPos) || world.getBlockState(upPos).getBlock().isReplaceable(world, upPos);
    }

    public boolean isCeiling(World world, BlockPos pos, IBlockState state) {
        if (!state.isFullBlock() || !state.isOpaqueCube())
            return false;

        BlockPos downPos = pos.down();
        return world.isAirBlock(downPos); // || world.getBlockState(downPos).getBlock().isReplaceable(world, downPos);
    }

    public boolean isWall(World world, BlockPos pos, IBlockState state) {
        if (!state.isFullBlock() || !state.isOpaqueCube() || !this.isStone(state))
            return false;

        return this.isBorder(world, pos);
    }

    public EnumFacing getBorderFacing(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos offsetPos = pos.offset(facing);
            IBlockState stateAt = world.getBlockState(offsetPos);

            if (state != stateAt && world.isAirBlock(offsetPos) || stateAt.getBlock().isReplaceable(world, offsetPos))
                return facing;
        }

        return null;
    }

    public boolean isBorder(World world, BlockPos pos) {
        return this.getBorderFacing(world, pos) != null;
    }

    public boolean isInside(IBlockState state) {
        return this.isStone(state);
    }

    public boolean isStone(IBlockState state) {
        if (state != null) {
            if (state.getBlock() == Blocks.STONE) {
                return state.getValue(BlockStone.VARIANT).isNatural();
            }
        }
        return false;
    }
    //endregion Methods

}
