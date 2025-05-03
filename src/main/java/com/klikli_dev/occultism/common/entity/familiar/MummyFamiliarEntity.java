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
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class MummyFamiliarEntity extends FamiliarEntity {

    private static final ResourceLocation DAMAGE_BONUS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "mummy_damage_bonus");

    private static final int MAX_FIGHT_TIMER = 5;

    private int fightPose, fightTimer;
    private Vec3 capowPos, capowOffset, capowOffset0;

    public MummyFamiliarEntity(EntityType<? extends MummyFamiliarEntity> type, Level level) {
        super(type, level);
        this.capowPos = this.capowOffset = this.capowOffset0 = Vec3.ZERO;
        this.fightPose = -1;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 9).add(Attributes.FOLLOW_RANGE, 30);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.25, true));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 4, 1));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 3, 7));

        this.targetSelector.addGoal(0, new FairyFamiliarEntity.SetAttackTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.fightPose != -1) {
            this.capowOffset0 = this.capowOffset;
            if (this.fightTimer++ == MAX_FIGHT_TIMER) {
                this.fightTimer = 0;
                this.fightPose += 1;
                if (this.fightPose == 3)
                    this.fightPose = -1;
                this.capowPos = new Vec3(this.randNum(2), -this.random.nextDouble(), this.randNum(2));
            }

            this.capowOffset = new Vec3(this.randNum(0.1), this.randNum(0.1), this.randNum(0.1));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setCrown(this.getRandom().nextDouble() < 0.1);
        this.setTooth(this.getRandom().nextBoolean());
        this.setHeka(this.getRandom().nextBoolean());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);
        this.fightPose = 0;
        this.fightTimer = 0;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return hasBlacksmithUpgrade() ? ImmutableList.of(new MobEffectInstance(OccultismEffects.MUMMY_DODGE, 300, 1, false, false)) :
                ImmutableList.of(new MobEffectInstance(OccultismEffects.MUMMY_DODGE, 300, 0, false, false));
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    private double randNum(double size) {
        return (this.random.nextDouble() - 0.5) * size;
    }

    public float getCapowAlpha(float partialTicks) {
        return (MAX_FIGHT_TIMER - this.fightTimer - partialTicks) / MAX_FIGHT_TIMER;
    }

    public Vec3 getCapowPosition(float partialTicks) {
        return this.capowPos.add(this.capowOffset0.add(this.capowOffset0.subtract(this.capowOffset).scale(partialTicks)));
    }

    public int getFightPose() {
        return this.fightPose;
    }

    @Override
    public void blacksmithUpgrade() {
        super.blacksmithUpgrade();
        AttributeModifier damage = new AttributeModifier(
                DAMAGE_BONUS, 3, Operation.ADD_VALUE);
        if (!this.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DAMAGE_BONUS))
            this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(damage);

    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasCrown())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    public boolean hasCrown() {
        return this.hasVariant(0);
    }

    private void setCrown(boolean b) {
        this.setVariant(0, b);
    }

    public boolean hasHeka() {
        return this.hasVariant(1);
    }

    private void setHeka(boolean b) {
        this.setVariant(1, b);
    }

    public boolean hasTooth() {
        return this.hasVariant(2);
    }

    private void setTooth(boolean b) {
        this.setVariant(2, b);
    }
}
