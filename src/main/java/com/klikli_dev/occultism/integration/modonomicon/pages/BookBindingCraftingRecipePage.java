/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

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

    public static BookBindingCraftingRecipePage fromJson(Identifier entryId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(entryId, json, provider);
        var unboundBook = ItemStack.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), json.get("unbound_book")).result().orElse(ItemStack.EMPTY);
        return new BookBindingCraftingRecipePage(common, unboundBook);
    }

    public static BookBindingCraftingRecipePage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var unboundBook = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
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
}
