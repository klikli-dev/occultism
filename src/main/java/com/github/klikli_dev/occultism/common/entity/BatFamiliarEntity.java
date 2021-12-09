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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEffects;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BatFamiliarEntity extends FamiliarEntity implements IFlyingAnimal {

    public BatFamiliarEntity(EntityType<? extends BatFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(Attributes.FLYING_SPEED, 0.4);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setRibbon(this.getRandom().nextDouble() < 0.1);
        this.setTail(this.getRandom().nextBoolean());
        this.setHair(this.getRandom().nextBoolean());
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasRibbon())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        SitGoal sitGoal = new SitGoal(this);
        sitGoal.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.goalSelector.addGoal(2, sitGoal);
        this.goalSelector.addGoal(3, new CannibalismGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, world) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                BlockState state = this.level.getBlockState(pos);
                return state.getBlock().isAir(state, this.level, pos) || !state.getMaterial().blocksMotion();
            }
        };
        return navigator;
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
        if (this.isSitting())
            this.setDeltaMovement(Vector3d.ZERO);
    }

    public float getAnimationHeight(float partialTicks) {
        return MathHelper.cos((this.tickCount + partialTicks) / 5);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        List<EffectInstance> effects = new ArrayList<>();
        effects.add(new EffectInstance(Effects.NIGHT_VISION, 300, 1, false, false));
        if (this.hasBlacksmithUpgrade())
            effects.add(new EffectInstance(OccultismEffects.BAT_LIFESTEAL.get(), 300, 0, false, false));
        return effects;
    }

    public boolean hasHair() {
        return this.hasVariant(0);
    }

    private void setHair(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasRibbon() {
        return this.hasVariant(1);
    }

    private void setRibbon(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasTail() {
        return this.hasVariant(2);
    }

    private void setTail(boolean b) {
        this.setVariant(2, b);
    }

    private static class CannibalismGoal extends Goal {

        BatFamiliarEntity bat;
        BatEntity nearby;

        private CannibalismGoal(BatFamiliarEntity bat) {
            this.bat = bat;
        }

        @Override
        public boolean canUse() {
            this.nearby = this.nearbyBat();
            return this.nearby != null;
        }

        @Override
        public void start() {
            if (this.nearby != null) {
                this.nearby.hurt(DamageSource.mobAttack(this.bat), 10);
                OccultismAdvancements.FAMILIAR.trigger(this.bat.getFamiliarOwner(), FamiliarTrigger.Type.BAT_EAT);
            }
        }

        private BatEntity nearbyBat() {
            BatEntity nearby = null;
            List<BatEntity> bats = this.bat.level.getEntitiesOfClass(BatEntity.class,
                    this.bat.getBoundingBox().inflate(2));
            if (!bats.isEmpty())
                nearby = bats.get(0);
            return nearby;
        }

    }
}
