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

import com.github.klikli_dev.occultism.common.entity.ai.target.BlockPosMoveTarget;
import com.github.klikli_dev.occultism.common.entity.ai.target.EntityMoveTarget;
import com.github.klikli_dev.occultism.common.entity.ai.target.IMoveTarget;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class DepositItemsGoal extends PausableGoal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected IMoveTarget moveTarget = null;
    //endregion Fields

    //region Initialization
    public DepositItemsGoal(SpiritEntity entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return the position to move to to deposit the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.position(), Math3DUtil.center(this.moveTarget.getBlockPos()));
        return this.moveTarget.getBlockPos().relative(Direction.fromYRot(angle).getOpposite());
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean canUse() {
        //do not use if there is a target to attack
        if (this.entity.getTarget() != null) {
            return false;
        }
        //nothing to deposit in hand
        if (this.entity.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return !this.isPaused() && this.moveTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.isPaused() && this.moveTarget != null && !this.entity.getItemInHand(Hand.MAIN_HAND).isEmpty();
    }

    public void stop() {
        this.entity.getNavigation().stop();
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.moveTarget != null) {
            if (this.moveTarget.isValid()) {
                float accessDistance = 1.86f;

                //when approaching a chest, open it visually
                double distance = this.entity.position().distanceTo(Math3DUtil.center(this.moveTarget.getBlockPos()));

                //briefly before reaching the target, open chest, if it is one.
                if (distance < 2.5 && distance >= accessDistance && this.canSeeTarget() &&
                        this.moveTarget.isChest()) {
                    this.toggleChest(this.moveTarget, true);
                }

                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigation().stop();
                } else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(moveTarget, 0), 1.0f);
                }

                //when close enough insert item
                if (distance < 1.86 && this.canSeeTarget()) {

                    LazyOptional<IItemHandler> handlerCapability = this.moveTarget.getCapability(
                            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.entity.getDepositFacing());
                    if (!handlerCapability
                            .isPresent()) { //worst case scenario if tile entity or entity changes since last target reset.
                        this.resetTarget();
                        return;
                    }
                    IItemHandler handler = handlerCapability.orElseThrow(ItemHandlerMissingException::new);
                    ItemStack duplicate = this.entity.getItemInHand(Hand.MAIN_HAND).copy();

                    //simulate insertion
                    ItemStack toInsert = ItemHandlerHelper.insertItem(handler, duplicate, true);
                    //if anything was inserted go for real
                    if (toInsert.getCount() != duplicate.getCount()) {
                        ItemStack leftover = ItemHandlerHelper.insertItem(handler, duplicate, false);
                        //if we inserted everything
                        this.entity.setItemInHand(Hand.MAIN_HAND, leftover);
                        if (toInsert.isEmpty()) {
                            this.moveTarget = null;
                            this.stop();
                        } else {
                            //pause ai to retry again in a little while.
                            this.pause(2000);
                        }
                    }

                    //after inserting, close chest
                    if (this.moveTarget != null && this.moveTarget.isChest()) {
                        this.toggleChest(this.moveTarget, false);
                    }
                }
            } else {
                this.resetTarget(); //if there is no tile entity, recheck
            }
        }
    }
    //endregion Overrides

    //region Methods
    public boolean canSeeTarget() {

        RayTraceContext context = new RayTraceContext(this.entity.position(),
                Math3DUtil.center(this.moveTarget.getBlockPos()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE,
                this.entity);
        BlockRayTraceResult result = this.entity.level.clip(context);

        if (result.getType() != BlockRayTraceResult.Type.MISS) {
            BlockPos sidePos = result.getBlockPos();
            BlockPos pos = new BlockPos(result.getLocation());
            return this.entity.level.isEmptyBlock(sidePos) || this.entity.level.isEmptyBlock(pos) ||
                    this.entity.level.getBlockEntity(pos) == this.entity.level.getBlockEntity(this.moveTarget.getBlockPos());
        }

        return true;
    }

    /**
     * Opens or closes a chest
     *
     * @param target the target
     * @param open   true to open the chest, false to close it.
     */
    public void toggleChest(IMoveTarget target, boolean open) {
        if (target instanceof BlockPosMoveTarget) {
            TileEntity tile = this.entity.level.getBlockEntity(target.getBlockPos());
            if (tile instanceof ChestTileEntity) {
                ChestTileEntity chest = (ChestTileEntity) tile;
                if (open) {
                    this.entity.level.blockEvent(this.moveTarget.getBlockPos(), chest.getBlockState().getBlock(), 1, 1);
                } else {
                    this.entity.level.blockEvent(this.moveTarget.getBlockPos(), chest.getBlockState().getBlock(), 1, 0);
                }
            }
        }
    }

    private void resetTarget() {
        //check a target block
        Optional<BlockPos> targetPos = this.entity.getDepositPosition();
        targetPos.ifPresent((pos) -> {
            this.moveTarget = new BlockPosMoveTarget(this.entity.level, pos);
            if (!this.moveTarget.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.entity.getDepositFacing())
                    .isPresent()) {
                //the deposit tile is not valid for depositing, so we disable this to allow exiting this task.
                this.entity.setDepositPosition(null);
            }
        });
        //also check a target entity -> its mutually exclusive with block, ensured by spirit entity
        Optional<UUID> targetUUID = this.entity.getDepositEntityUUID();
        targetUUID.ifPresent((uuid) -> {
            Entity targetEntity = ((ServerWorld) this.entity.level).getEntity(uuid);
            if (targetEntity != null) {
                this.moveTarget = new EntityMoveTarget(targetEntity);
            } else {
                this.entity.setDepositEntityUUID(null);
            }
        });
    }
    //endregion Methods
}
