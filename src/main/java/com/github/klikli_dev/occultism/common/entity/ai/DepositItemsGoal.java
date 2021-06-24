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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.EnumSet;
import java.util.Optional;

public class DepositItemsGoal extends PausableGoal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    //endregion Fields

    //region Initialization
    public DepositItemsGoal(SpiritEntity entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
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
        //nothing to deposit in hand
        if (this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return !this.isPaused() && this.targetBlock != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.isPaused() && this.targetBlock != null && !this.entity.getHeldItem(Hand.MAIN_HAND).isEmpty();
    }

    public void resetTask() {
        this.entity.getNavigator().clearPath();
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.targetBlock != null) {
            if (this.entity.world.getTileEntity(this.targetBlock) != null) {
                TileEntity tileEntity = this.entity.world.getTileEntity(this.targetBlock);


                float accessDistance = 1.86f;

                //when approaching a chest, open it visually
                double distance = this.entity.getPositionVec().distanceTo(Math3DUtil.center(this.targetBlock));

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
                    this.entity.getNavigator().setPath(this.entity.getNavigator().getPathToPos(moveTarget, 0), 1.0f);
                }

                //when close enough insert item
                if (distance < 1.86 && this.canSeeTarget()) {

                    LazyOptional<IItemHandler> handlerCapability = tileEntity.getCapability(
                            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.entity.getDepositFacing());
                    if (!handlerCapability
                                 .isPresent()) { //worst case scenario if tile entity changes since last target reset.
                        this.resetTarget();
                        return;
                    }
                    IItemHandler handler = handlerCapability.orElseThrow(ItemHandlerMissingException::new);
                    ItemStack duplicate = this.entity.getHeldItem(Hand.MAIN_HAND).copy();

                    //simulate insertion
                    ItemStack toInsert = ItemHandlerHelper.insertItem(handler, duplicate, true);
                    //if anything was inserted go for real
                    if (toInsert.getCount() != duplicate.getCount()) {
                        ItemStack leftover = ItemHandlerHelper.insertItem(handler, duplicate, false);
                        //if we insterted everything
                        this.entity.setHeldItem(Hand.MAIN_HAND, leftover);
                        if (toInsert.isEmpty()) {
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

        RayTraceContext context = new RayTraceContext(this.entity.getPositionVec(),
                Math3DUtil.center(this.targetBlock), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE,
                this.entity);
        BlockRayTraceResult result = this.entity.world.rayTraceBlocks(context);

        if (result.getType() != BlockRayTraceResult.Type.MISS) {
            BlockPos sidePos = result.getPos();
            BlockPos pos = new BlockPos(result.getHitVec());
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
        if (tileEntity instanceof ChestTileEntity) {
            ChestTileEntity chest = (ChestTileEntity) tileEntity;
            if (open) {
                this.entity.world.addBlockEvent(this.targetBlock, chest.getBlockState().getBlock(), 1, 1);
            }
            else {
                this.entity.world.addBlockEvent(this.targetBlock, chest.getBlockState().getBlock(), 1, 0);
            }
        }
    }

    private void resetTarget() {
        Optional<BlockPos> targetPos = this.entity.getDepositPosition();
        targetPos.ifPresent((pos) -> {
            this.targetBlock = pos;
            TileEntity tileEntity = this.entity.world.getTileEntity(this.targetBlock);
            if (tileEntity == null ||
                !tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.entity.getDepositFacing())
                         .isPresent()) {
                //the deposit tile is not valid for depositing, so we disable this to allow exiting this task.
                this.entity.setDepositPosition(null);
            }
        });
    }
    //endregion Methods
}
