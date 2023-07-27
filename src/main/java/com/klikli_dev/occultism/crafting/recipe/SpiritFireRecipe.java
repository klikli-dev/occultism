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

package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SpiritFireRecipe extends ItemStackFakeInventoryRecipe {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    //endregion Fields

    //region Initialization
    public SpiritFireRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        super(id, input, output);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean matches(ItemStackFakeInventory inv, Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(ItemStackFakeInventory inv) {
        ItemStack result = this.getResultItem().copy();
        result.setCount(inv.input.getCount());
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        //as we don't have a real inventory so this is ignored.
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(OccultismBlocks.SPIRIT_FIRE.get());
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.SPIRIT_FIRE_TYPE.get();
    }
    //endregion Overrides

    public static class Serializer implements RecipeSerializer<SpiritFireRecipe> {

        //region Overrides
        @Override
        public SpiritFireRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return ItemStackFakeInventoryRecipe.SERIALIZER.read(SpiritFireRecipe::new, recipeId, json);
        }

        @Override
        public SpiritFireRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return ItemStackFakeInventoryRecipe.SERIALIZER.read(SpiritFireRecipe::new, recipeId, buffer);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritFireRecipe recipe) {
            ItemStackFakeInventoryRecipe.SERIALIZER.write(buffer, recipe);
        }
        //endregion Overrides
    }
}