// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios;

import com.klikli_dev.occultism.integration.curios.CuriosIntegration.SelectedCurio;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Static facade over {@link CuriosIntegration}.
 *
 * <p>Kept so existing call sites only need an import change. All Curios access
 * is routed through the integration, which falls back to a dummy
 * implementation when Curios is missing or has no build for this version.</p>
 */
public class CuriosUtil {
    public static boolean hasGoggles(Player player) {
        return CuriosIntegration.get().hasGoggles(player);
    }

    public static boolean hasStaff(Player player) {
        return CuriosIntegration.get().hasStaff(player);
    }

    public static ItemStack getBackpack(Player player) {
        return CuriosIntegration.get().getBackpack(player);
    }

    public static int getFirstBackpackSlot(Player player) {
        return CuriosIntegration.get().getFirstBackpackSlot(player);
    }

    public static SelectedCurio getStorageRemote(Player player) {
        return CuriosIntegration.get().getStorageRemote(player);
    }

    public static ItemStack getStorageRemoteCurio(Player player) {
        return CuriosIntegration.get().getStorageRemoteCurio(player);
    }

    public static int getFirstStorageRemoteSlot(Player player) {
        return CuriosIntegration.get().getFirstStorageRemoteSlot(player);
    }

    public static ItemStack getEnderSatchel(Player player) {
        return CuriosIntegration.get().getEnderSatchel(player);
    }

    public static int getFirstEnderSatchelSlot(Player player) {
        return CuriosIntegration.get().getFirstEnderSatchelSlot(player);
    }

    public static ItemStack getXpTablet(Player player) {
        return CuriosIntegration.get().getXpTablet(player);
    }
}
