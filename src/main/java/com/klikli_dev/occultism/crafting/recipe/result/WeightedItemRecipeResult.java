// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.klikli_dev.occultism.registry.OccultismRecipeResults;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * A tag result for recipes that use tags as output.
 */
public class WeightedItemRecipeResult extends WeightedRecipeResult {

    public static final MapCodec<WeightedItemRecipeResult> CODEC =
            RecordCodecBuilder.mapCodec((builder) -> builder.group(
                    ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(WeightedItemRecipeResult::getStack),
                    Codec.INT.fieldOf("weight").forGetter(WeightedItemRecipeResult::weight)
            ).apply(builder, WeightedItemRecipeResult::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, WeightedItemRecipeResult> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            WeightedItemRecipeResult::getStack,
            ByteBufCodecs.INT,
            WeightedItemRecipeResult::weight,
            WeightedItemRecipeResult::new
    );

    private final ItemStack stack;

    @Nullable
    private ItemStack[] cachedStacks;

    public WeightedItemRecipeResult(ItemStack stack, int weight) {
        super(weight);
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public ItemStack[] getStacks() {
        if (this.cachedStacks == null) {
            this.cachedStacks = new ItemStack[]{this.stack};
        }
        return this.cachedStacks;
    }

    @Override
    public RecipeResultType<?> getType() {
        return OccultismRecipeResults.WEIGHTED_ITEM.get();
    }

    @Override
    public WeightedItemRecipeResult copyWithCount(int count) {
        return new WeightedItemRecipeResult(this.stack.copyWithCount(count), this.weight.asInt());
    }

    @Override
    public WeightedItemRecipeResult copyWithWeight(int weight) {
        return new WeightedItemRecipeResult(this.stack, weight);
    }
}
