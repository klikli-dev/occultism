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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class OccultismPackets {
    //region Fields
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Occultism.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int ID = 0;
    //endregion Fields

    //region Static Methods
    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {

        INSTANCE.registerMessage(nextID(),
                MessageRequestStacks.class,
                MessageRequestStacks::encode,
                MessageRequestStacks::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
                MessageUpdateLinkedMachines.class,
                MessageUpdateLinkedMachines::encode,
                MessageUpdateLinkedMachines::new,
                OccultismPacketHandler::handle);

        INSTANCE.registerMessage(nextID(),
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
    }

    public static <MSG> void sendTo(ServerPlayerEntity player, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToTracking(Entity entity, MSG message) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }

    public static <MSG> void sendToDimension(RegistryKey<World> dimensionKey, MSG message) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dimensionKey), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    //endregion Static Methods
}
