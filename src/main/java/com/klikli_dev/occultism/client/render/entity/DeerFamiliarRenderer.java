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
import com.klikli_dev.occultism.client.model.entity.DeerFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DeerFamiliarEntity;
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

public class DeerFamiliarRenderer extends MobRenderer<DeerFamiliarEntity, DeerFamiliarModel> {

    private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/deer_familiar.png");

    public DeerFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DEER)), 0.3f);
        this.addLayer(new RedNoseLayer(this));
    }

    @Override
    public void render(DeerFamiliarEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        if (entityIn.isPartying())
            matrixStackIn.translate(0, 0.08, 0);
        else if (entityIn.isSitting())
            matrixStackIn.translate(0, -0.38, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DeerFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class RedNoseLayer extends RenderLayer<DeerFamiliarEntity, DeerFamiliarModel> {

        private static final ResourceLocation RED_NOSE = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/deer_familiar_red_nose.png");

        public RedNoseLayer(RenderLayerParent<DeerFamiliarEntity, DeerFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, DeerFamiliarEntity deer, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (deer.isInvisible() || !deer.hasRedNose())
                return;

            DeerFamiliarModel model = this.getParentModel();
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutout(RED_NOSE));
            model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, LivingEntityRenderer.getOverlayCoords(deer, 0));
        }
    }
}
