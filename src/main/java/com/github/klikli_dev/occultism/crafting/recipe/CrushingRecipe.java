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
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CrushingRecipe extends ItemStackFakeInventoryRecipe {
    public static Serializer SERIALIZER = new Serializer();
    public static int DEFAULT_CRUSHING_TIME = 200;

    protected final int crushingTime;
    protected final int minTier;
    protected final boolean ignoreCrushingMultiplier;

    public CrushingRecipe(ResourceLocation id, Ingredient input, ItemStack output, int minTier, int crushingTime, boolean ignoreCrushingMultiplier) {
        super(id, input, output);
        this.crushingTime = crushingTime;
        this.minTier = minTier;
        this.ignoreCrushingMultiplier = ignoreCrushingMultiplier;
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
    public ItemStack assemble(ItemStackFakeInventory inv) {
        return this.getResultItem().copy();
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
    public RecipeType<?> getType() {
        return OccultismRecipes.CRUSHING_TYPE.get();
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingRecipe> {

        @Override
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int crushingTime = GsonHelper.getAsInt(json, "crushing_time", DEFAULT_CRUSHING_TIME);
            boolean ignoreCrushingMultiplier = GsonHelper.getAsBoolean(json, "ignore_crushing_multiplier", false);
            int minTier = GsonHelper.getAsInt(json, "min_tier", -1);
            return ItemStackFakeInventoryRecipe.SERIALIZER
                    .read((id, input, output) ->
                            new CrushingRecipe(id, input, output, minTier, crushingTime, ignoreCrushingMultiplier), recipeId, json);
        }

        @Override
        public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int crushingTime = buffer.readInt();
            boolean ignoreCrushingMultiplier = buffer.readBoolean();
            int minTier = buffer.readInt();
            return ItemStackFakeInventoryRecipe.SERIALIZER
                    .read((id, input, output) ->
                            new CrushingRecipe(id, input, output, minTier, crushingTime, ignoreCrushingMultiplier), recipeId, buffer);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeInt(recipe.crushingTime);
            buffer.writeBoolean(recipe.ignoreCrushingMultiplier);
            buffer.writeInt(recipe.minTier);
            ItemStackFakeInventoryRecipe.SERIALIZER.write(buffer, recipe);
        }
    }
}