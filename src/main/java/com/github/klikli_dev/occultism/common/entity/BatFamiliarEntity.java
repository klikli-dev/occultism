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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class BatFamiliarEntity extends FamiliarEntity implements FlyingAnimal {

    public BatFamiliarEntity(EntityType<? extends BatFamiliarEntity> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.FLYING_SPEED, 0.4);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        SitGoal sitGoal = new SitGoal(this);
        sitGoal.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.goalSelector.addGoal(2, sitGoal);
        this.goalSelector.addGoal(3, new CannibalismGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                BlockState state = this.level.getBlockState(pos);
                return state.isAir() || !state.getMaterial().blocksMotion();
            }
        };
        return navigation;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSitting())
            this.setDeltaMovement(Vec3.ZERO);
    }

    public float getAnimationHeight(float partialTicks) {
        return Mth.cos((this.tickCount + partialTicks) / 5);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
            return ImmutableList.of(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 1, false, false));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    private static class CannibalismGoal extends Goal {

        BatFamiliarEntity bat;
        Bat nearby;

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

        private Bat nearbyBat() {
            Bat nearby = null;
            List<Bat> bats = this.bat.level.getEntitiesOfClass(Bat.class, this.bat.getBoundingBox().inflate(2));
            if (!bats.isEmpty())
                nearby = bats.get(0);
            return nearby;
        }

    }
}
