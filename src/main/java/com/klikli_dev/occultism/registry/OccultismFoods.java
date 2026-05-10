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
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.neoforge.common.util.Lazy;

public class OccultismFoods {
    // FoodProperties handles nutrition / saturation / always-edible
    // Consumable handles effects via ApplyStatusEffectsConsumeEffect

    public static final Lazy<FoodProperties> DATURA = Lazy.of(
            () -> new Builder().nutrition(0).saturationModifier(0).alwaysEdible().build());

    public static final Consumable DATURA_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 15 * 20, 1), 0.7f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 15 * 20, 1), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 15 * 20, 1), 0.3f))
            .build();

    public static final Lazy<FoodProperties> DEMONS_DREAM_ESSENCE = Lazy.of(
            () -> new Builder().nutrition(0).saturationModifier(0).alwaysEdible().build());

    public static final Consumable DEMONS_DREAM_ESSENCE_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 60 * 20, 1), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 15 * 20, 1), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DARKNESS, 15 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.GLOWING, 15 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LUCK, 5 * 60 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.UNLUCK, 5 * 60 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 15 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WEAKNESS, 15 * 20, 1), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LEVITATION, 15 * 20, 1), 0.2f))
            .build();

    public static final Lazy<FoodProperties> OTHERWORLD_ESSENCE = Lazy.of(
            () -> new Builder().nutrition(0).saturationModifier(0).alwaysEdible().build());

    public static final Consumable OTHERWORLD_ESSENCE_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 60 * 20, 1), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LUCK, 5 * 60 * 20, 1), 1.0f))
            .build();

    public static final Lazy<FoodProperties> PITAYA = Lazy.of(
            () -> new Builder().nutrition(6).saturationModifier(3.6F).build());
    public static final Consumable PITAYA_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 30 * 20, 1), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 15 * 20, 1), 0.05f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 15 * 20, 1), 0.05f))
            .build();

    public static final Lazy<FoodProperties> PITAYA_GOLDEN = Lazy.of(
            () -> new Builder().nutrition(6).saturationModifier(14.4F).alwaysEdible().build());
    public static final Consumable PITAYA_GOLDEN_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 10 * 20, 1), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 60 * 20, 4), 1f))
            .build();

    public static final Lazy<FoodProperties> PITAYA_ENCHANTED = Lazy.of(
            () -> new Builder().nutrition(6).saturationModifier(14.4F).alwaysEdible().build());
    public static final Consumable PITAYA_ENCHANTED_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(OccultismEffects.THIRD_EYE, 3 * 20, 1), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 3 * 60 * 20, 4), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 3 * 60 * 20, 2), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3 * 60 * 20, 2), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3 * 60 * 20, 2), 1f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WIND_CHARGED, 3 * 60 * 20, 2), 1f))
            .build();

    public static final Lazy<FoodProperties> BEAVER_NUGGET = Lazy.of(
            () -> new Builder().nutrition(8).saturationModifier(0.8F).build());

    public static final Consumable BEAVER_NUGGET_CONSUMABLE = Consumables.DEFAULT_FOOD;

    public static final Lazy<FoodProperties> CURSED_HONEY = Lazy.of(
            () -> new Builder().nutrition(2).saturationModifier(1F).build());

    public static final Consumable CURSED_HONEY_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 1), 1.0f))
            .build();

    public static final Lazy<FoodProperties> SWEET_HONEY_HEART = Lazy.of(
            () -> new Builder().nutrition(5).saturationModifier(1.1F).build());

    public static final Consumable SWEET_HONEY_HEART_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 9, false, false, false), 1.0f))
            .build();

    public static final Lazy<FoodProperties> DEMONIC_MEAT = Lazy.of(
            () -> new Builder().nutrition(11).saturationModifier(0.1F).build());

    public static final Consumable DEMONIC_MEAT_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3 * 60 * 20, 1), 1.0f))
            .build();
}
