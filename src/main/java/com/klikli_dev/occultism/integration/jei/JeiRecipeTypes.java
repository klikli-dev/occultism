/*
 * MIT License
 *
 * Copyright 2022 klikli-dev
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

package com.klikli_dev.occultism.integration.jei;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.*;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class JeiRecipeTypes {

    public static final RecipeType<RecipeHolder<SpiritTradeRecipe>> SPIRIT_TRADE =
            create(Occultism.MODID, "spirit_trade", SpiritTradeRecipe.class);
    public static final RecipeType<RecipeHolder<SpiritFireRecipe>> SPIRIT_FIRE =
            create(Occultism.MODID, "spirit_fire", SpiritFireRecipe.class);
    public static final RecipeType<RecipeHolder<CrushingRecipe>> CRUSHING =
            create(Occultism.MODID, "crushing", CrushingRecipe.class);
    public static final RecipeType<RecipeHolder<MinerRecipe>> MINER =
            create(Occultism.MODID, "miner", MinerRecipe.class);
    public static final RecipeType<RecipeHolder<RitualRecipe>> RITUAL =
            create(Occultism.MODID, "ritual", RitualRecipe.class);

    public static <R extends Recipe<?>> RecipeType<RecipeHolder<R>> create(String modid, String name, Class<? extends R> recipeClass) {
        ResourceLocation uid = new ResourceLocation(modid, name);
        @SuppressWarnings({"unchecked", "RedundantCast"})
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return new RecipeType<>(uid, holderClass);
    }
}
