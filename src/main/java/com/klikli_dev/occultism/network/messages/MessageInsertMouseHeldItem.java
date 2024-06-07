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
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.util.InputUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * Inserts the mouse held item into the opened storage controller.
 */
public class MessageInsertMouseHeldItem implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "insert_mouse_held_item");
    public static final Type<MessageInsertMouseHeldItem> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageInsertMouseHeldItem> STREAM_CODEC = CustomPacketPayload.codec(MessageInsertMouseHeldItem::encode, MessageInsertMouseHeldItem::new);
    private int mouseButton;

    public MessageInsertMouseHeldItem(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageInsertMouseHeldItem(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        if (player.containerMenu instanceof IStorageControllerContainer) {
            IStorageController storageController = ((IStorageControllerContainer) player.containerMenu)
                    .getStorageController();

            ItemStack result = ItemStack.EMPTY;
            ItemStack carriedByMouse = player.containerMenu.getCarried();

            if (this.mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                //Left mouse button means try to insert entire stack
                int remainder = storageController.insertStack(carriedByMouse, false);
                //if not everything could be inserted, leave the rest in hand
                if (remainder != 0)
                    result = carriedByMouse.copyWithCount(remainder);
            } else if (this.mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                //right mouse button means insert one

                ItemStack toInsert = carriedByMouse.copy();
                toInsert.setCount(1);
                carriedByMouse.shrink(1);

                //handle correct result depending on if the stack was inserted or not
                int remainder = storageController.insertStack(toInsert, false) + carriedByMouse.getCount();
                if (remainder != 0)
                    result = carriedByMouse.copyWithCount(remainder);
            }

            //store result mouse held item
            player.containerMenu.setCarried(result);
            //send new mouse held item to client
            Networking.sendTo(player, new MessageUpdateMouseHeldItem(result));

            //update the storage controller
            Networking.sendTo(player, storageController.getMessageUpdateStacks());
            player.containerMenu.broadcastChanges();
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.mouseButton);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.mouseButton = buf.readInt();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
