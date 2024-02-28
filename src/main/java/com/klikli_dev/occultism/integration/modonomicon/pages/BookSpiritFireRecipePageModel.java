/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.datagen.book.condition.BookConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;

public class BookSpiritFireRecipePageModel extends BookRecipePageModel<BookSpiritFireRecipePageModel> {
    protected BookSpiritFireRecipePageModel() {
        super(OccultismModonomiconConstants.Page.SPIRIT_FIRE_RECIPE);
    }

    public static BookSpiritFireRecipePageModel create() {
        return new BookSpiritFireRecipePageModel();
    }
}
