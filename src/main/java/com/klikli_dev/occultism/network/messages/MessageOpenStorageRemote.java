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
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class MessageOpenStorageRemote implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "open_storage_remote");

    public MessageOpenStorageRemote(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenStorageRemote() {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        CuriosUtil.SelectedCurio selectedCurio = CuriosUtil.getStorageRemote(player);
        if (selectedCurio != null) {

            if (!selectedCurio.itemStack.getOrCreateTag().contains("linkedStorageController"))
                return;

            GlobalBlockPos storageControllerPos = GlobalBlockPos.from(
                    selectedCurio.itemStack.getTag().getCompound("linkedStorageController"));
            Level storageControllerWorld = minecraftServer.getLevel(storageControllerPos.getDimensionKey());
            if (storageControllerWorld.getBlockEntity(storageControllerPos.getPos()) instanceof IStorageController) {
                player.openMenu(OccultismItems.STORAGE_REMOTE.get(), buffer -> buffer.writeVarInt(selectedCurio.selectedSlot));
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
