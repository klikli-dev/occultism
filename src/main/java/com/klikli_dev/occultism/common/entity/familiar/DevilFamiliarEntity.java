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
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.List;

public class DevilFamiliarEntity extends FamiliarEntity implements GeoEntity {
    private static final EntityDataAccessor<Long> SIN_TIME = SynchedEntityData.defineId(DevilFamiliarEntity.class, EntityDataSerializers.LONG);

    protected static final long SIN_INTERVAL = 20 * 60 * 33;

    private final float heightOffset;

    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public DevilFamiliarEntity(EntityType<? extends DevilFamiliarEntity> type, Level level) {
        super(type, level);
        this.heightOffset = this.getRandom().nextFloat() * 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896);
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
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIN_TIME, (long) 0);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(SIN_TIME, compound.getLong("sinLastTime"));
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putLong("sinLastTime", this.getSinTime());
    }

    private void setSinTime(long b) {
        this.entityData.set(SIN_TIME, b);
    }

    private long getSinTime() {
        return this.entityData.get(SIN_TIME);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.getOwner() == pPlayer) {

            if (itemstack.is(Items.GOLDEN_APPLE)) {
                long time =  this.getSinTime() + SIN_INTERVAL - this.level().getGameTime();
                if (!this.hasBlacksmithUpgrade()) {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.devil.no_upgrade"), true);
                } else if (time < 0) {
                    this.setSinTime(this.level().getGameTime());
                    itemstack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                } else {
                    pPlayer.displayClientMessage(Component.translatable("dialog.occultism.devil.sin_on_cooldown", time), true);
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

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, false, false));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var mainController = new AnimationController<>(this, "mainController", 0, this::animPredicate);
        controllerRegistrar.add(mainController);
    }

    @Override
    public int getCurrentSwingDuration() {
        return 11; //to match our attack animation speed + 1 tick
    }

    private <T extends GeoAnimatable> PlayState animPredicate(AnimationState<T> tAnimationState) {

        if (this.swinging) {
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }

        if (this.isSitting()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().thenPlay("sitting"));
            return PlayState.CONTINUE;
        }

        return tAnimationState.setAndContinue(tAnimationState.isMoving() ? RawAnimation.begin().thenPlay("walk") : RawAnimation.begin().thenPlay("idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    public static class AttackGoal extends Goal {

        private static final int MAX_COOLDOWN = 20;

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
                OccultismAdvancements.FAMILIAR.get().trigger(this.entity.getFamiliarOwner(), FamiliarTrigger.Type.DEVIL_FIRE);

            this.attack(enemies);
            this.entity.swing(InteractionHand.MAIN_HAND);
            this.cooldown = MAX_COOLDOWN;
        }

        protected void attack(List<LivingEntity> enemies) {
            for (Entity e : enemies) {
                e.hurt(this.entity.damageSources().playerAttack((Player) this.entity.getFamiliarOwner()), 4);
                e.setRemainingFireTicks(4 * 20);
            }
        }

        public void stop() {
            this.cooldown = MAX_COOLDOWN;
        }
    }
}
