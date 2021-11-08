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

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GuardianFamiliarEntity extends FamiliarEntity {

    private static final byte MAX_LIVES = 5;
    public static final byte UNDAMAGED = MAX_LIVES;
    public static final byte ONE_ARMED = 4;
    public static final byte ONE_LEGGED = 3;
    public static final byte FLOATING = 2;
    public static final byte DEATHS_DOOR = 1;
    public static final byte DEAD = 0;

    private static final EntityDataAccessor<Float> RED = SynchedEntityData.defineId(GuardianFamiliarEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> GREEN = SynchedEntityData.defineId(GuardianFamiliarEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> BLUE = SynchedEntityData.defineId(GuardianFamiliarEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> LIVES = SynchedEntityData.defineId(GuardianFamiliarEntity.class,
            EntityDataSerializers.BYTE);

    private byte lives0 = -1;
    private int particleTimer;

    public GuardianFamiliarEntity(EntityType<? extends GuardianFamiliarEntity> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setColor();
        this.setTree(this.getRandom().nextDouble() < 0.1);
        this.setBird(this.getRandom().nextDouble() < 0.5);
        this.setTools(this.getRandom().nextDouble() < 0.5);
        this.setLives((byte) (this.getRandom().nextInt(5) + 1));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean hasLegs() {
        return this.getLives() > 2;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        if (!this.hasLegs())
            return;
        super.playStepSound(pPos, pBlock);
    }

    public float getAnimationHeight(float partialTicks) {
        return this.hasLegs() ? 0 : Mth.cos((this.tickCount + partialTicks) / 5);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade() && this.getLives() != MAX_LIVES;
    }

    @Override
    public void blacksmithUpgrade() {
        super.blacksmithUpgrade();
        this.setLives((byte) (this.getLives() + 1));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
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
        this.entityData.define(LIVES, (byte) 0);
    }

    private void setColor() {
        float r, g, b;
        Random rand = this.getRandom();
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

    public boolean sacrifice() {
        byte lives = this.getLives();
        boolean success = lives > 0;
        if (lives == DEATHS_DOOR)
            OccultismAdvancements.FAMILIAR.trigger(this.getFamiliarOwner(), FamiliarTrigger.Type.GUARDIAN_ULTIMATE_SACRIFICE);
        this.setLives((byte) (lives - 1));
        return success;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getLives() <= 0 && !this.level.isClientSide)
            this.kill();

        if (this.lives0 != -1 && this.lives0 > this.getLives()) {
            this.particleTimer = 30;
            this.playSound(SoundEvents.GENERIC_HURT, this.getSoundVolume(), this.getVoicePitch());
        }

        this.lives0 = this.getLives();

        if (this.level.isClientSide) {
            if (this.particleTimer-- > 0) {
                for (int i = 0; i < 20; i++) {
                    this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState())
                            .setPos(this.blockPosition()), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
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
        return this.hasVariant(0);
    }

    private void setTree(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasBird() {
        return this.hasVariant(1);
    }

    private void setBird(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasTools() {
        return this.hasVariant(2);
    }

    private void setTools(boolean b) {
        this.setVariant(2, b);
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("red", this.getRed());
        compound.putFloat("green", this.getGreen());
        compound.putFloat("blue", this.getBlue());
        compound.putByte("lives", this.getLives());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setRed(compound.getFloat("red"));
        this.setGreen(compound.getFloat("green"));
        this.setBlue(compound.getFloat("blue"));
        if (!compound.contains("variants")) {
            this.setTree(compound.getBoolean("hasTree"));
            this.setBird(compound.getBoolean("hasBird"));
            this.setTools(compound.getBoolean("hasTools"));
        }
        this.setLives(compound.getByte("lives"));

        if (compound.getBoolean("for_patchouli")) {
            this.setLives(MAX_LIVES);
            this.setColor();
        }
    }
}
