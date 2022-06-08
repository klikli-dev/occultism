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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.crafting.recipe.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OccultismRecipes {
    //region Fields
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, Occultism.MODID);

    //we now need to call get on each of those in RegistryEventHandler.onRegisterRecipeSerializers event
    //  if they are first accessed (and thus registered) only during recipe loading the registry will already be frozen and throw
    public static final NonNullLazy<RecipeType<SpiritTradeRecipe>> SPIRIT_TRADE_TYPE =
            NonNullLazy.of(() -> RecipeType.register("occultism:spirit_trade"));
    public static final NonNullLazy<RecipeType<SpiritFireRecipe>> SPIRIT_FIRE_TYPE =
            NonNullLazy.of(() -> RecipeType.register("occultism:spirit_fire"));
    public static final NonNullLazy<RecipeType<CrushingRecipe>> CRUSHING_TYPE =
            NonNullLazy.of(() -> RecipeType.register("occultism:crushing"));
    public static final NonNullLazy<RecipeType<MinerRecipe>> MINER_TYPE =
            NonNullLazy.of(() -> RecipeType.register("occultism:miner"));
    public static final NonNullLazy<RecipeType<RitualRecipe>> RITUAL_TYPE =
            NonNullLazy.of(() -> RecipeType.register("occultism:ritual"));

    public static final RegistryObject<RecipeSerializer<SpiritTradeRecipe>> SPIRIT_TRADE = RECIPES.register("spirit_trade",
            () -> SpiritTradeRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<SpiritFireRecipe>> SPIRIT_FIRE = RECIPES.register("spirit_fire",
            () -> SpiritFireRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<CrushingRecipe>> CRUSHING = RECIPES.register("crushing",
            () -> CrushingRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<MinerRecipe>> MINER = RECIPES.register("miner",
            () -> MinerRecipe.SERIALIZER);

    public static final RegistryObject<RecipeSerializer<RitualRecipe>> RITUAL = RECIPES.register("ritual",
            () -> RitualRecipe.SERIALIZER);

    //endregion Fields

}
