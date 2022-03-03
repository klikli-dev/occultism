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
import com.github.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe> {

    private final IDrawable background;
    private final Component localizedName;
    private final IDrawable overlay;

    public CrushingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 46); //64
        this.localizedName = new TranslatableComponent(Occultism.MODID + ".jei.crushing");
        this.overlay = guiHelper.createDrawable(
                new ResourceLocation(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
    }

    @Override
    public ResourceLocation getUid() {
        return OccultismRecipes.CRUSHING.getId();
    }

    @Override
    public Class<? extends CrushingRecipe> getRecipeClass() {
        return CrushingRecipe.class;
    }

    @Override
    public Component getTitle() {
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
    public void setIngredients(CrushingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrushingRecipe recipe, IIngredients ingredients) {
        int index = 0;
        recipeLayout.getItemStacks().init(index, true, 56, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        index++;

        recipeLayout.getItemStacks().init(index, false, 94, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    public void draw(CrushingRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.overlay.draw(poseStack, 76, 14); //(center=84) - (width/16=8) = 76
        this.drawStringCentered(poseStack, Minecraft.getInstance().font, this.getTitle(), 84, 0);
    }


    protected void drawStringCentered(PoseStack poseStack, Font fontRenderer, Component text, int x, int y) {
        fontRenderer.draw(poseStack, text, (x - fontRenderer.width(text) / 2.0f), y, 0);
    }
}
