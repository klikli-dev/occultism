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

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;

public class OccultismCapabilities {

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

        event.registerItem(
                CuriosCapability.ITEM, // capability to register for
                (itemStack, context) -> {
                    return new FamiliarRingItem.Curio(itemStack);
                },
                // items to register for
                OccultismItems.FAMILIAR_RING.get());

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.SACRIFICIAL_BOWL.get(),
                (blockEntity, side) -> (ResourceHandler<ItemResource>) blockEntity.itemStackHandler);

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.GOLDEN_SACRIFICIAL_BOWL.get(),
                (blockEntity, side) -> (ResourceHandler<ItemResource>) blockEntity.itemStackHandler);

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.ENTITY_WORMHOLE.get(),
                (blockEntity, side) -> (ResourceHandler<ItemResource>) blockEntity.itemStackHandler);

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get(),
                (blockEntity, side) -> {
                    if (side == Direction.DOWN) {
                        return (ResourceHandler<ItemResource>) blockEntity.bufferedOutputHandler;
                    } else if (side == Direction.UP) {
                        return (ResourceHandler<ItemResource>) blockEntity.inputHandler;
                    } else {
                        return (ResourceHandler<ItemResource>) blockEntity.combinedHandler;
                    }
                });

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.DIMENSIONAL_BATTLEFIELD.get(),
                (blockEntity, side) -> {
                    if (side == Direction.DOWN) {
                        return (ResourceHandler<ItemResource>) blockEntity.bufferedOutputHandler;
                    } else if (side == Direction.UP) {
                        return (ResourceHandler<ItemResource>) blockEntity.inputHandler;
                    } else if (side != null) {
                        return (ResourceHandler<ItemResource>) blockEntity.combinedHandler;
                    } else {
                        return (ResourceHandler<ItemResource>) blockEntity.jadeWrapper;
                    }
                });

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.STABLE_WORMHOLE.get(),
                (blockEntity, side) -> {
                    if (blockEntity.getLinkedStorageController() instanceof StorageControllerBlockEntity controller) {
                        return (ResourceHandler<ItemResource>) controller.itemStackHandler;
                    }
                    return null;
                });

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                OccultismBlockEntities.STORAGE_CONTROLLER.get(),
                (blockEntity, side) -> (ResourceHandler<ItemResource>) blockEntity.itemStackHandler);

        event.registerEntity(
                Capabilities.Item.ENTITY,
                OccultismEntities.FOLIOT.get(),
                (entity, side) -> (ResourceHandler<ItemResource>) entity.inventory);

        event.registerEntity(
                Capabilities.Item.ENTITY,
                OccultismEntities.DJINNI.get(),
                (entity, side) -> (ResourceHandler<ItemResource>) entity.inventory);

        event.registerEntity(
                Capabilities.Item.ENTITY,
                OccultismEntities.AFRIT.get(),
                (entity, side) -> (ResourceHandler<ItemResource>) entity.inventory);

        event.registerEntity(
                Capabilities.Item.ENTITY,
                OccultismEntities.MARID.get(),
                (entity, side) -> (ResourceHandler<ItemResource>) entity.inventory);

    }

}
