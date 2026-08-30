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
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;

public class PossessedEvokerEntity extends Evoker implements PossessedMob {

    public PossessedEvokerEntity(EntityType<? extends Evoker> type,
                                 Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static Builder createAttributes() {
        return Evoker.createAttributes()
                .add(Attributes.MAX_HEALTH, 42.0)
                .add(Attributes.ARMOR, 7);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, EntitySpawnReason reason,
                                        @Nullable SpawnGroupData spawnDataIn) {

        if (reason == EntitySpawnReason.MOB_SUMMONED && level.getLevel().getGameRules().get(GameRules.SPAWN_MOBS)) {
            for (int i = 0; i < 2; i++) {
                Vindicator entity = EntityTypes.VINDICATOR.create(this.level(), EntitySpawnReason.REINFORCEMENT);
                EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

                double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                        level.getRandom().nextInt(360), 0);
                entity.setCustomName(Component.literal(TextUtil.generateName()));
                level.addFreshEntity(entity);
            }

            for (int i = 0; i < 5; i++) {
                Pillager entity = EntityTypes.PILLAGER.create(this.level(), EntitySpawnReason.REINFORCEMENT);
                EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

                double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                        level.getRandom().nextInt(360), 0);
                entity.setCustomName(Component.literal(TextUtil.generateName()));
                level.addFreshEntity(entity);
            }

            for (int i = 0; i < 1; i++) {
                Illusioner entity = EntityTypes.ILLUSIONER.create(this.level(), EntitySpawnReason.REINFORCEMENT);
                EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

                double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                        level.getRandom().nextInt(360), 0);
                entity.setCustomName(Component.literal(TextUtil.generateName()));
                level.addFreshEntity(entity);
            }

            for (int i = 0; i < 1; i++) {
                Ravager entity = EntityTypes.RAVAGER.create(this.level(), EntitySpawnReason.REINFORCEMENT);
                EventHooks.finalizeMobSpawn(entity, level, difficultyIn, reason, spawnDataIn);

                double offsetX = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                double offsetZ = level.getRandom().nextGaussian() * (1 + level.getRandom().nextInt(4));
                entity.snapTo(this.getBlockX() + offsetX, this.getBlockY() + 1.5, this.getBlockZ() + offsetZ,
                        level.getRandom().nextInt(360), 0);
                entity.setCustomName(Component.literal(TextUtil.generateName()));
                entity.setHealth(10);
                level.addFreshEntity(entity);
            }
        }
        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn);
    }
    //endregion Static Methods

    @Override
    public EntityType basedMob() {
        return EntityTypes.EVOKER;
    }
}
