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
import com.github.klikli_dev.occultism.common.entity.spirits.EntitySpirit;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import com.github.klikli_dev.occultism.util.TextUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetWorkAreaSize extends MessageBase<MessageSetWorkAreaSize> {

    //region Fields
    public int workAreaSize;
    //endregion Fields

    //region Initialization
    public MessageSetWorkAreaSize() {
    }

    public MessageSetWorkAreaSize(int workAreaSize) {
        this.workAreaSize = workAreaSize;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageSetWorkAreaSize message, EntityPlayer player,
                                 MessageContext context) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageSetWorkAreaSize message, EntityPlayerMP player,
                                 MessageContext context) {
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.getItem() instanceof ItemBookOfCallingActive) {
            EntitySpirit spirit = ItemNBTUtil.getSpiritEntity(stack);
            if (spirit != null) {
                WorkAreaSize workAreaSize = WorkAreaSize.get(message.workAreaSize);
                spirit.setWorkAreaSize(workAreaSize);

                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, spirit);
                player.inventoryContainer.detectAndSendChanges();

                player.sendStatusMessage(new TextComponentTranslation(
                        "item." + Occultism.MODID + ".book_of_calling_active.message_set_work_area_size",
                        TextUtil.formatDemonName(spirit.getName()),
                        new TextComponentTranslation(workAreaSize.getTranslationKey())), true);
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.workAreaSize = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.workAreaSize);
    }
    //endregion Overrides
}
