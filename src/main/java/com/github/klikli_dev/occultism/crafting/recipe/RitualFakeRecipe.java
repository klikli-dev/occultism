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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.misc.WeightedIngredient;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RitualFakeRecipe implements IRecipe<RecipeWrapper> {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack output;
    //endregion Fields

    //region Initialization
    public RitualFakeRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.id = id;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean matches(RecipeWrapper inv, World world) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        //we only ever use one slot, and we only support miners, so return true.
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output.getStack();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(Ingredient.EMPTY, this.input);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }

    //endregion Overrides

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RitualFakeRecipe> {

        //region Overrides
        @Override
        public RitualFakeRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonElement ingredientElement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json,
                    "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
            Ingredient ingredient = Ingredient.deserialize(ingredientElement);
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);

            return new RitualFakeRecipe(recipeId, ingredient, result);
        }

        @Override
        public RitualFakeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack result = buffer.readItemStack();
            return new RitualFakeRecipe(recipeId, ingredient,result);
        }

        @Override
        public void write(PacketBuffer buffer, RitualFakeRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
        }
        //endregion Overrides
    }
}