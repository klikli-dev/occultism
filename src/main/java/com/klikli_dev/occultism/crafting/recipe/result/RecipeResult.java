// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.klikli_dev.occultism.registry.OccultismRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public abstract class RecipeResult {

    public static final Codec<RecipeResult> CODEC = OccultismRegistries.RECIPE_RESULT_TYPES.byNameCodec().dispatch(RecipeResult::getType, RecipeResultType::codec);

    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeResult> STREAM_CODEC = ByteBufCodecs.registry(OccultismRegistries.Keys.RECIPE_RESULT_TYPES).dispatch(RecipeResult::getType, RecipeResultType::streamCodec);

    public static RecipeResult of(ItemStack stack) {
        return new ItemRecipeResult(stack);
    }

    public static RecipeResult of(TagKey<Item> tag) {
        return new TagRecipeResult(tag, 1);
    }

    public static RecipeResult of(TagKey<Item> tag, int count) {
        return new TagRecipeResult(tag, count);
    }

    public static RecipeResult of(TagKey<Item> tag, int count, DataComponentPatch patch) {
        return new TagRecipeResult(tag, count, patch);
    }

    /**
     * Get the preferred item stack this result represents.
     */
    public abstract ItemStack getStack();

    /**
     * Get all item stacks this result represents.
     */
    public abstract ItemStack[] getStacks();

    public abstract RecipeResultType<?> getType();

    public abstract RecipeResult copyWithCount(int count);
}
