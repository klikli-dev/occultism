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

package com.github.klikli_dev.occultism.common.jobs;

import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIDepositItems;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIFellTrees;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIPickupItems;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class SpiritJobLumberjack extends SpiritJob {

    //region Fields
    protected SpiritAIPickupItems aiPickupItems;
    protected SpiritAIFellTrees aiFellTree;
    protected SpiritAIDepositItems aiDepositItems;
    protected NonNullList<ItemStack> itemsToPickUp = NonNullList.create();
    //endregion Fields


    //region Initialization
    public SpiritJobLumberjack(EntitySpirit entity) {
        super(entity);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void init() {
        this.entity.targetTasks.addTask(0, this.aiPickupItems = new SpiritAIPickupItems(this.entity));
        this.entity.tasks.addTask(3, this.aiFellTree = new SpiritAIFellTrees(this.entity));
        this.entity.tasks.addTask(4, this.aiDepositItems = new SpiritAIDepositItems(this.entity));

        this.itemsToPickUp.addAll(OreDictionary.getOres("treeLeaves"));
        this.itemsToPickUp.addAll(OreDictionary.getOres("logWood"));
        this.itemsToPickUp.addAll(OreDictionary.getOres("treeSapling"));
    }

    @Override
    public void cleanup() {
        this.entity.targetTasks.removeTask(this.aiPickupItems);
        this.entity.tasks.removeTask(this.aiFellTree);
        this.entity.tasks.removeTask(this.aiDepositItems);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        for (ItemStack itemToPickUp : this.itemsToPickUp) {
            if (OreDictionary
                        .itemMatches(itemToPickUp, stack, itemToPickUp.getMetadata() != OreDictionary.WILDCARD_VALUE))
                return true;
        }
        return false;
    }
    //endregion Overrides
}
