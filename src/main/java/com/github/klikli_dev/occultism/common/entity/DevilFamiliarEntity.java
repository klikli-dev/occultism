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
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DevilFamiliarEntity extends FamiliarEntity {
    
    private static final DataParameter<Boolean> LOLLIPOP = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> NOSE = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EARS = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    
    private float heightOffset;

    public DevilFamiliarEntity(EntityType<? extends DevilFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        heightOffset = getRNG().nextFloat() * 5;
    }
    
    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
    }
    
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setLollipop(this.getRNG().nextDouble() < 0.1);
        this.setNose(this.getRNG().nextDouble() < 0.5);
        this.setEars(this.getRNG().nextDouble() < 0.5);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }
    
    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LOLLIPOP, false);
        this.dataManager.register(NOSE, false);
        this.dataManager.register(EARS, false);
    }
    
    public float getAnimationHeight(float partialTicks) {
        return MathHelper.cos((this.ticksExisted + heightOffset + partialTicks) / 3.5f);
    }

    public boolean hasLollipop() {
        return this.dataManager.get(LOLLIPOP);
    }

    private void setLollipop(boolean b) {
        this.dataManager.set(LOLLIPOP, b);
    }
    
    public boolean hasNose() {
        return this.dataManager.get(NOSE);
    }

    private void setNose(boolean b) {
        this.dataManager.set(NOSE, b);
    }
    
    public boolean hasEars() {
        return this.dataManager.get(EARS);
    }

    private void setEars(boolean b) {
        this.dataManager.set(EARS, b);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setLollipop(compound.getBoolean("hasLollipop"));
        this.setNose(compound.getBoolean("hasNose"));
        this.setEars(compound.getBoolean("hasEars"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("hasLollipop", this.hasLollipop());
        compound.putBoolean("hasNose", this.hasNose());
        compound.putBoolean("hasEars", this.hasEars());
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }
}
