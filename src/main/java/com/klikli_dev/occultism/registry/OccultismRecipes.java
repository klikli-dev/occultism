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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.*;
import com.klikli_dev.occultism.crafting.recipe.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OccultismRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
            ForgeRegistries.RECIPE_TYPES, Occultism.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, Occultism.MODID);

    public static final RegistryObject<RecipeType<SpiritTradeRecipe>> SPIRIT_TRADE_TYPE = registerRecipeType("spirit_trade");
    public static final RegistryObject<RecipeType<SpiritFireRecipe>> SPIRIT_FIRE_TYPE = registerRecipeType("spirit_fire");
    public static final RegistryObject<RecipeType<CrushingRecipe>> CRUSHING_TYPE = registerRecipeType("crushing");
    public static final RegistryObject<RecipeType<MinerRecipe>> MINER_TYPE = registerRecipeType("miner");
    public static final RegistryObject<RecipeType<RitualRecipe>> RITUAL_TYPE = registerRecipeType("ritual");

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


    static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(final String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

}
