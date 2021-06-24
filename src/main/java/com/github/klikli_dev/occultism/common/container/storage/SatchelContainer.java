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

package com.github.klikli_dev.occultism.common.container.storage;

import com.github.klikli_dev.occultism.registry.OccultismContainers;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class SatchelContainer extends Container {
    //region Fields
    public static final int SATCHEL_SIZE = 13 * 9;
    protected IInventory satchelInventory;
    protected PlayerInventory playerInventory;
    protected int selectedSlot;
    //endregion Fields

    //region Initialization
    public SatchelContainer(int id, PlayerInventory playerInventory, IInventory satchelInventory, int selectedSlot) {
        super(OccultismContainers.SATCHEL.get(), id);
        this.satchelInventory = satchelInventory;
        this.playerInventory = playerInventory;
        this.selectedSlot = selectedSlot;

        this.setupSatchelSlots();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void detectAndSendChanges() {
        if (this.satchelInventory instanceof SatchelInventory) {
            ((SatchelInventory) this.satchelInventory).writeItemStack();
        }
        super.detectAndSendChanges();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        //Adapted from Chestcontainer
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index >= this.satchelInventory.getSizeInventory()) {
                //if putting into satchel, abort if it's another satchel
                if(itemstack.getItem() == OccultismItems.SATCHEL.get())
                    return ItemStack.EMPTY;
            }
            //take out of satchel
            if (index < this.satchelInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.satchelInventory.getSizeInventory(),
                        this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            //put into satchel
            else if (!this.mergeItemStack(itemstack1, 0, this.satchelInventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        if(this.selectedSlot == -1){
            return CuriosUtil.getBackpack(player).getItem() == OccultismItems.SATCHEL.get();
        }
        if(this.selectedSlot < 0 || this.selectedSlot >= player.inventory.getSizeInventory())
            return false;
        return player.inventory.getStackInSlot(this.selectedSlot).getItem() == OccultismItems.SATCHEL.get();
    }
    //endregion Overrides

    //region Static Methods
    public static SatchelContainer createClientContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new SatchelContainer(id, playerInventory, new Inventory(SATCHEL_SIZE), selectedSlot);
    }
    //endregion Static Methods

    //region Methods
    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 174;
        int playerInventoryLeft = 44;
        int hotbarSlots = 9;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + hotbarSlots, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupPlayerHotbar() {
        int hotbarTop = 232;
        int hotbarLeft = 44;
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
        }
    }

    protected void setupSatchelSlots() {
        //8x 8y for satchel
        int height = 9;
        int width = 13;
        int x = 8;
        int y = 8;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.addSlot(new SatchelSlot(this.satchelInventory, j + i * width, x + j * 18, y + i * 18));
            }
        }
    }
    //endregion Methods
}
