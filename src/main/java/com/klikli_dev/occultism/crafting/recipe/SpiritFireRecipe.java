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

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class SpiritFireRecipe extends SingleInputRecipe<SingleRecipeInput> {

    public static final MapCodec<SpiritFireRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(r -> r.output)
    ).apply(instance, SpiritFireRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritFireRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            ItemStack.OPTIONAL_STREAM_CODEC,
            (r) -> r.output,
            SpiritFireRecipe::new
    );
    public static Serializer SERIALIZER = new Serializer();

    public SpiritFireRecipe(Ingredient input, ItemStack output) {
        super(input, output);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput pCraftingContainer, HolderLookup.Provider pRegistries) {
        ItemStack result = this.getResultItem(pRegistries).copy();
        result.setCount(pCraftingContainer.getItem(0).getCount());
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        //as we don't have a real inventory so this is ignored.
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.output;
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
    public ItemStack getToastSymbol() {
        return new ItemStack(OccultismBlocks.SPIRIT_FIRE.get());
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.SPIRIT_FIRE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SpiritFireRecipe> {

        @Override
        public MapCodec<SpiritFireRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritFireRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}