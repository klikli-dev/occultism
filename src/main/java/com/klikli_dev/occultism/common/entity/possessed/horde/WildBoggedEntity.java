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

import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class WildBoggedEntity extends Bogged {

    protected Optional<PossessedStrongBreezeEntity> master = Optional.empty();

    public WildBoggedEntity(EntityType<? extends Bogged> type,
                            Level worldIn) {
        super(type, worldIn);
    }

    //region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return Bogged.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.ARMOR,15)
                .add(Attributes.KNOCKBACK_RESISTANCE,0.75);
    }

    public void setMaster(PossessedStrongBreezeEntity master) {
        this.master = Optional.ofNullable(master);
    }

    @Override
    public void remove(RemovalReason reason) {
        this.master.ifPresent(boss -> {
            boss.notifyMinionDeath(this);
        });
        super.remove(reason);
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

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }
}
