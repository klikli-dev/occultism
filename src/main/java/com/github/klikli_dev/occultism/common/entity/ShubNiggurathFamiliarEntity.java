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

import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ShubNiggurathFamiliarEntity extends FamiliarEntity {
    private static final int MAX_SPAWN_TIMER = 20 * 10;

    private int spawnTimer;

    public ShubNiggurathFamiliarEntity(EntityType<? extends ShubNiggurathFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        spawnTimer = MAX_SPAWN_TIMER;
    }

    public ShubNiggurathFamiliarEntity(World worldIn, GoatFamiliarEntity goat) {
        this(OccultismEntities.SHUB_NIGGURATH_FAMILIAR.get(), worldIn);
        this.setRing(goat.hasRing());
        this.setBeard(goat.hasBeard());
        this.setFamiliarOwner(goat.getFamiliarOwner());
        this.setPos(goat.getX(), goat.getY(), goat.getZ());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(2, new LookAtGoal(this, LivingEntity.class, 8));
        this.goalSelector.addGoal(3,
                new GreedyFamiliarEntity.RideFamiliarGoal<>(this, OccultismEntities.CTHULHU_FAMILIAR.get()));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(9, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected float tickHeadTurn(float p_110146_1_, float distance) {
        if (distance > 0.1) {
            yBodyRot = yRot;
            yHeadRot = MathHelper.rotateIfNecessary(yHeadRot, yBodyRot, getMaxHeadYRot());
        }
        return distance;
    }

    public CthulhuFamiliarEntity getCthulhuFriend() {
        if (getVehicle() instanceof CthulhuFamiliarEntity)
            return (CthulhuFamiliarEntity) getVehicle();
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            rotateTowardsFriend();

            createSpawn(this, new Vector3d(getRandomX(2), getRandomY(), getRandomZ(2)));
        }
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        createSpawn(wearer, new Vector3d(wearer.getRandomX(2), wearer.getRandomY(), wearer.getRandomZ(2)));
    }

    private void createSpawn(LivingEntity creator, Vector3d pos) {
        if (isEffectEnabled() && spawnTimer-- < 0) {
            spawnTimer = MAX_SPAWN_TIMER;
            ShubNiggurathSpawnEntity spawn = new ShubNiggurathSpawnEntity(creator.level, creator);
            spawn.setPos(pos.x, pos.y, pos.z);
            creator.level.addFreshEntity(spawn);
        }
    }

    private void rotateTowardsFriend() {
        CthulhuFamiliarEntity friend = getCthulhuFriend();
        if (friend != null) {
            Vector3d direction = position().vectorTo(friend.position());
            float rot = (float) Math.toDegrees(MathHelper.atan2(direction.z, direction.x)) - 50;
            yRotO = yRot;
            yRot = rot;
            yBodyRot = yRot;
            yBodyRotO = yRotO;
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (getVehicle() != null && immuneWhileHoldingHand(pSource))
            return false;

        if (super.hurt(pSource, pAmount)) {
            if (pSource.getEntity() != null) {
                GoatFamiliarEntity.ringBell(this);
            }
            return true;
        }
        return false;
    }

    private boolean immuneWhileHoldingHand(DamageSource s) {
        return s == DamageSource.CRAMMING || s == DamageSource.DROWN || s == DamageSource.FALL
                || s == DamageSource.FALLING_BLOCK || s == DamageSource.FLY_INTO_WALL || s == DamageSource.IN_WALL;
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    public boolean hasRing() {
        return this.hasVariant(0);
    }

    private void setRing(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasBeard() {
        return this.hasVariant(1);
    }

    private void setBeard(boolean b) {
        this.setVariant(1, b);
    }
}
