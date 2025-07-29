package com.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.occultism.integration.modonomicon.pages.*;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                OccultismModonomiconConstants.Page.SPIRIT_FIRE_RECIPE,
                p -> new BookSpiritFireRecipePageRenderer<>((BookSpiritFireRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                OccultismModonomiconConstants.Page.SPIRIT_TRADE_RECIPE,
                p -> new BookSpiritTradeRecipePageRenderer<>((BookSpiritTradeRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                OccultismModonomiconConstants.Page.RITUAL_RECIPE,
                p -> new BookRitualRecipePageRenderer((BookRitualRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                OccultismModonomiconConstants.Page.BOOK_BINDING_RECIPE,
                p -> new BookBindingCraftingRecipePageRenderer((BookBindingCraftingRecipePage) p) {
                });
    }

}
