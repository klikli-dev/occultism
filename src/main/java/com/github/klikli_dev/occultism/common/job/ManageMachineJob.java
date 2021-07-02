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

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.entity.ai.DepositItemsGoal;
import com.github.klikli_dev.occultism.common.entity.ai.FallbackDepositToControllerGoal;
import com.github.klikli_dev.occultism.common.entity.ai.ManageMachineGoal;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.misc.DepositOrder;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayDeque;
import java.util.Queue;

public class ManageMachineJob extends SpiritJob {
    //region Fields
    protected DepositItemsGoal depositItemsGoal;
    protected ManageMachineGoal manageMachineGoal;
    protected FallbackDepositToControllerGoal fallbackDepositToControllerGoal;
    protected OpenDoorGoal openDoorGoal;
    protected GlobalBlockPos storageControllerPosition;
    protected MachineReference managedMachine;
    protected DepositOrder currentDepositOrder;
    protected Queue<DepositOrder> depositOrderQueue = new ArrayDeque<>();
    protected IStorageController storageController;
    protected TileEntity managedMachineTileEntity;

    //endregion Fields
    //region Initialization
    public ManageMachineJob(SpiritEntity entity) {
        super(entity);
    }
    //endregion Initialization

    //region Getter / Setter
    public DepositOrder getCurrentDepositOrder() {
        return this.currentDepositOrder;
    }

    public void setCurrentDepositOrder(DepositOrder order) {
        this.currentDepositOrder = order;
    }

    public MachineReference getManagedMachine() {
        return this.managedMachine;
    }

    public void setManagedMachine(MachineReference managedMachine) {
        if (this.managedMachine != null)
            this.unregisterFromStorageController();

        this.managedMachine = managedMachine;
        this.managedMachineTileEntity = null; //reset, next call to get will refill it based on the new managed machine.
        this.clearAllOrders();
        this.registerWithStorageController();
    }

    public GlobalBlockPos getStorageControllerPosition() {
        return this.storageControllerPosition;
    }

    public void setStorageControllerPosition(GlobalBlockPos storageControllerPosition) {
        if (this.storageControllerPosition != null) {
            this.unregisterFromStorageController();
        }
        this.storageControllerPosition = storageControllerPosition;
        this.storageController = null; //reset, register will re-fill it if the position is valid.
        this.clearAllOrders();
        this.registerWithStorageController();
    }

    public IStorageController getStorageController() {
        if (this.storageControllerPosition == null)
            return null;

        if (this.storageController == null) {
            this.storageController = (IStorageController) TileEntityUtil.get(this.entity.world,
                    this.storageControllerPosition);
        }

        if (this.storageController == null)
            this.storageControllerPosition = null;
        return this.storageController;
    }

    public TileEntity getManagedMachineTileEntity() {
        if (this.managedMachine == null)
            return null;

        if (this.managedMachineTileEntity == null) {
            this.managedMachineTileEntity = TileEntityUtil.get(this.entity.world, this.managedMachine.globalPos);

        }

        return this.managedMachineTileEntity;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public void init() {
        this.entity.getNavigator().getNodeProcessor().setCanEnterDoors(true);
        ((GroundPathNavigator) this.entity.getNavigator()).setBreakDoors(true);
        this.entity.goalSelector.addGoal(3, this.manageMachineGoal = new ManageMachineGoal(this.entity, this));
        this.entity.goalSelector.addGoal(4,
                this.fallbackDepositToControllerGoal = new FallbackDepositToControllerGoal(this.entity, this));
        this.entity.goalSelector.addGoal(4, this.depositItemsGoal = new DepositItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(5, this.openDoorGoal = new OpenDoorGoal(this.entity, true));
        this.registerWithStorageController();
    }

    @Override
    public void cleanup() {
        this.entity.getNavigator().getNodeProcessor().setCanEnterDoors(false);
        ((GroundPathNavigator) this.entity.getNavigator()).setBreakDoors(false);
        this.entity.goalSelector.removeGoal(this.depositItemsGoal);
        this.entity.goalSelector.removeGoal(this.manageMachineGoal);
        this.entity.goalSelector.removeGoal(this.openDoorGoal);
        this.entity.goalSelector.removeGoal(this.fallbackDepositToControllerGoal);

        this.unregisterFromStorageController();
    }

    @Override
    public void update() {
        //we are basically inactive until we have both a storage controller and a managed machine.
        if (this.storageControllerPosition != null && this.managedMachine != null) {
            //if we don't have an order and there is one available, take it from queue.
            if (this.getCurrentDepositOrder() == null && !this.depositOrderQueue.isEmpty()) {
                this.setCurrentDepositOrder(this.depositOrderQueue.poll());
            }
        }

        if (this.currentDepositOrder != null && this.currentDepositOrder.comparator == null)
            this.setCurrentDepositOrder(null);
    }

    @Override
    public CompoundNBT writeJobToNBT(CompoundNBT compound) {
        if (this.storageControllerPosition != null)
            compound.put("storageControllerPosition", this.storageControllerPosition.serializeNBT());

        if (this.managedMachine != null)
            compound.put("managedMachine", this.managedMachine.serializeNBT());

        if (this.getCurrentDepositOrder() != null)
            compound.put("currentDepositOrder", this.getCurrentDepositOrder().writeToNBT(new CompoundNBT()));

        ListNBT nbtOrderList = new ListNBT();
        for (DepositOrder depositOrder : this.depositOrderQueue) {
            nbtOrderList.add(depositOrder.writeToNBT(new CompoundNBT()));
        }
        compound.put("depositOrders", nbtOrderList);
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(CompoundNBT compound) {
        if (compound.contains("storageControllerPosition"))
            this.storageControllerPosition = GlobalBlockPos.from(compound.getCompound("storageControllerPosition"));

        if (compound.contains("managedMachine"))
            this.managedMachine = MachineReference.from(compound.getCompound("managedMachine"));

        if (compound.contains("currentDepositOrder"))
            this.setCurrentDepositOrder(DepositOrder.from(compound.getCompound("currentDepositOrder")));

        this.depositOrderQueue = new ArrayDeque<>();
        if (compound.contains("depositOrders")) {
            ListNBT nbtOrderList = compound.getList("depositOrders", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < nbtOrderList.size(); i++) {
                DepositOrder depositOrder = DepositOrder.from(nbtOrderList.getCompound(i));
                this.depositOrderQueue.add(depositOrder);
            }
        }

        super.readJobFromNBT(compound);
    }

    //region Methods
    public void addDepsitOrder(DepositOrder order) {
        this.depositOrderQueue.add(order);
    }

    public void clearAllOrders() {
        this.currentDepositOrder = null;
        this.depositOrderQueue.clear();
    }

    protected void registerWithStorageController() {
        IStorageController storageController = this.getStorageController();

        if (storageController != null && this.managedMachine != null) {
            storageController.addDepositOrderSpirit(this.managedMachine.globalPos, this.entity.getUniqueID());
            storageController.linkMachine(this.managedMachine);
            TileEntityUtil.updateTile(this.entity.world, this.getStorageControllerPosition().getPos());
        }
    }

    protected void unregisterFromStorageController() {
        if (this.storageControllerPosition != null && this.managedMachine != null) {
            IStorageController storageController = this.getStorageController();
            if (storageController != null)
                storageController.removeDepositOrderSpirit(this.managedMachine.globalPos);
        }
    }
    //endregion Methods
}
