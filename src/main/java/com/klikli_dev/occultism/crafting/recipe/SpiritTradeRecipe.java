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

import com.klikli_dev.occultism.registry.OccultismItems;
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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritTradeRecipe extends SingleInputRecipe<SingleRecipeInput> {

    public static final MapCodec<SpiritTradeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(r -> r.output)
    ).apply(instance, SpiritTradeRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritTradeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            ItemStack.OPTIONAL_STREAM_CODEC,
            (r) -> r.output,
            SpiritTradeRecipe::new
    );
    public static Serializer SERIALIZER = new Serializer();

    public SpiritTradeRecipe(Ingredient input, ItemStack output) {
        super(input, output);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(SingleRecipeInput inv, @NotNull Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(SingleRecipeInput pCraftingContainer, HolderLookup.@NotNull Provider pRegistries) {
        ItemStack result = this.getResultItem(pRegistries).copy();
        result.setCount(pCraftingContainer.getItem(0).getCount());
        return result;
    }

    /**
     * Consumes the given input and returns all unused items. isValid needs to be called first!
     *
     * @param input the input to consume.
     * @return the remaining items.
     */
    public List<ItemStack> consume(List<ItemStack> input) {
        //deep copy, otherwise stack.shrink will eat original input.
        List<ItemStack> result = input.stream().map(ItemStack::copy).collect(Collectors.toList());
        for (Ingredient ingredient : this.getIngredients()) {
            for (Iterator<ItemStack> it = result.iterator(); it.hasNext(); ) {
                ItemStack stack = it.next();
                if (ingredient.test(stack)) {
                    stack.shrink(1);
                    if (stack.isEmpty())
                        it.remove();
                }
            }
        }
        return result;
    }

    public boolean isValid(ItemStack... input) {
        return this.isValid(Arrays.asList(input));
    }

    public boolean isValid(List<ItemStack> input) {
        //deep copy, otherwise stack.shrink will eat original input.
        //we use collect(collectors.toList), because toList() would return an unmodifiable list
        //noinspection SimplifyStreamApiCallChains
        List<ItemStack> cached = input.stream().map(ItemStack::copy).collect(Collectors.toList());
        for (Ingredient ingredient : this.getIngredients()) {
            boolean matched = false;
            for (Iterator<ItemStack> it = cached.iterator(); it.hasNext(); ) {
                ItemStack stack = it.next();
                if (ingredient.test(stack)) {
                    matched = true;
                    stack.shrink(1);
                    if (stack.isEmpty())
                        it.remove();
                }
            }
            if (!matched)
                return false;
        }
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(OccultismItems.PENTACLE.get());
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return OccultismRecipes.SPIRIT_TRADE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SpiritTradeRecipe> {

        @Override
        public @NotNull MapCodec<SpiritTradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SpiritTradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
