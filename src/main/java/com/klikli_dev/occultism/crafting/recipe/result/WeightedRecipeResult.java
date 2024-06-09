// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * A recipe result for recipes that need a random weight (eg miner recipes)
 */
public abstract class WeightedRecipeResult extends RecipeResult implements WeightedEntry {

    public static final Codec<WeightedRecipeResult> CODEC = RecipeResult.CODEC.validate(r -> {
        if (!(r instanceof WeightedRecipeResult))
            return DataResult.error(() -> "Not a weighted recipe result");
        return DataResult.success(r);
    }).xmap(r -> (WeightedRecipeResult) r, r -> r);

    public static final StreamCodec<RegistryFriendlyByteBuf, WeightedRecipeResult> STREAM_CODEC = RecipeResult.STREAM_CODEC.map(r -> (WeightedRecipeResult) r, r -> r);
    protected final Weight weight;

    public WeightedRecipeResult(int weight) {
        this.weight = Weight.of(weight);
    }

    public static WeightedRecipeResult of(ItemStack stack, int weight) {
        return new WeightedItemRecipeResult(stack, weight);
    }

    public static WeightedRecipeResult of(TagKey<Item> tag, int weight) {
        return new WeightedTagRecipeResult(tag, 1, weight);
    }

    public static WeightedRecipeResult of(TagKey<Item> tag, int count, int weight) {
        return new WeightedTagRecipeResult(tag, count, weight);
    }

    public static WeightedRecipeResult of(TagKey<Item> tag, int count, DataComponentPatch patch, int weight) {
        return new WeightedTagRecipeResult(tag, count, patch, weight);
    }

    public int weight() {
        return this.weight.asInt();
    }

    public abstract RecipeResult copyWithWeight(int weight);

    @Override
    public Weight getWeight() {
        return this.weight;
    }
}
