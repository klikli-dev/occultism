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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class ItemStackFakeInventoryRecipe implements Recipe<ItemStackFakeInventory> {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack output;
    //endregion Fields

    //region Initialization
    public ItemStackFakeInventoryRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.id = id;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean matches(ItemStackFakeInventory inv, Level level) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(ItemStackFakeInventory inv) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        //as we don't have a real inventory so this is ignored.
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(Ingredient.EMPTY, this.input);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    //endregion Overrides

    public interface IItemStackFakeInventoryRecipeFactory<T extends ItemStackFakeInventoryRecipe> {
        //region Methods
        T create(ResourceLocation id, Ingredient input, ItemStack output);
        //endregion Methods
    }

    public static class Serializer {

        //region Overrides
        //endregion Overrides

        //region Methods
        public <T extends ItemStackFakeInventoryRecipe> T read(IItemStackFakeInventoryRecipeFactory<T> factory,
                                                               ResourceLocation recipeId, JsonObject json) {
            //we also allow arrays, but only one ingredient will be used.
            JsonElement ingredientElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json,
                    "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            Ingredient ingredient = Ingredient.deserialize(ingredientElement);
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

            return factory.create(recipeId, ingredient, result);
        }

        public <T extends ItemStackFakeInventoryRecipe> T read(IItemStackFakeInventoryRecipeFactory<T> factory,
                                                               ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack result = buffer.readItemStack();
            return factory.create(recipeId, ingredient, result);
        }

        public <T extends ItemStackFakeInventoryRecipe> void write(FriendlyByteBuf buffer, T recipe) {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
        }
        //endregion Methods
    }
}