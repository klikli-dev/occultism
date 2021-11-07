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
import com.github.klikli_dev.occultism.client.model.entity.ShubNiggurathSpawnModel;
import com.github.klikli_dev.occultism.common.entity.ShubNiggurathSpawnEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class ShubNiggurathSpawnRenderer extends MobRenderer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/shub_niggurath_spawn.png");

    public ShubNiggurathSpawnRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShubNiggurathSpawnModel(), 0.1f);
        this.addLayer(new BlinkingEyesLayer(this));
    }

    @Override
    public void render(ShubNiggurathSpawnEntity entityIn, float entityYaw, float partialTicks,
            MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ShubNiggurathSpawnEntity entity) {
        return TEXTURES;
    }

    private static class BlinkingEyesLayer extends LayerRenderer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> {

        private static final ResourceLocation BLINKING = new ResourceLocation(Occultism.MODID,
                "textures/entity/shub_niggurath_spawn_blinking.png");

        private final ShubNiggurathSpawnModel model = new ShubNiggurathSpawnModel();

        public BlinkingEyesLayer(IEntityRenderer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                ShubNiggurathSpawnEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.isInvisible()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(BLINKING));

                blinkEyes(entitylivingbaseIn);

                this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                        LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0), 1, 1, 1, 1);
            }
        }

        private void blinkEyes(ShubNiggurathSpawnEntity shub) {
            ModelRenderer[] eyes = new ModelRenderer[] { model.eye1, model.eye2, model.eye3, model.eye4 };
            for (int i = 0; i < eyes.length; i++)
                eyes[i].visible = shub.isBlinking(i);
        }
    }

}
