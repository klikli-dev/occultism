/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BookBindingCraftingRecipePageModel extends BookRecipePageModel<BookBindingCraftingRecipePageModel> {

    public static final String RECIPE_ID = "occultism:crafting/bound_book_of_binding";
    protected ItemStack unboundBook = ItemStack.EMPTY;

    protected BookBindingCraftingRecipePageModel() {
        super(OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE);
    }

    public static BookBindingCraftingRecipePageModel create() {
        return new BookBindingCraftingRecipePageModel();
    }

    @Override
    public JsonObject toJson(HolderLookup.Provider provider) {
        var json = super.toJson(provider);
        json.add("unbound_book", ItemStack.STRICT_CODEC
                .encodeStart(provider.createSerializationContext(JsonOps.INSTANCE), this.unboundBook)
                .getOrThrow()
        );
        return json;
    }

    public ItemStack getUnboundBook() {
        return this.unboundBook;
    }

    public BookBindingCraftingRecipePageModel withUnboundBook(ItemStack unboundBook) {
        this.unboundBook = unboundBook;
        return this;
    }

    public BookBindingCraftingRecipePageModel withRecipeId1() {
        this.recipeId1 = RECIPE_ID;
        return this;
    }


    public BookBindingCraftingRecipePageModel withRecipeId2() {
        this.recipeId2 = RECIPE_ID;
        return this;
    }
}
