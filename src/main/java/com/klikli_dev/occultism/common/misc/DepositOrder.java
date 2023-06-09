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

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

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
    public static DepositOrder from(CompoundTag compound) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.deserializeNBT(compound);
        return depositOrder;
    }
    //endregion Static Methods

    public CompoundTag writeToNBT(CompoundTag compound) {
        compound.put("comparator", this.comparator.serializeNBT());
        compound.putInt("amount", this.amount);
        return compound;
    }

    public void readFromNBT(CompoundTag compound) {
        this.comparator = ItemStackComparator.from(compound.getCompound("comparator"));
        this.amount = compound.getInt("amount");
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.writeToNBT(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.readFromNBT(nbt);
    }

}
