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

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.Type;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class CthulhuFamiliarEntity extends FamiliarEntity {

    protected static final int PRISMARINE_INTERVAL = 20 * 3;
    private final WaterBoundPathNavigation waterNavigator;
    private final GroundPathNavigation groundNavigator;
    public float riderRot, riderRot0, riderLimbSwingAmount, riderLimbSwing;
    protected long lastPrismarineTime;
    private BlockPos lightPos, lightPos0;
    private int lightTimer;

    public CthulhuFamiliarEntity(EntityType<? extends CthulhuFamiliarEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.WATER, 0);
        this.waterNavigator = new WaterBoundPathNavigation(this, level);
        this.groundNavigator = new GroundPathNavigation(this, level);
        this.moveControl = new MoveController(this);
    }

    public static Builder createAttributes() {
        return createMobAttributes().add(NeoForgeMod.SWIM_SPEED, 1f);
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
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.getOwner() == pPlayer) {

            if (itemstack.is(Tags.Items.GEMS_LAPIS)) {
                if (this.level().getGameTime() > this.lastPrismarineTime + PRISMARINE_INTERVAL) {
                    if (this.hasBlacksmithUpgrade()) {
                        this.lastPrismarineTime = this.level().getGameTime();
                        itemstack.shrink(1);
                        ItemTransferUtil.giveItemToPlayer(pPlayer, new ItemStack(Items.PRISMARINE_SHARD, RandomSource.create().nextInt(1, 5)));
                    } else {
                        this.lastPrismarineTime = this.level().getGameTime();
                        itemstack.shrink(1);
                        ItemTransferUtil.giveItemToPlayer(pPlayer, new ItemStack(Items.PRISMARINE_SHARD));
                    }
                } else {
                    pPlayer.sendSystemMessage(Component.translatable("dialog.occultism.cthulhu.prismarine_on_cooldown"));
                }
                //even if we don't give a breath we return success, otherwise we make the familiar change sitting position
                return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
            }

        }
        return super.mobInteract(pPlayer, pHand);
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

        if (!this.level().isClientSide()) {
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, EntitySpawnReason pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setHat(this.getRandom().nextDouble() < 0.1);
        this.setTrunk(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
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
        if (this.level().isEmptyBlock(this.lightPos) && this.isAlive() && this.hasBlacksmithUpgrade())
            this.level().setBlockAndUpdate(this.lightPos, OccultismBlocks.LIGHTED_AIR.get().defaultBlockState());
    }

    private void removeLight(BlockPos pos) {
        if (!this.level().isClientSide() && pos != null
                && this.level().getBlockState(pos).getBlock() == OccultismBlocks.LIGHTED_AIR.get())
            this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
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
        if (!this.level().isClientSide()) {
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
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            Vec3 direction = this.riderLocation();
            pCallback.accept(pPassenger, direction.x, direction.y, direction.z);
        }
    }


    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return this.riderLocation();
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    public void actuallyHurt(ServerLevel serverLevel, DamageSource source, float amount) {
        super.actuallyHurt(serverLevel, source, amount);
        if (source.getEntity() == this.getFamiliarOwner()) {
            this.setAngry(true);
            this.setSitting(true);
            OccultismAdvancements.FAMILIAR.get().trigger(this.getFamiliarOwner(), Type.CTHULHU_SAD);
        } else if (source.getEntity() != null) {
            Vec3 tp = DefaultRandomPos.getPos(this, 8, 4);
            if (tp != null) {
                this.snapTo(tp.x() + 0.5, tp.y(), tp.z() + 0.5, this.yRotO,
                        this.xRotO);
            }
            this.navigation.stop();
        }
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
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
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
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        int lpX = input.getIntOr("lightPosX", Integer.MIN_VALUE);
        int lpY = input.getIntOr("lightPosY", Integer.MIN_VALUE);
        int lpZ = input.getIntOr("lightPosZ", Integer.MIN_VALUE);
        if (lpX != Integer.MIN_VALUE) this.lightPos = new BlockPos(lpX, lpY, lpZ);
        int lp0X = input.getIntOr("lightPos0X", Integer.MIN_VALUE);
        int lp0Y = input.getIntOr("lightPos0Y", Integer.MIN_VALUE);
        int lp0Z = input.getIntOr("lightPos0Z", Integer.MIN_VALUE);
        if (lp0X != Integer.MIN_VALUE) this.lightPos0 = new BlockPos(lp0X, lp0Y, lp0Z);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (this.lightPos != null) {
            output.putInt("lightPosX", this.lightPos.getX());
            output.putInt("lightPosY", this.lightPos.getY());
            output.putInt("lightPosZ", this.lightPos.getZ());
        }
        if (this.lightPos0 != null) {
            output.putInt("lightPos0X", this.lightPos0.getX());
            output.putInt("lightPos0Y", this.lightPos0.getY());
            output.putInt("lightPos0Z", this.lightPos0.getZ());
        }
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
            return !this.entity.level().isWaterAt(owner.blockPosition()) && this.entity.isInWater();
        }

    }

    private static class GiveFlowerGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 60 * 5;

        private final CthulhuFamiliarEntity cthulhu;
        private DevilFamiliarEntity devil;
        private int cooldown = MAX_COOLDOWN;

        public GiveFlowerGoal(CthulhuFamiliarEntity cthulhu) {
            this.cthulhu = cthulhu;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
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
                ((ServerLevel) this.cthulhu.level()).sendParticles(ParticleTypes.HEART, this.devil.getBlockX(), this.devil.getBlockY() + 1,
                        this.devil.getBlockZ(), 1, 0, 0, 0, 1);
                this.devil = null;
            }
        }

        @Override
        public void start() {
            this.cthulhu.getNavigation().moveTo(this.devil, 0.3);
            this.cthulhu.setGiving(true);
        }

        @Override
        public void stop() {
            this.cthulhu.setGiving(false);
            this.cthulhu.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN;
            this.devil = null;
        }

        private DevilFamiliarEntity findDevil() {
            List<DevilFamiliarEntity> devils = this.cthulhu.level().getEntitiesOfClass(DevilFamiliarEntity.class,
                    this.cthulhu.getBoundingBox().inflate(4));
            return devils.isEmpty() ? null : devils.get(0);
        }

    }
}

