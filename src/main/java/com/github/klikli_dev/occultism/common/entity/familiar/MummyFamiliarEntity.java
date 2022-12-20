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

package com.github.klikli_dev.occultism.common.entity.familiar;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEffects;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
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

    private static final UUID DAMAGE_BONUS = UUID.fromString("6aa62086-7009-402b-9c13-c2de74bf077d");

    private static final int MAX_FIGHT_TIMER = 5;

    private int fightPose, fightTimer;
    private Vec3 capowPos, capowOffset, capowOffset0;

    public MummyFamiliarEntity(EntityType<? extends MummyFamiliarEntity> type, Level level) {
        super(type, level);
        capowPos = capowOffset = capowOffset0 = Vec3.ZERO;
        this.fightPose = -1;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FamiliarEntity.createAttributes().add(Attributes.MAX_HEALTH, 18).add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.FOLLOW_RANGE, 30);
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

        if (level.isClientSide && fightPose != -1) {
            this.capowOffset0 = capowOffset;
            if (fightTimer++ == MAX_FIGHT_TIMER) {
                fightTimer = 0;
                fightPose += 1;
                if (fightPose == 3)
                    fightPose = -1;
                capowPos = new Vec3(randNum(2), -random.nextDouble(), randNum(2));
            }

            capowOffset = new Vec3(randNum(0.1), randNum(0.1), randNum(0.1));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setCrown(this.getRandom().nextDouble() < 0.1);
        this.setTooth(this.getRandom().nextBoolean());
        this.setHeka(this.getRandom().nextBoolean());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);
        this.fightPose = 0;
        this.fightTimer = 0;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(OccultismEffects.MUMMY_DODGE.get(), 300, 0, false, false));
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
    }

    private double randNum(double size) {
        return (random.nextDouble() - 0.5) * size;
    }

    public float getCapowAlpha(float partialTicks) {
        return (MAX_FIGHT_TIMER - fightTimer - partialTicks) / MAX_FIGHT_TIMER;
    }

    public Vec3 getCapowPosition(float partialTicks) {
        return capowPos.add(capowOffset0.add(capowOffset0.subtract(capowOffset).scale(partialTicks)));
    }

    public int getFightPose() {
        return fightPose;
    }

    @Override
    public void blacksmithUpgrade() {
        super.blacksmithUpgrade();
        AttributeModifier damage = new AttributeModifier(DAMAGE_BONUS, "Mummy attack bonus", 3, Operation.ADDITION);
        if (!this.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(damage))
            this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(damage);

    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasCrown())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
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
