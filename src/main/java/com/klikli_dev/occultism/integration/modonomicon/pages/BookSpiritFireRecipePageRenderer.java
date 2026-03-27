/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public abstract class BookSpiritFireRecipePageRenderer<T extends Recipe<?>> extends BookRecipePageRenderer<T, BookProcessingRecipePage<T>> {
    public BookSpiritFireRecipePageRenderer(BookProcessingRecipePage<T> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    protected void drawRecipe(GuiGraphics guiGraphics, RecipeHolder<T> recipeHolder, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        // Temporarily simplified for 26.1 port: detailed recipe rendering relies on legacy recipe API.
        // TODO: Port detailed rendering to new recipe and GuiGraphics APIs.
        guiGraphics.drawString(this.font, "[Recipe rendering disabled for 26.1]", recipeX, recipeY, 0x000000, false);
    }
}
