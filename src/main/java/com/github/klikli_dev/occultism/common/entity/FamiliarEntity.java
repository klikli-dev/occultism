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

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.github.klikli_dev.occultism.registry.OccultismItems;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.datasync.EntityDataAccessor;
import net.minecraft.network.datasync.EntityDataSerializers;
import net.minecraft.network.datasync.SynchedEntityData;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.level.Level;

public abstract class FamiliarEntity extends CreatureEntity implements IFamiliar {

    private static final float MAX_BOOST_DISTANCE = 8;

    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.createKey(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData
            .createKey(FamiliarEntity.class, EntityDataSerializers.OPTIONAL_UNIQUE_ID);

    private LivingEntity ownerCached;

    public FamiliarEntity(EntityType<? extends FamiliarEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.dataManager.register(SITTING, false);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void aiStep() {
        LivingEntity owner;
        if (!this.level.isClientSide && this.level.getGameTime() % 10 == 0 && (owner = this.getFamiliarOwner()) != null
                && this.getDistance(owner) < MAX_BOOST_DISTANCE)
            for (EffectInstance effect : this.getFamiliarEffects())
                owner.addPotionEffect(effect);

        super.aiStep();
    }

    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.level.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        this.setOwnerId(owner.getUniqueID());
    }

    public LivingEntity getOwnerCached() {
        if (this.ownerCached != null)
            return this.ownerCached;
        this.ownerCached = this.getOwner();
        return this.ownerCached;
    }

    @Override
    protected InteractionResult getEntityInteractionResult(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactWithEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            this.setOwnerId(playerIn.getUniqueID());
            return InteractionResult.func_233537_a_(this.level.isClientSide);
        } else {
            if (!this.level.isClientSide && this.getFamiliarOwner() == playerIn)
                this.setSitting(!this.isSitting());
            return InteractionResult.func_233537_a_(this.level.isClientSide);
        }
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwnerCached();
    }

    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse(null);
    }

    public void setOwnerId(UUID id) {
        this.ownerCached = null;
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(id));
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (compound.hasUniqueId("owner"))
            this.setOwnerId(compound.getUniqueId("owner"));
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
        this.dataManager.set(SITTING, b);
    }

    public boolean isSitting() {
        return this.dataManager.get(SITTING);
    }

    protected class FollowOwnerGoal extends Goal {

        private static final int TELEPORT_ATTEMPTS = 10;

        private FamiliarEntity entity;
        private double speed;
        private int cooldown;
        private float maxDist;
        private float minDist;
        private LivingEntity owner;

        public FollowOwnerGoal(FamiliarEntity entity, double speed, float minDist, float maxDist) {
            this.entity = entity;
            this.speed = speed;
            this.minDist = minDist * minDist;
            this.maxDist = maxDist * maxDist;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        private boolean shouldFollow(double distance) {
            return this.owner != null && !this.owner.isSpectator() && this.entity.getDistanceSq(this.owner) > distance;
        }

        public boolean shouldExecute() {
            this.owner = this.entity.getFamiliarOwner();
            return this.shouldFollow(this.minDist);
        }

        public boolean shouldContinueExecuting() {
            return this.shouldFollow(this.maxDist);
        }

        public void startExecuting() {
            this.cooldown = 0;
        }

        public void resetTask() {
            this.owner = null;
            this.entity.getNavigator().clearPath();
        }

        public void tick() {
            this.entity.getLookController().setLookPositionWithEntity(this.owner, 10,
                    this.entity.getVerticalFaceSpeed());
            if (--this.cooldown < 0) {
                this.cooldown = 10;
                if (!this.entity.getLeashed() && !this.entity.isPassenger()) {
                    if (this.entity.getDistanceSq(this.owner) >= 150)
                        this.tryTeleport();
                    else
                        this.entity.getNavigator().tryMoveToEntityLiving(this.owner, this.speed);
                }
            }
        }

        private void tryTeleport() {
            for (int i = 0; i < TELEPORT_ATTEMPTS; i++)
                if (this.tryTeleport(this.randomNearby(this.owner.getPosition())))
                    return;
        }

        private boolean tryTeleport(BlockPos pos) {
            boolean walkable = PathNodeType.WALKABLE == WalkNodeProcessor.getFloorNodeType(this.entity.level,
                    pos.toMutable());
            boolean noCollision = this.entity.level.hasNoCollisions(this.entity,
                    this.entity.getBoundingBox().offset(pos.subtract(this.entity.getPosition())));
            if (walkable && noCollision) {
                this.entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        this.entity.rotationYaw, this.entity.rotationPitch);
                this.entity.navigator.clearPath();
                return true;
            }

            return false;
        }

        private BlockPos randomNearby(BlockPos pos) {
            Random rand = this.entity.getRNG();
            return pos.add(MathHelper.nextDouble(rand, -3, 3), MathHelper.nextDouble(rand, -1, 1),
                    MathHelper.nextDouble(rand, -3, 3));
        }
    }

    protected class SitGoal extends Goal {
        private final FamiliarEntity entity;

        public SitGoal(FamiliarEntity entity) {
            this.entity = entity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            return !this.entity.isInWaterOrBubbleColumn() && this.entity.getFamiliarOwner() != null
                    && this.entity.isSitting();
        }

        public void startExecuting() {
            this.entity.getNavigator().clearPath();
        }

        public void resetTask() {
            this.entity.setSitting(false);
        }
    }
}
