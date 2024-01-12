package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.client.model.entity.DevilFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DevilFamiliarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DevilFamiliarRenderer extends GeoEntityRenderer<DevilFamiliarEntity> {
    public DevilFamiliarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DevilFamiliarModel());
    }
}
