package com.klikli_dev.occultism.integration.jade;

import net.neoforged.fml.ModList;
import snownee.jade.Jade;

public class WailaIntegration {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("jade");
    }

    public static boolean displayPentacles(){
        if (isLoaded()) {
            return WailaHelper.enabled();
        }
        return false;
    }

    public static class WailaHelper {
        protected static boolean enabled(){
            return Jade.CONFIG.get().getGeneral().shouldDisplayTooltip()
                    && Jade.CONFIG.get().getPlugin().get(SacrificialComponentProvider.INSTANCE);
        }
    }
}
