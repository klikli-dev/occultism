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

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class MessageSetWorkAreaSize extends MessageBase {

    public int workAreaSize;

    public MessageSetWorkAreaSize(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetWorkAreaSize(int workAreaSize) {
        this.workAreaSize = workAreaSize;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof BookOfCallingItem) {
            ItemNBTUtil.getSpiritEntity(stack).ifPresent(spirit -> {
                WorkAreaSize workAreaSize = WorkAreaSize.get(this.workAreaSize);
                spirit.setWorkAreaSize(workAreaSize);

                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, spirit);
                player.inventoryMenu.broadcastChanges();

                player.displayClientMessage(Component.translatable(
                        TranslationKeys.BOOK_OF_CALLING_GENERIC +
                                ".message_set_work_area_size",
                        TextUtil.formatDemonName((MutableComponent) spirit.getName()),
                        Component.translatable(workAreaSize.getDescriptionId())), true);
            });
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.workAreaSize);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.workAreaSize = buf.readInt();
    }

}
