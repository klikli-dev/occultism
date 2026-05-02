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

import com.klikli_dev.occultism.crafting.recipe.display.SpiritTradeRecipeDisplay;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.ItemSlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritTradeRecipe extends SingleInputRecipe<TraderRecipeInput> {
    public static final MapCodec<SpiritTradeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            WeightedRecipeResult.CODEC.fieldOf("result").forGetter(r -> r.result),
            Codec.STRING.fieldOf("trader_id").forGetter((r) -> r.trader)
    ).apply(instance, SpiritTradeRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritTradeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            WeightedRecipeResult.STREAM_CODEC,
            (r) -> r.result,
            ByteBufCodecs.STRING_UTF8,
            (r) -> r.trader,
            SpiritTradeRecipe::new
    );
    public static RecipeSerializer<SpiritTradeRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    protected final WeightedRecipeResult result;
    private final String trader;

    public SpiritTradeRecipe(Ingredient input, WeightedRecipeResult result, String trader) {
        super(input, ItemStack.EMPTY);
        this.result = result;
        this.trader = trader;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public WeightedRecipeResult getWeightedResult() {
        return this.result;
    }

    @Override
    public boolean matches(TraderRecipeInput inv, @NotNull Level level) {
        return this.input.test(inv.getItem(0)) && inv.trader().equals(this.trader);
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

    public ItemStack getResultItem() {
        return this.result.getStack();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<TraderRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<TraderRecipeInput>> getType() {
        return OccultismRecipes.SPIRIT_TRADE_TYPE.get();
    }

    public String getTrader() {
        return this.trader;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new SpiritTradeRecipeDisplay(
                this.input,
                ItemStackTemplate.fromNonEmptyStack(this.result.getStack()),
                new ItemSlotDisplay(OccultismBlocks.SPIRIT_FIRE.get().asItem())
        ));
    }

}
