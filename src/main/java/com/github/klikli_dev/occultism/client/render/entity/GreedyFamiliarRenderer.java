package com.github.klikli_dev.occultism.client.render.entity;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.model.entity.GreedyFamiliarModel;
import com.github.klikli_dev.occultism.common.entity.GreedyFamiliarEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
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

    @Override
    public void render(GreedyFamiliarEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        if (entityIn.isSitting())
            matrixStackIn.translate(0, -0.25, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }

}
