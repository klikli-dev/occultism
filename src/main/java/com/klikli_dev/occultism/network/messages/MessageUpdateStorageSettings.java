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
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * Inserts the mouse held item into the opened storage controller.
 */
public class MessageUpdateStorageSettings implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "update_storage_settings");
    public static final Type<MessageUpdateStorageSettings> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdateStorageSettings> STREAM_CODEC = CustomPacketPayload.codec(MessageUpdateStorageSettings::encode, MessageUpdateStorageSettings::new);
    private SortDirection sortDirection;
    private SortType sortType;


    public MessageUpdateStorageSettings(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageUpdateStorageSettings(SortDirection sortDirection, SortType sortType) {
        this.sortDirection = sortDirection;
        this.sortType = sortType;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        if (player.containerMenu instanceof IStorageControllerContainer storageContainer) {
            IStorageController storageController = storageContainer.getStorageController();

            //handle storage remotes
            if (storageContainer instanceof StorageRemoteContainer storageRemoteContainer) {
                ItemStack remote = storageRemoteContainer.getStorageRemote();
                if (remote != ItemStack.EMPTY) {
                    remote.set(OccultismDataComponents.SORT_DIRECTION, this.sortDirection);
                    remote.set(OccultismDataComponents.SORT_TYPE, this.sortType);
                    player.containerMenu.broadcastChanges();
                }
            }

            //handle storage controller
            if (storageContainer instanceof StorageControllerContainer storageControllerContainer) {
                storageControllerContainer.getStorageController().setSortDirection(this.sortDirection);
                storageControllerContainer.getStorageController().setSortType(this.sortType);
                storageControllerContainer.getStorageController().markNetworkDirty();
            }

            //handle stable wormhole
            if (storageContainer instanceof StableWormholeContainer stableWormholeContainer) {
                stableWormholeContainer.getStableWormhole().setSortDirection(this.sortDirection);
                stableWormholeContainer.getStableWormhole().setSortType(this.sortType);
                stableWormholeContainer.getStableWormhole().markNetworkDirty();
            }
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeEnum(this.sortDirection);
        buf.writeEnum(this.sortType);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.sortDirection = buf.readEnum(SortDirection.class);
        this.sortType = buf.readEnum(SortType.class);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
