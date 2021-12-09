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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToFindFieldException;

public class ChimeraFamiliarEntity extends ResizableFamiliarEntity implements IRideable {

    private static final UUID SPEED_BONUS = UUID.fromString("f1db15e0-174b-4534-96a3-d941cec44e55");
    private static final UUID DAMAGE_BONUS = UUID.fromString("fdaa6165-abdf-4b85-aed6-199086f6a5ee");

    public static final byte NO_ATTACKER = 0;
    public static final byte LION_ATTACKER = 1;
    public static final byte GOAT_ATTACKER = 2;
    public static final byte SNAKE_ATTACKER = 3;

    private static final byte RIDING_SIZE = 80;
    private static final double SHRINK_CHANCE = 0.005;
    private static final int JUMP_COOLDOWN = 20 * 2;
    private static final int ATTACK_TIME = 10;

    private static final DataParameter<Byte> ATTACKER = EntityDataManager.defineId(ChimeraFamiliarEntity.class,
            DataSerializers.BYTE);

    private static Field isRiderJumping;

    private BoostHelper boost = new DummyBoostHelper();
    private int jumpTimer;
    private int goatNoseTimer;
    private int attackTimer;

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(Attributes.ATTACK_DAMAGE, 3).add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 20);
    }

    public ChimeraFamiliarEntity(EntityType<? extends ChimeraFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.goatNoseTimer = this.getRandom().nextInt(100);
    }

    private boolean isRiderJumping(PlayerEntity rider) {
        try {
            if (isRiderJumping == null)
                isRiderJumping = ObfuscationReflectionHelper.findField(LivingEntity.class, "field_70703_bu");
            return isRiderJumping.getBoolean(rider);
        } catch (IllegalArgumentException | IllegalAccessException | UnableToFindFieldException e) {
            Occultism.LOGGER.debug("Unable to get jump field for Chimera familiar");
            return false;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new AttackGoal(this, 10));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason,
            ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
        this.setGoat(true);
        this.setSize((byte) 0);
        this.setFlaps(this.getRandom().nextBoolean());
        this.setRing(this.getRandom().nextBoolean());
        this.setBeard(this.getRandom().nextBoolean());
        this.setHat(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasHat())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKER, NO_ATTACKER);
    }

    private double getAttackBonus() {
        return MathHelper.lerp(this.getSize() / (double) MAX_SIZE, 0, 3);
    }

    private double getSpeedBonus() {
        return MathHelper.lerp(this.getSize() / (double) MAX_SIZE, 0, 0.08);
    }

    @Override
    public void setSize(byte size) {
        super.setSize(size);
        calcSizeModifiers();
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

    private void setAttacker(byte b) {
        this.entityData.set(ATTACKER, b);
    }

    private void calcSizeModifiers() {
        if (this.getSize() <= RIDING_SIZE)
            this.ejectPassengers();
        this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DAMAGE_BONUS);
        this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_BONUS);
        this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(
                new AttributeModifier(DAMAGE_BONUS, "Chimera attack bonus", getAttackBonus(), Operation.ADDITION));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(
                new AttributeModifier(SPEED_BONUS, "Chimera speed bonus", getSpeedBonus(), Operation.ADDITION));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide && getRandom().nextDouble() < SHRINK_CHANCE)
            this.setSize((byte) (this.getSize() - 1));

        if (jumpTimer > 0)
            jumpTimer--;

        this.attackTimer--;
        if (this.attackTimer == 0)
            this.setAttacker(NO_ATTACKER);

        if (level.isClientSide) {
            this.goatNoseTimer++;

            if (this.attackTimer > 0 && this.getAttacker() == LION_ATTACKER) {
                Vector3d direction = Vector3d.directionFromRotation(this.getRotationVector()).scale(getScale());
                for (int i = 0; i < 5; i++) {
                    Vector3d pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            1 + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                            direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                    this.level.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.1, 0,
                            direction.z * 0.1);
                }
            }
        }
    }

    public float getNoseGoatRot(float partialTicks) {
        if (this.goatNoseTimer % 200 >= 40)
            return 0;

        float progress = (this.goatNoseTimer % 200 + partialTicks) / 40;
        return MathHelper.sin(progress * (float) Math.PI * 4) * 0.1f;
    }

    public float getAttackProgress(float partialTicks) {
        if (this.attackTimer <= 0)
            return 0;
        return (ATTACK_TIME - this.attackTimer + partialTicks) / (float) ATTACK_TIME;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        Food food = stack.getItem().getFoodProperties();
        if (hasGoat() && stack.getItem() == Items.GOLDEN_APPLE && playerIn == this.getFamiliarOwner()) {
            if (!this.level.isClientSide) {
                stack.shrink(1);
                this.setGoat(false);
                GoatFamiliarEntity goat = new GoatFamiliarEntity(this.level, this.hasRing(), this.hasBeard(),
                        this.getSize(), this.getFamiliarOwner());
                goat.setPos(this.getX(), this.getY(), this.getZ());
                level.addFreshEntity(goat);
                OccultismAdvancements.FAMILIAR.trigger(playerIn, FamiliarTrigger.Type.GOAT_DETACH);
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
        if (getSize() < MAX_SIZE && food != null && food.isMeat()) {
            stack.shrink(1);
            setSize((byte) (getSize() + food.getNutrition()));
            this.heal(4);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (!isSitting() && !this.isVehicle() && !playerIn.isSecondaryUseActive()
                && getFamiliarOwner() == playerIn && getSize() > RIDING_SIZE) {
            if (!this.level.isClientSide) {
                playerIn.startRiding(this);
                OccultismAdvancements.FAMILIAR.trigger(playerIn, FamiliarTrigger.Type.CHIMERA_RIDE);
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 0.6;
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> pKey) {
        super.onSyncedDataUpdated(pKey);

        if (ATTACKER.equals(pKey))
            this.attackTimer = ATTACK_TIME;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
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
    public void travel(Vector3d pTravelVector) {
        this.travel(this, this.boost, pTravelVector);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    protected float getJumpPower() {
        return super.getJumpPower() * 1.35f;
    }

    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pDistance - 3, pDamageMultiplier);
    }

    private byte[] possibleAttackers() {
        return this.hasGoat() ? new byte[] { LION_ATTACKER, GOAT_ATTACKER, SNAKE_ATTACKER }
                : new byte[] { LION_ATTACKER, SNAKE_ATTACKER };
    }

    @Override
    public void travelWithInput(Vector3d pTravelVec) {
        if (this.isVehicle() && getControllingPassenger() instanceof PlayerEntity) {
            PlayerEntity rider = (PlayerEntity) getControllingPassenger();
            float forward = rider.zza;
            float strafe = rider.xxa * 0.5f;
            if (isRiderJumping(rider) && onGround && jumpTimer <= 0) {
                jumpTimer = JUMP_COOLDOWN;
                Vector3d forwardDirection = Vector3d.directionFromRotation(0, this.yRot).scale(0.7);
                this.setDeltaMovement(this.getDeltaMovement().add(forwardDirection.x, 0, forwardDirection.z));
                this.jumpFromGround();
            }
            if (forward < 0)
                forward *= 0.25f;
            super.travel(new Vector3d(strafe, 0, forward));
        } else {
            super.travel(pTravelVec);
        }
    }

    @Override
    protected float nextStep() {
        return this.moveDist + 1.6f;
    }

    @Override
    public float getSteeringSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.8f;
    }

    private static class DummyBoostHelper extends BoostHelper {

        public DummyBoostHelper() {
            super(null, null, null);
        }

        @Override
        public void readAdditionalSaveData(CompoundNBT pNbt) {
        }

        @Override
        public void addAdditionalSaveData(CompoundNBT pNbt) {
        }

        @Override
        public boolean boost(Random pRand) {
            return false;
        }

        @Override
        public boolean hasSaddle() {
            return false;
        }

        @Override
        public void onSynced() {
        }

        @Override
        public void setSaddle(boolean pSaddled) {
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
            byte attacker = randomAttacker();
            this.chimera.setAttacker(attacker);

            for (LivingEntity e : enemies) {
                e.hurt(DamageSource.playerAttack((PlayerEntity) this.chimera.getFamiliarOwner()),
                        (float) this.chimera.getAttributeValue(Attributes.ATTACK_DAMAGE));

                switch (attacker) {
                case LION_ATTACKER:
                    e.setSecondsOnFire(4);
                    break;
                case GOAT_ATTACKER:
                    Vector3d direction = e.position().vectorTo(this.chimera.position());
                    e.knockback(2, direction.x, direction.z);
                    break;
                case SNAKE_ATTACKER:
                    e.addEffect(new EffectInstance(Effects.POISON, 20 * 10));
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
