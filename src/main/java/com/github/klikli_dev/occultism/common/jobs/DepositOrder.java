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

package com.github.klikli_dev.occultism.common.jobs;

import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import net.minecraft.nbt.CompoundNBT;

public class DepositOrder {

    //region Fields
    public ItemStackComparator comparator;
    public int amount;
    //endregion Fields

    //region Initialization
    protected DepositOrder() {
    }

    public DepositOrder(ItemStackComparator comparator, int amount) {
        this.comparator = comparator;
        this.amount = amount;
    }
    //endregion Initialization

    //region Static Methods
    public static DepositOrder fromNbt(CompoundNBT compound) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.readFromNBT(compound);
        return depositOrder;
    }
    //endregion Static Methods

    //region Methods
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        compound.setTag("comparator", this.comparator.writeToNBT(new CompoundNBT()));
        compound.setInteger("amount", this.amount);
        return compound;
    }

    public void readFromNBT(CompoundNBT compound) {
        this.comparator = ItemStackComparator.loadFromNBT(compound.getCompoundTag("comparator"));
        this.amount = compound.getInteger("amount");
    }
    //endregion Methods
}
