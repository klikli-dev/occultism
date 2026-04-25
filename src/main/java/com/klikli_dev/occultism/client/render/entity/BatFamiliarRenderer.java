package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.model.entity.BatFamiliarModel;
import com.klikli_dev.occultism.client.render.entity.state.BatFamiliarRenderState;
import com.klikli_dev.occultism.common.entity.familiar.BatFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class BatFamiliarRenderer extends MobRenderer<BatFamiliarEntity, BatFamiliarRenderState, BatFamiliarModel> {
    private static final Identifier TEXTURES = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/bat_familiar.png");

    public BatFamiliarRenderer(Context c) {
        super(c, new BatFamiliarModel(c.bakeLayer(OccultismModelLayers.FAMILIAR_BAT)), 0.3f);
    }

    @Override
    public void extractRenderState(BatFamiliarEntity e, BatFamiliarRenderState s, float p) {
        super.extractRenderState(e, s, p);
        s.animationHeight = e.getAnimationHeight(p);
        s.isSitting = e.isSitting();
        s.isPartying = e.isPartying();
        s.hasHair = e.hasHair();
        s.hasRibbon = e.hasRibbon();
        s.hasTail = e.hasTail();
        s.hasBlacksmithUpgrade = e.hasBlacksmithUpgrade();
    }

    @Override
    public BatFamiliarRenderState createRenderState() {
        return new BatFamiliarRenderState();
    }

    @Override
    public void submit(BatFamiliarRenderState s, PoseStack ps, SubmitNodeCollector c, CameraRenderState cam) {
        ps.pushPose();
        if (!s.isSitting || s.isPartying) ps.translate(0, s.animationHeight * 0.1 + 0.1f, 0);
        super.submit(s, ps, c, cam);
        ps.popPose();
    }

    @Override
    public Identifier getTextureLocation(BatFamiliarRenderState s) {
        return TEXTURES;
    }
}
