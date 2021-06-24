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
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRecipes;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
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
import net.minecraft.util.math.vector.Vector3i;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RitualRecipeCategory implements IRecipeCategory<RitualRecipe> {

    //region Fields
    private final IDrawable background;
    private final IDrawable arrow;
    private final String localizedName;
    private final String pentacle;
    private final ItemStack goldenSacrificialBowl = new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());
    private final ItemStack sacrificialBowl = new ItemStack(OccultismBlocks.SACRIFICIAL_BOWL.get());
    private final ItemStack requireSacrifice = new ItemStack(OccultismItems.JEI_DUMMY_REQUIRE_SACRIFICE.get());
    private final ItemStack requireItemUse = new ItemStack(OccultismItems.JEI_DUMMY_REQUIRE_ITEM_USE.get());

    private int recipeOutputOffsetX = 50;
    private int iconWidth = 16;
    private int ritualCenterX;
    private int ritualCenterY;
    //endregion Fields

    //region Initialization
    public RitualRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 100); //64
        this.ritualCenterX = this.background.getWidth() / 2 - this.iconWidth / 2 - 30;
        this.ritualCenterY = this.background.getHeight() / 2 - this.iconWidth / 2 + 10;
        this.localizedName = I18n.format(Occultism.MODID + ".jei.ritual");
        this.pentacle = I18n.format(Occultism.MODID + ".jei.pentacle");
        this.goldenSacrificialBowl.getOrCreateTag().putBoolean("RenderFull", true);
        this.sacrificialBowl.getOrCreateTag().putBoolean("RenderFull", true);
        this.arrow = guiHelper.createDrawable(
                new ResourceLocation(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ResourceLocation getUid() {
        return OccultismRecipes.RITUAL.getId();
    }

    @Override
    public Class<? extends RitualRecipe> getRecipeClass() {
        return RitualRecipe.class;
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
    public void setIngredients(RitualRecipe recipe, IIngredients ingredients) {
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
    public void setRecipe(IRecipeLayout recipeLayout, RitualRecipe recipe, IIngredients ingredients) {
        int index = 0;
        this.recipeOutputOffsetX = 75;

        //0: activation item, 1...n: ingredients
        List<ItemStack> activationItem = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<List<ItemStack>> inputItems =
                ingredients.getInputs(VanillaTypes.ITEM).stream().skip(1).collect(Collectors.toList());

        //draw activation item on top of bowl
        recipeLayout.getItemStacks().init(index, true, this.ritualCenterX, this.ritualCenterY - 5);
        recipeLayout.getItemStacks().set(index, activationItem);
        index++;

        //draw the sacrificial bowl in the center
        recipeLayout.getItemStacks().init(index, false, this.ritualCenterX, this.ritualCenterY);
        recipeLayout.getItemStacks().set(index, this.goldenSacrificialBowl);
        index++;

        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        List<Vector3i> sacrificialBowlPosition = Stream.of(
                //first the 4 centers of each side
                new Vector3i(this.ritualCenterX, this.ritualCenterY - sacrificialCircleRadius, 0),
                new Vector3i(this.ritualCenterX + sacrificialCircleRadius, this.ritualCenterY, 0),
                new Vector3i(this.ritualCenterX, this.ritualCenterY + sacrificialCircleRadius, 0),
                new Vector3i(this.ritualCenterX - sacrificialCircleRadius, this.ritualCenterY, 0),

                //then clockwise of the enter the next 4
                new Vector3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vector3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vector3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vector3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),

                //then counterclockwise of the center the last 4
                new Vector3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vector3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vector3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vector3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).collect(Collectors.toList());

        for (int i = 0; i < inputItems.size(); i++) {
            Vector3i pos = sacrificialBowlPosition.get(i);

            recipeLayout.getItemStacks().init(index, true, pos.getX(), pos.getY() - 5);
            recipeLayout.getItemStacks().set(index, inputItems.get(i));
            index++;

            recipeLayout.getItemStacks().init(index, false, pos.getX(), pos.getY());
            recipeLayout.getItemStacks().set(index, this.sacrificialBowl);
            index++;

        }

        //ingredients: 0: recipe output, 1: ritual dummy item

        //draw recipe output on the left
        if(recipe.getRecipeOutput().getItem() != OccultismItems.JEI_DUMMY_NONE.get()){
            //if we have an item output -> render it
            recipeLayout.getItemStacks()
                    .init(index, false, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 5);
            recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
            index++;
        }
        else{
            //if not, we instead render our ritual dummy item, just like in the corner
            recipeLayout.getItemStacks()
                    .init(index, false, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 5);
            recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
            index++;
        }
            recipeLayout.getItemStacks()
                    .init(index, false, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY);
            recipeLayout.getItemStacks().set(index, this.goldenSacrificialBowl);
            index++;


        //draw ritual dummy item in upper left corner

        recipeLayout.getItemStacks().init(index, false, 0, 0);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
        index++;
        int dummyY = 0;
        int dummyX = this.background.getWidth() - this.iconWidth - 5;
        //draw info dummy items below
        if (recipe.requireSacrifice()) {
            recipeLayout.getItemStacks().init(index, false, dummyX, dummyY);
            recipeLayout.getItemStacks().set(index, this.requireSacrifice);
            index++;
            dummyY += 20;
        }

        if (recipe.requireItemUse()) {
            recipeLayout.getItemStacks().init(index, false, dummyX, dummyY);
            recipeLayout.getItemStacks().set(index, this.requireItemUse);
            index++;
            dummyY += 20;
        }
    }

    @Override
    public void draw(RitualRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.arrow.draw(matrixStack, this.ritualCenterX + this.recipeOutputOffsetX - 20, this.ritualCenterY);
        RenderSystem.disableBlend();

        Pentacle pentacle = OccultismRituals.PENTACLE_REGISTRY.getValue(recipe.getPentacleId());
        if(pentacle != null){
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                    I18n.format(pentacle.getTranslationKey()), 84, 0);
        } else {
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                    I18n.format("jei.occultism.error.pentacle_not_loaded"), 84, 0);
        }
    }
    //endregion Overrides

    //region Methods
    protected void drawStringCentered(MatrixStack matrixStack, FontRenderer fontRenderer, String text, int x, int y) {
        fontRenderer.drawString(matrixStack, text, (x - fontRenderer.getStringWidth(text) / 2.0f), y, 0);
    }
    //endregion Methods
}
