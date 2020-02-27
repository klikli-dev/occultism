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

package com.github.klikli_dev.occultism.crafting.recipe;

import com.github.klikli_dev.occultism.api.common.item.IIngredientCopyNBT;
import com.github.klikli_dev.occultism.api.common.item.IIngredientModifyCraftingResult;
import com.github.klikli_dev.occultism.api.common.item.IIngredientPreserve;
import com.github.klikli_dev.occultism.api.common.item.IIngredientPreventCrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

public class Shapeless extends ShapelessOreRecipe {
    //region Initialization
    public Shapeless(ResourceLocation group, final NonNullList<Ingredient> input, final ItemStack result) {
        super(group, input, result);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory) {
        final NonNullList<ItemStack> remainingItems = super.getRemainingItems(inventory);

        for (int i = 0; i < remainingItems.size(); i++) {
            final ItemStack itemStack = inventory.getStackInSlot(i);

            //preserve items that implement IPreserveIngredient
            if (itemStack.getItem() instanceof IIngredientPreserve &&
                ((IIngredientPreserve) itemStack.getItem()).shouldPreserve(itemStack, this, inventory))
                remainingItems.set(i, itemStack.copy());

            //preserver the dictionary of spirits
            NBTTagCompound compound = itemStack.getTagCompound();
            if (compound != null && compound.hasKey("patchouli:book") &&
                compound.getString("patchouli:book").equals("occultism:dictionary_of_spirits"))
                remainingItems.set(i, itemStack.copy());
        }

        return remainingItems;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventory) {
        ItemStack outputWithNBT = this.output.copy();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IIngredientCopyNBT) {
                    IIngredientCopyNBT item = (IIngredientCopyNBT) stack.getItem();
                    if (stack.getTagCompound() != null && item.shouldCopyNBT(stack, this, inventory)) {
                        NBTTagCompound resultNbt = stack.getTagCompound().copy();
                        resultNbt = item.overrideNBT(stack, resultNbt, this, inventory);
                        outputWithNBT.setTagCompound(resultNbt);
                    }
                }
            }
        }

        if (outputWithNBT.getItem() instanceof IIngredientModifyCraftingResult) {
            ((IIngredientModifyCraftingResult) outputWithNBT.getItem()).modifyResult(this, inventory, outputWithNBT);
        }

        return outputWithNBT;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inventory, @Nonnull World world) {

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IIngredientPreventCrafting &&
                    ((IIngredientPreventCrafting) stack.getItem())
                            .shouldPreventCrafting(stack, this, inventory, world)) {
                    //one of the ingredients prevents crafting conditionally, so we exit.
                    return false;
                }
            }
        }
        return super.matches(inventory, world);
    }
    //endregion Overrides
}
