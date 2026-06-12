/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.mixin;

import com.klikli_dev.occultism.common.item.tool.ChalkItem;
import net.minecraft.core.NonNullList;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {

    @Redirect(
            method = "tryPickItem(Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;findSlotMatchingItem(Lnet/minecraft/world/item/ItemStack;)I"
            )
    )
    private int occultism$findChalkSlotIgnoringComponents(Inventory inventory, ItemStack itemStack) {
        int slot = inventory.findSlotMatchingItem(itemStack);
        if (slot != -1 || !(itemStack.getItem() instanceof ChalkItem)) {
            return slot;
        }
        NonNullList<ItemStack> items = inventory.getNonEquipmentItems();
        for (int i = 0; i < items.size(); i++) {
            ItemStack existing = items.get(i);
            if (!existing.isEmpty() && ItemStack.isSameItem(itemStack, existing)) {
                return i;
            }
        }
        return -1;
    }
}
