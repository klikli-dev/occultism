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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CrushingRecipe extends ItemStackFakeInventoryRecipe {
    public static Serializer SERIALIZER = new Serializer();
    public static int DEFAULT_CRUSHING_TIME = 200;

    protected final int crushingTime;
    protected final int minTier;
    protected final boolean ignoreCrushingMultiplier;

    protected OutputIngredient output;

    public CrushingRecipe(ResourceLocation id, Ingredient input, OutputIngredient output, int minTier, int crushingTime, boolean ignoreCrushingMultiplier) {
        super(id, input, ItemStack.EMPTY); //hand over empty item stack, because we cannot resolve output.getStack() yet as tags are not resolved yet.
        this.output = output;
        this.crushingTime = crushingTime;
        this.minTier = minTier;
        this.ignoreCrushingMultiplier = ignoreCrushingMultiplier;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public int getCrushingTime() {
        return this.crushingTime;
    }

    public boolean getIgnoreCrushingMultiplier() {
        return this.ignoreCrushingMultiplier;
    }

    public int getMinTier() {
        return this.minTier;
    }

    @Override
    public boolean matches(ItemStackFakeInventory inv, Level level) {
        if (inv instanceof TieredItemStackFakeInventory tieredInv) {
            return tieredInv.getTier() >= this.minTier && this.input.test(inv.getItem(0));
        }

        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.getStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.CRUSHING_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<CrushingRecipe> {


        @Override
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject originalJson) {
            var json = originalJson.deepCopy(); //we are modifying the json, so we need a copy to avoid side effects to e.g. KubeJS

            int crushingTime = GsonHelper.getAsInt(json, "crushing_time", DEFAULT_CRUSHING_TIME);
            boolean ignoreCrushingMultiplier = GsonHelper.getAsBoolean(json, "ignore_crushing_multiplier", false);
            int minTier = GsonHelper.getAsInt(json, "min_tier", -1);

            var resultElement = GsonHelper.getAsJsonObject(json, "result");

            //our recipe supports tags and items as output, so we use the ingredient loader which handles both
            var outputIngredient = Ingredient.fromJson(resultElement);

            //the ingredient loader does not handle count and nbt, so we use the item loader
            //The item loader requires and "item" field, so we add it if it is missing
            if(!resultElement.has("item"))
                //just a dummy, OutputIngredient will not use the item type.
                //however, cannot be air as that will make ItemStack report as empty
                resultElement.addProperty("item", "minecraft:dirt");

            //helper to get count and nbt for our output ingredient
            ItemStack outputStackInfo = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

            JsonElement ingredientElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json,
                    "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(ingredientElement);

            return  new CrushingRecipe(recipeId, ingredient, new OutputIngredient(outputIngredient, outputStackInfo), minTier, crushingTime, ignoreCrushingMultiplier);
        }

        @Override
        public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int crushingTime = buffer.readInt();
            boolean ignoreCrushingMultiplier = buffer.readBoolean();
            int minTier = buffer.readInt();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient outputIngredient = Ingredient.fromNetwork(buffer);
            ItemStack outputStackInfo = buffer.readItem();
            return new CrushingRecipe(recipeId, ingredient, new OutputIngredient(outputIngredient, outputStackInfo), minTier, crushingTime, ignoreCrushingMultiplier);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeInt(recipe.crushingTime);
            buffer.writeBoolean(recipe.ignoreCrushingMultiplier);
            buffer.writeInt(recipe.minTier);
            recipe.input.toNetwork(buffer);
            recipe.output.getIngredient().toNetwork(buffer);
            buffer.writeItem(recipe.output.getOutputStackInfo());
        }
    }
}