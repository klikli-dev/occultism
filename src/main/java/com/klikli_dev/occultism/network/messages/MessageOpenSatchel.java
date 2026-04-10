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
import com.klikli_dev.occultism.common.container.satchel.StorageSatchelContainer;
import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class MessageOpenSatchel implements IMessage {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "open_satchel");
    public static final Type<MessageOpenSatchel> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageOpenSatchel> STREAM_CODEC = CustomPacketPayload.codec(MessageOpenSatchel::encode, MessageOpenSatchel::new);

    public MessageOpenSatchel(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenSatchel() {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {

        int selectedSlot = player.getInventory().selected;
        ItemStack backpackStack = player.getInventory().getSelected();

        // first attempt to get backpack from the currently selected stack to match right-click behavior
        if (!(backpackStack.getItem() instanceof SatchelItem)) {
            selectedSlot = -1;
            backpackStack = CuriosUtil.getBackpack(player);
        }

        // if not found, try to get from player inventory
        if (!(backpackStack.getItem() instanceof SatchelItem)) {
            selectedSlot = CuriosUtil.getFirstBackpackSlot(player);
            backpackStack = selectedSlot >= 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }

        // now, if we have a satchel, proceed
        if (backpackStack.getItem() instanceof SatchelItem) {
            if (!backpackStack.has(com.klikli_dev.occultism.registry.OccultismDataComponents.SATCHEL_UUID)) {
                backpackStack.set(com.klikli_dev.occultism.registry.OccultismDataComponents.SATCHEL_UUID, UUID.randomUUID());
            }
            UUID satchelUuid = backpackStack.get(com.klikli_dev.occultism.registry.OccultismDataComponents.SATCHEL_UUID);

            ItemStack finalBackpackStack = backpackStack;
            int finalSelectedSlot = selectedSlot;
            player.openMenu(
                    new SimpleMenuProvider((id, playerInventory, unused) -> {
                        return new StorageSatchelContainer(id, playerInventory,
                                ((SatchelItem) finalBackpackStack.getItem()).getInventory(player, finalBackpackStack),
                                finalSelectedSlot, satchelUuid);
                    }, backpackStack.getDisplayName()), buffer -> {
                        buffer.writeVarInt(finalSelectedSlot);
                        buffer.writeUUID(satchelUuid);
                    });
        }
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
