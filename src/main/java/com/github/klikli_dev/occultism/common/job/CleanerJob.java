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

import com.github.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.github.klikli_dev.occultism.common.entity.ai.DepositItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.PickupItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.ReturnToWorkAreaGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class CleanerJob extends SpiritJob implements INamedContainerProvider {

    //region Fields
    protected PickupItemsGoal pickupItemsGoal;
    protected ReturnToWorkAreaGoal returnToWorkAreaGoal;
    protected DepositItemsGoal depositItemsGoal;
    //endregion Fields


    //region Initialization
    public CleanerJob(SpiritEntity entity) {
        super(entity);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ITextComponent getDisplayName() {
        return this.entity.getDisplayName();
    }

    @Override
    public void init() {
        this.entity.targetSelector.addGoal(0, this.returnToWorkAreaGoal = new ReturnToWorkAreaGoal(this.entity));
        this.entity.targetSelector.addGoal(1, this.pickupItemsGoal = new PickupItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(4, this.depositItemsGoal = new DepositItemsGoal(this.entity));
    }

    @Override
    public void cleanup() {
        this.entity.targetSelector.removeGoal(this.returnToWorkAreaGoal);
        this.entity.targetSelector.removeGoal(this.pickupItemsGoal);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
    }

    @Override
    public boolean canPickupItem(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        boolean matches = StorageUtil.matchesFilter(stack,
                this.entity.getFilterItems().orElseThrow(ItemHandlerMissingException::new)) ||
                StorageUtil.matchesFilter(stack, this.entity.getTagFilter());

        boolean isBlacklist = this.entity.isFilterBlacklist();
        return ((!isBlacklist && matches) || (isBlacklist && !matches));
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpiritTransporterContainer(id, playerInventory, this.entity);
    }
    //endregion Overrides
}
