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

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CrushingRecipe extends SingleInputRecipe<TieredSingleRecipeInput> {

    public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            (r) -> r.input,
            RecipeResult.STREAM_CODEC,
            (r) -> r.result,
            ByteBufCodecs.INT,
            (r) -> r.minTier,
            ByteBufCodecs.INT,
            (r) -> r.maxTier,
            ByteBufCodecs.INT,
            (r) -> r.crushingTime,
            ByteBufCodecs.BOOL,
            (r) -> r.ignoreCrushingMultiplier,
            CrushingRecipe::new
    );
    public static int DEFAULT_CRUSHING_TIME = 200;
    public static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            RecipeResult.CODEC.fieldOf("result").forGetter(r -> r.result),
            Codec.INT.optionalFieldOf("min_tier", -1).forGetter(r -> r.minTier),
            Codec.INT.optionalFieldOf("max_tier", -1).forGetter(r -> r.maxTier),
            Codec.INT.optionalFieldOf("crushing_time", DEFAULT_CRUSHING_TIME).forGetter(r -> r.crushingTime),
            Codec.BOOL.optionalFieldOf("ignore_crushing_multiplier", false).forGetter(r -> r.ignoreCrushingMultiplier)
    ).apply(instance, CrushingRecipe::new));

    public static Serializer SERIALIZER = new Serializer();

    protected final int crushingTime;
    protected final int minTier;
    protected final int maxTier;
    protected final boolean ignoreCrushingMultiplier;

    protected RecipeResult result;

    public CrushingRecipe(Ingredient input, RecipeResult result, int minTier, int maxTier, int crushingTime, boolean ignoreCrushingMultiplier) {
        super(input, ItemStack.EMPTY); //hand over empty item stack, because we cannot resolve output.getStack() yet as tags are not resolved yet.
        this.result = result;
        this.crushingTime = crushingTime;
        this.minTier = minTier;
        this.maxTier = maxTier;
        this.ignoreCrushingMultiplier = ignoreCrushingMultiplier;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public int getCrushingTime() {
        return this.crushingTime;
    }

    public boolean getIgnoreCrushingMultiplier() {
        return this.ignoreCrushingMultiplier;
    }

    public int getMinTier() {
        return this.minTier;
    }

    public int getMaxTier() {
        return this.maxTier;
    }

    @Override
    public boolean matches(TieredSingleRecipeInput inv, Level level) {
        boolean tierMatches = true;
        //tiers can be -1 in which case they are ignored, only if >= 0 we check
        if (this.minTier >= 0 && this.maxTier >= 0) {
            tierMatches = inv.tier() >= this.minTier && inv.tier() <= this.maxTier;
        } else if (this.minTier >= 0) {
            tierMatches = inv.tier() >= this.minTier;
        } else if (this.maxTier >= 0) {
            tierMatches = inv.tier() <= this.maxTier;
        }
        return tierMatches && this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result.getStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return OccultismRecipes.CRUSHING_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<CrushingRecipe> {

        @Override
        public MapCodec<CrushingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}