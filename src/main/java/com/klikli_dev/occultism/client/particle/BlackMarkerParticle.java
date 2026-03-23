package com.klikli_dev.occultism.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class BlackMarkerParticle extends SingleQuadParticle {

    private final SpriteSet sprites;

    protected BlackMarkerParticle(
            ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites
    ) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0.99F;
        this.speedUpWhenYMotionIsBlocked = false;
        this.sprites = sprites;
        this.quadSize *= 0.75F;
        this.hasPhysics = false;
        this.gravity = 0;
        this.lifetime = 100;
        this.setSize(0.5F,0.5F);
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(partialTick);
        int j = i & 0xFF;
        int k = i >> 16 & 0xFF;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            BlackMarkerParticle particle = new BlackMarkerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
            particle.setParticleSpeed(0,0,0);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
