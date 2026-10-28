// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios;

import com.klikli_dev.occultism.common.item.storage.EnderSatchelItem;
import com.klikli_dev.occultism.common.item.storage.SatchelItem;
import com.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;

public class CuriosIntegrationDummy implements CuriosIntegration {
    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public boolean hasGoggles(Player player) {
        return false;
    }

    @Override
    public boolean hasStaff(Player player) {
        return false;
    }

    @Override
    public ItemStack getBackpack(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getFirstBackpackSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot).getItem() instanceof SatchelItem)
                return slot;
        }
        return -1;
    }

    @Override
    public SelectedCurio getStorageRemote(Player player) {
        int selectedSlot = player.getInventory().getSelectedSlot();
        ItemStack storageRemoteStack = player.getInventory().getSelectedItem();
        //if that is not a storage remote, look it up in the player inventory
        if (!(storageRemoteStack.getItem() instanceof StorageRemoteItem)) {
            selectedSlot = this.getFirstStorageRemoteSlot(player);
            storageRemoteStack = selectedSlot >= 0 ? player.getInventory().getItem(selectedSlot) : ItemStack.EMPTY;
        }
        //now, if we have a storage remote, proceed
        if (storageRemoteStack.getItem() instanceof StorageRemoteItem) {
            return new SelectedCurio(storageRemoteStack, selectedSlot);
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStorageRemoteCurio(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getFirstStorageRemoteSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot).getItem() instanceof StorageRemoteItem)
                return slot;
        }
        return -1;
    }

    @Override
    public ItemStack getEnderSatchel(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getFirstEnderSatchelSlot(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot).getItem() instanceof EnderSatchelItem)
                return slot;
        }
        return -1;
    }

    @Override
    public ItemStack getXpTablet(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getEquippedCurioStacks(LivingEntity entity) {
        return List.of();
    }

    @Override
    public void registerItemCapabilities(RegisterCapabilitiesEvent event) {
        // No Curios available, nothing to register.
    }
}
