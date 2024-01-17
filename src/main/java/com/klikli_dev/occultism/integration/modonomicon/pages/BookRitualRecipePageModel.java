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
import org.jetbrains.annotations.NotNull;

public class BookRitualRecipePageModel extends BookRecipePageModel {
    protected BookRitualRecipePageModel(@NotNull String anchor) {
        super(OccultismModonomiconConstants.Page.RITUAL_RECIPE, anchor);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BookRecipePageModel.Builder<Builder> {
        protected Builder() {
            super();
        }

        public BookRitualRecipePageModel build() {
            var model = new BookRitualRecipePageModel(this.anchor);
            model.title1 = this.title1;
            model.recipeId1 = this.recipeId1;
            model.title2 = this.title2;
            model.recipeId2 = this.recipeId2;
            model.text = this.text;
            return model;
        }

        @Override
        public Builder withTitle2(String title) {
            throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
        }

        @Override
        public Builder withTitle2(Component title) {
            throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
        }

        @Override
        public Builder withRecipeId2(String recipeId) {
            throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
        }

        @Override
        public Builder withRecipeId2(ResourceLocation recipeId) {
            throw new RuntimeException("Ritual recipe pages do not support a second recipe!");
        }

        @Override
        public Builder getThis() {
            return this;
        }
    }
}
