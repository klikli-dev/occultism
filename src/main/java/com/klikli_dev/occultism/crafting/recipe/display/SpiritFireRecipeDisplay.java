/*
 * MIT License
 *
 * Copyright 2024 klikli-dev
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

package com.klikli_dev.occultism.crafting.recipe.display;

import com.klikli_dev.occultism.registry.OccultismRecipeDisplays;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.ItemStackSlotDisplay;

public record SpiritFireRecipeDisplay(
        Ingredient ingredient,
        ItemStackTemplate output,
        SlotDisplay craftingStation
) implements RecipeDisplay {

    public static final MapCodec<SpiritFireRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(SpiritFireRecipeDisplay::ingredient),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(SpiritFireRecipeDisplay::output),
            SlotDisplay.CODEC.fieldOf("craftingStation").forGetter(SpiritFireRecipeDisplay::craftingStation)
    ).apply(instance, SpiritFireRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritFireRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            SpiritFireRecipeDisplay::ingredient,
            ItemStackTemplate.STREAM_CODEC,
            SpiritFireRecipeDisplay::output,
            SlotDisplay.STREAM_CODEC,
            SpiritFireRecipeDisplay::craftingStation,
            SpiritFireRecipeDisplay::new
    );

    @Override
    public Type<? extends RecipeDisplay> type() {
        return OccultismRecipeDisplays.SPIRIT_FIRE.get();
    }

    @Override
    public SlotDisplay result() {
        return new ItemStackSlotDisplay(this.output);
    }
}
