/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, MrRiegel, Sam Bassettl.
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

import com.klikli_dev.occultism.api.common.container.IItemStackComparator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class ItemStackComparator implements IItemStackComparator {

    protected ItemStack filterStack;
    protected boolean matchNbt;

    public ItemStackComparator(ItemStack stack) {
        this(stack, false);
    }

    public ItemStackComparator(ItemStack filterStack, boolean matchNbt) {
        this.filterStack = filterStack;
        this.matchNbt = matchNbt;
    }

    private ItemStackComparator() {
    }

    //region Static Methods
    public static ItemStackComparator from(CompoundTag nbt, Provider provider) {
        ItemStackComparator comparator = new ItemStackComparator();
        comparator.deserializeNBT(provider, nbt);
        return !comparator.filterStack.isEmpty() ? comparator : null;
    }

    //region Getter / Setter
    public boolean getMatchNbt() {
        return this.matchNbt;
    }

    public void setMatchNbt(boolean matchNbt) {
        this.matchNbt = matchNbt;
    }

    public ItemStack getFilterStack() {
        return this.filterStack;
    }
    //endregion Getter / Setter

    public void setFilterStack(@Nonnull ItemStack filterStack) {
        this.filterStack = filterStack;
    }

    @Override
    public boolean matches(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;

        if (this.matchNbt && !ItemStack.isSameItemSameComponents(this.filterStack, stack))
            return false;
        return stack.getItem() == this.filterStack.getItem();
    }

    public CompoundTag serializeNBT(Provider provider) {
        return this.write(new CompoundTag(), provider);
    }

    public void deserializeNBT(Provider provider, CompoundTag nbt) {
        this.read(nbt, provider);
    }
    //endregion Static Methods

    public void read(CompoundTag compound, Provider provider) {
        CompoundTag nbt = compound.getCompoundOrEmpty("stack");
        this.filterStack = ItemStack.OPTIONAL_CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), nbt).result().orElse(ItemStack.EMPTY);
        this.matchNbt = compound.getBooleanOr("matchNbt", false);
    }

    public CompoundTag write(CompoundTag compound, Provider provider) {
        compound.put("stack", ItemStack.OPTIONAL_CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this.filterStack).result().orElse(new CompoundTag()));
        compound.putBoolean("matchNbt", this.matchNbt);
        return compound;
    }

}
