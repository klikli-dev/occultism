package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.BeholderFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.BeholderFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.BeholderFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class BeholderFamiliarRenderer extends MobRenderer<BeholderFamiliarEntity, BeholderFamiliarRenderState, BeholderFamiliarModel> {
    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/beholder_familiar.png");

    public BeholderFamiliarRenderer(Context c) {
        super(c, new BeholderFamiliarModel(c.bakeLayer(OccultismModelLayers.FAMILIAR_BEHOLDER)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void extractRenderState(BeholderFamiliarEntity e, BeholderFamiliarRenderState s, float p) {
        super.extractRenderState(e, s, p);
        s.animationHeight = e.getAnimationHeight(p);
        s.eatTimer = e.getEatTimer(p);
        s.isEating = e.isEating();
        s.isSitting = e.isSitting();
        s.isPartying = e.isPartying();
        s.hasSpikes = e.hasSpikes();
        s.hasTongue = e.hasTongue();
        s.hasBeard = e.hasBeard();
        s.hasBlacksmithUpgrade = e.hasBlacksmithUpgrade();
        for (int i = 0; i < 4; i++) s.eyeRot[i] = e.getEyeRot(p, i);
        s.bigEyePos = e.getBigEyePos(p);
        s.mouthRot = e.getMouthRot(p);
    }

    @Override
    public BeholderFamiliarRenderState createRenderState() {
        return new BeholderFamiliarRenderState();
    }

    @Override
    public void submit(BeholderFamiliarRenderState s, PoseStack ps, SubmitNodeCollector c, CameraRenderState cam) {
        ps.pushPose();
        ps.translate(0, s.animationHeight, 0);
        if (s.isEating) {
            float scale = s.eatTimer < 5 / 6f ? 1 : Mth.sin((s.eatTimer - 5 / 6f) * 6 * (float) Math.PI) + 1;
            ps.scale(scale, scale, scale);
        }
        super.submit(s, ps, c, cam);
        ps.popPose();
    }

    @Override
    public Identifier getTextureLocation(BeholderFamiliarRenderState s) {
        return TEXTURES;
    }

    private static class SleepLayer extends RenderLayer<BeholderFamiliarRenderState, BeholderFamiliarModel> {
        private static final Identifier SLEEP = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/beholder_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<BeholderFamiliarRenderState, BeholderFamiliarModel> p) {
            super(p);
        }

        @Override
        public void submit(PoseStack ps, SubmitNodeCollector c, int light, BeholderFamiliarRenderState s, float yRot, float xRot) {
            if (s.isInvisible || !s.isSitting) return;
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), SLEEP, ps, c, light, s, -1, 0);
        }
    }
}
