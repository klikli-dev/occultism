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

package com.github.klikli_dev.occultism.util;

import com.github.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.github.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class CuriosUtil {
    public static boolean hasGoggles(PlayerEntity player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
        if (helmet.getItem() instanceof OtherworldGogglesItem)
            return true;

        Optional<Boolean> hasGoggles = CuriosApi.getCuriosHelper().getCuriosHandler(player)
                .map(curiosHandler -> curiosHandler.getCurios().values())
                .map(slotsHandler -> slotsHandler.stream().map(ICurioStacksHandler::getStacks).map(
                        stackHandler -> {
                            for (int i = 0; i < stackHandler.getSlots(); i++) {
                                ItemStack stack = stackHandler.getStackInSlot(i);
                                if (stack.getItem() instanceof OtherworldGogglesItem) {
                                    return true;

                                }
                            }
                            return false;
                        }
                )).map(results -> results.anyMatch(found -> found));
        return hasGoggles.orElse(false);
    }

    public static ItemStack getBackpack(PlayerEntity player) {
        Optional<ItemStack> hasBackpack = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(curiosHandler -> {
            Optional<ItemStack> hasBackpackStack = curiosHandler.getStacksHandler(SlotTypePreset.BELT.getIdentifier()).map(slotHandler -> {
                IDynamicStackHandler stackHandler = slotHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (stack.getItem() instanceof SatchelItem) {
                        return stack;
                    }
                }
                return ItemStack.EMPTY;
            });
            return hasBackpackStack.orElse(ItemStack.EMPTY);
        });
        return hasBackpack.orElse(ItemStack.EMPTY);
    }

    public static SelectedCurio getStorageRemote(PlayerEntity player) {
        int selectedSlot = player.inventory.selected;
        ItemStack storageRemoteStack = player.inventory.getSelected();
        //if that is not a storage remote, get from curio
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = -1;
            storageRemoteStack = CuriosUtil.getStorageRemoteCurio(player);
        }

        //if not found, try to get from player inventory
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = CuriosUtil.getFirstStorageRemoteSlot(player);
            storageRemoteStack = selectedSlot > 0 ? player.inventory.getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a storage remote, proceed
        if (storageRemoteStack.getItem() instanceof StorageRemoteItem) {
            return new SelectedCurio(storageRemoteStack, selectedSlot);

        } else {
            return null;
        }
    }

    public static ItemStack getStorageRemoteCurio(PlayerEntity player) {
        Optional<ItemStack> hasStorageRemote = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(curiosHandler -> {
            Optional<ItemStack> hasStorageRemoteStack = curiosHandler.getStacksHandler(SlotTypePreset.HANDS.getIdentifier()).map(slotHandler -> {
                IDynamicStackHandler stackHandler = slotHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (stack.getItem() instanceof StorageRemoteItem) {
                        return stack;
                    }
                }
                return ItemStack.EMPTY;
            });
            return hasStorageRemoteStack.orElse(ItemStack.EMPTY);
        });
        return hasStorageRemote.orElse(ItemStack.EMPTY);
    }

    public static int getFirstBackpackSlot(PlayerEntity player) {
        for (int slot = 0; slot < player.inventory.getContainerSize(); slot++) {
            ItemStack stack = player.inventory.getItem(slot);
            if (stack.getItem() instanceof SatchelItem)
                return slot;
        }
        return -1;
    }

    public static int getFirstStorageRemoteSlot(PlayerEntity player) {
        for (int slot = 0; slot < player.inventory.getContainerSize(); slot++) {
            ItemStack stack = player.inventory.getItem(slot);
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
