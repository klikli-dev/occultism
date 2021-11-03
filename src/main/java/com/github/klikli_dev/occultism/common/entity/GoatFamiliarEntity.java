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

import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GoatFamiliarEntity extends ResizableFamiliarEntity {

    private static final DataParameter<Byte> VARIANTS = EntityDataManager.defineId(GoatFamiliarEntity.class,
            DataSerializers.BYTE);

    public GoatFamiliarEntity(EntityType<? extends GoatFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GoatFamiliarEntity(World worldIn, boolean hasRing, boolean hasBeard, byte size, LivingEntity owner) {
        super(OccultismEntities.GOAT_FAMILIAR.get(), worldIn);
        this.setRing(hasRing);
        this.setBeard(hasBeard);
        this.setSize(size);
        this.setFamiliarOwner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (super.hurt(pSource, pAmount)) {
            if (pSource.getEntity() != null) {
                ringBell(this);
            }
            return true;
        }
        return false;
    }

    private static void ringBell(FamiliarEntity entity) {
        LivingEntity owner = entity.getFamiliarOwner();
        if (owner == null || !entity.hasBlacksmithUpgrade())
            return;
        
        entity.playSound(SoundEvents.BELL_BLOCK, 1, 1);

        for (MobEntity e : entity.level.getEntitiesOfClass(MobEntity.class, entity.getBoundingBox().inflate(30),
                e -> e.isAlive() && e.getClassification(false) == EntityClassification.MONSTER))
            e.setTarget(owner);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANTS, (byte) 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants"))
            this.setRing(compound.getBoolean("hasRing"));
        this.entityData.set(VARIANTS, compound.getByte("variants"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("variants", this.entityData.get(VARIANTS));
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    private void setVariant(int offset, boolean b) {
        if (b)
            this.entityData.set(VARIANTS, (byte) (this.entityData.get(VARIANTS) | (1 << offset)));
        else
            this.entityData.set(VARIANTS, (byte) (this.entityData.get(VARIANTS) & (0b11111110 << offset)));
    }

    private boolean hasVariant(int offset) {
        return ((this.entityData.get(VARIANTS) >> offset) & 1) == 1;
    }

    public boolean hasRing() {
        return this.hasVariant(0);
    }

    private void setRing(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasBeard() {
        return this.hasVariant(1);
    }

    private void setBeard(boolean b) {
        this.setVariant(1, b);
    }
}
