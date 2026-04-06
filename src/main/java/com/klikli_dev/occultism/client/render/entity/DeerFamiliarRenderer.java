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
import com.klikli_dev.occultism.client.model.entity.DeerFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DeerFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;

public class DeerFamiliarRenderer extends MobRenderer<DeerFamiliarEntity, LivingEntityRenderState, DeerFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/deer_familiar.png");

    private static final ContextKey<Boolean> IS_PARTYING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "deer_is_partying"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "deer_is_sitting"));
    private static final ContextKey<Boolean> HAS_RED_NOSE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "deer_has_red_nose"));

    public DeerFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DEER)), 0.3f);
        this.addLayer(new RedNoseLayer(this));
    }

    @Override
    public void extractRenderState(DeerFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(IS_PARTYING, entity.isPartying());
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(HAS_RED_NOSE, entity.hasRedNose());
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Boolean isPartying = state.getRenderData(IS_PARTYING);
        Boolean isSitting = state.getRenderData(IS_SITTING);
        if (isPartying != null && isPartying)
            poseStack.translate(0, 0.08, 0);
        else if (isSitting != null && isSitting)
            poseStack.translate(0, -0.38, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class RedNoseLayer extends RenderLayer<LivingEntityRenderState, DeerFamiliarModel> {

        private static final Identifier RED_NOSE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/deer_familiar_red_nose.png");

        public RedNoseLayer(RenderLayerParent<LivingEntityRenderState, DeerFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            Boolean hasRedNose = state.getRenderData(DeerFamiliarRenderer.HAS_RED_NOSE);
            if (state.isInvisible || hasRedNose == null || !hasRedNose)
                return;

            DeerFamiliarModel model = this.getParentModel();
            RenderLayer.renderColoredCutoutModel(model, RED_NOSE, pMatrixStack, submitNodeCollector, lightCoords, state, -1, 0);
        }
    }
}
