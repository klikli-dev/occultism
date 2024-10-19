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

import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;

public class PossessedBreezeEntity extends Breeze {

    public PossessedBreezeEntity(EntityType<? extends Breeze> type,
                                 Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return Breeze.createAttributes()
                .add(Attributes.MAX_HEALTH, 150.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn) {

        for (int i = 0; i < 5; i++) {
            WildSpiderEntity entity = OccultismEntities.WILD_SPIDER.get().create(this.level());
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.absMoveTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            level.addFreshEntity(entity);
        }

        for (int i = 0; i < 5; i++) {
            WildStrayEntity entity = OccultismEntities.WILD_STRAY.get().create(this.level());
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.absMoveTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            level.addFreshEntity(entity);
        }

        for (int i = 0; i < 5; i++) {
            WildCaveSpiderEntity entity = OccultismEntities.WILD_CAVE_SPIDER.get().create(this.level());
            EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

            double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
            entity.absMoveTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(Component.literal(TextUtil.generateName()));
            level.addFreshEntity(entity);
        }

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        TagKey<EntityType<?>> wildTrialTag = OccultismTags.Entities.WILD_TRIAL;

        Entity trueSource = source.getEntity();
        if (trueSource != null && trueSource.getType().is(wildTrialTag))
            return true;

        Entity immediateSource = source.getDirectEntity();
        if (immediateSource != null && immediateSource.getType().is(wildTrialTag))
            return true;

        return super.isInvulnerableTo(source);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }
    //endregion Static Methods
}
