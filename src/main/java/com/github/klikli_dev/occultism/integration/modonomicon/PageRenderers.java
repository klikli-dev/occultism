package com.github.klikli_dev.occultism.integration.modonomicon;

import com.github.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants.Page;
import com.github.klikli_dev.occultism.integration.modonomicon.pages.*;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                Page.SPIRIT_FIRE_RECIPE,
                p -> new BookSpiritFireRecipePageRenderer<>((BookSpiritFireRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                Page.SPIRIT_TRADE_RECIPE,
                p -> new BookSpiritTradeRecipePageRenderer<>((BookSpiritTradeRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                Page.RITUAL_RECIPE,
                p -> new BookRitualRecipePageRenderer((BookRitualRecipePage) p) {
                });
    }

}
