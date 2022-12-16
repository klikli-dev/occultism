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

package com.github.klikli_dev.occultism.common.entity.ai.goal;

import com.github.klikli_dev.occultism.common.entity.ai.BlockSorter;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.EnumSet;
import java.util.Optional;

public class ExtractItemsGoal extends PausableGoal {
    //region Fields
    protected final SpiritEntity entity;
    protected final BlockSorter targetSorter;
    protected BlockPos targetBlock = null;
    //endregion Fields

    //region Initialization
    public ExtractItemsGoal(SpiritEntity entity) {
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return the position to move to to take from the target block.
     */
    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.position(), Math3DUtil.center(this.targetBlock));
        return this.targetBlock.relative(Direction.fromYRot(angle).getOpposite());
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public boolean canUse() {
        //do not use if there is a target to attack
        if (this.entity.getTarget() != null) {
            return false;
        }

        //hand already full, cannot pick up anythings
        if (!this.entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        this.resetTarget();
        return !this.isPaused() && this.targetBlock != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.isPaused() && this.targetBlock != null && !this.entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    public void stop() {
        this.entity.getNavigation().stop();
        this.resetTarget();
    }

    @Override
    public void tick() {
        if (this.targetBlock != null) {
            if (this.entity.level.getBlockEntity(this.targetBlock) != null) {
                BlockEntity blockEntity = this.entity.level.getBlockEntity(this.targetBlock);

                float accessDistance = 1.86f;

                //when approaching a chest, open it visually
                double distance = this.entity.position().distanceTo(Math3DUtil.center(this.targetBlock));

                //briefly before reaching the target, open chest, if it is one.
                if (distance < 2.5 && distance >= accessDistance && this.canSeeTarget() &&
                        blockEntity instanceof Container container) {
                    this.toggleChest(container, true);
                }

                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigation().stop();
                } else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(moveTarget, 0), 1.0f);
                }

                //when close enough extract item
                if (distance < accessDistance && this.canSeeTarget()) {

                    LazyOptional<IItemHandler> handlerCapability = blockEntity.getCapability(
                            ForgeCapabilities.ITEM_HANDLER, this.entity.getExtractFacing());
                    if (!handlerCapability
                            .isPresent()) { //worst case scenario if block entity changes since last target reset.
                        this.resetTarget();
                        return;
                    }
                    IItemHandler blockEntityHandler = handlerCapability.orElseThrow(ItemHandlerMissingException::new);
                    IItemHandler entityHandler =
                            this.entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN)
                                    .orElseThrow(ItemHandlerMissingException::new);

                    int slot = StorageUtil.getFirstMatchingSlot(blockEntityHandler,
                            this.entity.getFilterItems().orElseThrow(ItemHandlerMissingException::new), this.entity.getTagFilter(), this.entity.isFilterBlacklist());
                    if (slot >= 0) {
                        //simulate extraction
                        ItemStack toExtract = blockEntityHandler.extractItem(slot, Integer.MAX_VALUE, true).copy();
                        if (!toExtract.isEmpty()) {
                            ItemStack remaining = ItemHandlerHelper.insertItem(entityHandler, toExtract, true);
                            if (remaining.getCount() < toExtract.getCount()) {
                                //if simulation went well, do for real
                                ItemStack extracted = blockEntityHandler.extractItem(slot, toExtract.getCount() - remaining.getCount(), false);
                                ItemHandlerHelper.insertItem(entityHandler, extracted, false);
                            }
                        }
                    }

                    //after extracting, close chest
                    if (blockEntity instanceof Container container) {
                        this.toggleChest(container, false);
                    }
                }
            } else {
                this.resetTarget(); //if there is no block entity, recheck
            }
        }
    }
    //endregion Overrides

    //region Methods
    public boolean canSeeTarget() {

        ClipContext context = new ClipContext(this.entity.position(),
                Math3DUtil.center(this.targetBlock), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                this.entity);
        BlockHitResult result = this.entity.level.clip(context);

        if (result.getType() != BlockHitResult.Type.MISS) {
            BlockPos sidePos = result.getBlockPos();
            BlockPos pos = new BlockPos(result.getLocation());
            return this.entity.level.isEmptyBlock(sidePos) || this.entity.level.isEmptyBlock(pos) ||
                    this.entity.level.getBlockEntity(pos) == this.entity.level.getBlockEntity(this.targetBlock);
        }

        return true;
    }

    /**
     * Opens or closes a chest
     *
     * @param BlockEntity the chest block entity
     * @param open        true to open the chest, false to close it.
     */
    public void toggleChest(Container blockEntity, boolean open) {
        if (blockEntity instanceof ChestBlockEntity chest) {
            if (open) {
                this.entity.level.blockEvent(this.targetBlock, chest.getBlockState().getBlock(), 1, 1);
            } else {
                this.entity.level.blockEvent(this.targetBlock, chest.getBlockState().getBlock(), 1, 0);
            }
        }
    }

    private void resetTarget() {
        Optional<BlockPos> targetPos = this.entity.getExtractPosition();
        targetPos.ifPresent((pos) -> {
            this.targetBlock = pos;
            BlockEntity blockEntity = this.entity.level.getBlockEntity(this.targetBlock);
            if (blockEntity == null ||
                    !blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, this.entity.getExtractFacing())
                            .isPresent()) {
                //the extract block is not valid for extracting, so we disable this to allow exiting this task.
                this.entity.setExtractPosition(null);
            }
        });
    }
    //endregion Methods
}
