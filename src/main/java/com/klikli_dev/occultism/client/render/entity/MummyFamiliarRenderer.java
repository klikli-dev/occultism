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
import com.klikli_dev.occultism.client.model.entity.MummyFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.MummyFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class MummyFamiliarRenderer extends MobRenderer<MummyFamiliarEntity, MummyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/mummy_familiar.png");

    public MummyFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new MummyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_MUMMY)), 0.3f);
        this.addLayer(new KapowLayer(this, context));
        this.addLayer(new EyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MummyFamiliarEntity entity) {
        return TEXTURES;
    }

    private static class EyesLayer extends RenderLayer<MummyFamiliarEntity, MummyFamiliarModel> {

        private static final ResourceLocation EYES = new ResourceLocation(Occultism.MODID,
                "textures/entity/mummy_familiar_eyes.png");

        public EyesLayer(RenderLayerParent<MummyFamiliarEntity, MummyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, MummyFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float ppPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isInvisible())
                return;

            int light = pLivingEntity.isSitting() ? 0 : 10;

            MummyFamiliarModel model = this.getParentModel();
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutout(EYES));
            model.renderToBuffer(pMatrixStack, ivertexbuilder, LightTexture.pack(light, light),
                    LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0), 1, 1, 1, 1);
        }

    }

    private static class KapowLayer extends RenderLayer<MummyFamiliarEntity, MummyFamiliarModel> {

        private static final ResourceLocation KAPOW_TEXTURE = new ResourceLocation(Occultism.MODID,
                "textures/entity/kapow.png");
        private static final Component KAPOW_TEXT = Component.translatable(
                "dialog.occultism.mummy.kapow");

        private static KapowModel model;
        private final MummyFamiliarRenderer renderer;

        public KapowLayer(MummyFamiliarRenderer renderer, EntityRendererProvider.Context context) {
            super(renderer);
            this.renderer = renderer;
            if (model == null) {
                model = new KapowModel(context.bakeLayer(OccultismModelLayers.KAPOW));
            }
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, MummyFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.getFightPose() == -1)
                return;

            float alpha = pLivingEntity.getCapowAlpha(pPartialTicks);

            pMatrixStack.pushPose();
            float scale = 0.5f;
            pMatrixStack.scale(scale, scale, scale);
            Vec3 capowPos = pLivingEntity.getCapowPosition(pPartialTicks);
            pMatrixStack.translate(capowPos.x, -0.4 + capowPos.y, capowPos.z);
            model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(model.renderType(KAPOW_TEXTURE)), pPackedLight,
                    OverlayTexture.NO_OVERLAY, 1, 1, 1, alpha);

            pMatrixStack.pushPose();
            pMatrixStack.scale(0.07f, 0.07f, 0.07f);
            pMatrixStack.translate(0, -2.5, 0);
            pMatrixStack.mulPose(new Quaternion(0, 0, 20, true));
            Font font = renderer.getFont();

            pMatrixStack.pushPose();
            pMatrixStack.translate(0, 0, -0.01);
            Matrix4f matrix = pMatrixStack.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2, 0, 0xff0000 | ((int) (alpha * 255) << 24), true,
                    matrix, pBuffer, false, 0, pPackedLight);
            pMatrixStack.popPose();

            pMatrixStack.pushPose();
            pMatrixStack.translate(0, 0, 0.01);
            pMatrixStack.mulPose(new Quaternion(0, 180, 0, true));
            matrix = pMatrixStack.last().pose();
            font.drawInBatch(KAPOW_TEXT, -font.width(KAPOW_TEXT) / 2, 0, 0xff0000 | ((int) (alpha * 255) << 24), true,
                    matrix, pBuffer, false, 0, pPackedLight);
            pMatrixStack.popPose();
            pMatrixStack.popPose();
            pMatrixStack.popPose();
        }

    }

    public static class KapowModel extends Model {
        public ModelPart kapow;

        public KapowModel(ModelPart part) {
            super(RenderType::entityTranslucent);
            this.kapow = part.getChild("kapow");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition mesh = new MeshDefinition();
            PartDefinition parts = mesh.getRoot();
            PartDefinition body = parts.addOrReplaceChild("kapow",
                    CubeListBuilder.create().texOffs(0, 0).
                            addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, false),
                    PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0));
            return LayerDefinition.create(mesh, 64, 32);
        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            this.kapow.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        }
    }
}
