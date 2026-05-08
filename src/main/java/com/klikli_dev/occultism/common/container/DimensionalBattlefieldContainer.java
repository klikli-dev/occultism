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

package com.klikli_dev.occultism.common.container;

import com.klikli_dev.occultism.common.blockentity.DimensionalBattlefieldBlockEntity;
import com.klikli_dev.occultism.registry.OccultismContainers;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.NotNull;

public class DimensionalBattlefieldContainer extends AbstractContainerMenu {

    public ItemStacksResourceHandler inputSoulHandler;
    public ItemStacksResourceHandler inputWeaponHandler;
    public ItemStacksResourceHandler inputFuelHandler;
    public ItemStacksResourceHandler outputHandler;
    public ResourceHandler<ItemResource> inputHandler;
    public DimensionalBattlefieldBlockEntity otherworldButcher;
    public Inventory playerInventory;

    public DimensionalBattlefieldContainer(int id, Inventory playerInventory,
                                           DimensionalBattlefieldBlockEntity otherworldButcher) {
        super(OccultismContainers.OTHERWORLD_BUTCHER.get(), id);
        this.playerInventory = playerInventory;
        this.otherworldButcher = otherworldButcher;
        this.inputSoulHandler = otherworldButcher.inputSoulHandler;
        this.inputWeaponHandler = otherworldButcher.inputWeaponHandler;
        this.inputFuelHandler = otherworldButcher.inputFuelHandler;
        this.outputHandler = otherworldButcher.outputHandler;
        this.inputHandler = otherworldButcher.inputHandler;

        this.setupButcherInventory();
        this.setupPlayerInventorySlots(playerInventory.player);
        this.setupPlayerHotbar(playerInventory.player);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(this.otherworldButcher.getBlockPos().getX() + 0.5D,
                this.otherworldButcher.getBlockPos().getY() + 0.5D,
                this.otherworldButcher.getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.outputHandler.size()) {
                //+1 because we have the input handler slot after the output hander slots
                if (!this.moveItemStackTo(itemstack1, this.outputHandler.size() + 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            //input handler slot is exactly at last output handler slot + 1
            else if (index >= this.outputHandler.size() && index <= this.outputHandler.size() + 2) {
                if (!this.moveItemStackTo(itemstack1, this.outputHandler.size() + 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            //+1 because we are actually only interested in inserting in the input handler. Could even start at the end index instead of 0.
            else if (!this.moveItemStackTo(itemstack1, this.outputHandler.size(), this.outputHandler.size() + 3, false)) {
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

    protected void setupPlayerInventorySlots(Player player) {
        int playerInventoryTop = 17 + 18 * 5 + 4 + 10;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 17 + 18 * 5 + 4 + 18 * 3 + 4 + 10;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    protected void setupButcherInventory() {
        int outputGridTop = 17;
        int outputGridLeft = 8 + 18 * 4;
        int index = 0;

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.addSlot(
                        new OutputSlot(this.otherworldButcher.outputHandler, index++, outputGridLeft + j * 18, outputGridTop + i * 18));
            }
        }

        this.addSlot(new InputSoulSlot(this.otherworldButcher.inputSoulHandler, 0, 27, 37, this.otherworldButcher));
        this.addSlot(new InputFuelSlot(this.otherworldButcher.inputFuelHandler, 0, 40, 59));
        this.addSlot(new InputWeaponSlot(this.otherworldButcher.inputWeaponHandler, 0, 14, 59, this.otherworldButcher));
    }

    public static class InputWeaponSlot extends ResourceHandlerSlot {
        DimensionalBattlefieldBlockEntity arena;

        public InputWeaponSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition, DimensionalBattlefieldBlockEntity arena) {
            super(itemHandler, itemHandler::set, index, xPosition, yPosition);
            this.arena = arena;
        }

        public boolean mayPlace(ItemStack stack) {
            this.arena.mobHealth = 0;
            return stack.has(DataComponents.ATTRIBUTE_MODIFIERS);
        }
    }

    public static class InputSoulSlot extends ResourceHandlerSlot {
        DimensionalBattlefieldBlockEntity arena;

        public InputSoulSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition, DimensionalBattlefieldBlockEntity arena) {
            super(itemHandler, itemHandler::set, index, xPosition, yPosition);
            this.arena = arena;
        }

        public boolean mayPlace(ItemStack stack) {
            this.arena.mobHealth = 0;
            return stack.has(DataComponents.ENTITY_DATA);
        }
    }

    public static class InputFuelSlot extends ResourceHandlerSlot {
        public InputFuelSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, itemHandler::set, index, xPosition, yPosition);
        }

        public boolean mayPlace(ItemStack stack) {
            return stack.has(OccultismDataComponents.SOUL_VALUE);
        }
    }

    public static class OutputSlot extends ResourceHandlerSlot {
        public OutputSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, itemHandler::set, index, xPosition, yPosition);
        }

        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }
    }
}
