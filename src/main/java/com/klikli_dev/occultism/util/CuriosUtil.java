/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CuriosUtil {
    public static boolean hasGoggles(Player player) {
        return false;

        //TODO: enable once curios is available
//        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
//        if (OtherworldGogglesItem.isGogglesItem(helmet))
//            return true;
//
//        var curiosHandler = player.getCapability(CuriosCapability.INVENTORY);
//        if (curiosHandler == null)
//            return false;
//
//        var slotsHandler = curiosHandler.getCurios().values();
//        return slotsHandler.stream().map(ICurioStacksHandler::getStacks).anyMatch(
//                stackHandler -> {
//                    for (int i = 0; i < stackHandler.getSlots(); i++) {
//                        ItemStack stack = stackHandler.getStackInSlot(i);
//                        if (OtherworldGogglesItem.isGogglesItem(stack)) {
//                            return true;
//
//                        }
//                    }
//                    return false;
//                }
//        );
    }

    public static ItemStack getBackpack(Player player) {
        return ItemStack.EMPTY;

        //TODO: enable once curios is available
//        var curiosHandler = player.getCapability(CuriosCapability.INVENTORY);
//        if (curiosHandler == null)
//            return ItemStack.EMPTY;
//
//        var belt = curiosHandler.getStacksHandler("belt");
//        if (!belt.isPresent())
//            return ItemStack.EMPTY;
//
//        IDynamicStackHandler stackHandler = belt.get().getStacks();
//        ItemStack hasBackpack = ItemStack.EMPTY;
//        for (int i = 0; i < stackHandler.getSlots(); i++) {
//            ItemStack stack = stackHandler.getStackInSlot(i);
//            if (stack.getItem() instanceof SatchelItem) {
//                hasBackpack = stack;
//                break;
//            }
//        }
//        return hasBackpack;
    }

    public static SelectedCurio getStorageRemote(Player player) {
        int selectedSlot = player.getInventory().selected;
        ItemStack storageRemoteStack = player.getInventory().getSelected();
        //if that is not a storage remote, get from curio
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = -1;
            storageRemoteStack = CuriosUtil.getStorageRemoteCurio(player);
        }

        //if not found, try to get from player inventory
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = CuriosUtil.getFirstStorageRemoteSlot(player);
            storageRemoteStack = selectedSlot > 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a storage remote, proceed
        if (storageRemoteStack.getItem() instanceof StorageRemoteItem) {
            return new SelectedCurio(storageRemoteStack, selectedSlot);

        } else {
            return null;
        }
    }

    public static ItemStack getStorageRemoteCurio(Player player) {

        return ItemStack.EMPTY;
        //TODO: enable once curios is available
//        ICuriosItemHandler curiosHandler = player.getCapability(CuriosCapability.INVENTORY);
//        ItemStack hasStorageRemote = ItemStack.EMPTY;
//        for (ICurioStacksHandler curiosStackshandler : curiosHandler.getCurios().values()) {
//            IDynamicStackHandler stackHandler = curiosStackshandler.getStacks();
//            for (int i = 0; i < stackHandler.getSlots(); i++) {
//                ItemStack stack = stackHandler.getStackInSlot(i);
//                if (stack.getItem() instanceof StorageRemoteItem) {
//                    hasStorageRemote = stack;
//                    break;
//                }
//            }
//            if (!hasStorageRemote.isEmpty()) {
//                break;
//            }
//        }
//        return hasStorageRemote;
    }

    public static int getFirstBackpackSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof SatchelItem)
                return slot;
        }
        return -1;
    }

    public static int getFirstStorageRemoteSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof StorageRemoteItem)
                return slot;
        }
        return -1;
    }

    public static class SelectedCurio {
        public ItemStack itemStack;
        public int selectedSlot;

        public SelectedCurio(ItemStack itemStack, int selectedSlot) {
            this.itemStack = itemStack;
            this.selectedSlot = selectedSlot;
        }
    }
}
