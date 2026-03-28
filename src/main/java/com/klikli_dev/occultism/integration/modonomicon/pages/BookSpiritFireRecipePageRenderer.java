/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;

public class BookSpiritFireRecipePageRenderer extends BookRecipePageRenderer<SpiritFireRecipe, BookProcessingRecipePage<SpiritFireRecipe>> {
    public BookSpiritFireRecipePageRenderer(BookProcessingRecipePage<SpiritFireRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        // Temporarily simplified for 26.1 port: detailed recipe rendering relies on legacy recipe API.
        // TODO: Port detailed rendering to new recipe and GuiGraphicsExtractor APIs.
        guiGraphics.drawString(this.font, "[Recipe rendering disabled for 26.1]", recipeX, recipeY, 0x000000, false);
    }
}
