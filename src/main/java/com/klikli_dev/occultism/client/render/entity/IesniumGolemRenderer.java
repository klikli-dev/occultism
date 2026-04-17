package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.resources.Identifier;

public class IesniumGolemRenderer extends IronGolemRenderer {
    private static final Identifier IESNIUM_GOLEM_LOCATION = Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/entity/iesnium_golem.png");

    public IesniumGolemRenderer(Context p_174188_) {
        super(p_174188_);
    }

    @Override
    public Identifier getTextureLocation(IronGolemRenderState state) {
        return IESNIUM_GOLEM_LOCATION;
    }
}
