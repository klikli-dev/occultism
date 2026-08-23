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
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.common.entity.familiar.DevilFamiliarEntity.AttackGoal;
import com.klikli_dev.occultism.common.entity.familiar.GreedyFamiliarEntity.FindItemGoal;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DragonFamiliarEntity extends FamiliarEntity {

    public static final int MAX_PET_TIMER = 20 * 2;
    private static final int GREEDY_INCREMENT = 20 * 60 * 5;

    private final float colorOffset;
    private int greedyTimer;
    private int flyingTimer, flyingTimer0, wingspan, wingspan0;
    private int petTimer;

    public DragonFamiliarEntity(EntityType<? extends DragonFamiliarEntity> type, Level level) {
        super(type, level);
        this.colorOffset = this.getRandom().nextFloat() * 2;
        this.petTimer = MAX_PET_TIMER;
    }

    public static Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.ARMOR, 25);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity pEntity) {
        return super.getPassengerRidingPosition(pEntity).subtract(0, 0.6, 0);
    }

    @Override
    public boolean hasRareVariant() {
        return this.hasFez();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, EntitySpawnReason pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setFez(this.getRandom().nextDouble() < 0.1);
        this.setEars(this.getRandom().nextDouble() < 0.5);
        this.setArms(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    public boolean hasSword() {
        return this.hasBlacksmithUpgrade() && !this.swinging;
    }

    public int getGreedyTimer() {
        return this.greedyTimer;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new FindItemGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && DragonFamiliarEntity.this.getPassengers().stream()
                        .anyMatch(e -> e instanceof GreedyFamiliarEntity);
            }
        });
        this.goalSelector.addGoal(4, new FetchGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(6, new AttackGoal(this, 5) {
            @Override
            public boolean canUse() {
                return super.canUse() && !DragonFamiliarEntity.this.hasBlacksmithUpgrade();
            }
        });
        this.goalSelector.addGoal(6, new ThrowSwordGoal(this, 100));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void swing(InteractionHand handIn, boolean updateSelf) {
        super.swing(handIn, updateSelf);
        this.swingTime = -20 + 6;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    public float getAttackProgress(float partialTicks) {
        return Mth.lerp((this.swingTime + (20 - 6) + partialTicks) / 20, 0, 1);
    }

    public int getPetTimer() {
        return this.petTimer;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.greedyTimer > 0)
            this.greedyTimer--;

        if (!this.onGround()) {
            Vec3 motion = this.getDeltaMovement();
            if (motion.y < 0) {
                motion = motion.multiply(1, 0.5, 1);
                this.setDeltaMovement(motion);
            }
        }

        if (!this.isEffectiveAi()) {
            this.flyingTimer0 = this.flyingTimer;
            this.wingspan0 = this.wingspan;
            if (this.onGround()) {
                this.wingspan -= 5;
                if (this.wingspan < 0)
                    this.wingspan = 0;

            } else {
                this.flyingTimer++;
                this.wingspan += 5;
                if (this.wingspan > 30)
                    this.wingspan = 30;
            }
            if (this.petTimer < MAX_PET_TIMER)
                this.petTimer++;
        }
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        Level level = wearer.level();
        if (this.isEffectEnabled(wearer) && this.hasBlacksmithUpgrade() && !level.isClientSide() && level.getGameTime() % 64 == 0) {
            List<Monster> enemies = level.getEntitiesOfClass(Monster.class,
                    wearer.getBoundingBox().inflate(50),
                    enemy -> {
                        Vec3 start = wearer.getEyePosition();
                        Vec3 end = enemy.getEyePosition();
                        BlockHitResult hit = level.clip(new ClipContext(
                                start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, wearer));
                        return hit.getType() == HitResult.Type.MISS && enemy.isAlive();
                    }
            );

            if (enemies.isEmpty())
                return;


            Entity enemy = enemies.get(wearer.getRandom().nextInt(enemies.size()));
            thrownSword(wearer, wearer, enemy);
        }
    }

    public void thrownSword(Entity start, Entity owner, Entity enemy) {
        AttributeInstance dmgInstance = this.getAttribute(Attributes.ATTACK_DAMAGE);
        float dmg = dmgInstance == null ? 6 : (float) dmgInstance.getValue();
        if (this.getGreedyTimer() > 0)
            dmg += 2;
        ThrownSwordEntity sword = new ThrownSwordEntity(OccultismEntities.THROWN_SWORD_TYPE.get(), start.level());
        sword.setDamage(dmg);
        sword.setOwner(owner);
        double x = start.getX();
        double y = start.getEyeY();
        double z = start.getZ();
        double xDir = enemy.getX() - x;
        double yDir = enemy.getY() + enemy.getBbHeight() - y;
        double zDir = enemy.getZ() - z;
        sword.setPos(x, y, z);
        sword.shoot(xDir, yDir, zDir, 0.5f, 3f);
        start.level().addFreshEntity(sword);
    }

    public float getFlyingTimer(float partialTicks) {
        return Mth.lerp(partialTicks, this.flyingTimer0, this.flyingTimer);
    }

    public float getWingspan(float partialTicks) {
        return Mth.lerp(partialTicks, this.wingspan0, this.wingspan);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (this.hasStick()) {
            ItemTransferUtil.giveItemToPlayer(playerIn, new ItemStack(Items.STICK));
            this.setStick(false);
            return !this.isEffectiveAi() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        } else if (stack.is(Tags.Items.NUGGETS_GOLD)) {
            OccultismAdvancements.FAMILIAR.get().trigger(this.getFamiliarOwner(), Type.DRAGON_NUGGET);
            this.greedyTimer += GREEDY_INCREMENT;
            if (this.isEffectiveAi())
                stack.shrink(1);
            else
                this.level().addParticle(ParticleTypes.HEART, this.getX(), this.getY() + 1, this.getZ(), 0, 0,
                        0);
            return !this.isEffectiveAi() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        } else if (stack.isEmpty() && playerIn.isShiftKeyDown()) {
            this.petTimer = 0;
            OccultismAdvancements.FAMILIAR.get().trigger(playerIn, Type.DRAGON_PET);
            return !this.isEffectiveAi() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        return super.mobInteract(playerIn, hand);
    }

    public boolean hasFez() {
        return this.hasVariant(0);
    }

    private void setFez(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasEars() {
        return this.hasVariant(1);
    }

    private void setEars(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasArms() {
        return this.hasVariant(2);
    }

    private void setArms(boolean b) {
        this.setVariant(2, b);
    }

    public boolean hasStick() {
        return this.hasVariant(3);
    }

    private void setStick(boolean b) {
        this.setVariant(3, b);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("greedyTimer", this.greedyTimer);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.greedyTimer = input.getIntOr("greedyTimer", 0);
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        if (this.effectDefinitionList == null || this.effectDefinitionList.isEmpty()
                || this.getOwner() == null)
            return List.of();

        FamiliarSettingsData data = this.getOwner().getData(OccultismDataStorage.FAMILIAR_SETTINGS.get());
        List<MobEffectInstance> effects = new ArrayList<>(this.effectDefinitionList.size());
        for (var effect : this.effectDefinitionList) {
            int amp = effect.getValue(this);
            amp = Math.min(amp, data.getEffectAmplifier(this.getFamiliarEntity().getType(), effect.effect()));
            if (amp >= 0) {
                effects.add(new MobEffectInstance(effect.effect(), 300, this.greedyTimer > 0 ? 1 + amp : amp, false, false));
            }
        }
        return effects;
    }

    public float getEyeColorR(float partialTicks) {
        return Math.abs(Mth.sin((this.tickCount + partialTicks + 5) / 20 + this.colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorG(float partialTicks) {
        return Math.abs(Mth.sin((this.tickCount + partialTicks + 10) / 30 + this.colorOffset)) * 0.8f + 0.2f;
    }

    public float getEyeColorB(float partialTicks) {
        return Math.abs(Mth.sin((this.tickCount + partialTicks) / 40 + this.colorOffset)) * 0.8f + 0.2f;
    }

    private static class ThrowSwordGoal extends AttackGoal {

        public ThrowSwordGoal(DragonFamiliarEntity entity, float range) {
            super(entity, range);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.entity.hasBlacksmithUpgrade() && !this.entity.isSitting();
        }

        @Override
        protected void attack(List<LivingEntity> enemies) {
            if (enemies.isEmpty())
                return;

            Entity enemy = enemies.get(this.entity.getRandom().nextInt(enemies.size()));
            if (this.entity instanceof DragonFamiliarEntity dragon)
                dragon.thrownSword(dragon, dragon.getOwner(), enemy);
        }

    }

    private static class FetchGoal extends Goal {

        private final DragonFamiliarEntity dragon;
        private ItemEntity stick;

        public FetchGoal(DragonFamiliarEntity dragon) {
            this.dragon = dragon;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.stick = this.findStick();
            return this.stick != null && !this.dragon.hasStick();
        }

        @Override
        public boolean canContinueToUse() {
            return this.stick != null && !this.dragon.hasStick();
        }

        public void start() {
            this.dragon.getNavigation().moveTo(this.stick, 1.2);
        }

        public void stop() {
            this.dragon.getNavigation().stop();
            this.stick = null;
        }

        @Override
        public void tick() {
            if (this.stick == null || !this.stick.isAlive()) {
                this.stick = this.findStick();
                if (this.stick == null)
                    return;
            }
            this.dragon.getNavigation().moveTo(this.stick, 1.2);

            if (this.stick.distanceToSqr(this.dragon) < 3) {
                this.dragon.setStick(true);
                OccultismAdvancements.FAMILIAR.get().trigger(this.dragon.getFamiliarOwner(),
                        Type.DRAGON_FETCH);
                this.stick.getItem().shrink(1);
                this.stick = null;
            }
        }

        private ItemEntity findStick() {
            List<ItemEntity> sticks = this.dragon.level().getEntitiesOfClass(ItemEntity.class,
                    this.dragon.getBoundingBox().inflate(8), e -> e.getItem().getItem() == Items.STICK && e.isAlive());
            return sticks.isEmpty() ? null : sticks.get(0);
        }

    }
}

