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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.theurgy.content.recipe.DistillationRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

import java.util.Optional;

public class CrushingRecipe extends ItemStackFakeInventoryRecipe {

    public static int DEFAULT_CRUSHING_TIME = 200;

    public static final Codec<CrushingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            Ingredient.CODEC.fieldOf("result").forGetter(r -> r.output.getIngredient()),
            OutputIngredient.OutputStackInfo.CODEC.fieldOf("result").forGetter(r -> r.output.getOutputStackInfo()),
            Codec.INT.optionalFieldOf("min_tier", -1).forGetter(r -> r.minTier),
            Codec.INT.optionalFieldOf("crushing_time", DEFAULT_CRUSHING_TIME).forGetter(r -> r.crushingTime),
            Codec.BOOL.optionalFieldOf("ignore_crushing_multiplier", false).forGetter(r -> r.ignoreCrushingMultiplier)
    ).apply(instance, (input, output, outputStackInfo, minTier, crushingTime, ignoreCrushingMultiplier) -> {
        return new CrushingRecipe(input, new OutputIngredient(output, outputStackInfo), minTier, crushingTime, ignoreCrushingMultiplier);
    }));

    public static final Codec<CrushingRecipe> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC
                    .fieldOf("ingredient").forGetter((r) -> r.input),
            Ingredient.CODEC.fieldOf("result").forGetter(r -> r.output.getIngredient()),
            OutputIngredient.OutputStackInfo.CODEC.fieldOf("result_stack_info").forGetter(r -> r.output.getOutputStackInfo()),
            Codec.INT.optionalFieldOf("min_tier", -1).forGetter(r -> r.minTier),
            Codec.INT.optionalFieldOf("crushing_time", DEFAULT_CRUSHING_TIME).forGetter(r -> r.crushingTime),
            Codec.BOOL.optionalFieldOf("ignore_crushing_multiplier", false).forGetter(r -> r.ignoreCrushingMultiplier)
    ).apply(instance, (input, output, outputStackInfo, minTier, crushingTime, ignoreCrushingMultiplier) -> {
        return new CrushingRecipe(input, new OutputIngredient(output, outputStackInfo), minTier, crushingTime, ignoreCrushingMultiplier);
    }));

    public static Serializer SERIALIZER = new Serializer();

    protected final int crushingTime;
    protected final int minTier;
    protected final boolean ignoreCrushingMultiplier;

    protected OutputIngredient output;

    public CrushingRecipe(Ingredient input, OutputIngredient output, int minTier, int crushingTime, boolean ignoreCrushingMultiplier) {
        super(input, ItemStack.EMPTY); //hand over empty item stack, because we cannot resolve output.getStack() yet as tags are not resolved yet.
        this.output = output;
        this.crushingTime = crushingTime;
        this.minTier = minTier;
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

    @Override
    public boolean matches(ItemStackFakeInventory inv, Level level) {
        if (inv instanceof TieredItemStackFakeInventory tieredInv) {
            return tieredInv.getTier() >= this.minTier && this.input.test(inv.getItem(0));
        }

        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.getStack();
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
        public Codec<CrushingRecipe> codec() {
            return CODEC;
        }

        @Override
        public CrushingRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            //noinspection deprecation
            return pBuffer.readWithCodecTrusted(NbtOps.INSTANCE, NETWORK_CODEC);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrushingRecipe pRecipe) {
            //noinspection deprecation
            pBuffer.writeWithCodec(NbtOps.INSTANCE, NETWORK_CODEC, pRecipe);
        }
    }
}