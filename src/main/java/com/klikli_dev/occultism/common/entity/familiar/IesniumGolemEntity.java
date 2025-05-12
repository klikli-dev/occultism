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

package com.klikli_dev.occultism.common.entity.familiar;

import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class IesniumGolemEntity extends IronGolem{

    public IesniumGolemEntity(EntityType<? extends IronGolem> type,
                              Level worldIn) {
        super(type, worldIn);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6, false));
        this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
        this.goalSelector.addGoal(5, new OfferFlowerGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new DefendVillageTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, p_28879_ -> p_28879_ instanceof Enemy));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    //region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 100.0)
                .add(Attributes.ATTACK_SPEED,5)
                .add(Attributes.FOLLOW_RANGE,64);
    }

    @Override
    public boolean canAttackType(EntityType<?> type) {
        return type != EntityType.PLAYER;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if(source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            this.teleportTo(this.level().getSharedSpawnPos().getX(), this.level().getSharedSpawnPos().getY(), this.level().getSharedSpawnPos().getZ());
            while (!level().getBlockState(this.getOnPos()).getBlock().isPossibleToRespawnInThis(level().getBlockState(this.getOnPos()))
                    || !level().getBlockState(this.getOnPos(1)).getBlock().isPossibleToRespawnInThis(level().getBlockState(this.getOnPos(1)))
                    || !level().getBlockState(this.getOnPos(2)).getBlock().isPossibleToRespawnInThis(level().getBlockState(this.getOnPos(2)))
                    || !level().getBlockState(this.getOnPos(3)).getBlock().isPossibleToRespawnInThis(level().getBlockState(this.getOnPos(3))))
                this.teleportRelative(0, 1, 0);
        }

        if (source.getEntity() == null || !source.getEntity().isCrouching())
            return true;

        return super.isInvulnerableTo(source);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            var shard = new ItemStack(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get());

            var health = this.getHealth();
            this.setHealth(this.getMaxHealth()); //simulate a healthy familiar to avoid death on respawn
            this.resetFallDistance();
            this.removeAllEffects();
            this.remove(RemovalReason.DISCARDED);

            var entityData = new CompoundTag();
            var id = this.getEncodeId();
            if (id != null)
                entityData.putString("id", id);
            entityData = this.saveWithoutId(entityData);

            shard.set(DataComponents.ENTITY_DATA, CustomData.of(entityData));
            this.setHealth(health);

            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), shard);
            entityitem.setPickUpDelay(5);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));

            this.level().addFreshEntity(entityitem);

            return super.hurt(source, amount);
        }
    }
}
