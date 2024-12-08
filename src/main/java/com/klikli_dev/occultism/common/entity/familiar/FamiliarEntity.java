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

import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public abstract class FamiliarEntity extends PathfinderMob implements IFamiliar {

    private static final float MAX_BOOST_DISTANCE = 8;

    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BLACKSMITH_UPGRADE = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> VARIANTS = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(FamiliarEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private boolean partying;
    private BlockPos jukeboxPos;

    public FamiliarEntity(EntityType<? extends FamiliarEntity> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 15.0)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    protected void dropFromLootTable(DamageSource pDamageSource, boolean pAttackedRecently) {
        if (this.getFamiliarEntity() instanceof GuardianFamiliarEntity)
            return;

        super.dropFromLootTable(pDamageSource, pAttackedRecently);

        var owner = this.getFamiliarOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        this.resetFallDistance();
        this.removeAllEffects();

        var entityData = new CompoundTag();
        var id = this.getEncodeId();
        if(id != null)
            entityData.putString("id", id);
        entityData = this.saveWithoutId(entityData);

        shard.set(DataComponents.ENTITY_DATA, CustomData.of(entityData));

        this.setHealth(health);

        if (owner instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, shard);
        } else {
            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), shard);
            entityitem.setPickUpDelay(5);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));

            this.level().addFreshEntity(entityitem);
        }
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SITTING, false);
        builder.define(BLACKSMITH_UPGRADE, false);
        builder.define(OWNER_UNIQUE_ID, Optional.empty());
        builder.define(VARIANTS, (byte) 0);
    }

    public boolean hasBlacksmithUpgrade() {
        return this.entityData.get(BLACKSMITH_UPGRADE);
    }

    private void setBlacksmithUpgrade(boolean b) {
        this.entityData.set(BLACKSMITH_UPGRADE, b);
    }

    @Override
    public void blacksmithUpgrade() {
        this.setBlacksmithUpgrade(true);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();

        if (this.jukeboxPos == null || !this.jukeboxPos.closerThan(this.blockPosition(), 3.5)
                || !this.level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX)) {
            this.partying = false;
            this.jukeboxPos = null;
        }

        LivingEntity owner;
        if (!this.level().isClientSide && this.level().getGameTime() % 10 == 0 && (owner = this.getFamiliarOwner()) != null
                && this.distanceTo(owner) < MAX_BOOST_DISTANCE)
            for (MobEffectInstance effect : this.getFamiliarEffects())
                owner.addEffect(effect);

        super.aiStep();
    }

    public LivingEntity getOwner() {
        UUID uuid = this.getOwnerId();
        return uuid == null ? null : this.level().getPlayerByUUID(uuid);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        ItemStack stack = playerIn.getItemInHand(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactLivingEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            this.setOwnerId(playerIn.getUUID());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (stack.isEmpty() && !this.level().isClientSide && this.getFamiliarOwner() == playerIn) {
            this.setSitting(!this.isSitting());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwner();
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        this.setOwnerId(owner.getUUID());
    }

    public UUID getOwnerId() {
        return this.entityData.get(OWNER_UNIQUE_ID).orElse(null);
    }

    private void setOwnerId(UUID id) {
        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(id));
    }

    @Override
    public Entity getFamiliarEntity() {
        return this;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("owner"))
            this.setOwnerId(compound.getUUID("owner"));
        if (compound.contains("isSitting"))
            this.setSitting(compound.getBoolean("isSitting"));
        this.setBlacksmithUpgrade(compound.getBoolean("hasBlacksmithUpgrade"));
        this.entityData.set(VARIANTS, compound.getByte("variants"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerId() != null) {
            compound.putUUID("owner", this.getOwnerId());
        }

        compound.putBoolean("isSitting", this.isSitting());
        compound.putBoolean("hasBlacksmithUpgrade", this.hasBlacksmithUpgrade());
        compound.putByte("variants", this.entityData.get(VARIANTS));
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FLY_INTO_WALL);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    protected void setSitting(boolean b) {
        this.entityData.set(SITTING, b);
    }

    protected void setVariant(int offset, boolean b) {
        if (b)
            this.entityData.set(VARIANTS, (byte) (this.entityData.get(VARIANTS) | (1 << offset)));
        else
            this.entityData.set(VARIANTS, (byte) (this.entityData.get(VARIANTS) & ~(1 << offset)));
    }

    protected boolean hasVariant(int offset) {
        return ((this.entityData.get(VARIANTS) >> offset) & 1) == 1;
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if(target == owner)
            return false;
        else if (target instanceof Creeper || target instanceof Ghast || target instanceof ArmorStand) {
            return false;
        } else if (target instanceof Wolf wolf) {
            return !wolf.isTame() || wolf.getOwner() != owner;
        } else {
            if (target instanceof Player player && owner instanceof Player player1 && !player1.canHarmPlayer(player)) {
                return false;
            }

            if (target instanceof AbstractHorse abstracthorse && abstracthorse.isTamed()) {
                return false;
            }

            if (target instanceof TamableAnimal tamableanimal && tamableanimal.isTame()) {
                return false;
            }

            return true;
        }
    }

    protected static class FollowOwnerGoal extends Goal {

        private static final int TELEPORT_ATTEMPTS = 10;
        private final double speed;
        private final float maxDist;
        private final float minDist;
        protected FamiliarEntity entity;
        private int cooldown;
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
            boolean walkable = PathType.WALKABLE == WalkNodeEvaluator.getPathTypeStatic(this.entity, pos.mutable());
            boolean noCollision = this.entity.level().noCollision(this.entity,
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
            var rand = this.entity.getRandom();
            return pos.offset(Mth.nextInt(rand, -3, 3), Mth.nextInt(rand, -1, 1),
                    Mth.nextInt(rand, -3, 3));
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

        //Causing lifting when changing dimensions
        //@Override
        //public void stop() {
        //    this.entity.setSitting(false);
        //}
    }
    public static class FamiliarPanicGoal extends PanicGoal {
        protected final FamiliarEntity entity;
        public FamiliarPanicGoal(FamiliarEntity mob, double speedModifier) {
            super(mob, speedModifier);
            this.entity = mob;
        }
        @Override
        public void start() {
            this.entity.setSitting(false);
            this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
            this.isRunning = true;
        }
    }
}
