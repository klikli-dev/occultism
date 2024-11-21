package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.familiar.IesniumGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class IesniumGolemRenderer extends IronGolemRenderer {
    private static final ResourceLocation IESNIUM_GOLEM_LOCATION = ResourceLocation.fromNamespaceAndPath(Occultism.MODID,"textures/entity/iesnium_golem.png");

    public IesniumGolemRenderer(EntityRendererProvider.Context p_174188_) {
        super(p_174188_);
        this.addLayer(new IronGolemFlowerLayer(this, p_174188_.getBlockRenderDispatcher()));
    }

    @Override
    public ResourceLocation getTextureLocation(IronGolem entity) {
        return IESNIUM_GOLEM_LOCATION;
    }


}

