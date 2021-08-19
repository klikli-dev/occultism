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

import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class MessageOpenStorageRemote extends MessageBase {

    //region Initialization

    public MessageOpenStorageRemote(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenStorageRemote() {

    }
    //endregion Initialization

    //region Overrides

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {

        int selectedSlot = -1;
        //first attempt to get storage remote from curios slot
        ItemStack storageRemoteStack = CuriosUtil.getStorageRemote(player);

        //if not found, try to get from player inventory
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = CuriosUtil.getFirstStorageRemoteSlot(player);
            storageRemoteStack = selectedSlot > 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a storage remote, proceed
        if (storageRemoteStack.getItem() instanceof StorageRemoteItem) {
            int finalSelectedSlot = selectedSlot;

            NetworkHooks.openGui(player, OccultismItems.STORAGE_REMOTE.get(),
                    buffer -> buffer.writeVarInt(finalSelectedSlot));
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }
    //endregion Overrides
}
