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
import com.klikli_dev.occultism.common.effect.DoubleJumpEffect;
import com.klikli_dev.occultism.common.effect.ThirdEyeEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Occultism.MODID);

    public static final Supplier<ThirdEyeEffect> THIRD_EYE = EFFECTS.register("third_eye", ThirdEyeEffect::new);
    public static final Supplier<DoubleJumpEffect> DOUBLE_JUMP = EFFECTS.register("double_jump", DoubleJumpEffect::new);
    public static final Supplier<MobEffect> DRAGON_GREED = EFFECTS.register("dragon_greed", () -> new ModEffect(MobEffectCategory.BENEFICIAL, 0xFFD700));
    public static final Supplier<MobEffect> MUMMY_DODGE = EFFECTS.register("mummy_dodge", () -> new ModEffect(MobEffectCategory.BENEFICIAL, 0xe4d8a7));
    public static final Supplier<MobEffect> BAT_LIFESTEAL = EFFECTS.register("bat_lifesteal", () -> new ModEffect(MobEffectCategory.BENEFICIAL, 0x960201));
    public static final Supplier<MobEffect> BEAVER_HARVEST = EFFECTS.register("beaver_harvest", () -> new ModEffect(MobEffectCategory.BENEFICIAL, 0x603613));

    public static final Supplier<MobEffect> STEP_HEIGHT = EFFECTS.register("step_height", () ->
            new ModEffect(MobEffectCategory.BENEFICIAL, 3402751)
                    .addAttributeModifier(NeoForgeMod.STEP_HEIGHT.value(), "748e2cfd-8db4-4b55-ba07-014fdf0f74da", 2, AttributeModifier.Operation.ADDITION));

    public static class ModEffect extends MobEffect {

        private ModEffect(MobEffectCategory category, int color) {
            super(category, color);
        }

    }
}
