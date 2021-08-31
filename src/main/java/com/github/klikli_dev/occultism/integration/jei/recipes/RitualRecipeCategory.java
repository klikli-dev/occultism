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
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RitualRecipeCategory implements IRecipeCategory<RitualRecipe> {

    //region Fields
    private final IDrawable background;
    private final IDrawable arrow;
    private final Component localizedName;
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
        this.background = guiHelper.createBlankDrawable(168, 120); //64
        this.ritualCenterX = this.background.getWidth() / 2 - this.iconWidth / 2 - 30;
        this.ritualCenterY = this.background.getHeight() / 2 - this.iconWidth / 2 + 20;
        this.localizedName = new TranslatableComponent(Occultism.MODID + ".jei.ritual");
        this.pentacle = I18n.get(Occultism.MODID + ".jei.pentacle");
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
    public void setIngredients(RitualRecipe recipe, IIngredients ingredients) {
        //0: activation item, 1...n: ingredients
        Stream<Ingredient> ingredientStream = Stream.concat(
                Stream.of(recipe.getActivationItem()),
                recipe.getIngredients().stream()
        );
        if(recipe.requiresItemUse()){
            ingredientStream = Stream.concat(Stream.of(recipe.getItemToUse()), ingredientStream);
        }
        ingredients.setInputIngredients(
                ingredientStream.collect(Collectors.toList())
        );
        //0: recipe output, 1: ritual dummy item
        ingredients.setOutputs(VanillaTypes.ITEM, Stream.of(recipe.getRecipeOutput(),
                recipe.getRitualDummy()).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RitualRecipe recipe, IIngredients ingredients) {
        int index = 0;
        this.recipeOutputOffsetX = 75;

        int currentIngredient = 0;
        List<ItemStack> itemToUse = new ArrayList<>();
        if(recipe.requiresItemUse()){
           itemToUse = ingredients.getInputs(VanillaTypes.ITEM).get(currentIngredient++);
        }
        //0: activation item, 1...n: ingredients
        List<ItemStack> activationItem = ingredients.getInputs(VanillaTypes.ITEM).get(currentIngredient++);
        List<List<ItemStack>> inputItems =
                ingredients.getInputs(VanillaTypes.ITEM).stream().skip(currentIngredient).collect(Collectors.toList());

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
        List<Vec3i> sacrificialBowlPosition = Stream.of(
                //first the 4 centers of each side
                new Vec3i(this.ritualCenterX, this.ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius, this.ritualCenterY, 0),
                new Vec3i(this.ritualCenterX, this.ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius, this.ritualCenterY, 0),

                //then clockwise of the enter the next 4
                new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),

                //then counterclockwise of the center the last 4
                new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).collect(Collectors.toList());

        for (int i = 0; i < inputItems.size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);

            recipeLayout.getItemStacks().init(index, true, pos.getX(), pos.getY() - 5);
            recipeLayout.getItemStacks().set(index, inputItems.get(i));
            index++;

            recipeLayout.getItemStacks().init(index, false, pos.getX(), pos.getY());
            recipeLayout.getItemStacks().set(index, this.sacrificialBowl);
            index++;

        }

        //ingredients: 0: recipe output, 1: ritual dummy item

        //draw recipe output on the left
        if (recipe.getResultItem().getItem() != OccultismItems.JEI_DUMMY_NONE.get()) {
            //if we have an item output -> render it
            recipeLayout.getItemStacks()
                    .init(index, false, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 5);
            recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
            index++;
        } else {
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

        //draw item to use
        if (recipe.requiresItemUse()) {
            //first simulate the info rendering to get the right render position
            int infotextY = 0;
            if(recipe.requiresSacrifice()){
                infotextY += 10;
            }

            infotextY += 10;
            String text = I18n.format("jei.occultism.item_to_use");
            int itemToUseY = infotextY - 5;
            int itemToUseX = this.getStringCenteredMaxX(Minecraft.getInstance().fontRenderer, text, 84, infotextY);

            recipeLayout.getItemStacks().init(index, false, itemToUseX, itemToUseY);
            recipeLayout.getItemStacks().set(index, itemToUse);
            index++;
        }
    }

    @Override
    public void draw(RitualRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        this.arrow.draw(poseStack, this.ritualCenterX + this.recipeOutputOffsetX - 20, this.ritualCenterY);
        RenderSystem.disableBlend();

        Pentacle pentacle = PentacleManager.get(recipe.getPentacleId());
        if(pentacle != null){
            this.drawStringCentered(poseStack, Minecraft.getInstance().font,
                    new TranslatableComponent(pentacle.getDescriptionId()), 84, 0);
        } else {
            this.drawStringCentered(poseStack, Minecraft.getInstance().font,
                    new TranslatableComponent("jei.occultism.error.pentacle_not_loaded"), 84, 0);
        }

        int infotextY = 0;
        if(recipe.requiresSacrifice()){
            infotextY += 10;
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                    I18n.format("jei.occultism.sacrifice", I18n.format(recipe.getEntityToSacrificeDisplayName())), 84, infotextY);
        }

        if(recipe.requiresItemUse()){
            infotextY += 10;
            String text = I18n.format("jei.occultism.item_to_use");
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer, text, 84, infotextY);
        }

        if(recipe.getEntityToSummon() != null){
            infotextY += 10;
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                    I18n.format("jei.occultism.summon", I18n.format(recipe.getEntityToSummon().getTranslationKey())),
                    84, infotextY);
        }

        if(recipe.getSpiritJobType() != null){
            infotextY += 10;
            this.drawStringCentered(matrixStack, Minecraft.getInstance().fontRenderer,
                    I18n.format("jei.occultism.job",
                            I18n.format("job." + recipe.getSpiritJobType().toString().replace(":", "."))),
                    84, infotextY);
        }
    }
    //endregion Overrides

    //region Methods

    protected int getStringCenteredMaxX(FontRenderer fontRenderer, String text, int x, int y){
        int width = fontRenderer.getStringWidth(text);
        int actualX = (int)(x - width / 2.0f);
        return actualX + width;
    }

    protected void drawStringCentered(PoseStack poseStack, Font fontRenderer, Component text, int x, int y) {
        fontRenderer.draw(poseStack, text, (x - fontRenderer.width(text) / 2.0f), y, 0);
    }
    //endregion Methods
}
