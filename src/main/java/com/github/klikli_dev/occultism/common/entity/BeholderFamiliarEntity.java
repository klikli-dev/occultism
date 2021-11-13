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
import java.util.List;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.network.MessageBeholderAttack;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BeholderFamiliarEntity extends ColoredFamiliarEntity {

    private static final float DEG_30 = FamiliarUtil.toRads(30);
    private static final int EAT_EFFECT_DURATION = 20 * 60 * 10;

    private Eye[] eyes = new Eye[] { new Eye(-0.2 + 0.07, 1.3, -0.2 + 0.07), new Eye(0.24 - 0.1, 1.3, -0.23 + 0.1),
            new Eye(0.28 - 0.1, 1.3, 0.23 - 0.07), new Eye(-0.15 + 0.06, 1.3, 0.2 - 0.09) };

    private Vector2f bigEyePos, bigEyePos0, bigEyeTarget;
    private final float heightOffset;
    private int eatTimer = -1;
    private float mouthRot, actualMouthRot, actualMouthRot0;

    public BeholderFamiliarEntity(EntityType<? extends BeholderFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.bigEyePos = this.bigEyePos0 = this.bigEyeTarget = Vector2f.ZERO;
        this.heightOffset = this.getRandom().nextFloat() * 5;
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new RayGoal(this));
        this.goalSelector.addGoal(3, new EatGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    public float getAnimationHeight(float partialTicks) {
        if (this.isSitting())
            return -0.44f;

        return MathHelper.cos((this.tickCount + this.heightOffset + partialTicks) / 5f) * 0.1f;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason,
            ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
        this.setColor();
        this.setBeard(this.getRandom().nextBoolean());
        this.setSpikes(this.getRandom().nextBoolean());
        this.setTongue(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasTongue())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    private void tickGlow(LivingEntity owner) {
        if (!isEffectEnabled(owner))
            return;

        List<LivingEntity> nearby = owner.level.getEntitiesOfClass(LivingEntity.class,
                owner.getBoundingBox().inflate(10),
                e -> !(e instanceof PlayerEntity) && e != owner && e != this && !e.hasEffect(Effects.GLOWING));
        if (nearby.isEmpty())
            return;

        nearby.get(getRandom().nextInt(nearby.size()))
                .addEffect(new EffectInstance(Effects.GLOWING, 20 * 60, 0, false, false));
    }
    
    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
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

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            for (Eye eye : eyes)
                eye.tick();

            this.bigEyePos0 = new Vector2f(this.bigEyePos.x, this.bigEyePos.y);
            if (Math.abs(bigEyePos.x - bigEyeTarget.x) + Math.abs(bigEyePos.y - bigEyeTarget.y) < 0.05) {
                bigEyeTarget = new Vector2f((getRandom().nextFloat() - 0.5f) * 2.9f,
                        (getRandom().nextFloat() - 0.5f) * 1.9f);
            }
            this.bigEyePos = lerpVec(0.1f, this.bigEyePos, this.bigEyeTarget);

            this.actualMouthRot0 = this.actualMouthRot;
            this.mouthRot = MathHelper.cos(tickCount * 0.1f) * FamiliarUtil.toRads(10) + FamiliarUtil.toRads(27);
            if (this.eatTimer != -1) {
                this.eatTimer++;
                if (this.eatTimer == 60)
                    this.eatTimer = -1;

                if (this.eatTimer < 50) {
                    if (this.eatTimer % 5 == 0)
                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.GENERIC_EAT,
                                SoundCategory.HOSTILE, getSoundVolume(), getVoicePitch(), false);

                    this.mouthRot = MathHelper.sin(tickCount) * FamiliarUtil.toRads(50) + FamiliarUtil.toRads(20);
                }
                if (this.eatTimer == 51)
                    this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE,
                            SoundCategory.HOSTILE, getSoundVolume(), 0.2f, false);
            }
            this.actualMouthRot = MathHelper.lerp(0.2f, this.actualMouthRot, this.mouthRot);
        } else {
            if (getRandom().nextDouble() >= 0.98)
                tickGlow(getFamiliarOwner());
        }
        this.yBodyRot = this.yRot;
    }

    public float getMouthRot(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.actualMouthRot0, this.actualMouthRot);
    }

    public float getEatTimer(float partialTicks) {
        return (this.eatTimer + partialTicks) / 60;
    }

    public boolean isEating() {
        return this.eatTimer != -1;
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (getRandom().nextDouble() >= 0.98)
            tickGlow(wearer);
    }

    public Vector2f getBigEyePos(float partialTicks) {
        return lerpVec(partialTicks, this.bigEyePos0, this.bigEyePos);
    }

    public Vector2f getEyeRot(float partialTicks, int i) {
        return eyes[i].getEyeRot(partialTicks);
    }

    private Vector3d lerpVec(float value, Vector3d start, Vector3d stop) {
        return new Vector3d(MathHelper.lerp(value, start.x, stop.x), MathHelper.lerp(value, start.y, stop.y),
                MathHelper.lerp(value, start.z, stop.z));
    }

    private Vector2f lerpVec(float value, Vector2f start, Vector2f stop) {
        return new Vector2f(MathHelper.lerp(value, start.x, stop.x), MathHelper.lerp(value, start.y, stop.y));
    }

    @Override
    public void swing(Hand pHand) {
        super.swing(pHand);
        this.eatTimer = 0;
    }

    // Client method
    public void shootRay(List<Integer> targetIds) {
        List<Eye> eyeList = new ArrayList<>();
        for (Eye e : eyes)
            eyeList.add(e);

        for (int id : targetIds) {
            if (eyeList.isEmpty())
                break;

            int index = this.getRandom().nextInt(eyeList.size());
            Eye e = eyeList.get(index);
            eyeList.remove(index);
            e.prepareShot(id);
        }
    }

    private class Eye {
        private Vector3d lookPos0, lookPos, pos;
        private EyeTarget eyeTarget;
        private int shotTimer;

        private Eye(double x, double y, double z) {
            this.pos = new Vector3d(x, y, z);
            this.init();
        }

        public void prepareShot(int id) {
            shotTimer = 20;
            eyeTarget = new EntityEyeTarget(id);
        }

        private void init() {
            this.selectEyeTarget();
            Vector3d targetPos = eyeTarget.getEyeTarget();
            if (targetPos != null)
                this.lookPos = targetPos;
            else
                this.lookPos = Vector3d.ZERO;
            this.lookPos0 = this.lookPos;
        }

        private void selectEyeTarget() {
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(7),
                    e -> e != BeholderFamiliarEntity.this);
            if (entities.isEmpty()) {
                eyeTarget = new PositionEyeTarget(Vector3d.ZERO.add(5, 0, 0).yRot(getRandom().nextFloat() * 360)
                        .xRot(getRandom().nextFloat() * 360).add(position()));
            } else {
                eyeTarget = new EntityEyeTarget(entities.get(getRandom().nextInt(entities.size())).getId());
            }
        }

        private boolean needNewEyeTarget() {
            Vector3d targetPos = eyeTarget.getEyeTarget();
            return targetPos == null || targetPos.distanceToSqr(position()) > 15 * 15
                    || getRandom().nextDouble() < 0.01;
        }

        private void tick() {
            this.lookPos0 = this.lookPos;
            if (needNewEyeTarget() && shotTimer == 0)
                selectEyeTarget();

            Vector3d targetPos = eyeTarget.getEyeTarget();
            if (targetPos != null)
                this.lookPos = lerpVec(0.2f, this.lookPos, targetPos);

            if (shotTimer > 0) {
                shotTimer--;
                if (shotTimer < 5)
                    shoot();
            }
        }

        private Vector2f getEyeRot(float partialTicks) {
            float bodyRot = FamiliarUtil.toRads(MathHelper.rotLerp(partialTicks, yBodyRotO, yBodyRot));

            Vector3d direction = getPosition(partialTicks).add(0, getAnimationHeight(partialTicks), 0).add(pos.yRot(-bodyRot))
                    .vectorTo(lerpVec(partialTicks, lookPos0, lookPos));
            double yRot = MathHelper.atan2(direction.z, direction.x) + FamiliarUtil.toRads(-90) - bodyRot;
            double xRot = direction.normalize().y;
            return new Vector2f((float) (DEG_30 - DEG_30 * xRot), (float) yRot);
        }

        private void shoot() {
            float bodyRot = FamiliarUtil.toRads(MathHelper.rotLerp(1, yBodyRotO, yBodyRot));

            Vector2f rot = getEyeRot(1);
            Vector3d increment = new Vector3d(0, 0.15, 0);
            Vector3d end = eyeTarget.getEyeTarget();
            if (end == null)
                return;
            Vector3d start = position().add(new Vector3d(pos.x, 0.9 + getAnimationHeight(1), pos.z).yRot(-bodyRot))
                    .add(increment);
            for (int i = 0; i < 3; i++) {
                increment = increment.xRot(-rot.x);
                start = start.add(increment.yRot(-rot.y - bodyRot));
            }

            Vector3d direction = start.vectorTo(end).normalize();
            AxisAlignedBB endBox = new AxisAlignedBB(end, end).inflate(0.25);
            for (int i = 0; i < 150; i++) {
                Vector3d particlePos = start.add(direction.scale(i * 0.1));
                level.addParticle(new RedstoneParticleData(getRed(), getBlue(), getGreen(), 1), particlePos.x,
                        particlePos.y, particlePos.z, 0, 0, 0);
                if (endBox.intersects(new AxisAlignedBB(particlePos, particlePos).inflate(0.25)))
                    break;
            }
        }

        private abstract class EyeTarget {
            abstract protected Vector3d getEyeTarget();
        }

        private class EntityEyeTarget extends EyeTarget {
            int entityId;

            private EntityEyeTarget(int entityId) {
                this.entityId = entityId;
            }

            @Override
            protected Vector3d getEyeTarget() {
                Entity e = level.getEntity(entityId);
                return e == null ? null : e.getEyePosition(0);
            }
        }

        private class PositionEyeTarget extends EyeTarget {
            Vector3d position;

            private PositionEyeTarget(Vector3d position) {
                this.position = position;
            }

            @Override
            protected Vector3d getEyeTarget() {
                return position;
            }
        }
    }

    private static class RayGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 5;

        protected final BeholderFamiliarEntity entity;
        private int cooldown = MAX_COOLDOWN;
        private int attackTimer;
        private List<Integer> targetIds;

        private RayGoal(BeholderFamiliarEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return this.entity.getFamiliarOwner() instanceof PlayerEntity;
        }

        private List<Integer> getNearbyEnemies() {
            List<Integer> enemies = new ArrayList<>();
            for (Entity e : FamiliarUtil.getOwnerEnemies(this.entity.getFamiliarOwner(), this.entity, 100)) {
                enemies.add(e.getId());
            }
            return enemies;
        }

        protected void attack() {
            for (int id : targetIds) {
                Entity e = entity.level.getEntity(id);
                float damage = 6;
                if (entity.hasEffect(Effects.DAMAGE_BOOST))
                    damage *= entity.getEffect(Effects.DAMAGE_BOOST).getAmplifier() + 2;
                System.out.println(damage);
                if (e != null)
                    e.hurt(DamageSource.playerAttack((PlayerEntity) this.entity.getFamiliarOwner()), damage);
            }
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
                attackTimer = 23;
                OccultismPackets.sendToTracking(this.entity,
                        new MessageBeholderAttack(this.entity.getId(), this.targetIds));
            }

            if (targetIds != null && attackTimer-- < 0) {
                attack();
                targetIds = null;
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
            return !entity.isSitting() && cooldown-- < 0;
        }

        @Override
        public void stop() {
            this.cooldown = MAX_COOLDOWN;
        }

        @Override
        public void start() {
            this.cooldown = MAX_COOLDOWN;
            List<Entity> foods = entity.level.getEntitiesOfClass(ShubNiggurathSpawnEntity.class,
                    entity.getBoundingBox().inflate(3), e -> e.isAlive());

            if (!foods.isEmpty() && entity.isEffectEnabled(entity.getFamiliarOwner())) {
                Entity food = foods.get(entity.getRandom().nextInt(foods.size()));
                food.remove();
                this.entity.swing(Hand.MAIN_HAND);
                entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, EAT_EFFECT_DURATION, 0, false, false));
                entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, EAT_EFFECT_DURATION, 0, false, false));
            }
        }
    }
}
