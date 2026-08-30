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

import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.Type;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public class DeerFamiliarEntity extends FamiliarEntity {

    private static final byte START_EATING = 10;

    private int eatTimer, neckRotTimer, oNeckRotTimer;

    public DeerFamiliarEntity(EntityType<? extends DeerFamiliarEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 8, 1));
        this.goalSelector.addGoal(3, new DeerMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(4, new EatBlockGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public boolean hasRareVariant() {
        return this.hasRedNose();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && !this.hasGlowingTag() && this.hasRedNose())
            this.setGlowingTag(true);

        if (this.level().isClientSide()) {
            this.eatTimer--;
            this.oNeckRotTimer = this.neckRotTimer;
            if (this.isEating())
                this.neckRotTimer = Math.min(this.neckRotTimer + 1, 10);
            else
                this.neckRotTimer = Math.max(this.neckRotTimer - 1, 0);
        }
    }

    public float getNeckRot(float partialTick) {
        return 0.4f
                + Mth.lerp(Mth.lerp(partialTick, this.oNeckRotTimer, this.neckRotTimer) / 10, 0, 1.5f);
    }

    @Override
    public void ate() {
        if (this.getRandom().nextDouble() < 0.25) {
            if (this.level() instanceof ServerLevel sl)
                this.spawnAtLocation(sl, OccultismItems.DATURA_SEEDS.get());
            LivingEntity owner = this.getOwner();
            if (owner instanceof ServerPlayer serverPlayer)
                OccultismAdvancements.FAMILIAR.get().trigger(serverPlayer, Type.DEER_POOP);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, EntitySpawnReason reason,
                                        @Nullable SpawnGroupData spawnDataIn) {
        this.setRedNose(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setRedNose(input.getBooleanOr("hasRedNose", false));
    }

    public boolean hasRedNose() {
        return this.hasVariant(0);
    }

    private void setRedNose(boolean b) {
        this.setVariant(0, b);
    }

    public boolean isEating() {
        return this.eatTimer > 0;
    }

    private void startEating() {
        this.eatTimer = 40;
        this.neckRotTimer = 0;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == START_EATING)
            this.startEating();
        else
            super.handleEntityEvent(id);
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        List<LivingEntity> list = FamiliarUtil.getOwnerEnemies(this.getFamiliarOwner(), this, 49);
        LivingEntity ent = this.getLastHurtByMob();
        if (ent != null && ent.isAlive())
            list.add(this.getLastHurtByMob());
        return list.isEmpty() ? null : list.getLast();
    }

    public boolean attackEnabled() {
        return this.hasBlacksmithUpgrade() && this.isEffectEnabled(this.getOwner());
    }

    private static class DeerMeleeAttackGoal extends MeleeAttackGoal {

        DeerFamiliarEntity deer;

        public DeerMeleeAttackGoal(DeerFamiliarEntity deer, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(deer, speedModifier, followingTargetEvenIfNotSeen);
            this.deer = deer;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.deer.attackEnabled();
        }

        protected void checkAndPerformAttack(@NonNull LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(getServerLevel(this.mob), target);

                if (this.deer.hasIesniumUpgrade()) {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * 10), this.deer);
                    Level level = this.deer.level();
                    if (level.getRandom().nextFloat() < 0.04F) {
                        LightningBolt lightningBolt = EntityTypes.LIGHTNING_BOLT.create(level, EntitySpawnReason.EVENT);
                        if (lightningBolt != null) {
                            lightningBolt.snapTo(target.getX(), target.getY(), target.getZ());
                            level.addFreshEntity(lightningBolt);
                        }
                    }
                }
            }
        }
    }
}
