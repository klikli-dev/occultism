/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.github.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.BookContentScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.crafting.Recipe;

public abstract class BookSpiritFireRecipePageRenderer<T extends Recipe<?>> extends BookRecipePageRenderer<T, BookProcessingRecipePage<T>> {
    public BookSpiritFireRecipePageRenderer(BookProcessingRecipePage<T> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    protected void drawRecipe(PoseStack poseStack, T recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {

        recipeY += 10;


        if(!second){
            if (!this.page.getTitle1().isEmpty()) {
                this.renderTitle(this.page.getTitle1(), false, poseStack, BookContentScreen.PAGE_WIDTH / 2, 0);
            }
        }
        else {
            if (!this.page.getTitle2().isEmpty()) {
                this.renderTitle(this.page.getTitle2(), false, poseStack, BookContentScreen.PAGE_WIDTH / 2,
                        recipeY - (this.page.getTitle2().getString().isEmpty() ? 10 : 0) - 10);
            }
        }

        RenderSystem.setShaderTexture(0, this.page.getBook().getCraftingTexture());
        RenderSystem.enableBlend();
        GuiComponent.blit(poseStack, recipeX, recipeY, 11, 71, 24, 24, 128, 256); //first box
        GuiComponent.blit(poseStack, recipeX + 22, recipeY + 7, 0, 246, 18, 10, 128, 256); //"throw arrow"
        GuiComponent.blit(poseStack, recipeX + 61, recipeY, 72, 71, 36, 24, 128, 256); //straight arrow and second box

        this.parentScreen.renderIngredient(poseStack, recipeX + 4, recipeY + 4, mouseX, mouseY, recipe.getIngredients().get(0));
        this.parentScreen.renderItemStack(poseStack, recipeX + 40, recipeY + 4, mouseX, mouseY, recipe.getToastSymbol());
        this.parentScreen.renderItemStack(poseStack, recipeX + 76, recipeY + 4, mouseX, mouseY, recipe.getResultItem(this.parentScreen.getMinecraft().level.registryAccess()));
    }
}
