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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.network.MessageFairySupport;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismParticles;
import com.github.klikli_dev.occultism.util.FamiliarUtil;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FairyFamiliarEntity extends FamiliarEntity implements IFlyingAnimal {

    private static final DataParameter<Integer> MAGIC_TARGET = EntityDataManager.defineId(FairyFamiliarEntity.class,
            DataSerializers.INT);

    private static final float ANIMATION_HEIGHT_SPEED = 0.2f;

    private int saveCooldown = 0;
    private int supportAnim;

    public FairyFamiliarEntity(EntityType<? extends FairyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.WALKABLE, -1);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().add(Attributes.FLYING_SPEED, 0.4).add(Attributes.MAX_HEALTH, 18);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MAGIC_TARGET, -1);
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
        this.goalSelector.addGoal(3, new MagicGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
        this.goalSelector.addGoal(8, new SupportGoal(this));

        this.targetSelector.addGoal(0, new SetAttackTargetGoal(this));
    }

    public float getSupportAnim(float partialTicks) {
        if (supportAnim == 0)
            return 0;
        return (20 - supportAnim + partialTicks) / 20;
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

        if (saveCooldown > 0)
            saveCooldown--;

        partyParticle();

        if (!level.isClientSide && this.getTarget() == null)
            this.setMagicTarget(null);

        if (level.isClientSide && this.hasMagicTarget()) {
            this.yBodyRot = 0;
            this.yBodyRotO = 0;

            magicParticle();
        }

        if (supportAnim > 0)
            supportAnim--;
    }

    private IParticleData createParticle() {
        return FamiliarUtil.isChristmas() ? OccultismParticles.SNOWFLAKE.get()
                : new RedstoneParticleData(0.9f, 0.9f, 0.5f, 1);
    }

    private void magicParticle() {
        Vector3d pos = getMagicPosition(1);
        level.addParticle(createParticle(), pos.x, pos.y + 1, pos.z, 0, 0, 0);
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (saveCooldown > 0)
            saveCooldown--;
    }

    private void partyParticle() {
        if (!level.isClientSide || !this.isPartying() || this.tickCount % 2 != 0)
            return;

        Vector3d right = Vector3d.directionFromRotation(0, this.yBodyRot).yRot(FamiliarUtil.toRads(-90));
        Vector3d armVector = new Vector3d(0, -0.4, 0).xRot(getPartyArmRotX(0))
                .yRot(-getPartyArmRotY(0) + FamiliarUtil.toRads(-yBodyRot + 180));
        Vector3d pos = position().add(right.scale(0.2 * (this.isLeftHanded() ? -1 : 1)))
                .add(0, 0.7 + getAnimationHeight(0), 0).add(armVector);

        level.addParticle(createParticle(), pos.x, pos.y, pos.z, 0, 0, 0);
    }

    public Vector2f getMagicRadiusAngle(float partialTicks) {
        Entity target = getMagicTarget();
        if (target == null)
            return Vector2f.ZERO;
        float radius = target.getBbWidth() * 1.2f;
        float angle = (tickCount + partialTicks) * 0.1f;
        return new Vector2f(radius, angle);
    }

    public Vector3d getMagicPosition(float partialTicks) {
        Entity target = getMagicTarget();
        if (target == null)
            return null;

        Vector3d targetPos = target.getPosition(partialTicks).add(0, target.getBbHeight() / 2, 0);
        Vector2f radiusAngle = getMagicRadiusAngle(partialTicks);
        Vector3d offset = new Vector3d(MathHelper.cos(radiusAngle.y) * radiusAngle.x, 0,
                MathHelper.sin(radiusAngle.y) * radiusAngle.x);
        return targetPos.add(offset);
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

    private void setMagicTarget(Entity entity) {
        int id = entity == null ? -1 : entity.getId();
        this.entityData.set(MAGIC_TARGET, id);
    }

    public Entity getMagicTarget() {
        int id = this.entityData.get(MAGIC_TARGET);
        if (id < 0)
            return null;
        return level.getEntity(id);
    }

    public boolean hasMagicTarget() {
        return getMagicTarget() != null;
    }

    public boolean saveFamiliar(IFamiliar familiar) {
        if (saveCooldown > 0 || isSitting())
            return false;

        saveCooldown = 20 * 20;
        if (!familiar.getFamiliarEntity().level.isClientSide)
            OccultismPackets.sendToTracking(this,
                    new MessageFairySupport(this.getId(), familiar.getFamiliarEntity().getId()));
        return true;
    }

    @Override
    public void push(Entity pEntity) {
        if (hasMagicTarget())
            return;
        super.push(pEntity);
    }

    @Override
    protected void doPush(Entity pEntity) {
        if (hasMagicTarget())
            return;
        super.doPush(pEntity);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!pSource.isBypassInvul() && getMagicTarget() != null)
            return false;

        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isPickable() {
        return !hasMagicTarget();
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

    public void startSupportAnimation() {
        this.supportAnim = 20;
    }

    private static class SupportGoal extends Goal {

        private static final int DURATION = 20 * 10;

        private FairyFamiliarEntity fairy;
        private Map<UUID, Integer> cooldowns;

        private SupportGoal(FairyFamiliarEntity fairy) {
            this.fairy = fairy;
            cooldowns = new HashMap<>();
        }

        @Override
        public boolean canUse() {
            return !fairy.isSitting() && fairy.getFamiliarEntity() != null;
        }

        @Override
        public void tick() {
            for (Entry<UUID, Integer> entry : cooldowns.entrySet())
                if (entry.getValue() > 0)
                    cooldowns.put(entry.getKey(), entry.getValue() - 1);

            if (fairy.tickCount % 5 != 0)
                return;

            LivingEntity owner = fairy.getFamiliarOwner();

            if (owner == null)
                return;

            List<MobEntity> familiars = fairy.level.getEntitiesOfClass(MobEntity.class,
                    fairy.getBoundingBox().inflate(10),
                    e -> e != fairy && e instanceof IFamiliar && ((IFamiliar) e).getFamiliarOwner() == owner);

            for (MobEntity familiar : familiars) {
                UUID id = familiar.getUUID();
                if (!cooldowns.containsKey(id))
                    cooldowns.put(id, 0);

                if (cooldowns.get(id) == 0) {
                    boolean gaveSupport = false;
                    if (familiar.isOnFire()
                            && familiar.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, DURATION)))
                        gaveSupport = true;
                    if (familiar.isInWater()
                            && familiar.addEffect(new EffectInstance(Effects.WATER_BREATHING, DURATION)))
                        gaveSupport = true;
                    if (!familiar.isNoGravity() && familiar.fallDistance > 3
                            && familiar.addEffect(new EffectInstance(Effects.SLOW_FALLING, DURATION)))
                        gaveSupport = true;
                    if (familiar.getNavigation().isInProgress()
                            && familiar.getNavigation().getTargetPos().distSqr(familiar.blockPosition()) > 100
                            && familiar.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, DURATION)))
                        gaveSupport = true;
                    if (familiar.getLastHurtByMob() != null
                            && familiar.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, DURATION)))
                        gaveSupport = true;
                    if (gaveSupport) {
                        OccultismPackets.sendToTracking(fairy,
                                new MessageFairySupport(fairy.getId(), familiar.getId()));
                        cooldowns.put(id, DURATION);
                    }
                }
            }
        }

    }

    private static class MagicGoal extends MeleeAttackGoal {

        private FairyFamiliarEntity fairy;
        private int attackTimer;

        public MagicGoal(FairyFamiliarEntity fairy) {
            super(fairy, 1.8, true);
            this.fairy = fairy;
        }

        @Override
        public void start() {
            super.start();
            attackTimer = 20;
        }

        @Override
        public void stop() {
            super.stop();
            fairy.setMagicTarget(null);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            attackTimer--;
            double reach = this.getAttackReachSqr(pEnemy);
            if (pDistToEnemySqr <= reach) {
                fairy.setMagicTarget(pEnemy);
                if (this.attackTimer <= 0) {
                    this.attackTimer = 20;
                    LivingEntity owner = fairy.getFamiliarOwner();
                    if (owner != null) {
                        pEnemy.hurt(DamageSource.mobAttack(owner), 1);
                        pEnemy.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 40, 1));
                        List<LivingEntity> allies = fairy.level.getEntitiesOfClass(LivingEntity.class,
                                fairy.getBoundingBox().inflate(7), e -> e != fairy && e instanceof IFamiliar
                                        && ((IFamiliar) e).getFamiliarOwner() == owner);
                        allies.add(owner);
                        for (LivingEntity ally : allies) {
                            ally.heal(1);
                            ((ServerWorld) fairy.level).sendParticles(ParticleTypes.HEART, ally.getX(),
                                    ally.getY() + ally.getBbHeight(), ally.getZ(), 1, 0, 0, 0, 1);
                        }
                    }
                }
            } else if (pDistToEnemySqr > reach * 3) {
                fairy.setMagicTarget(null);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return super.getAttackReachSqr(pAttackTarget) * 3;
        }

    }

    public static class SetAttackTargetGoal extends TargetGoal {

        private FamiliarEntity entity;
        private int timestamp;

        public SetAttackTargetGoal(FamiliarEntity entity) {
            super(entity, false);
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            LivingEntity owner = entity.getFamiliarOwner();
            if (owner == null)
                return false;

            if (owner.distanceToSqr(entity) >= 400)
                return false;

            if (timestamp == owner.getLastHurtMobTimestamp())
                return false;

            Entity target = owner.getLastHurtMob();
            if (target == null || target instanceof PlayerEntity || target instanceof IFamiliar)
                return false;

            return true;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity owner = entity.getFamiliarOwner();
            return super.canContinueToUse() && owner != null && owner.distanceToSqr(entity) < 400;
        }

        @Override
        public void start() {
            LivingEntity owner = entity.getFamiliarOwner();
            if (owner == null)
                return;

            entity.setTarget(owner.getLastHurtMob());
            timestamp = owner.getLastHurtMobTimestamp();
            super.start();
        }
    }
}
