package com.github.klikli_dev.occultism.util.modonomicon;

import com.github.klikli_dev.occultism.util.modonomicon.OccultismModonomiconConstants.Page;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookRitualRecipePage;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookSpiritFireRecipePage;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class PageLoaders {

    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LoaderRegistry.registerPageLoader(Page.SPIRIT_FIRE_RECIPE, BookSpiritFireRecipePage::fromJson, BookSpiritFireRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(Page.RITUAL_RECIPE, BookRitualRecipePage::fromJson, BookRitualRecipePage::fromNetwork);
    }


}
