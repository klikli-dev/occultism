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
import com.github.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SpiritFireRecipeCategory implements IRecipeCategory<SpiritFireRecipe> {

    //region Fields
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(OccultismItems.SPIRIT_FIRE.get());
    //endregion Fields

    //region Initialization
    public SpiritFireRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 46); //64
        this.localizedName = I18n.format(Occultism.MODID + ".jei.spirit_fire");
        this.overlay = guiHelper.createDrawable(
                new ResourceLocation(Occultism.MODID, "textures/gui/jei/spirit_fire.png"), 0, 0, 64, 46);
        this.icon = guiHelper.createDrawableIngredient(this.renderStack);
        this.renderStack.getOrCreateTag().putBoolean("RenderFull", true);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ResourceLocation getUid() {
        return OccultismRecipes.SPIRIT_FIRE.getId();
    }

    @Override
    public Class<? extends SpiritFireRecipe> getRecipeClass() {
        return SpiritFireRecipe.class;
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
        return this.icon;
    }

    @Override
    public void setIngredients(SpiritFireRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SpiritFireRecipe recipe, IIngredients ingredients) {
        int index = 0;

        recipeLayout.getItemStacks().init(index, true, 40, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        index++;

        recipeLayout.getItemStacks().init(index, true, 75, 12);
        recipeLayout.getItemStacks().set(index, this.renderStack);
        index++;

        recipeLayout.getItemStacks().init(index, false, 110, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    public void draw(SpiritFireRecipe recipe, MatrixStack poseStack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.overlay.draw(poseStack,48, 0);
    }
    //endregion Overrides
}
