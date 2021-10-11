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

package com.github.klikli_dev.occultism.common.entity;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class HeadlessFamiliarEntity extends FamiliarEntity {

    private static final ImmutableBiMap<Byte, EntityType<?>> TYPES_LOOKUP;
    static {
        ImmutableBiMap.Builder<Byte, EntityType<?>> builder = new ImmutableBiMap.Builder<>();
        builder.put((byte) 1, EntityType.PLAYER);
        builder.put((byte) 2, EntityType.ZOMBIE);
        builder.put((byte) 3, EntityType.SKELETON);
        builder.put((byte) 4, EntityType.WITHER_SKELETON);
        builder.put((byte) 5, EntityType.CREEPER);
        builder.put((byte) 6, EntityType.SPIDER);
        TYPES_LOOKUP = builder.build();
    }

    private static final int HEAD_TIME = 20 * 60;
    private static final byte NO_HEAD = 0;

    private static final DataParameter<Byte> HEAD = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BYTE);

    private int headTimer;

    public HeadlessFamiliarEntity(EntityType<? extends HeadlessFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD, NO_HEAD);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {

            if (headTimer-- == 0)
                this.setHead(NO_HEAD);
        }
    }

    private void setHead(byte head) {
        this.entityData.set(HEAD, head);
        if (head != NO_HEAD)
            this.headTimer = HEAD_TIME;
    }

    private byte getHead() {
        return this.entityData.get(HEAD);
    }

    public boolean hasHead() {
        return getHead() != NO_HEAD;
    }

    public EntityType<?> getHeadType() {
        return TYPES_LOOKUP.getOrDefault(this.getHead(), null);
    }

    public void setHeadType(EntityType<?> type) {
        if (type == null || !TYPES_LOOKUP.inverse().containsKey(type))
            return;
        this.setHead(TYPES_LOOKUP.inverse().get(type));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("head", this.getHead());
        compound.putInt("headTimer", this.headTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setHead(compound.getByte("head"));
        this.headTimer = compound.getInt("headTimer");
    }

}
