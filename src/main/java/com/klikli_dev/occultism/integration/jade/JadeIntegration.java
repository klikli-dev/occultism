package com.klikli_dev.occultism.integration.jade;

import net.neoforged.fml.ModList;

public class JadeIntegration {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("jade");
    }

    public static boolean displayPentacles(){
        return isLoaded();
    }
}
