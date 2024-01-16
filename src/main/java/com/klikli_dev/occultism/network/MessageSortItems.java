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

package com.klikli_dev.occultism.network;

import com.klikli_dev.occultism.api.common.blockentity.IStorageAccessor;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.NetworkEvent;

public class MessageSortItems extends MessageBase {

    private BlockPos entityPosition;
    private SortDirection sortDirection;
    private SortType sortType;

    public MessageSortItems(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSortItems(BlockPos entityPosition, SortDirection sortDirection, SortType sortType) {
        this.entityPosition = entityPosition;
        this.sortDirection = sortDirection;
        this.sortType = sortType;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {
        if (player.containerMenu instanceof IStorageControllerContainer) {
            if (!((IStorageControllerContainer) player.containerMenu).isContainerItem()) {

                //ensure players cannot load arbitrary chunks
                if (!player.level().hasChunkAt(this.entityPosition))
                    return;

                BlockEntity blockEntity = player.level().getBlockEntity(this.entityPosition);
                if (blockEntity instanceof IStorageAccessor storageAccessor) {
                    storageAccessor.setSortType(this.sortType);
                    storageAccessor.setSortDirection(this.sortDirection);
                    blockEntity.setChanged();
                }
            } else {
                //for item remotes, we just set the nbt.
                ItemStack stack = player.getInventory().getSelected();
                stack.getOrCreateTag().putInt("sortDirection", this.sortDirection.getValue());
                stack.getTag().putInt("sortType", this.sortType.getValue());
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeBlockPos(this.entityPosition);
        byteBuf.writeByte(this.sortDirection.getValue());
        byteBuf.writeByte(this.sortType.getValue());
    }

    @Override
    public void decode(FriendlyByteBuf byteBuf) {
        this.entityPosition = byteBuf.readBlockPos();
        this.sortDirection = SortDirection.get(byteBuf.readByte());
        this.sortType = SortType.get(byteBuf.readByte());
    }

}
