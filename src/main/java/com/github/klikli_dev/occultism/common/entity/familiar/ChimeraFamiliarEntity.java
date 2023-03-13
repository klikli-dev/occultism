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

package com.github.klikli_dev.occultism.common.entity.familiar;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ChimeraFamiliarEntity extends ResizableFamiliarEntity implements ItemSteerable {

    public static final byte NO_ATTACKER = 0;
    public static final byte LION_ATTACKER = 1;
    public static final byte GOAT_ATTACKER = 2;
    public static final byte SNAKE_ATTACKER = 3;
    private static final UUID SPEED_BONUS = UUID.fromString("f1db15e0-174b-4534-96a3-d941cec44e55");
    private static final UUID DAMAGE_BONUS = UUID.fromString("fdaa6165-abdf-4b85-aed6-199086f6a5ee");
    private static final byte RIDING_SIZE = 80;
    private static final double SHRINK_CHANCE = 0.005;
    private static final int JUMP_COOLDOWN = 20 * 2;
    private static final int ATTACK_TIME = 10;

    private static final EntityDataAccessor<Byte> ATTACKER = SynchedEntityData.defineId(ChimeraFamiliarEntity.class,
            EntityDataSerializers.BYTE);

    private final ItemBasedSteering boost = new DummyBoostHelper();
    private int jumpTimer;
    private int goatNoseTimer;
    private int attackTimer;

    public ChimeraFamiliarEntity(EntityType<? extends ChimeraFamiliarEntity> type, Level level) {
        super(type, level);
        this.goatNoseTimer = this.getRandom().nextInt(100);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.MAX_HEALTH, 20);
    }

    private boolean isRiderJumping(Player rider) {
        return this.jumping;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new AttackGoal(this, 10));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.getRandom().nextDouble() < SHRINK_CHANCE)
            this.setSize((byte) (this.getSize() - 1));

        if (this.jumpTimer > 0)
            this.jumpTimer--;

        this.attackTimer--;
        if (this.attackTimer == 0)
            this.setAttacker(NO_ATTACKER);

        if (this.level.isClientSide) {
            this.goatNoseTimer++;

            if (this.attackTimer > 0 && this.getAttacker() == LION_ATTACKER) {
                Vec3 direction = Vec3.directionFromRotation(this.getRotationVector()).scale(this.getScale());
                for (int i = 0; i < 5; i++) {
                    Vec3 pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            1 + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                    this.level.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.1, 0,
                            direction.z * 0.1);
                }
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setGoat(true);
        this.setSize((byte) 0);
        this.setFlaps(this.getRandom().nextBoolean());
        this.setRing(this.getRandom().nextBoolean());
        this.setBeard(this.getRandom().nextBoolean());
        this.setHat(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return true;
    }

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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKER, NO_ATTACKER);
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
                new AttributeModifier(DAMAGE_BONUS, "Chimera attack bonus", this.getAttackBonus(), Operation.ADDITION));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(
                new AttributeModifier(SPEED_BONUS, "Chimera speed bonus", this.getSpeedBonus(), Operation.ADDITION));
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
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        FoodProperties food = stack.getItem().getFoodProperties();
        if (this.hasGoat() && stack.getItem() == Items.GOLDEN_APPLE && playerIn == this.getFamiliarOwner()) {
            if (!this.level.isClientSide) {
                stack.shrink(1);
                this.setGoat(false);
                GoatFamiliarEntity goat = new GoatFamiliarEntity(this.level, this.hasRing(), this.hasBeard(),
                        this.getSize(), this.getFamiliarOwner());
                goat.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(goat);
                OccultismAdvancements.FAMILIAR.trigger(playerIn, FamiliarTrigger.Type.GOAT_DETACH);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        if (this.getSize() < MAX_SIZE && food != null && food.isMeat()) {
            stack.shrink(1);
            this.setSize((byte) (this.getSize() + food.getNutrition()));
            this.heal(4);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (!this.isSitting() && !this.isVehicle() && !playerIn.isSecondaryUseActive()
                && this.getFamiliarOwner() == playerIn && this.getSize() > RIDING_SIZE) {
            if (!this.level.isClientSide) {
                playerIn.startRiding(this);
                OccultismAdvancements.FAMILIAR.trigger(playerIn, FamiliarTrigger.Type.CHIMERA_RIDE);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void travelWithInput(Vec3 pTravelVec) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player rider) {
            float forward = rider.zza;
            float strafe = rider.xxa * 0.5f;
            if (this.isRiderJumping(rider) && this.onGround && this.jumpTimer <= 0) {
                this.jumpTimer = JUMP_COOLDOWN;
                Vec3 forwardDirection = Vec3.directionFromRotation(0, this.yRotO).scale(0.7);
                this.setDeltaMovement(this.getDeltaMovement().add(forwardDirection.x, 0, forwardDirection.z));
                this.jumpFromGround();
            }
            if (forward < 0)
                forward *= 0.25f;
            super.travel(new Vec3(strafe, 0, forward));
        } else {
            super.travel(pTravelVec);
        }
    }

    @Override
    public float getSteeringSpeed() {
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

    @Override
    public void travel(Vec3 pTravelVector) {
        this.travel(this, this.boost, pTravelVector);
    }

    private byte[] possibleAttackers() {
        return this.hasGoat() ? new byte[]{LION_ATTACKER, GOAT_ATTACKER, SNAKE_ATTACKER}
                : new byte[]{LION_ATTACKER, SNAKE_ATTACKER};
    }

    @Override
    protected float nextStep() {
        return this.moveDist + 1.6f;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 0.6;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
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

    private static class AttackGoal extends DevilFamiliarEntity.AttackGoal {

        ChimeraFamiliarEntity chimera;

        public AttackGoal(ChimeraFamiliarEntity chimera, float range) {
            super(chimera, range);
            this.chimera = chimera;
        }

        @Override
        protected void attack(List<LivingEntity> enemies) {
            byte attacker = this.randomAttacker();
            this.chimera.setAttacker(attacker);

            for (LivingEntity e : enemies) {
                e.hurt(DamageSource.playerAttack((Player) this.chimera.getFamiliarOwner()),
                        (float) this.chimera.getAttributeValue(Attributes.ATTACK_DAMAGE));

                switch (attacker) {
                    case LION_ATTACKER:
                        e.setSecondsOnFire(4);
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
}
