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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.handler.GuiHandler;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStorageController extends Block {
    //region Initialization
    public BlockStorageController() {
        super(Material.ROCK);
        BlockRegistry.registerBlock(this, "storage_controller", Material.ROCK, SoundType.STONE, 5, 100);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityStorageController) {
            ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            itemstack.setTagInfo("BlockEntityTag", tileentity.writeToNBT(new CompoundNBT()));
            spawnAsEntity(worldIn, pos, itemstack);
            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune) {
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand,
                                    Direction facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (!(tileEntity instanceof TileEntityStorageController)) {
                return false;
            }
            player.openGui(Occultism.instance, GuiHandler.GuiID.STORAGE_CONTROLLER.ordinal(), world, pos.getX(),
                    pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityStorageController) {
            CompoundNBT nbttagcompound = tileentity.writeToNBT(new CompoundNBT());
            if (!nbttagcompound.isEmpty()) {
                itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
            }
        }

        return itemstack;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        return new TileEntityStorageController();
    }

    //endregion Overrides
}
