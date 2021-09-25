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

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class CthulhuFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Boolean> HAT = EntityDataManager.defineId(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRUNK = EntityDataManager.defineId(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.defineId(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GIVING = EntityDataManager.defineId(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private final SwimmerPathNavigator waterNavigator;
    private final GroundPathNavigator groundNavigator;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 0);
        this.waterNavigator = new SwimmerPathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
        this.moveControl = new MoveController(this);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(ForgeMod.SWIM_SPEED.get(), 1f);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
                                           ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setHat(this.getRandom().nextDouble() < 0.1);
        this.setTrunk(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerWaterGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new GiveFlowerGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
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
        return MathHelper.cos((this.tickCount + partialTicks) / 5);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (source.getEntity() == this.getFamiliarOwner()) {
                this.setAngry(true);
                this.setSitting(true);
                OccultismAdvancements.FAMILIAR.trigger(this.getFamiliarOwner(), FamiliarTrigger.Type.CTHULHU_SAD);
            } else if (source.getEntity() != null) {
                Vector3d tp = RandomPositionGenerator.getPos(this, 8, 4);
                if (tp != null) {
                    this.moveTo(tp.x() + 0.5, tp.y(), tp.z() + 0.5, this.yRot,
                            this.xRot);
                }
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
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            if (this.isAngry())
                return ImmutableList.of(new EffectInstance(Effects.WATER_BREATHING, 300, 0, false, false));
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
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setHat(compound.getBoolean("hasHat"));
        this.setTrunk(compound.getBoolean("hasTrunk"));
        this.setAngry(compound.getBoolean("isAngry"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasHat", this.hasHat());
        compound.putBoolean("hasTrunk", this.hasTrunk());
        compound.putBoolean("isAngry", this.isAngry());
    }

    private static class MoveController extends MovementController {
        private final CthulhuFamiliarEntity cthulhu;

        MoveController(CthulhuFamiliarEntity cthulhu) {
            super(cthulhu);
            this.cthulhu = cthulhu;
        }

        @Override
        public void tick() {
            if (this.cthulhu.isInWater()) {
                this.cthulhu.setDeltaMovement(this.cthulhu.getDeltaMovement().add(0, 0.005, 0));
                if (this.operation == MovementController.Action.MOVE_TO) {
                    float maxSpeed = (float) (this.speedModifier * this.cthulhu.getAttributeValue(Attributes.MOVEMENT_SPEED))
                            * 3;
                    this.cthulhu.setSpeed(MathHelper.lerp(0.125f, this.cthulhu.getSpeed(), maxSpeed));
                    double dx = this.wantedX - this.cthulhu.getX();
                    double dy = this.wantedY - this.cthulhu.getY();
                    double dz = this.wantedZ - this.cthulhu.getZ();
                    double distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance < 0.1) {
                        this.cthulhu.setZza(0);
                        return;
                    }

                    if (Math.abs(dy) > 0.0001) {
                        this.cthulhu.setDeltaMovement(this.cthulhu.getDeltaMovement().add(0,
                                this.cthulhu.getSpeed() * (dy / distance) * 0.1, 0));
                    }

                    if (Math.abs(dx) > 0.0001 || Math.abs(dz) > 0.0001) {
                        float rotate = (float) (MathHelper.atan2(dz, dx) * (180 / Math.PI)) - 90f;
                        this.cthulhu.yRot = this.rotlerp(this.cthulhu.yRot, rotate, 8);
                        this.cthulhu.yBodyRot = this.cthulhu.yRot;
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

        public void start() {
            this.cthulhu.getNavigation().moveTo(this.devil, 0.3);
            this.cthulhu.setGiving(true);
        }

        public void stop() {
            this.cthulhu.setGiving(false);
            this.cthulhu.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN;
            this.devil = null;
        }

        @Override
        public void tick() {
            if (this.cthulhu.distanceToSqr(this.devil) < 2) {
                ((ServerWorld) this.cthulhu.level).sendParticles(ParticleTypes.HEART, this.devil.getX(), this.devil.getY() + 1,
                        this.devil.getZ(), 1, 0, 0, 0, 1);
                this.devil = null;
            }
        }

        private DevilFamiliarEntity findDevil() {
            List<DevilFamiliarEntity> devils = this.cthulhu.level.getEntitiesOfClass(DevilFamiliarEntity.class,
                    this.cthulhu.getBoundingBox().inflate(4));
            return devils.isEmpty() ? null : devils.get(0);
        }

    }
}
