// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios;

import com.klikli_dev.occultism.common.item.armor.OtherworldGogglesItem;
import com.klikli_dev.occultism.integration.curios.CuriosIntegration.SelectedCurio;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Static facade over {@link CuriosIntegration}.
 *
 * <p>Kept so existing call sites only need an import change. Vanilla equipment
 * slots are checked here, directly, so they keep working when Curios is
 * missing or has no build for this version. All Curios access is routed
 * through the integration, which falls back to a dummy implementation in that
 * case.</p>
 */
public class CuriosUtil {
    /**
     * Checks vanilla equipment slots (head slot) for goggles.
     * Works independently of the curios integration.
     */
    public static boolean hasVanillaGoggles(Player player) {
        return OtherworldGogglesItem.isGogglesItem(player.getItemBySlot(EquipmentSlot.HEAD));
    }

    /**
     * Checks vanilla equipment slots (offhand) for the true sight staff.
     * Works independently of the curios integration.
     */
    public static boolean hasVanillaStaff(Player player) {
        return player.getOffhandItem().is(OccultismItems.TRUE_SIGHT_STAFF);
    }

    public static boolean hasGoggles(Player player) {
        return hasVanillaGoggles(player) || CuriosIntegration.get().hasGoggles(player);
    }

    public static boolean hasStaff(Player player) {
        return hasVanillaStaff(player) || CuriosIntegration.get().hasStaff(player);
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
