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

package com.github.klikli_dev.occultism.api.common.item;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Implement this to copy item nbt to the crafting result.
 */
public interface IIngredientCopyNBT {
    //region Methods

    /**
     * Determines if this item's nbt should be copied to the result.
     *
     * @param itemStack the item stack to use in crafting.
     * @param recipe    the recipe the item is used in.
     * @param inventory the crafting inventory.
     * @return true to copy nbt to result.
     */
    default boolean shouldCopyNBT(ItemStack itemStack, IRecipe recipe, InventoryCrafting inventory) {
        return true;
    }

    /**
     * Allows to override the copied NBT and e.g. included only some tags.
     *
     * @param itemStack the item stack to use in crafting.
     * @param nbt       the nbt to copy.
     * @param recipe    the recipe the item is used in.
     * @param inventory the crafting inventory.
     * @return the tag to use on the resulting item.
     */
    default NBTTagCompound overrideNBT(ItemStack itemStack, NBTTagCompound nbt, IRecipe recipe,
                                       InventoryCrafting inventory) {
        return nbt;
    }
    //endregion Methods
}
