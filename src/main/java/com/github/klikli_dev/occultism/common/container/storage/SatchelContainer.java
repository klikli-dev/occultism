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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SatchelContainer extends AbstractContainerMenu {
    //region Fields
    public static final int SATCHEL_SIZE = 13 * 9;
    protected Container satchelInventory;
    protected Inventory playerInventory;
    protected int selectedSlot;
    //endregion Fields

    //region Initialization
    public SatchelContainer(int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(OccultismContainers.SATCHEL.get(), id);
        this.satchelInventory = satchelInventory;
        this.playerInventory = playerInventory;
        this.selectedSlot = selectedSlot;

        this.setupSatchelSlots();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();
    }
    //endregion Initialization

    //region Static Methods
    public static SatchelContainer createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        final int selectedSlot = buffer.readVarInt();
        return new SatchelContainer(id, playerInventory, new SimpleContainer(SATCHEL_SIZE), selectedSlot);
    }

    //region Overrides
    @Override
    public void broadcastChanges() {
        if (this.satchelInventory instanceof SatchelInventory) {
            ((SatchelInventory) this.satchelInventory).writeItemStack();
        }
        super.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        //Adapted from Chestcontainer
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index >= this.satchelInventory.getContainerSize()) {
                //if putting into satchel, abort if it's another satchel
                if (itemstack.getItem() == OccultismItems.SATCHEL.get())
                    return ItemStack.EMPTY;
            }
            //take out of satchel
            if (index < this.satchelInventory.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.satchelInventory.getContainerSize(),
                        this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            //put into satchel
            else if (!this.moveItemStackTo(itemstack1, 0, this.satchelInventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
    //endregion Overrides

    @Override
    public boolean stillValid(Player player) {
        if (this.selectedSlot == -1) {
            return CuriosUtil.getBackpack(player).getItem() == OccultismItems.SATCHEL.get();
        }
        if (this.selectedSlot < 0 || this.selectedSlot >= player.getInventory().getContainerSize())
            return false;
        return player.getInventory().getItem(this.selectedSlot).getItem() == OccultismItems.SATCHEL.get();
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
