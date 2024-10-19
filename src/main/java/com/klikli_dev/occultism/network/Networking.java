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
import com.klikli_dev.occultism.network.messages.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class Networking {

    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar(Occultism.MODID);

        //to server
        registrar.playToServer(MessageClearCraftingMatrix.TYPE, MessageClearCraftingMatrix.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageDoubleJump.TYPE, MessageDoubleJump.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageInsertMouseHeldItem.TYPE, MessageInsertMouseHeldItem.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageOpenSatchel.TYPE, MessageOpenSatchel.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageOpenStorageRemote.TYPE, MessageOpenStorageRemote.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageRequestOrder.TYPE, MessageRequestOrder.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageRequestStacks.TYPE, MessageRequestStacks.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetDivinationResult.TYPE, MessageSetDivinationResult.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetFilterMode.TYPE, MessageSetFilterMode.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetItemMode.TYPE, MessageSetItemMode.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetManagedMachine.TYPE, MessageSetManagedMachine.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetRecipe.TYPE, MessageSetRecipe.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetRecipeByTemplate.TYPE, MessageSetRecipeByTemplate.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetRecipeByID.TYPE, MessageSetRecipeByID.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetTagFilterText.TYPE, MessageSetTagFilterText.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSetWorkAreaSize.TYPE, MessageSetWorkAreaSize.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSortItems.TYPE, MessageSortItems.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageTakeItem.TYPE, MessageTakeItem.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageToggleFamiliarSettings.TYPE, MessageToggleFamiliarSettings.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageUpdateStorageSettings.TYPE, MessageUpdateStorageSettings.STREAM_CODEC, MessageHandler::handle);
        registrar.playToServer(MessageSendPreviewedPentacle.TYPE, MessageSendPreviewedPentacle.STREAM_CODEC, MessageHandler::handle);

        //to client
        registrar.playToClient(MessageBeholderAttack.TYPE, MessageBeholderAttack.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageFairySupport.TYPE, MessageFairySupport.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageHeadlessDie.TYPE, MessageHeadlessDie.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageSelectBlock.TYPE, MessageSelectBlock.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageSetJumps.TYPE, MessageSetJumps.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageSyncFamiliarSettings.TYPE, MessageSyncFamiliarSettings.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageUpdateLinkedMachines.TYPE, MessageUpdateLinkedMachines.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageUpdateMouseHeldItem.TYPE, MessageUpdateMouseHeldItem.STREAM_CODEC, MessageHandler::handle);
        registrar.playToClient(MessageUpdateStacks.TYPE, MessageUpdateStacks.STREAM_CODEC, MessageHandler::handle);
    }

    public static <T extends IMessage> void sendTo(ServerPlayer player, T message) {
        PacketDistributor.sendToPlayer(player, message);
    }

    public static <T extends IMessage> void sendToTracking(ServerLevel level, ChunkPos chunkPos, T message) {
        PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, message);
    }

    public static <T extends IMessage> void sendToTracking(Entity entity, T message) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, message);
    }

    public static <T extends IMessage> void sendToServer(T message) {
        PacketDistributor.sendToServer(message);
    }
}
