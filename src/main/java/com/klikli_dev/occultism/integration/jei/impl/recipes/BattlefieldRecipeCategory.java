package com.klikli_dev.occultism.integration.jei.impl.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

// Based on https://github.com/CyclopsMC/EvilCraft-Compat/blob/master-26/src/main/java/org/cyclops/evilcraftcompat/modcompat/jei/spiritfurnace/SpiritFurnaceRecipeCategory.java
public class BattlefieldRecipeCategory implements IRecipeCategory<BattlefieldRecipeJEI> {

    public static final IRecipeType<BattlefieldRecipeJEI> TYPE = IRecipeType.create(Occultism.MODID, "battlefield", BattlefieldRecipeJEI.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    public BattlefieldRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(124, 24);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get()));
        this.arrow = guiHelper.getRecipeArrow();
    }


    @Override
    public IRecipeType<BattlefieldRecipeJEI> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.occultism.dimensional_battlefield");
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BattlefieldRecipeJEI recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 4)
                .add(recipe.getSpawnEgg());
        builder.addSlot(RecipeIngredientRole.INPUT, 28, 4)
                .add(recipe.getInputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 4)
                .addItemStacks(recipe.getSoulItems());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 100, 4)
                .addItemStacks(recipe.getOutputItems());
    }

    @Override
    public void draw(BattlefieldRecipeJEI recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        arrow.draw(guiGraphics, 72, 4);
    }

    @Override
    public Identifier getIdentifier(BattlefieldRecipeJEI recipe) {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, "battlefield/" + recipe.getSpawnEgg().getItem().toString().replace(":","/"));
    }
}
