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

import jdk.nashorn.internal.ir.annotations.Immutable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
import net.minecraftforge.common.ForgeMod;

import java.util.Collections;

public class CthulhuFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Boolean> HAT = EntityDataManager.createKey(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRUNK = EntityDataManager.createKey(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.createKey(CthulhuFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private final SwimmerPathNavigator waterNavigator;
    private final GroundPathNavigator groundNavigator;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0);
        this.waterNavigator = new SwimmerPathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
        this.moveController = new MoveController(this);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().createMutableAttribute(ForgeMod.SWIM_SPEED.get(), 1f);
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
        this.goalSelector.addGoal(3, new FollowOwnerWaterGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void updateSwimming() {
        if (!this.world.isRemote) {
            if (this.isInWater()) {
                this.navigator = this.waterNavigator;
                this.setSwimming(true);
            } else {
                this.navigator = this.groundNavigator;
                this.setSwimming(false);
            }
        }
    }
    
    public float getAnimationHeight(float partialTicks) {
        return MathHelper.cos((this.ticksExisted + partialTicks) / 5);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount)) {
            if (source.getTrueSource() == this.getFamiliarOwner()) {
                this.setAngry(true);
                this.setSitting(true);
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
        if (this.isAngry() && this.getRNG().nextDouble() < 0.0007)
            this.setAngry(false);
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
        if (this.getFamiliarOwner().getCapability(OccultismCapabilities.FAMILIAR_SETTINGS)
                .map(FamiliarSettingsCapability::isCthulhuEnabled).orElse(false)) {
            if (this.isAngry())
                return ImmutableList.of(new EffectInstance(Effects.WATER_BREATHING, 300, 0, false, false));
        }
        return Collections.emptyList();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAT, false);
        this.dataManager.register(TRUNK, false);
        this.dataManager.register(ANGRY, false);
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
    
    public boolean isAngry() {
        return this.dataManager.get(ANGRY);
    }

    private void setAngry(boolean b) {
        this.dataManager.set(ANGRY, b);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setHat(compound.getBoolean("hasHat"));
        this.setTrunk(compound.getBoolean("hasTrunk"));
        this.setAngry(compound.getBoolean("isAngry"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
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
                this.cthulhu.setMotion(this.cthulhu.getMotion().add(0, 0.005, 0));
                if (this.action == MovementController.Action.MOVE_TO) {
                    float maxSpeed = (float) (this.speed * this.cthulhu.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 3;
                    this.cthulhu.setAIMoveSpeed(MathHelper.lerp(0.125f, this.cthulhu.getAIMoveSpeed(), maxSpeed));
                    double dx = this.posX - this.cthulhu.getPosX();
                    double dy = this.posY - this.cthulhu.getPosY();
                    double dz = this.posZ - this.cthulhu.getPosZ();
                    double distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance < 0.1) {
                        this.cthulhu.setMoveForward(0);
                        return;
                    }

                    if (Math.abs(dy) > 0.0001) {
                        this.cthulhu.setMotion(
                                this.cthulhu.getMotion().add(0, this.cthulhu.getAIMoveSpeed() * (dy / distance) * 0.1, 0));
                    }

                    if (Math.abs(dx) > 0.0001 || Math.abs(dz) > 0.0001) {
                        float rotate = (float) (MathHelper.atan2(dz, dx) * (180 / Math.PI)) - 90f;
                        this.cthulhu.rotationYaw = this.limitAngle(this.cthulhu.rotationYaw, rotate, 8);
                        this.cthulhu.renderYawOffset = this.cthulhu.rotationYaw;
                    }

                } else {
                    this.cthulhu.setAIMoveSpeed(0);
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
            return !this.entity.world.hasWater(owner.getPosition()) && this.entity.isInWater();
        }

    }
}
