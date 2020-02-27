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

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIDepositItems;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIFallbackDepositToController;
import com.github.klikli_dev.occultism.common.entity.ai.SpiritAIManageMachine;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayDeque;
import java.util.Queue;

public class SpiritJobManageMachine extends SpiritJob {
    //region Fields
    protected SpiritAIDepositItems aiDepositItems;
    protected SpiritAIManageMachine aiHandleDepositOrders;
    protected SpiritAIFallbackDepositToController aiFallbackDepositToController;
    protected EntityAIOpenDoor aiOpenDoor;
    protected GlobalBlockPos storageControllerPosition;
    protected MachineReference managedMachine;
    protected DepositOrder currentDepositOrder;
    protected Queue<DepositOrder> depositOrderQueue = new ArrayDeque<>();
    protected IStorageController storageController;
    protected TileEntity managedMachineTileEntity;

    //endregion Fields
    //region Initialization
    public SpiritJobManageMachine(EntitySpirit entity) {
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
        ((PathNavigateGround) this.entity.getNavigator()).setEnterDoors(true);
        ((PathNavigateGround) this.entity.getNavigator()).setBreakDoors(true);
        this.entity.tasks.addTask(3, this.aiHandleDepositOrders = new SpiritAIManageMachine(this.entity, this));
        this.entity.tasks.addTask(4,
                this.aiFallbackDepositToController = new SpiritAIFallbackDepositToController(this.entity, this));
        this.entity.tasks.addTask(4, this.aiDepositItems = new SpiritAIDepositItems(this.entity));
        this.entity.tasks.addTask(5, this.aiOpenDoor = new EntityAIOpenDoor(this.entity, true));
        this.registerWithStorageController();
    }

    @Override
    public void cleanup() {
        ((PathNavigateGround) this.entity.getNavigator()).setEnterDoors(false);
        ((PathNavigateGround) this.entity.getNavigator()).setBreakDoors(false);
        this.entity.tasks.removeTask(this.aiDepositItems);
        this.entity.tasks.removeTask(this.aiHandleDepositOrders);
        this.entity.tasks.removeTask(this.aiOpenDoor);
        this.entity.tasks.removeTask(this.aiFallbackDepositToController);

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
    public NBTTagCompound writeJobToNBT(NBTTagCompound compound) {
        if (this.storageControllerPosition != null)
            compound.setTag("storageControllerPosition",
                    this.storageControllerPosition.writeToNBT(new NBTTagCompound()));

        if (this.managedMachine != null)
            compound.setTag("managedMachine", this.managedMachine.writeToNBT(new NBTTagCompound()));

        if (this.getCurrentDepositOrder() != null)
            compound.setTag("currentDepositOrder", this.getCurrentDepositOrder().writeToNBT(new NBTTagCompound()));

        NBTTagList nbtOrderList = new NBTTagList();
        for (DepositOrder depositOrder : this.depositOrderQueue) {
            nbtOrderList.appendTag(depositOrder.writeToNBT(new NBTTagCompound()));
        }
        compound.setTag("depositOrders", nbtOrderList);
        return super.writeJobToNBT(compound);
    }

    @Override
    public void readJobFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("storageControllerPosition"))
            this.storageControllerPosition = GlobalBlockPos
                                                     .fromNbt(compound.getCompoundTag("storageControllerPosition"));

        if (compound.hasKey("managedMachine"))
            this.managedMachine = MachineReference.fromNbt(compound.getCompoundTag("managedMachine"));

        if (compound.hasKey("currentDepositOrder"))
            this.setCurrentDepositOrder(DepositOrder.fromNbt(compound.getCompoundTag("currentDepositOrder")));

        this.depositOrderQueue = new ArrayDeque<>();
        if (compound.hasKey("depositOrders")) {
            NBTTagList nbtOrderList = compound.getTagList("depositOrders", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < nbtOrderList.tagCount(); i++) {
                DepositOrder depositOrder = DepositOrder.fromNbt(nbtOrderList.getCompoundTagAt(i));
                this.depositOrderQueue.add(depositOrder);
            }
        }

        super.readJobFromNBT(compound);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
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
