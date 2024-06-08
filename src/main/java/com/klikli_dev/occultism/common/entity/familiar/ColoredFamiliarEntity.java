/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.klikli_dev.occultism.common.entity.familiar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class ColoredFamiliarEntity extends FamiliarEntity {

    private static final EntityDataAccessor<Float> RED = SynchedEntityData.defineId(ColoredFamiliarEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> GREEN = SynchedEntityData.defineId(ColoredFamiliarEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> BLUE = SynchedEntityData.defineId(ColoredFamiliarEntity.class,
            EntityDataSerializers.FLOAT);

    public ColoredFamiliarEntity(EntityType<? extends ColoredFamiliarEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RED, 0f);
        builder.define(GREEN, 0f);
        builder.define(BLUE, 0f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setRed(compound.getFloat("red"));
        this.setGreen(compound.getFloat("green"));
        this.setBlue(compound.getFloat("blue"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("red", this.getRed());
        compound.putFloat("green", this.getGreen());
        compound.putFloat("blue", this.getBlue());
    }

    public float getRed() {
        return this.entityData.get(RED);
    }

    private void setRed(float f) {
        this.entityData.set(RED, f);
    }

    public float getGreen() {
        return this.entityData.get(GREEN);
    }

    private void setGreen(float f) {
        this.entityData.set(GREEN, f);
    }

    public float getBlue() {
        return this.entityData.get(BLUE);
    }

    private void setBlue(float f) {
        this.entityData.set(BLUE, f);
    }

    protected void setColor() {
        float r, g, b;
        var rand = this.getRandom();
        for (int i = 0; i < 20; i++) {
            r = rand.nextFloat();
            g = rand.nextFloat();
            b = rand.nextFloat();
            if (Mth.abs(r - g) > 0.2f || Mth.abs(r - b) > 0.2f || Mth.abs(g - b) > 0.2f) {
                this.setRed(r);
                this.setGreen(g);
                this.setBlue(b);
                return;
            }
        }
    }
}
