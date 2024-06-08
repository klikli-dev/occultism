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
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class MessageSetTagFilterText implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "set_tag_filter_text");
    public static final Type<MessageSetTagFilterText> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetTagFilterText> STREAM_CODEC = CustomPacketPayload.codec(MessageSetTagFilterText::encode, MessageSetTagFilterText::new);

    public String tagFilterText;
    public int entityId;

    public MessageSetTagFilterText(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetTagFilterText(String tagFilterText, int entityId) {
        this.tagFilterText = tagFilterText;
        this.entityId = entityId;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {

        Entity e = player.level().getEntity(this.entityId);
        if (e instanceof SpiritEntity spirit) {
            spirit.setTagFilter(this.tagFilterText);
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeUtf(this.tagFilterText);
        buf.writeInt(this.entityId);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.tagFilterText = buf.readUtf(255);
        this.entityId = buf.readInt();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
