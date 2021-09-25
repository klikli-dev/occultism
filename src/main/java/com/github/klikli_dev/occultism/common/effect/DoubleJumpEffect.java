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

package com.github.klikli_dev.occultism.common.effect;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.EffectRenderer;

import java.util.function.Consumer;

public class DoubleJumpEffect extends MobEffect {

    //region Fields
    public static final ResourceLocation ICON = new ResourceLocation(Occultism.MODID,
            "textures/mob_effect/double_jump.png");

    public static final EffectRenderer EFFECT_RENDERER = new EffectRenderer() {
        @Override
        public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {
            gui.getMinecraft().getTextureManager().bindForSetup(ICON);
            GuiComponent.blit(mStack, x + 6, y + 7, 18, 18, 0, 0, 255, 255, 256, 256);
        }

        @Override
        public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {
            Minecraft.getInstance().getTextureManager().bindForSetup(ICON);
            GuiComponent.blit(mStack, x + 3, y + 3, 18, 18, 0, 0, 255, 255, 256, 256);
        }
    };

    //endregion Fields

    //region Initialization
    public DoubleJumpEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffff00);
    }
    //endregion Initialization

    /**
     * Gets the amount of jumps provided by the double jump effect on the player, or 0 if there is no double jump effect present.
     *
     * @param player the player
     * @return the max amount of jumps.
     */
    public static int getMaxJumps(Player player) {
        MobEffectInstance effect = player.getEffect(OccultismEffects.DOUBLE_JUMP.get());
        if (effect != null) {
            return 1 + effect.getAmplifier();
        }
        return 0;
    }

    //region Overrides
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }


    //endregion Overrides

    //region Static Methods

    @Override
    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(EFFECT_RENDERER);
    }
    //endregion Static Methods
}
