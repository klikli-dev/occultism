/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.client.gui.book.BookContentScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class BookRitualRecipePageRenderer<T extends Recipe<?>> extends BookRecipePageRenderer<RitualRecipe, BookRecipePage<RitualRecipe>> {

    public static final int RITUAL_DUMMY_OFFSET = 10;

    private final ItemStack sacrificialBowl = new ItemStack(OccultismBlocks.SACRIFICIAL_BOWL.get());
    private final ItemStack goldenSacrificialBowl = new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());

    public BookRitualRecipePageRenderer(BookRitualRecipePage page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 45;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        int recipeX = X;
        int recipeY = Y;

        if (this.page.getRecipe1() != null) {
            //rituals only support one recipe
            this.drawRecipe(guiGraphics, this.page.getRecipe1(), recipeX, recipeY, mouseX, mouseY, false);
        }


        var style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null)
            this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
    }

    @Nullable
    @Override
    public Style getClickedComponentStyleAt(double pMouseX, double pMouseY) {
        var textStyle = super.getClickedComponentStyleAt(pMouseX, pMouseY);
        if (pMouseX > 0 && pMouseY > 0 && textStyle == null && this.page.getRecipe1() != null) {

            int recipeX = X;
            int recipeY = Y;
            int pentacleNameX = recipeX + RITUAL_DUMMY_OFFSET; // see render x/y below
            int pentacleNameY = recipeY + 8;
            //8 is a magic constant, maybe actually because of line height?
            // IDK why but I put 8 and it works so I won't touch it

            var pentacleName = I18n.get(Util.makeDescriptionId("multiblock", this.page.getRecipe1().getPentacleId()));
            var nameWidth = this.font.width(pentacleName);

            int maxWidth = BookContentScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10; //account for the ritual dummy icon, 10 is a magic constant
            var scale = Math.min(1.0f, (float) maxWidth / (float) nameWidth);
            if (scale < 1) {
                nameWidth = (int) (nameWidth * scale);
            }

            if (pMouseX > pentacleNameX && pMouseX < pentacleNameX + nameWidth && pMouseY > pentacleNameY && pMouseY < pentacleNameY + this.font.lineHeight) {
                var goToText = "book.occultism.dictionary_of_spirits.pentacles." + this.page.getRecipe1().getPentacleId().getPath() + ".name";
                var hoverComponent = Component.translatable(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_GO_TO_PENTACLE,
                        Component.translatable(goToText));
                return Style.EMPTY
                        .withClickEvent(new ClickEvent(Action.CHANGE_PAGE,
                                "entry://occultism:dictionary_of_spirits/pentacles/" + this.page.getRecipe1().getPentacleId().getPath()))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent));
            }
        }
        return textStyle;
    }


    @Override
    protected void drawRecipe(GuiGraphics guiGraphics, RitualRecipe recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {

        recipeY += 10;

        if (!second) {
            //rituals only support one recipe
            if (!this.page.getTitle1().isEmpty()) {
                this.renderTitle(guiGraphics, this.page.getTitle1(), false, BookContentScreen.PAGE_WIDTH / 2, 0);
            }
        }


        int ritualCenterX = recipeX + 30;
        int ritualCenterY = recipeY + 70;
        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        List<Vec3i> sacrificialBowlPosition = Stream.of(
                //first the 4 centers of each side
                new Vec3i(ritualCenterX, ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius, ritualCenterY, 0),
                new Vec3i(ritualCenterX, ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius, ritualCenterY, 0),

                //then clockwise of the enter the next 4
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal,
                        ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius,
                        ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal,
                        ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius,
                        ritualCenterY + sacricialBowlPaddingVertical, 0),

                //then counterclockwise of the center the last 4
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal,
                        ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius,
                        ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal,
                        ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius,
                        ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).toList();

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);

            this.parentScreen.renderItemStack(guiGraphics, pos.getX(), pos.getY(), mouseX, mouseY, this.sacrificialBowl);
            this.parentScreen.renderIngredient(guiGraphics, pos.getX(), pos.getY() - 5, mouseX, mouseY, recipe.getIngredients().get(i));
        }

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 110, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 105, mouseX, mouseY, recipe.getResultItem(this.parentScreen.getMinecraft().level.registryAccess()));

        this.parentScreen.renderItemStack(guiGraphics, recipeX - 10, recipeY - 5, mouseX, mouseY, recipe.getRitualDummy());

        if (recipe.getPentacle() != null) {
            guiGraphics.pose().pushPose();

            String pentacleName = I18n.get(Util.makeDescriptionId("multiblock", recipe.getPentacleId()));

            //if pentacleName is larger than allowed, scaled to fit
            int y = recipeY - 1;
            int x = recipeX;
            int maxWidth = BookContentScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10; //account for the ritual dummy icon, 10 is a magic constant
            var scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(pentacleName));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale, 0);
                guiGraphics.pose().scale(scale, scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, pentacleName, x + RITUAL_DUMMY_OFFSET, y, 0x3366CC, scale);

            guiGraphics.pose().popPose();

        }

        if (recipe.requiresItemUse()) {
            this.parentScreen.renderIngredient(guiGraphics, recipeX + 50, recipeY + 21, mouseX, mouseY, recipe.getItemToUse());
            guiGraphics.drawString(this.font, I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_ITEM_USE), recipeX - 15, recipeY + 25, 0, false);
        }

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 30, recipeY + 70, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderIngredient(guiGraphics, recipeX + 30, recipeY + 65, mouseX, mouseY, recipe.getActivationItem());

        if (recipe.getEntityToSummon() != null) {
            guiGraphics.pose().pushPose();

            var text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SUMMON,
                    I18n.get(recipe.getEntityToSummon().getDescriptionId()));

            int y = recipeY + 120;
            int x = recipeX - 15;
            int maxWidth = BookContentScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10; //account for the ritual output, 10 is our magic constant
            var scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale, 0);
                guiGraphics.pose().scale(scale, scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);

            guiGraphics.pose().popPose();
        }

        if (recipe.getSpiritJobType() != null) {
            guiGraphics.pose().pushPose();

            var text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_JOB,
                    I18n.get("job." + recipe.getSpiritJobType().toString().replace(":", ".")));

            int y = recipeY + 130;
            int x = recipeX;
            int maxWidth = BookContentScreen.MAX_TITLE_WIDTH - 15; //account for the ritual dummy icon, 10 is a magic constant
            var scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale, 0);
                guiGraphics.pose().scale(scale, scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x - 15, y, 0x3366CC, scale);

            guiGraphics.pose().popPose();
        }

        if (recipe.requiresSacrifice()) {
            guiGraphics.pose().pushPose();

            var text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SACRIFICE,
                    I18n.get(recipe.getEntityToSacrificeDisplayName()));

            int y = recipeY + 15;
            int x = recipeX - 15;
            int maxWidth = BookContentScreen.MAX_TITLE_WIDTH;
            var scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale, 0);
                guiGraphics.pose().scale(scale, scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);

            guiGraphics.pose().popPose();
        }
    }

    public void drawScaledStringNoShadow(GuiGraphics guiGraphics, String s, int x, int y, int color, float scale) {
        guiGraphics.drawString(this.font, s, x, y + (this.font.lineHeight * (1 - scale)), color, false);
    }
}
