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

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.List;

public class OccultismFoods {
    // NOTE: Minecraft 26.1 moved food effect handling to the Consumable component API.
    // To keep compilation simple we provide FoodProperties instances here which are
    // accepted by Item.Properties.food(...). Effects can be migrated to Consumable
    // later if desired.
    public static final Lazy<FoodProperties> DATURA = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());

    public static final Lazy<FoodProperties> DEMONS_DREAM_ESSENCE = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());

    public static final Lazy<FoodProperties> OTHERWORLD_ESSENCE = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());

    public static final Lazy<FoodProperties> BEAVER_NUGGET = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());
    public static final Lazy<FoodProperties> CURSED_HONEY = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());
    public static final Lazy<FoodProperties> SWEET_HONEY_HEART = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());
    public static final Lazy<FoodProperties> DEMONIC_MEAT = Lazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).build());
}
