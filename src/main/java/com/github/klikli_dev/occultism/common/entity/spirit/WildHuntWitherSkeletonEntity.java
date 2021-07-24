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

package com.github.klikli_dev.occultism.common.entity.spirit;

import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.SkeletonEntity;
import net.minecraft.world.entity.monster.WitherSkeletonEntity;
import net.minecraft.level.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraft.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WildHuntWitherSkeletonEntity extends WitherSkeletonEntity {
    //region Fields
    List<WildHuntSkeletonEntity> minions = new ArrayList<>();
    //endregion Fields

    //region Initialization
    public WildHuntWitherSkeletonEntity(EntityType<? extends WildHuntWitherSkeletonEntity> type,
                                        Level worldIn) {
        super(type, worldIn);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason,
                                            @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        int maxSkeletons = 3 + level.getRandom().nextInt(6);

        for (int i = 0; i < maxSkeletons; i++) {
            WildHuntSkeletonEntity entity = OccultismEntities.WILD_HUNT_SKELETON.get().create(this.level);
            entity.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
            double offsetX = (level.getRandom().nextGaussian() - 1.0) * (1 + level.getRandom().nextInt(4));
            double offsetZ = (level.getRandom().nextGaussian() - 1.0) * (1 + level.getRandom().nextInt(4));
            entity.absMoveTo(this.getPosX() + offsetX, this.getPosY() + 1.5, this.getPosZ() + offsetZ,
                    level.getRandom().nextInt(360), 0);
            entity.setCustomName(new TextComponent(TextUtil.generateName()));
            level.addEntity(entity);
            entity.setMaster(this);
            this.minions.add(entity);
        }

        return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    protected boolean isInDaylight() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Tag<EntityType<?>> wildHuntTag = OccultismTags.WILD_HUNT;

        Entity trueSource = source.getTrueSource();
        if (trueSource != null && wildHuntTag.contains(trueSource.getType()))
            return true;

        Entity immediateSource = source.getImmediateSource();
        if (immediateSource != null && wildHuntTag.contains(immediateSource.getType()))
            return true;

        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isInvulnerable() {
        return !this.minions.isEmpty() || super.isInvulnerable();
    }
    //endregion Overrides

    //region Static Methods
    public static AttributeSupplier.Builder createLivingAttributes() {
        return SkeletonEntity.registerAttributes()
                       .add(Attributes.ATTACK_DAMAGE, 6.0)
                       .add(Attributes.MAX_HEALTH, 60.0);
    }
    //endregion Static Methods

    //region Methods
    public void notifyMinionDeath(WildHuntSkeletonEntity minion) {
        this.minions.remove(minion);
    }
    //endregion Methods
}
