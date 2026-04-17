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
import com.klikli_dev.occultism.client.model.entity.FairyFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.FairyFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class FairyFamiliarRenderer extends MobRenderer<FairyFamiliarEntity, LivingEntityRenderState, FairyFamiliarModel> {

    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/entity/fairy_familiar.png");

    private static final ContextKey<Float> ANIM_HEIGHT = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_anim_height"));
    private static final ContextKey<Boolean> IS_SITTING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_is_sitting"));
    private static final ContextKey<Boolean> IS_PARTYING = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_is_partying"));
    // For magic target transform
    private static final ContextKey<Float> MAGIC_POS_X = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_magic_pos_x"));
    private static final ContextKey<Float> MAGIC_POS_Y = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_magic_pos_y"));
    private static final ContextKey<Float> MAGIC_POS_Z = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_magic_pos_z"));
    private static final ContextKey<Float> MAGIC_RADIUS_ANGLE_Y = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_magic_angle_y"));
    private static final ContextKey<Boolean> HAS_MAGIC_TARGET = new ContextKey<>(Identifier.fromNamespaceAndPath(Occultism.MODID, "fairy_has_magic_target"));

    public FairyFamiliarRenderer(Context context) {
        super(context, new FairyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_FAIRY)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void extractRenderState(FairyFamiliarEntity entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.setRenderData(ANIM_HEIGHT, entity.getAnimationHeight(partialTick));
        reusedState.setRenderData(IS_SITTING, entity.isSitting());
        reusedState.setRenderData(IS_PARTYING, entity.isPartying());

        Entity target = entity.getMagicTarget();
        if (target != null) {
            Vec3 pos = entity.getMagicPosition(partialTick).subtract(entity.getPosition(partialTick));
            Vec2 radiusAngle = entity.getMagicRadiusAngle(partialTick);
            reusedState.setRenderData(HAS_MAGIC_TARGET, true);
            reusedState.setRenderData(MAGIC_POS_X, (float) pos.x);
            reusedState.setRenderData(MAGIC_POS_Y, (float) pos.y);
            reusedState.setRenderData(MAGIC_POS_Z, (float) pos.z);
            reusedState.setRenderData(MAGIC_RADIUS_ANGLE_Y, radiusAngle.y);
        } else {
            reusedState.setRenderData(HAS_MAGIC_TARGET, false);
        }
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Float animHeight = state.getRenderData(ANIM_HEIGHT);
        if (animHeight != null) {
            poseStack.translate(0, animHeight, 0);
        }
        Boolean hasMagicTarget = state.getRenderData(HAS_MAGIC_TARGET);
        if (hasMagicTarget != null && hasMagicTarget) {
            Float px = state.getRenderData(MAGIC_POS_X);
            Float py = state.getRenderData(MAGIC_POS_Y);
            Float pz = state.getRenderData(MAGIC_POS_Z);
            Float ay = state.getRenderData(MAGIC_RADIUS_ANGLE_Y);
            if (px != null && py != null && pz != null && ay != null) {
                poseStack.translate(px, py, pz);
                poseStack.mulPose(new Quaternionf().rotateXYZ(0, -ay * ((float) Math.PI / 180F), 0));
            }
            this.shadowStrength = 0;
        } else {
            this.shadowStrength = 1;
        }
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURES;
    }

    private static class SleepLayer extends RenderLayer<LivingEntityRenderState, FairyFamiliarModel> {

        private static final Identifier SLEEP = Identifier.fromNamespaceAndPath(Occultism.MODID,
                "textures/entity/fairy_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<LivingEntityRenderState, FairyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack pMatrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot) {
            Boolean isSitting = state.getRenderData(FairyFamiliarRenderer.IS_SITTING);
            Boolean isPartying = state.getRenderData(FairyFamiliarRenderer.IS_PARTYING);
            if (state.isInvisible || isSitting == null || !isSitting || (isPartying != null && isPartying))
                return;

            FairyFamiliarModel model = this.getParentModel();
            RenderLayer.renderColoredCutoutModel(model, SLEEP, pMatrixStack, submitNodeCollector, lightCoords, state, -1, 0);
        }
    }
}
