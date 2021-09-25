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

package com.github.klikli_dev.occultism.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OtherworldBirdRenderer extends MobRenderer<ParrotEntity, ParrotModel> {
    public static final ResourceLocation TEXTURE = modLoc("textures/entity/otherworld_bird.png");

    public OtherworldBirdRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ParrotModel(), 0.3F);
    }

    public ResourceLocation getTextureLocation(ParrotEntity entity) {
        return TEXTURE;
    }

    public float getBob(ParrotEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}