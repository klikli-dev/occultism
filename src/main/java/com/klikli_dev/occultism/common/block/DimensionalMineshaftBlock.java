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

package com.klikli_dev.occultism.common.block;

import com.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class DimensionalMineshaftBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(10, 0, 6, 16, 1, 10),
            Block.box(0, 0, 6, 6, 1, 10),
            Block.box(0, 0, 10, 16, 1, 16),
            Block.box(0, 0, 0, 16, 1, 6),
            Block.box(10, 1, 6, 15, 2, 10),
            Block.box(2, 2, 6, 6, 3, 10),
            Block.box(1, 1, 6, 6, 2, 10),
            Block.box(10, 2, 6, 14, 3, 10),
            Block.box(10, 3, 6, 13, 4, 10),
            Block.box(1, 1, 10, 15, 2, 15),
            Block.box(1, 1, 1, 15, 2, 6),
            Block.box(2, 2, 10, 14, 3, 14),
            Block.box(3, 3, 10, 13, 4, 13),
            Block.box(3, 3, 3, 13, 4, 6),
            Block.box(2, 2, 2, 14, 3, 6),
            Block.box(3, 3, 6, 6, 4, 10),
            Block.box(6, 0, 6, 10, 3, 10)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    public DimensionalMineshaftBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity != null) {
                StorageUtil.dropInventoryItems(blockEntity);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MenuProvider menu && pPlayer instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(menu, pPos);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get().create(blockPos, blockState);

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (l, p, s, be) -> {
            if (be instanceof DimensionalMineshaftBlockEntity shaft)
                shaft.tick();
        };
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        return (blockentity instanceof DimensionalMineshaftBlockEntity mineshaft)? mineshaft.getRedstoneSignal() : 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }
}
