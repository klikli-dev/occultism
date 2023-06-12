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
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class ShubNiggurathSpawnEntity extends PathfinderMob {

    private static final int EYE_COUNT = 4;
    private static final int LIFE_TICKS_MAX = 20 * 20;

    private final int[] eyeBlinkTimers = new int[EYE_COUNT];
    private int lifeTicks;
    private UUID creatorId;

    public ShubNiggurathSpawnEntity(EntityType<? extends ShubNiggurathSpawnEntity> type, Level level) {
        super(type, level);
        this.lifeTicks = LIFE_TICKS_MAX;
    }

    public ShubNiggurathSpawnEntity(Level level, LivingEntity creator) {
        this(OccultismEntities.SHUB_NIGGURATH_SPAWN.get(), level);
        this.creatorId = creator.getUUID();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MoveToTargetGoal(this));
        this.targetSelector.addGoal(0, new FindEnemiesGoal(this));
        this.targetSelector.addGoal(1, new FindCreatorGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.lifeTicks-- < 0)
                this.explode();

            if (this.lifeTicks % 10 == 0 && !FamiliarUtil.getOwnerEnemies(this.getCreatorOwner(), this, 10).isEmpty())
                this.explode();
        } else {
            for (int i = 0; i < EYE_COUNT; i++) {
                this.eyeBlinkTimers[i]--;
                if (this.eyeBlinkTimers[i] < 0)
                    this.eyeBlinkTimers[i] = this.getRandom().nextInt(100) + 50;
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("lifeTicks", this.lifeTicks);
        if (this.creatorId != null)
            pCompound.putUUID("creatorId", this.creatorId);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("lifeTicks"))
            this.lifeTicks = pCompound.getInt("lifeTicks");
        if (pCompound.hasUUID("creatorId"))
            this.creatorId = pCompound.getUUID("creatorId");
    }

    public boolean isBlinking(int eye) {
        return this.eyeBlinkTimers[eye] < 3;
    }

    private void explode() {
        if (!this.isAlive())
            return;

        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        LivingEntity owner = this.getCreatorOwner();
        List<LivingEntity> enemies = FamiliarUtil.getOwnerEnemies(owner, this, 10);
        for (LivingEntity e : enemies)
            e.hurt(owner == null ? this.damageSources().generic() : this.damageSources().mobAttack(owner), damage);

        if (!enemies.isEmpty())
            OccultismAdvancements.FAMILIAR.trigger(this.getCreatorOwner(), FamiliarTrigger.Type.SHUB_NIGGURATH_SPAWN);

        this.kill();
    }

    private LivingEntity getCreator() {
        if (this.creatorId == null)
            return null;
        Entity creator = ((ServerLevel) this.level()).getEntity(this.creatorId);
        if (!(creator instanceof LivingEntity))
            return null;
        return (LivingEntity) creator;
    }

    private LivingEntity getCreatorOwner() {
        LivingEntity creator = this.getCreator();
        if (creator instanceof IFamiliar)
            return ((IFamiliar) creator).getFamiliarOwner();
        return creator;
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);

        if (this.level().isClientSide)
            for (int i = 0; i < 30; i++)
                this.level().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0, 0), 1), this.getRandomX(1), this.getRandomY(),
                        this.getRandomZ(1), 0, 1, 0);
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
            LivingEntity target = this.findTarget();
            if (target != null)
                this.entity.setTarget(target);
            super.start();
        }

        @Override
        public boolean canUse() {
            return this.findTarget() != null;
        }
    }

    private static class FindCreatorGoal extends FindTargetGoal {

        private FindCreatorGoal(ShubNiggurathSpawnEntity entity) {
            super(entity);
        }

        protected LivingEntity findTarget() {
            return this.entity.getCreator();
        }
    }

    private static class FindEnemiesGoal extends FindTargetGoal {

        private FindEnemiesGoal(ShubNiggurathSpawnEntity entity) {
            super(entity);
        }

        protected LivingEntity findTarget() {
            List<LivingEntity> enemies = FamiliarUtil.getOwnerEnemies(this.entity.getCreatorOwner(), this.entity, 30);
            if (!enemies.isEmpty())
                return enemies.get(0);
            return null;
        }
    }

    private static class MoveToTargetGoal extends Goal {

        private final ShubNiggurathSpawnEntity entity;
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
