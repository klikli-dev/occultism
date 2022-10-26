/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.github.klikli_dev.occultism.util.modonomicon.pages;

import com.github.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.github.klikli_dev.occultism.util.modonomicon.OccultismModonomiconConstants.Page;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookSpiritFireRecipePage extends BookProcessingRecipePage<SpiritFireRecipe> {
    public BookSpiritFireRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor) {
        super(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), title1, recipeId1, title2, recipeId2, text, anchor);
    }

    @Override
    public ResourceLocation getType() {
        return Page.SPIRIT_FIRE_RECIPE;
    }

    public static BookSpiritFireRecipePage fromJson(JsonObject json) {
        var common = BookRecipePage.commonFromJson(json);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        return new BookSpiritFireRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    public static BookSpiritFireRecipePage fromNetwork(FriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        return new BookSpiritFireRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeUtf(this.anchor);
    }
}
