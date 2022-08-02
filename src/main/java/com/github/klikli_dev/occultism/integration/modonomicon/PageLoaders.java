package com.github.klikli_dev.occultism.integration.modonomicon;

import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModnomiconConstants.Page;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class PageLoaders {

    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LoaderRegistry.registerPageLoader(Page.SPIRIT_FIRE_RECIPE, BookSpiritFireRecipePage::fromJson, BookSpiritFireRecipePage::fromNetwork);
    }


}
