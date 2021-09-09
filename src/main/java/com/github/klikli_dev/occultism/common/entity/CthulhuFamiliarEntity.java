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

import com.github.klikli_dev.occultism.common.capability.FamiliarSettingsCapability;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class CthulhuFamiliarEntity extends FamiliarEntity {

    private static final EntityDataAccessor<Boolean> HAT = SynchedEntityData.defineId(CthulhuFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TRUNK = SynchedEntityData.defineId(CthulhuFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(CthulhuFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GIVING = SynchedEntityData.defineId(CthulhuFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);

    private final WaterBoundPathNavigation waterNavigator;
    private final GroundPathNavigation groundNavigator;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0);
        this.waterNavigator = new WaterBoundPathNavigation(this, level);
        this.groundNavigator = new GroundPathNavigation(this, level);
        this.moveControl = new CthulhuMoveController(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createMobAttributes().add(ForgeMod.SWIM_SPEED.get(), 1f);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setHat(this.getRandom().nextDouble() < 0.1);
        this.setTrunk(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerWaterGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new GiveFlowerGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void updateSwimming() {
        if (!this.level.isClientSide) {
            if (this.isInWater()) {
                this.navigation = this.waterNavigator;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigator;
                this.setSwimming(false);
            }
        }
    }

    public float getAnimationHeight(float partialTicks) {
        return Mth.cos((this.tickCount + partialTicks) / 5);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (source.getEntity() == this.getFamiliarOwner()) {
                this.setAngry(true);
                this.setSitting(true);
            } else if (source.getEntity() != null) {
                Vec3 tp = DefaultRandomPos.getPos(this, 8, 4);
                this.absMoveTo(tp.x() + 0.5, tp.y(), tp.z() + 0.5, this.yRotO,
                        this.xRotO);
                this.navigation.stop();
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAngry() && this.getRandom().nextDouble() < 0.0007)
            this.setAngry(false);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            if (this.isAngry())
                return ImmutableList.of(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0, false, false));
        }
        return Collections.emptyList();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAT, false);
        this.entityData.define(TRUNK, false);
        this.entityData.define(ANGRY, false);
        this.entityData.define(GIVING, false);
    }

    public boolean hasHat() {
        return this.entityData.get(HAT);
    }

    private void setHat(boolean b) {
        this.entityData.set(HAT, b);
    }

    public boolean hasTrunk() {
        return this.entityData.get(TRUNK);
    }

    private void setTrunk(boolean b) {
        this.entityData.set(TRUNK, b);
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    private void setAngry(boolean b) {
        this.entityData.set(ANGRY, b);
    }

    public boolean isGiving() {
        return this.entityData.get(GIVING);
    }

    private void setGiving(boolean b) {
        this.entityData.set(GIVING, b);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHat(compound.getBoolean("hasHat"));
        this.setTrunk(compound.getBoolean("hasTrunk"));
        this.setAngry(compound.getBoolean("isAngry"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasHat", this.hasHat());
        compound.putBoolean("hasTrunk", this.hasTrunk());
        compound.putBoolean("isAngry", this.isAngry());
    }

    private static class CthulhuMoveController extends MoveControl {
        private final CthulhuFamiliarEntity cthulhu;

        CthulhuMoveController(CthulhuFamiliarEntity cthulhu) {
            super(cthulhu);
            this.cthulhu = cthulhu;
        }

        @Override
        public void tick() {
            if (this.cthulhu.isInWater()) {
                this.cthulhu.setDeltaMovement(this.cthulhu.getDeltaMovement().add(0, 0.005, 0));
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    float maxSpeed = (float) (this.speedModifier * this.cthulhu.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 3;
                    this.cthulhu.setSpeed(Mth.lerp(0.125f, this.cthulhu.getSpeed(), maxSpeed));
                    double dx = this.wantedX - this.cthulhu.getX();
                    double dy = this.wantedY - this.cthulhu.getY();
                    double dz = this.wantedZ - this.cthulhu.getZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance < 0.1) {
                        this.cthulhu.setZza(0);
                        return;
                    }

                    if (Math.abs(dy) > 0.0001) {
                        this.cthulhu.setDeltaMovement(
                                this.cthulhu.getDeltaMovement().add(0, this.cthulhu.getSpeed() * (dy / distance) * 0.1, 0));
                    }

                    if (Math.abs(dx) > 0.0001 || Math.abs(dz) > 0.0001) {
                        float rotate = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90f;
                        this.cthulhu.yRotO = this.rotlerp(this.cthulhu.yRotO, rotate, 8);
                        this.cthulhu.yBodyRot = this.cthulhu.yRotO;
                    }

                } else {
                    this.cthulhu.setSpeed(0);
                }
            } else {
                super.tick();
            }
        }
    }

    private static class FollowOwnerWaterGoal extends FollowOwnerGoal {

        public FollowOwnerWaterGoal(FamiliarEntity entity, double speed, float minDist, float maxDist) {
            super(entity, speed, minDist, maxDist);
        }

        @Override
        protected boolean shouldTeleport(LivingEntity owner) {
            return !this.entity.level.isWaterAt(owner.blockPosition()) && this.entity.isInWater();
        }

    }

    private static class GiveFlowerGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 60 * 5;

        private final CthulhuFamiliarEntity cthulhu;
        private DevilFamiliarEntity devil;
        private int cooldown = MAX_COOLDOWN;

        public GiveFlowerGoal(CthulhuFamiliarEntity cthulhu) {
            this.cthulhu = cthulhu;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.devil = this.findDevil();
            return this.devil != null && this.cooldown-- < 0 && this.cthulhu.distanceToSqr(this.devil) > 3;
        }

        @Override
        public boolean canContinueToUse() {
            return this.devil != null && this.cthulhu.isPathFinding();
        }

        public void startExecuting() {
            this.cthulhu.getNavigation().moveTo(this.devil, 0.3);
            this.cthulhu.setGiving(true);
        }

        public void resetTask() {
            this.cthulhu.setGiving(false);
            this.cthulhu.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN;
            this.devil = null;
        }

        @Override
        public void tick() {
            if (this.cthulhu.distanceToSqr(this.devil) < 2) {
                ((ServerLevel) this.cthulhu.level).sendParticles(ParticleTypes.HEART, this.devil.getBlockX(), this.devil.getBlockY() + 1,
                        this.devil.getBlockZ(), 1, 0, 0, 0, 1);
                this.devil = null;
            }
        }

        private DevilFamiliarEntity findDevil() {
            List<DevilFamiliarEntity> devils = this.cthulhu.level.getEntities(DevilFamiliarEntity.class,
                    this.cthulhu.getBoundingBox().inflate(4));
            return devils.isEmpty() ? null : devils.get(0);
        }

    }
}
