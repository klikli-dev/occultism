package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.FairyFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.FairyFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.FairyFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class FairyFamiliarRenderer extends MobRenderer<FairyFamiliarEntity, FairyFamiliarRenderState, FairyFamiliarModel> {
    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/fairy_familiar.png");

    public FairyFamiliarRenderer(Context context) {
        super(context, new FairyFamiliarModel(context.bakeLayer(OccultismModelLayers.FAMILIAR_FAIRY)), 0.3f);
        this.addLayer(new SleepLayer(this));
    }

    @Override
    public void extractRenderState(FairyFamiliarEntity entity, FairyFamiliarRenderState s, float pt) {
        super.extractRenderState(entity, s, pt);
        s.animationHeight = entity.getAnimationHeight(pt);
        s.isSitting = entity.isSitting();
        s.isPartying = entity.isPartying();
        s.hasFlower = entity.hasFlower();
        s.hasTeeth = entity.hasTeeth();
        s.isLeftHanded = entity.isLeftHanded();
        s.partyArmRotX = entity.getPartyArmRotX(pt);
        s.partyArmRotY = entity.getPartyArmRotY(pt);
        s.supportAnim = entity.getSupportAnim(pt);
        Entity target = entity.getMagicTarget();
        if (target != null) {
            Vec3 pos = entity.getMagicPosition(pt).subtract(entity.getPosition(pt));
            Vec2 ra = entity.getMagicRadiusAngle(pt);
            s.hasMagicTarget = true;
            s.magicPosX = (float) pos.x;
            s.magicPosY = (float) pos.y;
            s.magicPosZ = (float) pos.z;
            s.magicRadiusAngleY = ra.y;
        } else s.hasMagicTarget = false;
    }

    @Override
    public FairyFamiliarRenderState createRenderState() {
        return new FairyFamiliarRenderState();
    }

    @Override
    public void submit(FairyFamiliarRenderState s, PoseStack ps, SubmitNodeCollector c, CameraRenderState cam) {
        ps.pushPose();
        ps.translate(0, s.animationHeight, 0);
        if (s.hasMagicTarget) {
            ps.translate(s.magicPosX, s.magicPosY, s.magicPosZ);
            ps.mulPose(new Quaternionf().rotateXYZ(0, -s.magicRadiusAngleY * ((float) Math.PI / 180F), 0));
            this.shadowStrength = 0;
        } else this.shadowStrength = 1;
        super.submit(s, ps, c, cam);
        ps.popPose();
    }

    @Override
    public Identifier getTextureLocation(FairyFamiliarRenderState state) {
        return TEXTURES;
    }

    private static class SleepLayer extends RenderLayer<FairyFamiliarRenderState, FairyFamiliarModel> {
        private static final Identifier SLEEP = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/fairy_familiar_sleep.png");

        public SleepLayer(RenderLayerParent<FairyFamiliarRenderState, FairyFamiliarModel> parent) {
            super(parent);
        }

        @Override
        public void submit(PoseStack ps, SubmitNodeCollector c, int light, FairyFamiliarRenderState s, float yRot, float xRot) {
            if (s.isInvisible || !s.isSitting || s.isPartying) return;
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), SLEEP, ps, c, light, s, -1, 0);
        }
    }
}
