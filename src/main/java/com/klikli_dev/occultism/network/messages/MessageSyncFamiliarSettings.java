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
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MessageSyncFamiliarSettings implements IMessage {
    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "sync_familiar_settings");
    public static final Type<MessageSyncFamiliarSettings> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSyncFamiliarSettings> STREAM_CODEC = CustomPacketPayload.codec(MessageSyncFamiliarSettings::encode, MessageSyncFamiliarSettings::new);

    public CompoundTag tag;

    public MessageSyncFamiliarSettings(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSyncFamiliarSettings(FamiliarSettingsData cap, RegistryAccess registryAccess) {
        this.tag = cap.serializeNBT(registryAccess);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        player.getData(OccultismDataStorage.FAMILIAR_SETTINGS).deserializeNBT(player.registryAccess(), this.tag);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf byteBuf) {
        byteBuf.writeNbt(this.tag);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf byteBuf) {
        this.tag = byteBuf.readNbt();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
