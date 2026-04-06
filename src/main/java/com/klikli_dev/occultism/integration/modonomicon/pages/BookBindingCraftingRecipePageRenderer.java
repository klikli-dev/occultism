/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;

public class BookBindingCraftingRecipePageRenderer extends BookRecipePageRenderer<Recipe<?>, BookBindingCraftingRecipePage> {
    public BookBindingCraftingRecipePageRenderer(BookBindingCraftingRecipePage page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 78;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        this.drawBindingRecipe(guiGraphics, X, Y, mouseX, mouseY, false);

        int textY = this.getTextY();
        this.renderBookTextHolder(guiGraphics, this.page.getText(), 0, textY, 124, 155 - textY);

        var style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null) {
            this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
        }
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        this.drawBindingRecipe(guiGraphics, recipeX, recipeY, mouseX, mouseY, second);
    }

    private void drawBindingRecipe(GuiGraphicsExtractor guiGraphics, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        if (!second) {
            if (!this.page.getTitle1().isEmpty()) {
                this.renderTitle(guiGraphics, this.page.getTitle1(), false, BookEntryScreen.PAGE_WIDTH / 2, -5);
            }
        } else if (!this.page.getTitle2().isEmpty()) {
            this.renderTitle(guiGraphics, this.page.getTitle2(), false, BookEntryScreen.PAGE_WIDTH / 2,
                    recipeY - (this.page.getTitle2().getString().isEmpty() ? 10 : 0) - 10);
        }

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.page.getBook().getCraftingTexture(), recipeX - 2, recipeY - 2,
                0.0F, 0.0F, 100, 62, 128, 256);

        int iconX = recipeX + 62;
        int iconY = recipeY + 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.page.getBook().getCraftingTexture(), iconX, iconY,
                0.0F, 64.0F, 11, 11, 128, 256);
        if (this.parentScreen.isMouseInRange(mouseX, mouseY, iconX, iconY, 11, 11)) {
            this.parentScreen.setTooltip(java.util.List.of(Component.translatable(ModonomiconConstants.I18n.Tooltips.RECIPE_CRAFTING_SHAPELESS)));
        }

        ItemStack boundBook = this.page.unboundBook != null
                ? BoundBookOfBindingRecipe.getBoundBookFromBook(this.page.unboundBook.create())
                : ItemStack.EMPTY;
        this.parentScreen.renderItemStack(guiGraphics, recipeX + 79, recipeY + 22, mouseX, mouseY, boundBook);

        Ingredient dictionary = Ingredient.of(OccultismItems.DICTIONARY_OF_SPIRITS.get());
        Ingredient unboundBook = this.page.unboundBook != null
                ? Ingredient.of(this.page.unboundBook.create().getItem())
                : Ingredient.of(Items.AIR);

        this.parentScreen.renderIngredient(guiGraphics, recipeX + 3, recipeY + 3, mouseX, mouseY, dictionary);
        this.parentScreen.renderIngredient(guiGraphics, recipeX + 22, recipeY + 3, mouseX, mouseY, unboundBook);
        this.parentScreen.renderItemStack(guiGraphics, recipeX + 79, recipeY + 41, mouseX, mouseY,
                Items.CRAFTING_TABLE.getDefaultInstance());
    }
}
