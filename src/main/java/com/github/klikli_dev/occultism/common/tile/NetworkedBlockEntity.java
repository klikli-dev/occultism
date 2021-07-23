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

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateBlockEntityPacket;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.BlockEntity.BlockEntityType;

public abstract class NetworkedBlockEntity extends BlockEntity {

    public NetworkedBlockEntity(BlockEntityType<?> BlockEntityTypeIn) {
        super(BlockEntityTypeIn);
    }

    //region Overrides
    @Override
    public void read(BlockState state, CompoundTag compound) {
        this.readNetwork(compound);
        super.read(state, compound);
    }

    @Override
    public CompoundTag write(CompoundTag compound) {
        this.writeNetwork(compound);
        return super.write(compound);
    }

    @Override
    public SUpdateBlockEntityPacket getUpdatePacket() {
        return new SUpdateBlockEntityPacket(this.pos, 1, this.writeNetwork(new CompoundTag()));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.writeNetwork(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateBlockEntityPacket pkt) {
        this.readNetwork(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundTag tag) {
        super.read(state, tag);
        this.readNetwork(tag);
    }

    //endregion Overrides

    //region Methods

    /**
     * Reads networked nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to read from.
     */
    public void readNetwork(CompoundTag compound) {
    }

    /**
     * Writes network nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to write to.
     * @return the compound written to,
     */
    public CompoundTag writeNetwork(CompoundTag compound) {
        return compound;
    }

    public void markNetworkDirty(){
        if (this.level != null) {
            this.level.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 2);
        }
    }
}
