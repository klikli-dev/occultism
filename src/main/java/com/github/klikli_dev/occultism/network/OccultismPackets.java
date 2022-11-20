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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class OccultismPackets {
    public static final String PROTOCOL_VERSION = "1";
    public static final ResourceLocation CHANNEL =  new ResourceLocation(Occultism.MODID, "main");
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            CHANNEL,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static final PacketSplitter SPLITTER = new PacketSplitter(5, INSTANCE, CHANNEL);

    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {

        INSTANCE.registerMessage(nextID(),
                MessageSplitPacket.class,
                MessageSplitPacket::encode,
                MessageSplitPacket::decode,
                MessageSplitPacket::handle);

        INSTANCE.registerMessage(nextID(),
                MessageRequestStacks.class,
                MessageRequestStacks::encode,
                MessageRequestStacks::new,
                OccultismPacketHandler::handle);

        SPLITTER.registerMessage(nextID(),
                MessageUpdateLinkedMachines.class,
                MessageUpdateLinkedMachines::encode,
                MessageUpdateLinkedMachines::new,
                OccultismPacketHandler::handle);

        SPLITTER.registerMessage(nextID(),
                MessageUpdateStacks.class,
                MessageUpdateStacks::encode,
                MessageUpdateStacks::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetRecipe.class,
                MessageSetRecipe::encode,
                MessageSetRecipe::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageClearCraftingMatrix.class,
                MessageClearCraftingMatrix::encode,
                MessageClearCraftingMatrix::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageTakeItem.class,
                MessageTakeItem::encode,
                MessageTakeItem::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageUpdateMouseHeldItem.class,
                MessageUpdateMouseHeldItem::encode,
                MessageUpdateMouseHeldItem::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageInsertMouseHeldItem.class,
                MessageInsertMouseHeldItem::encode,
                MessageInsertMouseHeldItem::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageRequestOrder.class,
                MessageRequestOrder::encode,
                MessageRequestOrder::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSortItems.class,
                MessageSortItems::encode,
                MessageSortItems::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetItemMode.class,
                MessageSetItemMode::encode,
                MessageSetItemMode::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetWorkAreaSize.class,
                MessageSetWorkAreaSize::encode,
                MessageSetWorkAreaSize::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetManagedMachine.class,
                MessageSetManagedMachine::encode,
                MessageSetManagedMachine::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetDivinationResult.class,
                MessageSetDivinationResult::encode,
                MessageSetDivinationResult::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSelectBlock.class,
                MessageSelectBlock::encode,
                MessageSelectBlock::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetJumps.class,
                MessageSetJumps::encode,
                MessageSetJumps::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageDoubleJump.class,
                MessageDoubleJump::encode,
                MessageDoubleJump::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetFilterMode.class,
                MessageSetFilterMode::encode,
                MessageSetFilterMode::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetRecipeByID.class,
                MessageSetRecipeByID::encode,
                MessageSetRecipeByID::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageOpenSatchel.class,
                MessageOpenSatchel::encode,
                MessageOpenSatchel::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageSetTagFilterText.class,
                MessageSetTagFilterText::encode,
                MessageSetTagFilterText::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageToggleFamiliarSettings.class,
                MessageToggleFamiliarSettings::encode,
                MessageToggleFamiliarSettings::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageOpenStorageRemote.class,
                MessageOpenStorageRemote::encode,
                MessageOpenStorageRemote::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageHeadlessDie.class,
                MessageHeadlessDie::encode,
                MessageHeadlessDie::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageBeholderAttack.class,
                MessageBeholderAttack::encode,
                MessageBeholderAttack::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageFairySupport.class,
                MessageFairySupport::encode,
                MessageFairySupport::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageUpdateStorageSettings.class,
                MessageUpdateStorageSettings::encode,
                MessageUpdateStorageSettings::new,
                OccultismPacketHandler::handle);
    }

    public static <MSG> void sendToTracking(Entity entity, MSG message) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }

    public static <MSG> void sendToDimension(ResourceKey<Level> dimensionKey, MSG message) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dimensionKey), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        if (SPLITTER.shouldMessageBeSplit(message.getClass())) {
            SPLITTER.sendToServer(message);
        } else {
            INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
        }
    }

    public static <MSG> void sendTo(ServerPlayer player, MSG message) {
        if (!(player instanceof FakePlayer)) {
            if (SPLITTER.shouldMessageBeSplit(message.getClass())) {
                SPLITTER.sendToPlayer(player, message);
            } else {
                INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
            }
        }
    }

    public static void addPackagePart(int communicationId, int packetIndex, byte[] payload) {
        SPLITTER.addPackagePart(communicationId, packetIndex, payload);
    }
}
