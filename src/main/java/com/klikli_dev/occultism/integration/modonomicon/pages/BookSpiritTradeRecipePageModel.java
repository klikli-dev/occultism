/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;

public class BookSpiritTradeRecipePageModel extends BookRecipePageModel<BookSpiritTradeRecipePageModel> {
    protected BookSpiritTradeRecipePageModel() {
        super(BookSpiritTradeRecipePage.ID);
    }

    public static BookSpiritTradeRecipePageModel create() {
        return new BookSpiritTradeRecipePageModel();
    }

    @Override
    protected BookPage createPage(BookRecipePage.JsonDataHolder common) {
        return new BookSpiritTradeRecipePage(common);
    }

}
