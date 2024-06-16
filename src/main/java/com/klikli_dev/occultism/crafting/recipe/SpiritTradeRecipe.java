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

import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritTradeRecipe extends ShapelessRecipe {

    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritTradeRecipe> STREAM_CODEC = StreamCodec.of(
            Serializer::toNetwork, Serializer::fromNetwork
    );
    private static final MapCodec<SpiritTradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
            p_340779_ -> p_340779_.group(
                            Codec.STRING.optionalFieldOf("group", "").forGetter(p_301127_ -> p_301127_.getGroup()),
                            CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301133_ -> p_301133_.category()),
                            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_301142_ -> p_301142_.getResultItem(RegistryAccess.EMPTY)),
                            Ingredient.CODEC_NONEMPTY
                                    .listOf()
                                    .fieldOf("ingredients")
                                    .flatXmap(
                                            p_301021_ -> {
                                                Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new); // Neo skip the empty check and immediately create the array.
                                                if (aingredient.length == 0) {
                                                    return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                } else {
                                                    return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()
                                                            ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
                                                            : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                                }
                                            },
                                            DataResult::success
                                    )
                                    .forGetter(p_300975_ -> p_300975_.getIngredients())
                    )
                    .apply(p_340779_, (group, category, result, ingredients) -> new SpiritTradeRecipe(group, result, ingredients))
    );
    public static Serializer SERIALIZER = new Serializer();

    public SpiritTradeRecipe(String group, ItemStack result, NonNullList<Ingredient> input) {
        super(group, CraftingBookCategory.MISC, result, input);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(@Nonnull CraftingInput inventory, @Nonnull Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput pCraftingContainer, HolderLookup.Provider pRegistries) {
        //as we don't have an inventory this is ignored.
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.SPIRIT_TRADE_TYPE.get();
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

    public static class Serializer implements RecipeSerializer<SpiritTradeRecipe> {

        //Codec copied from ShaplessRecipe, because xmap throws errors

        //Copied from Shapeless Recipe
        private static SpiritTradeRecipe fromNetwork(RegistryFriendlyByteBuf p_319905_) {
            String s = p_319905_.readUtf();
            CraftingBookCategory craftingbookcategory = p_319905_.readEnum(CraftingBookCategory.class);
            int i = p_319905_.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll(p_319735_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(p_319905_));
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(p_319905_);
            return new SpiritTradeRecipe(s, itemstack, nonnulllist);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_320371_, SpiritTradeRecipe p_320323_) {
            p_320371_.writeUtf(p_320323_.getGroup());
            p_320371_.writeEnum(p_320323_.category());
            p_320371_.writeVarInt(p_320323_.getIngredients().size());

            for (Ingredient ingredient : p_320323_.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(p_320371_, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(p_320371_, p_320323_.getResultItem(RegistryAccess.EMPTY));
        }

        @Override
        public MapCodec<SpiritTradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritTradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
