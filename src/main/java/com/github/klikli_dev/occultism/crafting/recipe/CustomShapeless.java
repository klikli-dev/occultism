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
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * A custom shapeless recipe that allows to preserve select ingredients, as well as copy nbt from certain ingredients.
 */
public class CustomShapeless extends ShapelessRecipe {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    //endregion Fields

    //region Initialization
    public CustomShapeless(ResourceLocation id, ItemStack result, NonNullList<Ingredient> input) {
        super(id, null, result, input);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(@Nonnull CraftingInventory inventory, @Nonnull World world) {

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

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull CraftingInventory inventory) {
        ItemStack outputWithNBT = this.getRecipeOutput().copy();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IIngredientCopyNBT) {
                    IIngredientCopyNBT item = (IIngredientCopyNBT) stack.getItem();
                    if (stack.getTag() != null && item.shouldCopyNBT(stack, this, inventory)) {
                        CompoundNBT resultNbt = stack.getTag().copy();
                        resultNbt = item.overrideNBT(stack, resultNbt, this, inventory);
                        outputWithNBT.setTag(resultNbt);
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
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inventory) {
        final NonNullList<ItemStack> remainingItems = super.getRemainingItems(inventory);

        for (int i = 0; i < remainingItems.size(); i++) {
            final ItemStack itemStack = inventory.getStackInSlot(i);

            //preserve items that implement IPreserveIngredient
            if (itemStack.getItem() instanceof IIngredientPreserve &&
                ((IIngredientPreserve) itemStack.getItem()).shouldPreserve(itemStack, this, inventory))
                remainingItems.set(i, itemStack.copy());

            //preserver the dictionary of spirits
            CompoundNBT compound = itemStack.getTag();
            if (compound != null && compound.contains("patchouli:book") &&
                compound.getString("patchouli:book").equals("occultism:dictionary_of_spirits"))
                remainingItems.set(i, itemStack.copy());
        }

        return remainingItems;
    }

    @Override
    public IRecipeType<?> getType() {
        return OccultismRecipes.CUSTOM_SHAPELESS_TYPE.get();
    }

    //endregion Overrides

    //region Methods

    //endregion Methods

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CustomShapeless> {
        //region Fields
        private static final ShapelessRecipe.Serializer serializer = new ShapelessRecipe.Serializer();
        //endregion Fields

        //region Overrides
        public CustomShapeless read(ResourceLocation recipeId, JsonObject json) {
            ShapelessRecipe recipe = serializer.read(recipeId, json);
            return new CustomShapeless(recipe.getId(), recipe.getRecipeOutput(), recipe.getIngredients());
        }


        public CustomShapeless read(ResourceLocation recipeId, PacketBuffer buffer) {
            ShapelessRecipe recipe = serializer.read(recipeId, buffer);
            return new CustomShapeless(recipe.getId(), recipe.getRecipeOutput(), recipe.getIngredients());
        }

        public void write(PacketBuffer buffer, CustomShapeless recipe) {
            serializer.write(buffer, recipe);
        }
        //endregion Overrides
    }
}
