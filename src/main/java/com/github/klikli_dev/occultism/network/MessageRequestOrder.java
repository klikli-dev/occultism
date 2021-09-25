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
import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * This message requests a work order to be executed on a storage controller
 */
public class MessageRequestOrder extends MessageBase {

    //region Fields
    private GlobalBlockPos storageControllerPosition;
    private GlobalBlockPos targetMachinePosition;
    private ItemStack stack = ItemStack.EMPTY;
    //endregion Fields

    //region Initialization

    public MessageRequestOrder(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageRequestOrder(GlobalBlockPos storageControllerPosition, GlobalBlockPos targetMachinePosition,
                               ItemStack stack) {
        this.storageControllerPosition = storageControllerPosition;
        this.targetMachinePosition = targetMachinePosition;
        this.stack = stack;
    }

    //endregion Initialization


    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {

        World world = minecraftServer.getLevel(this.storageControllerPosition.getDimensionKey());
        //prevent block loading by message
        if (!world.hasChunkAt(this.storageControllerPosition.getPos()))
            return;

        TileEntity tileEntity = world.getBlockEntity(this.storageControllerPosition.getPos());
        if (!(tileEntity instanceof IStorageController))
            return; //early exit because we did not find the storage controller.

        IStorageController storageController = (IStorageController) tileEntity;

        //first dump the order slot back in.
        StorageUtil.clearOpenOrderSlot(player, true);

        //then place the order.
        ItemStackComparator comparator = new ItemStackComparator(this.stack, true);
        storageController.addDepositOrder(this.targetMachinePosition, comparator, this.stack.getCount());
        player.displayClientMessage(
                new TranslationTextComponent("network.messages." + Occultism.MODID + ".request_order.order_received"),
                true);
    }

    @Override
    public void encode(PacketBuffer buf) {
        this.storageControllerPosition.encode(buf);
        this.targetMachinePosition.encode(buf);
        buf.writeItem(this.stack);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.storageControllerPosition = GlobalBlockPos.from(buf);
        this.targetMachinePosition = GlobalBlockPos.from(buf);
        this.stack = buf.readItem();
    }
    //endregion Overrides
}
