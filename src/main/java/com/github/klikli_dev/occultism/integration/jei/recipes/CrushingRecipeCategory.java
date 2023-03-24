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

//TODO: Re-enable
//public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe> {
//
//    private final IDrawable background;
//    private final Component localizedName;
//    private final IDrawable overlay;
//
//    public CrushingRecipeCategory(IGuiHelper guiHelper) {
//        this.background = guiHelper.createBlankDrawable(168, 46); //64
//        this.localizedName = Component.translatable(Occultism.MODID + ".jei.crushing");
//        this.overlay = guiHelper.createDrawable(
//                new ResourceLocation(Occultism.MODID, "textures/gui/jei/arrow.png"), 0, 0, 64, 46);
//    }
//
//    protected void drawStringCentered(PoseStack poseStack, Font fontRenderer, Component text, int x, int y) {
//        fontRenderer.draw(poseStack, text, (x - fontRenderer.width(text) / 2.0f), y, 0);
//    }
//
//    @Override
//    public RecipeType<CrushingRecipe> getRecipeType() {
//        return JeiRecipeTypes.CRUSHING;
//    }
//
//    @Override
//    public Component getTitle() {
//        return this.localizedName;
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return this.background;
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return null;
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses) {
//        builder.addSlot(RecipeIngredientRole.INPUT, 56, 12)
//                .addIngredients(recipe.getIngredients().get(0));
//
//        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 12)
//                .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
//    }
//
//    @Override
//    public void draw(CrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
//        RenderSystem.enableBlend();
//        this.overlay.draw(stack, 76, 14); //(center=84) - (width/16=8) = 76
//        this.drawStringCentered(stack, Minecraft.getInstance().font, this.getTitle(), 84, 0);
//        if(recipe.getMinTier() >= 0){
//           this.drawStringCentered(stack, Minecraft.getInstance().font, Component.translatable(TranslationKeys.JEI_CRUSHING_RECIPE_TIER, recipe.getMinTier()), 84, 35);
//        }
//    }
//}
//
