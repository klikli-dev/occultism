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
import com.klikli_dev.occultism.common.misc.WeightedOutputIngredient;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.integration.jei.impl.JeiRecipeTypes;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.util.GuiGraphicsExt;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinerRecipeCategory implements IRecipeCategory<RecipeHolder<MinerRecipe>> {

    private final IDrawable background;
    private final Component localizedName;
    private final IDrawable overlay;

    private final Map<MinerRecipe, Float> chances = new HashMap<>();

    public MinerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 46); //64
        this.localizedName = Component.translatable(Occultism.MODID + ".jei.miner");
        this.overlay = guiHelper.createDrawable(
                ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
    }

    protected void drawStringCentered(GuiGraphics guiGraphics, Font font, Component text, int x, int y) {
        GuiGraphicsExt.drawString(guiGraphics, font, text, x - font.width(text) / 2.0f, y, 0, false);
    }

    @Override
    public RecipeType<RecipeHolder<MinerRecipe>> getRecipeType() {
        return JeiRecipeTypes.MINER;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MinerRecipe> recipe, IFocusGroup focuses) {
        //set up a simulated handler to get all possible results
        Level level = Minecraft.getInstance().level;
        ItemStackHandler simulatedHandler = new ItemStackHandler(1);
        simulatedHandler.setStackInSlot(0, recipe.value().getIngredients().get(0).getItems()[0]);
        var recipes = level.getRecipeManager()
                .getRecipesFor(OccultismRecipes.MINER_TYPE.get(),
                        new RecipeWrapper(simulatedHandler), level);
        List<WeightedOutputIngredient> possibleResults = recipes.stream().map(RecipeHolder::value).map(MinerRecipe::getWeightedResult).toList();

        float chance = (float) recipe.value().getWeightedResult().getWeight().asInt()/100;
        this.chances.put(recipe.value(), chance);

        builder.addSlot(RecipeIngredientRole.INPUT, 56, 12)
                .addIngredients(recipe.value().getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 12)
                .addItemStack(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    @Override
    public void draw(RecipeHolder<MinerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.overlay.draw(guiGraphics, 76, 14); //(center=84) - (width/16=8) = 76
        this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                Component.translatable(Occultism.MODID + ".jei.miner.chance", this.chances.get(recipe)), 84, 0);
    }
}
