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

package com.klikli_dev.occultism.common.entity.familiar;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class OtherworldBirdEntity extends Parrot implements IFamiliar {

    // region Fields
    public static final float MAX_BOOST_DISTANCE = 8f;

    public SitWhenOrderedToGoal sitGoal;
    // endregion Fields

    // region Initialization
    public OtherworldBirdEntity(EntityType<? extends Parrot> type, Level worldIn) {
        super(type, worldIn);
    }
    // endregion Initialization

    //region Static Methods
    public static AttributeSupplier.Builder createAttributes() {
        return Parrot.createAttributes();
    }
    // endregion Getter / Setter

    // region Overrides

    // region Getter / Setter

    @Override
    protected void registerGoals() {
        // same as parrot, except we don't land on shoulders.
        this.sitGoal = new SitWhenOrderedToGoal(this);
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }

    @Override
    public void aiStep() {
        // Every 10 ticks, attempt to refresh the owner buff
        if (!this.level().isClientSide && this.level().getGameTime() % 10 == 0 && this.isTame()) {
            LivingEntity owner = this.getOwner();
            if (owner != null && this.distanceTo(owner) < MAX_BOOST_DISTANCE) {
                // close enough to boost
                for (MobEffectInstance effect : this.getFamiliarEffects())
                    owner.addEffect(effect);
            }
        }

        super.aiStep();
    }

    @Override
    public void setOwnerUUID(@Nullable UUID ownerId) {
        super.setOwnerUUID(ownerId);
    }

    @Override
    public LivingEntity getFamiliarOwner() {
        return this.getOwner();
    }

    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        this.setOwnerUUID(owner.getUUID());
    }

    @Override
    public Entity getFamiliarEntity() {
        return this;
    }

    @Override
    public Iterable<MobEffectInstance> getFamiliarEffects() {
        return ImmutableList.of(new MobEffectInstance(MobEffects.JUMP, 60, 5, false, false),
                new MobEffectInstance(MobEffects.SLOW_FALLING,
                        20 * Occultism.SERVER_CONFIG.spiritJobs.drikwingFamiliarSlowFallingSeconds.get(), 0, false,
                        false),
                new MobEffectInstance(OccultismEffects.DOUBLE_JUMP.get(), 120, 4, false, false));
    }
    // endregion Overrides

    @Override
    public InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (stack.getItem() == OccultismItems.FAMILIAR_RING.get()) {
            return stack.interactLivingEntity(playerIn, this, hand);
        }
        return super.mobInteract(playerIn, hand);
    }
//endregion Static Methods
}
