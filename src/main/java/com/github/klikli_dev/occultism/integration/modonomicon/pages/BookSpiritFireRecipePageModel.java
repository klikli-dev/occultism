/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.github.klikli_dev.occultism.integration.modonomicon.pages;

import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModnomiconConstants.Page;
import com.klikli_dev.modonomicon.datagen.book.page.BookRecipePageModel;
import org.jetbrains.annotations.NotNull;

public class BookSpiritFireRecipePageModel extends BookRecipePageModel {
    protected BookSpiritFireRecipePageModel(@NotNull String anchor) {
        super(Page.SPIRIT_FIRE_RECIPE, anchor);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BookRecipePageModel.Builder<Builder> {
        protected Builder() {
            super();
        }

        public BookSpiritFireRecipePageModel build() {
            var model = new BookSpiritFireRecipePageModel(this.anchor);
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
