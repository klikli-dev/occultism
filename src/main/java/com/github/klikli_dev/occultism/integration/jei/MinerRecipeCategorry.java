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

package com.github.klikli_dev.occultism.integration.jei;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.misc.WeightedIngredient;
import com.github.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.github.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.util.RecipeUtil;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.stream.Collectors;

public class MinerRecipeCategorry implements IRecipeCategory<MinerRecipe> {

    //region Fields
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private float chance;
    //endregion Fields

    //region Initialization
    public MinerRecipeCategorry(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 46); //64
        this.localizedName = I18n.format(Occultism.MODID + ".jei.miner");
        this.overlay = guiHelper.createDrawable(
                new ResourceLocation(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ResourceLocation getUid() {
        return OccultismRecipes.MINER.getId();
    }

    @Override
    public Class<? extends MinerRecipe> getRecipeClass() {
        return MinerRecipe.class;
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
    public void setIngredients(MinerRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());

        //set up a simulated handler to get all possible results
        World world = Minecraft.getInstance().world;
        ItemStackHandler simulatedHandler = new ItemStackHandler(1);
        simulatedHandler.setStackInSlot(0, recipe.getIngredients().get(0).getMatchingStacks()[0]);
        List<MinerRecipe> recipes = world.getRecipeManager()
                                            .getRecipes(OccultismRecipes.MINER_TYPE.get(),
                                                    new RecipeWrapper(simulatedHandler), world);
        List<WeightedIngredient> possibleResults = recipes.stream().map(MinerRecipe::getWeightedOutput).collect(Collectors.toList());
        this.chance = Math.round(recipe.getWeightedOutput().itemWeight / (float)WeightedRandom.getTotalWeight(possibleResults) * 10000.0F) / 100.0F;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MinerRecipe recipe, IIngredients ingredients) {
        int index = 0;
        recipeLayout.getItemStacks().init(index, true, 56, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        index++;

        recipeLayout.getItemStacks().init(index, false, 94, 12);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    public void draw(MinerRecipe recipe, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.overlay.draw(76, 14); //(center=84) - (width/16=8) = 76
        String text = I18n.format(Occultism.MODID + ".jei.miner.chance", this.chance);
        this.drawStringCentered(Minecraft.getInstance().fontRenderer, text, 84, 0);

    }

    protected void drawStringCentered(FontRenderer fontRenderer, String text, int x, int y) {
        fontRenderer.drawString(text, (x - fontRenderer.getStringWidth(text) / 2.0f), y, 0);
    }
    //endregion Overrides
}
