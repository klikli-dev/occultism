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
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class DepositOrder implements INBTSerializable<CompoundTag> {

    public ItemStackComparator comparator;
    public int amount;

    protected DepositOrder() {
    }

    public DepositOrder(ItemStackComparator comparator, int amount) {
        this.comparator = comparator;
        this.amount = amount;
    }

    //region Static Methods
    public static DepositOrder from(CompoundTag compound, HolderLookup.Provider provider
    ) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.deserializeNBT(provider, compound);
        return depositOrder;
    }
    //endregion Static Methods

    public CompoundTag writeToNBT(CompoundTag compound, HolderLookup.Provider provider) {
        compound.put("comparator", this.comparator.serializeNBT(provider));
        compound.putInt("amount", this.amount);
        return compound;
    }

    public void readFromNBT(CompoundTag compound, HolderLookup.Provider provider) {
        this.comparator = ItemStackComparator.from(compound.getCompound("comparator"), provider);
        this.amount = compound.getInt("amount");
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.writeToNBT(new CompoundTag(), provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.readFromNBT(nbt, provider);
    }

}
