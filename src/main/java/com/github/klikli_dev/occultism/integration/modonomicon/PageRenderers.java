package com.github.klikli_dev.occultism.integration.modonomicon;

import com.klikli_dev.modonomicon.client.render.page.BookProcessingRecipePageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                OccultismModnomiconConstants.Page.SPIRIT_FIRE_RECIPE,
                p -> new BookProcessingRecipePageRenderer<>((BookSpiritFireRecipePage) p) {
                });
    }

}
