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

package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.data.SortDirection;
import com.github.klikli_dev.occultism.api.common.data.SortType;
import com.github.klikli_dev.occultism.api.common.tile.IStorageAccessor;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSortItems extends MessageBase {

    //region Fields
    private BlockPos entityPosition;
    private SortDirection sortDirection;
    private SortType sortType;
    //endregion Fields

    //region Initialization

    public MessageSortItems(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageSortItems(BlockPos entityPosition, SortDirection sortDirection, SortType sortType) {
        this.entityPosition = entityPosition;
        this.sortDirection = sortDirection;
        this.sortType = sortType;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {
        if (player.openContainer instanceof IStorageControllerContainer) {
            if (!((IStorageControllerContainer) player.openContainer).isContainerItem()) {

                //ensure players cannot load arbitrary chunks
                if (!player.world.isBlockLoaded(this.entityPosition))
                    return;

                TileEntity tileEntity = player.world.getTileEntity(this.entityPosition);
                if (tileEntity instanceof IStorageAccessor) {
                    IStorageAccessor storageAccessor = (IStorageAccessor) tileEntity;
                    storageAccessor.setSortType(this.sortType);
                    storageAccessor.setSortDirection(this.sortDirection);
                    tileEntity.markDirty();
                }
            }
            else {
                //for item remotes, we just set the nbt.
                ItemStack stack = player.inventory.getCurrentItem();
                stack.getOrCreateTag().putInt("sortDirection", this.sortDirection.getValue());
                stack.getTag().putInt("sortType", this.sortType.getValue());
            }
        }
    }

    @Override
    public void encode(PacketBuffer byteBuf) {
        byteBuf.writeBlockPos(this.entityPosition);
        byteBuf.writeByte(this.sortDirection.getValue());
        byteBuf.writeByte(this.sortType.getValue());
    }

    @Override
    public void decode(PacketBuffer byteBuf) {
        this.entityPosition = byteBuf.readBlockPos();
        this.sortDirection = SortDirection.get(byteBuf.readByte());
        this.sortType = SortType.get(byteBuf.readByte());
    }
    //endregion Overrides
}
