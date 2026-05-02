package com.klikli_dev.occultism.integration.jade;

import net.neoforged.fml.ModList;
import snownee.jade.Jade;

public class JadeIntegration {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("jade");
    }

    public static boolean displayPentacles() {
        if (isLoaded()) {
            return !JadeHelper.enabled();
        }

        return true;
    }

    public static class JadeHelper {
        protected static boolean enabled(){
            return Jade.config().general().shouldDisplayTooltip()
                    && Jade.config().plugin().get(SacrificialComponentProvider.INSTANCE);
        }
    }
}
