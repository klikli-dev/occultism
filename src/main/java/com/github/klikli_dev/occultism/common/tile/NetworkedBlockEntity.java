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

import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.BlockEntity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.ClientboundBlockEntityDataPacket;

public abstract class NetworkedBlockEntity extends BlockEntity {

    public NetworkedBlockEntity(BlockEntityType<?> BlockEntityTypeIn, BlockPos worldPos, BlockState state) {
        super(BlockEntityTypeIn, worldPos, state);
    }

    //region Overrides
    @Override
    public void load(CompoundTag compound) {
        this.loadNetwork(compound);
        super.load(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        this.saveNetwork(compound);
        return super.save(compound);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 1, this.saveNetwork(new CompoundTag()));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveNetwork(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.loadNetwork(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.load(tag);
        this.loadNetwork(tag);
    }

    //endregion Overrides

    //region Methods

    /**
     * Reads networked nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to read from.
     */
    public void loadNetwork(CompoundTag compound) {
    }

    /**
     * Writes network nbt, this is a subset of the entire nbt that is synchronized over network.
     *
     * @param compound the compound to write to.
     * @return the compound written to,
     */
    public CompoundTag saveNetwork(CompoundTag compound) {
        return compound;
    }

    public void markNetworkDirty(){
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
        }
    }
}
