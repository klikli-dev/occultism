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

package com.github.klikli_dev.occultism.common.block.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class StableWormholeBlock extends Block {

    //region Fields
    public static final Property<Boolean> LINKED = BooleanProperty.create("linked");
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
            ImmutableMap.<Direction, VoxelShape>builder()
                    .put(Direction.EAST, VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 0.0625, 1, 1)))
                    .put(Direction.WEST, VoxelShapes.create(new AxisAlignedBB(0.9375, 0, 0, 1, 1, 1)))
                    .put(Direction.NORTH, VoxelShapes.create(new AxisAlignedBB(0, 0, 0.9375, 1, 1, 1)))
                    .put(Direction.SOUTH, VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 1, 0.0625)))
                    .put(Direction.UP, VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1)))
                    .put(Direction.DOWN, VoxelShapes.create(new AxisAlignedBB(0, 0.9375, 0, 1, 1, 1))).build());

    //endregion Fields
    //region Initialization
    public StableWormholeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(LINKED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(BlockStateProperties.FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                                        ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //TODO: verify this works the way we want, otherwise find out how to raytrace from player eyes.
        return this.getDefaultState().with(BlockStateProperties.FACING, context.getFace());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
                                ItemStack stack) {
        //TODO: schedule a client side render update if still necessary
        //Occultism.proxy.scheduleDelayedTask(worldIn, () -> worldIn.markBlockRangeForRenderUpdate(pos, pos), 1000);

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);

        TileEntity tileentity = worldIn.getTileEntity(pos);
        //TODO: Enable once tile entity is ready
        //        if (tileentity instanceof TileEntityStableWormhole) {
        //            CompoundNBT nbttagcompound = tileentity.writeToNBT(new CompoundNBT());
        //            if (!nbttagcompound.isEmpty()) {
        //                itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
        //            }
        //        }

        return itemstack;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LINKED, BlockStateProperties.FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        //TODO: return true once tile entity is ready
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        //TODO: return tile entity once ready
        return null;
    }

    @Override
    public BlockState getExtendedState(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        boolean linked = false;
        //TODO: enable once TileEntityStableWormhole is ready
        //        if (tileEntity instanceof TileEntityStableWormhole)
        //            linked = ((TileEntityStableWormhole) tileEntity).getLinkedStorageControllerPosition() != null;
        return state.with(LINKED, linked);
    }
    //endregion Overrides


    //region Methods
    //endregion Methods
}


