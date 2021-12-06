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
import com.github.klikli_dev.occultism.client.model.entity.MummyFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.MummyFamiliarEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class MummyFamiliarRenderer extends MobRenderer<MummyFamiliarEntity, MummyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/mummy_familiar.png");

    public MummyFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MummyFamiliarModel(), 0.3f);
        this.addLayer(new KapowLayer(this));
        this.addLayer(new EyesLayer(this));
    }

    @Override
    public void render(MummyFamiliarEntity mummy, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(mummy, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(MummyFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class EyesLayer extends LayerRenderer<MummyFamiliarEntity, MummyFamiliarModel> {

        private static final ResourceLocation EYES = new ResourceLocation(Occultism.MODID,
                "textures/entity/mummy_familiar_eyes.png");

        public EyesLayer(IEntityRenderer<MummyFamiliarEntity, MummyFamiliarModel> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                MummyFamiliarEntity mummy, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (mummy.isInvisible())
                return;

            int light = mummy.isSitting() ? 0 : 10;

            MummyFamiliarModel model = this.getParentModel();
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(EYES));
            model.renderToBuffer(matrixStackIn, ivertexbuilder, LightTexture.pack(light, light),
                    LivingRenderer.getOverlayCoords(mummy, 0), 1, 1, 1, 1);
        }
    }

    private static class KapowLayer extends LayerRenderer<MummyFamiliarEntity, MummyFamiliarModel> {

        private static final ResourceLocation KAPOW_TEXTURE = new ResourceLocation(Occultism.MODID,
                "textures/entity/kapow.png");
        private static final TranslationTextComponent KAPOW_TEXT = new TranslationTextComponent(
                "dialog.occultism.mummy.kapow");

        private static KapowModel model = new KapowModel();
        private MummyFamiliarRenderer renderer;

        public KapowLayer(MummyFamiliarRenderer renderer) {
            super(renderer);
            this.renderer = renderer;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                MummyFamiliarEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.getFightPose() == -1)
                return;

            float alpha = entitylivingbaseIn.getCapowAlpha(partialTicks);

            matrixStackIn.pushPose();
            float scale = 0.5f;
            matrixStackIn.scale(scale, scale, scale);
            Vector3d capowPos = entitylivingbaseIn.getCapowPosition(partialTicks);
            matrixStackIn.translate(capowPos.x, -0.4 + capowPos.y, capowPos.z);
            model.renderToBuffer(matrixStackIn, bufferIn.getBuffer(model.renderType(KAPOW_TEXTURE)), packedLightIn,
                    OverlayTexture.NO_OVERLAY, 1, 1, 1, alpha);

            matrixStackIn.pushPose();
            matrixStackIn.scale(0.07f, 0.07f, 0.07f);
            matrixStackIn.translate(0, -2.5, 0);
            matrixStackIn.mulPose(new Quaternion(0, 0, 20, true));
            FontRenderer font = renderer.getFont();

            matrixStackIn.pushPose();
            matrixStackIn.translate(0, 0, -0.01);
            Matrix4f matrix = matrixStackIn.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2, 0, 0xff0000 | ((int) (alpha * 255) << 24), true,
                    matrix, bufferIn, false, 0, packedLightIn);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.translate(0, 0, 0.01);
            matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
            matrix = matrixStackIn.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2, 0, 0xff0000 | ((int) (alpha * 255) << 24), true,
                    matrix, bufferIn, false, 0, packedLightIn);
            matrixStackIn.popPose();
            matrixStackIn.popPose();
            matrixStackIn.popPose();
        }
    }

    private static class KapowModel extends Model {
        public ModelRenderer kapow;

        public KapowModel() {
            super(RenderType::entityTranslucent);
            this.texWidth = 64;
            this.texHeight = 32;
            this.kapow = new ModelRenderer(this, 0, 0);
            this.kapow.setPos(0.0F, 0.0F, 0.0F);
            this.kapow.addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }

        @Override
        public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn,
                int packedOverlayIn, float red, float green, float blue, float alpha) {
            ImmutableList.of(this.kapow).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }
    }
}
