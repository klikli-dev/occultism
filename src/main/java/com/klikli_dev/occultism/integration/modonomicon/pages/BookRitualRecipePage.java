/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    public BookRitualRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookRitualRecipePage(NetworkDataHolder data) {
        super(data);
    }

    public static BookRitualRecipePage fromJson(Identifier entryId, JsonObject json, Provider provider) {
        var common = BookRecipePage.commonFromJson(entryId, json, provider);
        return new BookRitualRecipePage(common);
    }

    public static BookRitualRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        return new BookRitualRecipePage(common);
    }

    @Override
    public Identifier getType() {
        return Page.RITUAL_RECIPE;
    }
}
