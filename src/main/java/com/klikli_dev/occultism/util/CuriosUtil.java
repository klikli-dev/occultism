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

import com.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.klikli_dev.occultism.common.item.storage.EnderSatchelItem;
import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
//import top.theillusivec4.curios.api.CuriosCapability;
//import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
//import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
//import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosUtil {
    public static boolean hasGoggles(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        return OtherworldGogglesItem.isGogglesItem(helmet);

        // TODO: re-enable when Curios is available for 26.1
    }

    public static boolean hasStaff(Player player) {
        return player.getOffhandItem().is(OccultismItems.TRUE_SIGHT_STAFF);

        // TODO: re-enable when Curios is available for 26.1
    }

    public static ItemStack getBackpack(Player player) {
        // TODO: re-enable when Curios is available for 26.1
        return ItemStack.EMPTY;
    }

    protected static ItemStack getSatchelItemFromSlot(Object curiosHandler, String identifier) {
        // TODO: re-enable when Curios is available for 26.1
        return ItemStack.EMPTY;
    }


    public static SelectedCurio getStorageRemote(Player player) {
        int selectedSlot = player.getInventory().getSelectedSlot();
        ItemStack storageRemoteStack = player.getInventory().getSelectedItem();
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
        // TODO: re-enable when Curios is available for 26.1
        return ItemStack.EMPTY;
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

    public static ItemStack getEnderSatchel(Player player) {
        // TODO: re-enable when Curios is available for 26.1
        return ItemStack.EMPTY;
    }

    protected static ItemStack getEnderSatchelItemFromSlot(Object curiosHandler, String identifier) {
        // TODO: re-enable when Curios is available for 26.1
        return ItemStack.EMPTY;
    }

    public static int getFirstEnderSatchelSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof EnderSatchelItem)
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
