/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.DevilFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.DevilFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DevilFamiliarRenderer extends MobRenderer<DevilFamiliarEntity, DevilFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/devil_familiar.png");

    public DevilFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new DevilFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DEVIL)), 0.3f);
    }

    @Override
    public void render(DevilFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0, pEntity.isSitting() && !pEntity.isPartying() ? 0 : pEntity.getAnimationHeight(pPartialTicks) * 0.08 + 0.3, 0);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(DevilFamiliarEntity entity) {
        return TEXTURES;
    }
}
