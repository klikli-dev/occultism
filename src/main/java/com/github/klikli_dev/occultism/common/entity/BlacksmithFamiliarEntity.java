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
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BlacksmithFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Boolean> EARRING = EntityDataManager.createKey(BlacksmithFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> MARIO_MOUSTACHE = EntityDataManager
            .createKey(BlacksmithFamiliarEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SQUARE_HAIR = EntityDataManager
            .createKey(BlacksmithFamiliarEntity.class, DataSerializers.BOOLEAN);

    public BlacksmithFamiliarEntity(EntityType<? extends BlacksmithFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setEarring(this.getRNG().nextDouble() < 0.1);
        this.setMarioMoustache(this.getRNG().nextDouble() < 0.5);
        this.setSquareHair(this.getRNG().nextDouble() < 0.5);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasEarring())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(EARRING, false);
        this.dataManager.register(MARIO_MOUSTACHE, false);
        this.dataManager.register(SQUARE_HAIR, false);
    }

    public boolean hasEarring() {
        return this.dataManager.get(EARRING);
    }

    public boolean hasMarioMoustache() {
        return this.dataManager.get(MARIO_MOUSTACHE);
    }

    public boolean hasSquareHair() {
        return this.dataManager.get(SQUARE_HAIR);
    }

    private void setEarring(boolean b) {
        this.dataManager.set(EARRING, b);
    }

    private void setMarioMoustache(boolean b) {
        this.dataManager.set(MARIO_MOUSTACHE, b);
    }

    private void setSquareHair(boolean b) {
        this.dataManager.set(SQUARE_HAIR, b);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("hasEarring", this.hasEarring());
        compound.putBoolean("hasMarioMoustache", this.hasMarioMoustache());
        compound.putBoolean("hasSquareHair", this.hasSquareHair());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setEarring(compound.getBoolean("hasEarring"));
        this.setMarioMoustache(compound.getBoolean("hasMarioMoustache"));
        this.setSquareHair(compound.getBoolean("hasSquareHair"));
    }
}
