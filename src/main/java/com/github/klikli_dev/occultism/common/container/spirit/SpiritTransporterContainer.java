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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SpiritTransporterContainer extends SpiritContainer {
    //region Fields
    protected final Player player;
    //endregion Fields

    //region Initialization
    public SpiritTransporterContainer(int id, Inventory playerInventory,
                                      SpiritEntity spirit) {

        super(OccultismContainers.SPIRIT_TRANSPORTER.get(), id, playerInventory, spirit);

        this.player = playerInventory.player;
        //needs to be called after transport item jobs has been set, so its not in setupSlots()
        this.setupFilterSlots();
    }
    //endregion Initialization

    //region Overrides

    @Override
    protected void setupPlayerInventorySlots(Player player) {
        int playerInventoryTop = 120;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.getInventory(), j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar(Player player) {
        int hotbarTop = 178;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.getInventory(), i, hotbarLeft + i * 18, hotbarTop));
    }

    @Override
    public void clicked(int id, int dragType, ClickType clickType, Player player) {
        Slot slot = id >= 0 ? this.getSlot(id) : null;

        ItemStack holding = player.getInventory().getSelected();

        if (slot instanceof FilterSlot) {
            if (holding.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else if (slot.mayPlace(holding)) {
                slot.set(holding.copy());
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
    //endregion Overrides

    //region Methods
    protected void setupFilterSlots() {
        int x = 8;
        int y = 84;
        ItemStackHandler filterItems = this.spirit.filterItemStackHandler.orElseThrow(ItemHandlerMissingException::new);
        for (int i = 0; i < filterItems.getSlots(); ++i) {
            this.addSlot(new FilterSlot(filterItems, i, x, y));
            x += 18;
        }

    }
    //endregion Methods

    public class FilterSlot extends SlotItemHandler {
        //region Initialization
        public FilterSlot(IItemHandler handler, int inventoryIndex, int x, int y) {
            super(handler, inventoryIndex, x, y);
        }
        //endregion Initialization

        //region Overrides
        @Override
        public void set(@Nonnull ItemStack stack) {
            if (!stack.isEmpty()) {
                stack.setCount(1);
            }

            super.set(stack);
        }
        //endregion Overrides
    }

}
