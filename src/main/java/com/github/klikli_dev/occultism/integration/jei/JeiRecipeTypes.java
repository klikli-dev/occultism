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

package com.github.klikli_dev.occultism.integration.jei;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.crafting.recipe.*;
import mezz.jei.api.recipe.RecipeType;

public class JeiRecipeTypes {
    public static final RecipeType<SpiritTradeRecipe> SPIRIT_TRADE =
            RecipeType.create(Occultism.MODID, "spirit_trade", SpiritTradeRecipe.class);
    public static final RecipeType<SpiritFireRecipe> SPIRIT_FIRE =
            RecipeType.create(Occultism.MODID, "spirit_fire", SpiritFireRecipe.class);
    public static final RecipeType<CrushingRecipe> CRUSHING =
            RecipeType.create(Occultism.MODID, "crushing", CrushingRecipe.class);
    public static final RecipeType<MinerRecipe> MINER =
            RecipeType.create(Occultism.MODID, "miner", MinerRecipe.class);
    public static final RecipeType<RitualRecipe> RITUAL =
            RecipeType.create(Occultism.MODID, "ritual", RitualRecipe.class);
}
