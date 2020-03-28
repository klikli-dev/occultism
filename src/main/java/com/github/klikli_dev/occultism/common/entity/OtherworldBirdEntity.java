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

package com.github.klikli_dev.occultism.common.entity;

import com.github.klikli_dev.occultism.registry.OccultismEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class OtherworldBirdEntity extends ParrotEntity {

    //region Fields
    public static final float MAX_BOOST_DISTANCE = 8f;
    public LivingEntity ownerCached;
    //endregion Fields


    //region Initialization
    public OtherworldBirdEntity(EntityType<? extends ParrotEntity> type,
                                World worldIn) {
        super(type, worldIn);
    }
    //endregion Initialization

    //region Getter / Setter
    public LivingEntity getOwnerCached() {
        if (this.ownerCached != null)
            return this.ownerCached;
        this.ownerCached = this.getOwner();
        return this.ownerCached;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public void livingTick() {
        //Every 10 ticks, attempt to refresh the owner buff
        if (this.world.getGameTime() % 10 == 0 && this.isTamed()) {
            LivingEntity owner = this.getOwnerCached();
            if (owner != null && this.getDistance(owner) < MAX_BOOST_DISTANCE) {
                //close enough to boost
                owner.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 60, 5));
                owner.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 3600));
                owner.addPotionEffect(new EffectInstance(OccultismEffects.DOUBLE_JUMP.get(), 3600, 4));
            }
        }

        super.livingTick();
    }

    @Override
    public void setOwnerId(@Nullable UUID ownerId) {
        this.ownerCached = null;
        super.setOwnerId(ownerId);
    }
    //endregion Overrides
}
