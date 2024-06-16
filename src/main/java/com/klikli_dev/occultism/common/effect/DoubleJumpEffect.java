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

package com.klikli_dev.occultism.common.effect;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEffects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import java.util.function.Consumer;

public class DoubleJumpEffect extends MobEffect {

    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/mob_effect/double_jump.png");

    public static final IClientMobEffectExtensions EFFECT_RENDERER = new IClientMobEffectExtensions() {

        @Override
        public boolean renderInventoryIcon(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
            guiGraphics.blit(ICON, x + 6, y + 7, 18, 18, 0, 0, 255, 255, 256, 256);
            return false;
        }


        @Override
        public boolean renderGuiIcon(MobEffectInstance instance, Gui gui, GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
            guiGraphics.blit(ICON, x + 3, y + 3, 18, 18, 0, 0, 255, 255, 256, 256);
            return false;
        }
    };

    public DoubleJumpEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffff00);
    }

    /**
     * Gets the amount of jumps provided by the double jump effect on the player, or 0 if there is no double jump effect
     * present.
     *
     * @param player the player
     * @return the max amount of jumps.
     */
    public static int getMaxJumps(Player player) {
        MobEffectInstance effect = player.getEffect(OccultismEffects.DOUBLE_JUMP);
        if (effect != null) {
            return 1 + effect.getAmplifier();
        }
        return 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(EFFECT_RENDERER);
    }
}
