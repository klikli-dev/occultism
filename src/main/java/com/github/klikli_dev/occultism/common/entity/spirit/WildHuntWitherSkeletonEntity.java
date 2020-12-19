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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import com.github.klikli_dev.occultism.util.TextUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WildHuntWitherSkeletonEntity extends WitherSkeletonEntity {
    //region Fields
    List<WildHuntSkeletonEntity> minions = new ArrayList<>();
    //endregion Fields

    //region Initialization
    public WildHuntWitherSkeletonEntity(EntityType<? extends WildHuntWitherSkeletonEntity> type,
                                        World worldIn) {
        super(type, worldIn);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason,
                                            @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int maxSkeletons = 3 + world.getRandom().nextInt(6);

        for (int i = 0; i < maxSkeletons; i++) {
            WildHuntSkeletonEntity entity = OccultismEntities.WILD_HUNT_SKELETON.get().create(this.world);
            entity.onInitialSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
            double offsetX = (world.getRandom().nextGaussian() - 1.0) * (1 + world.getRandom().nextInt(4));
            double offsetZ = (world.getRandom().nextGaussian() - 1.0) * (1 + world.getRandom().nextInt(4));
            entity.setPositionAndRotation(this.getPosX() + offsetX, this.getPosY() + 1.5, this.getPosZ() + offsetZ,
                    world.getRandom().nextInt(360), 0);
            entity.setCustomName(new StringTextComponent(TextUtil.generateName()));
            world.addEntity(entity);
            entity.setMaster(this);
            this.minions.add(entity);
        }

        return super.onInitialSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
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
        ITag<EntityType<?>> wildHuntTag = OccultismTags.WILD_HUNT;

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
    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return SkeletonEntity.registerAttributes()
                       .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0)
                       .createMutableAttribute(Attributes.MAX_HEALTH, 60.0);
    }
    //endregion Static Methods

    //region Methods
    public void notifyMinionDeath(WildHuntSkeletonEntity minion) {
        this.minions.remove(minion);
    }
    //endregion Methods
}
