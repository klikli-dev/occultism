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

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.network.MessageHeadlessDie;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class HeadlessFamiliarEntity extends FamiliarEntity {

    public enum Rebuilt {
        LeftLeg(0), RightLeg(1), Body(2), LeftArm(3), RightArm(4), Head(5);

        private int value;

        Rebuilt(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    private static ImmutableBiMap<Byte, EntityType<? extends LivingEntity>> typesLookup;

    private static final int HEAD_TIME = 20 * 60;
    private static final byte NO_HEAD = 0;

    private static final DataParameter<Byte> HEAD = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BYTE);
    private static final DataParameter<Boolean> HAIRY = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GLASSES = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> WEAPON = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BYTE);
    private static final DataParameter<Boolean> HEADLESS_DEAD = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> REBUILT = EntityDataManager.defineId(HeadlessFamiliarEntity.class,
            DataSerializers.BYTE);

    private int headTimer, headlessDieTimer;

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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(Attributes.MAX_HEALTH, 40);
    }

    public HeadlessFamiliarEntity(EntityType<? extends HeadlessFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasGlasses())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason,
            ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
        this.setWeapon((byte) this.getRandom().nextInt(3));
        this.setHairy(this.getRandom().nextBoolean());
        this.setGlasses(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD, NO_HEAD);
        this.entityData.define(HAIRY, false);
        this.entityData.define(GLASSES, false);
        this.entityData.define(WEAPON, (byte) 0);
        this.entityData.define(HEADLESS_DEAD, false);
        this.entityData.define(REBUILT, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {

            if (headTimer-- == 0)
                this.setHead(NO_HEAD);

            if (this.hasBlacksmithUpgrade() && !this.isHeadlessDead() && this.tickCount % 10 == 0
                    && this.getHeadType() != null)
                for (LivingEntity e : this.level.getEntities(this.getHeadType(), this.getBoundingBox().inflate(5),
                        e -> e != this.getFamiliarOwner()))
                    e.addEffect(new EffectInstance(Effects.WEAKNESS, 20 * 3));
        } else {
            if (this.hasBlacksmithUpgrade() && !this.isHeadlessDead() && this.tickCount % 10 == 0) {
                Vector3d forward = Vector3d.directionFromRotation(0, this.yRot);
                Vector3d pos = this.position().add(forward.reverse().scale(0.15)).add(randPos(0.08), randPos(0.08),
                        randPos(0.08));
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + 1.1, pos.z, 0, 0, 0);
            }

            if (headlessDieTimer == 1)
                level.addParticle(ParticleTypes.SOUL, getX(), getY() + 1, getZ(), 0, 0, 0);

            if (headlessDieTimer-- > 7)
                for (int i = 0; i < 2; i++) {
                    level.addParticle(new RedstoneParticleData(0.5f, 0, 0, 1), getX() + randPos(0.3),
                            getY() + 1 + randPos(0.3), getZ() + randPos(0.3), 0, 0, 0);
                }
        }
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSrc, float pDamageAmount) {
        super.actuallyHurt(pDamageSrc, pDamageAmount);
        if (this.getHealth() / this.getMaxHealth() < 0.5 && !this.isHeadlessDead()) {
            this.setHeadlessDead(true);
            OccultismPackets.sendToTracking(this, new MessageHeadlessDie(this.getId()));
        }
    }

    private double randPos(double scale) {
        return (this.getRandom().nextFloat() - 0.5) * scale;
    }

    private void setHairy(boolean b) {
        this.entityData.set(HAIRY, b);
    }

    public boolean isHairy() {
        return this.entityData.get(HAIRY);
    }

    private void setGlasses(boolean b) {
        this.entityData.set(GLASSES, b);
    }

    public boolean hasGlasses() {
        return this.entityData.get(GLASSES);
    }

    private void setWeapon(byte b) {
        this.entityData.set(WEAPON, b);
    }

    private byte getWeapon() {
        return this.entityData.get(WEAPON);
    }

    public ItemStack getWeaponItem() {
        Item weapon = Items.IRON_SWORD;
        switch (getWeapon()) {
        case 1:
            weapon = Items.IRON_AXE;
            break;
        case 2:
            weapon = Items.IRON_HOE;
            break;
        }
        return new ItemStack(weapon);
    }

    private void setHead(byte head) {
        this.entityData.set(HEAD, head);
        if (head != NO_HEAD)
            this.headTimer = HEAD_TIME;
    }

    private byte getHead() {
        return this.entityData.get(HEAD);
    }

    public boolean hasHead() {
        return getHead() != NO_HEAD;
    }

    public EntityType<? extends LivingEntity> getHeadType() {
        return getTypesLookup().getOrDefault(this.getHead(), null);
    }

    public void setHeadType(EntityType<?> type) {
        if (type == null || !getTypesLookup().inverse().containsKey(type))
            return;
        this.setHead(getTypesLookup().inverse().get(type));
    }

    private void setHeadlessDead(boolean b) {
        this.entityData.set(HEADLESS_DEAD, b);
    }

    public boolean isHeadlessDead() {
        return this.entityData.get(HEADLESS_DEAD);
    }

    private void setRebuilt(byte b) {
        this.entityData.set(REBUILT, b);
    }

    private byte getRebuilt() {
        return this.entityData.get(REBUILT);
    }

    public boolean isRebuilt(Rebuilt r) {
        return ((this.getRebuilt() >> r.getValue()) & 1) == 1;
    }

    private void setRebuilt(Rebuilt r) {
        this.setRebuilt((byte) (this.getRebuilt() | (1 << r.getValue())));
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        if (this.isHeadlessDead()) {
            ItemStack stack = playerIn.getItemInHand(hand);
            Item item = stack.getItem();
            boolean success = false;
            if (item.is(Tags.Items.CROPS_WHEAT) && !isRebuilt(Rebuilt.LeftLeg)) {
                setRebuilt(Rebuilt.LeftLeg);
                success = true;
            } else if (item.is(Tags.Items.CROPS_WHEAT) && !isRebuilt(Rebuilt.RightLeg)) {
                setRebuilt(Rebuilt.RightLeg);
                success = true;
            } else if (item == Items.HAY_BLOCK && !isRebuilt(Rebuilt.Body)) {
                setRebuilt(Rebuilt.Body);
                success = true;
            } else if (item.is(Tags.Items.RODS_WOODEN) && isRebuilt(Rebuilt.Body) && !isRebuilt(Rebuilt.LeftArm)) {
                setRebuilt(Rebuilt.LeftArm);
                success = true;
            } else if (item.is(Tags.Items.RODS_WOODEN) && isRebuilt(Rebuilt.Body) && !isRebuilt(Rebuilt.RightArm)) {
                setRebuilt(Rebuilt.RightArm);
                success = true;
            } else if (item == Items.CARVED_PUMPKIN && isRebuilt(Rebuilt.Body) && !isRebuilt(Rebuilt.Head)) {
                setRebuilt(Rebuilt.Head);
                success = true;
            }

            if (success) {
                stack.shrink(1);
                return ActionResultType.sidedSuccess(!this.level.isClientSide);
            }
        }
        return super.mobInteract(playerIn, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("head", this.getHead());
        compound.putInt("headTimer", this.headTimer);
        compound.putBoolean("isHairy", this.isHairy());
        compound.putBoolean("hasGlasses", this.hasGlasses());
        compound.putByte("getWeapon", this.getWeapon());
        compound.putBoolean("isHeadlessDead", this.isHeadlessDead());
        compound.putByte("getRebuilt", this.getRebuilt());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setHead(compound.getByte("head"));
        this.headTimer = compound.getInt("headTimer");
        this.setHairy(compound.getBoolean("isHairy"));
        this.setGlasses(compound.getBoolean("hasGlasses"));
        this.setWeapon(compound.getByte("getWeapon"));
        this.setHeadlessDead(compound.getBoolean("isHeadlessDead"));
        this.setRebuilt(compound.getByte("getRebuilt"));
    }

    public void killHeadless() {
        this.headlessDieTimer = 20;
    }

}
