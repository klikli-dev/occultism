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
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class OccultismCapabilities {

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

        //TODO: enable once curios is available
//        event.registerItem(
//                CuriosCapability.ITEM, // capability to register for
//                (itemStack, context) -> {
//                    return new FamiliarRingItem.Curio(itemStack);
//                },
//            // items to register for
//            OccultismItems.FAMILIAR_RING.get()
//        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                OccultismBlockEntities.SACRIFICIAL_BOWL.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                }
        );
        //note the golden sacrificial bowl intentionally does not get a capability!

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get(),
                (blockEntity, side) -> {
                    if (side == null)
                        return blockEntity.combinedHandler;
                    else if (side == Direction.UP)
                        return blockEntity.inputHandler;
                    else
                        return blockEntity.outputHandler;
                }

        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                OccultismBlockEntities.STABLE_WORMHOLE.get(),
                (blockEntity, side) -> {
                    if (blockEntity.getLinkedStorageController() instanceof StorageControllerBlockEntity controller) {
                        return controller.itemStackHandler;
                    }
                    return null;
                }
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                OccultismBlockEntities.STORAGE_CONTROLLER.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                }
        );

        event.registerEntity(
                Capabilities.ItemHandler.ENTITY,
                OccultismEntities.FOLIOT.get(),
                (entity, side) -> {
                    return entity.inventory;
                }
        );

        event.registerEntity(
                Capabilities.ItemHandler.ENTITY,
                OccultismEntities.DJINNI.get(),
                (entity, side) -> {
                    return entity.inventory;
                }
        );

        event.registerEntity(
                Capabilities.ItemHandler.ENTITY,
                OccultismEntities.AFRIT.get(),
                (entity, side) -> {
                    return entity.inventory;
                }
        );

        event.registerEntity(
                Capabilities.ItemHandler.ENTITY,
                OccultismEntities.MARID.get(),
                (entity, side) -> {
                    return entity.inventory;
                }
        );

    }

}
