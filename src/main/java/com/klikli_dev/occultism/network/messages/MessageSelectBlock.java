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
import com.klikli_dev.occultism.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class MessageSelectBlock implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "select_block");
    public static final Type<MessageSelectBlock> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSelectBlock> STREAM_CODEC = CustomPacketPayload.codec(MessageSelectBlock::encode, MessageSelectBlock::new);

    public BlockPos blockPos;
    public int durationMilliseconds;
    public int color;

    public MessageSelectBlock(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSelectBlock(BlockPos blockPos, int durationMilliseconds, int color) {
        this.blockPos = blockPos;
        this.durationMilliseconds = durationMilliseconds;
        this.color = color;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        Color color = new Color(this.color);
        Occultism.SELECTED_BLOCK_RENDERER.selectBlock(this.blockPos, System.currentTimeMillis() + this.durationMilliseconds, color);
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeInt(this.durationMilliseconds);
        buf.writeInt(this.color);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.durationMilliseconds = buf.readInt();
        this.color = buf.readInt();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
