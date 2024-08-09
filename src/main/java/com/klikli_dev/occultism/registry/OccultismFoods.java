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
import net.minecraftforge.common.util.NonNullLazy;

public class OccultismFoods {
    public static final NonNullLazy<FoodProperties> DATURA = NonNullLazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationMod(0).alwaysEat()
                    .effect(() -> new MobEffectInstance(OccultismEffects.THIRD_EYE.get(), 15 * 20, 1), 0.7f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 15 * 20, 1), 1.0f)
                    .build());

    public static final NonNullLazy<FoodProperties> DEMONS_DREAM_ESSENCE = NonNullLazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationMod(0).alwaysEat()
                    .effect(() -> new MobEffectInstance(OccultismEffects.THIRD_EYE.get(), 60 * 20, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 15 * 20, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, 15 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 15 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK,  5 * 60 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.UNLUCK,  5 * 60 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION,  15 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS,  15 * 20, 1), 0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION,  15 * 20, 1), 0.2f)
                    .build());

    public static final NonNullLazy<FoodProperties> OTHERWORLD_ESSENCE = NonNullLazy.of(
            () -> new FoodProperties.Builder().nutrition(0).saturationMod(0).alwaysEat()
                    .effect(() -> new MobEffectInstance(OccultismEffects.THIRD_EYE.get(), 60 * 20, 1), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK,  5* 60 * 20, 1), 1.0f)
                    .build());

    public static final NonNullLazy<FoodProperties> BEAVER_NUGGET = NonNullLazy.of(
            () ->  new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build());
}
