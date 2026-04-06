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
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * A weighted recipe result that stores an ItemStackTemplate and lazily creates the ItemStack at runtime.
 */
public class WeightedItemRecipeResult extends WeightedRecipeResult {

    public static final MapCodec<WeightedItemRecipeResult> CODEC =
            RecordCodecBuilder.mapCodec((builder) -> builder.group(
                    ItemStackTemplate.CODEC.fieldOf("stack").forGetter(r -> r.template),
                    Codec.INT.fieldOf("weight").forGetter(WeightedItemRecipeResult::weight)
            ).apply(builder, WeightedItemRecipeResult::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, WeightedItemRecipeResult> STREAM_CODEC = StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC,
            r -> r.template,
            ByteBufCodecs.INT,
            WeightedItemRecipeResult::weight,
            WeightedItemRecipeResult::new
    );

    private final ItemStackTemplate template;

    @Nullable
    private ItemStack stack;

    @Nullable
    private ItemStack[] cachedStacks;

    public WeightedItemRecipeResult(ItemStackTemplate template, int weight) {
        super(weight);
        this.template = template;
    }

    public WeightedItemRecipeResult(ItemStack stack, int weight) {
        this(ItemStackTemplate.fromNonEmptyStack(stack), weight);
    }

    @Override
    public ItemStack getStack() {
        if (this.stack == null) {
            this.stack = this.template.create();
        }
        return this.stack;
    }

    @Override
    public ItemStack[] getStacks() {
        if (this.cachedStacks == null) {
            this.cachedStacks = new ItemStack[]{this.getStack()};
        }
        return this.cachedStacks;
    }

    @Override
    public RecipeResultType<?> getType() {
        return OccultismRecipeResults.WEIGHTED_ITEM.get();
    }

    @Override
    public WeightedItemRecipeResult copyWithCount(int count) {
        return new WeightedItemRecipeResult(this.template.withCount(count), this.weight);
    }

    @Override
    public WeightedItemRecipeResult copyWithWeight(int weight) {
        return new WeightedItemRecipeResult(this.template, weight);
    }
}
