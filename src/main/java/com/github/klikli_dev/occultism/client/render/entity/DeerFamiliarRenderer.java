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
import com.github.klikli_dev.occultism.client.model.entity.DeerFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.DeerFamiliarEntity;
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

public class DeerFamiliarRenderer extends MobRenderer<DeerFamiliarEntity, DeerFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/deer_familiar.png");

    public DeerFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DeerFamiliarModel(), 0.3f);
        this.layerRenderers.add(new RedNoseLayer(this));
    }

    @Override
    public void render(DeerFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        if (entityIn.isSitting())
            matrixStackIn.translate(0, -0.38, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(DeerFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class RedNoseLayer extends LayerRenderer<DeerFamiliarEntity, DeerFamiliarModel> {

        private static final ResourceLocation RED_NOSE = new ResourceLocation(Occultism.MODID,
                "textures/entity/deer_familiar_red_nose.png");

        public RedNoseLayer(IEntityRenderer<DeerFamiliarEntity, DeerFamiliarModel> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                DeerFamiliarEntity deer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (deer.isInvisible() || !deer.hasRedNose())
                return;

            DeerFamiliarModel model = this.getEntityModel();
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(RED_NOSE));
            model.render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(deer, 0), 1, 1,
                    1, 1);
        }
    }
}
