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

import com.github.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.github.klikli_dev.occultism.api.common.blockentity.IStorageControllerProxy;
import com.github.klikli_dev.occultism.api.common.data.MachineReference;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.ManageMachineJob;
import com.github.klikli_dev.occultism.common.misc.DepositOrder;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

public class ManageMachineGoal extends Goal {
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    protected BlockEntity cachedStorageAccessor;
    protected DepositOrder cachedStorageAccessorOrder;
    protected ManageMachineJob job;

    public ManageMachineGoal(SpiritEntity entity, ManageMachineJob job) {
        this.entity = entity;
        this.job = job;
        this.targetSorter = new BlockSorter(entity);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }


    /**
     * @return the position to move to to deposit the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.position(), Math3DUtil.center(this.targetBlock));
        return this.targetBlock.relative(Direction.fromYRot(angle).getOpposite());
    }

    @Override
    public boolean canUse() {
        //do not use if there is a target to attack
        if (this.entity.getTarget() != null) {
            return false;
        }
        //if we have something in hand, we can
        if (!this.entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return this.targetBlock != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetBlock != null && this.entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    public void stop() {
        this.entity.getNavigation().stop();
        this.resetTarget();
    }

    @Override
    public void tick() {
        //extract Resource from Storage Controller: ManageMachineGoal
        //insert Resource into Machine: DepositItemsGoal
        //Extract Result from Machine: ManageMachineGoal
        //Insert Result into Storage Controller: DepositItemsGoal

        if (this.targetBlock != null) {
            if (this.entity.level.getBlockEntity(this.targetBlock) != null && this.job.getStorageController() != null) {

                BlockEntity blockEntity = this.entity.level.getBlockEntity(this.targetBlock);

                //when approaching a chest, open it visually
                double distance = this.entity.position().distanceTo(Math3DUtil.center(this.targetBlock));
                float accessDistance = 2.2f;//1.86f;
                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigation().stop();
                } else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(moveTarget, 0), 1.0f);
                }

                //when close enough, interact
                if (distance < accessDistance && this.canSeeTarget()) {
                    DepositOrder currentOrder = this.job.getCurrentDepositOrder();
                    MachineReference machineReference = this.job.getManagedMachine();
                    if (blockEntity instanceof IStorageControllerProxy && currentOrder != null) {
                        //if we reached the storage controller proxy, we take out items as per our order
                        ItemStack itemToExtract = this.job.getStorageController()
                                .getItemStack(currentOrder.comparator, currentOrder.amount,
                                        true);
                        IItemHandler handler = this.entity.getCapability(ForgeCapabilities.ITEM_HANDLER,
                                Direction.UP).orElseThrow(ItemHandlerMissingException::new);
                        if (!itemToExtract.isEmpty() &&
                                ItemHandlerHelper.insertItem(handler, itemToExtract, true).isEmpty()) {
                            //we can insert all, so we can perform for real now
                            ItemStack extracted = this.job.getStorageController()
                                    .getItemStack(currentOrder.comparator, currentOrder.amount,
                                            false);
                            ItemHandlerHelper.insertItem(handler, extracted, false);

                            //job fulfilled, deposit ai will take over
                            this.entity.setDepositPosition(machineReference.insertGlobalPos.getPos());
                            this.entity.setDepositFacing(machineReference.insertFacing);
                            this.job.setCurrentDepositOrder(null);
                            this.targetBlock = null;
                        }
                    } else if (this.targetBlock.equals(machineReference.extractGlobalPos.getPos())) {
                        //if we reached the machine (=extract block entity), we take out the result


                        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER,
                                machineReference.extractFacing).ifPresent(machineHandler -> {

                            IItemHandler entityHandler = this.entity.getCapability(
                                            ForgeCapabilities.ITEM_HANDLER, Direction.UP)
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
                                if (storageControllerProxy != null) {
                                    this.entity.setDepositPosition(storageControllerProxy.getBlockPos());
                                }
                                this.entity.setDepositFacing(Direction.UP);
                                this.targetBlock = null;

                            }
                        });
                    }
                    this.stop();
                }
            } else {
                //if we have a target block but no block there we need to reset
                this.stop();
            }
        }
    }

    public boolean canSeeTarget() {
        BlockState targetBlockState = this.entity.level.getBlockState(this.targetBlock);
        ClipContext context = new ClipContext(this.entity.getEyePosition(0),
                Math3DUtil.center(this.targetBlock), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                this.entity);
        BlockHitResult rayTrace = this.entity.level.clip(context);

        if (rayTrace.getType() != BlockHitResult.Type.MISS) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.getLocation());
            return this.entity.level.isEmptyBlock(sidePos) || this.entity.level.isEmptyBlock(pos) ||
                    this.entity.level.getBlockEntity(pos) == this.entity.level.getBlockEntity(this.targetBlock);
        }

        return true;
    }

    protected BlockEntity findClosestStorageProxy() {
        if (this.cachedStorageAccessor != null && this.cachedStorageAccessorOrder == this.job.getCurrentDepositOrder())
            return this.cachedStorageAccessor;

        Level level = this.entity.level;
        List<BlockPos> allBlocks = new ArrayList<>();
        BlockPos machinePosition = this.job.getManagedMachine().insertGlobalPos.getPos();

        //get work area, but only half height, we don't need full.
        int workAreaSize = this.entity.getWorkAreaSize().getValue();
        Stream<BlockPos> searchBlocks = BlockPos.betweenClosedStream(
                machinePosition.offset(-workAreaSize, -workAreaSize / 2, -workAreaSize),
                machinePosition.offset(workAreaSize, workAreaSize / 2, workAreaSize));
        searchBlocks.forEachOrdered(pos -> {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof IStorageControllerProxy proxy) {
                if (proxy.getLinkedStorageControllerPosition() != null &&
                        proxy.getLinkedStorageControllerPosition().equals(this.job.getStorageControllerPosition()))
                    allBlocks.add(pos.immutable());
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
        return machine.getCapability(ForgeCapabilities.ITEM_HANDLER, machineReference.insertFacing)
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
                            this.targetBlock = storageControllerProxy.getBlockPos();
                            return true;
                        }
                        //no proxy in range, so we just skip this task which will lead to idle.
                        this.targetBlock = null;
                        return true;
                    } else if (!orderStack.isEmpty()) {
                        //returning false leads to a call to startTargetingMachine
                        return false;
                    }
                    return false;
                }).orElse(false);
    }

    private boolean startTargetingExtractBlockEntity(DepositOrder depositOrder, MachineReference machineReference,
                                                     BlockEntity extractBlockEntity, IStorageController storageController) {
        return extractBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, machineReference.extractFacing)
                .map(machineItemHandler -> {
                    for (int i = 0; i < machineItemHandler.getSlots(); i++) {
                        if (!machineItemHandler.getStackInSlot(i).isEmpty()) {
                            this.targetBlock = extractBlockEntity.getBlockPos();
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
        BlockEntity extractBlockEntity = this.job.getExtractBlockEntity();
        IStorageController storageController = this.job.getStorageController();

        if (machine != null && extractBlockEntity != null && storageController != null) {

            //machine was replaced or no longer supports inventories, so we unlink it and abort
            if (!machine.getCapability(ForgeCapabilities.ITEM_HANDLER, machineReference.insertFacing).isPresent() ||
                    !extractBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, machineReference.extractFacing).isPresent()) {
                this.job.setManagedMachine(null);
                this.targetBlock = null;
                return;
            }

            //if we have an order, we move to the controller to get resources for the machine
            if (currentOrder == null ||
                    !this.startTargetingStorageController(currentOrder, machineReference, machine, storageController)) {
                //we either have no order, or no space in the machine, so we extract from  the machine first
                //to be precise, we extract from the extract block entity, which might be different from the machine
                this.startTargetingExtractBlockEntity(currentOrder, machineReference, extractBlockEntity, storageController);
            }
        }
    }
}
