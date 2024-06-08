/*
 * MIT License
 *
 * Copyright 2021 vemerion
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
import com.klikli_dev.occultism.common.entity.familiar.HeadlessFamiliarEntity;
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MessageHeadlessDie implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "headless_die");
    public static final Type<MessageHeadlessDie> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageHeadlessDie> STREAM_CODEC = CustomPacketPayload.codec(MessageHeadlessDie::encode, MessageHeadlessDie::new);
    private int id;

    public MessageHeadlessDie(int id) {
        this.id = id;
    }

    public MessageHeadlessDie(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.id = buf.readInt();
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        Entity headless = minecraft.level.getEntity(this.id);
        if (headless instanceof HeadlessFamiliarEntity)
            ((HeadlessFamiliarEntity) headless).killHeadless();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
