package com.klikli_dev.occultism.integration.modonomicon.pages;

import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Modonomicon renderer compatibility stub for Minecraft 26.1.
 *
 * The Modonomicon rendering API changed in 26.1. To keep the project compiling
 * we replace the detailed renderer implementation with a small placeholder.
 * TODO: Re-implement full recipe rendering using the new Modonomicon recipe display API.
 */
public class BookBindingCraftingRecipePageRenderer {
    public BookBindingCraftingRecipePageRenderer() {
    }

    public void drawPlaceholder(GuiGraphicsExtractor guiGraphics, int recipeX, int recipeY) {
        // Simple placeholder - actual rendering is disabled until a proper port is implemented.
        guiGraphics.text(null, "[Modonomicon recipe rendering disabled for 26.1]", recipeX, recipeY, 0x000000, false);
    }
}
