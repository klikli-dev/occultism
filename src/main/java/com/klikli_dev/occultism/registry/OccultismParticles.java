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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(
            BuiltInRegistries.PARTICLE_TYPE, Occultism.MODID);

    public static final Supplier<SimpleParticleType> RITUAL_WAITING = PARTICLES.register(
            "ritual_waiting", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SNOWFLAKE = PARTICLES.register(
            "snowflake", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SPIRIT_FIRE_FLAME = PARTICLES.register(
            "spirit_fire_flame", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> RED_FIRE_FLAME = PARTICLES.register(
            "red_fire_flame", () -> new SimpleParticleType(false));

    //NOTE: IF adding new particles, Register in ClientRegistryEventHandler.onRegisterParticleFactories
}
