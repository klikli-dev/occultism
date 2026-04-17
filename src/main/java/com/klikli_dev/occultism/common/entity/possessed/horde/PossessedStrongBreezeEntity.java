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
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Entities;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PossessedStrongBreezeEntity extends Breeze implements PossessedMob {
    List<WildHuskEntity> minionsA = new ArrayList<>();
    List<WildBoggedEntity> minionsB = new ArrayList<>();
    List<WildSlimeEntity> minionsC = new ArrayList<>();

    public PossessedStrongBreezeEntity(EntityType<? extends Breeze> type,
                                       Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static Builder createAttributes() {
        return Breeze.createAttributes()
                .add(Attributes.MAX_HEALTH, 300.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, EntitySpawnReason reason,
                                        @Nullable SpawnGroupData spawnDataIn) {

        for (int i = 0; i < 7; i++) {
            WildHuskEntity entity = OccultismEntities.WILD_HUSK.get().create(this.level(), EntitySpawnReason.EVENT);
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            level.addFreshEntity(entity);
            entity.setMaster(this);
            this.minionsA.add(entity);
        }

        for (int i = 0; i < 7; i++) {
            WildBoggedEntity entity = OccultismEntities.WILD_BOGGED.get().create(this.level(), EntitySpawnReason.EVENT);
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            level.addFreshEntity(entity);
            entity.setMaster(this);
            this.minionsB.add(entity);
        }

        for (int i = 1; i < 8; i++) {
            WildSlimeEntity entity = OccultismEntities.WILD_SLIME.get().create(this.level(), EntitySpawnReason.EVENT);
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            entity.setSize(i,true); //Haha bigger slimes
            level.addFreshEntity(entity);
            entity.setMaster(this);
            this.minionsC.add(entity);
        }

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        TagKey<EntityType<?>> wildTrialTag = Entities.WILD_TRIAL;

        Entity trueSource = source.getEntity();
        if (trueSource != null && trueSource.getType().builtInRegistryHolder().is(wildTrialTag))
            return true;

        Entity immediateSource = source.getDirectEntity();
        if (immediateSource != null && immediateSource.getType().builtInRegistryHolder().is(wildTrialTag))
            return true;

        return super.isInvulnerableTo(level, source);
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource source, float amount) {
        if (!this.minionsA.isEmpty()) {
            this.minionsA.forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false)));
        }
        if (!this.minionsB.isEmpty()) {
            this.minionsB.forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false)));
        }
        if (!this.minionsC.isEmpty()) {
            this.minionsC.forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false)));
        }

        super.actuallyHurt(level, source, (float) (amount * (1 - (this.minionsA.size() + this.minionsB.size() + this.minionsC.size())/24.0) ) );
    }

    public void notifyMinionDeath(WildHuskEntity minion) {
        this.minionsA.remove(minion);
    }
    public void notifyMinionDeath(WildBoggedEntity minion) {
        this.minionsB.remove(minion);
    }
    public void notifyMinionDeath(WildSlimeEntity minion) {
        this.minionsC.remove(minion);
    }

    @Override
    public EntityType basedMob(){
        return EntityType.BREEZE;
    }
}
