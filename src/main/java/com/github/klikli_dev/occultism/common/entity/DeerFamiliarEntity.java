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

import java.util.Collections;
import java.util.UUID;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DeerFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Boolean> RED_NOSE = EntityDataManager.defineId(DeerFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private static final UUID SPEED_UUID = UUID.fromString("5ebf190f-3c59-41e7-9085-d14b37dfc863");

    private static final byte START_EATING = 10;

    private int eatTimer, neckRotTimer, oNeckRotTimer;

    public DeerFamiliarEntity(EntityType<? extends DeerFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new EatGrassGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }
    
    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (hasRedNose())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && !this.isGlowing() && this.hasRedNose())
            this.setGlowing(true);

        if (this.level.isClientSide) {
            this.eatTimer--;
            this.oNeckRotTimer = this.neckRotTimer;
            if (this.isEating())
                this.neckRotTimer = Math.min(this.neckRotTimer + 1, 10);
            else
                this.neckRotTimer = Math.max(this.neckRotTimer - 1, 0);
        }

        if (!this.level.isClientSide) {
            Entity owner = this.getFamiliarOwner();
            if (owner != null && this.distanceToSqr(owner) > 50) {
                if (this.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_UUID) == null)
                    this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(
                            new AttributeModifier(SPEED_UUID, "deer_speedup", 0.15, Operation.ADDITION));
            } else if (this.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_UUID) != null) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_UUID);
            }
        }
    }

    public float getNeckRot(float partialTick) {
        return 0.4f
                + MathHelper.lerp(MathHelper.lerp(partialTick, this.oNeckRotTimer, this.neckRotTimer) / 10, 0, 1.5f);
    }

    @Override
    public void ate() {
        if (this.getRandom().nextDouble() < 0.25) {
            this.spawnAtLocation(OccultismItems.DATURA_SEEDS.get(), 0);
            LivingEntity owner = getOwner();
            if (owner instanceof ServerPlayerEntity)
                OccultismAdvancements.FAMILIAR.trigger((ServerPlayerEntity) owner, FamiliarTrigger.Type.DEER_POOP);
        }
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            return ImmutableList.of(new EffectInstance(Effects.JUMP, 300, 0, false, false));
        }
        return Collections.emptyList();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RED_NOSE, false);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setRedNose(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setRedNose(compound.getBoolean("hasRedNose"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasRedNose", this.hasRedNose());
    }

    public boolean hasRedNose() {
        return this.entityData.get(RED_NOSE);
    }

    private void setRedNose(boolean b) {
        this.entityData.set(RED_NOSE, b);
    }

    public boolean isEating() {
        return this.eatTimer > 0;
    }

    private void startEating() {
        this.eatTimer = 40;
        this.neckRotTimer = 0;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == START_EATING)
            this.startEating();
        else
            super.handleEntityEvent(id);
    }
}
