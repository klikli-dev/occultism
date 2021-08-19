/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, MrRiegel, Sam Bassett
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class ItemTagComparator implements IItemStackComparator {
    //region Fields
    protected Tag<Item> tag;
    //endregion Fields

    //region Initialization
    public ItemTagComparator(Tag<Item> tag) {
        this.tag = tag;
    }

    private ItemTagComparator() {
    }
    //endregion Initialization

    //region Overrides

    @Override
    public boolean matches(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;

        return this.tag.contains(stack.getItem());
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.write(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.read(nbt);
    }
    //endregion Overrides

    //region Static Methods
    public static ItemTagComparator from(CompoundTag nbt) {
        ItemTagComparator comparator = new ItemTagComparator();
        comparator.deserializeNBT(nbt);
        return comparator;
    }
    //endregion Static Methods

    //region Methods
    public void read(CompoundTag compound) {
        compound.putString("tag", ItemTags.getAllTags().getId(this.tag).toString());
    }

    public CompoundTag write(CompoundTag compound) {
        this.tag = ItemTags.getAllTags().getTag(new ResourceLocation(compound.getString("tag")));
        return compound;
    }
    //endregion Methods
}
