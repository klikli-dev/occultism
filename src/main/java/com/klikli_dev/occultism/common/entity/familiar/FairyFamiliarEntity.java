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
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageFairySupport;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

public class FairyFamiliarEntity extends FamiliarEntity implements FlyingAnimal {

    private static final EntityDataAccessor<Integer> MAGIC_TARGET = SynchedEntityData.defineId(FairyFamiliarEntity.class,
            EntityDataSerializers.INT);

    private static final float ANIMATION_HEIGHT_SPEED = 0.2f;
    private static final double DEFAULT_ATTACK_REACH = Math.sqrt(2.04F) - 0.6F;
    private int saveCooldown = 0;
    private int supportAnim;
    protected static final int BREATH_INTERVAL = 20 * 3;
    protected long lastBreathTime;

    public FairyFamiliarEntity(EntityType<? extends FairyFamiliarEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.WALKABLE, -1);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.FLYING_SPEED, 0.4).add(Attributes.MAX_HEALTH, 18);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MAGIC_TARGET, -1);
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasFlower())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MagicGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
        this.goalSelector.addGoal(8, new SupportGoal(this));

        this.targetSelector.addGoal(0, new SetAttackTargetGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigator = new FlyingPathNavigation(this, pLevel) {
            @Override
            public void tick() {
                super.tick();
                BlockPos pos = this.mob.blockPosition().below();
                BlockState below = this.level.getBlockState(pos);

                if (below.isAir()) {
                    if (this.mob.yya < 0)
                        this.mob.setYya(this.mob.yya * 0.9f);

                    if (this.mob.getDeltaMovement().y < 0)
                        this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(1, 0.9, 1));
                }
            }

            @Override
            public boolean isStableDestination(BlockPos pos) {
                BlockState state = this.level.getBlockState(pos);
                BlockState below = this.level.getBlockState(pos.below());

                return state.isAir() && below.isAir();
            }
        };
        return navigator;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSitting()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.yBodyRot = this.yBodyRotO;
        }

        if (this.saveCooldown > 0)
            this.saveCooldown--;

        this.partyParticle();

        if (!this.level().isClientSide && this.getTarget() == null)
            this.setMagicTarget(null);

        if (this.level().isClientSide && this.hasMagicTarget()) {
            this.yBodyRot = 0;
            this.yBodyRotO = 0;

            this.magicParticle();
        }

        if (this.supportAnim > 0)
            this.supportAnim--;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        var data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
        this.setTeeth(this.getRandom().nextBoolean());
        this.setLeftHanded(this.getRandom().nextBoolean());
        this.setFlower(this.getRandom().nextDouble() < 0.1);
        return data;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.getOwner() == pPlayer) {

            if (itemstack.is(Items.GLASS_BOTTLE)) {
                if (!this.hasBlacksmithUpgrade()) {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.fairy.no_upgrade"), true);
                } else if (this.level().getGameTime() > this.lastBreathTime + BREATH_INTERVAL) {
                    this.lastBreathTime = this.level().getGameTime();
                    itemstack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Items.DRAGON_BREATH));
                } else {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.fairy.breath_on_cooldown"), true);
                }
                //even if we don't give a breath we return success, otherwise we make the familiar change sitting position
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    public float getSupportAnim(float partialTicks) {
        if (this.supportAnim == 0)
            return 0;
        return (20 - this.supportAnim + partialTicks) / 20;
    }

    public float getAnimationHeight(float partialTicks) {
        return Mth.cos((this.tickCount + partialTicks) * ANIMATION_HEIGHT_SPEED) * 0.1f + 0.12f;
    }

    public float getWingRot(float partialTicks) {
        return Mth.cos((this.tickCount + partialTicks) * ANIMATION_HEIGHT_SPEED * 3);
    }

    private float getPartyArmArg(float partialTicks) {
        return (this.tickCount + partialTicks) * 0.2f;
    }

    public float getPartyArmRotX(float partialTicks) {
        return Mth.cos(this.getPartyArmArg(partialTicks)) * FamiliarUtil.toRads(40) - FamiliarUtil.toRads(90);
    }

    public float getPartyArmRotY(float partialTicks) {
        return Mth.sin(this.getPartyArmArg(partialTicks)) * FamiliarUtil.toRads(40);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private ParticleOptions createParticle() {
        return FamiliarUtil.isChristmas() ? OccultismParticles.SNOWFLAKE.get()
                : new DustParticleOptions(new Vector3f(0.9f, 0.9f, 0.5f), 1);
    }

    private void magicParticle() {
        Vec3 pos = this.getMagicPosition(1);
        this.level().addParticle(this.createParticle(), pos.x, pos.y + 1, pos.z, 0, 0, 0);
    }

    private void partyParticle() {
        if (!this.level().isClientSide || !this.isPartying() || this.tickCount % 2 != 0)
            return;

        Vec3 right = Vec3.directionFromRotation(0, this.yBodyRot).yRot(FamiliarUtil.toRads(-90));
        Vec3 armVector = new Vec3(0, -0.4, 0).xRot(this.getPartyArmRotX(0))
                .yRot(-this.getPartyArmRotY(0) + FamiliarUtil.toRads(-this.yBodyRot + 180));
        Vec3 pos = this.position().add(right.scale(0.2 * (this.isLeftHanded() ? -1 : 1)))
                .add(0, 0.7 + this.getAnimationHeight(0), 0).add(armVector);

        this.level().addParticle(this.createParticle(), pos.x, pos.y, pos.z, 0, 0, 0);
    }

    public Vec2 getMagicRadiusAngle(float partialTicks) {
        Entity target = this.getMagicTarget();
        if (target == null)
            return Vec2.ZERO;
        float radius = target.getBbWidth() * 1.2f;
        float angle = (this.tickCount + partialTicks) * 0.1f;
        return new Vec2(radius, angle);
    }

    public Vec3 getMagicPosition(float partialTicks) {
        Entity target = this.getMagicTarget();
        if (target == null)
            return null;

        Vec3 targetPos = target.getPosition(partialTicks).add(0, target.getBbHeight() / 2, 0);
        Vec2 radiusAngle = this.getMagicRadiusAngle(partialTicks);
        Vec3 offset = new Vec3(Mth.cos(radiusAngle.y) * radiusAngle.x, 0,
                Mth.sin(radiusAngle.y) * radiusAngle.x);
        return targetPos.add(offset);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return Collections.emptyList();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (this.saveCooldown > 0)
            this.saveCooldown--;
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

    public Entity getMagicTarget() {
        int id = this.entityData.get(MAGIC_TARGET);
        if (id < 0)
            return null;
        return this.level().getEntity(id);
    }

    private void setMagicTarget(Entity entity) {
        int id = entity == null ? -1 : entity.getId();
        this.entityData.set(MAGIC_TARGET, id);
    }

    public boolean hasMagicTarget() {
        return this.getMagicTarget() != null;
    }

    public boolean saveFamiliar(IFamiliar familiar) {
        if (this.saveCooldown > 0 || this.isSitting())
            return false;

        this.saveCooldown = 20 * 20;
        if (!familiar.getFamiliarEntity().level().isClientSide)
            Networking.sendToTracking(this,
                    new MessageFairySupport(this.getId(), familiar.getFamiliarEntity().getId()));
        return true;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getMagicTarget() != null)
            return false;

        return super.hurt(pSource, pAmount);
    }

    @Override
    protected AABB getAttackBoundingBox() {
        //copied from parent and set reach * 3
        Entity entity = this.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = this.getBoundingBox();
            aabb = new AABB(
                    Math.min(aabb2.minX, aabb1.minX),
                    aabb2.minY,
                    Math.min(aabb2.minZ, aabb1.minZ),
                    Math.max(aabb2.maxX, aabb1.maxX),
                    aabb2.maxY,
                    Math.max(aabb2.maxZ, aabb1.maxZ)
            );
        } else {
            aabb = this.getBoundingBox();
        }

        return aabb.inflate(DEFAULT_ATTACK_REACH * 3, 0.0, DEFAULT_ATTACK_REACH * 3);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource p_147189_) {
        return false;
    }

    @Override
    public void push(Entity pEntity) {
        if (this.hasMagicTarget())
            return;
        super.push(pEntity);
    }

    @Override
    protected void doPush(Entity pEntity) {
        if (this.hasMagicTarget())
            return;
        super.doPush(pEntity);
    }

    @Override
    public boolean isPickable() {
        return !this.hasMagicTarget();
    }

    public void startSupportAnimation() {
        this.supportAnim = 20;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    private static class SupportGoal extends Goal {

        private static final int DURATION = 20 * 10;

        private final FairyFamiliarEntity fairy;
        private final Map<UUID, Integer> cooldowns;

        private SupportGoal(FairyFamiliarEntity fairy) {
            this.fairy = fairy;
            this.cooldowns = new HashMap<>();
        }

        @Override
        public boolean canUse() {
            return !this.fairy.isSitting() && this.fairy.getFamiliarEntity() != null;
        }

        @Override
        public void tick() {
            for (Entry<UUID, Integer> entry : this.cooldowns.entrySet())
                if (entry.getValue() > 0)
                    this.cooldowns.put(entry.getKey(), entry.getValue() - 1);

            if (this.fairy.tickCount % 5 != 0)
                return;

            LivingEntity owner = this.fairy.getFamiliarOwner();

            if (owner == null)
                return;

            List<Mob> familiars = this.fairy.level().getEntitiesOfClass(Mob.class,
                    this.fairy.getBoundingBox().inflate(10),
                    e -> e != this.fairy && e instanceof IFamiliar && ((IFamiliar) e).getFamiliarOwner() == owner);

            for (Mob familiar : familiars) {
                UUID id = familiar.getUUID();
                if (!this.cooldowns.containsKey(id))
                    this.cooldowns.put(id, 0);

                if (this.cooldowns.get(id) == 0) {
                    boolean gaveSupport = familiar.isOnFire()
                            && familiar.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, DURATION));
                    if (familiar.isInWater()
                            && familiar.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, DURATION)))
                        gaveSupport = true;
                    if (!familiar.isNoGravity() && familiar.fallDistance > 3
                            && familiar.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, DURATION)))
                        gaveSupport = true;
                    if (familiar.getNavigation().isInProgress()
                            && familiar.getNavigation().getTargetPos().distSqr(familiar.blockPosition()) > 100
                            && familiar.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DURATION)))
                        gaveSupport = true;
                    if (familiar.getLastHurtByMob() != null
                            && familiar.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, DURATION)))
                        gaveSupport = true;
                    if (gaveSupport) {
                        Networking.sendToTracking(this.fairy,
                                new MessageFairySupport(this.fairy.getId(), familiar.getId()));
                        this.cooldowns.put(id, DURATION);
                    }
                }
            }
        }

    }

    private static class MagicGoal extends MeleeAttackGoal {

        private final FairyFamiliarEntity fairy;
        private int attackTimer;

        public MagicGoal(FairyFamiliarEntity fairy) {
            super(fairy, 1.8, true);
            this.fairy = fairy;
        }

        @Override
        public void start() {
            super.start();
            this.attackTimer = 10;
        }

        @Override
        public void stop() {
            super.stop();
            this.fairy.setMagicTarget(null);
        }

        @Override
        protected boolean canPerformAttack(LivingEntity pEntity) {
            return this.mob.isWithinMeleeAttackRange(pEntity);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy) {
            this.attackTimer--;

            if (this.canPerformAttack(pEnemy)) {
                this.fairy.setMagicTarget(pEnemy);
                if (this.attackTimer <= 0) {
                    this.attackTimer = 10;
                    LivingEntity owner = this.fairy.getFamiliarOwner();
                    if (owner != null) {
                        pEnemy.hurt(this.fairy.damageSources().mobAttack(owner), 3);
                        pEnemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                        List<LivingEntity> allies = this.fairy.level().getEntitiesOfClass(LivingEntity.class,
                                this.fairy.getBoundingBox().inflate(7), e -> e != this.fairy && e instanceof IFamiliar
                                        && ((IFamiliar) e).getFamiliarOwner() == owner);
                        allies.add(owner);
                        for (LivingEntity ally : allies) {
                            ally.heal(3);
                            ((ServerLevel) this.fairy.level()).sendParticles(ParticleTypes.HEART, ally.getX(),
                                    ally.getY() + ally.getBbHeight(), ally.getZ(), 1, 0, 0, 0, 1);
                        }
                    }
                }
            } else {
                this.fairy.setMagicTarget(null);
            }
        }

    }

    public static class SetAttackTargetGoal extends TargetGoal {

        private final FamiliarEntity entity;
        private int timestamp;

        public SetAttackTargetGoal(FamiliarEntity entity) {
            super(entity, false);
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (owner == null)
                return false;

            if (owner.distanceToSqr(this.entity) >= 400)
                return false;

            if (this.timestamp == owner.getLastHurtMobTimestamp())
                return false;

            Entity target = owner.getLastHurtMob();
            return target != null && !(target instanceof Player) && !(target instanceof IFamiliar);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            return super.canContinueToUse() && owner != null && owner.distanceToSqr(this.entity) < 400;
        }

        @Override
        public void start() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (owner == null)
                return;

            this.entity.setTarget(owner.getLastHurtMob());
            this.timestamp = owner.getLastHurtMobTimestamp();
            super.start();
        }
    }
}
