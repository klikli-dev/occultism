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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class RitualRecipe extends ShapelessRecipe {
    //region Fields
    public static Serializer SERIALIZER = new Serializer();

    private ResourceLocation pentacleId;
    private ItemStack ritual;
    private Ingredient activationItem;
    private boolean requireSacrifice;
    private boolean requireItemUse;
    //endregion Fields

    //region Initialization
    public RitualRecipe(ResourceLocation id, String group, ResourceLocation pentacleId, ItemStack ritual,
                        ItemStack result, Ingredient activationItem, NonNullList<Ingredient> input,
                        boolean requireSacrifice, boolean requireItemUse) {
        super(id, group, result, input);
        this.pentacleId = pentacleId;
        this.ritual = ritual;
        this.activationItem = activationItem;
        this.requireSacrifice = requireSacrifice;
        this.requireItemUse = requireItemUse;
    }
    //endregion Initialization

    //region Getter / Setter
    public ResourceLocation getPentacleId() {
        return this.pentacleId;
    }

    public ItemStack getRitual() {
        return this.ritual;
    }

    public Ingredient getActivationItem() {
        return this.activationItem;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inventory, @Nonnull Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventoryCrafting) {
        //as we don't have an inventory this is ignored.
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.RITUAL_TYPE.get();
    }
    //endregion Overrides

//region Methods
    public boolean requireSacrifice() {
        return this.requireSacrifice;
    }

    public boolean requireItemUse() {
        return this.requireItemUse;
    }
//endregion Methods

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RitualRecipe> {
        //region Fields
        private static final ShapelessRecipe.Serializer serializer = new ShapelessRecipe.Serializer();
        //endregion Fields

        //region Overrides
        @Override
        public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ShapelessRecipe recipe = serializer.fromJson(recipeId, json);
            JsonElement activationItemElement =
                    GsonHelper.isArrayNode(json, "activation_item") ? GsonHelper.getAsJsonArray(json,
                            "activation_item") : GsonHelper.getAsJsonObject(json, "activation_item");
            Ingredient activationItem = Ingredient.fromJson(activationItemElement);
            ResourceLocation pentacleId = new ResourceLocation(json.get("pentacle_id").getAsString());
            ItemStack ritual = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "ritual"), true);
            boolean requireSacrifice = json.get("require_sacrifice").getAsBoolean();
            boolean requireItemUse = json.get("require_item_use").getAsBoolean();
            return new RitualRecipe(recipe.getId(), recipe.getGroup(), pentacleId, ritual,
                    recipe.getResultItem(), activationItem,
                    recipe.getIngredients(), requireSacrifice, requireItemUse);
        }

        @Override
        public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ShapelessRecipe recipe = serializer.fromNetwork(recipeId, buffer);
            ResourceLocation pentacleId = buffer.readResourceLocation();
            ItemStack ritual = buffer.readItem();
            Ingredient activationItem = Ingredient.fromNetwork(buffer);
            boolean requireSacrifice = buffer.readBoolean();
            boolean requireItemUse = buffer.readBoolean();

            return new RitualRecipe(recipe.getId(), recipe.getGroup(), pentacleId, ritual,
                    recipe.getResultItem(), activationItem,
                    recipe.getIngredients(), requireSacrifice, requireItemUse);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
            serializer.toNetwork(buffer, recipe);
            buffer.writeResourceLocation(recipe.pentacleId);
            buffer.writeItem(recipe.ritual);
            recipe.activationItem.toNetwork(buffer);
            buffer.writeBoolean(recipe.requireSacrifice);
            buffer.writeBoolean(recipe.requireItemUse);
        }
        //endregion Overrides
    }
}
