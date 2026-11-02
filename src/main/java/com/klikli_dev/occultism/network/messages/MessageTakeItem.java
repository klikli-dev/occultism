/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, MrRiegel, Sam Bassett
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
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.util.InputUtil;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * This message requests to take an item from the currently opened storage Based on
 * https://github.com/Lothrazar/Storage-Network
 */
public class MessageTakeItem implements IMessage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "take_item");
    public static final Type<MessageTakeItem> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageTakeItem> STREAM_CODEC = CustomPacketPayload.codec(MessageTakeItem::encode, MessageTakeItem::new);

    private ItemStack stack = ItemStack.EMPTY;
    private int slot = -1;
    private int mouseButton;
    private boolean isShiftDown;
    private boolean isCtrlDown;

    public MessageTakeItem(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageTakeItem(ItemStack stack, int mouseButton, boolean isShiftDown, boolean isCtrlDown) {
        this(stack, -1, mouseButton, isShiftDown, isCtrlDown);
    }

    public MessageTakeItem(ItemStack stack, int slot, int mouseButton, boolean isShiftDown, boolean isCtrlDown) {
        this.slot = slot;
        this.mouseButton = mouseButton;
        this.stack = stack;
        this.isShiftDown = isShiftDown;
        this.isCtrlDown = isCtrlDown;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        IStorageControllerContainer container = StorageUtil.getValidStorageControllerContainer(player);
        if (container != null) {
            IStorageController storageController = container
                    .getStorageController();
            if (storageController == null)
                return;

            ItemStack stack = ItemStack.EMPTY;
            if (this.slot >= 0) {
                stack = this.takeFromSlot(storageController);
            }

            if (stack.isEmpty()) {
                stack = this.takeByComparator(storageController);
            }

            if (!stack.isEmpty()) {
                //if shift click plop it directly into the inventory
                if (this.isShiftDown) {
                    ItemTransferUtil.giveItemToPlayer(player, stack);
                } else {
                    //otherwise put it on the players mouse
                    player.containerMenu.setCarried(stack);
                    Networking.sendTo(player, new MessageUpdateMouseHeldItem(stack));
                }
            }

            //finally, update the storage controller stacks
            Networking.sendTo(player, storageController.getMessageUpdateStacks());
            player.containerMenu.broadcastChanges();
        }
    }

    private int computeAmountRequested(int maxStackSize, int available) {
        int amountRequested = 0;

        //ctrl down means one
        if (this.isCtrlDown) {
            amountRequested = 1;
        }

        //left mouse button takes full stack
        else if (this.mouseButton == InputUtil.MOUSE_BUTTON_LEFT) {
            amountRequested = maxStackSize;
        }

        //right mouse button takes half stack
        else if (this.mouseButton == InputUtil.MOUSE_BUTTON_RIGHT) {
            amountRequested = Math.min(maxStackSize / 2, available / 2);
        }

        return Math.max(amountRequested, 1); //need to request at least once
    }

    private ItemStack takeFromSlot(IStorageController storageController) {
        ItemStack stackInSlot = storageController.getStackInSlot(this.slot);
        if (stackInSlot.isEmpty() || stackInSlot.getItem() != this.stack.getItem()) {
            return ItemStack.EMPTY;
        }

        int available = storageController.getAvailableAmountInSlot(this.slot);
        int amountRequested = this.computeAmountRequested(stackInSlot.getMaxStackSize(), available);
        return storageController.getItemStackInSlot(this.slot, amountRequested, false);
    }

    private ItemStack takeByComparator(IStorageController storageController) {
        int available = storageController
                .getAvailableAmount(new ItemStackComparator(this.stack, true));
        int amountRequested = this.computeAmountRequested(this.stack.getMaxStackSize(), available);

        ItemStack stack = storageController.getItemStack(new ItemStackComparator(this.stack, true),
                amountRequested, false);

        if (stack.isEmpty()) {
            //if we did not find anything, try again without matching nbt
            stack = storageController.getItemStack(new ItemStackComparator(this.stack, false),
                    amountRequested, false);
        }
        return stack;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        ItemStack toWrite = this.stack.copyWithCount(1);
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, toWrite);
        buf.writeInt(this.stack.getCount());
        buf.writeVarInt(this.slot);
        buf.writeByte(this.mouseButton);

        buf.writeBoolean(this.isShiftDown);
        buf.writeBoolean(this.isCtrlDown);
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        this.stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
        this.stack.setCount(buf.readInt());
        this.slot = buf.readVarInt();
        this.mouseButton = buf.readByte();
        this.isShiftDown = buf.readBoolean();
        this.isCtrlDown = buf.readBoolean();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

