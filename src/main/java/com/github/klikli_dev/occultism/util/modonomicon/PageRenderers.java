package com.github.klikli_dev.occultism.util.modonomicon;

import com.github.klikli_dev.occultism.util.modonomicon.OccultismModonomiconConstants.Page;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookRitualRecipePage;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookRitualRecipePageRenderer;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookSpiritFireRecipePage;
import com.github.klikli_dev.occultism.util.modonomicon.pages.BookSpiritFireRecipePageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PageRenderers {

    public static void onClientSetup(FMLClientSetupEvent event) {
        PageRendererRegistry.registerPageRenderer(
                OccultismModonomiconConstants.Page.SPIRIT_FIRE_RECIPE,
                p -> new BookSpiritFireRecipePageRenderer<>((BookSpiritFireRecipePage) p) {
                });
        PageRendererRegistry.registerPageRenderer(
                Page.RITUAL_RECIPE,
                p -> new BookRitualRecipePageRenderer((BookRitualRecipePage) p) {
                });
    }

}
