package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.GreedyFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GreedyFamiliarRenderer extends MobRenderer<GreedyFamiliarEntity, GreedyFamiliarModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Occultism.MODID,
            "textures/entity/greedy_familiar.png");

    public GreedyFamiliarRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GreedyFamiliarModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(GreedyFamiliarEntity entity) {
        return TEXTURES;
    }

}
