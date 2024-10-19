/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BookBindingCraftingRecipePage extends BookRecipePage<Recipe<?>> {
    ItemStack unboundBook;

    public BookBindingCraftingRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor, BookCondition condition, ItemStack unboundBook) {
        super(RecipeType.CRAFTING, title1, recipeId1, title2, recipeId2, text, anchor, condition);

        this.unboundBook = unboundBook;
    }

    public static BookBindingCraftingRecipePage fromJson(ResourceLocation conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(json, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(conditionParentId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();

        var unboundBook = ItemStack.STRICT_CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), json.get("unbound_book")).result().get();

        return new BookBindingCraftingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition, unboundBook);
    }

    public static BookBindingCraftingRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var unboundBook = ItemStack.STREAM_CODEC.decode(buffer);
        return new BookBindingCraftingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition, unboundBook);
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        ItemStack.STREAM_CODEC.encode(buffer, this.unboundBook);
    }

    @Override
    public ResourceLocation getType() {
        return OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE;
    }

    @Override
    protected ItemStack getRecipeOutput(Level level, RecipeHolder<Recipe<?>> recipe) {
        if (recipe == null) {
            return ItemStack.EMPTY;
        }

        return BoundBookOfBindingRecipe.getBoundBookFromBook(this.unboundBook);
    }
}
