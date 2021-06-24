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
import com.github.klikli_dev.occultism.common.entity.ai.ExtractItemsGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TransportItemsJob extends SpiritJob implements INamedContainerProvider {
    //region Fields

    protected DepositItemsGoal depositItemsGoal;
    protected ExtractItemsGoal extractItemsGoal;
    protected OpenDoorGoal openDoorGoal;
    //endregion Fields

    //region Initialization


    public TransportItemsJob(SpiritEntity entity) {
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
        this.entity.getNavigator().getNodeProcessor().setCanEnterDoors(true);
        ((GroundPathNavigator) this.entity.getNavigator()).setBreakDoors(true);
        this.entity.goalSelector.addGoal(3, this.depositItemsGoal = new DepositItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(4, this.extractItemsGoal = new ExtractItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(5, this.openDoorGoal = new OpenDoorGoal(this.entity, true));
        //TODO: register data
    }

    @Override
    public void cleanup() {
        this.entity.getNavigator().getNodeProcessor().setCanEnterDoors(false);
        ((GroundPathNavigator) this.entity.getNavigator()).setBreakDoors(false);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
        this.entity.goalSelector.removeGoal(this.extractItemsGoal);
        this.entity.goalSelector.removeGoal(this.openDoorGoal);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpiritTransporterContainer(id, playerInventory, this.entity);
    }
    //endregion Overrides
}
