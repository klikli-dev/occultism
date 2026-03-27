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
import com.klikli_dev.modonomicon.book.page.BookRecipePage.JsonDataHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage.NetworkDataHolder;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class BookRitualRecipePage extends BookRecipePage<RitualRecipe> {
    public BookRitualRecipePage(JsonDataHolder data) {
        super(data);
    }

    public BookRitualRecipePage(NetworkDataHolder data) {
        super(data);
    }

    public static BookRitualRecipePage fromJson(Identifier conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        var common = BookRecipePage.commonFromJson(conditionParentId, json, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(conditionParentId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        return new BookRitualRecipePage(common);
    }

    public static BookRitualRecipePage fromNetwork(RegistryFriendlyByteBuf buffer){
        var common = BookRecipePage.commonFromNetwork(buffer);
        return new BookRitualRecipePage(common);
    }

    @Override
    public Identifier getType() {
        return OccultismModonomiconConstants.Page.RITUAL_RECIPE;
    }
}
