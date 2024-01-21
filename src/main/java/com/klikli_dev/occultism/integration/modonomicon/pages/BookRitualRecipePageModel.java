/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BookRitualRecipePageModel extends BookRecipePageModel<BookRitualRecipePageModel> {
    protected BookRitualRecipePageModel() {
        super(OccultismModonomiconConstants.Page.RITUAL_RECIPE);
    }

    public static BookRitualRecipePageModel create() {
        return new BookRitualRecipePageModel();
    }

    @Override
    public BookRitualRecipePageModel withTitle2(String title) {
        throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
    }

    @Override
    public BookRitualRecipePageModel withTitle2(Component title) {
        throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
    }

    @Override
    public BookRitualRecipePageModel withRecipeId2(String recipeId) {
        throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
    }

    @Override
    public BookRitualRecipePageModel withRecipeId2(ResourceLocation recipeId) {
        throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
    }

}
