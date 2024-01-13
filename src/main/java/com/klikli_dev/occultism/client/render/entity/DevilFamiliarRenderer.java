package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.client.model.entity.DevilFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DevilFamiliarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DevilFamiliarRenderer extends GeoEntityRenderer<DevilFamiliarEntity> {
    public DevilFamiliarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DevilFamiliarModel());
    }
}
