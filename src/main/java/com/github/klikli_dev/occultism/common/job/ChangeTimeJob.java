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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public abstract class ChangeTimeJob extends SpiritJob {

    //region Fields
    protected int currentChangeTicks;
    protected int requiredChangeTicks;
    //endregion Fields


    //region Initialization
    public ChangeTimeJob(SpiritEntity entity, int requiredChangeTicks) {
        super(entity);
        this.requiredChangeTicks = requiredChangeTicks;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void init() {

    }

    @Override
    public void cleanup() {
        //in this case called on spirit death
        for (int i = 0; i < 5; i++) {
            ((ServerWorld) this.entity.level)
                    .sendParticles(ParticleTypes.PORTAL, this.entity.getX() + this.entity.level.getRandom().nextGaussian(),
                            this.entity.getY() + 0.5 + this.entity.level.getRandom().nextGaussian(), this.entity.getZ() + this.entity.level.getRandom().nextGaussian(), 5,
                            0.0, 0.0, 0.0,
                            0.0);
        }

    }

    @Override
    public void update() {
        super.update();

        this.currentChangeTicks++;
        if (!this.entity.swinging) {
            this.entity.swing(Hand.MAIN_HAND);
        }
        if (this.entity.level.getGameTime() % 2 == 0) {
            ((ServerWorld) this.entity.level)
                    .sendParticles(ParticleTypes.PORTAL, this.entity.getX(),
                            this.entity.getY() + 0.5, this.entity.getZ(), 3,
                            0.5, 0.0, 0.0,
                            0.0);
        }

        if (this.currentChangeTicks == this.requiredChangeTicks) {
            this.changeTime();
            this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundCategory.NEUTRAL, 1, 1);
            this.entity.die(DamageSource.OUT_OF_WORLD);
            this.entity.remove();
        }
    }

    @Override
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        compound.putInt("currentChangeTicks", this.currentChangeTicks);
        compound.putInt("requiredChangeTicks", this.requiredChangeTicks);
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        super.readJobFromNBT(compound);
        this.currentChangeTicks = compound.getInt("currentChangeTicks");
        this.requiredChangeTicks = compound.getInt("requiredChangeTicks");
    }

    //endregion Overrides

    //region Methods
    public abstract void changeTime();
    //endregion Methods
}
