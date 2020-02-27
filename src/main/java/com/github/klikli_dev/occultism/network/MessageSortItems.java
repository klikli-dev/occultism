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
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSortItems extends MessageBase<MessageSortItems> {

    //region Fields
    private BlockPos entityPosition;
    private SortDirection sortDirection;
    private SortType sortType;
    //endregion Fields

    //region Initialization
    public MessageSortItems() {
    }

    public MessageSortItems(BlockPos entityPosition, SortDirection sortDirection, SortType sortType) {
        this.entityPosition = entityPosition;
        this.sortDirection = sortDirection;
        this.sortType = sortType;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageSortItems message, PlayerEntity player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageSortItems message, ServerPlayerEntity player,
                                 MessageContext context) {
        if (player.openContainer instanceof IStorageControllerContainer) {
            if (!((IStorageControllerContainer) player.openContainer).isContainerItem()) {
                TileEntity tileEntity = player.world.getTileEntity(message.entityPosition);
                if (tileEntity instanceof IStorageAccessor) {
                    IStorageAccessor storageAccessor = (IStorageAccessor) tileEntity;
                    storageAccessor.setSortType(message.sortType);
                    storageAccessor.setSortDirection(message.sortDirection);
                    tileEntity.markDirty();
                }
            }
            else {
                //for item remotes, we just set the nbt.
                ItemStack stackPlayerHeld = player.inventory.getCurrentItem();
                ItemNBTUtil.setInteger(stackPlayerHeld, "sortDirection", message.sortDirection.getValue());
                ItemNBTUtil.setInteger(stackPlayerHeld, "sortType", message.sortType.getValue());
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.entityPosition = BlockPos.fromLong(byteBuf.readLong());
        this.sortDirection = SortDirection.get(byteBuf.readByte());
        this.sortType = SortType.get(byteBuf.readByte());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(this.entityPosition.toLong());
        byteBuf.writeByte(this.sortDirection.getValue());
        byteBuf.writeByte(this.sortType.getValue());
    }
    //endregion Overrides
}
