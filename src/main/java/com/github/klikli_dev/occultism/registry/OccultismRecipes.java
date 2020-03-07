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
import com.github.klikli_dev.occultism.crafting.recipe.CustomShapeless;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritTrade;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismRecipes {
    //region Fields
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = new DeferredRegister<>(
            ForgeRegistries.RECIPE_SERIALIZERS, Occultism.MODID);

    public static final NonNullLazy<IRecipeType<SpiritTrade>> SPIRIT_TRADE_TYPE =
            NonNullLazy.of(() -> IRecipeType.register("occultism:spirit_trade"));
    public static final NonNullLazy<IRecipeType<?>> CUSTOM_SHAPELESS_TYPE =
            NonNullLazy.of(() -> IRecipeType.CRAFTING);

    public static final RegistryObject<IRecipeSerializer<SpiritTrade>> SPIRIT_TRADE = RECIPES.register("spirit_trade",
            () -> SpiritTrade.SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<CustomShapeless>> CUSTOM_SHAPELESS = RECIPES.register("custom_shapeless",
            () -> CustomShapeless.SERIALIZER);

    //endregion Fields

}
