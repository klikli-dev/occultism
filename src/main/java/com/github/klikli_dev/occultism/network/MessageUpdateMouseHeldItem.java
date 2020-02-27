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
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Updates the item currently held by the mouse
 */
public class MessageUpdateMouseHeldItem extends MessageBase<MessageUpdateMouseHeldItem> {

    //region Fields
    private ItemStack stack;
    //endregion Fields

    //region Initialization
    public MessageUpdateMouseHeldItem() {
    }

    public MessageUpdateMouseHeldItem(ItemStack itemStack) {
        this.stack = itemStack;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageUpdateMouseHeldItem message, PlayerEntity player,
                                 MessageContext context) {
        player.inventory.setItemStack(message.stack);
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageUpdateMouseHeldItem message,
                                 ServerPlayerEntity player, MessageContext context) {

    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.stack = ByteBufUtils.readItemStack(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeItemStack(byteBuf, this.stack);
    }
    //endregion Overrides
}
