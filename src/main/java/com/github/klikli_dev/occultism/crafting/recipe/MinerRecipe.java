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

import com.github.klikli_dev.occultism.common.misc.WeightedIngredient;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MinerRecipe implements Recipe<RecipeWrapper> {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final WeightedIngredient output;
    //endregion Fields

    //region Initialization
    public MinerRecipe(ResourceLocation id, Ingredient input, WeightedIngredient output) {
        this.input = input;
        this.output = output;
        this.id = id;
    }
    //endregion Initialization

    //region Getter / Setter
    public WeightedIngredient getWeightedOutput() {
        return this.output;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        //we only ever use one slot, and we only support miners, so return true.
        return true;
    }

    @Override
    public ItemStack getResultItem() {
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
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.MINER_TYPE.get();
    }

    //endregion Overrides

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MinerRecipe> {

        //region Overrides
        @Override
        public MinerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonElement ingredientElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json,
                    "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(ingredientElement);
            JsonElement resultElement = GsonHelper.getAsJsonObject(json, "result");
            Ingredient result = Ingredient.fromJson(resultElement);
            int weight = GsonHelper.getAsInt(json, "weight");

            return new MinerRecipe(recipeId, ingredient, new WeightedIngredient(result, weight));
        }

        @Override
        public MinerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient result = Ingredient.fromNetwork(buffer);
            int weight = buffer.readInt();

            return new MinerRecipe(recipeId, ingredient, new WeightedIngredient(result, weight));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MinerRecipe recipe) {
            recipe.input.toNetwork(buffer);
            recipe.output.getIngredient().toNetwork(buffer);
            buffer.writeInt(recipe.output.getWeight().asInt());
        }
        //endregion Overrides
    }
}