package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import com.klikli_dev.occultism.integration.modonomicon.pages.*;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                Page.SPIRIT_FIRE_RECIPE,
                p -> new BookSpiritFireRecipePageRenderer((BookSpiritFireRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                Page.SPIRIT_TRADE_RECIPE,
                p -> new BookSpiritTradeRecipePageRenderer((BookSpiritTradeRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                Page.RITUAL_RECIPE,
                p -> new BookRitualRecipePageRenderer((BookRitualRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                Page.BOOK_BINDING_RECIPE,
                p -> new BookBindingCraftingRecipePageRenderer((BookBindingCraftingRecipePage) p));
    }

}
