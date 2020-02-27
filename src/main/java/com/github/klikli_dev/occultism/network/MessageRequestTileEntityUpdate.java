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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRequestTileEntityUpdate extends MessageBase<MessageRequestTileEntityUpdate> {

    //region Fields
    private BlockPos pos;
    private int dimension;
    //endregion Fields

    //region Initialization
    public MessageRequestTileEntityUpdate() {
    }

    public MessageRequestTileEntityUpdate(TileEntity entity) {
        this.pos = entity.getPos();
        this.dimension = entity.getWorld().provider.getDimension();
    }

    public MessageRequestTileEntityUpdate(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageRequestTileEntityUpdate message, EntityPlayer player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageRequestTileEntityUpdate message,
                                 EntityPlayerMP player, MessageContext context) {
        World world = minecraftServer.getWorld(message.dimension);
        TileEntity entity = world.getTileEntity(message.pos);
        if (entity != null) {
            SPacketUpdateTileEntity updatePacket = entity.getUpdatePacket();
            if (updatePacket != null)
                player.connection.sendPacket(updatePacket);
            world.markChunkDirty(message.pos, entity);
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.pos = BlockPos.fromLong(byteBuf.readLong());
        this.dimension = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(this.pos.toLong());
        byteBuf.writeInt(this.dimension);
    }
    //endregion Overrides
}
