package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;

/**
 * Modonomicon renderer compatibility stub for Minecraft 26.1.
 *
 * The Modonomicon rendering API changed in 26.1. To keep the project compiling
 * we replace the detailed renderer implementation with a small placeholder.
 * TODO: Re-implement full recipe rendering using the new Modonomicon recipe display API.
 */
public class BookBindingCraftingRecipePageRenderer extends BookRecipePageRenderer<Recipe<?>, BookBindingCraftingRecipePage> {
    public BookBindingCraftingRecipePageRenderer(BookBindingCraftingRecipePage page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 78;
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        if (!second) {
            if (!this.page.getTitle1().isEmpty()) {
                this.renderTitle(guiGraphics, this.page.getTitle1(), false, BookEntryScreen.PAGE_WIDTH / 2, -5);
            }
        } else if (!this.page.getTitle2().isEmpty()) {
            this.renderTitle(guiGraphics, this.page.getTitle2(), false, BookEntryScreen.PAGE_WIDTH / 2,
                    recipeY - (this.page.getTitle2().getString().isEmpty() ? 10 : 0) - 10);
        }

        guiGraphics.text(null, "[Modonomicon recipe rendering disabled for 26.1]", recipeX, recipeY, 0x000000, false);
    }
}
