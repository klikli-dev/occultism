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

import com.klikli_dev.occultism.common.misc.WeightedOutputIngredient;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class MinerRecipe implements Recipe<RecipeWrapper> {

    public static final MapCodec<MinerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            Ingredient.CODEC.fieldOf("result").forGetter(r -> r.output.getIngredient()),
            Codec.INT.fieldOf("weight").forGetter(r -> r.output.weight())
    ).apply(instance, (input, output, weight) -> {
        return new MinerRecipe(input, new WeightedOutputIngredient(output, weight));
    }));

    public static final StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.output.getIngredient(),
            ByteBufCodecs.INT,
            (r) -> r.output.weight(),
            (input, output, weight) -> new MinerRecipe(input, new WeightedOutputIngredient(output, weight))
    );

    public static Serializer SERIALIZER = new Serializer();
    protected final Ingredient input;
    protected final WeightedOutputIngredient output;

    public MinerRecipe(Ingredient input, WeightedOutputIngredient output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public WeightedOutputIngredient getWeightedOutput() {
        return this.output;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        //we only ever use one slot, and we only support miners, so return true.
        return true;
    }

    @Override
    public ItemStack assemble(RecipeWrapper pCraftingContainer, HolderLookup.Provider pRegistries) {
        return this.getResultItem(pRegistries).copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.output.getStack();
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