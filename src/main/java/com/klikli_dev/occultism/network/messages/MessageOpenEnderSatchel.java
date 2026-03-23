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
import com.klikli_dev.occultism.common.item.storage.EnderSatchelItem;
import com.klikli_dev.occultism.network.IMessage;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.CuriosUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class MessageOpenEnderSatchel implements IMessage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "open_ender_satchel");
    public static final Type<MessageOpenEnderSatchel> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageOpenEnderSatchel> STREAM_CODEC = CustomPacketPayload.codec(MessageOpenEnderSatchel::encode, MessageOpenEnderSatchel::new);

    public MessageOpenEnderSatchel(RegistryFriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageOpenEnderSatchel() {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {

        int selectedSlot = -1;
        //first attempt to get backpack from curios slot
        ItemStack backpackStack = CuriosUtil.getEnderSatchel(player);

        //if not found, try to get from player inventory
        if (!(backpackStack.getItem() instanceof EnderSatchelItem)) {
            selectedSlot = CuriosUtil.getFirstEnderSatchelSlot(player);
            backpackStack = selectedSlot > 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a satchel, proceed
        if (backpackStack.getItem() instanceof EnderSatchelItem enderSatchelItem) {

            String name = player.getName().getString();
            PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
            if (backpackStack.has(OccultismDataComponents.LINKED_PLAYER_UUID)) {
                UUID playerLinkedUUID = ItemNBTUtil.getLinkedPlayerUUID(backpackStack);
                name = ItemNBTUtil.getLikedPlayerName(backpackStack);
                if (playerLinkedUUID != null) {
                    for (ServerLevel eachLvl : Objects.requireNonNull(minecraftServer.getAllLevels())) {
                        if (eachLvl.getEntity(playerLinkedUUID) instanceof Player playerLinked) {
                            enderChestContainer = playerLinked.getEnderChestInventory();
                            break;
                        }
                    }
                }
            }
            PlayerEnderChestContainer finalEnderChestContainer = enderChestContainer;

            player.openMenu(
                    new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) ->
                            ChestMenu.threeRows(p_53124_, p_53125_, finalEnderChestContainer),
                            Component.translatable(OccultismItems.ENDER_SATCHEL.get().getDescriptionId() + ".chest_menu", name)));
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
