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

package com.github.klikli_dev.occultism.common.tile;

import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntitySacrificialBowl extends TileEntityBase {

    //region Fields
    public long lastChangeTime;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        //region Overrides
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (!TileEntitySacrificialBowl.this.world.isRemote) {
                TileEntitySacrificialBowl.this.lastChangeTime = TileEntitySacrificialBowl.this.world
                                                                        .getTotalWorldTime();
                TileEntityUtil
                        .updateTile(TileEntitySacrificialBowl.this.world, TileEntitySacrificialBowl.this.getPos());
            }
        }
        //endregion Overrides
    };
    protected boolean initialized = false;
    //endregion Fields

    //region Overrides

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    public void readFromNetworkNBT(NBTTagCompound compound) {
        this.itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        this.lastChangeTime = compound.getLong("lastChangeTime");
        super.readFromNetworkNBT(compound);
    }

    public NBTTagCompound writeToNetworkNBT(NBTTagCompound compound) {
        compound.setTag("inventory", this.itemStackHandler.serializeNBT());
        compound.setLong("lastChangeTime", this.lastChangeTime);
        return super.writeToNetworkNBT(compound);
    }
    //endregion Overrides

    //region Methods
    //endregion Methods
}
