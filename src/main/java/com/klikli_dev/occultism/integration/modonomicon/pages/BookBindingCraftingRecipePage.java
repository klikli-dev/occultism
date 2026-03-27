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
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BookBindingCraftingRecipePage extends BookRecipePage<Recipe<?>> {
    ItemStack unboundBook;

    public BookBindingCraftingRecipePage(JsonDataHolder data, ItemStack unboundBook) {
        super(data);

        this.unboundBook = unboundBook;
    }

    public BookBindingCraftingRecipePage(NetworkDataHolder data, ItemStack unboundBook) {
        super(data);

        this.unboundBook = unboundBook;
    }

    public static BookBindingCraftingRecipePage fromJson(Identifier conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        // Modonomicon API changed in 26.1; commonFromJson now requires an Identifier for the parent.
        var common = BookRecipePage.commonFromJson(conditionParentId, json, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(conditionParentId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();

        // ItemStack strict codec was replaced with ItemStack.MAP_CODEC/CODEC in 26.1
        var unboundBook = ItemStack.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), json.get("unbound_book")).result().orElse(ItemStack.EMPTY);

        return new BookBindingCraftingRecipePage(common, unboundBook);
    }

    public static BookBindingCraftingRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        var unboundBook = net.minecraft.world.item.ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
        return new BookBindingCraftingRecipePage(common, unboundBook);
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        ItemStack.STREAM_CODEC.encode(buffer, this.unboundBook);
    }

    @Override
    public Identifier getType() {
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
