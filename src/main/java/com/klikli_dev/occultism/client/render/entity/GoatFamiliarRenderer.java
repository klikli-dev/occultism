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
import com.klikli_dev.occultism.client.model.entity.GoatFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.GoatFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.GoatFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;


public class GoatFamiliarRenderer extends MobRenderer<GoatFamiliarEntity, GoatFamiliarRenderState, GoatFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/goat_familiar.png");

    public GoatFamiliarRenderer(Context context) {
        super(context, new GoatFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GOAT)), 0.3f);
        this.addLayer(new BlackLayer(this, context));
    }

    @Override
    public GoatFamiliarRenderState createRenderState() {
        return new GoatFamiliarRenderState();
    }

    @Override
    public void extractRenderState(GoatFamiliarEntity entity, GoatFamiliarRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.scale = entity.getScale();
        reusedState.isPartying = entity.isPartying();
        reusedState.isSitting = entity.isSitting();
        reusedState.isBlack = entity.isBlack();
        reusedState.neckYRot = entity.getNeckYRot(partialTick);
        reusedState.hasRing = entity.hasRing();
        reusedState.hasBeard = entity.hasBeard();
        reusedState.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        reusedState.hasRedEyes = entity.hasRedEyes();
        reusedState.hasEvilHorns = entity.hasEvilHorns();
    }

    @Override
    public void submit(GoatFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        float s = state.scale;
        poseStack.scale(s, s, s);
        if (state.isPartying)
            poseStack.translate(0, -0.25, 0);
        else if (state.isSitting)
            poseStack.translate(0, -0.3, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(GoatFamiliarRenderState state) {
        return TEXTURES;
    }

    private static class BlackLayer extends RenderLayer<GoatFamiliarRenderState, GoatFamiliarModel> {

        private static final Identifier BLACK_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/goat_familiar.png");

        public BlackLayer(RenderLayerParent<GoatFamiliarRenderState, GoatFamiliarModel> parent, Context context) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GoatFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible || !state.isBlack)
                return;

            GoatFamiliarModel model = this.getParentModel();
            // Render with a dark translucent overlay to indicate "black" goat variant
            RenderLayer.renderColoredCutoutModel(model, BLACK_TEXTURE, pMatrixStack, submitNodeCollector, lightCoords, state, 0x7F000000, 0);
        }
    }
}
