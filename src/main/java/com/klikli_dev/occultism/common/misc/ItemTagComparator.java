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

package com.klikli_dev.occultism.common.misc;

import com.klikli_dev.occultism.api.common.container.IItemStackComparator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class ItemTagComparator implements IItemStackComparator {
    protected TagKey<Item> tag;

    public ItemTagComparator(TagKey<Item> tag) {
        this.tag = tag;
    }

    private ItemTagComparator() {
    }

    public static ItemTagComparator from(CompoundTag nbt, HolderLookup.Provider provider) {
        ItemTagComparator comparator = new ItemTagComparator();
        comparator.deserializeNBT(provider, nbt);
        return comparator;
    }

    @Override
    public boolean matches(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;

        return stack.is(this.tag);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.write(new CompoundTag());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.read(nbt);
    }

    public void read(CompoundTag compound) {
        compound.putString("tag", this.tag.location().toString());
    }

    public CompoundTag write(CompoundTag compound) {
        this.tag = TagKey.create(Registries.ITEM, new ResourceLocation(compound.getString("tag")));
        return compound;
    }
}
