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

import com.klikli_dev.occultism.common.container.storage.SatchelContainer;
import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.NetworkHooks;

public class MessageOpenSatchel extends MessageBase {

    public MessageOpenSatchel(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenSatchel() {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {

        int selectedSlot = -1;
        //first attempt to get backpack from curios slot
        ItemStack backpackStack = CuriosUtil.getBackpack(player);

        //if not found, try to get from player inventory
        if (!(backpackStack.getItem() instanceof SatchelItem)) {
            selectedSlot = CuriosUtil.getFirstBackpackSlot(player);
            backpackStack = selectedSlot > 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a satchel, proceed
        if (backpackStack.getItem() instanceof SatchelItem) {
            ItemStack finalBackpackStack = backpackStack;
            int finalSelectedSlot = selectedSlot;
            NetworkHooks.openScreen(player,
                    new SimpleMenuProvider((id, playerInventory, unused) -> {
                        return new SatchelContainer(id, playerInventory,
                                ((SatchelItem) finalBackpackStack.getItem()).getInventory(player, finalBackpackStack),
                                finalSelectedSlot);
                    }, backpackStack.getDisplayName()), buffer -> {
                        buffer.writeVarInt(finalSelectedSlot);
                    });
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }

}
