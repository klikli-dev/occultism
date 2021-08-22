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

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class CthulhuFamiliarEntity extends FamiliarEntity {
    
    // TODO: Proper underwater navigation

    private static final DataParameter<Boolean> HAT = EntityDataManager.createKey(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRUNK = EntityDataManager.createKey(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private int angryTimer;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setHat(this.getRNG().nextDouble() < 0.1);
        this.setTrunk(this.getRNG().nextDouble() < 0.5);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount)) {
            if (source.getTrueSource() == getFamiliarOwner()) {
                angryTimer = 20 * 60 * 5;
            } else if (source.getTrueSource() != null) {
                Vector3d tp = RandomPositionGenerator.findRandomTarget(this, 8, 4);
                this.setLocationAndAngles(tp.getX() + 0.5, tp.getY(), tp.getZ() + 0.5, this.rotationYaw,
                        this.rotationPitch);
                this.navigator.clearPath();
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (angryTimer > 0)
            angryTimer--;
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (angryTimer > 0)
            angryTimer--;
    }

    @Override
    public boolean onLivingFall(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (angryTimer <= 0)
            return ImmutableList.of(new EffectInstance(Effects.WATER_BREATHING, 300, 0, false, false));
        else
            return ImmutableList.of();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAT, false);
        this.dataManager.register(TRUNK, false);
    }

    public boolean hasHat() {
        return this.dataManager.get(HAT);
    }

    private void setHat(boolean b) {
        this.dataManager.set(HAT, b);
    }

    public boolean hasTrunk() {
        return this.dataManager.get(TRUNK);
    }

    private void setTrunk(boolean b) {
        this.dataManager.set(TRUNK, b);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setHat(compound.getBoolean("hasHat"));
        this.setTrunk(compound.getBoolean("hasTrunk"));
        this.angryTimer = compound.getInt("angryTimer");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("hasTrunk", this.hasTrunk());
        compound.putInt("angryTimer", angryTimer);
    }
}
