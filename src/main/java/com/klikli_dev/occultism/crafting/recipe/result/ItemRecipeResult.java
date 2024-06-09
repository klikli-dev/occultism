// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.klikli_dev.occultism.registry.OccultismRecipeResults;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * A tag result for recipes that use tags as output.
 */
public class ItemRecipeResult extends RecipeResult {

    public static final MapCodec<ItemRecipeResult> CODEC = MapCodec.assumeMapUnsafe(ItemStack.STRICT_CODEC.xmap(ItemRecipeResult::new, ItemRecipeResult::getStack));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemRecipeResult> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            ItemRecipeResult::getStack,
            ItemRecipeResult::new
    );

    private final ItemStack stack;

    @Nullable
    private ItemStack[] cachedStacks;

    public ItemRecipeResult(ItemStack stack) {
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
        return OccultismRecipeResults.ITEM.get();
    }

    @Override
    public RecipeResult copyWithCount(int count) {
        return new ItemRecipeResult(this.stack.copyWithCount(count));
    }
}
