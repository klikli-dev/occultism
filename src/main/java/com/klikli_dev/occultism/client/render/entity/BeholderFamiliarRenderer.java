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
import com.klikli_dev.occultism.client.model.entity.BeholderFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.BeholderFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;

public class BeholderFamiliarRenderer extends MobRenderer<BeholderFamiliarEntity, LivingEntityRenderState, BeholderFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/beholder_familiar.png");

    private static final ContextKey<Float> ANIM_HEIGHT = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "beholder_anim_height"));
    private static final ContextKey<Float> EAT_TIMER = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "beholder_eat_timer"));
    private static final ContextKey<Boolean> IS_EATING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "beholder_is_eating"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "beholder_is_sitting"));

    public BeholderFamiliarRenderer(Context context) {
        super(context, new BeholderFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BEHOLDER)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void extractRenderState(BeholderFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(ANIM_HEIGHT, entity.getAnimationHeight(partialTick));
        reusedState.setRenderData(EAT_TIMER, entity.getEatTimer(partialTick));
        reusedState.setRenderData(IS_EATING, entity.isEating());
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Float animHeight = state.getRenderData(ANIM_HEIGHT);
        Float eatTimer = state.getRenderData(EAT_TIMER);
        Boolean isEating = state.getRenderData(IS_EATING);
        float height = animHeight != null ? animHeight : 0f;
        poseStack.translate(0, height, 0);
        if (isEating != null && isEating && eatTimer != null) {
            float scale = eatTimer < 5 / 6f ? 1 : Mth.sin((eatTimer - 5 / 6f) * 6 * (float) Math.PI) + 1;
            poseStack.scale(scale, scale, scale);
        }
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class SleepLayer extends RenderLayer<LivingEntityRenderState, BeholderFamiliarModel> {

        private static final Identifier SLEEP = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/beholder_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<LivingEntityRenderState, BeholderFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            Boolean isSitting = state.getRenderData(BeholderFamiliarRenderer.IS_SITTING);
            if (state.isInvisible || isSitting == null || !isSitting)
                return;

            BeholderFamiliarModel model = this.getParentModel();
            RenderLayer.renderColoredCutoutModel(model, SLEEP, pMatrixStack, submitNodeCollector, lightCoords, state, -1, 0);
        }
    }
}
