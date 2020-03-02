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

package com.github.klikli_dev.occultism.common.container.spirit;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SpiritContainer extends Container {

    //region Fields
    public ItemStackHandler inventory;
    public SpiritEntity spirit;
    //endregion Fields

    //region Initialization
    public SpiritContainer(int id, PlayerInventory playerInventory, SpiritEntity spirit) {
        super(OccultismContainers.SPIRIT.get(), id);
        this.inventory = spirit.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        this.spirit = spirit;

        this.setupPlayerInventorySlots(playerInventory.player);
        this.setupPlayerHotbar(playerInventory.player);
        this.setupEntityInventory();
    }
    //endregion Initialization

    //region Overrides

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSlots()) {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSlots(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSlots(), false)) {
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
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity entityPlayer) {
        return this.spirit.isAlive() && this.spirit.getDistance(entityPlayer) < 8.0F;
    }
    //endregion Overrides

    //region Methods
    protected void setupPlayerInventorySlots(PlayerEntity player) {
        int playerInventoryTop = 84;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.inventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupPlayerHotbar(PlayerEntity player) {
        int hotbarTop = 142;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.inventory, i, hotbarLeft + i * 18, hotbarTop));
    }

    protected void setupEntityInventory() {
        this.addSlot(new SlotItemHandler(this.inventory, 0, 152, 54) {
            //region Overrides
            @Override
            public boolean isItemValid(ItemStack stack) {
                return super.isItemValid(stack);
            }

            public void onSlotChanged() {
                this.inventory.markDirty();
            }
            //endregion Overrides
        });
    }
    //endregion Methods
}
