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

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.common.data.FamiliarEffects;
import com.klikli_dev.occultism.common.item.familiar.FamiliarCurio;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nullable;
import java.util.*;

public abstract class FamiliarEntity extends PathfinderMob implements IFamiliar {

    final List<FamiliarEffects.FamiliarEffectDefinition> effectDefinitionList = FamiliarEffects.effectMap().get(this.getType());
    private static final float MAX_BOOST_DISTANCE = 8;
    private static final Identifier DAMAGE_BONUS = Identifier.fromNamespaceAndPath(Occultism.MODID, "upgraded_damage_bonus");
    private static final Identifier DAMAGE_BONUS_IESNIUM = Identifier.fromNamespaceAndPath(Occultism.MODID, "iesnium_damage_bonus");
    private static final Identifier HEALTH_BONUS = Identifier.fromNamespaceAndPath(Occultism.MODID, "upgraded_health_bonus");
    private static final Identifier HEALTH_BONUS_IESNIUM = Identifier.fromNamespaceAndPath(Occultism.MODID, "iesnium_health_bonus");
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BLACKSMITH_UPGRADE = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IESNIUM_UPGRADE = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> VARIANTS = SynchedEntityData.defineId(FamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(FamiliarEntity.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    private boolean partying;
    private BlockPos jukeboxPos;

    public FamiliarEntity(EntityType<? extends FamiliarEntity> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 15.0)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.FOLLOW_RANGE, 30);
    }

    public Iterable<MobEffectInstance> getFamiliarEffects() {
        if (this.effectDefinitionList == null || this.effectDefinitionList.isEmpty()
                || this.getOwner() == null)
            return List.of();

        FamiliarSettingsData data = this.getOwner().getData(OccultismDataStorage.FAMILIAR_SETTINGS.get());
        List<MobEffectInstance> effects = new ArrayList<>(this.effectDefinitionList.size());
        for (var effect : this.effectDefinitionList) {
            int amp = effect.getValue(this);
            amp = Math.min(amp, data.getEffectAmplifier(this.getFamiliarEntity().getType(), effect.effect()));
            if (amp >= 0) {
                effects.add(new MobEffectInstance(effect.effect(), 300, amp, false, false));
            }
        }
        return effects;
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource pDamageSource, boolean pAttackedRecently) {
        super.dropFromLootTable(level, pDamageSource, pAttackedRecently);

        var owner = this.getFamiliarOwner();

        var shard = new ItemStack(OccultismItems.SOUL_SHARD_ITEM.get());

        var health = this.getHealth();
        this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
        this.resetFallDistance();
        this.removeAllEffects();
        this.resetCustomFamiliarData();

        var entityData = new CompoundTag();
        var id = this.getEncodeId();
        if (id != null)
            entityData.putString("id", id);
        var valueOutput = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.registryAccess());
        this.saveWithoutId(valueOutput);
        entityData.merge(valueOutput.buildResult());

        shard.set(DataComponents.ENTITY_DATA, TypedEntityData.of(this.getType(), entityData));

        this.setHealth(health);

        if (owner instanceof Player player) {
            ItemTransferUtil.giveItemToPlayer(player, shard);
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

    protected void resetCustomFamiliarData() {
    }

    public boolean isPartying() {
        return this.partying;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SITTING, false);
        builder.define(BLACKSMITH_UPGRADE, false);
        builder.define(IESNIUM_UPGRADE, false);
        builder.define(OWNER_UNIQUE_ID, Optional.empty());
        builder.define(VARIANTS, (byte) 0);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    public boolean canIesniumUpgrade() {
        return this.hasBlacksmithUpgrade() && !this.hasIesniumUpgrade();
    }

    public boolean hasBlacksmithUpgrade() {
        return this.entityData.get(BLACKSMITH_UPGRADE);
    }

    public boolean hasIesniumUpgrade() {
        return this.entityData.get(IESNIUM_UPGRADE);
    }

    private void setBlacksmithUpgrade(boolean b) {
        this.entityData.set(BLACKSMITH_UPGRADE, b);
    }

    private void setIesniumUpgrade(boolean b) {
        this.entityData.set(IESNIUM_UPGRADE, b);
    }

    @Override
    public void blacksmithUpgrade() {
        if (this.getOwner() instanceof Player player)
            player.sendSystemMessage(Component.translatable(String.format("message.%s.familiar.upgraded", Occultism.MODID), this.getName()));
        if (!this.hasBlacksmithUpgrade() && this.hasCustomName())
            this.setCustomName(Component.empty().append(this.getName()).append(" ⛤"));

        AttributeModifier damage = new AttributeModifier(DAMAGE_BONUS, 3, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceDmg = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (instanceDmg != null && !instanceDmg.hasModifier(DAMAGE_BONUS))
            instanceDmg.addPermanentModifier(damage);

        AttributeModifier health = new AttributeModifier(HEALTH_BONUS, 20, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceHp = this.getAttribute(Attributes.MAX_HEALTH);
        if (instanceHp != null && !instanceHp.hasModifier(HEALTH_BONUS))
            instanceHp.addPermanentModifier(health);

        this.setBlacksmithUpgrade(true);
    }

    @Override
    public void iesniumUpgrade() {
        if (this.getOwner() instanceof Player player)
            player.sendSystemMessage(Component.translatable(String.format("message.%s.familiar.iesnium_upgraded", Occultism.MODID), this.getName()));
        if (!this.hasIesniumUpgrade() && this.hasCustomName())
            this.setCustomName(Component.empty().append("⛤ ").append(this.getName()));

        AttributeModifier damage = new AttributeModifier(DAMAGE_BONUS_IESNIUM, 9, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceDmg = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (instanceDmg != null && !instanceDmg.hasModifier(DAMAGE_BONUS_IESNIUM))
            instanceDmg.addPermanentModifier(damage);

        AttributeModifier health = new AttributeModifier(HEALTH_BONUS_IESNIUM, 50, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance instanceHp = this.getAttribute(Attributes.MAX_HEALTH);
        if (instanceHp != null && !instanceHp.hasModifier(HEALTH_BONUS_IESNIUM))
            instanceHp.addPermanentModifier(health);

        this.setIesniumUpgrade(true);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();

        if (this.level().getGameTime() % 1024 == 0)
            this.heal(1F);

        if (this.jukeboxPos == null || !this.jukeboxPos.closerThan(this.blockPosition(), 3.5)
                || !this.level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX)) {
            this.partying = false;
            this.jukeboxPos = null;
        }

        LivingEntity owner;
        if (!this.level().isClientSide() && this.level().getGameTime() % 10 == 0 && (owner = this.getFamiliarOwner()) != null
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
        if (this.getOwner() == null)
            this.setFamiliarOwner(playerIn);

        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        ItemStack stack = playerIn.getItemInHand(hand);
        if (stack.getItem() instanceof FamiliarCurio) {
            return stack.interactLivingEntity(playerIn, this, hand);
        } else if (stack.getItem() == OccultismItems.DEBUG_WAND.get()) {
            if (playerIn.isShiftKeyDown() && !this.level().isClientSide()) {
                if (this.hasBlacksmithUpgrade()) {
                    if (this.hasIesniumUpgrade()) {
                        this.setBlacksmithUpgrade(false);
                        this.setIesniumUpgrade(false);
                    } else {
                        this.iesniumUpgrade();
                    }
                } else {
                    this.blacksmithUpgrade();
                }
            } else {
                this.setFamiliarOwner(playerIn);
            }
            return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        } else if (stack.isEmpty() && !this.level().isClientSide() && this.getFamiliarOwner() == playerIn) {
            this.setSitting(!this.isSitting());
            return this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwner();
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (hasRareVariant())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        this.setOwnerId(owner.getUUID());
    }

    public UUID getOwnerId() {
        return this.entityData.get(OWNER_UNIQUE_ID).map(EntityReference::getUUID).orElse(null);
    }

    private void setOwnerId(UUID id) {
        this.entityData.set(OWNER_UNIQUE_ID, id == null ? Optional.empty() : Optional.of(EntityReference.of(id)));
    }

    @Override
    public @NonNull Entity getFamiliarEntity() {
        return this;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        var ownerRef = EntityReference.read(input, "owner");
        if (ownerRef != null) this.setOwnerId(ownerRef.getUUID());
        this.setSitting(input.getBooleanOr("isSitting", false));
        this.setBlacksmithUpgrade(input.getBooleanOr("hasBlacksmithUpgrade", false));
        this.setIesniumUpgrade(input.getBooleanOr("hasIesniumUpgrade", false));
        this.entityData.set(VARIANTS, input.getByteOr("variants", (byte) 0));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        EntityReference.store(this.entityData.get(OWNER_UNIQUE_ID).orElse(null), output, "owner");

        output.putBoolean("isSitting", this.isSitting());
        output.putBoolean("hasBlacksmithUpgrade", this.hasBlacksmithUpgrade());
        output.putBoolean("hasIesniumUpgrade", this.hasIesniumUpgrade());
        output.putByte("variants", this.entityData.get(VARIANTS));
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, @NotNull DamageSource source) {
        return super.isInvulnerableTo(level, source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FLY_INTO_WALL);
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

    public boolean hasRareVariant() {
        return false;
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target == owner)
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

            return !(target instanceof TamableAnimal tamableanimal) || !tamableanimal.isTame();
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
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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
                this.entity.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
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

    protected class SitGoal extends Goal {
        private final FamiliarEntity entity;

        public SitGoal(FamiliarEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.entity.isInWater() && this.entity.getFamiliarOwner() != null
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
}

