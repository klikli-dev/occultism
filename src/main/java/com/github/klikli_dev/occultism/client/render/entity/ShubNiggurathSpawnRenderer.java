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
import com.github.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class ShubNiggurathSpawnRenderer extends MobRenderer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/shub_niggurath_spawn.png");

    public ShubNiggurathSpawnRenderer(EntityRendererProvider.Context context) {
        super(context, new ShubNiggurathSpawnModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN)), 0.1f);
        this.addLayer(new BlinkingEyesLayer(this, context));
    }

    @Override
    public ResourceLocation getTextureLocation(ShubNiggurathSpawnEntity entity) {
        return TEXTURES;
    }

    private static class BlinkingEyesLayer extends RenderLayer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> {

        private static final ResourceLocation BLINKING = new ResourceLocation(Occultism.MODID,
                "textures/entity/shub_niggurath_spawn_blinking.png");

        private final ShubNiggurathSpawnModel model;

        public BlinkingEyesLayer(RenderLayerParent<ShubNiggurathSpawnEntity, ShubNiggurathSpawnModel> parent, EntityRendererProvider.Context context) {
            super(parent);
            this.model = new ShubNiggurathSpawnModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN));
        }


        @Override
        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, ShubNiggurathSpawnEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (!pLivingEntity.isInvisible()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(BLINKING));

                this.blinkEyes(pLivingEntity);

                this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight,
                        LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0), 1, 1, 1, 1);
            }
        }

        private void blinkEyes(ShubNiggurathSpawnEntity shub) {
            ModelPart[] eyes = new ModelPart[]{this.model.eye1, this.model.eye2, this.model.eye3, this.model.eye4};
            for (int i = 0; i < eyes.length; i++)
                eyes[i].visible = shub.isBlinking(i);
        }
    }

}
