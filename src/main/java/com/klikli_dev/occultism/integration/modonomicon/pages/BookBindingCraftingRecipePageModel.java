/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BookBindingCraftingRecipePageModel extends BookRecipePageModel {

    public static final String RECIPE_ID = "occultism:crafting/bound_book_of_binding";
    protected ItemStack unboundBook = ItemStack.EMPTY;

    protected BookBindingCraftingRecipePageModel(@NotNull String anchor, @NotNull BookConditionModel condition) {
        super(OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE, anchor, condition);
    }

    public static BookBindingCraftingRecipePageModel.Builder builder() {
        return new Builder();
    }

    @Override
    public JsonObject toJson() {
        var json = super.toJson();
        var unboundBookJson = new JsonObject();
        unboundBookJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.unboundBook.getItem()).toString());

        json.add("unbound_book", unboundBookJson);

        return json;
    }

    public static class Builder extends BookRecipePageModel.Builder<Builder> {
        protected ItemStack unboundBook = ItemStack.EMPTY;

        protected Builder() {
            super();
        }

        public BookBindingCraftingRecipePageModel build() {
            var model = new BookBindingCraftingRecipePageModel(this.anchor, this.condition);
            model.title1 = this.title1;
            model.recipeId1 = this.recipeId1;
            model.title2 = this.title2;
            model.recipeId2 = this.recipeId2;
            model.text = this.text;
            model.unboundBook = this.unboundBook;
            return model;
        }

        public ItemStack getUnboundBook() {
            return this.unboundBook;
        }

        public Builder withUnboundBook(ItemStack unboundBook) {
            this.unboundBook = unboundBook;
            return this;
        }

        public Builder withRecipeId1() {
            this.recipeId1 = RECIPE_ID;
            return this;
        }


        public Builder withRecipeId2() {
            this.recipeId2 = RECIPE_ID;
            return this;
        }


        @Override
        public Builder getThis() {
            return this;
        }
    }
}
