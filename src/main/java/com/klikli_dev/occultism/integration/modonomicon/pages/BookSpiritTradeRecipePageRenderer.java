/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.crafting.recipe.display.SpiritTradeRecipeDisplay;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;

public class BookSpiritTradeRecipePageRenderer extends BookRecipePageRenderer<SpiritTradeRecipe, BookProcessingRecipePage<SpiritTradeRecipe>> {
    public BookSpiritTradeRecipePageRenderer(BookProcessingRecipePage<SpiritTradeRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {

        recipeY += 10;

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

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.page.getBook().getCraftingTexture(), recipeX, recipeY,
                11.0F, 71.0F, 24, 24, 128, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.page.getBook().getCraftingTexture(), recipeX + 36, recipeY + 7,
                0.0F, 246.0F, 18, 10, 128, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.page.getBook().getCraftingTexture(), recipeX + 61, recipeY,
                72.0F, 71.0F, 36, 24, 128, 256);

        if (!(entry.display() instanceof SpiritTradeRecipeDisplay display) || Minecraft.getInstance().level == null) {
            guiGraphics.text(this.font, "[Spirit trade recipe unavailable]", recipeX, recipeY, 0x000000, false);
            return;
        }

        var context = SlotDisplayContext.fromLevel(Minecraft.getInstance().level);

        this.parentScreen.renderIngredient(guiGraphics, recipeX + 4, recipeY + 4, mouseX, mouseY, display.ingredient());
        this.parentScreen.renderItemStacks(guiGraphics, recipeX + 76, recipeY + 4, mouseX, mouseY,
                display.result().resolveForStacks(context));
    }
}
