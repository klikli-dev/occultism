/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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

package com.klikli_dev.occultism.common.entity.spirit;

import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import javax.annotation.Nullable;

public class AfritWildEntity extends Monster implements GeoEntity {
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public AfritWildEntity(EntityType<? extends AfritWildEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 50.0)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_SPEED, 8.0)
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.40000001192092896)
                .add(Attributes.ARMOR, 8.0)
                .add(Attributes.ARMOR_TOUGHNESS, 50.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn) {
        int maxBlazes = 3 + level.getRandom().nextInt(6);

        for (int i = 0; i < maxBlazes; i++) {
            Blaze entity = EntityType.BLAZE.create(level.getLevel());

            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.absMoveTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            level.addFreshEntity(entity);
        }

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    protected void registerGoals() {
        //Override all base goals
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(Blaze.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE))
            return true;
        TagKey<EntityType<?>> alliesTags = OccultismTags.Entities.AFRIT_ALLIES;

        //alliesTags should never be null - should in fact be impossible - but somehow for some people sometimes is.
        if (alliesTags != null) {
            Entity trueSource = source.getEntity();
            if (trueSource != null && trueSource.getType().is(alliesTags))
                return true;

            Entity immediateSource = source.getDirectEntity();
            if (immediateSource != null && immediateSource.getType().is(alliesTags))
                return true;
        }

        return super.isInvulnerableTo(source);
    }

    @Override
    public int getCurrentSwingDuration() {
        return 11; //to match our attack animation speed + 1 tick
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        var mainController = new AnimationController<>(this, "mainController", 0, this::animPredicate);
        controllers.add(mainController);
    }

    private <T extends GeoAnimatable> PlayState animPredicate(AnimationState<T> tAnimationState) {

        if (this.swinging) {
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }

        return tAnimationState.setAndContinue(tAnimationState.isMoving() ? RawAnimation.begin().thenPlay("walk") : RawAnimation.begin().thenPlay("idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }
}
