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
import com.klikli_dev.occultism.common.entity.familiar.ShubNiggurathSpawnEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class ShubNiggurathSpawnRenderer extends MobRenderer<ShubNiggurathSpawnEntity, LivingEntityRenderState, ShubNiggurathSpawnModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/shub_niggurath_spawn.png");

    public ShubNiggurathSpawnRenderer(EntityRendererProvider.Context context) {
        super(context, new ShubNiggurathSpawnModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN)), 0.1f);
        this.addLayer(new BlinkingEyesLayer(this, context));
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class BlinkingEyesLayer extends RenderLayer<LivingEntityRenderState, ShubNiggurathSpawnModel> {

        private static final Identifier BLINKING = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/shub_niggurath_spawn_blinking.png");

        private final ShubNiggurathSpawnModel model;

        public BlinkingEyesLayer(RenderLayerParent<LivingEntityRenderState, ShubNiggurathSpawnModel> parent, EntityRendererProvider.Context context) {
            super(parent);
            this.model = new ShubNiggurathSpawnModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_SHUB_NIGGURATH_SPAWN));
        }

        @Override
        public void submit(PoseStack pMatrixStack, net.minecraft.client.renderer.SubmitNodeCollector pSubmitNodeCollector, int pPackedLight, LivingEntityRenderState pRenderState, float pNetHeadYaw, float pHeadPitch) {
            // TODO: Port to 26.1 rendering API
        }

        // Old render method preserved for reference - TODO: Port to 26.1 rendering API
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
