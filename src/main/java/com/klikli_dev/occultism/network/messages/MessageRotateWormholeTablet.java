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
import com.klikli_dev.occultism.common.item.storage.TeleportTabletItem;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismItems;
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

/**
 * Requests the server to rotate the compasses stored in the wormhole tablet held by the player.
 * <p>
 * Only the rotation direction is sent, never the contents itself, so the server stays authoritative over the
 * item stack. This makes it impossible to use this message to insert arbitrary items into the tablet.
 */
public class MessageRotateWormholeTablet implements IMessage {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "rotate_wormhole_tablet");
    public static final Type<MessageRotateWormholeTablet> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageRotateWormholeTablet> STREAM_CODEC = CustomPacketPayload.codec(MessageRotateWormholeTablet::encode, MessageRotateWormholeTablet::new);

    public boolean forward;
    public InteractionHand hand;

    public MessageRotateWormholeTablet(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageRotateWormholeTablet(boolean forward, InteractionHand hand) {
        this.forward = forward;
        this.hand = hand;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        ItemStack stack = player.getItemInHand(this.hand);
        //never trust the client to tell us what it is holding
        if (!stack.is(OccultismItems.WORMHOLE_TABLET.get()))
            return;

        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents == null)
            return;

        ItemContainerContents rotated = TeleportTabletItem.rotateContents(contents, this.forward ? 1 : -1);
        if (rotated == contents)
            return;

        stack.set(DataComponents.CONTAINER, rotated);
        player.inventoryMenu.broadcastChanges();
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(this.forward);
        buf.writeEnum(this.hand);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.forward = buf.readBoolean();
        this.hand = buf.readEnum(InteractionHand.class);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
