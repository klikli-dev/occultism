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
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class ThirdEyeEffect extends Effect {

    //region Fields
    public static final ResourceLocation ICON = new ResourceLocation(Occultism.MODID,
            "textures/mob_effect/third_eye.png");
    //endregion Fields

    //region Initialization
    public ThirdEyeEffect() {
        super(EffectType.BENEFICIAL, 0xffff00);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x,
                                      int y, float z) {
        gui.getMinecraft().getTextureManager().bindTexture(ICON);
        AbstractGui.blit(mStack, x + 6, y + 7, 18, 18, 0, 0, 255, 255, 256, 256);

    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z,
                                float alpha) {
        Minecraft.getInstance().getTextureManager().bindTexture(ICON);
        AbstractGui.blit(mStack, x + 3, y + 3, 18, 18, 0, 0, 255, 255, 256, 256);
    }

    //endregion Overrides
}
