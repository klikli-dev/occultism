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

package com.klikli_dev.occultism.common.entity.familiar;

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class DevilFamiliarEntity extends FamiliarEntity {

    private final float heightOffset;

    public DevilFamiliarEntity(EntityType<? extends DevilFamiliarEntity> type, Level level) {
        super(type, level);
        this.heightOffset = this.getRandom().nextFloat() * 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 1f);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setLollipop(this.getRandom().nextDouble() < 0.1);
        this.setNose(this.getRandom().nextDouble() < 0.5);
        this.setEars(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new AttackGoal(this, 5));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasLollipop())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.swinging) {
            Vec3 direction = Vec3.directionFromRotation(this.getRotationVector()).scale(0.6);
            for (int i = 0; i < 5; i++) {
                Vec3 pos = this.position().add(direction.x + (this.getRandom().nextFloat() - 0.5f) * 0.7,
                        1.5 + (this.getRandom().nextFloat() - 0.5f) * 0.7, direction.z + (this.getRandom().nextFloat() - 0.5f) * 0.7);
                this.level().addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.25, 0, direction.z * 0.25);
            }
        }
    }

    public float getAnimationHeight(float partialTicks) {
        return Mth.cos((this.tickCount + this.heightOffset + partialTicks) / 3.5f);
    }

    public boolean hasLollipop() {
        return this.hasVariant(0);
    }

    private void setLollipop(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasNose() {
        return this.hasVariant(1);
    }

    private void setNose(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasEars() {
        return this.hasVariant(2);
    }

    private void setEars(boolean b) {
        this.setVariant(2, b);
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants")) {
            this.setLollipop(compound.getBoolean("hasLollipop"));
            this.setNose(compound.getBoolean("hasNose"));
            this.setEars(compound.getBoolean("hasEars"));
        }
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, false, false));
    }

    public static class AttackGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 5;

        protected final FamiliarEntity entity;
        private final float range;
        private int cooldown = MAX_COOLDOWN;

        public AttackGoal(FamiliarEntity entity, float range) {
            this.entity = entity;
            this.range = range;
        }

        @Override
        public boolean canUse() {
            return this.cooldown-- < 0 && this.entity.getFamiliarOwner() instanceof Player && !this.getNearbyEnemies().isEmpty();
        }

        private List<LivingEntity> getNearbyEnemies() {
            return FamiliarUtil.getOwnerEnemies(this.entity.getFamiliarOwner(), this.entity, this.range);
        }

        public void start() {
            List<LivingEntity> enemies = this.getNearbyEnemies();
            if (!enemies.isEmpty() && this.entity instanceof DevilFamiliarEntity)
                OccultismAdvancements.FAMILIAR.trigger(this.entity.getFamiliarOwner(), FamiliarTrigger.Type.DEVIL_FIRE);

            this.attack(enemies);
            this.entity.swing(InteractionHand.MAIN_HAND);
            this.cooldown = MAX_COOLDOWN;
        }

        protected void attack(List<LivingEntity> enemies) {
            for (Entity e : enemies) {
                e.hurt(this.entity.damageSources().playerAttack((Player) this.entity.getFamiliarOwner()), 4);
            }
        }

        public void stop() {
            this.cooldown = MAX_COOLDOWN;
        }
    }
}
