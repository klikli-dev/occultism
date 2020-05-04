/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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

package com.github.klikli_dev.occultism.common.misc;

import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of slot crafting that sends network updates.
 */
public class StorageControllerSlot extends CraftingResultSlot {
    //region Fields
    IStorageControllerContainer storageControllerContainer;
    CraftingInventory matrix;
    //endregion Fields

    //region Initialization
    public StorageControllerSlot(PlayerEntity player, CraftingInventory matrix, IInventory inventory,
                                 IStorageControllerContainer storageControllerContainer, int slotIndex, int xPosition,
                                 int yPosition) {
        super(player, matrix, inventory, slotIndex, xPosition, yPosition);
        this.storageControllerContainer = storageControllerContainer;
        this.matrix = matrix;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        if (player.world.isRemote) {
            return stack;
        }

        List<ItemStack> craftingStacks = new ArrayList();
        for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
            craftingStacks.add(this.matrix.getStackInSlot(i).copy());
        }
        super.onTake(player, stack);
        ((Container)this.storageControllerContainer).detectAndSendChanges();
        for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
            IStorageController storageController = this.storageControllerContainer.getStorageController();
            if (this.matrix.getStackInSlot(i).isEmpty() && storageController != null) {
                ItemStack req = storageController.getItemStack(
                        !craftingStacks.get(i).isEmpty() ? new ItemStackComparator(craftingStacks.get(i)) : null, 1,
                        false);
                if (!req.isEmpty()) {
                    this.matrix.setInventorySlotContents(i, req);
                }
            }
        }
        ((Container)this.storageControllerContainer).detectAndSendChanges();
        return stack;
    }
    //endregion Overrides

}
