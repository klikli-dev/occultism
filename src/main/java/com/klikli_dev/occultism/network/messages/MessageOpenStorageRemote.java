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
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class MessageOpenStorageRemote implements IMessage {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "open_storage_remote");
    public static final Type<MessageOpenStorageRemote> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageOpenStorageRemote> STREAM_CODEC = CustomPacketPayload.codec(MessageOpenStorageRemote::encode, MessageOpenStorageRemote::new);

    public MessageOpenStorageRemote(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenStorageRemote() {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        CuriosUtil.SelectedCurio selectedCurio = CuriosUtil.getStorageRemote(player);
        if (selectedCurio != null) {

            if (!selectedCurio.itemStack.has(OccultismDataComponents.LINKED_STORAGE_CONTROLLER))
                return;

            GlobalBlockPos storageControllerPos = selectedCurio.itemStack.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);
            Level storageControllerWorld = minecraftServer.getLevel(storageControllerPos.getDimensionKey());
            if (storageControllerWorld.getBlockEntity(storageControllerPos.getPos()) instanceof IStorageController) {
                player.openMenu(OccultismItems.STORAGE_REMOTE.get(), buffer -> buffer.writeVarInt(selectedCurio.selectedSlot));
            }
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
