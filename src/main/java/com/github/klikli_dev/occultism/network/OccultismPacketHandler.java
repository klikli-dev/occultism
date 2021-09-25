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

package com.github.klikli_dev.occultism.network;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OccultismPacketHandler {
    //region Static Methods
    public static <T extends IMessage> void handle(T message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
            ctx.get().enqueueWork(() -> {
                handleServer(message, ctx);
            });
        } else {
            ctx.get().enqueueWork(() -> {
                handleClient(message, ctx);
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public static <T extends IMessage> void handleServer(T message, Supplier<NetworkEvent.Context> ctx) {
        MinecraftServer server = ctx.get().getSender().level.getServer();
        message.onServerReceived(server, ctx.get().getSender(), ctx.get());
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends IMessage> void handleClient(T message, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        message.onClientReceived(minecraft, minecraft.player, ctx.get());
    }
    //endregion Static Methods
}
