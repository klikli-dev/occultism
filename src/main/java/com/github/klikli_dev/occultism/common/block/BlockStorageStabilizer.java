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

package com.github.klikli_dev.occultism.common.block;

import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStorageStabilizer extends Block {

    //region Fields
    private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0, 0, 0, 0.3, 1, 1);
    private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.7, 0, 0, 1, 1, 1);

    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0, 0, 0.7, 1, 1, 1);
    private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 0.3);

    private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.3, 1);
    private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0, 0.7, 0, 1, 1, 1);

    //endregion Fields

    //region Initialization
    public BlockStorageStabilizer(String name) {
        super(Material.ROCK);

        BlockRegistry.registerBlock(this, name, Material.ROCK, SoundType.STONE, 1.5f, 30);
    }
    //endregion Initialization


    //region Overrides
    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DirectionalBlock.FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(DirectionalBlock.FACING).getIndex();
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        switch (state.getValue(DirectionalBlock.FACING)) {
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case NORTH:
                return NORTH_AABB;
            case DOWN:
                return DOWN_AABB;
            case UP:
            default:
                return UP_AABB;
        }
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(DirectionalBlock.FACING);

        //storage controller actually wants stabilizers to point at one block above it, so unless we are on y axis we trace one below
        BlockPos min = facing != Direction.DOWN && facing != Direction.UP ? pos.down() : pos;
        //trace a straight line for the possible controller positions
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(min,
                min.offset(facing, TileEntityStorageController.MAX_STABILIZER_DISTANCE));

        //we do not use an actual trace, because players might have put a block inbetween controller and stabilizer
        for (BlockPos block : blocks) {
            TileEntity tileEntity = world.getTileEntity(block);
            if (tileEntity instanceof TileEntityStorageController) {
                TileEntityStorageController controller = (TileEntityStorageController) tileEntity;
                controller.updateStabilizers(); //force controller to re-check available stabilizers.
            }
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY,
                                           float hitZ, int meta, LivingEntity placer) {
        //place on the face the placer looks at, this is more comfortable than using entity facing.
        RayTraceResult result = placer.rayTrace(10, 1.0f);
        return this.getDefaultState().withProperty(DirectionalBlock.FACING, result.sideHit);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer,
                                ItemStack stack) {

        Direction facing = state.getValue(DirectionalBlock.FACING);

        //storage controller actually wants stabilizers to point at one block above it, so unless we are on y axis we trace one below
        BlockPos min = facing != Direction.DOWN && facing != Direction.UP ? pos.down() : pos;
        //trace a straight line for the possible controller positions
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(min,
                min.offset(facing, TileEntityStorageController.MAX_STABILIZER_DISTANCE));

        //we are also using the fake trace here, because players may have placed a block on this trace line.
        //The trace line is actually below the line the controller uses to check for valid stabilizers.
        //This is due to the stabilizers aiming for one block above the controller.
        for (BlockPos block : blocks) {
            TileEntity tileEntity = world.getTileEntity(block);
            if (tileEntity instanceof TileEntityStorageController) {
                TileEntityStorageController controller = (TileEntityStorageController) tileEntity;
                controller.updateStabilizers(); //force controller to re-check available stabilizers.
            }
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DirectionalBlock.FACING);
    }
    //endregion Overrides

}


