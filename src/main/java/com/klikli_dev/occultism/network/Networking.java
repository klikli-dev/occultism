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
import com.klikli_dev.occultism.network.messages.Messages.*;
import com.klikli_dev.theurgy.network.MessageHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class Networking {

    public static void register(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(Occultism.MODID);

        registrar.play(MessageBeholderAttack.ID, MessageBeholderAttack::new, MessageHandler::handle);
        registrar.play(MessageClearCraftingMatrix.ID, MessageClearCraftingMatrix::new, MessageHandler::handle);
        registrar.play(MessageDoubleJump.ID, MessageDoubleJump::new, MessageHandler::handle);
        registrar.play(MessageFairySupport.ID, MessageFairySupport::new, MessageHandler::handle);
        registrar.play(MessageHeadlessDie.ID, MessageHeadlessDie::new, MessageHandler::handle);
        registrar.play(MessageInsertMouseHeldItem.ID, MessageInsertMouseHeldItem::new, MessageHandler::handle);
        registrar.play(MessageOpenSatchel.ID, MessageOpenSatchel::new, MessageHandler::handle);
        registrar.play(MessageOpenStorageRemote.ID, MessageOpenStorageRemote::new, MessageHandler::handle);
        registrar.play(MessageRequestOrder.ID, MessageRequestOrder::new, MessageHandler::handle);
        registrar.play(MessageRequestStacks.ID, MessageRequestStacks::new, MessageHandler::handle);
        registrar.play(MessageSelectBlock.ID, MessageSelectBlock::new, MessageHandler::handle);
        registrar.play(MessageSetDivinationResult.ID, MessageSetDivinationResult::new, MessageHandler::handle);
        registrar.play(MessageSetFilterMode.ID, MessageSetFilterMode::new, MessageHandler::handle);
        registrar.play(MessageSetItemMode.ID, MessageSetItemMode::new, MessageHandler::handle);
        registrar.play(MessageSetJumps.ID, MessageSetJumps::new, MessageHandler::handle);
        registrar.play(MessageSetManagedMachine.ID, MessageSetManagedMachine::new, MessageHandler::handle);
        registrar.play(MessageSetRecipe.ID, MessageSetRecipe::new, MessageHandler::handle);
        registrar.play(MessageSetRecipeByID.ID, MessageSetRecipeByID::new, MessageHandler::handle);
        registrar.play(MessageSetTagFilterText.ID, MessageSetTagFilterText::new, MessageHandler::handle);
        registrar.play(MessageSetWorkAreaSize.ID, MessageSetWorkAreaSize::new, MessageHandler::handle);
        registrar.play(MessageSortItems.ID, MessageSortItems::new, MessageHandler::handle);
        registrar.play(MessageSyncFamiliarSettings.ID, MessageSyncFamiliarSettings::new, MessageHandler::handle);
        registrar.play(MessageTakeItem.ID, MessageTakeItem::new, MessageHandler::handle);
        registrar.play(MessageToggleFamiliarSettings.ID, MessageToggleFamiliarSettings::new, MessageHandler::handle);
        registrar.play(MessageUpdateLinkedMachines.ID, MessageUpdateLinkedMachines::new, MessageHandler::handle);
        registrar.play(MessageUpdateMouseHeldItem.ID, MessageUpdateMouseHeldItem::new, MessageHandler::handle);
        registrar.play(MessageUpdateStacks.ID, MessageUpdateStacks::new, MessageHandler::handle);
        registrar.play(MessageUpdateStorageSettings.ID, MessageUpdateStorageSettings::new, MessageHandler::handle);
    }

    public static <T extends IMessage> void sendTo(ServerPlayer player, T message) {
        PacketDistributor.PLAYER.with(player).send(message);
    }

    public static <T extends IMessage> void sendToServer(T message) {
        PacketDistributor.SERVER.noArg().send(message);
    }

    public static <T extends IMessage> void sendToTracking(Entity entity, T message) {
        PacketDistributor.TRACKING_ENTITY.with(entity).send(message);
    }

    public static <T extends IMessage> void sendToTracking(LevelChunk chunk, T message) {
        PacketDistributor.TRACKING_CHUNK.with(chunk).send(message);
    }

    public static <T extends IMessage> void sendToDimension(ResourceKey<Level> dimensionKey, T message) {
        PacketDistributor.DIMENSION.with(dimensionKey).send(message);
    }
}
