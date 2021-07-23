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

package com.github.klikli_dev.occultism.common.entity.ai;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.google.common.base.Predicate;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class PickupItemsGoal extends TargetGoal {

    //region Fields

    protected final SpiritEntity entity;
    protected final Predicate<? super ItemEntity> targetItemSelector;
    protected final EntitySorter entitySorter;
    protected ItemEntity targetItem;
    protected int executionChance;
    //endregion Fields

    //region Initialization

    public PickupItemsGoal(SpiritEntity entity) {
        this(entity, 10);
    }

    public PickupItemsGoal(SpiritEntity entity, int executionChance) {
        super(entity, false, false);
        this.entity = entity;
        this.executionChance = executionChance;
        this.targetItemSelector = new Predicate<ItemEntity>() {
            //region Overrides
            @Override
            public boolean apply(@Nullable ItemEntity item) {
                ItemStack stack = item.getItem();
                return !stack.isEmpty() && entity.canPickupItem(item) && ItemHandlerHelper.insertItemStacked(
                        entity.itemStackHandler.orElseThrow(ItemHandlerMissingException::new), stack, true).getCount() <
                                                                          stack.getCount();
            }
            //endregion Overrides
        };
        this.entitySorter = new EntitySorter(entity);
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean shouldExecute() {

        //fire on a slow tick based on chance
        long worldTime = this.goalOwner.level.getGameTime() % 10;
        if (this.entity.getIdleTime() >= 100 && worldTime != 0) {
            return false;
        }
        if (this.entity.getRNG().nextInt(this.executionChance) != 0 && worldTime != 0) {
            return false;
        }

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        AxisAlignedBB targetBox = new AxisAlignedBB(-workAreaSize, -workAreaSize / 2.0, -workAreaSize, workAreaSize,
                workAreaSize / 2.0, workAreaSize).offset(this.entity.getWorkAreaCenter());

        List<ItemEntity> list = this.goalOwner.level
                                        .getEntitiesWithinAABB(ItemEntity.class, targetBox, this.targetItemSelector);
        if (list.isEmpty()) {
            return false;
        }
        else {
            list.sort(this.entitySorter);
            this.targetItem = list.get(0);
            return true;
        }
    }

    @Override
    public void tick() {
        if (this.targetItem == null || !this.targetItem.isAlive()) {
            this.resetTask();
            this.goalOwner.getNavigator().clearPath();
        }
        else {
            this.goalOwner.getNavigator().setPath(this.goalOwner.getNavigator().pathfind(this.targetItem, 0), 1.0f);
            double distance = this.entity.getPositionVec().distanceTo(this.targetItem.getPositionVec());
            if (distance < 1F) {
                this.entity.setMotion(0, 0, 0);
                this.entity.getNavigator().clearPath();

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
    public boolean shouldContinueExecuting() {
        return !this.goalOwner.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.goalOwner.getNavigator().setPath(this.goalOwner.getNavigator().pathfind(this.targetItem, 0), 1.0f);
        super.startExecuting();
    }
    //endregion Overrides

}
