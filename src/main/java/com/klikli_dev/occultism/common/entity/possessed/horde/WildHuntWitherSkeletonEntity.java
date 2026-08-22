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

package com.klikli_dev.occultism.common.entity.possessed.horde;

import com.klikli_dev.occultism.common.entity.possessed.PossessedMob;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WildHuntWitherSkeletonEntity extends WitherSkeleton implements PossessedMob {

    List<WildHuntSkeletonEntity> minions = new ArrayList<>();

    public WildHuntWitherSkeletonEntity(EntityType<? extends WildHuntWitherSkeletonEntity> type,
                                        Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static Builder createAttributes() {
        return WitherSkeleton.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MAX_HEALTH, 60.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, EntitySpawnReason reason,
                                        @Nullable SpawnGroupData spawnDataIn) {
        if (reason == EntitySpawnReason.MOB_SUMMONED && level.getLevel().getGameRules().get(GameRules.SPAWN_MOBS)) {
            int maxSkeletons = 3 + level.getRandom().nextInt(6);

            for (int i = 0; i < maxSkeletons; i++) {
                WildHuntSkeletonEntity entity = OccultismEntities.WILD_HUNT_SKELETON.get().create(this.level(), EntitySpawnReason.REINFORCEMENT);

                EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

                double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                        level.getRandom().nextInt(360), 0);
                entity.setCustomName(Component.literal(TextUtil.generateName()));
                level.addFreshEntity(entity);
                entity.setMaster(this);
                this.minions.add(entity);
            }
        }
        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        TagKey<EntityType<?>> wildHuntTag = Entities.WILD_HUNT;

        Entity trueSource = source.getEntity();
        if (trueSource != null && trueSource.getType().builtInRegistryHolder().is(wildHuntTag))
            return true;

        Entity immediateSource = source.getDirectEntity();
        if (immediateSource != null && immediateSource.getType().builtInRegistryHolder().is(wildHuntTag))
            return true;

        return super.isInvulnerableTo(level, source);
    }

    @Override
    public void actuallyHurt(ServerLevel level, DamageSource source, float amount) {
        if (!this.minions.isEmpty()) {
            this.minions.forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false)));
        }

        super.actuallyHurt(level, source, (float) (amount * (1 - this.minions.size() / 10.0)));
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);
        if (this.level() instanceof ServerLevel) {
            if (!this.minions.isEmpty()) {
                this.minions.forEach(e -> {
                    e.setMaster(null);
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION,
                            e.getX(),e.getY() + 0.5, e.getZ(), 3, 0.0, 0.0, 0.0, 0.0);
                    this.level().playSound(null, e.getOnPos(), OccultismSounds.POOF.get(), SoundSource.HOSTILE, 1, 3);
                    e.remove(RemovalReason.DISCARDED);
                });
            }
        }
    }

    public void notifyMinionDeath(WildHuntSkeletonEntity minion) {
        this.minions.remove(minion);
    }

    @Override
    public EntityType basedMob() {
        return EntityType.WITHER_SKELETON;
    }
}
