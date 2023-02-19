package com.github.klikli_dev.occultism.integration.modonomicon;

import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePage;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePage;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePage;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class PageLoaders {

    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LoaderRegistry.registerPageLoader(Page.SPIRIT_FIRE_RECIPE, BookSpiritFireRecipePage::fromJson, BookSpiritFireRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(Page.SPIRIT_TRADE_RECIPE, BookSpiritTradeRecipePage::fromJson, BookSpiritTradeRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(Page.RITUAL_RECIPE, BookRitualRecipePage::fromJson, BookRitualRecipePage::fromNetwork);
    }


}
