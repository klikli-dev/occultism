package com.klikli_dev.occultism.client.render.entity;

import com.klikli_dev.occultism.Occultism;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Zombie;

public class PossessedZombiePiglinRenderer extends ZombieRenderer {
    private static final Identifier ZOMBIE_PIGLIN_LOCATION = Identifier.fromNamespaceAndPath(Occultism.MODID,"textures/entity/old_zombie_piglin.png");

    public PossessedZombiePiglinRenderer(Context p_174180_) {
        super(p_174180_);
    }

    protected void scale(ZombieRenderState state, PoseStack poseStack) {
        poseStack.scale(1.0625F, 1.0625F, 1.0625F);
        super.scale(state, poseStack);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        return ZOMBIE_PIGLIN_LOCATION;
    }
}
