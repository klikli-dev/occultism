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

import com.klikli_dev.occultism.common.blockentity.RitualCatcherBlockEntity;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RitualCatcherBlock extends OtherstoneFrameBlock implements EntityBlock {

    public static final MapCodec<RitualCatcherBlock> CODEC = simpleCodec(RitualCatcherBlock::new);


    public RitualCatcherBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any());
    }

    @Override
    protected MapCodec<? extends OtherstoneFrameBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState pState, @NotNull PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            RitualCatcherBlockEntity catcher = (RitualCatcherBlockEntity) pLevel.getBlockEntity(pPos);
            var handler = catcher.itemStackHandler;
            if (!pPlayer.isShiftKeyDown()) {
                ItemStack itemStack = handler.getResource(0).toStack();
                if (!itemStack.isEmpty()) {
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
                catcher.setChanged();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return OccultismBlockEntities.RITUAL_CATCHER.get().create(blockPos, blockState);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos, Direction direction) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        return (blockentity instanceof RitualCatcherBlockEntity be) ?
                be.itemStackHandler.getResource(0).toStack().isEmpty() ? 0 : 15
                : 0;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }
}
