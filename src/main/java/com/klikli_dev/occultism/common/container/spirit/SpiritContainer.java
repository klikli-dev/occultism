/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.container.spirit;

import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

import javax.annotation.Nullable;

public class SpiritContainer extends AbstractContainerMenu {
    protected static final int AGED_INVENTORY_OFFSET = 13;
    protected static final int MAIN_TOP = 15;
    protected static final int MAIN_HEIGHT = 63;
    protected static final int TOP_BAR_HEIGHT = 18;
    protected static final int INVENTORY_SLOT_SIZE = 18;

    public ItemStacksResourceHandler inventory;
    public IFilterConfigurable spirit;

    public SpiritContainer(int id, Inventory playerInventory, SpiritEntity spirit) {
        this(OccultismContainers.SPIRIT.get(), id, playerInventory, spirit);
    }

    public SpiritContainer(@Nullable MenuType<?> type, int id, Inventory playerInventory, IFilterConfigurable spirit) {
        super(type, id);
        this.inventory = spirit.getInventory();
        this.spirit = spirit;

        this.setupSlots(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index >= this.slots.size() - this.inventory.size()) {
                if (!this.moveItemStackTo(itemstack1, 0, this.slots.size() - this.inventory.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, this.slots.size() - this.inventory.size(), this.slots.size(), true)) {
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
    public void removed(Player playerIn) {
        super.removed(playerIn);
    }

    @Override
    public boolean stillValid(Player entityPlayer) {
        return this.spirit.getEntity().isAlive() && this.spirit.getEntity().distanceTo(entityPlayer) < 8.0F;
    }

    public void setupSlots(Inventory playerInventory) {
        this.setupPlayerInventorySlots(playerInventory.player);
        this.setupPlayerHotbar(playerInventory.player);
        this.setupEntityInventory();
    }

    protected int inventoryOffsetY() {
        return this.spirit.getEntity() instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0
                ? AGED_INVENTORY_OFFSET
                : 0;
    }

    protected void setupPlayerInventorySlots(Player player) {
        int playerInventoryTop = 97 + this.inventoryOffsetY();
        int playerInventoryLeft = 11;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 155 + this.inventoryOffsetY();
        int hotbarLeft = 11;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    protected void setupEntityInventory() {
        int inventorySlotTop = TOP_BAR_HEIGHT + ((MAIN_TOP + MAIN_HEIGHT - TOP_BAR_HEIGHT) - INVENTORY_SLOT_SIZE) / 2;
        this.addSlot(new ResourceHandlerSlot(this.inventory, this.inventory::set, 0, 153, inventorySlotTop) {

            @Override
            public boolean isActive() {
                return SpiritContainer.this.spirit.isInventorySlotActive();
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return SpiritContainer.this.spirit.canPlaceInInventory(stack) && super.mayPlace(stack);
            }

        });
    }

}
