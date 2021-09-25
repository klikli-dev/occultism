/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.github.klikli_dev.occultism.common.blockentity;

import com.github.klikli_dev.occultism.registry.OccultismTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SacrificialBowlBlockEntity extends NetworkedBlockEntity {

    //region Fields
    public long lastChangeTime;
    public LazyOptional<ItemStackHandler> itemStackHandler = LazyOptional.of(
            () -> new ItemStackHandler(1) {
                //region Overrides
                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }

                @Override
                protected void onContentsChanged(
                        int slot) {
                    if (!SacrificialBowlBlockEntity.this.level.isClientSide) {
                        SacrificialBowlBlockEntity.this.lastChangeTime = SacrificialBowlBlockEntity.this.level
                                .getGameTime();
                        SacrificialBowlBlockEntity.this.markNetworkDirty();
                    }
                }
                //endregion Overrides
            });
    protected boolean initialized = false;
    //endregion Fields

    //region Initialization
    public SacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismTiles.SACRIFICIAL_BOWL.get(), worldPos, state);
    }

    public SacrificialBowlBlockEntity(BlockEntityType<?> BlockEntityTypeIn, BlockPos worldPos, BlockState state) {
        super(BlockEntityTypeIn, worldPos, state);
    }
    //endregion Initialization

    //region Overrides
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemStackHandler.cast();
        }
        return super.getCapability(cap, direction);
    }

    @Override
    public void loadNetwork(CompoundTag compound) {
        this.itemStackHandler.ifPresent((handler) -> handler.deserializeNBT(compound.getCompound("inventory")));
        this.lastChangeTime = compound.getLong("lastChangeTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound) {
        this.itemStackHandler.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
        compound.putLong("lastChangeTime", this.lastChangeTime);
        return compound;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemStackHandler.invalidate();
    }

    //endregion Overrides
}
