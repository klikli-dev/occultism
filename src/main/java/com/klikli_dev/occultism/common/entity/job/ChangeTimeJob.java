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

package com.klikli_dev.occultism.common.entity.job;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.function.Supplier;

public abstract class ChangeTimeJob extends SpiritJob {

    protected int currentChangeTicks;
    protected Supplier<Integer> requiredChangeTicks;

    protected long newTime;

    public ChangeTimeJob(SpiritEntity entity, Supplier<Integer>  requiredChangeTicks) {
        super(entity);
        this.requiredChangeTicks = requiredChangeTicks;
    }

    @Override
    public void onInit() {
        this.newTime = this.getNewTime();

        if (!this.isEnabled()) {
            this.entity.getOwner().sendSystemMessage(this.getDisabledMessage());
            this.finishChangeTime();
        }
    }

    @Override
    public void cleanup() {
        //in this case called on spirit death
        for (int i = 0; i < 5; i++) {
            ((ServerLevel) this.entity.level())
                    .sendParticles(ParticleTypes.PORTAL, this.entity.getX() + this.entity.level().getRandom().nextGaussian(),
                            this.entity.getY() + 0.5 + this.entity.level().getRandom().nextGaussian(), this.entity.getZ() + this.entity.level().getRandom().nextGaussian(), 5,
                            0.0, 0.0, 0.0,
                            0.0);
        }

    }

    @Override
    public void update() {
        super.update();

        this.currentChangeTicks++;
        if (!this.entity.swinging) {
            this.entity.swing(InteractionHand.MAIN_HAND);
        }

        if (this.entity.level().getGameTime() % 2 == 0) {
            ((ServerLevel) this.entity.level())
                    .sendParticles(ParticleTypes.PORTAL, this.entity.getX(),
                            this.entity.getY() + 0.5, this.entity.getZ(), 3,
                            0.5, 0.0, 0.0,
                            0.0);
        }

        if (this.isEnabled())
            this.updateTime();

        if (this.currentChangeTicks == this.requiredChangeTicks.get()) {
            this.finishChangeTime();
        }
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putInt("currentChangeTicks", this.currentChangeTicks);
        compound.putInt("requiredChangeTicks", this.requiredChangeTicks.get());
        compound.putLong("newTime", this.newTime);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, HolderLookup.Provider provider) {
        super.readJobFromNBT(compound, provider);
        this.currentChangeTicks = compound.getInt("currentChangeTicks");
        var requiredChangeTicks = compound.getInt("requiredChangeTicks");
        this.requiredChangeTicks = () -> requiredChangeTicks;
        this.newTime = compound.getLong("newTime");
    }

    public void updateTime() {
        var level = (ServerLevelData) this.entity.level().getLevelData();

        var remainingTime = this.newTime - level.getDayTime();
        var remainingTicks = Math.max(this.requiredChangeTicks.get() - this.currentChangeTicks, 1);
        var timeChange = remainingTime / remainingTicks;

        var interpolatedTime = level.getDayTime() + timeChange;

        if (interpolatedTime >= this.newTime) {
            interpolatedTime = this.newTime;
        }

        level.setDayTime(interpolatedTime);
    }

    public void finishChangeTime() {
        this.entity.level().playSound(null, this.entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL, 1, 1);
        this.entity.die(this.entity.damageSources().fellOutOfWorld());
        this.entity.remove(Entity.RemovalReason.DISCARDED);
    }

    public abstract long getNewTime();

    public abstract Component getDisabledMessage();

    public abstract boolean isEnabled();
}
