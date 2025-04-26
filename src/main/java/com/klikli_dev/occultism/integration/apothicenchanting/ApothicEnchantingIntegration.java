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

    public static int getTotalExperiencePointsForLevel(int level) {
        int expReq = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            expReq += getExperienceForLevel(lvl);
        }
        return expReq;
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level > 30) return 112 + (level - 31) * 9;
        if (level > 15) return 37 + (level - 16) * 5;
        return 7 + (level - 1) * 2;
    }

}
