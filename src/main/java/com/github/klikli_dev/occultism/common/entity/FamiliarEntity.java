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

import com.github.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public abstract class FamiliarEntity extends PathfinderMob implements IFamiliar {

    private static final float MAX_BOOST_DISTANCE = 8;

    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(FamiliarEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private LivingEntity ownerCached;
    private boolean partying;
    private BlockPos jukeboxPos;

    public FamiliarEntity(EntityType<? extends FamiliarEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    public void setRecordPlayingNearby(BlockPos jukeboxPos, boolean partying) {
        this.jukeboxPos = jukeboxPos;
        this.partying = partying;
    }

    public boolean isPartying() {
        return this.partying;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();

        if (this.jukeboxPos == null || !this.jukeboxPos.closerThan(this.position(), 3.5)
                || !this.level.getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX)) {
            this.partying = false;
            this.jukeboxPos = null;
        }

        LivingEntity owner;
        if (!this.level.isClientSide && this.level.getGameTime() % 10 == 0 && (owner = this.getFamiliarOwner()) != null
                && this.distanceTo(owner) < MAX_BOOST_DISTANCE)
            for (MobEffectInstance effect : this.getFamiliarEffects())
                owner.addEffect(effect);

        super.aiStep();
    }

    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        this.setOwnerId(owner.getUUID());
    }

    public LivingEntity getOwnerCached() {
        if (this.ownerCached != null)
            return this.ownerCached;
        this.ownerCached = this.getOwner();
        return this.ownerCached;
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactLivingEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            this.setOwnerId(playerIn.getUUID());
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            if (!this.level.isClientSide && this.getFamiliarOwner() == playerIn)
                this.setSitting(!this.isSitting());
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwnerCached();
    }

    public UUID getOwnerId() {
        return this.entityData.get(OWNER_UNIQUE_ID).orElse(null);
    }

    private void setOwnerId(UUID id) {
        this.ownerCached = null;
        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(id));
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("owner"))
            this.setOwnerId(compound.getUUID("owner"));
        if (compound.contains("isSitting"))
            this.setSitting(compound.getBoolean("isSitting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerId() != null) {
            compound.putUUID("owner", this.getOwnerId());
        }

        compound.putBoolean("isSitting", this.isSitting());
    }

    protected void setSitting(boolean b) {
        this.entityData.set(SITTING, b);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    protected static class FollowOwnerGoal extends Goal {

        private static final int TELEPORT_ATTEMPTS = 10;

        protected FamiliarEntity entity;
        private final double speed;
        private int cooldown;
        private final float maxDist;
        private final float minDist;
        private LivingEntity owner;

        public FollowOwnerGoal(FamiliarEntity entity, double speed, float minDist, float maxDist) {
            this.entity = entity;
            this.speed = speed;
            this.minDist = minDist * minDist;
            this.maxDist = maxDist * maxDist;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        private boolean shouldFollow(double distance) {
            return this.owner != null && !this.owner.isSpectator() && this.entity.distanceToSqr(this.owner) > distance;
        }

        @Override
        public boolean canUse() {
            this.owner = this.entity.getFamiliarOwner();
            return this.shouldFollow(this.minDist);
        }

        @Override
        public boolean canContinueToUse() {
            return this.shouldFollow(this.maxDist);
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
            this.owner = null;
            this.entity.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.entity.getLookControl().setLookAt(this.owner, 10,
                    this.entity.getMaxHeadXRot());
            if (--this.cooldown < 0) {
                this.cooldown = 10;
                if (!this.entity.isLeashed() && !this.entity.isPassenger()) {
                    if (this.entity.distanceToSqr(this.owner) >= 150 || this.shouldTeleport(this.owner))
                        this.tryTeleport();
                    else
                        this.entity.getNavigation().moveTo(this.owner, this.speed);
                }
            }
        }

        protected boolean shouldTeleport(LivingEntity owner) {
            return false;
        }

        private void tryTeleport() {
            for (int i = 0; i < TELEPORT_ATTEMPTS; i++)
                if (this.tryTeleport(this.randomNearby(this.owner.blockPosition())))
                    return;
        }

        private boolean tryTeleport(BlockPos pos) {
            boolean walkable = BlockPathTypes.WALKABLE == WalkNodeEvaluator.getBlockPathTypeStatic(this.entity.level,
                    pos.mutable());
            boolean noCollision = this.entity.level.noCollision(this.entity,
                    this.entity.getBoundingBox().move(pos.subtract(this.entity.blockPosition())));
            if (walkable && noCollision) {
                this.entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        this.entity.yRotO, this.entity.xRotO);
                this.entity.navigation.stop();
                return true;
            }

            return false;
        }

        private BlockPos randomNearby(BlockPos pos) {
            Random rand = this.entity.getRandom();
            return pos.offset(Mth.nextDouble(rand, -3, 3), Mth.nextDouble(rand, -1, 1),
                    Mth.nextDouble(rand, -3, 3));
        }
    }

    protected class SitGoal extends Goal {
        private final FamiliarEntity entity;

        public SitGoal(FamiliarEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.entity.isInWaterOrBubble() && this.entity.getFamiliarOwner() != null
                    && this.entity.isSitting();
        }

        @Override
        public void start() {
            this.entity.getNavigation().stop();
        }

        @Override
        public void stop() {
            this.entity.setSitting(false);
        }
    }
}
