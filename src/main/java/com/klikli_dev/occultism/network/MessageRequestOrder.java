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

package com.klikli_dev.occultism.network;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

/**
 * This message requests a work order to be executed on a storage controller
 */
public class MessageRequestOrder extends MessageBase {

    private GlobalBlockPos storageControllerPosition;
    private GlobalBlockPos targetMachinePosition;
    private ItemStack stack = ItemStack.EMPTY;

    public MessageRequestOrder(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageRequestOrder(GlobalBlockPos storageControllerPosition, GlobalBlockPos targetMachinePosition,
                               ItemStack stack) {
        this.storageControllerPosition = storageControllerPosition;
        this.targetMachinePosition = targetMachinePosition;
        this.stack = stack;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {

        Level level = minecraftServer.getLevel(this.storageControllerPosition.getDimensionKey());
        //prevent block loading by message
        if (!level.hasChunkAt(this.storageControllerPosition.getPos()))
            return;

        BlockEntity blockEntity = level.getBlockEntity(this.storageControllerPosition.getPos());
        if (!(blockEntity instanceof IStorageController storageController))
            return; //early exit because we did not find the storage controller.

        //first dump the order slot back in.
        StorageUtil.clearOpenOrderSlot(player, true);

        //then place the order.
        ItemStackComparator comparator = new ItemStackComparator(this.stack, true);
        storageController.addDepositOrder(this.targetMachinePosition, comparator, this.stack.getCount());
        player.displayClientMessage(
                Component.translatable("network.messages." + Occultism.MODID + ".request_order.order_received"),
                true);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        this.storageControllerPosition.encode(buf);
        this.targetMachinePosition.encode(buf);
        buf.writeItem(this.stack);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.storageControllerPosition = GlobalBlockPos.from(buf);
        this.targetMachinePosition = GlobalBlockPos.from(buf);
        this.stack = buf.readItem();
    }

}
