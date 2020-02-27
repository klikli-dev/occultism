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

package com.github.klikli_dev.occultism.client.particle;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public enum ModParticleType {
    RITUAL_WAITING(ParticleRitualWaiting.class);

    //region Fields
    private final Class<? extends Particle> particleClass;
    //endregion Fields

    //region Initialization
    ModParticleType(Class<? extends Particle> particleClass) {
        this.particleClass = particleClass;
    }
    //endregion Initialization

    //region Methods
    public Particle get(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
                        double ySpeedIn, double zSpeedIn) {
        try {
            return this.particleClass
                           .getConstructor(World.class, double.class, double.class, double.class, double.class,
                                   double.class, double.class)
                           .newInstance(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        } catch (Exception e) {
            Occultism.logger.error(e);
            return null;
        }
    }
    //endregion Methods
}
