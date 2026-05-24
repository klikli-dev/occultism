/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;

public class BookSpiritFireRecipePageModel extends BookRecipePageModel<BookSpiritFireRecipePageModel> {
    protected BookSpiritFireRecipePageModel() {
        super(BookSpiritFireRecipePage.ID);
    }

    public static BookSpiritFireRecipePageModel create() {
        return new BookSpiritFireRecipePageModel();
    }

    @Override
    protected BookPage createPage(BookRecipePage.JsonDataHolder common) {
        return new BookSpiritFireRecipePage(common);
    }
}
