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

import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CrushingRecipe extends ItemStackFakeInventoryRecipe {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();
    public static int DEFAULT_CRUSHING_TIME = 200;

    protected final int crushingTime;
    protected final boolean ignoreCrushingMultiplier;
    //endregion Fields

    //region Initialization
    public CrushingRecipe(ResourceLocation id, Ingredient input, ItemStack output, int crushingTime, boolean ignoreCrushingMultiplier) {
        super(id, input, output);
        this.crushingTime = crushingTime;
        this.ignoreCrushingMultiplier = ignoreCrushingMultiplier;
    }
    //endregion Initialization

    //region Getter / Setter
    public int getCrushingTime() {
        return this.crushingTime;
    }

    public boolean getIgnoreCrushingMultiplier() {
        return this.ignoreCrushingMultiplier;
    }

    //endregion Getter / Setter

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

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return OccultismRecipes.CRUSHING_TYPE.get();
    }

    //endregion Overrides

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe> {

        //region Overrides
        @Override
        public CrushingRecipe read(ResourceLocation recipeId, JsonObject json) {
            int crushingTime = JSONUtils.getInt(json, "crushing_time", DEFAULT_CRUSHING_TIME);
            boolean ignoreCrushingMultiplier = JSONUtils.getBoolean(json, "ignore_crushing_multiplier", false);
            return ItemStackFakeInventoryRecipe.SERIALIZER
                    .read((id, input, output) ->
                            new CrushingRecipe(id, input, output, crushingTime, ignoreCrushingMultiplier), recipeId, json);
        }

        @Override
        public CrushingRecipe read(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int crushingTime = buffer.readInt();
            boolean ignoreCrushingMultiplier = buffer.readBoolean();
            return ItemStackFakeInventoryRecipe.SERIALIZER
                    .read((id, input, output) ->
                            new CrushingRecipe(id, input, output, crushingTime, ignoreCrushingMultiplier), recipeId, buffer);
        }

        @Override
        public void write(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeInt(recipe.crushingTime);
            buffer.writeBoolean(recipe.ignoreCrushingMultiplier);
            ItemStackFakeInventoryRecipe.SERIALIZER.write(buffer, recipe);
        }
        //endregion Overrides
    }
}