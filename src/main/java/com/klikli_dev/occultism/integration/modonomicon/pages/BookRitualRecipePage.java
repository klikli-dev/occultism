/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    public BookRitualRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor) {
        super(OccultismRecipes.RITUAL_TYPE.get(), title1, recipeId1, title2, recipeId2, text, anchor);
    }

    public static BookRitualRecipePage fromJson(JsonObject json) {
        var common = BookRecipePage.commonFromJson(json);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        return new BookRitualRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    public static BookRitualRecipePage fromNetwork(FriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        return new BookRitualRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    @Override
    public ResourceLocation getType() {
        return OccultismModonomiconConstants.Page.RITUAL_RECIPE;
    }

    @Override
    protected ItemStack getRecipeOutput(RitualRecipe recipe) {
        if (recipe == null) {
            return ItemStack.EMPTY;
        }

        return recipe.getRitualDummy();
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeUtf(this.anchor);
    }
}
