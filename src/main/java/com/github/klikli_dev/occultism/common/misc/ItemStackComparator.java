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

import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class ItemStackComparator implements IItemStackComparator {
    //region Fields
    protected ItemStack filterStack;
    protected boolean matchMeta;
    protected boolean matchOre;
    protected boolean matchNbt;
    //endregion Fields

    //region Initialization
    public ItemStackComparator(ItemStack stack) {
        this(stack, stack == null || stack.getItemDamage() != OreDictionary.WILDCARD_VALUE, false, false);
    }

    public ItemStackComparator(ItemStack filterStack, boolean matchMeta, boolean matchOre, boolean matchNbt) {
        this.filterStack = filterStack;
        this.matchMeta = matchMeta;
        this.matchOre = matchOre;
        this.matchNbt = matchNbt;
    }

    private ItemStackComparator() {
    }
    //endregion Initialization

    //region Getter / Setter
    public boolean getMatchMeta() {
        return this.matchMeta;
    }

    public void setMatchMeta(boolean matchMeta) {
        this.matchMeta = matchMeta;
    }

    public boolean getMatchOre() {
        return this.matchOre;
    }

    public void setMatchOre(boolean matchOre) {
        this.matchOre = matchOre;
    }

    public boolean getMatchNbt() {
        return this.matchNbt;
    }

    public void setMatchNbt(boolean matchNbt) {
        this.matchNbt = matchNbt;
    }
    //endregion Getter / Setter

    //region Overrides
    public ItemStack getFilterStack() {
        return this.filterStack;
    }

    public void setFilterStack(@Nonnull ItemStack filterStack) {
        this.filterStack = filterStack;
    }

    @Override
    public boolean matches(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        if (this.matchOre && OreDictionary.itemMatches(this.filterStack, stack, this.matchMeta))
            return true;
        if (this.matchNbt && !ItemStack.areItemStackTagsEqual(this.filterStack, stack))
            return false;
        if (this.matchMeta && stack.getItemDamage() != this.filterStack.getItemDamage())
            return false;
        return stack.getItem() == this.filterStack.getItem();
    }
    //endregion Overrides

    //region Static Methods
    public static ItemStackComparator loadFromNBT(NBTTagCompound nbt) {
        ItemStackComparator comparator = new ItemStackComparator();
        comparator.readFromNBT(nbt);
        return !comparator.getFilterStack().isEmpty() ? comparator : null;
    }
    //endregion Static Methods

    //region Methods
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagCompound nbt = compound.getCompoundTag("stack");

        this.filterStack = new ItemStack(nbt);
        this.matchMeta = compound.getBoolean("matchMeta");
        this.matchOre = compound.getBoolean("matchOre");
        this.matchNbt = compound.getBoolean("matchNbt");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound c = new NBTTagCompound();
        this.filterStack.writeToNBT(c);
        compound.setTag("stack", c);
        compound.setBoolean("matchMeta", this.matchMeta);
        compound.setBoolean("matchOre", this.matchOre);
        compound.setBoolean("matchNbt", this.matchNbt);
        return compound;
    }
    //endregion Methods
}
