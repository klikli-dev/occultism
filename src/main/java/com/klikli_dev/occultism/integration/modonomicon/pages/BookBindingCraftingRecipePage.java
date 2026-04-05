/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public class BookBindingCraftingRecipePage extends BookRecipePage<Recipe<?>> {
    ItemStackTemplate unboundBook;

    public BookBindingCraftingRecipePage(JsonDataHolder data, ItemStackTemplate unboundBook) {
        super(data);
        this.unboundBook = unboundBook;
    }

    public BookBindingCraftingRecipePage(NetworkDataHolder data, ItemStackTemplate unboundBook) {
        super(data);
        this.unboundBook = unboundBook;
    }

    public static BookBindingCraftingRecipePage fromJson(Identifier entryId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(entryId, json, provider);
        var unboundBook = ItemStackTemplate.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), json.get("unbound_book")).getOrThrow();
        return new BookBindingCraftingRecipePage(common, unboundBook);
    }

    public static BookBindingCraftingRecipePage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var unboundBook = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        return new BookBindingCraftingRecipePage(common, unboundBook);
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, this.unboundBook);
    }

    @Override
    public void build(Level level, BookContentEntry parentEntry, int pageNum) {
        // This page renders its recipe manually from the stored unbound book.
        // In 26.1 Modonomicon resolves recipe pages through RecipeDisplayEntry,
        // but this special crafting recipe has no server-side display entry.
        // Calling the base BookRecipePage#build would therefore log a spurious
        // "Recipe ... not found" warning even though the recipe itself exists.
        this.setParentEntry(parentEntry);
        this.setPageNumber(pageNum);
        this.book = parentEntry.getBook();

        if (this.title1.isEmpty()) {
            var boundBook = BoundBookOfBindingRecipe.getBoundBookFromBook(this.unboundBook.create());
            this.title1 = new BookTextHolder(boundBook.getHoverName().copy()
                    .withStyle(Style.EMPTY
                            .withBold(true)
                            .withColor(this.getParentEntry().getBook().getDefaultTitleColor())));
        }
    }

    @Override
    public Identifier getType() {
        return OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE;
    }
}
