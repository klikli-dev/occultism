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
import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;

public class BookSpiritTradeRecipePage extends BookProcessingRecipePage<SpiritTradeRecipe> {
    public BookSpiritTradeRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookSpiritTradeRecipePage(NetworkDataHolder data) {
        super(data);
    }

    public static BookSpiritTradeRecipePage fromJson(Identifier conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(conditionParentId, json, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(conditionParentId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        return new BookSpiritTradeRecipePage(common);
    }

    public static BookSpiritTradeRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        return new BookSpiritTradeRecipePage(common);
    }

    @Override
    public Identifier getType() {
        return OccultismModonomiconConstants.Page.SPIRIT_TRADE_RECIPE;
    }
}
