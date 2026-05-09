/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.integration.jei.impl.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.crafting.recipe.CrystallizeRecipe;
import com.klikli_dev.occultism.integration.jei.impl.JeiRecipeTypes;
import com.klikli_dev.occultism.util.StringRenderHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

public class CrystallizeRecipeCategory implements IRecipeCategory<RecipeHolder<CrystallizeRecipe>> {

    private final IDrawable background;
    private final Component localizedName;
    private final IDrawable overlay;

    public CrystallizeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 56); //64
        this.localizedName = Component.translatable(Occultism.MODID + ".jei.crystallize");
        this.overlay = guiHelper.getRecipeArrow();
    }

    protected void drawStringCentered(GuiGraphicsExtractor guiGraphics, Font font, Component text, int x, int y) {
        StringRenderHelper.drawString(guiGraphics, font, text, (x - font.width(text) / 2.0f), y, 0, false);
    }

    @Override
    public IRecipeType<RecipeHolder<CrystallizeRecipe>> getRecipeType() {
        return JeiRecipeTypes.CRYSTALLIZE;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public int getWidth() {
        return 168;
    }

    @Override
    public int getHeight() {
        return 56;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystallizeRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 12)
                .add(recipe.value().getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 12)
                .add(recipe.value().getResultItem());
    }

    @Override
    public void draw(RecipeHolder<CrystallizeRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 73, 13);
        this.drawStringCentered(guiGraphics, Minecraft.getInstance().font, this.getTitle(), 84, 0);
        int y = 35;
        if (recipe.value().getMinTier() >= 0) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font, Component.translatable(TranslationKeys.JEI_CRYSTALLIZE_RECIPE_MIN_TIER, recipe.value().getMinTier()), 84, y);
            y += 10;
        }
        if (recipe.value().getMaxTier() >= 0) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font, Component.translatable(TranslationKeys.JEI_CRYSTALLIZE_RECIPE_MAX_TIER, recipe.value().getMaxTier()), 84, y);
        }
    }
}

