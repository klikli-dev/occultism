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

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageParticle extends MessageBase<MessageParticle> {
    //region Fields
    public EnumParticleTypes particleType;
    public double x;
    public double y;
    public double z;
    //endregion Fields

    //region Initialization
    public MessageParticle() {
    }

    public MessageParticle(EnumParticleTypes type, double x, double y, double z) {
        this.particleType = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.particleType = EnumParticleTypes.getParticleFromId(byteBuf.readInt());
        this.x = byteBuf.readDouble();
        this.y = byteBuf.readDouble();
        this.z = byteBuf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.particleType.getParticleID());
        byteBuf.writeDouble(this.x);
        byteBuf.writeDouble(this.y);
        byteBuf.writeDouble(this.z);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, MessageParticle message, PlayerEntity player,
                                 MessageContext context) {
        minecraft.world.spawnParticle(message.particleType, message.x, message.y, message.z, 0, 0, 0);
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageParticle message, ServerPlayerEntity player,
                                 MessageContext context) {

    }
    //endregion Overrides
}
