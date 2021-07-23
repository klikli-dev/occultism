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
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.ManageMachineJob;
import com.github.klikli_dev.occultism.common.misc.DepositOrder;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.InteractionHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

public class ManageMachineGoal extends Goal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    protected BlockEntity cachedStorageAccessor;
    protected DepositOrder cachedStorageAccessorOrder;
    protected ManageMachineJob job;
    //endregion Fields

    //region Initialization
    public ManageMachineGoal(SpiritEntity entity, ManageMachineJob job) {
        this.entity = entity;
        this.job = job;
        this.targetSorter = new BlockSorter(entity);
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return the position to move to to deposit the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.getPositionVec(), Math3DUtil.center(this.targetBlock));
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
        if (!this.entity.getHeldItem(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.targetBlock != null && this.entity.getHeldItem(InteractionHand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.targetBlock != null) {
            if (this.entity.level.getBlockEntity(this.targetBlock) != null && this.job.getStorageController() != null) {

                BlockEntity blockEntity = this.entity.level.getBlockEntity(this.targetBlock);

                //when approaching a chest, open it visually
                double distance = this.entity.getPositionVec().distanceTo(Math3DUtil.center(this.targetBlock));
                float accessDistance = 1.86f;
                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigator().clearPath();
                }
                else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigator().setPath(this.entity.getNavigator().getPathToPos(moveTarget, 0), 1.0f);
                }

                //when close enough, interact
                if (distance < accessDistance && this.canSeeTarget()) {
                    DepositOrder currentOrder = this.job.getCurrentDepositOrder();
                    MachineReference machineReference = this.job.getManagedMachine();
                    if (BlockEntity instanceof IStorageControllerProxy && currentOrder != null) {
                        //if we reached the storage controller proxy, we take out items as per our order
                        ItemStack itemToExtract = this.job.getStorageController()
                                                          .getItemStack(currentOrder.comparator, currentOrder.amount,
                                                                  true);
                        IItemHandler handler = this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                Direction.UP).orElseThrow(ItemHandlerMissingException::new);
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


                        blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                machineReference.extractFacing).ifPresent(machineHandler -> {

                            IItemHandler entityHandler = this.entity.getCapability(
                                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP)
                                                                 .orElseThrow(ItemHandlerMissingException::new);

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
                                BlockEntity storageControllerProxy = this.findClosestStorageProxy();

                                this.entity.setDepositPosition(storageControllerProxy.getPos());
                                this.entity.setDepositFacing(Direction.UP);
                                this.targetBlock = null;

                            }
                        });
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
        BlockState targetBlockState = this.entity.level.getBlockState(this.targetBlock);
        RayTraceContext context = new RayTraceContext(this.entity.getEyePosition(0),
                Math3DUtil.center(this.targetBlock), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE,
                this.entity);
        BlockRayTraceResult rayTrace = this.entity.level.rayTraceBlocks(context);

        if (rayTrace.getType() != RayTraceResult.Type.MISS) {
            BlockPos sidePos = rayTrace.getPos();
            BlockPos pos = new BlockPos(rayTrace.getHitVec());
            return this.entity.level.isAirBlock(sidePos) || this.entity.level.isAirBlock(pos) ||
                   this.entity.level.getBlockEntity(pos) == this.entity.level.getBlockEntity(this.targetBlock);
        }

        return true;
    }

    protected BlockEntity findClosestStorageProxy() {
        if (this.cachedStorageAccessor != null && this.cachedStorageAccessorOrder == this.job.getCurrentDepositOrder())
            return this.cachedStorageAccessor;

        Level level = this.entity.level;
        List<BlockPos> allBlocks = new ArrayList<>();
        BlockPos machinePosition = this.job.getManagedMachine().globalPos.getPos();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        Stream<BlockPos> searchBlocks = BlockPos.getAllInBox(
                machinePosition.add(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                machinePosition.add(workAreaSize, workAreaSize / 2, workAreaSize));
        searchBlocks.forEachOrdered(pos -> {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (BlockEntity instanceof IStorageControllerProxy) {
                IStorageControllerProxy proxy = (IStorageControllerProxy) BlockEntity;
                if (proxy.getLinkedStorageControllerPosition() != null &&
                    proxy.getLinkedStorageControllerPosition().equals(this.job.getStorageControllerPosition()))
                    allBlocks.add(pos.toImmutable());
            }
        });

        //set closest log as target
        if (!allBlocks.isEmpty()) {
            allBlocks.sort(this.targetSorter);
            this.cachedStorageAccessor = level.getBlockEntity(allBlocks.get(0));
            this.cachedStorageAccessorOrder = this.job.getCurrentDepositOrder();
            return this.cachedStorageAccessor;
        }
        return null;
    }

    private boolean startTargetingStorageController(DepositOrder depositOrder, MachineReference machineReference,
                                                    BlockEntity machine, IStorageController storageController) {
        return machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.insertFacing)
                       .map(machineItemHandler -> {
                           //simulate taking and inserting items to ensure we have space
                           ItemStack orderStack = storageController
                                                          .getItemStack(depositOrder.comparator, depositOrder.amount,
                                                                  true);
                           if (!orderStack.isEmpty() &&
                               ItemHandlerHelper.insertItem(machineItemHandler, orderStack, true).isEmpty()) {
                               //if we can insert everything we can get for this order, perform it.
                               BlockEntity storageControllerProxy = this.findClosestStorageProxy();
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
                       }).orElse(false);
    }

    private boolean startTargetingMachine(DepositOrder depositOrder, MachineReference machineReference,
                                          BlockEntity machine, IStorageController storageController) {
        return machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.extractFacing)
                       .map(machineItemHandler -> {
                           for (int i = 0; i < machineItemHandler.getSlots(); i++) {
                               if (!machineItemHandler.getStackInSlot(i).isEmpty()) {
                                   this.targetBlock = machine.getPos();
                                   return true;
                               }
                           }
                           this.targetBlock = null;
                           return false;
                       }).orElse(false);
    }

    private void resetTarget() {
        DepositOrder currentOrder = this.job.getCurrentDepositOrder();
        MachineReference machineReference = this.job.getManagedMachine();
        BlockEntity machine = this.job.getManagedMachineBlockEntity();
        IStorageController storageController = this.job.getStorageController();

        if (machine != null && storageController != null) {

            //machine was replaced or no longer supports inventories, so we unlink it and abort
            if (!machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.insertFacing)
                         .isPresent() ||
                !machine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, machineReference.extractFacing)
                         .isPresent()) {
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
