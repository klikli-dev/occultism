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

import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.google.common.base.Predicate;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;

public class SpiritAIPickupItems extends EntityAITarget {

    //region Fields

    protected final EntitySpirit entity;
    protected final Predicate<? super EntityItem> targetItemSelector;
    protected final EntitySorter entitySorter;
    protected EntityItem targetItem;
    protected int executionChance;
    //endregion Fields

    //region Initialization

    public SpiritAIPickupItems(EntitySpirit entity) {
        this(entity, 10);
    }

    public SpiritAIPickupItems(EntitySpirit entity, int executionChance) {
        super(entity, false, false);
        this.entity = entity;
        this.executionChance = executionChance;
        this.targetItemSelector = new Predicate<EntityItem>() {
            //region Overrides
            @Override
            public boolean apply(@Nullable EntityItem item) {
                ItemStack stack = item.getItem();
                return !stack.isEmpty() && entity.canPickupItem(stack);
            }
            //endregion Overrides
        };
        this.entitySorter = new EntitySorter(entity);
        this.setMutexBits(1);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean shouldExecute() {

        //fire on a slow tick based on chance
        long worldTime = this.taskOwner.world.getWorldTime() % 10;
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

        List<EntityItem> list = this.taskOwner.world
                                        .getEntitiesWithinAABB(EntityItem.class, targetBox, this.targetItemSelector);
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
    public void updateTask() {
        super.updateTask();
        //Not sure if items really can be dead, but EntityItem checks for it, so ...
        if (this.targetItem == null || this.targetItem.isDead) {
            this.resetTask();
            this.taskOwner.getNavigator().clearPath();
        }
        else if (this.taskOwner.getDistanceSq(this.targetItem) < 1) {
            ItemStack heldItem = this.entity.getHeldItem(EnumHand.MAIN_HAND);
            if (heldItem.isEmpty() || (ItemHandlerHelper.canItemStacksStack(this.targetItem.getItem(), heldItem) &&
                                       heldItem.getCount() < heldItem.getMaxStackSize())) {
                ItemStack duplicate = this.targetItem.getItem().copy();

                int extractSize = Math.min(heldItem.getMaxStackSize() - heldItem.getCount(),
                        this.targetItem.getItem().getCount());
                duplicate.setCount(extractSize);
                this.targetItem.getItem().shrink(extractSize);
                this.entity.setHeldItem(EnumHand.MAIN_HAND, duplicate);
                this.resetTask();
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.taskOwner.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.taskOwner.getNavigator().tryMoveToXYZ(this.targetItem.posX, this.targetItem.posY, this.targetItem.posZ, 1);
        super.startExecuting();
    }
    //endregion Overrides

}
