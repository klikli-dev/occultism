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

import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.klikli_dev.occultism.common.item.tool.GuideBookItem;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class SacrificialBowlBlock extends DirectionalBlock implements EntityBlock {

    public static final MapCodec<SacrificialBowlBlock> CODEC = simpleCodec(SacrificialBowlBlock::new);

    private static final DirectionalBlockShape SHAPE = new DirectionalBlockShape(10, 10, 6f, 8, 2f);

    public SacrificialBowlBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            SacrificialBowlBlockEntity bowl = (SacrificialBowlBlockEntity) pLevel.getBlockEntity(pPos);
            var handler = bowl.itemStackHandler;
            if (!pPlayer.isShiftKeyDown()) {
                ItemStack itemStack = handler.getResource(0).toStack();
                if (itemStack.isEmpty() && !heldItem.isEmpty()) {
                    //if there is nothing in the bowl, put the hand held item in
                    try (var tx = Transaction.openRoot()) {
                        int inserted = handler.insert(0, ItemResource.of(heldItem), 1, tx);
                        if (inserted > 0) {
                            heldItem.shrink(inserted);
                            tx.commit();
                        }
                    }
                    pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                } else {
                    //otherwise take out the item.
                    try (var tx = Transaction.openRoot()) {
                        int extractedCount = handler.extract(0, ItemResource.of(itemStack), 64, tx);
                        if (extractedCount > 0) {
                            ItemStack extracted = itemStack.copyWithCount(extractedCount);
                            if (heldItem.isEmpty()) {
                                //place it in the hand if possible
                                pPlayer.setItemInHand(pHand, extracted);
                                tx.commit();
                            } else {
                                //and if not, just put it in the inventory
                                ItemTransferUtil.giveItemToPlayer(pPlayer, extracted, tx);
                            }
                            pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);
                        }
                    }
                }
                bowl.setChanged();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean isMoving) {
        if (!level.isClientSide() && level.getBlockState(pos).is(this) && level.hasNeighborSignal(pos)
                && level.getBlockEntity(pos) instanceof SacrificialBowlBlockEntity bowl
                && level.getBlockEntity(pos.below()) instanceof ChiseledBookShelfBlockEntity bookShelf
                && bowl.itemStackHandler.getResource(0).toStack().getItem() instanceof GuideBookItem) {
            for (int i = 0; i < 6; i++) {
                if (bookShelf.getItem(i).getItem() instanceof BookOfBindingItem book) {
                    bookShelf.setItem(i, BoundBookOfBindingRecipe.bookshelfCraft(book.getDefaultInstance(), bowl.itemStackHandler.getResource(0).toStack()));
                }
            }
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.getShape(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace();
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(direction.getOpposite()));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction
                ? this.defaultBlockState().setValue(FACING, direction.getOpposite())
                : this.defaultBlockState().setValue(FACING, direction);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return OccultismBlockEntities.SACRIFICIAL_BOWL.get().create(blockPos, blockState);
    }
}
