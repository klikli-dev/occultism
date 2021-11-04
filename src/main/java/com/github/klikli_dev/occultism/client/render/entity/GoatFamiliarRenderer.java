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
import com.github.klikli_dev.occultism.common.entity.GoatFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class GoatFamiliarRenderer extends MobRenderer<GoatFamiliarEntity, GoatFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/goat_familiar.png");

    public GoatFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GoatFamiliarModel(), 0.3f);
        this.addLayer(new BlackLayer(this));
    }

    @Override
    public void render(GoatFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        float size = entityIn.getScale();
        matrixStackIn.scale(size, size, size);
        if (entityIn.isPartying())
            matrixStackIn.translate(0, -0.25, 0);
        else if (entityIn.isSitting())
            matrixStackIn.translate(0, -0.3, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(GoatFamiliarEntity entity) {
        return TEXTURES;
    }

    private class BlackLayer extends LayerRenderer<GoatFamiliarEntity, GoatFamiliarModel> {

        private final GoatFamiliarModel model = new GoatFamiliarModel();

        public BlackLayer(IEntityRenderer<GoatFamiliarEntity, GoatFamiliarModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                GoatFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isBlack()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = bufferIn
                        .getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entitylivingbaseIn)));
                this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                        LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0), 0, 0, 0, 0.5f);
            }
        }
    }
}
