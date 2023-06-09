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

package com.klikli_dev.occultism.common.entity.ai.goal;

import com.klikli_dev.occultism.common.entity.ai.EntitySorter;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.google.common.base.Predicate;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class PickupItemsGoal extends TargetGoal {

    protected final SpiritEntity entity;
    protected final Predicate<? super ItemEntity> targetItemSelector;
    protected final EntitySorter entitySorter;
    protected ItemEntity targetItem;
    protected int executionChance;
    protected float pickupRange;

    public PickupItemsGoal(SpiritEntity entity) {
        this(entity, 2f, 10);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public PickupItemsGoal(SpiritEntity entity, float pickupRange, int executionChance) {
        super(entity, false, false);
        this.entity = entity;
        this.pickupRange = pickupRange;
        this.executionChance = executionChance;
        this.targetItemSelector = new Predicate<ItemEntity>() {

            @Override
            public boolean apply(@Nullable ItemEntity item) {
                ItemStack stack = item.getItem();
                return !stack.isEmpty() && entity.canPickupItem(item) && ItemHandlerHelper.insertItemStacked(
                        entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new), stack, true).getCount() <
                        stack.getCount();
            }

        };
        this.entitySorter = new EntitySorter(entity);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {

        //fire on a slow tick based on chance
        long worldTime = this.mob.level().getGameTime() % 10;
        if (this.entity.getNoActionTime() >= 100 && worldTime != 0) {
            return false;
        }
        if (this.executionChance > 0 && this.entity.getRandom().nextInt(this.executionChance) != 0 && worldTime != 0) {
            return false;
        }

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        AABB targetBox = new AABB(-workAreaSize, -workAreaSize / 2.0, -workAreaSize, workAreaSize,
                workAreaSize / 2.0, workAreaSize).move(this.entity.getWorkAreaCenter());

        List<ItemEntity> list = this.mob.level()
                .getEntitiesOfClass(ItemEntity.class, targetBox, this.targetItemSelector);
        if (list.isEmpty()) {
            return false;
        } else {
            list.sort(this.entitySorter);
            this.targetItem = list.get(0);
            return true;
        }
    }

    @Override
    public void tick() {
        if (this.targetItem == null || !this.targetItem.isAlive()) {
            this.stop();
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.mob.getNavigation().createPath(this.targetItem, 0), 1.0f);
            double distance = this.entity.position().distanceTo(this.targetItem.position());
            if (distance < this.pickupRange) {
                this.entity.setDeltaMovement(0, 0, 0);
                this.entity.getNavigation().stop();

                ItemStack duplicate = this.targetItem.getItem().copy();
                ItemStackHandler handler = this.entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new);
                if (ItemHandlerHelper.insertItemStacked(handler, duplicate, true).getCount() < duplicate.getCount()) {
                    ItemStack remaining = ItemHandlerHelper.insertItemStacked(handler, duplicate, false);
                    this.targetItem.getItem().setCount(remaining.getCount());
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.mob.getNavigation().createPath(this.targetItem, 0), 1.0f);
        super.start();
    }

}
