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
import com.klikli_dev.occultism.common.item.filter.EntityItemFilter;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class SpiritTransporterContainer extends SpiritContainer {

    public static final int FILTER_SIZE = 1;

    protected final Player player;
    protected int filterSlotIndex = -1;

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
        int playerInventoryTop = 84;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 142;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    @Override
    protected void setupEntityInventory() {
        this.addSlot(new ResourceHandlerSlot(this.inventory, this.inventory::set, 0, 153, 29) {

            @Override
            public boolean isActive() {
                return SpiritTransporterContainer.this.spirit.isInventorySlotActive();
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return SpiritTransporterContainer.this.spirit.canPlaceInInventory(stack) && super.mayPlace(stack);
            }
        });
    }

    protected void setupFilterSlots() {
        int x = 153;
        int y = 53;
        ItemStacksResourceHandler filterItems = this.spirit.getFilterItems();
        this.filterSlotIndex = this.slots.size();
        this.addSlot(new FilterSlot(filterItems, 0, x, y));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index == this.filterSlotIndex || index == this.filterSlotIndex - 1) {
                if (!this.moveItemStackTo(itemstack1, 0, this.filterSlotIndex - 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (EntityItemFilter.isFilterItem(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, this.filterSlotIndex, this.filterSlotIndex + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, this.filterSlotIndex - 1, this.filterSlotIndex, false)) {
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

        @Override
        public boolean mayPlace(ItemStack stack) {
            return EntityItemFilter.isFilterItem(stack) && super.mayPlace(stack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

    }

    public int getFilterSlotIndex() {
        return this.filterSlotIndex;
    }

}
