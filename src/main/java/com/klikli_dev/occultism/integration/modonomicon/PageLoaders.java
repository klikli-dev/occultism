package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookBindingCraftingRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookRitualRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritFireRecipePage;
import com.klikli_dev.occultism.integration.modonomicon.pages.BookSpiritTradeRecipePage;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class PageLoaders {

    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LoaderRegistry.registerPageLoader(OccultismModonomiconConstants.Page.SPIRIT_FIRE_RECIPE, BookSpiritFireRecipePage::fromJson, BookSpiritFireRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(OccultismModonomiconConstants.Page.SPIRIT_TRADE_RECIPE, BookSpiritTradeRecipePage::fromJson, BookSpiritTradeRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(OccultismModonomiconConstants.Page.RITUAL_RECIPE, BookRitualRecipePage::fromJson, BookRitualRecipePage::fromNetwork);
        LoaderRegistry.registerPageLoader(OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE, BookBindingCraftingRecipePage::fromJson, BookBindingCraftingRecipePage::fromNetwork);
    }


}
