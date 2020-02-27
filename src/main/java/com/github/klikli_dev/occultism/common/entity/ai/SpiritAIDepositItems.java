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

import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SpiritAIDepositItems extends EntityAIPausable {
    //region Fields
    protected final EntitySpirit entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    //endregion Fields

    //region Initialization
    public SpiritAIDepositItems(EntitySpirit entity) {
        this.entity = entity;
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
        return this.targetBlock.offset(EnumFacing.fromAngle(angle).getOpposite());
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean shouldExecute() {
        //do not use if there is a target to attack
        if (this.entity.getAttackTarget() != null) {
            return false;
        }
        //nothing to deposit in hand
        if (this.entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return !this.isPaused() && this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.isPaused() && this.targetBlock != null && !this.entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.resetTarget();
    }

    @Override
    public void updateTask() {
        if (this.targetBlock != null) {
            if (this.entity.world.getTileEntity(this.targetBlock) != null) {
                TileEntity tileEntity = this.entity.world.getTileEntity(this.targetBlock);


                float accessDistance = 1.86f;

                //when approaching a chest, open it visually
                double distance = this.entity.getDistance(this.targetBlock.getX() + 0.5D, this.targetBlock.getY() + 1,
                        this.targetBlock.getZ() + 0.5D);

                //briefly before reaching the target, open chest, if it is one.
                if (distance < 2.5 && distance >= accessDistance && this.canSeeTarget() &&
                    tileEntity instanceof IInventory) {
                    this.toggleChest((IInventory) tileEntity, true);
                }

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
                if (distance < 1.86 && this.canSeeTarget()) {

                    IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                            this.entity.getDepositFacing());
                    if (handler == null) { //worst case scenario if tile entity changes since last target reset.
                        this.resetTarget();
                        return;
                    }
                    ItemStack duplicate = this.entity.getHeldItem(EnumHand.MAIN_HAND).copy();

                    //simulate insertion
                    ItemStack toInsert = ItemHandlerHelper.insertItem(handler, duplicate, true);
                    //if anything was inserted go for real
                    if (toInsert.getCount() != duplicate.getCount()) {
                        ItemHandlerHelper.insertItem(handler, duplicate, false);
                        //if we insterted everything
                        if (toInsert.isEmpty()) {
                            this.entity.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                            this.targetBlock = null;
                            this.resetTask();
                        }
                        else {
                            //pause ai to retry again in a little while.
                            this.pause(2000);
                        }
                    }

                    //after inserting, close chest
                    if (tileEntity instanceof IInventory) {
                        this.toggleChest((IInventory) tileEntity, false);
                    }
                }
            }
            else {
                this.resetTarget(); //if there is no tile entity, recheck
            }
        }
    }
    //endregion Overrides

    //region Methods
    public boolean canSeeTarget() {
        IBlockState targetBlockState = this.entity.world.getBlockState(this.targetBlock);
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

    /**
     * Opens or closes a chest
     *
     * @param tileEntity the chest tile entity
     * @param open       true to open the chest, false to close it.
     */
    public void toggleChest(IInventory tileEntity, boolean open) {
        if (tileEntity instanceof TileEntityChest) {
            TileEntityChest chest = (TileEntityChest) tileEntity;
            if (open) {
                chest.numPlayersUsing++;
                this.entity.world.addBlockEvent(this.targetBlock, chest.getBlockType(), 1, chest.numPlayersUsing);
            }
            else {
                if (chest.numPlayersUsing > 0) {
                    chest.numPlayersUsing = 0;
                    this.entity.world.addBlockEvent(this.targetBlock, chest.getBlockType(), 1, chest.numPlayersUsing);
                }
            }
        }
    }

    private void resetTarget() {
        this.targetBlock = this.entity.getDepositPosition();
        if (this.targetBlock != null) {
            TileEntity tileEntity = this.entity.world.getTileEntity(this.targetBlock);
            if (tileEntity == null || !tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    this.entity.getDepositFacing())) {
                //the deposit tile is not valid for depositing, so we disable this to allow exiting this task.
                this.entity.setDepositPosition(null);
            }
        }
    }
    //endregion Methods
}
