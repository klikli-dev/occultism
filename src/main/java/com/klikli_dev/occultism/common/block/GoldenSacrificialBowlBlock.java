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

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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

public class GoldenSacrificialBowlBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 2.3, 12);
    private static final VoxelShape SHAPE_TROPHY = Stream.of(
            Block.box(6, 0, 6, 10, 1, 10),
            Block.box(5, 0, 7, 11, 1, 9),
            Block.box(7, 0, 5, 9, 1, 11),
            Block.box(7.5, 1, 7.5, 8.5, 6, 8.5),
            Block.box(6, 6, 6, 10, 7, 10),
            Block.box(5, 6, 7, 11, 7, 9),
            Block.box(7, 6, 5, 9, 7, 11),
            Block.box(5, 7, 5, 11, 10, 11),
            Block.box(4, 7, 7, 12, 10, 9),
            Block.box(7, 7, 4, 9, 10, 12),
            Block.box(5, 10, 4, 11, 15, 12),
            Block.box(4, 10, 5, 12, 15, 11),
            Block.box(3, 10, 6, 13, 15, 10),
            Block.box(6, 10, 3, 10, 15, 13)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    public GoldenSacrificialBowlBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
        level.scheduleTick(pos, this, 0);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity != null) {
                GoldenSacrificialBowlBlockEntity bowl = (GoldenSacrificialBowlBlockEntity) blockEntity;
                bowl.stopRitual(false); //if block changed/was destroyed, interrupt the ritual.
                StorageUtil.dropInventoryItems(bowl);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof GoldenSacrificialBowlBlockEntity bowl) {
            return bowl.activate(pLevel, pPos, pPlayer, pHand,
                    pHitResult.getDirection()) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getBlock().equals(OccultismBlocks.ELDRITCH_CHALICE.get()))
            return SHAPE_TROPHY;

        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        BlockEntity blockEntityAbove = pBlockAccess.getBlockEntity(pPos.above(3));
        if (blockEntityAbove instanceof GoldenSacrificialBowlBlockEntity bowl) {
            return bowl.getSignal();
        }
        BlockEntity blockEntity = pBlockAccess.getBlockEntity(pPos);
        if (blockEntity instanceof GoldenSacrificialBowlBlockEntity bowl) {
            return bowl.getSignal();
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.GOLDEN_SACRIFICIAL_BOWL.get().create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return (l, p, s, be) -> {
            if (be instanceof GoldenSacrificialBowlBlockEntity bowl)
                bowl.tick();
        };
    }
}
