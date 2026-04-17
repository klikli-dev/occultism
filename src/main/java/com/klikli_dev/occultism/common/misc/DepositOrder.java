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

package com.klikli_dev.occultism.common.misc;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;

public class DepositOrder {

    public ItemStackComparator comparator;
    public int amount;

    protected DepositOrder() {
    }

    public DepositOrder(ItemStackComparator comparator, int amount) {
        this.comparator = comparator;
        this.amount = amount;
    }

    //region Static Methods
    public static DepositOrder from(CompoundTag compound, Provider provider
    ) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.deserializeNBT(provider, compound);
        return depositOrder;
    }
    //endregion Static Methods

    public CompoundTag writeToNBT(CompoundTag compound, Provider provider) {
        compound.put("comparator", this.comparator.serializeNBT(provider));
        compound.putInt("amount", this.amount);
        return compound;
    }

    public void readFromNBT(CompoundTag compound, Provider provider) {
        this.comparator = ItemStackComparator.from(compound.getCompoundOrEmpty("comparator"), provider);
        this.amount = compound.getIntOr("amount", 0);
    }

    public CompoundTag serializeNBT(Provider provider) {
        return this.writeToNBT(new CompoundTag(), provider);
    }

    public void deserializeNBT(Provider provider, CompoundTag nbt) {
        this.readFromNBT(nbt, provider);
    }

}
