/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

public class BookSpiritFireRecipePage extends BookProcessingRecipePage<SpiritFireRecipe> {
    public BookSpiritFireRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookSpiritFireRecipePage(NetworkDataHolder data) {
        super(data);
    }

    public static BookSpiritFireRecipePage fromJson(Identifier conditionParentId, JsonObject json, Provider provider) {
        var common = BookRecipePage.commonFromJson(conditionParentId, json, provider);
        return new BookSpiritFireRecipePage(common);
    }

    public static BookSpiritFireRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        return new BookSpiritFireRecipePage(common);
    }

    @Override
    public Identifier getType() {
        return Page.SPIRIT_FIRE_RECIPE;
    }
}
