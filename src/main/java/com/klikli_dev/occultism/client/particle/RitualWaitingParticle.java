/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;

public class RitualWaitingParticle extends TextureSheetParticle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;

    private RitualWaitingParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
                                  double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.xd = xSpeedIn;
        this.yd = ySpeedIn;
        this.zd = zSpeedIn;
        this.x = xCoordIn;
        this.y = yCoordIn;
        this.z = zCoordIn;
        this.portalPosX = this.x;
        this.portalPosY = this.y;
        this.portalPosZ = this.z;
        this.quadSize = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
        //Make particle a random shade of gray, the rest is the same as the portal
        float f = this.random.nextFloat() * 0.6f + 0.4f;
        this.rCol = f * 0.3f;
        this.gCol = f * 0.3f;
        this.bCol = f * 0.3f;

        this.lifetime = (int) (Math.random() * 10.0D) + 40;
    }

    @Override
    public float getQuadSize(float p_217561_1_) {
        float f = ((float) this.age + p_217561_1_) / (float) this.lifetime;
        f = 1.0F - f;
        f = f * f;
        f = 1.0F - f;
        return this.quadSize * f;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float f = (float) this.age / (float) this.lifetime;
            float f1 = -f + f * f * 2.0F;
            float f2 = 1.0F - f1;
            this.x = this.portalPosX + this.xd * (double) f2;
            this.y = this.portalPosY + this.yd * (double) f2 + (double) (1.0F - f);
            this.z = this.portalPosZ + this.zd * (double) f2;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    @Override
    public int getLightColor(float partialTick) {
        int i = super.getLightColor(partialTick);
        float f = (float) this.age / (float) this.lifetime;
        f = f * f;
        f = f * f;
        int j = i & 255;
        int k = i >> 16 & 255;
        k = k + (int) (f * 15.0F * 16.0F);
        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }


    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50607_1_) {
            this.spriteSet = p_i50607_1_;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            RitualWaitingParticle particle = new RitualWaitingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}