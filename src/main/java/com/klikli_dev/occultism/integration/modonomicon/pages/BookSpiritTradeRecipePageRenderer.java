/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public abstract class BookSpiritTradeRecipePageRenderer<T extends Recipe<?>> extends BookRecipePageRenderer<T, BookProcessingRecipePage<T>> {
    public BookSpiritTradeRecipePageRenderer(BookProcessingRecipePage<T> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    protected void drawRecipe(GuiGraphics guiGraphics, RecipeHolder<T> recipeHolder, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {

        recipeY += 10;
        var recipe = recipeHolder.value();

        if (!second) {
            if (!this.page.getTitle1().isEmpty()) {
                this.renderTitle(guiGraphics, this.page.getTitle1(), false, BookEntryScreen.PAGE_WIDTH / 2, 0);
            }
        } else {
            if (!this.page.getTitle2().isEmpty()) {
                this.renderTitle(guiGraphics, this.page.getTitle2(), false, BookEntryScreen.PAGE_WIDTH / 2,
                        recipeY - (this.page.getTitle2().getString().isEmpty() ? 10 : 0) - 10);
            }
        }

        // Simplified for 26.1 port - port rendering later
        guiGraphics.drawString(this.font, "[Recipe rendering disabled for 26.1]", recipeX, recipeY, 0x000000, false);
    }
}
