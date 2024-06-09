// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.result.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OccultismRecipeResults {
    public static final DeferredRegister<RecipeResultType<?>> RECIPE_RESULT_TYPES = DeferredRegister.create(
            OccultismRegistries.RECIPE_RESULT_TYPES, Occultism.MODID);


    public static final DeferredHolder<RecipeResultType<?>, RecipeResultType<ItemRecipeResult>> ITEM =
            RECIPE_RESULT_TYPES.register("item", () -> new RecipeResultType<>(ItemRecipeResult.CODEC, ItemRecipeResult.STREAM_CODEC));

    public static final DeferredHolder<RecipeResultType<?>, RecipeResultType<TagRecipeResult>> TAG =
            RECIPE_RESULT_TYPES.register("tag", () -> new RecipeResultType<>(TagRecipeResult.CODEC, TagRecipeResult.STREAM_CODEC));

    public static final DeferredHolder<RecipeResultType<?>, RecipeResultType<WeightedItemRecipeResult>> WEIGHTED_ITEM =
            RECIPE_RESULT_TYPES.register("weighted_item", () -> new RecipeResultType<>(WeightedItemRecipeResult.CODEC, WeightedItemRecipeResult.STREAM_CODEC));

    public static final DeferredHolder<RecipeResultType<?>, RecipeResultType<WeightedTagRecipeResult>> WEIGHTED_TAG =
            RECIPE_RESULT_TYPES.register("weighted_tag", () -> new RecipeResultType<>(WeightedTagRecipeResult.CODEC, WeightedTagRecipeResult.STREAM_CODEC));

}
