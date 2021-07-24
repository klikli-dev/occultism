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

import com.github.klikli_dev.occultism.common.tile.SacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.CollisionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class SacrificialBowlBlock extends Block {
    //region Fields
    private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 2.3, 12);
    //endregion Fields

    //region Initialization
    public SacrificialBowlBlock(Properties properties) {
        super(properties);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile != null){
                StorageUtil.dropInventoryItems(tile);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, Player player,
                                             InteractionHand hand, BlockRayTraceResult hit) {
        if (!level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            SacrificialBowlBlockEntity bowl = (SacrificialBowlBlockEntity) level.getBlockEntity(pos);
            bowl.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).ifPresent(handler -> {
                if (!player.isShiftKeyDown()) {
                    ItemStack itemStack = handler.getItem(0);
                    if (itemStack.isEmpty()) {
                        //if there is nothing in the bowl, put the hand held item in
                        player.setItemInHand(hand, handler.insertItem(0, heldItem, false));
                        level.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                    }
                    else {
                        //otherwise take out the item.
                        if (heldItem.isEmpty()) {
                            //place it in the hand if possible
                            player.setItemInHand(hand, handler.extractItem(0, 64, false));
                        }
                        else {
                            //and if not, just put it in the inventory
                            ItemHandlerHelper.giveItemToPlayer(player, handler.extractItem(0, 64, false));
                        }
                        level.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);
                    }
                    bowl.markDirty();
                }
            });
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, BlockGetter level) {
        return OccultismTiles.SACRIFICIAL_BOWL.get().create();
    }
    //endregion Overrides
}
