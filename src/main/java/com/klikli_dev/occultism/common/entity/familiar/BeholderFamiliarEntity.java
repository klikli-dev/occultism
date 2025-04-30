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

import com.google.common.collect.ImmutableList;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.possessed.PossessedWardenEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageBeholderAttack;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BeholderFamiliarEntity extends ColoredFamiliarEntity {

    private static final float DEG_30 = FamiliarUtil.toRads(30);
    private static final int EAT_EFFECT_DURATION = 20 * 60 * 10;
    private final float heightOffset;
    private final Eye[] eyes = new Eye[]{new Eye(-0.2 + 0.07, 1.3, -0.2 + 0.07), new Eye(0.24 - 0.1, 1.3, -0.23 + 0.1),
            new Eye(0.28 - 0.1, 1.3, 0.23 - 0.07), new Eye(-0.15 + 0.06, 1.3, 0.2 - 0.09)};
    private Vec2 bigEyePos, bigEyePos0, bigEyeTarget;
    private int eatTimer = -1;
    private float mouthRot, actualMouthRot, actualMouthRot0;

    private static final EntityDataAccessor<Boolean> WARDEN_UPGRADE = SynchedEntityData.defineId(BeholderFamiliarEntity.class,
            EntityDataSerializers.BOOLEAN);

    public BeholderFamiliarEntity(EntityType<? extends BeholderFamiliarEntity> type, Level level) {
        super(type, level);
        this.bigEyePos = this.bigEyePos0 = this.bigEyeTarget = Vec2.ZERO;
        this.heightOffset = this.getRandom().nextFloat() * 5;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WARDEN_UPGRADE, false);
    }

    public boolean hasWardenUpgrade() {
        return this.entityData.get(WARDEN_UPGRADE);
    }

    private void setWardenUpgrade(boolean b) {
        this.entityData.set(WARDEN_UPGRADE, b);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setWardenUpgrade(compound.getBoolean("hasWardenUpgrade"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasWardenUpgrade", this.hasWardenUpgrade());
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (this.getRandom().nextDouble() >= 0.98)
            this.tickGlow(wearer);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {

    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new RayGoal(this));
        this.goalSelector.addGoal(3, new EatGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            for (Eye eye : this.eyes)
                eye.tick();

            this.bigEyePos0 = new Vec2(this.bigEyePos.x, this.bigEyePos.y);
            if (Math.abs(this.bigEyePos.x - this.bigEyeTarget.x) + Math.abs(this.bigEyePos.y - this.bigEyeTarget.y) < 0.05) {
                this.bigEyeTarget = new Vec2((this.getRandom().nextFloat() - 0.5f) * 2.9f,
                        (this.getRandom().nextFloat() - 0.5f) * 1.9f);
            }
            this.bigEyePos = this.lerpVec(0.1f, this.bigEyePos, this.bigEyeTarget);

            this.actualMouthRot0 = this.actualMouthRot;
            this.mouthRot = Mth.cos(this.tickCount * 0.1f) * FamiliarUtil.toRads(10) + FamiliarUtil.toRads(27);
            if (this.eatTimer != -1) {
                this.eatTimer++;
                if (this.eatTimer == 60)
                    this.eatTimer = -1;

                if (this.eatTimer < 50) {
                    if (this.eatTimer % 5 == 0)
                        this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EAT,
                                SoundSource.HOSTILE, this.getSoundVolume(), this.getVoicePitch(), false);

                    this.mouthRot = Mth.sin(this.tickCount) * FamiliarUtil.toRads(50) + FamiliarUtil.toRads(20);
                }
                if (this.eatTimer == 51)
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.HOSTILE, this.getSoundVolume(), 0.2f, false);
            }
            this.actualMouthRot = Mth.lerp(0.2f, this.actualMouthRot, this.mouthRot);
        } else {
            if (this.getRandom().nextDouble() >= 0.98)
                this.tickGlow(this.getFamiliarOwner());
        }
        this.yBodyRot = this.yRotO;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setColor();
        this.setBeard(this.getRandom().nextBoolean());
        this.setSpikes(this.getRandom().nextBoolean());
        this.setTongue(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    public float getAnimationHeight(float partialTicks) {
        if (this.isSitting())
            return -0.44f;

        return 1 + Mth.cos((this.tickCount + this.heightOffset + partialTicks) / 5f) * 0.1f;
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasTongue())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    private void tickGlow(LivingEntity owner) {
        if (!this.isEffectEnabled(owner))
            return;

        List<LivingEntity> nearby = owner.level().getEntitiesOfClass(LivingEntity.class,
                owner.getBoundingBox().inflate(10),
                e -> !(e instanceof Player) && e != owner && e != this && !e.hasEffect(MobEffects.GLOWING));
        if (nearby.isEmpty())
            return;

        LivingEntity mob = nearby.get(this.getRandom().nextInt(nearby.size()));
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 60, 0, false, false));

        if (this.hasBlacksmithUpgrade() && (mob instanceof Warden || mob instanceof PossessedWardenEntity)){
            this.setWardenUpgrade(true);
        }
    }

    public boolean hasBeard() {
        return this.hasVariant(0);
    }

    private void setBeard(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasSpikes() {
        return this.hasVariant(1);
    }

    private void setSpikes(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasTongue() {
        return this.hasVariant(2);
    }

    private void setTongue(boolean b) {
        this.setVariant(2, b);
    }

    public float getMouthRot(float partialTicks) {
        return Mth.lerp(partialTicks, this.actualMouthRot0, this.actualMouthRot);
    }

    public float getEatTimer(float partialTicks) {
        return (this.eatTimer + partialTicks) / 60;
    }

    public boolean isEating() {
        return this.eatTimer != -1;
    }

    public Vec2 getBigEyePos(float partialTicks) {
        return this.lerpVec(partialTicks, this.bigEyePos0, this.bigEyePos);
    }

    public Vec2 getEyeRot(float partialTicks, int i) {
        return this.eyes[i].getEyeRot(partialTicks);
    }

    private Vec3 lerpVec(float value, Vec3 start, Vec3 stop) {
        return new Vec3(Mth.lerp(value, start.x, stop.x), Mth.lerp(value, start.y, stop.y),
                Mth.lerp(value, start.z, stop.z));
    }

    private Vec2 lerpVec(float value, Vec2 start, Vec2 stop) {
        return new Vec2(Mth.lerp(value, start.x, stop.x), Mth.lerp(value, start.y, stop.y));
    }

    @Override
    public void swing(InteractionHand pHand) {
        super.swing(pHand);
        this.eatTimer = 0;
    }

    // Client method
    public void shootRay(List<Integer> targetIds) {
        List<Eye> eyeList = new ArrayList<>();
        Collections.addAll(eyeList, this.eyes);

        for (int id : targetIds) {
            if (eyeList.isEmpty())
                break;

            int index = this.getRandom().nextInt(eyeList.size());
            Eye e = eyeList.get(index);
            eyeList.remove(index);
            e.prepareShot(id);
        }
    }

    private static class RayGoal extends Goal {

        private static final int MAX_COOLDOWN = 20;

        protected final BeholderFamiliarEntity entity;
        private int cooldown = MAX_COOLDOWN;
        private int attackTimer;
        private List<Integer> targetIds;

        private RayGoal(BeholderFamiliarEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return this.entity.getFamiliarOwner() instanceof Player;
        }

        public void stop() {
            this.cooldown = MAX_COOLDOWN;
            this.targetIds = null;
            this.attackTimer = 0;
        }

        @Override
        public void tick() {
            List<Integer> enemies = this.getNearbyEnemies();
            if (this.cooldown-- < 0 && !enemies.isEmpty()) {
                this.targetIds = enemies;
                this.cooldown = MAX_COOLDOWN;
                this.attackTimer = 23;
                Networking.sendToTracking(this.entity,
                        new MessageBeholderAttack(this.entity.getId(), this.targetIds));
            }

            if (this.targetIds != null && this.attackTimer-- < 0) {
                this.attack();
                this.targetIds = null;
            }
        }

        private List<Integer> getNearbyEnemies() {
            List<Integer> enemies = new ArrayList<>();
            for (Entity e : FamiliarUtil.getOwnerEnemies(this.entity.getFamiliarOwner(), this.entity, 100)) {
                enemies.add(e.getId());
            }
            return enemies;
        }

        protected void attack() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.BEHOLDER_RAY);

            for (int id : this.targetIds) {
                Entity e = this.entity.level().getEntity(id);
                float damage = 9;
                if (this.entity.hasEffect(MobEffects.DAMAGE_BOOST))
                    damage *= this.entity.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() + 2;

                if (e == null)
                    continue;

                if (owner instanceof Player player) {
                    e.hurt(this.entity.damageSources().playerAttack(player), damage);
                } else {
                    e.hurt(this.entity.damageSources().mobAttack(this.entity), damage);
                }
            }
        }
    }

    private static class EatGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 30;

        protected final BeholderFamiliarEntity entity;
        private int cooldown;

        private EatGoal(BeholderFamiliarEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return !this.entity.isSitting() && this.cooldown-- < 0;
        }

        @Override
        public void start() {
            this.cooldown = MAX_COOLDOWN;
            List<ShubNiggurathSpawnEntity> foods = this.entity.level().getEntitiesOfClass(ShubNiggurathSpawnEntity.class,
                    this.entity.getBoundingBox().inflate(3), Entity::isAlive);

            LivingEntity owner = this.entity.getFamiliarOwner();

            if (!foods.isEmpty() && this.entity.isEffectEnabled(owner)) {
                Entity food = foods.get(this.entity.getRandom().nextInt(foods.size()));
                food.remove(RemovalReason.DISCARDED);
                this.entity.swing(InteractionHand.MAIN_HAND);
                this.entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, EAT_EFFECT_DURATION, 0, false, false));
                this.entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EAT_EFFECT_DURATION, 0, false, false));

                OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.BEHOLDER_EAT);
            }
        }

        @Override
        public void stop() {
            this.cooldown = MAX_COOLDOWN;
        }
    }

    private class Eye {
        private final Vec3 pos;
        private Vec3 lookPos0;
        private Vec3 lookPos;
        private EyeTarget eyeTarget;
        private int shotTimer;

        private Eye(double x, double y, double z) {
            this.pos = new Vec3(x, y, z);
            this.init();
        }

        public void prepareShot(int id) {
            this.shotTimer = 20;
            this.eyeTarget = new EntityEyeTarget(id);
        }

        private void init() {
            this.selectEyeTarget();
            Vec3 targetPos = this.eyeTarget.getEyeTarget();
            if (targetPos != null)
                this.lookPos = targetPos;
            else
                this.lookPos = Vec3.ZERO;
            this.lookPos0 = this.lookPos;
        }

        private void selectEyeTarget() {
            List<LivingEntity> entities = BeholderFamiliarEntity.this.level().getEntitiesOfClass(LivingEntity.class, BeholderFamiliarEntity.this.getBoundingBox().inflate(7),
                    e -> e != BeholderFamiliarEntity.this);
            if (entities.isEmpty()) {
                this.eyeTarget = new PositionEyeTarget(Vec3.ZERO.add(5, 0, 0).yRot(BeholderFamiliarEntity.this.getRandom().nextFloat() * 360)
                        .xRot(BeholderFamiliarEntity.this.getRandom().nextFloat() * 360).add(BeholderFamiliarEntity.this.position()));
            } else {
                this.eyeTarget = new EntityEyeTarget(entities.get(BeholderFamiliarEntity.this.getRandom().nextInt(entities.size())).getId());
            }
        }

        private boolean needNewEyeTarget() {
            Vec3 targetPos = this.eyeTarget.getEyeTarget();
            return targetPos == null || targetPos.distanceToSqr(BeholderFamiliarEntity.this.position()) > 15 * 15
                    || BeholderFamiliarEntity.this.getRandom().nextDouble() < 0.01;
        }

        private void tick() {
            this.lookPos0 = this.lookPos;
            if (this.needNewEyeTarget() && this.shotTimer == 0)
                this.selectEyeTarget();

            Vec3 targetPos = this.eyeTarget.getEyeTarget();
            if (targetPos != null)
                this.lookPos = BeholderFamiliarEntity.this.lerpVec(0.2f, this.lookPos, targetPos);

            if (this.shotTimer > 0) {
                this.shotTimer--;
                if (this.shotTimer < 5)
                    this.shoot();
            }
        }

        private Vec2 getEyeRot(float partialTicks) {
            float bodyRot = FamiliarUtil.toRads(Mth.rotLerp(partialTicks, BeholderFamiliarEntity.this.yBodyRotO, BeholderFamiliarEntity.this.yBodyRot));

            Vec3 direction = BeholderFamiliarEntity.this.getPosition(partialTicks).add(0, BeholderFamiliarEntity.this.getAnimationHeight(partialTicks), 0)
                    .add(this.pos.yRot(-bodyRot)).vectorTo(BeholderFamiliarEntity.this.lerpVec(partialTicks, this.lookPos0, this.lookPos));
            double yRot = Mth.atan2(direction.z, direction.x) + FamiliarUtil.toRads(-90) - bodyRot;
            double xRot = direction.normalize().y;
            return new Vec2((float) (DEG_30 - DEG_30 * xRot), (float) yRot);
        }

        private void shoot() {
            float bodyRot = FamiliarUtil.toRads(Mth.rotLerp(1, BeholderFamiliarEntity.this.yBodyRotO, BeholderFamiliarEntity.this.yBodyRot));

            Vec2 rot = this.getEyeRot(1);
            Vec3 increment = new Vec3(0, 0.15, 0);
            Vec3 end = this.eyeTarget.getEyeTarget();
            if (end == null)
                return;
            Vec3 start = BeholderFamiliarEntity.this.position().add(new Vec3(this.pos.x, 0.9 + BeholderFamiliarEntity.this.getAnimationHeight(1), this.pos.z).yRot(-bodyRot))
                    .add(increment);
            for (int i = 0; i < 3; i++) {
                increment = increment.xRot(-rot.x);
                start = start.add(increment.yRot(-rot.y - bodyRot));
            }

            Vec3 direction = start.vectorTo(end).normalize();
            AABB endBox = new AABB(end, end).inflate(0.25);
            for (int i = 0; i < 150; i++) {
                Vec3 particlePos = start.add(direction.scale(i * 0.1));
                BeholderFamiliarEntity.this.level().addParticle(new DustParticleOptions(new Vector3f(BeholderFamiliarEntity.this.getRed(), BeholderFamiliarEntity.this.getBlue(), BeholderFamiliarEntity.this.getGreen()), 1), particlePos.x,
                        particlePos.y, particlePos.z, 0, 0, 0);
                if (endBox.intersects(new AABB(particlePos, particlePos).inflate(0.25)))
                    break;
            }
        }

        private abstract class EyeTarget {
            abstract protected Vec3 getEyeTarget();
        }

        private class EntityEyeTarget extends EyeTarget {
            int entityId;

            private EntityEyeTarget(int entityId) {
                this.entityId = entityId;
            }

            @Override
            protected Vec3 getEyeTarget() {
                Entity e = BeholderFamiliarEntity.this.level().getEntity(this.entityId);
                return e == null ? null : e.getEyePosition(0);
            }
        }

        private class PositionEyeTarget extends EyeTarget {
            Vec3 position;

            private PositionEyeTarget(Vec3 position) {
                this.position = position;
            }

            @Override
            protected Vec3 getEyeTarget() {
                return this.position;
            }
        }
    }
}
