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
import java.util.UUID;

import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class GreedyFamiliarEntity extends CreatureEntity implements IFamiliar {

    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(GreedyFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public LivingEntity ownerCached;

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SITTING, false);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FindItemGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
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
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactWithEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            this.setOwnerId(playerIn.getUniqueID());
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            if (!this.world.isRemote && this.getFamiliarOwner() == playerIn)
                this.setSitting(!this.isSitting());
            return ActionResultType.func_233537_a_(this.world.isRemote);
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
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (!(wearer instanceof PlayerEntity))
            return;

        for (ItemEntity e : this.world.getEntitiesWithinAABB(ItemEntity.class, wearer.getBoundingBox().grow(5))) {
            ItemStack stack = e.getItem().getStack();
            boolean isDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
            if(!isDemagnetized){
                e.onCollideWithPlayer((PlayerEntity) wearer);
            }
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.hasUniqueId("owner"))
            this.setOwnerId(compound.getUniqueId("owner"));
        if (compound.contains("isSitting"))
            this.setSitting(compound.getBoolean("isSitting"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.getOwnerId() != null) {
            compound.putUniqueId("owner", this.getOwnerId());
        }

        compound.putBoolean("isSitting", this.isSitting());
    }

    private void setSitting(boolean b) {
        this.dataManager.set(SITTING, b);
    }

    public boolean isSitting() {
        return this.dataManager.get(SITTING);
    }

    public class FollowOwnerGoal extends Goal {
        private GreedyFamiliarEntity entity;
        private double speed;
        private int cooldown;
        private float maxDist;
        private float minDist;
        private LivingEntity owner;

        public FollowOwnerGoal(GreedyFamiliarEntity entity, double speed, float minDist, float maxDist) {
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
            this.entity.getLookController().setLookPositionWithEntity(this.owner, 10, this.entity.getVerticalFaceSpeed());
            if (--this.cooldown < 0) {
                this.cooldown = 10;
                if (!this.entity.getLeashed() && !this.entity.isPassenger()) {
                    this.entity.getNavigator().tryMoveToEntityLiving(this.owner, this.speed);
                }
            }
        }
    }

    private class SitGoal extends Goal {
        private final GreedyFamiliarEntity entity;

        public SitGoal(GreedyFamiliarEntity entity) {
            this.entity = entity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            return !this.entity.isInWaterOrBubbleColumn() && this.entity.isOnGround() && this.entity.getFamiliarOwner() != null
                    && this.entity.isSitting();
        }

        public void startExecuting() {
            this.entity.getNavigator().clearPath();
        }

        public void resetTask() {
            this.entity.setSitting(false);
        }
    }

    private static class FindItemGoal extends Goal {

        private static final double RANGE = 12;

        private GreedyFamiliarEntity entity;

        public FindItemGoal(GreedyFamiliarEntity raptor) {
            this.entity = raptor;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return this.getNearbyItem() != null && this.entity.getFamiliarOwner() instanceof PlayerEntity;
        }

        @Override
        public void startExecuting() {
            ItemEntity item = this.getNearbyItem();
            if (item != null)
                this.entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
        }

        @Override
        public void tick() {
            ItemEntity item = this.getNearbyItem();
            if (item != null) {
                this.entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
                LivingEntity owner = this.entity.getFamiliarOwner();
                if (item.getDistanceSq(this.entity) < 4 && owner instanceof PlayerEntity)
                    item.onCollideWithPlayer(((PlayerEntity) owner));
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (!(owner instanceof PlayerEntity))
                return null;

            PlayerEntity player = (PlayerEntity) owner;
            IItemHandler inv = new PlayerMainInvWrapper(player.inventory);

            for (ItemEntity item : this.entity.world.getEntitiesWithinAABB(ItemEntity.class,
                    this.entity.getBoundingBox().grow(RANGE))) {
                ItemStack stack = item.getItem();
                boolean isDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                if (!isDemagnetized && ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }
}
