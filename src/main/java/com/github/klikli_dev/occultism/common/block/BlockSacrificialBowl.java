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

import com.github.klikli_dev.occultism.common.tile.TileEntitySacrificialBowl;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class BlockSacrificialBowl extends Block {
    //region Fields
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.20, 1);
    //endregion Fields

    //region Initialization
    public BlockSacrificialBowl() {
        super(Material.ROCK);
        BlockRegistry.registerBlock(this, "sacrificial_bowl", Material.ROCK, SoundType.STONE, 1.5f, 30);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, BlockState state) {
        TileEntitySacrificialBowl tile = (TileEntitySacrificialBowl) world.getTileEntity(pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand,
                                    Direction side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            TileEntitySacrificialBowl tileEntitySacrificialBowl = (TileEntitySacrificialBowl) world.getTileEntity(pos);
            IItemHandler itemHandler = tileEntitySacrificialBowl
                                               .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (!player.isSneaking()) {
                ItemStack itemStack = itemHandler.getStackInSlot(0);
                if (itemStack.isEmpty()) {
                    //if there is nothing in the bowl, put the hand held item in
                    player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                }
                else {
                    //otherwise take out the item.

                    if (heldItem.isEmpty()) {
                        //place it in the hand if possible
                        player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                    }
                    else {
                        //and if not, just put it in the inventory
                        ItemHandlerHelper.giveItemToPlayer(player, itemHandler.extractItem(0, 64, false));

                    }
                }
                tileEntitySacrificialBowl.markDirty();
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        return new TileEntitySacrificialBowl();
    }

    //endregion Overrides
}
