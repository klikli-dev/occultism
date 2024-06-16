/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.crafting.recipe;

import com.klikli_dev.occultism.crafting.recipe.input.ItemHandlerRecipeInput;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class MinerRecipe implements Recipe<ItemHandlerRecipeInput> {

    public static final MapCodec<MinerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            WeightedRecipeResult.CODEC.fieldOf("result").forGetter(r -> r.result)
    ).apply(instance, MinerRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            WeightedRecipeResult.STREAM_CODEC,
            (r) -> r.result,
            MinerRecipe::new
    );

    public static Serializer SERIALIZER = new Serializer();
    protected final Ingredient input;
    protected final WeightedRecipeResult result;

    public MinerRecipe(Ingredient input, WeightedRecipeResult result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public WeightedRecipeResult getWeightedResult() {
        return this.result;
    }

    @Override
    public boolean matches(ItemHandlerRecipeInput inv, Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        //we only ever use one slot, and we only support miners, so return true.
        return true;
    }

    @Override
    public ItemStack assemble(ItemHandlerRecipeInput pCraftingContainer, HolderLookup.Provider pRegistries) {
        return this.getResultItem(pRegistries).copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result.getStack();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.MINER_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<MinerRecipe> {

        @Override
        public MapCodec<MinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}