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
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.ai.goal.OwnerHurtByTargetGoal;
import com.klikli_dev.occultism.common.entity.ai.goal.OwnerHurtTargetGoal;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ChimeraFamiliarEntity extends ResizableFamiliarEntity implements ItemSteerable, PlayerRideableJumping {

    public static final byte NO_ATTACKER = 0;
    public static final byte LION_ATTACKER = 1;
    public static final byte GOAT_ATTACKER = 2;
    public static final byte SNAKE_ATTACKER = 3;
    private static final ResourceLocation DAMAGE_BONUS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "chimera_damage_bonus");
    private static final ResourceLocation SPEED_BONUS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "chimera_speed_bonus");
    private static final byte RIDING_SIZE = 80;
    private static final double SHRINK_CHANCE = 0.005;
    private static final int ATTACK_TIME = 10;

    private static final EntityDataAccessor<Byte> ATTACKER = SynchedEntityData.defineId(ChimeraFamiliarEntity.class,
            EntityDataSerializers.BYTE);

    private final ItemBasedSteering boost = new DummyBoostHelper();
    private int goatNoseTimer;
    private int attackTimer;
    private float playerJumpPendingScale;
    private boolean isJumping;

    public ChimeraFamiliarEntity(EntityType<? extends ChimeraFamiliarEntity> type, Level level) {
        super(type, level);
        this.goatNoseTimer = this.getRandom().nextInt(100);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.JUMP_STRENGTH, 0.7)
                ;
    }

    private boolean isRiderJumping(Player rider) {
        return this.jumping;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));

        this.goalSelector.addGoal(4, new ChimeraMeleeAttackGoal(this, 1.0f, true));

        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
//        this.goalSelector.addGoal(6, new ChimeraRangedAttackGoal(this, 10)); //this one is buggy

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));

        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
//        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.getRandom().nextDouble() < SHRINK_CHANCE)
            this.setSize((byte) (this.getSize() - 1));

        this.attackTimer--;
        if (this.attackTimer == 0)
            this.setAttacker(NO_ATTACKER);

        if (this.level().isClientSide) {
            this.goatNoseTimer++;

            if (this.attackTimer > 0 && this.getAttacker() == LION_ATTACKER) {
                Vec3 direction = Vec3.directionFromRotation(this.getRotationVector()).scale(this.getScale());
                for (int i = 0; i < 5; i++) {
                    Vec3 pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            1 + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                    this.level().addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.1, 0,
                            direction.z * 0.1);
                }
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setGoat(true);
        this.setSize((byte) 0);
        this.setFlaps(this.getRandom().nextBoolean());
        this.setRing(this.getRandom().nextBoolean());
        this.setBeard(this.getRandom().nextBoolean());
        this.setHat(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

//    @Override
//    public boolean isControlledByLocalInstance() {
//        return true;
//    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void setSize(byte size) {
        super.setSize(size);
        this.calcSizeModifiers();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKER, NO_ATTACKER);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants")) {
            this.setFlaps(compound.getBoolean("hasFlaps"));
            this.setRing(compound.getBoolean("hasRing"));
            if (compound.contains("hasBeard"))
                this.setBeard(compound.getBoolean("hasBeard"));
            this.setHat(compound.getBoolean("hasHat"));
            this.setGoat(compound.getBoolean("hasGoat"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasFlaps", this.hasFlaps());
        compound.putBoolean("hasRing", this.hasRing());
        compound.putBoolean("hasBeard", this.hasBeard());
        compound.putBoolean("hasHat", this.hasHat());
        compound.putBoolean("hasGoat", this.hasGoat());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);

        if (ATTACKER.equals(pKey))
            this.attackTimer = ATTACK_TIME;
    }

    private double getAttackBonus() {
        return Mth.lerp(this.getSize() / (double) MAX_SIZE, 0, 3);
    }

    private double getSpeedBonus() {
        return Mth.lerp(this.getSize() / (double) MAX_SIZE, 0, 0.08);
    }

    public boolean hasFlaps() {
        return this.hasVariant(0);
    }

    public boolean hasRing() {
        return this.hasVariant(1);
    }

    public boolean hasBeard() {
        return this.hasVariant(2);
    }

    public boolean hasHat() {
        return this.hasVariant(3);
    }

    public boolean hasGoat() {
        return this.hasVariant(4);
    }

    public byte getAttacker() {
        return this.entityData.get(ATTACKER);
    }

    private void setAttacker(byte b) {
        this.entityData.set(ATTACKER, b);
    }

    private void setFlaps(boolean b) {
        this.setVariant(0, b);
    }

    private void setRing(boolean b) {
        this.setVariant(1, b);
    }

    private void setBeard(boolean b) {
        this.setVariant(2, b);
    }

    private void setHat(boolean b) {
        this.setVariant(3, b);
    }

    private void setGoat(boolean b) {
        this.setVariant(4, b);
    }

    private void calcSizeModifiers() {
        if (this.getSize() <= RIDING_SIZE)
            this.ejectPassengers();
        this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DAMAGE_BONUS);
        this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_BONUS);
        this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(
                new AttributeModifier(DAMAGE_BONUS, this.getAttackBonus(), Operation.ADD_VALUE));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(
                new AttributeModifier(SPEED_BONUS, this.getSpeedBonus(), Operation.ADD_VALUE));
    }

    public float getNoseGoatRot(float partialTicks) {
        if (this.goatNoseTimer % 200 >= 40)
            return 0;

        float progress = (this.goatNoseTimer % 200 + partialTicks) / 40;
        return Mth.sin(progress * (float) Math.PI * 4) * 0.1f;
    }

    public float getAttackProgress(float partialTicks) {
        if (this.attackTimer <= 0)
            return 0;
        return (ATTACK_TIME - this.attackTimer + partialTicks) / (float) ATTACK_TIME;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity pEntity) {
        return super.getPassengerRidingPosition(pEntity).subtract(0, 0.6, 0);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        FoodProperties food = stack.getItem().getFoodProperties(stack, this);
        if (this.hasGoat() && stack.getItem() == Items.GOLDEN_APPLE && playerIn == this.getFamiliarOwner()) {
            if (!this.level().isClientSide) {
                stack.shrink(1);
                this.setGoat(false);
                GoatFamiliarEntity goat = new GoatFamiliarEntity(this.level(), this.hasRing(), this.hasBeard(),
                        this.getSize(), this.getFamiliarOwner());
                goat.setPos(this.getX(), this.getY(), this.getZ());
                goat.setCustomName(Component.literal(TextUtil.generateName()));
                this.level().addFreshEntity(goat);
                OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.GOAT_DETACH);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getSize() < MAX_SIZE && food != null && stack.is(ItemTags.MEAT)) {
            stack.shrink(1);
            this.setSize((byte) (this.getSize() + food.nutrition()));
            this.heal(4);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (!this.isSitting() && !this.isVehicle() && !playerIn.isSecondaryUseActive()
                && this.getFamiliarOwner() == playerIn && this.getSize() > RIDING_SIZE) {
            if (!this.level().isClientSide) {
                playerIn.startRiding(this);
                OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.CHIMERA_RIDE);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    protected void tickRidden(Player rider, Vec3 travelVec) {
        super.tickRidden(rider, travelVec);
        this.setRot(rider.getYRot(), rider.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        this.boost.tickBoost();

        if (this.isControlledByLocalInstance()) {
//            if (travelVec.z <= 0.0) {
//                this.gallopSoundCounter = 0;
//            }

            if (this.onGround()) {
                this.setIsJumping(false);
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                    this.executeRidersJump(this.playerJumpPendingScale, travelVec);
                }

                this.playerJumpPendingScale = 0.0F;
            }
        }
    }

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH) * 2;
    }


    protected void executeRidersJump(float pPlayerJumpPendingScale, Vec3 pTravelVector) {
        double d0 = this.getCustomJump() * (double) pPlayerJumpPendingScale * (double) this.getBlockJumpFactor();
        double d1 = d0 + (double) this.getJumpBoostPower();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d1, vec3.z);
        this.setIsJumping(true);
        this.hasImpulse = true;
        net.neoforged.neoforge.common.CommonHooks.onLivingJump(this);
        if (pTravelVector.z > 0.0) {
            float f = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
            float f1 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
            this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f * pPlayerJumpPendingScale, 0.0, 0.4F * f1 * pPlayerJumpPendingScale));
        }
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean pJumping) {
        this.isJumping = pJumping;
    }


    @Override
    protected Vec3 getRiddenInput(Player rider, Vec3 travelVec) {
//        if (this.onGround() && this.playerJumpPendingScale == 0.0F) {// && this.isStanding() && !this.allowStandSliding) {
//            return Vec3.ZERO;
//        } else {
//            float forward = rider.zza;
//            float strafe = rider.xxa * 0.5f;
//
//            if (forward < 0)
//                forward *= 0.25f;
//
//            return new Vec3(strafe, 0, forward);
//        }

        if (this.isVehicle()) {
            float forward = rider.zza;
            float strafe = rider.xxa * 0.5f;

            if (forward < 0)
                forward *= 0.25f;

            return super.getRiddenInput(rider, new Vec3(strafe, 0, forward));
        } else {
            return super.getRiddenInput(rider, travelVec);
        }
    }

    @Override
    protected float getRiddenSpeed(Player rider) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.8f;
    }


    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pDistance - 3, pDamageMultiplier);
    }

    @Override
    protected float getJumpPower() {
        return super.getJumpPower() * 1.35f;
    }

    private byte[] possibleAttackers() {
        return this.hasGoat() ? new byte[]{LION_ATTACKER, GOAT_ATTACKER, SNAKE_ATTACKER}
                : new byte[]{LION_ATTACKER, SNAKE_ATTACKER};
    }

    @Override
    protected float nextStep() {
        return this.moveDist + 1.6f;
    }


    @Nullable
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity)
            return livingEntity;
        return null;
    }

    @Override
    public void onPlayerJump(int pJumpPower) {
        if (pJumpPower < 0) {
            pJumpPower = 0;
        } else {
//            this.allowStandSliding = true;
//            this.standIfPossible();
        }

        if (pJumpPower >= 90) {
            this.playerJumpPendingScale = 1.0F;
        } else {
            this.playerJumpPendingScale = 0.6F + 0.4F * (float) pJumpPower / 90.0F;
        }
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void handleStartJump(int pJumpPower) {
//        this.allowStandSliding = true;
//        this.standIfPossible();
//        this.playJumpSound();
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return entity.distanceToSqr(this) < 9; //distance = 3 max
    }

    private static class DummyBoostHelper extends ItemBasedSteering {

        public DummyBoostHelper() {
            super(null, null, null);
        }

        @Override
        public void onSynced() {
        }

        @Override
        public boolean boost(RandomSource pRand) {
            return false;
        }

        @Override
        public void addAdditionalSaveData(CompoundTag pNbt) {
        }

        @Override
        public void readAdditionalSaveData(CompoundTag pNbt) {
        }

        @Override
        public void setSaddle(boolean pSaddled) {
        }

        @Override
        public boolean hasSaddle() {
            return false;
        }

    }

    private static class ChimeraRangedAttackGoal extends DevilFamiliarEntity.AttackGoal {

        ChimeraFamiliarEntity chimera;

        public ChimeraRangedAttackGoal(ChimeraFamiliarEntity chimera, float range) {
            super(chimera, range);
            this.chimera = chimera;
        }

        @Override
        protected void attack(List<LivingEntity> enemies) {
            byte attacker = this.randomAttacker();
            this.chimera.setAttacker(attacker);

            for (LivingEntity e : enemies) {
                e.hurt(this.chimera.damageSources().playerAttack((Player) this.chimera.getFamiliarOwner()),
                        (float) this.chimera.getAttributeValue(Attributes.ATTACK_DAMAGE));

                switch (attacker) {
                    case LION_ATTACKER:
                        e.setRemainingFireTicks(4 * 20);
                        break;
                    case GOAT_ATTACKER:
                        Vec3 direction = e.position().vectorTo(this.chimera.position());
                        e.knockback(2, direction.x, direction.z);
                        break;
                    case SNAKE_ATTACKER:
                        e.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 10));
                        break;
                }
            }
        }

        private byte randomAttacker() {
            byte[] attackers = this.chimera.possibleAttackers();
            return attackers[this.chimera.getRandom().nextInt(attackers.length)];
        }

    }

    private static class ChimeraMeleeAttackGoal extends MeleeAttackGoal {

        ChimeraFamiliarEntity chimera;

        public ChimeraMeleeAttackGoal(ChimeraFamiliarEntity chimera, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(chimera, speedModifier, followingTargetEvenIfNotSeen);
            this.chimera = chimera;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (!this.canPerformAttack(target))
                return;

            this.resetAttackCooldown();

            byte attacker = this.randomAttacker();
            this.chimera.setAttacker(attacker);

            target.hurt(this.chimera.damageSources().playerAttack((Player) this.chimera.getFamiliarOwner()),
                    (float) this.chimera.getAttributeValue(Attributes.ATTACK_DAMAGE));

            switch (attacker) {
                case LION_ATTACKER:
                    target.setRemainingFireTicks(4 * 20);
                    break;
                case GOAT_ATTACKER:
                    Vec3 direction = target.position().vectorTo(this.chimera.position());
                    target.knockback(2, direction.x, direction.z);
                    break;
                case SNAKE_ATTACKER:
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 10));
                    break;
            }
        }

        private byte randomAttacker() {
            byte[] attackers = this.chimera.possibleAttackers();
            return attackers[this.chimera.getRandom().nextInt(attackers.length)];
        }

    }
}
