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

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.util.FamiliarUtil;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ShubNiggurathSpawnEntity extends CreatureEntity {

    private static final int EYE_COUNT = 4;
    private static final int LIFE_TICKS_MAX = 20 * 20;

    private int[] eyeBlinkTimers = new int[EYE_COUNT];
    private int lifeTicks;
    private UUID creatorId;

    public ShubNiggurathSpawnEntity(EntityType<? extends ShubNiggurathSpawnEntity> type, World worldIn) {
        super(type, worldIn);
        this.lifeTicks = LIFE_TICKS_MAX;
    }

    public ShubNiggurathSpawnEntity(World worldIn, LivingEntity creator) {
        this(OccultismEntities.SHUB_NIGGURATH_SPAWN.get(), worldIn);
        this.creatorId = creator.getUUID();
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MoveToTargetGoal(this));
        this.targetSelector.addGoal(0, new FindEnemiesGoal(this));
        this.targetSelector.addGoal(1, new FindCreatorGoal(this));
    }

    public boolean isBlinking(int eye) {
        return eyeBlinkTimers[eye] < 3;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            if (this.lifeTicks-- < 0)
                explode();

            if (this.lifeTicks % 10 == 0 && !FamiliarUtil.getOwnerEnemies(getCreatorOwner(), this, 10).isEmpty())
                explode();
        } else {
            for (int i = 0; i < EYE_COUNT; i++) {
                eyeBlinkTimers[i]--;
                if (eyeBlinkTimers[i] < 0)
                    eyeBlinkTimers[i] = this.getRandom().nextInt(100) + 50;
            }
        }
    }

    private void explode() {
        if (!this.isAlive())
            return;

        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        LivingEntity owner = getCreatorOwner();
        for (LivingEntity e : FamiliarUtil.getOwnerEnemies(owner, this, 10))
            e.hurt(owner == null ? DamageSource.GENERIC : DamageSource.mobAttack(owner), damage);

        kill();
    }

    private LivingEntity getCreator() {
        if (creatorId == null)
            return null;
        Entity creator = ((ServerWorld) level).getEntity(creatorId);
        if (!(creator instanceof LivingEntity))
            return null;
        return (LivingEntity) creator;
    }

    private LivingEntity getCreatorOwner() {
        LivingEntity creator = getCreator();
        if (creator instanceof IFamiliar)
            return ((IFamiliar) creator).getFamiliarOwner();
        return creator;
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);

        if (level.isClientSide)
            for (int i = 0; i < 30; i++)
                this.level.addParticle(new RedstoneParticleData(0.5f, 0, 0, 1), this.getRandomX(1), this.getRandomY(),
                        this.getRandomZ(1), 0, 1, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("lifeTicks", lifeTicks);
        if (creatorId != null)
            pCompound.putUUID("creatorId", creatorId);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("lifeTicks"))
            this.lifeTicks = pCompound.getInt("lifeTicks");
        if (pCompound.hasUUID("creatorId"))
            this.creatorId = pCompound.getUUID("creatorId");
    }

    private abstract static class FindTargetGoal extends TargetGoal {

        protected ShubNiggurathSpawnEntity entity;

        private FindTargetGoal(ShubNiggurathSpawnEntity entity) {
            super(entity, false);
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        protected abstract LivingEntity findTarget();

        @Override
        public void start() {
            LivingEntity target = findTarget();
            if (target != null)
                entity.setTarget(target);
            super.start();
        }

        @Override
        public boolean canUse() {
            return findTarget() != null;
        }
    }

    private static class FindCreatorGoal extends FindTargetGoal {

        private FindCreatorGoal(ShubNiggurathSpawnEntity entity) {
            super(entity);
        }

        protected LivingEntity findTarget() {
            return entity.getCreator();
        }
    }

    private static class FindEnemiesGoal extends FindTargetGoal {

        private FindEnemiesGoal(ShubNiggurathSpawnEntity entity) {
            super(entity);
        }

        protected LivingEntity findTarget() {
            List<LivingEntity> enemies = FamiliarUtil.getOwnerEnemies(entity.getCreatorOwner(), entity, 30);
            if (!enemies.isEmpty())
                return enemies.get(0);
            return null;
        }
    }

    private static class MoveToTargetGoal extends Goal {

        private ShubNiggurathSpawnEntity entity;
        private int timer;

        private MoveToTargetGoal(ShubNiggurathSpawnEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.entity.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void stop() {
            this.entity.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (this.timer-- < 0) {
                this.timer = 20;
                LivingEntity target = this.entity.getTarget();
                if (target != null)
                    this.entity.getNavigation().moveTo(target, 1);
            }
        }

    }
}
