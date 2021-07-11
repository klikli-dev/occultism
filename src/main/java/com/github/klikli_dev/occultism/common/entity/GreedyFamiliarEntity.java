/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

import java.util.EnumSet;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class GreedyFamiliarEntity extends FamiliarEntity {

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FindItemGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    private static class FindItemGoal extends Goal {

        private static final double RANGE = 12;

        private GreedyFamiliarEntity entity;

        public FindItemGoal(GreedyFamiliarEntity raptor) {
            this.entity = raptor;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return this.getNearbyItem() != null && this.entity.getFamiliarOwner() instanceof PlayerEntity;
        }

        @Override
        public void startExecuting() {
            ItemEntity item = this.getNearbyItem();
            if (item != null)
                this.entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
        }

        @Override
        public void tick() {
            ItemEntity item = this.getNearbyItem();
            if (item != null) {
                this.entity.getNavigator().tryMoveToEntityLiving(item, 1.2);
                LivingEntity owner = this.entity.getFamiliarOwner();
                if (item.getDistanceSq(this.entity) < 4 && owner instanceof PlayerEntity)
                    item.onCollideWithPlayer(((PlayerEntity) owner));
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (!(owner instanceof PlayerEntity))
                return null;

            PlayerEntity player = (PlayerEntity) owner;
            IItemHandler inv = new PlayerMainInvWrapper(player.inventory);

            for (ItemEntity item : this.entity.world.getEntitiesWithinAABB(ItemEntity.class,
                    this.entity.getBoundingBox().grow(RANGE))) {
                ItemStack stack = item.getItem();
                boolean isDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                if (!isDemagnetized
                        && ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }
}
