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
import com.github.klikli_dev.occultism.common.job.TransportItemsJob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SpiritTransporterContainer extends SpiritContainer {

    //region Fields
    protected TransportItemsJob transportItemsJob;
    //endregion Fields

    //region Initialization
    public SpiritTransporterContainer(int id, PlayerInventory playerInventory,
                                      SpiritEntity spirit) {

        super(id, playerInventory, spirit);
        this.transportItemsJob = (TransportItemsJob) this.spirit.getJob().orElse(null);
        //needs to be called after transport item jobs has been set, so its not in setupSlots()
        this.setupFilterSlots();
    }
    //endregion Initialization

    //region Overrides

    @Override
    protected void setupPlayerInventorySlots(PlayerEntity player) {
        int playerInventoryTop = 106;
        int playerInventoryLeft = 8;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(player.inventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                        playerInventoryTop + i * 18));
    }

    @Override
    protected void setupPlayerHotbar(PlayerEntity player) {
        int hotbarTop = 164;
        int hotbarLeft = 8;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(player.inventory, i, hotbarLeft + i * 18, hotbarTop));
    }
    //endregion Overrides

    //region Methods
    protected void setupFilterSlots() {
        int x = 8;
        int y = 84;
        for (int i = 0; i < TransportItemsJob.MAX_FILTER_SLOTS; ++i) {
            this.addSlot(new FilterSlot(this.transportItemsJob.getItemFilter(), i, x, y));
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
        public void putStack(@Nonnull ItemStack stack) {
            if (!stack.isEmpty()) {
                stack.setCount(1);
            }

            super.putStack(stack);
        }
        //endregion Overrides
    }

}
