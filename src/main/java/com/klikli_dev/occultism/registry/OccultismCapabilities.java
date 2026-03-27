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
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
//import top.theillusivec4.curios.api.CuriosCapability; // TODO: re-enable when Curios is available for 26.1

public class OccultismCapabilities {

    // Capability instances - using explicit BlockCapability and EntityCapability
    private static final BlockCapability<IItemHandler, Direction> ITEM_HANDLER_BLOCK = 
            BlockCapability.createSided(Identifier.fromNamespaceAndPath("neoforge", "item_handler"), IItemHandler.class);
    private static final EntityCapability<IItemHandler, Direction> ITEM_HANDLER_ENTITY = 
            EntityCapability.createSided(Identifier.fromNamespaceAndPath("neoforge", "item_handler"), IItemHandler.class);
    private static final EntityCapability<IItemHandler, Void> ITEM_HANDLER_ENTITY_VOID = 
            EntityCapability.createVoid(Identifier.fromNamespaceAndPath("neoforge", "item_handler"), IItemHandler.class);

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

        // TODO: re-enable when Curios is available for 26.1
        //event.registerItem(
        //        CuriosCapability.ITEM, // capability to register for
        //        (itemStack, context) -> {
        //            return new FamiliarRingItem.Curio(itemStack);
        //        },
        //        // items to register for
        //        OccultismItems.FAMILIAR_RING.get());

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.SACRIFICIAL_BOWL.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                });

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.GOLDEN_SACRIFICIAL_BOWL.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                });

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.ENTITY_WORMHOLE.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                });

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.DIMENSIONAL_MINESHAFT.get(),
                (blockEntity, side) -> {
                    if (side == Direction.DOWN)
                        return blockEntity.bufferedOutputHandler;
                    else if (side == Direction.UP)
                        return blockEntity.inputHandler;
                    else
                        return blockEntity.combinedHandler;
                }
        );

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.DIMENSIONAL_BATTLEFIELD.get(),
                (blockEntity, side) -> {
                    if (side == Direction.DOWN)
                        return blockEntity.bufferedOutputHandler;
                    else if (side == Direction.UP)
                        return blockEntity.inputHandler;
                    else if (side != null)
                        return blockEntity.combinedHandler;
                    else
                        return blockEntity.jadeWrapper;
                }
        );

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.STABLE_WORMHOLE.get(),
                (blockEntity, side) -> {
                    if (blockEntity.getLinkedStorageController() instanceof StorageControllerBlockEntity controller) {
                        return controller.itemStackHandler;
                    }
                    return null;
                });

        event.registerBlockEntity(
                ITEM_HANDLER_BLOCK,
                OccultismBlockEntities.STORAGE_CONTROLLER.get(),
                (blockEntity, side) -> {
                    return blockEntity.itemStackHandler;
                });

        event.registerEntity(
                ITEM_HANDLER_ENTITY_VOID,
                OccultismEntities.FOLIOT.get(),
                (entity, side) -> {
                    return entity.inventory;
                });

        event.registerEntity(
                ITEM_HANDLER_ENTITY_VOID,
                OccultismEntities.DJINNI.get(),
                (entity, side) -> {
                    return entity.inventory;
                });

        event.registerEntity(
                ITEM_HANDLER_ENTITY_VOID,
                OccultismEntities.AFRIT.get(),
                (entity, side) -> {
                    return entity.inventory;
                });

        event.registerEntity(
                ITEM_HANDLER_ENTITY_VOID,
                OccultismEntities.MARID.get(),
                (entity, side) -> {
                    return entity.inventory;
                });

    }

}
