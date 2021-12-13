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
import com.github.klikli_dev.occultism.client.model.entity.FairyFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.FairyFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class FairyFamiliarRenderer extends MobRenderer<FairyFamiliarEntity, FairyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/fairy_familiar.png");

    public FairyFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FairyFamiliarModel(), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void render(FairyFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0, entityIn.getAnimationHeight(partialTicks), 0);
        performMagicTransform(entityIn, entityYaw, partialTicks, matrixStackIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    private void performMagicTransform(FairyFamiliarEntity entityIn, float entityYaw, float partialTicks,
            MatrixStack matrixStackIn) {
        Entity target = entityIn.getMagicTarget();
        if (target != null) {
            Vector3d pos = entityIn.getMagicPosition(partialTicks).subtract(entityIn.getPosition(partialTicks));
            Vector2f radiusAngle = entityIn.getMagicRadiusAngle(partialTicks);
            matrixStackIn.translate(pos.x, pos.y, pos.z);
            matrixStackIn.mulPose(new Quaternion(0, -radiusAngle.y, 0, false));
            this.shadowStrength = 0;
        } else {
            this.shadowStrength = 1;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(FairyFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class SleepLayer extends LayerRenderer<FairyFamiliarEntity, FairyFamiliarModel> {

        private static final ResourceLocation SLEEP = new ResourceLocation(Occultism.MODID,
                "textures/entity/fairy_familiar_sleep.png");

        public SleepLayer(IEntityRenderer<FairyFamiliarEntity, FairyFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                FairyFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isInvisible() || !entitylivingbaseIn.isSitting() || entitylivingbaseIn.isPartying())
                return;

            FairyFamiliarModel model = this.getParentModel();
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(SLEEP));
            model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
        }
    }
}
