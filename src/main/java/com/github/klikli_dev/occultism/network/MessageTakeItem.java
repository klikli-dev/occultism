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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * This message requests to take an item from the currently opened storage
 */
public class MessageTakeItem extends MessageBase {

    //region Fields
    private ItemStack stack = ItemStack.EMPTY;
    private int mouseButton;
    private boolean isShiftDown;
    private boolean isCtrlDown;
    //endregion Fields

    //region Initialization

    public MessageTakeItem(PacketBuffer buf) {
        super(buf);
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
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {
        if (player.openContainer instanceof IStorageControllerContainer) {
            IStorageController storageController = ((IStorageControllerContainer) player.openContainer)
                                                           .getStorageController();
            if (storageController == null)
                return;

            int available = storageController
                                    .getAvailableAmount(new ItemStackComparator(this.stack, true));
            int amountRequested = 0;

            //ctrl down means one
            if (this.isCtrlDown) {
                amountRequested = 1;
            }

            //left mouse button takes full stack
            else if (this.mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
                amountRequested = this.stack.getMaxStackSize();
            }

            //right mouse button takes half stack
            else if (this.mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
                amountRequested = Math.min(this.stack.getMaxStackSize() / 2, available / 2);
            }

            amountRequested = Math.max(amountRequested, 1); //need to request at least once
            ItemStack stack = storageController.getItemStack(new ItemStackComparator(this.stack, true),
                    amountRequested, false);

            if (stack.isEmpty()) {
                //if we did not find anything, try again without matching nbt
                stack = storageController.getItemStack(new ItemStackComparator(this.stack, false),
                        amountRequested, false);
            }
            if (!stack.isEmpty()) {
                //if shift click plop it directly into the inventory
                if (this.isShiftDown) {
                    ItemHandlerHelper.giveItemToPlayer(player, stack);
                }
                else {
                    //otherwise put it on the players mouse
                    player.inventory.setItemStack(stack);
                    OccultismPacketHandler.sendTo(player, new MessageUpdateMouseHeldItem(stack));
                }
            }

            //finally, update the storage controller stacks
            OccultismPacketHandler.sendTo(player, storageController.getMessageUpdateStacks());
            player.openContainer.detectAndSendChanges();
        }
    }

    @Override
    public void encode(PacketBuffer buf) {
        ItemStack toWrite = this.stack.copy();
        toWrite.setCount(1);
        buf.writeItemStack(toWrite);
        buf.writeInt(this.stack.getCount());
        buf.writeInt(this.mouseButton);

        buf.writeBoolean(this.isShiftDown);
        buf.writeBoolean(this.isCtrlDown);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.stack = buf.readItemStack();
        this.stack.setCount(buf.readInt());
        this.mouseButton = buf.readInt();
        this.isShiftDown = buf.readBoolean();
        this.isCtrlDown = buf.readBoolean();
    }
    //endregion Overrides
}
