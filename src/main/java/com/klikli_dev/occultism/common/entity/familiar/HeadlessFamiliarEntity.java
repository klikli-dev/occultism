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

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.network.messages.MessageHeadlessDie;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class HeadlessFamiliarEntity extends FamiliarEntity {

    private static final int HEAD_TIME = 20 * 60;

    private static final byte NO_HEAD = 0;
    private static final EntityDataAccessor<Byte> HEAD = SynchedEntityData.defineId(HeadlessFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> WEAPON = SynchedEntityData.defineId(HeadlessFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> REBUILT = SynchedEntityData.defineId(HeadlessFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private static ImmutableBiMap<Byte, EntityType<? extends LivingEntity>> typesLookup;
    private int headTimer, headlessDieTimer;

    public HeadlessFamiliarEntity(EntityType<? extends HeadlessFamiliarEntity> type, Level level) {
        super(type, level);
    }

    private static ImmutableBiMap<Byte, EntityType<? extends LivingEntity>> getTypesLookup() {
        if (typesLookup == null) {
            ImmutableBiMap.Builder<Byte, EntityType<? extends LivingEntity>> builder = new ImmutableBiMap.Builder<>();
            builder.put((byte) 1, EntityType.PLAYER);
            builder.put((byte) 2, EntityType.ZOMBIE);
            builder.put((byte) 3, EntityType.SKELETON);
            builder.put((byte) 4, EntityType.WITHER_SKELETON);
            builder.put((byte) 5, EntityType.CREEPER);
            builder.put((byte) 6, EntityType.SPIDER);
            builder.put((byte) 7, OccultismEntities.CTHULHU_FAMILIAR.get());
            typesLookup = builder.build();
        }
        return typesLookup;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.MAX_HEALTH, 40);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new DevilFamiliarEntity.AttackGoal(this, 5) {
            @Override
            public boolean canUse() {
                return super.canUse() && !HeadlessFamiliarEntity.this.isHeadlessDead();
            }
        });
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {

            if (this.headTimer-- == 0)
                this.setHead(NO_HEAD);

            if (this.hasBlacksmithUpgrade() && !this.isHeadlessDead() && this.tickCount % 10 == 0
                    && this.getHeadType() != null)
                for (LivingEntity e : this.level().getEntities(this.getHeadType(), this.getBoundingBox().inflate(5),
                        e -> e != this.getFamiliarOwner()))
                    e.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * 3));
        } else {
            if (this.hasBlacksmithUpgrade() && !this.isHeadlessDead() && this.tickCount % 10 == 0) {
                Vec3 forward = Vec3.directionFromRotation(0, this.getYRot());
                Vec3 pos = this.position().add(forward.reverse().scale(0.15)).add(this.randPos(0.08), this.randPos(0.08),
                        this.randPos(0.08));
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + 1.1, pos.z, 0, 0, 0);
            }

            if (this.headlessDieTimer == 1)
                this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY() + 1, this.getZ(), 0, 0, 0);

            if (this.headlessDieTimer-- > 7)
                for (int i = 0; i < 2; i++) {
                    this.level().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0, 0), 1), this.getX() + this.randPos(0.3),
                            this.getY() + 1 + this.randPos(0.3), this.getZ() + this.randPos(0.3), 0, 0, 0);
                }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setWeapon((byte) this.getRandom().nextInt(3));
        this.setHairy(this.getRandom().nextBoolean());
        this.setGlasses(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HEAD, NO_HEAD);
        builder.define(WEAPON, (byte) 0);
        builder.define(REBUILT, (byte) 0);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        if (this.isHeadlessDead()) {
            ItemStack stack = playerIn.getItemInHand(hand);
            Item item = stack.getItem();
            boolean success = false;
            if (stack.is(Tags.Items.CROPS_WHEAT) && !this.isRebuilt(Rebuilt.LeftLeg)) {
                this.setRebuilt(Rebuilt.LeftLeg);
                success = true;
            } else if (stack.is(Tags.Items.CROPS_WHEAT) && !this.isRebuilt(Rebuilt.RightLeg)) {
                this.setRebuilt(Rebuilt.RightLeg);
                success = true;
            } else if (item == Items.HAY_BLOCK && !this.isRebuilt(Rebuilt.Body)) {
                this.setRebuilt(Rebuilt.Body);
                success = true;
            } else if (stack.is(Tags.Items.RODS_WOODEN) && this.isRebuilt(Rebuilt.Body) && !this.isRebuilt(Rebuilt.LeftArm)) {
                this.setRebuilt(Rebuilt.LeftArm);
                success = true;
            } else if (stack.is(Tags.Items.RODS_WOODEN) && this.isRebuilt(Rebuilt.Body) && !this.isRebuilt(Rebuilt.RightArm)) {
                this.setRebuilt(Rebuilt.RightArm);
                success = true;
            } else if (item == Items.CARVED_PUMPKIN && this.isRebuilt(Rebuilt.Body) && !this.isRebuilt(Rebuilt.Head)) {
                this.setRebuilt(Rebuilt.Head);
                success = true;
            }

            if (success) {
                stack.shrink(1);
                if (this.isFullyRebuilt())
                    OccultismAdvancements.FAMILIAR.get().trigger(playerIn, FamiliarTrigger.Type.HEADLESS_REBUILT);

                return InteractionResult.sidedSuccess(!this.level().isClientSide);
            }
        }
        return super.mobInteract(playerIn, hand);
    }


    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasGlasses())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHead(compound.getByte("head"));
        this.headTimer = compound.getInt("headTimer");
        if (!compound.contains("variants")) {
            this.setHairy(compound.getBoolean("isHairy"));
            this.setGlasses(compound.getBoolean("hasGlasses"));
            this.setHeadlessDead(compound.getBoolean("isHeadlessDead"));
        }
        this.setWeapon(compound.getByte("getWeapon"));
        this.setRebuilt(compound.getByte("getRebuilt"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("head", this.getHead());
        compound.putInt("headTimer", this.headTimer);
        compound.putByte("getWeapon", this.getWeapon());
        compound.putByte("getRebuilt", this.getRebuilt());
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSrc, float pDamageAmount) {
        super.actuallyHurt(pDamageSrc, pDamageAmount);
        if (this.getHealth() / this.getMaxHealth() < 0.5 && !this.isHeadlessDead()) {
            this.setHeadlessDead(true);
            Networking.sendToTracking(this, new MessageHeadlessDie(this.getId()));
        }
    }

    private double randPos(double scale) {
        return (this.getRandom().nextFloat() - 0.5) * scale;
    }

    public boolean isHairy() {
        return this.hasVariant(0);
    }

    private void setHairy(boolean b) {
        this.setVariant(0, b);
    }

    private void setGlasses(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasGlasses() {
        return this.hasVariant(1);
    }

    private byte getWeapon() {
        return this.entityData.get(WEAPON);
    }

    private void setWeapon(byte b) {
        this.entityData.set(WEAPON, b);
    }

    public ItemStack getWeaponItem() {
        Item weapon = Items.IRON_SWORD;
        switch (this.getWeapon()) {
            case 1:
                weapon = Items.IRON_AXE;
                break;
            case 2:
                weapon = Items.IRON_HOE;
                break;
        }
        return new ItemStack(weapon);
    }

    private byte getHead() {
        return this.entityData.get(HEAD);
    }

    private void setHead(byte head) {
        this.entityData.set(HEAD, head);
        if (head != NO_HEAD)
            this.headTimer = HEAD_TIME;
    }

    public boolean hasHead() {
        return this.getHead() != NO_HEAD;
    }

    public EntityType<? extends LivingEntity> getHeadType() {
        return getTypesLookup().getOrDefault(this.getHead(), null);
    }

    public void setHeadType(EntityType<?> type) {
        if (type == null || !getTypesLookup().inverse().containsKey(type))
            return;
        this.setHead(getTypesLookup().inverse().get(type));
    }

    public boolean isHeadlessDead() {
        return this.hasVariant(2);
    }

    private void setHeadlessDead(boolean b) {
        this.setVariant(2, b);
    }

    private byte getRebuilt() {
        return this.entityData.get(REBUILT);
    }

    private void setRebuilt(byte b) {
        this.entityData.set(REBUILT, b);
    }

    private void setRebuilt(Rebuilt r) {
        this.setRebuilt((byte) (this.getRebuilt() | (1 << r.getValue())));
    }

    public boolean isRebuilt(Rebuilt r) {
        return ((this.getRebuilt() >> r.getValue()) & 1) == 1;
    }

    private boolean isFullyRebuilt() {
        return this.getRebuilt() == 63;
    }

    public void killHeadless() {
        this.headlessDieTimer = 20;
    }

    public enum Rebuilt {
        LeftLeg(0), RightLeg(1), Body(2), LeftArm(3), RightArm(4), Head(5);

        private final int value;

        Rebuilt(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

}
