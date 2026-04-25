package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.DeerFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.DeerFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.DeerFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DeerFamiliarRenderer extends MobRenderer<DeerFamiliarEntity, DeerFamiliarRenderState, DeerFamiliarModel> {
    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/deer_familiar.png");

    public DeerFamiliarRenderer(Context context) {
        super(context, new DeerFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_DEER)), 0.3f);
        this.addLayer(new RedNoseLayer(this));
    }

    @Override
    public void extractRenderState(DeerFamiliarEntity entity, DeerFamiliarRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isPartying = entity.isPartying();
        state.isSitting = entity.isSitting();
        state.hasRedNose = entity.hasRedNose();
        state.hasBlacksmithUpgrade = entity.hasBlacksmithUpgrade();
        state.isEating = entity.isEating();
        state.neckRot = entity.getNeckRot(partialTick);
        state.isFast = entity.getAttributeValue(Attributes.MOVEMENT_SPEED) > 0.4;
        state.attackAnim = entity.getAttackAnim(partialTick);
    }

    @Override
    public DeerFamiliarRenderState createRenderState() { return new DeerFamiliarRenderState(); }

    @Override
    public void submit(DeerFamiliarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.isPartying) poseStack.translate(0, 0.08, 0);
        else if (state.isSitting) poseStack.translate(0, -0.38, 0);
        super.submit(state, poseStack, collector, camera);
        poseStack.popPose();
    }

    @Override
    public Identifier getTextureLocation(DeerFamiliarRenderState state) { return TEXTURES; }

    private static class RedNoseLayer extends RenderLayer<DeerFamiliarRenderState, DeerFamiliarModel> {
        private static final Identifier RED_NOSE = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/deer_familiar_red_nose.png");
        public RedNoseLayer(RenderLayerParent<DeerFamiliarRenderState, DeerFamiliarModel> parent) { super(parent); }
        @Override
        public void submit(PoseStack stack, SubmitNodeCollector collector, int light, DeerFamiliarRenderState state, float yRot, float xRot) {
            if (state.isInvisible || !state.hasRedNose) return;
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), RED_NOSE, stack, collector, light, state, -1, 0);
        }
    }
}
