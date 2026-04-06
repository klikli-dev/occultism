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
import com.klikli_dev.occultism.common.entity.familiar.GoatFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;


public class GoatFamiliarRenderer extends MobRenderer<GoatFamiliarEntity, LivingEntityRenderState, GoatFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/goat_familiar.png");

    private static final ContextKey<Float> SCALE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "goat_scale"));
    private static final ContextKey<Boolean> IS_PARTYING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "goat_is_partying"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "goat_is_sitting"));
    private static final ContextKey<Boolean> IS_BLACK = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "goat_is_black"));

    public GoatFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new GoatFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_GOAT)), 0.3f);
        this.addLayer(new BlackLayer(this, context));
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(GoatFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(SCALE, entity.getScale());
        reusedState.setRenderData(IS_PARTYING, entity.isPartying());
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(IS_BLACK, entity.isBlack());
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Float scale = state.getRenderData(SCALE);
        Boolean partying = state.getRenderData(IS_PARTYING);
        Boolean sitting = state.getRenderData(IS_SITTING);
        float s = scale != null ? scale : 1f;
        poseStack.scale(s, s, s);
        if (Boolean.TRUE.equals(partying))
            poseStack.translate(0, -0.25, 0);
        else if (Boolean.TRUE.equals(sitting))
            poseStack.translate(0, -0.3, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class BlackLayer extends RenderLayer<LivingEntityRenderState, GoatFamiliarModel> {

        private static final Identifier BLACK_TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/goat_familiar.png");

        public BlackLayer(RenderLayerParent<LivingEntityRenderState, GoatFamiliarModel> parent, EntityRendererProvider.Context context) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            Boolean isBlack = state.getRenderData(GoatFamiliarRenderer.IS_BLACK);
            if (state.isInvisible || !Boolean.TRUE.equals(isBlack))
                return;

            GoatFamiliarModel model = this.getParentModel();
            // Render with a dark translucent overlay to indicate "black" goat variant
            RenderLayer.renderColoredCutoutModel(model, BLACK_TEXTURE, pMatrixStack, submitNodeCollector, lightCoords, state, 0x7F000000, 0);
        }
    }
}
