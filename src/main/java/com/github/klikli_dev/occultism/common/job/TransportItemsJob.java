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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TransportItemsJob extends SpiritJob implements INamedContainerProvider {
    //region Fields
    public static final int MAX_FILTER_SLOTS = 7;
    protected final ItemStackHandler itemFilter;
    protected DepositItemsGoal depositItemsGoal;
    protected ExtractItemsGoal extractItemsGoal;
    protected OpenDoorGoal openDoorGoal;
    protected boolean isFilterBlacklist;

    //endregion Fields
    //region Initialization
    public TransportItemsJob(SpiritEntity entity) {
        super(entity);

        this.isFilterBlacklist = false;
        this.itemFilter = new ItemStackHandler(MAX_FILTER_SLOTS);
    }
    //endregion Initialization

    //region Getter / Setter
    public boolean isFilterBlacklist() {
        return this.isFilterBlacklist;
    }

    public void setFilterBlacklist(boolean filterBlacklist) {
        this.isFilterBlacklist = filterBlacklist;
    }

    public ItemStackHandler getItemFilter() {
        return this.itemFilter;
    }
    //endregion Getter / Setter

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
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        compound.putBoolean("isFilterBlacklist", isFilterBlacklist);
        compound.put("itemFilter", this.itemFilter.serializeNBT());
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        super.readJobFromNBT(compound);
        if(compound.contains("isFilterBlacklist"))
            this.isFilterBlacklist = compound.getBoolean("isFilterBlacklist");
        if(compound.contains("itemFilter"))
            this.itemFilter.deserializeNBT(compound.getCompound("itemFilter"));
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
