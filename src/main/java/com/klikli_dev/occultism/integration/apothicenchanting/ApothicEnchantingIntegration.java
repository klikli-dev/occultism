package com.klikli_dev.occultism.integration.apothicenchanting;

import dev.shadowsoffire.apothic_enchanting.ApothicEnchanting;
import dev.shadowsoffire.apothic_enchanting.util.MiscUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.fml.ModList;

public class ApothicEnchantingIntegration {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("apothic_enchanting");
    }

    /**
     * Copied from Apothic Enchanting Hooks
     * Using a different function to call in iesnium anvil
     */
    public static int getApothicMaxLevel(Enchantment enchantment){
        Holder<Enchantment> holder = MiscUtil.findHolder(Registries.ENCHANTMENT, enchantment);
        if (holder != null) {
            return ApothicEnchanting.getEnchInfo(holder).getMaxLevel();
        }
        return enchantment.getMaxLevel();
    }

}
