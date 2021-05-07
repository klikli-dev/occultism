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

package com.github.klikli_dev.occultism.integration.jei.recipes;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.crafting.recipe.RitualIngredientRecipe;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RitualRecipeCategory implements IRecipeCategory<RitualIngredientRecipe> {

    //region Fields
    private final IDrawable background;
    private final String localizedName;
    private final String pentacle;
    private final IDrawable overlay;
    //endregion Fields

    //region Initialization
    public RitualRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 86); //64
        this.localizedName = I18n.format(Occultism.MODID + ".jei.ritual");
        this.pentacle = I18n.format(Occultism.MODID + ".jei.pentacle");
        this.overlay = guiHelper.createDrawable(
                new ResourceLocation(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ResourceLocation getUid() {
        return OccultismRecipes.RITUAL_INGREDIENT.getId();
    }

    @Override
    public Class<? extends RitualIngredientRecipe> getRecipeClass() {
        return RitualIngredientRecipe.class;
    }

    @Override
    public String getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setIngredients(RitualIngredientRecipe recipe, IIngredients ingredients) {
        //0: activation item, 1...n: ingredients
        ingredients.setInputIngredients(
                Stream.concat(
                        Stream.of(recipe.getActivationItem()),
                        recipe.getIngredients().stream()
                ).collect(Collectors.toList())
        );
        //0: recipe output, 1: ritual dummy item
        ingredients.setOutputs(VanillaTypes.ITEM, Stream.of(recipe.getRecipeOutput(),
                recipe.getRitual()).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RitualIngredientRecipe recipe, IIngredients ingredients) {
        int index = 0;
        int iconWidth = 16;
        int x = this.background.getWidth() / 2 - iconWidth / 2;
        int y = 12;

        //0: activation item, 1...n: ingredients
        List<ItemStack> activationItem = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<List<ItemStack>> inputItems =
                ingredients.getInputs(VanillaTypes.ITEM).stream().skip(0).collect(Collectors.toList());

        recipeLayout.getItemStacks().init(index, true, 56, y);
        recipeLayout.getItemStacks().set(index, activationItem);
        index++;
        y+= 10;

        int inputItemSlotOffset = 10;
        if (inputItems.size() > 1)
            x = x - (inputItems.size() * (iconWidth + inputItemSlotOffset)) / 2 + (iconWidth + inputItemSlotOffset) / 2;
        for (int i = 0; i < inputItems.size(); i++) {
            recipeLayout.getItemStacks().init(index + i, false, x + i * (iconWidth + inputItemSlotOffset), y);
            recipeLayout.getItemStacks().set(index + i, inputItems.get(i));
        }

        y+= 20;
        //0: recipe output, 1: ritual dummy item
        recipeLayout.getItemStacks().init(index, false, 94, y);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
        recipeLayout.getItemStacks().init(index, false, 112, y);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
    }

    @Override
    public void draw(RitualIngredientRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.overlay.draw(matrixStack, 76, 14); //(center=84) - (width/16=8) = 76
        this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer, this.getTitle(), 84, 0);
        this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                I18n.format(this.pentacle) + ": " + I18n.format(recipe.getPentacleTranslationKey()), 70, 12);
    }
    //endregion Overrides

    //region Methods
    protected void drawStringCentered(MatrixStack matrixStack, FontRenderer fontRenderer, String text, int x, int y) {
        fontRenderer.drawString(matrixStack, text, (x - fontRenderer.getStringWidth(text) / 2.0f), y, 0);
    }
    //endregion Methods
}
