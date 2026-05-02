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

package com.klikli_dev.occultism.common.entity.job;

import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.common.entity.ai.goal.DepositItemsGoal;
import com.klikli_dev.occultism.common.entity.ai.goal.FallbackDepositToControllerGoal;
import com.klikli_dev.occultism.common.entity.ai.goal.ManageMachineGoal;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.misc.DepositOrder;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayDeque;
import java.util.Queue;

public class ManageMachineJob extends SpiritJob {

    protected DepositItemsGoal depositItemsGoal;
    protected ManageMachineGoal manageMachineGoal;
    protected FallbackDepositToControllerGoal fallbackDepositToControllerGoal;
    protected OpenDoorGoal openDoorGoal;
    protected GlobalBlockPos storageControllerPosition;
    protected MachineReference managedMachine;
    protected DepositOrder currentDepositOrder;
    protected Queue<DepositOrder> depositOrderQueue = new ArrayDeque<>();
    protected IStorageController storageController;
    protected BlockEntity managedMachineBlockEntity;
    protected BlockEntity extractBlockEntity;

    public ManageMachineJob(SpiritEntity entity) {
        super(entity);
    }

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
        this.managedMachineBlockEntity = null; //reset, next call to get will refill it based on the new managed machine.
        this.extractBlockEntity = null; //reset, next call to get will refill it based on the new managed machine.
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
        return this.resolveCurrentStorageController();
    }

    public BlockEntity getManagedMachineBlockEntity() {
        if (this.managedMachine == null)
            return null;

        if (this.managedMachineBlockEntity == null) {
            this.managedMachineBlockEntity = BlockEntityUtil.get(this.entity.level(), this.managedMachine.insertGlobalPos);

        }

        return this.managedMachineBlockEntity;
    }

    public BlockEntity getExtractBlockEntity() {
        if (this.managedMachine == null)
            return null;

        if (this.extractBlockEntity == null) {
            this.extractBlockEntity = BlockEntityUtil.get(this.entity.level(), this.managedMachine.extractGlobalPos);

        }

        return this.extractBlockEntity;
    }
    //endregion Getter / Setter

    @Override
    public void onInit() {
        this.entity.getNavigation().getNodeEvaluator().setCanPassDoors(true);
        this.entity.getNavigation().setCanOpenDoors(true);
        this.entity.goalSelector.addGoal(3, this.manageMachineGoal = new ManageMachineGoal(this.entity, this));
        this.entity.goalSelector.addGoal(4,
                this.fallbackDepositToControllerGoal = new FallbackDepositToControllerGoal(this.entity, this));
        this.entity.goalSelector.addGoal(4, this.depositItemsGoal = new DepositItemsGoal(this.entity));
        this.entity.goalSelector.addGoal(5, this.openDoorGoal = new OpenDoorGoal(this.entity, true));
        this.registerWithStorageController();
    }

    @Override
    public void cleanup() {
        this.entity.getNavigation().getNodeEvaluator().setCanPassDoors(false);
        this.entity.getNavigation().setCanOpenDoors(false);
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
            this.refreshStorageControllerRegistration();

            //if we don't have an order and there is one available, take it from queue.
            if (this.getCurrentDepositOrder() == null && !this.depositOrderQueue.isEmpty()) {
                this.setCurrentDepositOrder(this.depositOrderQueue.poll());
            }
        }

        if (this.currentDepositOrder != null && this.currentDepositOrder.comparator == null)
            this.setCurrentDepositOrder(null);
    }

    @Override
    public CompoundTag writeJobToNBT(CompoundTag compound, Provider provider) {
        if (this.storageControllerPosition != null)
            compound.put("storageControllerPosition", GlobalBlockPos.CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this.storageControllerPosition).getOrThrow());

        if (this.managedMachine != null)
            compound.put("managedMachine", this.managedMachine.serializeNBT(provider));

        if (this.getCurrentDepositOrder() != null)
            compound.put("currentDepositOrder", this.getCurrentDepositOrder().writeToNBT(new CompoundTag(), provider));

        ListTag nbtOrderList = new ListTag();
        for (DepositOrder depositOrder : this.depositOrderQueue) {
            nbtOrderList.add(depositOrder.writeToNBT(new CompoundTag(), provider));
        }
        compound.put("depositOrders", nbtOrderList);
        return super.writeJobToNBT(compound, provider);
    }

    @Override
    public void readJobFromNBT(CompoundTag compound, Provider provider) {
        if (compound.contains("storageControllerPosition"))
            this.storageControllerPosition = GlobalBlockPos.from(provider, compound.getCompoundOrEmpty("storageControllerPosition"));

        if (compound.contains("managedMachine"))
            this.managedMachine = MachineReference.CODEC.decode(provider.createSerializationContext(NbtOps.INSTANCE), compound.get("managedMachine")).getOrThrow().getFirst();

        if (compound.contains("currentDepositOrder"))
            this.setCurrentDepositOrder(DepositOrder.from(compound.getCompoundOrEmpty("currentDepositOrder"), provider));

        this.depositOrderQueue = new ArrayDeque<>();
        if (compound.contains("depositOrders")) {
            ListTag nbtOrderList = compound.getList("depositOrders").orElse(new ListTag());
            for (int i = 0; i < nbtOrderList.size(); i++) {
                DepositOrder depositOrder = DepositOrder.from(nbtOrderList.getCompoundOrEmpty(i), provider);
                this.depositOrderQueue.add(depositOrder);
            }
        }

        super.readJobFromNBT(compound, provider);
    }

    public void addDepsitOrder(DepositOrder order) {
        this.depositOrderQueue.add(order);
    }

    public void clearAllOrders() {
        this.currentDepositOrder = null;
        this.depositOrderQueue.clear();
    }

    protected void registerWithStorageController() {
        if (!BlockEntityUtil.isLoaded(this.entity.level(), this.storageControllerPosition)) {
            return;
        }

        IStorageController storageController = this.resolveCurrentStorageController();
        if (storageController != null) {
            this.registerWithStorageController(storageController);
        }
    }

    protected void unregisterFromStorageController() {
        if (this.managedMachine != null && BlockEntityUtil.isLoaded(this.entity.level(), this.storageControllerPosition)) {
            IStorageController storageController = this.resolveCurrentStorageController();
            if (storageController != null)
                storageController.removeDepositOrderSpirit(this.managedMachine.insertGlobalPos);
        }
    }

    protected void refreshStorageControllerRegistration() {
        IStorageController previousStorageController = this.storageController;
        IStorageController currentStorageController = this.resolveCurrentStorageController();
        if (currentStorageController != null && currentStorageController != previousStorageController) {
            this.registerWithStorageController(currentStorageController);
        }
    }

    protected IStorageController resolveCurrentStorageController() {
        if (this.storageControllerPosition == null)
            return null;

        if (!BlockEntityUtil.isLoaded(this.entity.level(), this.storageControllerPosition)) {
            this.storageController = null;
            return null;
        }

        BlockEntity blockEntity = BlockEntityUtil.get(this.entity.level(), this.storageControllerPosition);
        if (!(blockEntity instanceof IStorageController currentStorageController)) {
            this.storageController = null;
            return null;
        }

        this.storageController = currentStorageController;
        return currentStorageController;
    }

    protected void registerWithStorageController(IStorageController storageController) {
        if (this.managedMachine != null) {
            storageController.addDepositOrderSpirit(this.managedMachine.insertGlobalPos, this.entity.getUUID());
            storageController.linkMachine(this.managedMachine);
            BlockEntityUtil.updateTile(this.entity.level(), this.getStorageControllerPosition().getPos());
        }
    }

}
