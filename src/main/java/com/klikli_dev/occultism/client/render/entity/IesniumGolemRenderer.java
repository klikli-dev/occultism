package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IesniumGolemRenderer extends IronGolemRenderer {
    private static final Identifier IESNIUM_GOLEM_LOCATION = Identifier.fromNamespaceAndPath(Occultism.MODID,"textures/entity/iesnium_golem.png");

    public IesniumGolemRenderer(EntityRendererProvider.Context p_174188_) {
        super(p_174188_);
        this.addLayer(new IronGolemFlowerLayer(this, p_174188_.getBlockRenderDispatcher()));
    }

    @Override
    public Identifier getTextureLocation(IronGolem entity) {
        return IESNIUM_GOLEM_LOCATION;
    }


}

