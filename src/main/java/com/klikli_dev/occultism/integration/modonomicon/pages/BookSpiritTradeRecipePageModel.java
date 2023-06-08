/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import org.jetbrains.annotations.NotNull;

public class BookSpiritTradeRecipePageModel extends BookRecipePageModel {
    protected BookSpiritTradeRecipePageModel(@NotNull String anchor) {
        super(OccultismModonomiconConstants.Page.SPIRIT_TRADE_RECIPE, anchor);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BookRecipePageModel.Builder<Builder> {
        protected Builder() {
            super();
        }

        public BookSpiritTradeRecipePageModel build() {
            var model = new BookSpiritTradeRecipePageModel(this.anchor);
            model.title1 = this.title1;
            model.recipeId1 = this.recipeId1;
            model.title2 = this.title2;
            model.recipeId2 = this.recipeId2;
            model.text = this.text;
            return model;
        }

        @Override
        public Builder getThis() {
            return this;
        }
    }
}
