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

package com.klikli_dev.occultism.common.container.spirit;

import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class SpiritTransporterContainer extends SpiritContainer {

    public static final int FILTER_SIZE = 14;

    protected final Player player;

    public SpiritTransporterContainer(int id, Inventory playerInventory,
                                      SpiritEntity spirit) {
        this(id, playerInventory, (IFilterConfigurable) spirit);
    }

    public SpiritTransporterContainer(int id, Inventory playerInventory,
                                      IFilterConfigurable spirit) {

        super(OccultismContainers.SPIRIT_TRANSPORTER.get(), id, playerInventory, spirit);

        this.player = playerInventory.player;
        //needs to be called after transport item jobs has been set, so its not in setupSlots()
        this.setupFilterSlots();
    }

    @Override
    protected void setupPlayerInventorySlots(Player player) {
        int playerInventoryTop = 138;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 196;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    @Override
    public void clicked(int id, int dragType, ContainerInput clickType, Player player) {
        Slot slot = id >= 0 ? this.getSlot(id) : null;

        ItemStack holding = player.containerMenu.getCarried();

        if (slot instanceof FilterSlot) {
            if (holding.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else if (slot.mayPlace(holding)) {
                slot.set(holding.copyWithCount(1));
            }

            //Used to be "return holding;" in 1.16 (back then method was named slotClick on MCP names
            return;
        }

        super.clicked(id, dragType, clickType, player);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        if (slot instanceof FilterSlot) {
            return false;
        }

        return super.canTakeItemForPickAll(stack, slot);
    }

    protected void setupFilterSlots() {
        int x = 8;
        int y = 84;
        ItemStacksResourceHandler filterItems = this.spirit.getFilterItems();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < filterItems.size() / 2; j++) {
                this.addSlot(new FilterSlot(filterItems, j + i * 7, x + j * 18,
                        y + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        final int filterSize = FILTER_SIZE;

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index >= this.slots.size() - this.inventory.size() - filterSize) {
                if (!this.moveItemStackTo(itemstack1, 0, this.slots.size() - this.inventory.size() - filterSize, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, this.slots.size() - this.inventory.size() - filterSize, this.slots.size() - filterSize, true)) {
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

    public class FilterSlot extends ResourceHandlerSlot {

        public FilterSlot(ItemStacksResourceHandler handler, int inventoryIndex, int x, int y) {
            super(handler, handler::set, inventoryIndex, x, y);
        }

    }

}
