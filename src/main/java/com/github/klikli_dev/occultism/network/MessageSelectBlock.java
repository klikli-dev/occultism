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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.item.IHandleItemMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.awt.*;

public class MessageSelectBlock extends MessageBase {

    //region Fields
    public BlockPos blockPos;
    public int durationMilliseconds;
    public int color;
    //endregion Fields

    //region Initialization

    public MessageSelectBlock(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageSelectBlock(BlockPos blockPos, int durationMilliseconds, int color) {
        this.blockPos = blockPos;
        this.durationMilliseconds = durationMilliseconds;
        this.color = color;
    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onClientReceived(Minecraft minecraft, PlayerEntity player, NetworkEvent.Context context) {
        Color color = new Color(this.color);
        Occultism.SELECTED_BLOCK_RENDERER.selectBlock(this.blockPos, System.currentTimeMillis() + this.durationMilliseconds, color);
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeInt(this.durationMilliseconds);
        buf.writeInt(this.color);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.blockPos = buf.readBlockPos();
        this.durationMilliseconds = buf.readInt();
        this.color = buf.readInt();
    }
    //endregion Overrides
}
