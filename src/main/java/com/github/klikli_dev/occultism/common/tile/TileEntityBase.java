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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Provides default implementations for some tile entity methods.
 */
public class TileEntityBase extends TileEntity {

    //region Overrides
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.readFromNetworkNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeToNetworkNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 1, this.writeToNetworkNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNetworkNBT(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNetworkNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        this.readFromNetworkNBT(tag);
    }

    //endregion Overrides

    //region Methods

    /**
     * Reads networked nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to read from.
     */
    public void readFromNetworkNBT(NBTTagCompound compound) {
    }

    /**
     * Writes network nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to write to.
     * @return the compound written to,
     */
    public NBTTagCompound writeToNetworkNBT(NBTTagCompound compound) {
        return compound;
    }

    /**
     * Force a network update.
     */
    public void syncToClient() {
        this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
        this.world
                .notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 2);
        this.world.scheduleBlockUpdate(this.pos, this.blockType, 0, 0);
        this.markDirty();
    }
    //endregion Methods
}
