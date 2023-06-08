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

package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.BeholderFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.BeholderFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BeholderFamiliarRenderer extends MobRenderer<BeholderFamiliarEntity, BeholderFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/beholder_familiar.png");

    public BeholderFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new BeholderFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BEHOLDER)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void render(BeholderFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0, pEntity.getAnimationHeight(pPartialTicks), 0);
        if (pEntity.isEating()) {
            float eatTimer = pEntity.getEatTimer(pPartialTicks);
            float scale = eatTimer < 5 / 6f ? 1 : Mth.sin((eatTimer - 5 / 6f) * 6 * (float) Math.PI) + 1;
            pMatrixStack.scale(scale, scale, scale);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(BeholderFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class SleepLayer extends RenderLayer<BeholderFamiliarEntity, BeholderFamiliarModel> {

        private static final ResourceLocation SLEEP = new ResourceLocation(Occultism.MODID,
                "textures/entity/beholder_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<BeholderFamiliarEntity, BeholderFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, BeholderFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isInvisible() || !pLivingEntity.isSitting())
                return;

            BeholderFamiliarModel model = this.getParentModel();
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutout(SLEEP));
            model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight,
                    LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0), 1, 1, 1, 1);
        }
    }
}
