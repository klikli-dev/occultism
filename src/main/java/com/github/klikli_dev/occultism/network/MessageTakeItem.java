/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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
import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.util.InputUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * This message requests to take an item from the currently openeds torage
 */
public class MessageTakeItem extends MessageBase<MessageTakeItem> {

    //region Fields
    private ItemStack stack = ItemStack.EMPTY;
    private int mouseButton;
    private boolean isShiftDown;
    private boolean isCtrlDown;
    //endregion Fields

    //region Initialization
    public MessageTakeItem() {
    }

    public MessageTakeItem(ItemStack stack, int mouseButton, boolean isShiftDown, boolean isCtrlDown) {
        this.mouseButton = mouseButton;
        this.stack = stack;
        this.isShiftDown = isShiftDown;
        this.isCtrlDown = isCtrlDown;
    }
    //endregion Initialization


    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageTakeItem message, PlayerEntity player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageTakeItem message, ServerPlayerEntity player,
                                 MessageContext context) {

        if (player.openContainer instanceof IStorageControllerContainer) {
            IStorageController storageController = ((IStorageControllerContainer) player.openContainer)
                                                           .getStorageController();
            if (storageController == null)
                return;

            int available = storageController
                                    .getAvailableAmount(new ItemStackComparator(message.stack, true, false, true));
            int amountRequested = 0;

            //ctrl down means one
            if (message.isCtrlDown) {
                amountRequested = 1;
            }

            //left mouse button takes full stack
            else if (message.mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                amountRequested = message.stack.getMaxStackSize();
            }

            //right mouse button takes half stack
            else if (message.mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                amountRequested = Math.min(message.stack.getMaxStackSize() / 2, available / 2);
            }

            amountRequested = Math.max(amountRequested, 1); //need to request at least once
            ItemStack stack = storageController.getItemStack(new ItemStackComparator(message.stack, true, false, true),
                    amountRequested, false);

            if (stack.isEmpty()) {
                //if we did not find anything, try again without matching nbt
                stack = storageController.getItemStack(new ItemStackComparator(message.stack, true, false, false),
                        amountRequested, false);
            }
            if (!stack.isEmpty()) {
                //if shift click plop it directly into the inventory
                if (message.isShiftDown) {
                    ItemHandlerHelper.giveItemToPlayer(player, stack);
                }
                else {
                    //otherwise put it on the players mouse
                    player.inventory.setItemStack(stack);
                    Occultism.network.sendTo(new MessageUpdateMouseHeldItem(stack), player);
                }
            }

            //finally, update the storage controller stacks
            Occultism.network.sendTo(storageController.getMessageUpdateStacks(), player);
            player.openContainer.detectAndSendChanges();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.stack.setCount(buf.readInt());
        this.mouseButton = buf.readInt();
        this.isShiftDown = buf.readBoolean();
        this.isCtrlDown = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ItemStack toWrite = this.stack.copy();
        toWrite.setCount(1);
        ByteBufUtils.writeItemStack(buf, toWrite);
        buf.writeInt(this.stack.getCount());
        buf.writeInt(this.mouseButton);

        buf.writeBoolean(this.isShiftDown);
        buf.writeBoolean(this.isCtrlDown);
    }
    //endregion Overrides
}
