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
import com.github.klikli_dev.occultism.util.FamiliarUtil;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class FairyFamiliarEntity extends FamiliarEntity implements IFlyingAnimal {

    private static final float ANIMATION_HEIGHT_SPEED = 0.2f;

    public FairyFamiliarEntity(EntityType<? extends FairyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.WALKABLE, -1);
        this.moveControl = new FlyingMovementController(this, 20, true);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(Attributes.FLYING_SPEED, 0.4);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasFlower())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    public float getAnimationHeight(float partialTicks) {
        return MathHelper.cos((this.tickCount + partialTicks) * ANIMATION_HEIGHT_SPEED) * 0.1f + 0.12f;
    }

    public float getWingRot(float partialTicks) {
        return MathHelper.cos((this.tickCount + partialTicks) * ANIMATION_HEIGHT_SPEED * 3);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, world) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                BlockState state = this.level.getBlockState(pos);
                BlockState below = this.level.getBlockState(pos.below());
                return state.getBlock().isAir(state, this.level, pos)
                        && below.getBlock().isAir(below, this.level, pos.below());
            }

            @Override
            public void tick() {
                super.tick();
                BlockPos pos = this.mob.blockPosition().below();
                BlockState below = this.level.getBlockState(pos);

                if (below.getBlock().isAir(below, this.level, pos)) {
                    if (this.mob.yya < 0)
                        this.mob.setYya(this.mob.yya * 0.9f);

                    if (this.mob.getDeltaMovement().y < 0)
                        this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(1, 0.9, 1));
                }
            }
        };
        return navigator;
    }

    private float getPartyArmArg(float partialTicks) {
        return (this.tickCount + partialTicks) * 0.2f;
    }

    public float getPartyArmRotX(float partialTicks) {
        return MathHelper.cos(getPartyArmArg(partialTicks)) * FamiliarUtil.toRads(40) - FamiliarUtil.toRads(90);
    }

    public float getPartyArmRotY(float partialTicks) {
        return MathHelper.sin(getPartyArmArg(partialTicks)) * FamiliarUtil.toRads(40);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSitting()) {
            this.setDeltaMovement(Vector3d.ZERO);
            this.yBodyRot = this.yBodyRotO;
        }

        partyParticle();
    }

    private void partyParticle() {
        if (!level.isClientSide || !this.isPartying())
            return;

        Vector3d right = Vector3d.directionFromRotation(0, this.yBodyRot).yRot(FamiliarUtil.toRads(-90));
        Vector3d armVector = new Vector3d(0, -0.4, 0).xRot(getPartyArmRotX(0))
                .yRot(-getPartyArmRotY(0) + FamiliarUtil.toRads(-yBodyRot + 180));
        Vector3d pos = position().add(right.scale(0.2 * (this.isLeftHanded() ? -1 : 1)))
                .add(0, 0.7 + getAnimationHeight(0), 0).add(armVector);

        level.addParticle(new RedstoneParticleData(0.9f, 0.9f, 0.5f, 1), pos.x, pos.y, pos.z, 0, 0, 0);
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
