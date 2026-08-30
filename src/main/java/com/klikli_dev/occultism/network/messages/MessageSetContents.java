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
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class MessageSetContents implements IMessage {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "set_contentes");
    public static final Type<MessageSetContents> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSetContents> STREAM_CODEC = CustomPacketPayload.codec(MessageSetContents::encode, MessageSetContents::new);

    public ItemContainerContents contents;
    public InteractionHand hand;

    public MessageSetContents(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetContents(ItemContainerContents contents, InteractionHand hand) {
        this.contents = contents;
        this.hand = hand;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        ItemStack stack = player.getItemInHand(this.hand);
        stack.set(DataComponents.CONTAINER, this.contents);
        player.inventoryMenu.broadcastChanges();
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        ItemContainerContents.STREAM_CODEC.encode(buf, this.contents);
        buf.writeEnum(this.hand);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.contents = ItemContainerContents.STREAM_CODEC.decode(buf);
        this.hand = buf.readEnum(InteractionHand.class);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
