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

package com.klikli_dev.occultism.common.container.satchel;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public abstract class AbstractSatchelContainer extends AbstractContainerMenu {

    protected Container satchelInventory;
    protected Inventory playerInventory;
    protected int selectedSlot;
    protected ItemStack satchelStack;

    public AbstractSatchelContainer(@Nullable MenuType<?> menuType, int id, Inventory playerInventory, Container satchelInventory, int selectedSlot) {
        super(menuType, id);
        this.satchelInventory = satchelInventory;
        this.playerInventory = playerInventory;
        this.selectedSlot = selectedSlot;

        if (this.selectedSlot == -1) {
            this.satchelStack = CuriosUtil.getBackpack(playerInventory.player);
        }

        this.satchelStack = playerInventory.player.getInventory().getItem(this.selectedSlot).copy();

        this.setupSatchelSlots();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();
    }

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

    @Override
    public boolean stillValid(Player player) {
        if (this.selectedSlot == -1) {
            return CuriosUtil.getBackpack(player).getItem() == this.satchelStack.getItem();
        }
        if (this.selectedSlot < 0 || this.selectedSlot >= player.getInventory().getContainerSize())
            return false;
        return player.getInventory().getItem(this.selectedSlot).getItem() == this.satchelStack.getItem();
    }

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

    protected abstract void setupSatchelSlots();

}
