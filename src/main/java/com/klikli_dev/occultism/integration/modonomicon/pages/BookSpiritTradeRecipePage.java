/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

public class BookSpiritTradeRecipePage extends BookProcessingRecipePage<SpiritTradeRecipe> {
    public BookSpiritTradeRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookSpiritTradeRecipePage(NetworkDataHolder data) {
        super(data);
    }

    public static BookSpiritTradeRecipePage fromJson(Identifier entryId, JsonObject json, Provider provider) {
        var common = BookRecipePage.commonFromJson(entryId, json, provider);
        return new BookSpiritTradeRecipePage(common);
    }

    public static BookSpiritTradeRecipePage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        return new BookSpiritTradeRecipePage(common);
    }

    @Override
    public Identifier getType() {
        return Page.SPIRIT_TRADE_RECIPE;
    }
}
