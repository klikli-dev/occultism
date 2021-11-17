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

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class FairyFamiliarEntity extends FamiliarEntity {

    public FairyFamiliarEntity(EntityType<? extends FairyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasFlower())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return Collections.emptyList();
    }

    public boolean hasTeeth() {
        return this.hasVariant(0);
    }

    private void setTeeth(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasFlower() {
        return this.hasVariant(1);
    }

    private void setFlower(boolean b) {
        this.setVariant(1, b);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        ILivingEntityData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setTeeth(this.getRandom().nextBoolean());
        this.setLeftHanded(this.getRandom().nextBoolean());
        this.setFlower(this.getRandom().nextDouble() < 0.1);
        return data;
    }
}
