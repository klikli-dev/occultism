// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.klikli_dev.occultism.registry.OccultismRecipeResults;
import com.klikli_dev.occultism.util.OccultismExtraCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * A recipe result that stores an ItemStackTemplate and lazily creates the ItemStack at runtime.
 */
public class ItemRecipeResult extends RecipeResult {

    public static final MapCodec<ItemRecipeResult> INGREDIENT_COMPAT_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(t -> t.template.item()),
            Codec.INT.fieldOf("count").forGetter(t -> t.template.count()),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(t -> t.template.components())
    ).apply(builder, (item, count, components) -> new ItemRecipeResult(new ItemStackTemplate(item, count, components))));

    public static final MapCodec<ItemRecipeResult> ITEM_STACK_COMPAT_CODEC = MapCodec.assumeMapUnsafe(ItemStackTemplate.CODEC.xmap(ItemRecipeResult::new, (ItemRecipeResult t) -> t.template));

    public static final MapCodec<ItemRecipeResult> CODEC = OccultismExtraCodecs.mapWithAlternative(
            ITEM_STACK_COMPAT_CODEC,
            INGREDIENT_COMPAT_CODEC
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemRecipeResult> STREAM_CODEC = StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC,
            (ItemRecipeResult t) -> t.template,
            ItemRecipeResult::new
    );

    private final ItemStackTemplate template;

    @Nullable
    private ItemStack stack;

    @Nullable
    private ItemStack[] cachedStacks;

    public ItemRecipeResult(ItemStackTemplate template) {
        this.template = template;
    }

    public ItemRecipeResult(ItemStack stack) {
        this(ItemStackTemplate.fromNonEmptyStack(stack));
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
        return OccultismRecipeResults.ITEM.get();
    }

    @Override
    public ItemRecipeResult copyWithCount(int count) {
        return new ItemRecipeResult(this.template.withCount(count));
    }
}
