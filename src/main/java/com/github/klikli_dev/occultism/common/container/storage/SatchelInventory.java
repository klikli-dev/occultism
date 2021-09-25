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

package com.github.klikli_dev.occultism.common.container.storage;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class SatchelInventory extends Inventory {

    //region Fields
    private final ItemStack itemStack;
    //endregion Fields

    //region Initialization
    public SatchelInventory(ItemStack itemStack, int count) {
        super(count);
        this.itemStack = itemStack;
        this.readItemStack();
    }
    //endregion Initialization

    //region Getter / Setter
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    //endregion Getter / Setter

    //region Methods
    public void readItemStack() {
        this.readNBT(this.itemStack.getOrCreateTag());
    }

    public void writeItemStack() {
        if (this.isEmpty()) {
            this.itemStack.removeTagKey("Items");
        } else {
            this.writeNBT(this.itemStack.getOrCreateTag());
        }
    }

    private void readNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (compound.contains("Items")) {
            ItemStackHelper.loadAllItems(compound, list);
            for (int index = 0; index < list.size(); index++) {
                this.setItem(index, list.get(index));
            }
        }
    }

    private void writeNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        for (int index = 0; index < list.size(); index++) {
            list.set(index, this.getItem(index));
        }
        ItemStackHelper.saveAllItems(compound, list, false);
    }
    //endregion Methods
}