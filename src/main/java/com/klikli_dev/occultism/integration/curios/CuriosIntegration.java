// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.curios;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;

/**
 * Abstraction over the Curios API.
 *
 * <p>All Curios access goes through this interface so the mod compiles and runs
 * when Curios is missing or has no build for the current Minecraft version.
 * The real implementation lives in {@code impl} and is excluded from the
 * source set until Curios ships a compatible release (see build.gradle).</p>
 */
public interface CuriosIntegration {

    static CuriosIntegration get() {
        return Holder.INSTANCE;
    }

    private static CuriosIntegration create() {
        if (!ModList.get().isLoaded("curios")) {
            return new CuriosIntegrationDummy();
        }

        try {
            return (CuriosIntegration) Class.forName("com.klikli_dev.occultism.integration.curios.impl.CuriosIntegrationImpl")
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException | LinkageError e) {
            Occultism.LOGGER.warn("Failed to initialize Curios integration, falling back to dummy implementation.", e);
            return new CuriosIntegrationDummy();
        }
    }

    boolean isLoaded();

    boolean hasGoggles(Player player);

    boolean hasStaff(Player player);

    ItemStack getBackpack(Player player);

    int getFirstBackpackSlot(Player player);

    SelectedCurio getStorageRemote(Player player);

    ItemStack getStorageRemoteCurio(Player player);

    int getFirstStorageRemoteSlot(Player player);

    ItemStack getEnderSatchel(Player player);

    int getFirstEnderSatchelSlot(Player player);

    ItemStack getXpTablet(Player player);

    List<ItemStack> getEquippedCurioStacks(LivingEntity entity);

    void registerItemCapabilities(RegisterCapabilitiesEvent event);

    class SelectedCurio {
        public ItemStack itemStack;
        public int selectedSlot;

        public SelectedCurio(ItemStack itemStack, int selectedSlot) {
            this.itemStack = itemStack;
            this.selectedSlot = selectedSlot;
        }
    }

    final class Holder {
        private static final CuriosIntegration INSTANCE = create();

        private Holder() {
        }
    }
}
