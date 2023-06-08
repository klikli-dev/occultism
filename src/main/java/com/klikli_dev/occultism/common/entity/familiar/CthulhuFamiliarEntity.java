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

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class CthulhuFamiliarEntity extends FamiliarEntity {

    private final WaterBoundPathNavigation waterNavigator;
    private final GroundPathNavigation groundNavigator;
    public float riderRot, riderRot0, riderLimbSwingAmount, riderLimbSwing;
    private BlockPos lightPos, lightPos0;
    private int lightTimer;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0);
        this.waterNavigator = new WaterBoundPathNavigation(this, level);
        this.groundNavigator = new GroundPathNavigation(this, level);
        this.moveControl = new MoveController(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(ForgeMod.SWIM_SPEED.get(), 1f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerWaterGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new GiveFlowerGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAngry() && this.getRandom().nextDouble() < 0.0007)
            this.setAngry(false);

        this.riderRot0 = this.riderRot;
        this.riderRot = Mth.approachDegrees(this.riderRot, this.yRotO, 10);

        if (!this.level.isClientSide) {
            this.lightTimer--;
            if (this.lightTimer < 0) {
                this.lightTimer = 10;
                if (this.lightPos == null)
                    this.lightPos = this.blockPosition();
                this.updateLight();
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setHat(this.getRandom().nextDouble() < 0.1);
        this.setTrunk(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void removeAfterChangingDimensions() {
        this.removeLight(this.lightPos);
        this.removeLight(this.lightPos0);
        this.lightPos = null;
        this.lightPos0 = null;
        super.removeAfterChangingDimensions();
    }

    private Vec3 riderLocation() {
        return Vec3.directionFromRotation(0, this.riderRot).yRot(230).scale(0.68).add(this.position());
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    public float getAnimationHeight(float partialTicks) {
        return Mth.cos((this.tickCount + partialTicks) / 5);
    }

    public float riderRot(float partialTicks) {
        return Mth.lerp(partialTicks, this.riderRot0, this.riderRot);
    }

    private void updateLight() {
        this.removeLight(this.lightPos0);
        this.lightPos0 = null;
        if (this.lightPos != this.blockPosition()) {
            this.lightPos0 = this.lightPos;
            this.lightPos = this.blockPosition();
        }
        if (this.level.isEmptyBlock(this.lightPos) && this.isAlive() && this.hasBlacksmithUpgrade())
            this.level.setBlockAndUpdate(this.lightPos, OccultismBlocks.LIGHTED_AIR.get().defaultBlockState());
    }

    private void removeLight(BlockPos pos) {
        if (!this.level.isClientSide && pos != null
                && this.level.getBlockState(pos).getBlock() == OccultismBlocks.LIGHTED_AIR.get())
            this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    @Override
    public void remove(RemovalReason reason) {
        this.removeLight(this.lightPos);
        this.removeLight(this.lightPos0);
        this.lightPos = null;
        this.lightPos0 = null;
        super.remove(reason);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
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

    @Override
    public void positionRider(Entity pPassenger) {
        if (this.hasPassenger(pPassenger)) {
            Vec3 direction = this.riderLocation();
            pPassenger.setPos(direction.x, direction.y, direction.z);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return this.riderLocation();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (source.getEntity() == this.getFamiliarOwner()) {
                this.setAngry(true);
                this.setSitting(true);
                OccultismAdvancements.FAMILIAR.trigger(this.getFamiliarOwner(), FamiliarTrigger.Type.CTHULHU_SAD);
            } else if (source.getEntity() != null) {
                Vec3 tp = DefaultRandomPos.getPos(this, 8, 4);
                if (tp != null) {
                    this.absMoveTo(tp.x() + 0.5, tp.y(), tp.z() + 0.5, this.yRotO,
                            this.xRotO);
                }
                this.navigation.stop();
            }
            return true;
        }
        return false;
    }

    @Override
    public void die(DamageSource pCause) {
        this.removeLight(this.lightPos);
        this.removeLight(this.lightPos0);
        this.lightPos = null;
        this.lightPos0 = null;
        super.die(pCause);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0, false, false));
    }

    public boolean hasHat() {
        return this.hasVariant(0);
    }

    private void setHat(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasTrunk() {
        return this.hasVariant(1);
    }

    private void setTrunk(boolean b) {
        this.setVariant(1, b);
    }

    public boolean isAngry() {
        return this.hasVariant(2);
    }

    private void setAngry(boolean b) {
        this.setVariant(2, b);
    }

    public boolean isGiving() {
        return this.hasVariant(3);
    }

    private void setGiving(boolean b) {
        this.setVariant(3, b);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants")) {
            this.setHat(compound.getBoolean("hasHat"));
            this.setTrunk(compound.getBoolean("hasTrunk"));
            this.setAngry(compound.getBoolean("isAngry"));
        }
        if (compound.contains("lightPos"))
            this.lightPos = NbtUtils.readBlockPos(compound.getCompound("lightPos"));
        if (compound.contains("lightPos0"))
            this.lightPos0 = NbtUtils.readBlockPos(compound.getCompound("lightPos0"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.lightPos != null)
            compound.put("lightPos", NbtUtils.writeBlockPos(this.lightPos));
        if (this.lightPos0 != null)
            compound.put("lightPos0", NbtUtils.writeBlockPos(this.lightPos0));
    }

    public static class MoveController extends MoveControl {
        private final FamiliarEntity familiar;

        MoveController(FamiliarEntity familiar) {
            super(familiar);
            this.familiar = familiar;
        }

        @Override
        public void tick() {
            if (this.familiar.isInWater()) {
                this.familiar.setDeltaMovement(this.familiar.getDeltaMovement().add(0, 0.005, 0));
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    float maxSpeed = (float) (this.speedModifier * this.familiar.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 3;
                    this.familiar.setSpeed(Mth.lerp(0.125f, this.familiar.getSpeed(), maxSpeed));
                    double dx = this.wantedX - this.familiar.getX();
                    double dy = this.wantedY - this.familiar.getY();
                    double dz = this.wantedZ - this.familiar.getZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance < 0.1) {
                        this.familiar.setZza(0);
                        return;
                    }

                    if (Math.abs(dy) > 0.0001) {
                        this.familiar.setDeltaMovement(
                                this.familiar.getDeltaMovement().add(0, this.familiar.getSpeed() * (dy / distance) * 0.1, 0));
                    }

                    if (Math.abs(dx) > 0.0001 || Math.abs(dz) > 0.0001) {
                        float rotate = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90f;
                        this.familiar.yRotO = this.rotlerp(this.familiar.yRotO, rotate, 8);
                        this.familiar.yBodyRot = this.familiar.yRotO;
                    }

                } else {
                    this.familiar.setSpeed(0);
                }
            } else {
                super.tick();
            }
        }
    }

    public static class FollowOwnerWaterGoal extends FollowOwnerGoal {

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
            return this.devil != null && this.cooldown-- < 0 && this.cthulhu.distanceToSqr(this.devil) > 3 && !this.cthulhu.isVehicle();
        }

        @Override
        public boolean canContinueToUse() {
            return this.devil != null && this.cthulhu.isPathFinding() && !this.cthulhu.isVehicle();
        }

        @Override
        public void tick() {
            if (this.cthulhu.distanceToSqr(this.devil) < 2) {
                ((ServerLevel) this.cthulhu.level).sendParticles(ParticleTypes.HEART, this.devil.getBlockX(), this.devil.getBlockY() + 1,
                        this.devil.getBlockZ(), 1, 0, 0, 0, 1);
                this.devil = null;
            }
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

        private DevilFamiliarEntity findDevil() {
            List<DevilFamiliarEntity> devils = this.cthulhu.level.getEntitiesOfClass(DevilFamiliarEntity.class,
                    this.cthulhu.getBoundingBox().inflate(4));
            return devils.isEmpty() ? null : devils.get(0);
        }

    }
}
