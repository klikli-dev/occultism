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
import com.klikli_dev.occultism.client.model.entity.BatFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.BatFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;

public class BatFamiliarRenderer extends MobRenderer<BatFamiliarEntity, LivingEntityRenderState, BatFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/bat_familiar.png");

    // ContextKey to pass entity-specific data to render state
    private static final ContextKey<Float> ANIM_HEIGHT = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "bat_anim_height"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "bat_is_sitting"));
    private static final ContextKey<Boolean> IS_PARTYING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "bat_is_partying"));

    public BatFamiliarRenderer(Context context) {
        super(context, new BatFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_BAT)), 0.3f);
    }

    @Override
    public void extractRenderState(BatFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(ANIM_HEIGHT, entity.getAnimationHeight(partialTick));
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(IS_PARTYING, entity.isPartying());
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Boolean sitting = state.getRenderData(IS_SITTING);
        Boolean partying = state.getRenderData(IS_PARTYING);
        Float animHeight = state.getRenderData(ANIM_HEIGHT);
        boolean isSitting = sitting != null && sitting;
        boolean isPartying = partying != null && partying;
        float height = animHeight != null ? animHeight : 0f;
        if (!isSitting || isPartying)
            poseStack.translate(0, height * 0.1 + 0.1f, 0);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }
}
