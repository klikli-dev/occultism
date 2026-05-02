/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;

public class BookSpiritTradeRecipePageModel extends BookRecipePageModel<BookSpiritTradeRecipePageModel> {
    protected BookSpiritTradeRecipePageModel() {
        super(Page.SPIRIT_TRADE_RECIPE);
    }

    public static BookSpiritTradeRecipePageModel create() {
        return new BookSpiritTradeRecipePageModel();
    }

}
