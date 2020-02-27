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

import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.api.common.tile.IStorageControllerProxy;
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.jobs.DepositOrder;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobManageMachine;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class SpiritAIManageMachine extends Goal {
    //region Fields
    protected final EntitySpirit entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    protected TileEntity cachedStorageAccessor;
    protected DepositOrder cachedStorageAccessorOrder;
    protected SpiritJobManageMachine job;
    //endregion Fields

    //region Initialization
    public SpiritAIManageMachine(EntitySpirit entity, SpiritJobManageMachine job) {
        this.entity = entity;
        this.job = job;
        this.targetSorter = new BlockSorter(entity);
        this.setMutexBits(1);
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return the position to move to to deposit the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.getPositionVector(), Math3DUtil.getBlockCenter(this.targetBlock));
        return this.targetBlock.offset(Direction.fromAngle(angle).getOpposite());
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean shouldExecute() {
        //do not use if there is a target to attack
        if (this.entity.getAttackTarget() != null) {
            return false;
        }
        //if we have something in hand, we can
        if (!this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.targetBlock != null && this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.resetTarget();
    }

    @Override
    public void updateTask() {
        if (this.targetBlock != null) {
            if (this.entity.world.getTileEntity(this.targetBlock) != null && this.job.getStorageController() != null) {

                TileEntity tileEntity = this.entity.world.getTileEntity(this.targetBlock);

                //when approaching a chest, open it visually
                double distance = this.entity.getDistance(this.targetBlock.getX() + 0.5D, this.targetBlock.getY() + 1,
                        this.targetBlock.getZ() + 0.5D);
                float accessDistance = 1.86f;
                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigator().clearPath();
                }
                else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigator()
                            .tryMoveToXYZ(moveTarget.getX() + 0.5D, moveTarget.getY(), moveTarget.getZ() + 0.5D, 1D);
                }

                //when close enough insert item
                if (distance < accessDistance && this.canSeeTarget()) {
                    DepositOrder currentOrder = this.job.getCurrentDepositOrder();
                    MachineReference machineReference = this.job.getManagedMachine();
                    if (tileEntity instanceof IStorageControllerProxy && currentOrder != null) {
                        //if we reached the storage controller proxy, we take out items as per our order
                        ItemStack itemToExtract = this.job.getStorageController()
                                                          .getItemStack(currentOrder.comparator, currentOrder.amount,
                                                                  true);
                        IItemHandler handler = this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                Direction.UP);
                        if (!itemToExtract.isEmpty() &&
                            ItemHandlerHelper.insertItem(handler, itemToExtract, true).isEmpty()) {
                            //we can insert all, so we can perform for real now
                            ItemStack extracted = this.job.getStorageController()
                                                          .getItemStack(currentOrder.comparator, currentOrder.amount,
                                                                  false);
                            ItemHandlerHelper.insertItem(handler, extracted, false);

                            //job fulfilled, deposit ai will take over
                            this.entity.setDepositPosition(machineReference.globalPos.getPos());
                            this.entity.setDepositFacing(machineReference.insertFacing);
                            this.job.setCurrentDepositOrder(null);
                            this.targetBlock = null;
                        }
                    }
                    else if (this.targetBlock.equals(machineReference.globalPos.getPos())) {
                        //if we reached the machine, we take out the result

                        IItemHandler machineHandler = tileEntity.getCapability(
                                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.extractFacing);
                        IItemHandler entityHandler = this.entity.getCapability(
                                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP);

                        boolean movedAnyItems = false;
                        for (int i = 0; i < machineHandler.getSlots(); i++) {
                            //first simulate if we can put anything into the entity
                            ItemStack itemToExtract = machineHandler
                                                              .extractItem(i, machineHandler.getSlotLimit(i), true);
                            ItemStack remaining = ItemHandlerHelper.insertItem(entityHandler, itemToExtract, true);
                            //if anything was taken and inserted, perform for real
                            if (!itemToExtract.isEmpty() && remaining.getCount() != itemToExtract.getCount()) {
                                //extract only as much as we can insert
                                ItemStack extracted = machineHandler.extractItem(i,
                                        itemToExtract.getCount() - remaining.getCount(), false);
                                ItemHandlerHelper.insertItem(entityHandler, extracted, false);
                                movedAnyItems = true;
                                //If something was moved, but not everything the entity is full and we can break
                                if (remaining.getCount() > 0) {
                                    break;
                                }
                            }
                        }

                        //now we need to set up depositing in the storage controller proxy
                        if (movedAnyItems) {
                            TileEntity storageControllerProxy = this.findClosestStorageProxy();

                            this.entity.setDepositPosition(storageControllerProxy.getPos());
                            this.entity.setDepositFacing(Direction.UP);
                            this.targetBlock = null;

                        }
                    }
                    this.resetTask();
                }
            }
            else {
                //if we have a target block but no tile there we need to reset
                this.resetTask();
            }
        }
    }
    //endregion Overrides

    //region Methods
    public boolean canSeeTarget() {
        BlockState targetBlockState = this.entity.world.getBlockState(this.targetBlock);
        RayTraceResult rayTrace = targetBlockState.collisionRayTrace(this.entity.world, this.targetBlock,
                this.entity.getPositionVector(), Math3DUtil.getBlockCenter(this.targetBlock));

        if (rayTrace != null && rayTrace.typeOfHit != RayTraceResult.Type.MISS) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.hitVec);
            return this.entity.world.isAirBlock(sidePos) || this.entity.world.isAirBlock(pos) ||
                   this.entity.world.getTileEntity(pos) == this.entity.world.getTileEntity(this.targetBlock);
        }

        return true;
    }

    protected TileEntity findClosestStorageProxy() {
        if (this.cachedStorageAccessor != null && this.cachedStorageAccessorOrder == this.job.getCurrentDepositOrder())
            return this.cachedStorageAccessor;

        World world = this.entity.world;
        List<BlockPos> allBlocks = new ArrayList<>();
        BlockPos machinePosition = this.job.getManagedMachine().globalPos.getPos();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        Iterable<BlockPos> searchBlocks = BlockPos.getAllInBox(
                machinePosition.add(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                machinePosition.add(workAreaSize, workAreaSize / 2, workAreaSize));

        for (BlockPos pos : searchBlocks) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IStorageControllerProxy) {
                IStorageControllerProxy proxy = (IStorageControllerProxy) tileEntity;
                if (proxy.getLinkedStorageControllerPosition() != null &&
                    proxy.getLinkedStorageControllerPosition().equals(this.job.getStorageControllerPosition()))
                    allBlocks.add(pos);
            }
        }

        //set closest log as target
        if (!allBlocks.isEmpty()) {
            allBlocks.sort(this.targetSorter);
            this.cachedStorageAccessor = world.getTileEntity(allBlocks.get(0));
            this.cachedStorageAccessorOrder = this.job.getCurrentDepositOrder();
            return this.cachedStorageAccessor;
        }
        return null;
    }

    private boolean startTargetingStorageController(DepositOrder depositOrder, MachineReference machineReference,
                                                    TileEntity machine, IStorageController storageController) {
        IItemHandler machineItemHandler = machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                machineReference.insertFacing);

        //simulate taking and inserting items to ensure we have space
        ItemStack orderStack = storageController.getItemStack(depositOrder.comparator, depositOrder.amount, true);
        if (!orderStack.isEmpty() && ItemHandlerHelper.insertItem(machineItemHandler, orderStack, true).isEmpty()) {
            //if we can insert everything we can get for this order, perform it.
            TileEntity storageControllerProxy = this.findClosestStorageProxy();
            if (storageControllerProxy != null) {
                this.targetBlock = storageControllerProxy.getPos();
                return true;
            }
            //no proxy in range, so we just skip this task which will lead to idle.
            this.targetBlock = null;
            return true;
        }
        else if (!orderStack.isEmpty()) {
            //returning false leads to a call to startTargetingMachine
            return false;
        }
        return false;
    }

    private boolean startTargetingMachine(DepositOrder depositOrder, MachineReference machineReference,
                                          TileEntity machine, IStorageController storageController) {
        IItemHandler machineItemHandler = machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                machineReference.extractFacing);
        for (int i = 0; i < machineItemHandler.getSlots(); i++) {
            if (!machineItemHandler.getStackInSlot(i).isEmpty()) {
                this.targetBlock = machine.getPos();
                return true;
            }
        }
        this.targetBlock = null;
        return false;
    }

    private void resetTarget() {
        DepositOrder currentOrder = this.job.getCurrentDepositOrder();
        MachineReference machineReference = this.job.getManagedMachine();
        TileEntity machine = this.job.getManagedMachineTileEntity();
        IStorageController storageController = this.job.getStorageController();

        if (machine != null && storageController != null) {

            //machine was replaced or no longer supports inventories, so we unlink it and abort
            if (!machine.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.insertFacing) ||
                !machine.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.extractFacing)) {
                this.job.setManagedMachine(null);
                this.targetBlock = null;
                return;
            }

            //if we have an order, we move to the controller to get resources for the machine
            if (currentOrder == null ||
                !this.startTargetingStorageController(currentOrder, machineReference, machine, storageController)) {
                //we either have no order, or no space in the machine, so we extract from  the machine first
                this.startTargetingMachine(currentOrder, machineReference, machine, storageController);
            }
        }
    }
    //endregion Methods
}
