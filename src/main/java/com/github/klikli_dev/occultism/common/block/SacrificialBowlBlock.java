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

import com.github.klikli_dev.occultism.common.tile.SacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            StorageUtil.dropInventoryItems(worldIn.getTileEntity(pos));
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                             Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            SacrificialBowlTileEntity bowl = (SacrificialBowlTileEntity) world.getTileEntity(pos);
            bowl.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).ifPresent(handler -> {
                if (!player.isSneaking()) {
                    ItemStack itemStack = handler.getStackInSlot(0);
                    if (itemStack.isEmpty()) {
                        //if there is nothing in the bowl, put the hand held item in
                        player.setHeldItem(hand, handler.insertItem(0, heldItem, false));
                        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1, 1);
                    }
                    else {
                        //otherwise take out the item.
                        if (heldItem.isEmpty()) {
                            //place it in the hand if possible
                            player.setHeldItem(hand, handler.extractItem(0, 64, false));
                        }
                        else {
                            //and if not, just put it in the inventory
                            ItemHandlerHelper.giveItemToPlayer(player, handler.extractItem(0, 64, false));
                        }
                        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1, 1);
                    }
                    bowl.markDirty();
                }
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return OccultismTiles.SACRIFICIAL_BOWL.get().create();
    }
    //endregion Overrides
}
