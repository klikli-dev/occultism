/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

public class BookBindingCraftingRecipePageModel extends BookRecipePageModel<BookBindingCraftingRecipePageModel> {

    public static final String RECIPE_ID = "occultism:crafting/bound_book_of_binding";
    @Nullable
    protected ItemStackTemplate unboundBook;

    protected BookBindingCraftingRecipePageModel() {
        super(OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE);
    }

    public static BookBindingCraftingRecipePageModel create() {
        return new BookBindingCraftingRecipePageModel();
    }

    public @Nullable ItemStackTemplate getUnboundBook() {
        return this.unboundBook;
    }

    public BookBindingCraftingRecipePageModel withUnboundBook(ItemStackTemplate unboundBook) {
        this.unboundBook = unboundBook;
        return this;
    }

    @Override
    public JsonObject toJson(Identifier entryId, HolderLookup.Provider provider) {
        var json = super.toJson(entryId, provider);
        if (this.unboundBook != null) {
            json.add("unbound_book", ItemStackTemplate.CODEC.encodeStart(provider.createSerializationContext(com.mojang.serialization.JsonOps.INSTANCE), this.unboundBook).getOrThrow());
        }
        return json;
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
