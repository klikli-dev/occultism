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

package com.klikli_dev.occultism.common.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class WeightedOutputIngredient extends WeightedEntry.IntrusiveBase {
    protected OutputIngredient ingredient;
    protected int weight;

    public WeightedOutputIngredient(Ingredient ingredient, int itemWeightIn) {
        super(itemWeightIn);
        this.ingredient = OutputIngredient.of(ingredient);
        this.weight = itemWeightIn;
    }

    public int weight() {
        return this.weight;
    }

    public ItemStack getStack() {
        return this.ingredient.getStack();
    }

    public Ingredient getIngredient() {
        return this.ingredient.getIngredient();
    }

    public int count() {
        return this.ingredient.count();
    }
    public CompoundTag nbt() {
        return this.ingredient.nbt();
    }
}
