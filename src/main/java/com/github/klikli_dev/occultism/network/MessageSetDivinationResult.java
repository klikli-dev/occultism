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

import com.github.klikli_dev.occultism.common.item.ItemDivinationRod;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetDivinationResult extends MessageBase<MessageSetDivinationResult> {

    //region Fields
    public byte result;
    //endregion Fields

    //region Initialization
    public MessageSetDivinationResult() {
    }

    public MessageSetDivinationResult(float result) {
        this.result = (byte) result;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageSetDivinationResult message, PlayerEntity player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageSetDivinationResult message,
                                 ServerPlayerEntity player, MessageContext context) {
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        if (stack.getItem() instanceof ItemDivinationRod) {
            ItemNBTUtil.setFloat(stack, "distance", message.result);
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.result = byteBuf.readByte();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeByte(this.result);
    }
    //endregion Overrides
}
