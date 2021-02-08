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

package com.github.klikli_dev.occultism.util;

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class TileEntityUtil {
    //region Static Methods

    /**
     * Gets the tile entity from the given global position.
     *
     * @param world the world. Returns null if the dimension is unloaded.
     * @param pos   the global position.
     * @return the tile entity or null.
     */
    public static TileEntity get(World world, GlobalBlockPos pos) {
        if (pos == null)
            return null;

        if (world.getDimensionKey() == pos.getDimensionKey()) {
            return world.getTileEntity(pos.getPos());
        }
        if (world.isRemote) //can only access other dimensions on the server.
            return null;

        World dimensionWorld = ServerLifecycleHooks.getCurrentServer().getWorld(pos.getDimensionKey());
        if (dimensionWorld != null)
            return dimensionWorld.getTileEntity(pos.getPos());

        return null;
    }

    /**
     * Updates the tile entity at the given position (mark dirty & send updates)
     *
     * @param world the world to update
     * @param pos
     */
    public static void updateTile(World world, BlockPos pos) {
        if (world == null || world.isRemote || !world.isBlockLoaded(pos)) {
            return;
        }

        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
        world.markChunkDirty(pos, world.getTileEntity(pos));
    }

    /**
     * Checks all faces of a tile entity for the given capability.
     *
     * @param tileEntity the tile entity to check.
     * @param capability the capability to check for.
     * @return true if the capability is found on any face.
     */
    public static boolean hasCapabilityOnAnySide(TileEntity tileEntity, Capability<?> capability) {
        for (Direction face : Direction.values()) {
            if (tileEntity.getCapability(capability, face).isPresent())
                return true;
        }
        return false;
    }

    /**
     * Creates the item entity with nbt from the tile entity.
     * Default pickup delay is set.
     *
     * @param itemStack  the stack to drop.
     * @param tileEntity the tile entity to get nbt from.
     * @return the item entity.
     */
    public static ItemEntity getDroppedItemWithNbt(ItemStack itemStack, TileEntity tileEntity) {
        CompoundNBT compoundnbt = tileEntity.serializeNBT();
        if (!compoundnbt.isEmpty()) {
            itemStack.setTagInfo("BlockEntityTag", compoundnbt);
        }
        ItemEntity itementity =
                new ItemEntity(tileEntity.getWorld(), tileEntity.getPos().getX(), tileEntity.getPos().getY(),
                        tileEntity.getPos().getZ(), itemStack);
        itementity.setDefaultPickupDelay();
        return itementity;
    }

    /**
     * Handles the common use case of dropping self with tile entity nbt on block change during replace.
     *
     * @param block    the current block.
     * @param state    the old state.
     * @param world    the world
     * @param pos      the position.
     * @param newState the new state
     */
    public static void onBlockChangeDropWithNbt(Block block, BlockState state, World world, BlockPos pos,
                                                BlockState newState) {
        if (state.getBlock() != newState.getBlock()) {
            if (!world.isRemote) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null) {
                    world.addEntity(TileEntityUtil.getDroppedItemWithNbt(new ItemStack(block), tile));
                }
            }
            world.updateComparatorOutputLevel(pos, block);
        }
    }

    /**
     * Handles the common use case of giving self as item with tile entity nbt.
     *
     * @param block the current block.
     * @param world the world
     * @param pos   the position.
     */
    public static ItemStack getItemWithNbt(Block block, IBlockReader world, BlockPos pos) {
        ItemStack itemStack = new ItemStack(block);
        TileEntity tileEntity = world.getTileEntity(pos);
        CompoundNBT compoundnbt = tileEntity.serializeNBT();
        if (!compoundnbt.isEmpty()) {
            itemStack.setTagInfo("BlockEntityTag", compoundnbt);
        }

        return itemStack;
    }
    //endregion Static Methods
}
