package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.occultism.integration.modonomicon.pages.*;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                BookSpiritFireRecipePage.ID,
                p -> new BookSpiritFireRecipePageRenderer((BookSpiritFireRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                BookSpiritTradeRecipePage.ID,
                p -> new BookSpiritTradeRecipePageRenderer((BookSpiritTradeRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                BookRitualRecipePage.ID,
                p -> new BookRitualRecipePageRenderer((BookRitualRecipePage) p));
        PageRendererRegistry.registerPageRenderer(
                BookBindingCraftingRecipePage.ID,
                p -> new BookBindingCraftingRecipePageRenderer((BookBindingCraftingRecipePage) p));
    }

}
