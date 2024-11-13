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
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DeerFamiliarEntity extends FamiliarEntity {

    private static final ResourceLocation SPEED_BONUS = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "deer_speed_bonus");

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
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new EatBlockGoal(this));
        this.goalSelector.addGoal(5, new DevilFamiliarEntity.AttackGoal(this, 5) {
            @Override
            public boolean canUse() {
                return super.canUse() && DeerFamiliarEntity.this.hasBlacksmithUpgrade();
            }
        });
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (this.hasRedNose())
            OccultismAdvancements.FAMILIAR.get().trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && !this.hasGlowingTag() && this.hasRedNose())
            this.setGlowingTag(true);

        if (this.level().isClientSide) {
            this.eatTimer--;
            this.oNeckRotTimer = this.neckRotTimer;
            if (this.isEating())
                this.neckRotTimer = Math.min(this.neckRotTimer + 1, 10);
            else
                this.neckRotTimer = Math.max(this.neckRotTimer - 1, 0);
        }

        if (!this.level().isClientSide) {
            Entity owner = this.getFamiliarOwner();
            if (owner != null && this.distanceToSqr(owner) > 50) {
                if (this.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_BONUS) == null)
                    this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(
                            new AttributeModifier(SPEED_BONUS, 0.15, AttributeModifier.Operation.ADD_VALUE));
            } else if (this.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_BONUS) != null) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_BONUS);
            }
        }
    }

    public float getNeckRot(float partialTick) {
        return 0.4f
                + Mth.lerp(Mth.lerp(partialTick, this.oNeckRotTimer, this.neckRotTimer) / 10, 0, 1.5f);
    }

    @Override
    public void ate() {
        if (this.getRandom().nextDouble() < 0.25) {
            this.spawnAtLocation(OccultismItems.DATURA_SEEDS.get(), 0);
            LivingEntity owner = this.getOwner();
            if (owner instanceof ServerPlayer serverPlayer)
                OccultismAdvancements.FAMILIAR.get().trigger(serverPlayer, FamiliarTrigger.Type.DEER_POOP);
        }
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        List<MobEffectInstance> effects = new ArrayList<>();
        effects.add(new MobEffectInstance(MobEffects.JUMP, 300, 0, false, false));
        effects.add(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0, false, false));
        if (DeerFamiliarEntity.this.hasBlacksmithUpgrade()){
            effects.add(new MobEffectInstance(OccultismEffects.STEP_HEIGHT, 300, 1, false, false));
        } else {
            effects.add(new MobEffectInstance(OccultismEffects.STEP_HEIGHT, 300, 0, false, false));
        }
        return effects;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn) {
        this.setRedNose(this.getRandom().nextDouble() < 0.1);
        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("variants"))
            this.setRedNose(compound.getBoolean("hasRedNose"));
    }

    @Override
    public boolean canBlacksmithUpgrade() {
        return !this.hasBlacksmithUpgrade();
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
}
