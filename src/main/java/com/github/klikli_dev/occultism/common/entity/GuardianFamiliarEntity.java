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

import java.util.Random;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class GuardianFamiliarEntity extends FamiliarEntity {

    private static final byte MAX_LIVES = 5;

    private static final DataParameter<Float> RED = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> GREEN = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> BLUE = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Boolean> TREE = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BIRD = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TOOLS = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> LIVES = EntityDataManager.defineId(GuardianFamiliarEntity.class,
            DataSerializers.BYTE);

    private byte lives0 = -1;
    private int particleTimer;

    public GuardianFamiliarEntity(EntityType<? extends GuardianFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason,
            ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
        this.setColor();
        this.setTree(this.getRandom().nextDouble() < 0.1);
        this.setBird(this.getRandom().nextDouble() < 0.5);
        this.setTools(this.getRandom().nextDouble() < 0.5);
        this.setLives((byte) (this.getRandom().nextInt(5) + 1));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !hasBlacksmithUpgrade() && this.getLives() != MAX_LIVES;
    }

    @Override
    public void blacksmithUpgrade() {
        super.blacksmithUpgrade();
        this.setLives((byte) (this.getLives() + 1));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasTree())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RED, 0f);
        this.entityData.define(GREEN, 0f);
        this.entityData.define(BLUE, 0f);
        this.entityData.define(TREE, false);
        this.entityData.define(BIRD, false);
        this.entityData.define(TOOLS, false);
        this.entityData.define(LIVES, (byte) 0);
    }

    private void setColor() {
        float r, g, b;
        Random rand = this.getRandom();
        for (int i = 0; i < 20; i++) {
            r = rand.nextFloat();
            g = rand.nextFloat();
            b = rand.nextFloat();
            if (MathHelper.abs(r - g) > 0.2f || MathHelper.abs(r - b) > 0.2f || MathHelper.abs(g - b) > 0.2f) {
                this.setRed(r);
                this.setGreen(g);
                this.setBlue(b);
                return;
            }
        }
    }

    public boolean sacrifice() {
        boolean success = this.getLives() > 0;
        this.setLives((byte) (this.getLives() - 1));
        return success;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getLives() <= 0 && !level.isClientSide)
            this.kill();

        if (lives0 != -1 && lives0 != getLives()) {
            particleTimer = 30;
            playSound(SoundEvents.GENERIC_HURT, getSoundVolume(), getVoicePitch());
        }

        lives0 = getLives();

        if (level.isClientSide) {
            if (particleTimer-- > 0) {
                for (int i = 0; i < 20; i++) {
                    level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState())
                            .setPos(this.blockPosition()), getRandomX(0.5), getRandomY(), getRandomZ(0.5), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
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

    public boolean hasTree() {
        return this.entityData.get(TREE);
    }

    private void setTree(boolean b) {
        this.entityData.set(TREE, b);
    }

    public boolean hasBird() {
        return this.entityData.get(BIRD);
    }

    private void setBird(boolean b) {
        this.entityData.set(BIRD, b);
    }

    public boolean hasTools() {
        return this.entityData.get(TOOLS);
    }

    private void setTools(boolean b) {
        this.entityData.set(TOOLS, b);
    }

    public byte getLives() {
        return this.entityData.get(LIVES);
    }

    private void setLives(byte b) {
        if (b < 0 || b > MAX_LIVES)
            return;
        this.entityData.set(LIVES, b);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("red", getRed());
        compound.putFloat("green", getGreen());
        compound.putFloat("blue", getBlue());
        compound.putBoolean("hasTree", hasTree());
        compound.putBoolean("hasBird", hasBird());
        compound.putBoolean("hasTools", hasTools());
        compound.putByte("lives", getLives());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setRed(compound.getFloat("red"));
        this.setGreen(compound.getFloat("green"));
        this.setBlue(compound.getFloat("blue"));
        this.setTree(compound.getBoolean("hasTree"));
        this.setBird(compound.getBoolean("hasBird"));
        this.setTools(compound.getBoolean("hasTools"));
        this.setLives(compound.getByte("lives"));
    }
}
