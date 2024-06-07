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

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.blockentity.IStorageAccessor;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageSortItems implements IMessage {
    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "sort_items");
    public static final Type<MessageSortItems> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSortItems> STREAM_CODEC = CustomPacketPayload.codec(MessageSortItems::encode, MessageSortItems::new);
    private BlockPos entityPosition;
    private SortDirection sortDirection;
    private SortType sortType;

    public MessageSortItems(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSortItems(BlockPos entityPosition, SortDirection sortDirection, SortType sortType) {
        this.entityPosition = entityPosition;
        this.sortDirection = sortDirection;
        this.sortType = sortType;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
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
                stack.set(OccultismDataComponents.SORT_DIRECTION, this.sortDirection);
                stack.set(OccultismDataComponents.SORT_TYPE, this.sortType);
            }
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf byteBuf) {
        byteBuf.writeBlockPos(this.entityPosition);
        SortDirection.STREAM_CODEC.encode(byteBuf, this.sortDirection);
        SortType.STREAM_CODEC.encode(byteBuf, this.sortType);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf byteBuf) {
        this.entityPosition = byteBuf.readBlockPos();
        this.sortDirection = SortDirection.STREAM_CODEC.decode(byteBuf);
        this.sortType = SortType.STREAM_CODEC.decode(byteBuf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
