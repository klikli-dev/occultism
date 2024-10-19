/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    public BookRitualRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor, BookCondition condition) {
        super(OccultismRecipes.RITUAL_TYPE.get(), title1, recipeId1, title2, recipeId2, text, anchor, condition);
    }

    public static BookRitualRecipePage fromJson(ResourceLocation conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(json, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(conditionParentId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        return new BookRitualRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition);
    }

    public static BookRitualRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        return new BookRitualRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition);
    }

    @Override
    public ResourceLocation getType() {
        return OccultismModonomiconConstants.Page.RITUAL_RECIPE;
    }

    @Override
    protected ItemStack getRecipeOutput(Level level, RecipeHolder<RitualRecipe> recipe) {
        if (recipe == null) {
            return ItemStack.EMPTY;
        }

        return recipe.value().getRitualDummy();
    }
}
