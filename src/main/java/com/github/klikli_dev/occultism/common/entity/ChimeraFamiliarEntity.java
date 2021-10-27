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

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ChimeraFamiliarEntity extends FamiliarEntity {

    private static final byte MAX_SIZE = 100;
    private static final double SHRINK_CHANCE = 0.01;

    private static final DataParameter<Byte> SIZE = EntityDataManager.defineId(ChimeraFamiliarEntity.class,
            DataSerializers.BYTE);

    public ChimeraFamiliarEntity(EntityType<? extends ChimeraFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason,
            ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
        this.setSize((byte) 0);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, (byte) 0);
    }

    public byte getSize() {
        return this.entityData.get(SIZE);
    }

    private void setSize(byte size) {
        this.entityData.set(SIZE, (byte) MathHelper.clamp(size, 0, MAX_SIZE));
    }

    @Override
    public float getScale() {
        return 1 + getSize() * 1f / MAX_SIZE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide && getRandom().nextDouble() < SHRINK_CHANCE)
            this.setSize((byte) (this.getSize() - 1));
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        Food food = stack.getItem().getFoodProperties();
        if (getSize() < MAX_SIZE && food != null && food.isMeat()) {
            stack.shrink(1);
            setSize((byte) (getSize() + food.getNutrition()));
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(playerIn, hand);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (SIZE.equals(pKey))
            this.refreshDimensions();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("getSize", this.getSize());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSize(compound.getByte("getSize"));
    }
}
