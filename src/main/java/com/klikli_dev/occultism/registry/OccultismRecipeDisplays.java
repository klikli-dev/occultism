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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.display.RitualRecipeDisplay;
import com.klikli_dev.occultism.crafting.recipe.display.SpiritFireRecipeDisplay;
import com.klikli_dev.occultism.crafting.recipe.display.SpiritTradeRecipeDisplay;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay.Type;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismRecipeDisplays {
    public static final DeferredRegister<Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(Registries.RECIPE_DISPLAY, Occultism.MODID);

    public static final Supplier<Type<SpiritFireRecipeDisplay>> SPIRIT_FIRE = RECIPE_DISPLAYS.register("spirit_fire",
            () -> new Type<>(SpiritFireRecipeDisplay.CODEC, SpiritFireRecipeDisplay.STREAM_CODEC));

    public static final Supplier<Type<SpiritTradeRecipeDisplay>> SPIRIT_TRADE = RECIPE_DISPLAYS.register("spirit_trade",
            () -> new Type<>(SpiritTradeRecipeDisplay.CODEC, SpiritTradeRecipeDisplay.STREAM_CODEC));

    public static final Supplier<Type<RitualRecipeDisplay>> RITUAL = RECIPE_DISPLAYS.register("ritual",
            () -> new Type<>(RitualRecipeDisplay.CODEC, RitualRecipeDisplay.STREAM_CODEC));
}
