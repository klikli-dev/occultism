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
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
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
    public boolean matches(@Nonnull CraftingContainer inventory, @Nonnull Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventoryCrafting, RegistryAccess access) {
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

        public static Codec<SpiritTradeRecipe> CODEC = RecipeSerializer.SHAPELESS_RECIPE.codec().flatXmap(
                shapeless -> {
                    var recipe = new SpiritTradeRecipe(shapeless.getGroup(), shapeless.getResultItem(RegistryAccess.EMPTY), shapeless.getIngredients());
                    return DataResult.success(recipe);
                }
                ,
                trade -> {
                    var recipe = new ShapelessRecipe(trade.getGroup(), CraftingBookCategory.MISC, trade.getResultItem(RegistryAccess.EMPTY), trade.getIngredients());
                    return DataResult.success(recipe);
                }
        );

        @Override
        public Codec<SpiritTradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public SpiritTradeRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            //noinspection deprecation
            return pBuffer.readWithCodecTrusted(NbtOps.INSTANCE, CODEC);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SpiritTradeRecipe pRecipe) {
            //noinspection deprecation
            pBuffer.writeWithCodec(NbtOps.INSTANCE, CODEC, pRecipe);
        }

    }
}
