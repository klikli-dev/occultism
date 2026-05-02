package com.klikli_dev.occultism.client.render.entity;

import com.geckolib.renderer.GeoEntityRenderer;
import com.klikli_dev.occultism.client.model.entity.DevilFamiliarModel;
import com.klikli_dev.occultism.common.entity.familiar.DevilFamiliarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class DevilFamiliarRenderer extends GeoEntityRenderer<DevilFamiliarEntity, EntityRenderState> {
    public DevilFamiliarRenderer(Context renderManager) {
        super(renderManager, new DevilFamiliarModel());
    }
}
