// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios;

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
        return -1;
    }

    @Override
    public SelectedCurio getStorageRemote(Player player) {
        return null;
    }

    @Override
    public ItemStack getStorageRemoteCurio(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getFirstStorageRemoteSlot(Player player) {
        return -1;
    }

    @Override
    public ItemStack getEnderSatchel(Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getFirstEnderSatchelSlot(Player player) {
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
