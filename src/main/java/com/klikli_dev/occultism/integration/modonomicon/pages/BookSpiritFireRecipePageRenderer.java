/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.display.SpiritFireRecipeDisplay;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;

public class BookSpiritFireRecipePageRenderer extends BookRecipePageRenderer<SpiritFireRecipe, BookProcessingRecipePage<SpiritFireRecipe>> {
    public static final GuiSprite CRAFTING_ARROW_BENT = new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID,"book/crafting_arrow_bent"), 18, 10);

    public BookSpiritFireRecipePageRenderer(BookProcessingRecipePage<SpiritFireRecipe> page) {
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
        } else if (!this.page.getTitle2().isEmpty()) {
            this.renderTitle(guiGraphics, this.page.getTitle2(), false, BookEntryScreen.PAGE_WIDTH / 2,
                    recipeY - (this.page.getTitle2().getString().isEmpty() ? 10 : 0) - 10);
        }

        var craftingSlot = this.page.getBook().theme().content().craftingSlot();
        var craftingArrow = this.page.getBook().theme().content().craftingArrow();

        craftingSlot.extractRenderState(guiGraphics, recipeX+1, recipeY+1);

        CRAFTING_ARROW_BENT.extractRenderState(guiGraphics, recipeX + 25, recipeY + 9, (int)(CRAFTING_ARROW_BENT.width()/1.3f), (int)(CRAFTING_ARROW_BENT.height()/1.3f));

        craftingArrow.extractRenderState(guiGraphics, recipeX + 60, recipeY + 8);

        craftingSlot.extractRenderState(guiGraphics, recipeX+73, recipeY+1);

        if (!(entry.display() instanceof SpiritFireRecipeDisplay display) || Minecraft.getInstance().level == null) {
            guiGraphics.text(this.font, "[Spirit fire recipe unavailable]", recipeX, recipeY, 0xFF000000, false);
            return;
        }

        var context = SlotDisplayContext.fromLevel(Minecraft.getInstance().level);

        this.parentScreen.renderIngredient(guiGraphics, recipeX + 4, recipeY + 4, mouseX, mouseY, display.ingredient());
        this.parentScreen.renderItemStacks(guiGraphics, recipeX + 40, recipeY + 4, mouseX, mouseY,
                display.craftingStation().resolveForStacks(context));
        this.parentScreen.renderItemStacks(guiGraphics, recipeX + 76, recipeY + 4, mouseX, mouseY,
                display.result().resolveForStacks(context));
    }
}
