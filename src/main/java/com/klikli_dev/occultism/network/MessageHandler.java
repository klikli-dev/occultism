// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.network;

import com.klikli_dev.theurgy.network.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageHandler {

    public static <T extends CustomPacketPayload> void handle(T payload, PlayPayloadContext ctx) {
        if (ctx.flow().getReceptionSide() == LogicalSide.SERVER) {
            ctx.workHandler().submitAsync(() -> {
                handleServer(payload, ctx);
            });
        } else {
            ctx.workHandler().submitAsync(() -> {
                //separate class to avoid loading client code on server.
                ClientMessageHandler.handleClient(payload, ctx);
            });
        }
    }

    public static <T extends CustomPacketPayload> void handleServer(T payload, PlayPayloadContext ctx) {
        MinecraftServer server = ctx.level().get().getServer();
        if (payload instanceof IMessage message)
            message.onServerReceived(server, (ServerPlayer) ctx.player().get());
    }

    public static class ClientMessageHandler {

        public static <T extends CustomPacketPayload> void handleClient(T payload, PlayPayloadContext ctx) {
            Minecraft minecraft = Minecraft.getInstance();
            if (payload instanceof IMessage message)
                message.onClientReceived(minecraft, minecraft.player);
        }
    }
}
