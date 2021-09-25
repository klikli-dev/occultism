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
import java.util.EnumSet;
import java.util.List;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
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
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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
        return MathHelper.cos((tickCount + partialTicks) / 5);
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            return ImmutableList.of(new EffectInstance(Effects.NIGHT_VISION, 300, 1, false, false));
        }
        return Collections.emptyList();
    }

    private static class CannibalismGoal extends Goal {

        BatFamiliarEntity bat;
        BatEntity nearby;

        private CannibalismGoal(BatFamiliarEntity bat) {
            this.bat = bat;
        }

        @Override
        public boolean canUse() {
            nearby = nearbyBat();
            return nearby != null;
        }

        @Override
        public void start() {
            if (nearby != null) {
                nearby.hurt(DamageSource.mobAttack(bat), 10);
                OccultismAdvancements.FAMILIAR.trigger(bat.getFamiliarOwner(), FamiliarTrigger.Type.BAT_EAT);
            }
        }

        private BatEntity nearbyBat() {
            BatEntity nearby = null;
            List<BatEntity> bats = bat.level.getEntitiesOfClass(BatEntity.class, bat.getBoundingBox().inflate(2));
            if (!bats.isEmpty())
                nearby = bats.get(0);
            return nearby;
        }

    }
}
