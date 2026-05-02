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
import com.klikli_dev.occultism.client.model.entity.ShubNiggurathSpawnModel;
import com.klikli_dev.occultism.client.render.entity.state.ShubNiggurathSpawnRenderState;
import com.klikli_dev.occultism.common.entity.familiar.ShubNiggurathSpawnEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class ShubNiggurathSpawnRenderer extends MobRenderer<ShubNiggurathSpawnEntity, ShubNiggurathSpawnRenderState, ShubNiggurathSpawnModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/shub_niggurath_spawn.png");

    public ShubNiggurathSpawnRenderer(Context context) {
        super(context, new ShubNiggurathSpawnModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN)), 0.1f);
        this.addLayer(new BlinkingEyesLayer(this));
    }

    @Override
    public void extractRenderState(ShubNiggurathSpawnEntity entity, ShubNiggurathSpawnRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.eye1Blinking = entity.isBlinking(0);
        reusedState.eye2Blinking = entity.isBlinking(1);
        reusedState.eye3Blinking = entity.isBlinking(2);
        reusedState.eye4Blinking = entity.isBlinking(3);
    }

    @Override
    public ShubNiggurathSpawnRenderState createRenderState() {
        return new ShubNiggurathSpawnRenderState();
    }

    @Override
    public Identifier getTextureLocation(ShubNiggurathSpawnRenderState state) {
        return TEXTURES;
    }

    private static class BlinkingEyesLayer extends RenderLayer<ShubNiggurathSpawnRenderState, ShubNiggurathSpawnModel> {

        private static final Identifier BLINKING = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/shub_niggurath_spawn_blinking.png");

        public BlinkingEyesLayer(RenderLayerParent<ShubNiggurathSpawnRenderState, ShubNiggurathSpawnModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, ShubNiggurathSpawnRenderState renderState, float netHeadYaw, float headPitch) {
            if (renderState.isInvisible) {
                return;
            }

            ShubNiggurathSpawnModel model = this.getParentModel();
            boolean eye1Visible = model.eye1.visible;
            boolean eye2Visible = model.eye2.visible;
            boolean eye3Visible = model.eye3.visible;
            boolean eye4Visible = model.eye4.visible;

            model.eye1.visible = renderState.eye1Blinking;
            model.eye2.visible = renderState.eye2Blinking;
            model.eye3.visible = renderState.eye3Blinking;
            model.eye4.visible = renderState.eye4Blinking;

            RenderLayer.renderColoredCutoutModel(model, BLINKING, poseStack, submitNodeCollector, packedLight, renderState, -1, 0);

            model.eye1.visible = eye1Visible;
            model.eye2.visible = eye2Visible;
            model.eye3.visible = eye3Visible;
            model.eye4.visible = eye4Visible;
        }

        // Old render method preserved for reference
        // public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, ShubNiggurathSpawnEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        //     if (!pLivingEntity.isInvisible()) {
        //         this.getParentModel().copyPropertiesTo(this.model);
        //         this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
        //         this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        //         VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(BLINKING));
        //         this.blinkEyes(pLivingEntity);
        //         this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight,
        //                 LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0));
        //     }
        // }

        // private void blinkEyes(ShubNiggurathSpawnEntity shub) {
        //     ModelPart[] eyes = new ModelPart[]{this.model.eye1, this.model.eye2, this.model.eye3, this.model.eye4};
        //     for (int i = 0; i < eyes.length; i++)
        //         eyes[i].visible = shub.isBlinking(i);
        // }
    }

}
