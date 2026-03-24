package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PossessedZombiePiglinRenderer extends ZombieRenderer {
    private static final Identifier ZOMBIE_PIGLIN_LOCATION = Identifier.fromNamespaceAndPath(Occultism.MODID,"textures/entity/old_zombie_piglin.png");

    public PossessedZombiePiglinRenderer(EntityRendererProvider.Context p_174180_) {
        super(p_174180_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    protected void scale(Zombie livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 1.0625F;
        poseStack.scale(1.0625F, 1.0625F, 1.0625F);
        super.scale(livingEntity, poseStack, partialTickTime);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public Identifier getTextureLocation(Zombie entity) {
        return ZOMBIE_PIGLIN_LOCATION;
    }
}

