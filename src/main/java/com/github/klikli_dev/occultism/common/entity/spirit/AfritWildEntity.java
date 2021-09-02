/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.github.klikli_dev.occultism.common.entity.spirit;

import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AfritWildEntity extends AfritEntity {

    //region Initialization
    public AfritWildEntity(EntityType<? extends SpiritEntity> type, World world) {
        super(type, world);
        this.setSpiritMaxAge(60 * 603); //1h default for wild afrit
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason,
                                            @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int maxBlazes = 3 + world.getRandom().nextInt(6);

        for (int i = 0; i < maxBlazes; i++) {
            BlazeEntity entity = EntityType.BLAZE.create(this.world);
            entity.onInitialSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
            double offsetX = world.getRandom().nextGaussian() * (1 + world.getRandom().nextInt(4));
            double offsetZ = world.getRandom().nextGaussian() * (1 + world.getRandom().nextInt(4));
            entity.setPositionAndRotation(this.getPosX() + offsetX, this.getPosY() + 1.5, this.getPosZ() + offsetZ,
                    world.getRandom().nextInt(360), 0);
            world.addEntity(entity);
        }

        return super.onInitialSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerGoals() {
        //Override all base goals
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(BlazeEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isFireDamage())
            return true;
        ITag<EntityType<?>> alliesTags = OccultismTags.AFRIT_ALLIES;

        Entity trueSource = source.getTrueSource();
        if (trueSource != null && alliesTags.contains(trueSource.getType()))
            return true;

        Entity immediateSource = source.getImmediateSource();
        if (immediateSource != null && alliesTags.contains(immediateSource.getType()))
            return true;

        return super.isInvulnerableTo(source);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return AfritEntity.registerAttributes();
    }
    //endregion Overrides
}
