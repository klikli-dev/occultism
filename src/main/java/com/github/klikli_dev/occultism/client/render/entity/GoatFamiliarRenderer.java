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
import com.github.klikli_dev.occultism.client.model.entity.GoatFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.familiar.GoatFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
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

public class GoatFamiliarRenderer extends MobRenderer<GoatFamiliarEntity, GoatFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/goat_familiar.png");

    public GoatFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new GoatFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GOAT)), 0.3f);
        this.addLayer(new BlackLayer(this, context));
    }

    @Override
    public void render(GoatFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        float size = pEntity.getScale();
        pMatrixStack.scale(size, size, size);
        if (pEntity.isPartying())
            pMatrixStack.translate(0, -0.25, 0);
        else if (pEntity.isSitting())
            pMatrixStack.translate(0, -0.3, 0);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(GoatFamiliarEntity entity) {
        return TEXTURES;
    }

    private class BlackLayer extends RenderLayer<GoatFamiliarEntity, GoatFamiliarModel> {

        private final GoatFamiliarModel model;

        public BlackLayer(RenderLayerParent<GoatFamiliarEntity, GoatFamiliarModel> parent, EntityRendererProvider.Context context) {
            super(parent);
            this.model = new GoatFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GOAT));
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, GoatFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (!pLivingEntity.isInvisible() && pLivingEntity.isBlack()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                VertexConsumer ivertexbuilder = pBuffer
                        .getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity)));
                this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight,
                        LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0), 0, 0, 0, 0.5f);
            }
        }

    }
}
