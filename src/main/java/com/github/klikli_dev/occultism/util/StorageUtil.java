/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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

import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class StorageUtil {
    //region Static Methods

    /**
     * Clears the crafting matrix of the open container, if that container implements IStorageControllerContainer
     *
     * @param player          the player to clear the crafting matrix for.
     * @param sendStackUpdate true to resend the current stacks to the client.
     */
    public static void clearOpenCraftingMatrix(ServerPlayerEntity player, boolean sendStackUpdate) {
        if (player.openContainer instanceof IStorageControllerContainer) {
            IStorageControllerContainer container = (IStorageControllerContainer) player.openContainer;
            CraftingInventory craftMatrix = container.getCraftMatrix();
            IStorageController storageController = container.getStorageController();

            if (storageController == null) {
                return;
            }

            for (int i = 0; i < 9; i++) {
                ItemStack stackInSlot = craftMatrix.getStackInSlot(i);
                //ignore already cleared slots
                if (stackInSlot.isEmpty()) {
                    continue;
                }

                //move items into storage, and if storage is full, keep remainder in crafting matrix
                int amountBeforeInsert = stackInSlot.getCount();
                int remainingAfterInsert = storageController.insertStack(stackInSlot.copy(), false);
                if (amountBeforeInsert == remainingAfterInsert) {
                    continue;
                }
                if (remainingAfterInsert == 0)
                    craftMatrix.setInventorySlotContents(i, ItemStack.EMPTY);
                else
                    craftMatrix.setInventorySlotContents(i,
                            ItemHandlerHelper.copyStackWithSize(stackInSlot, remainingAfterInsert));
            }

            //finally if requested, send the updated storage controller contents to the player.
            if (sendStackUpdate) {
                OccultismPackets.sendTo(player, storageController.getMessageUpdateStacks());
                ((Container) container).detectAndSendChanges();
            }
        }
    }

    /**
     * Clears the crafting matrix of the open container, if that container implements IStorageControllerContainer
     *
     * @param player          the player to clear the crafting matrix for.
     * @param sendStackUpdate true to resend the current stacks to the client.
     */
    public static void clearOpenOrderSlot(ServerPlayerEntity player, boolean sendStackUpdate) {
        if (player.openContainer instanceof IStorageControllerContainer) {
            IStorageControllerContainer container = (IStorageControllerContainer) player.openContainer;
            Inventory orderSlot = container.getOrderSlot();
            IStorageController storageController = container.getStorageController();

            if (storageController == null) {
                return;
            }

            ItemStack stackInSlot = orderSlot.getStackInSlot(0);
            if (!stackInSlot.isEmpty()) {
                //move items into storage, and if storage is full, keep remainder in crafting matrix
                int amountBeforeInsert = stackInSlot.getCount();
                int remainingAfterInsert = storageController.insertStack(stackInSlot.copy(), false);
                if (amountBeforeInsert != remainingAfterInsert) {
                    if (remainingAfterInsert == 0)
                        orderSlot.setInventorySlotContents(0, ItemStack.EMPTY);
                    else
                        orderSlot.setInventorySlotContents(0,
                                ItemHandlerHelper.copyStackWithSize(stackInSlot, remainingAfterInsert));
                }
            }

            //finally if requested, send the updated storage controller contents to the player.
            if (sendStackUpdate) {
                OccultismPackets.sendTo(player, storageController.getMessageUpdateStacks());
                ((Container) container).detectAndSendChanges();
            }
        }
    }

    /**
     * Extracts the given amount of items matching the given comparator from the given item handler
     *
     * @param itemHandler the handler to extract from.
     * @param comparator  the comparator to match item stacks.
     * @param amount      the amount to extract.
     * @param simulate    true to simulate.
     * @return the extracted stack.
     */
    public static ItemStack extractItem(IItemHandler itemHandler, ItemStackComparator comparator, int amount,
                                        boolean simulate) {
        if (itemHandler == null || comparator == null) {
            return ItemStack.EMPTY;
        }
        int amountExtracted = 0;
        //go through all slots in the handler
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack slot = itemHandler.getStackInSlot(i);
            //check if current slot matches
            if (comparator.matches(slot)) {
                //take out of handler, one by one
                ItemStack extractedStack = itemHandler.extractItem(i, 1, simulate);
                if (!extractedStack.isEmpty()) {
                    //if not empty increase the amount we extracted
                    amountExtracted++;

                    //once we found enough, use the current slot to create a stack with the desired amount and return it
                    if (amountExtracted == amount)
                        return ItemHandlerHelper.copyStackWithSize(slot, amount);
                    else
                        //continue extracting from this slot until we get nothing back.
                        i--;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Drops all items of the given tile entity.
     * Tile entity <bold>must</bold> return a combined item handler for direction null.
     * @param tileEntity the tile entity to drop contents for.
     */
    public static void dropInventoryItems(TileEntity tileEntity){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            dropInventoryItems(tileEntity.getWorld(), tileEntity.getPos(), handler);
        });
    }

    public static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler itemHandler){
        for(int i = 0; i < itemHandler.getSlots(); i++){
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemHandler.getStackInSlot(i));
        }
    }
    //endregion Static Methods

}
