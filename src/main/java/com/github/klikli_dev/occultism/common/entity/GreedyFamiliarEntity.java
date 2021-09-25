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

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.EnumSet;
import java.util.List;

public class GreedyFamiliarEntity extends FamiliarEntity {

    public GreedyFamiliarEntity(EntityType<? extends GreedyFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new RideDragonGoal(this));
        this.goalSelector.addGoal(3, new FindItemGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        return ImmutableList.of();
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (!(wearer instanceof PlayerEntity))
            return;

        if (this.isEffectEnabled())
            for (ItemEntity e : wearer.level.getEntitiesOfClass(ItemEntity.class, wearer.getBoundingBox().inflate(5),
                    e -> e.isAlive())) {
                ItemStack stack = e.getItem();

                boolean isStackDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = e.getPersistentData().getBoolean("PreventRemoteMovement");

                if (!isStackDemagnetized && !isEntityDemagnetized) {
                    e.playerTouch((PlayerEntity) wearer);
                }
            }
    }

    public static class FindItemGoal extends Goal {

        private static final double RANGE = 12;

        private final FamiliarEntity entity;

        public FindItemGoal(FamiliarEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.getNearbyItem() != null && this.entity.getFamiliarOwner() instanceof PlayerEntity;
        }

        @Override
        public void start() {
            ItemEntity item = this.getNearbyItem();
            if (item != null)
                this.entity.getNavigation().moveTo(item, 1.2);
        }

        @Override
        public void tick() {
            ItemEntity item = this.getNearbyItem();
            if (item != null) {
                this.entity.getNavigation().moveTo(item, 1.2);
                LivingEntity owner = this.entity.getFamiliarOwner();
                if (item.distanceToSqr(this.entity) < 4 && owner instanceof PlayerEntity) {
                    item.playerTouch(((PlayerEntity) owner));
                    if (this.entity instanceof GreedyFamiliarEntity)
                        OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.GREEDY_ITEM);
                    else if (this.entity instanceof DragonFamiliarEntity)
                        OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.DRAGON_RIDE);
                }
            }
        }

        private ItemEntity getNearbyItem() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            if (!(owner instanceof PlayerEntity))
                return null;

            PlayerEntity player = (PlayerEntity) owner;
            IItemHandler inv = new PlayerMainInvWrapper(player.inventory);

            for (ItemEntity item : this.entity.level.getEntitiesOfClass(ItemEntity.class,
                    this.entity.getBoundingBox().inflate(RANGE), e -> e.isAlive())) {
                ItemStack stack = item.getItem();

                boolean isStackDemagnetized = stack.hasTag() && stack.getTag().getBoolean("PreventRemoteMovement");
                boolean isEntityDemagnetized = item.getPersistentData().getBoolean("PreventRemoteMovement");

                if ((!isStackDemagnetized && !isEntityDemagnetized)
                        && ItemHandlerHelper.insertItemStacked(inv, stack, true).getCount() != stack.getCount())
                    return item;
            }
            return null;
        }

    }

    private static class RideDragonGoal extends Goal {

        private final GreedyFamiliarEntity greedy;
        private DragonFamiliarEntity dragon;

        private RideDragonGoal(GreedyFamiliarEntity greedy) {
            this.greedy = greedy;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (this.greedy.getVehicle() instanceof DragonFamiliarEntity)
                return true;

            DragonFamiliarEntity dragon = this.findDragon();
            if (dragon != null) {
                this.dragon = dragon;
                this.greedy.getNavigation().moveTo(dragon, 1);
                return true;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return this.greedy.getVehicle() instanceof DragonFamiliarEntity
                    || (this.greedy.isPathFinding() && this.dragon != null);
        }

        @Override
        public void stop() {
            this.dragon = null;
        }

        @Override
        public void tick() {
            if (this.dragon != null && this.greedy.distanceToSqr(this.dragon) < 5) {
                this.greedy.startRiding(this.dragon);
            }
        }

        private DragonFamiliarEntity findDragon() {
            LivingEntity owner = this.greedy.getOwner();
            if (owner == null)
                return null;

            List<DragonFamiliarEntity> dragons = this.greedy.level.getEntitiesOfClass(DragonFamiliarEntity.class,
                    this.greedy.getBoundingBox().inflate(5),
                    e -> e.getFamiliarOwner() == owner && !e.isVehicle() && !e.isSitting());
            if (dragons.isEmpty())
                return null;
            return dragons.get(0);
        }
    }

}
