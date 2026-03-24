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
import com.klikli_dev.occultism.client.model.entity.ChimeraFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.ChimeraFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;

public class ChimeraFamiliarRenderer extends MobRenderer<ChimeraFamiliarEntity, LivingEntityRenderState, ChimeraFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/chimera_familiar.png");

    private static final ContextKey<Float> SCALE = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "chimera_scale"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "chimera_is_sitting"));

    public ChimeraFamiliarRenderer(EntityRendererProvider.Context context) {
        super(context, new ChimeraFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_CHIMERA)), 0.3f);
    }

    @Override
    public void extractRenderState(ChimeraFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(SCALE, entity.getScale());
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Boolean sitting = state.getRenderData(IS_SITTING);
        Float entityScale = state.getRenderData(SCALE);
        boolean isSitting = sitting != null && sitting;
        float scale = entityScale != null ? entityScale : 1f;
        if (isSitting)
            poseStack.translate(0, -0.14 * scale, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
        Float entityScale = state.getRenderData(SCALE);
        float size = (entityScale != null ? entityScale : 1f) * 0.5f;
        poseStack.scale(size, size, size);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }
}
