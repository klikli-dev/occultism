/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.container.spirit;

import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.filter.EntityItemFilter;
import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class FilterableSpiritContainer extends SpiritContainer {

    public static final int FILTER_SIZE = 1;

    protected final Player player;
    protected int filterSlotIndex = -1;

    public FilterableSpiritContainer(int id, Inventory playerInventory,
                                     SpiritEntity spirit) {
        this(id, playerInventory, (IFilterConfigurable) spirit);
    }

    public FilterableSpiritContainer(int id, Inventory playerInventory,
                                     IFilterConfigurable spirit) {

        super(OccultismContainers.SPIRIT_TRANSPORTER.get(), id, playerInventory, spirit);

        this.player = playerInventory.player;
        this.setupFilterSlots();
    }

    @Override
    protected void setupPlayerInventorySlots(Player player) {
        int playerInventoryTop = 97;
        int playerInventoryLeft = 11;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 155;
        int hotbarLeft = 11;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    @Override
    protected void setupEntityInventory() {
        this.addSlot(new ResourceHandlerSlot(this.inventory, this.inventory::set, 0, 153, 27) {

            @Override
            public boolean isActive() {
                return FilterableSpiritContainer.this.spirit.isInventorySlotActive();
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return FilterableSpiritContainer.this.spirit.canPlaceInInventory(stack) && super.mayPlace(stack);
            }
        });
    }

    protected void setupFilterSlots() {
        int x = 153;
        int y = 51;
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
