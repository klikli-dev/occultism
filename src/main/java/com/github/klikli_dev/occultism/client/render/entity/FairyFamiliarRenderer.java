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
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class FairyFamiliarRenderer extends MobRenderer<FairyFamiliarEntity, FairyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/fairy_familiar.png");

    public FairyFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new FairyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_FAIRY)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void render(FairyFamiliarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0, pEntity.getAnimationHeight(pPartialTicks), 0);
        this.performMagicTransform(pEntity, pEntityYaw, pPartialTicks, pMatrixStack);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }

    private void performMagicTransform(FairyFamiliarEntity entityIn, float entityYaw, float partialTicks,
                                       PoseStack matrixStackIn) {
        Entity target = entityIn.getMagicTarget();
        if (target != null) {
            Vec3 pos = entityIn.getMagicPosition(partialTicks).subtract(entityIn.getPosition(partialTicks));
            Vec2 radiusAngle = entityIn.getMagicRadiusAngle(partialTicks);
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

    private static class SleepLayer extends RenderLayer<FairyFamiliarEntity, FairyFamiliarModel> {

        private static final ResourceLocation SLEEP = new ResourceLocation(Occultism.MODID,
                "textures/entity/fairy_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<FairyFamiliarEntity, FairyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, FairyFamiliarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (pLivingEntity.isInvisible() || !pLivingEntity.isSitting())
                return;

            FairyFamiliarModel model = this.getParentModel();
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutout(SLEEP));
            model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight,
                    LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0), 1, 1, 1, 1);
        }
    }
}
