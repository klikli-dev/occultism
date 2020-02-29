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

package com.github.klikli_dev.occultism.common.container;

import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.client.gui.StorageControllerGuiBase;
import com.github.klikli_dev.occultism.common.misc.InventoryCraftingCached;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.SlotCraftingNetwork;
import com.github.klikli_dev.occultism.network.OccultismPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StorageControllerContainerBase extends Container implements IStorageControllerContainer {

    //region Fields
    public PlayerInventory playerInventory;
    public PlayerEntity player;
    protected CraftResultInventory result;
    protected InventoryCraftingCached matrix;
    protected Inventory orderInventory;
    protected ICraftingRecipe currentRecipe;

    /**
     * used to lock recipe while crafting
     */
    protected boolean recipeLocked = false;
    //endregion Fields

    //region Initialization


    protected StorageControllerContainerBase(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory) {
        super(type, id);
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;

        this.result = new CraftResultInventory();
        this.orderInventory = new Inventory(1);
    }

    //endregion Initialization

    //region Overrides
    @Override
    public CraftingInventory getCraftMatrix() {
        return this.matrix;
    }

    @Override
    public Inventory getOrderSlot() {
        return this.orderInventory;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        if (player.world.isRemote)
            return ItemStack.EMPTY;

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();

            IStorageController storageController = this.getStorageController();

            //index 0 is our crafting output, so we need to craft the item.
            if (index == 0) {
                this.craftShift(player, storageController);
                return ItemStack.EMPTY;
            }
            else if (storageController != null) {
                //insert item into controller
                int remainingItems = storageController.insertStack(slotStack, false);

                //get the stack of remaining items
                ItemStack remainingItemStack = remainingItems == 0 ? ItemStack.EMPTY : ItemHandlerHelper
                                                                                               .copyStackWithSize(
                                                                                                       slotStack,
                                                                                                       remainingItems);
                slot.putStack(remainingItemStack);

                //sync slots
                this.detectAndSendChanges();

                //get updated stacks from storage controller and send to client
                OccultismPacketHandler.sendTo((ServerPlayerEntity) player, storageController.getMessageUpdateStacks());

                if (!remainingItemStack.isEmpty()) {
                    slot.onTake(player, slotStack);
                }
                return ItemStack.EMPTY;
            }
        }
        return result;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.updateCraftingSlots(false);
        this.updateOrderSlot(true); //only send network update on second call
        super.onContainerClosed(playerIn);
    }

    //endregion Overrides

    //region Methods
    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 174;
        int playerInventoryLeft = 8 + StorageControllerGuiBase.ORDER_AREA_OFFSET;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupCraftingGrid() {

        int craftingGridTop = 113;
        int craftingGridLeft = 37 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        int index = 0;
        //3x3 crafting grid
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(
                        new Slot(this.matrix, index++, craftingGridLeft + j * 18, craftingGridTop + i * 18));
            }
        }
    }

    protected void setupCraftingOutput() {
        int craftingOutputTop = 131;
        int craftingOutputLeft = 130 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        SlotCraftingNetwork slotCraftOutput = new SlotCraftingNetwork(this.playerInventory.player, this.matrix,
                this.result, this, 0, craftingOutputLeft, craftingOutputTop);
        this.addSlot(slotCraftOutput);
    }

    protected void setupOrderInventorySlot() {
        int orderSlotTop = 36;
        int orderSlotLeft = 13;
        this.addSlot(new Slot(this.orderInventory, 0, orderSlotLeft, orderSlotTop));
    }

    protected abstract void setupPlayerHotbar();

    protected void findRecipeForMatrix() {
        //TODO: if there are issues, set up a copy of this based on WorkBenchContainer func_217066_a
        //      and call it onCraftingMatrixChanged(). Send slot packet!
        this.currentRecipe = null;
        Optional<ICraftingRecipe> optional = this.player.world.getRecipeManager()
                                                     .getRecipe(IRecipeType.CRAFTING, this.matrix, this.player.world);
        if (optional.isPresent()) {
            this.currentRecipe = optional.get();
            ItemStack itemstack = this.currentRecipe.getCraftingResult(this.matrix);
            this.result.setInventorySlotContents(0, itemstack);
        }
        else {
            this.result.setInventorySlotContents(0, ItemStack.EMPTY);
        }
    }

    protected void craftShift(PlayerEntity player, IStorageController storageController) {
        if (this.matrix == null) {
            return;
        }

        this.findRecipeForMatrix();
        if (this.currentRecipe == null) {
            return;
        }

        //lock recipes to avoid modification while we shift craft
        this.recipeLocked = true;

        //copy the recipe stacks
        List<ItemStack> recipeCopy = new ArrayList<>(this.matrix.getSizeInventory());
        for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
            recipeCopy.add(this.matrix.getStackInSlot(i).copy());
        }

        //Get the crafting result and abort if none
        ItemStack result = this.currentRecipe.getCraftingResult(this.matrix);
        if (result.isEmpty()) {
            return;
        }

        //get the stack size of the result
        int resultStackSize = result.getCount();

        int crafted = 0;
        while (crafted + resultStackSize <= result.getMaxStackSize()) {
            result = this.currentRecipe.getCraftingResult(this.matrix);

            //exit if we can no longer insert
            if (!ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(this.playerInventory), result, true)
                         .isEmpty()) {
                break;
            }

            //if recipe is no longer fulfilled, stop
            if (!this.currentRecipe.matches(this.matrix, player.world)) {
                break;
            }

            //region onTake replacement for crafting

            //give to the player
            ItemHandlerHelper.giveItemToPlayer(player, result);

            //get remaining items in the crafting matrix
            NonNullList<ItemStack> remainingCraftingItems = this.currentRecipe.getRemainingItems(this.matrix);
            for (int i = 0; i < remainingCraftingItems.size(); ++i) {

                ItemStack currentCraftingItem = remainingCraftingItems.get(i);
                ItemStack stackInSlot = this.matrix.getStackInSlot(i);

                //if we find an empty stack, shrink it to remove it.
                if (currentCraftingItem.isEmpty()) {
                    this.matrix.getStackInSlot(i).shrink(1);
                    continue;
                }

                //handle container item refunding
                if (stackInSlot.getItem().getContainerItem() != null) {
                    stackInSlot = new ItemStack(stackInSlot.getItem().getContainerItem());
                    this.matrix.setInventorySlotContents(i, stackInSlot);
                }
                //handle items that accept multiple containers
                else if (!stackInSlot.getItem().getContainerItem(stackInSlot).isEmpty()) {
                    stackInSlot = stackInSlot.getItem().getContainerItem(stackInSlot);
                    this.matrix.setInventorySlotContents(i, stackInSlot);
                }
                else if (!currentCraftingItem.isEmpty()) {
                    //if the slot is empty now we just place the crafting item in it
                    if (stackInSlot.isEmpty()) {
                        this.matrix.setInventorySlotContents(i, currentCraftingItem);
                    }
                    //handle "normal items"
                    else if (ItemStack.areItemsEqual(stackInSlot, currentCraftingItem) &&
                             ItemStack.areItemStackTagsEqual(stackInSlot, currentCraftingItem)) {
                        currentCraftingItem.grow(stackInSlot.getCount());
                        this.matrix.setInventorySlotContents(i, currentCraftingItem);
                    }
                    //handle items that consume durability on craft
                    else if (ItemStack.areItemsEqualIgnoreDurability(stackInSlot, currentCraftingItem)) {
                        this.matrix.setInventorySlotContents(i, currentCraftingItem);
                    }
                    else {
                        //last resort, try to place in player inventory or if that fails, drop.
                        ItemHandlerHelper.giveItemToPlayer(player, result);
                    }
                }
                else if (!stackInSlot.isEmpty()) {
                    //decrease the stack size in the matrix
                    this.matrix.decrStackSize(i, 1);
                }
            }
            //endregion onTake replacement for crafting


            crafted += resultStackSize;
            for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
                ItemStack stackInSlot = this.matrix.getStackInSlot(i);
                //if the stack is empty, refill from storage and then continue looping
                if (stackInSlot.isEmpty()) {
                    ItemStack recipeStack = recipeCopy.get(i);

                    ItemStackComparator comparator = !recipeStack.isEmpty() ? new ItemStackComparator(
                            recipeStack) : null;

                    ItemStack requestedItem = this.getStorageController().getItemStack(comparator, 1, false);
                    this.matrix.setInventorySlotContents(i, requestedItem);
                }
            }
            this.onCraftMatrixChanged(this.matrix);
        }
        this.detectAndSendChanges();

        //unlock crafting matrix
        this.recipeLocked = false;

        //update crafting matrix to handle container items / items that survive crafting
        this.onCraftMatrixChanged(this.matrix);
    }
    //endregion Methods
}
