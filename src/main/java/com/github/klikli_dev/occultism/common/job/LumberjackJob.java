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

package com.github.klikli_dev.occultism.common.job;

import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import com.github.klikli_dev.occultism.common.entity.ai.FellTreesGoal;
import com.github.klikli_dev.occultism.common.entity.ai.PickupItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.DepositItemsGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.common.misc.ItemTagComparator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;

import java.util.ArrayList;
import java.util.List;

public class LumberjackJob extends SpiritJob {

    //region Fields
    protected PickupItemsGoal pickupItemsGoal;
    protected FellTreesGoal fellTreesGoal;
    protected DepositItemsGoal depositItemsGoal;
    protected List<IItemStackComparator> itemsToPickUp = new ArrayList<>();
    //endregion Fields


    //region Initialization
    public LumberjackJob(SpiritEntity entity) {
        super(entity);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void init() {
        this.entity.targetSelector.addGoal(0, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(3, this.fellTreesGoal = new FellTreesGoal(this.entity));
        this.entity.goalSelector.addGoal(4, this.depositItemsGoal = new DepositItemsGoal(this.entity));

        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LOGS));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.LEAVES));
        this.itemsToPickUp.add(new ItemTagComparator(ItemTags.SAPLINGS));
        this.itemsToPickUp.add(new ItemStackComparator(new ItemStack(Items.STICK), false));
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
        this.entity.goalSelector.removeGoal(this.fellTreesGoal);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        for (IItemStackComparator comparator : this.itemsToPickUp) {
            if (comparator.matches(stack))
                return true;
        }
        return false;
    }
    //endregion Overrides
}
