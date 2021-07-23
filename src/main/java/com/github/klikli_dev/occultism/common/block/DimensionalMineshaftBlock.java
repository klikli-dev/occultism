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

import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.inventory.container.MenuProvider;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.level.IBlockReader;
import net.minecraft.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class DimensionalMineshaftBlock extends Block {
    //region Fields
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(10, 0, 6, 16, 1, 10),
            Block.makeCuboidShape(0, 0, 6, 6, 1, 10),
            Block.makeCuboidShape(0, 0, 10, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 6),
            Block.makeCuboidShape(10, 1, 6, 15, 2, 10),
            Block.makeCuboidShape(2, 2, 6, 6, 3, 10),
            Block.makeCuboidShape(1, 1, 6, 6, 2, 10),
            Block.makeCuboidShape(10, 2, 6, 14, 3, 10),
            Block.makeCuboidShape(10, 3, 6, 13, 4, 10),
            Block.makeCuboidShape(1, 1, 10, 15, 2, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 2, 6),
            Block.makeCuboidShape(2, 2, 10, 14, 3, 14),
            Block.makeCuboidShape(3, 3, 10, 13, 4, 13),
            Block.makeCuboidShape(3, 3, 3, 13, 4, 6),
            Block.makeCuboidShape(2, 2, 2, 14, 3, 6),
            Block.makeCuboidShape(3, 3, 6, 6, 4, 10),
            Block.makeCuboidShape(6, 0, 6, 10, 3, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    //endregion Fields

    //region Initialization
    public DimensionalMineshaftBlock(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile != null) {
                StorageUtil.dropInventoryItems(tile);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, Player player,
                                             InteractionHand hand, BlockRayTraceResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (BlockEntity instanceof MenuProvider) {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) BlockEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader level) {
        return OccultismTiles.DIMENSIONAL_MINESHAFT.get().create();
    }
    //endregion Overrides
}
