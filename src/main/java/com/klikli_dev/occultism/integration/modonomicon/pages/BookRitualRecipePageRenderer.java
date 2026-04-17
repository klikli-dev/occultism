/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.crafting.recipe.display.RitualRecipeDisplay;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.OpenFile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.ShowText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class BookRitualRecipePageRenderer extends BookRecipePageRenderer<RitualRecipe, BookRitualRecipePage> {
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
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        var entry = this.page.getRecipeDisplayEntry1();
        if (entry != null && entry.display() instanceof RitualRecipeDisplay display) {
            this.drawRitualRecipe(guiGraphics, display, X, Y, mouseX, mouseY, false);
        } else {
            this.drawWrappedStringNoShadow(guiGraphics,
                    Component.translatable("modonomicon.gui.recipe_page.recipe_missing", String.valueOf(this.page.getRecipeKey1()))
                            .withStyle(ChatFormatting.RED),
                    X - 13, Y - 15, -1, 124);
        }

        int textY = this.getTextY();
        this.renderBookTextHolder(guiGraphics, this.page.getText(), 0, textY, 124, 155 - textY);

        var style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null) {
            this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
        }
    }

    @Nullable
    @Override
    public Style getClickedComponentStyleAt(double mouseX, double mouseY) {
        var textStyle = super.getClickedComponentStyleAt(mouseX, mouseY);
        if (textStyle != null || mouseX <= 0 || mouseY <= 0) {
            return textStyle;
        }

        var entry = this.page.getRecipeDisplayEntry1();
        if (entry == null || !(entry.display() instanceof RitualRecipeDisplay display)) {
            return textStyle;
        }

        int pentacleNameX = X + RITUAL_DUMMY_OFFSET;
        int pentacleNameY = Y + 8;
        String pentacleName = I18n.get(Util.makeDescriptionId("multiblock", display.pentacleId()));
        int nameWidth = this.font.width(pentacleName);

        int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10;
        float scale = Math.min(1.0f, (float) maxWidth / (float) nameWidth);
        if (scale < 1) {
            nameWidth = (int) (nameWidth * scale);
        }

        if (mouseX > pentacleNameX && mouseX < pentacleNameX + nameWidth && mouseY > pentacleNameY && mouseY < pentacleNameY + this.font.lineHeight) {
            String goToText = "book.occultism.dictionary_of_spirits.pentacles." + display.pentacleId().getPath() + ".name";
            Component hoverComponent = Component.translatable(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_GO_TO_PENTACLE,
                    Component.translatable(goToText));
            return Style.EMPTY
                    .withClickEvent(new OpenFile("entry://occultism:dictionary_of_spirits/pentacles/" + display.pentacleId().getPath()))
                    .withHoverEvent(new ShowText(hoverComponent));
        }

        return textStyle;
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        if (entry.display() instanceof RitualRecipeDisplay display) {
            this.drawRitualRecipe(guiGraphics, display, recipeX, recipeY, mouseX, mouseY, second);
        }
    }

    private void drawRitualRecipe(GuiGraphicsExtractor guiGraphics, RitualRecipeDisplay display, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        recipeY += 10;
        final int recipeBaseY = recipeY;
        var level = Minecraft.getInstance().level;
        if (level == null) {
            guiGraphics.text(this.font, "[Ritual recipe unavailable]", recipeX, recipeY, 0x000000, false);
            return;
        }
        var context = SlotDisplayContext.fromLevel(level);

        if (!second && !this.page.getTitle1().isEmpty()) {
            this.renderTitle(guiGraphics, this.page.getTitle1(), false, BookEntryScreen.PAGE_WIDTH / 2, 0);
        }

        int ritualCenterX = recipeX + 30;
        int ritualCenterY = recipeY + 70;
        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        List<Vec3i> sacrificialBowlPosition = Stream.of(
                new Vec3i(ritualCenterX, ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius, ritualCenterY, 0),
                new Vec3i(ritualCenterX, ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius, ritualCenterY, 0),
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal, ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius, ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal, ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius, ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal, ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius, ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal, ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius, ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).toList();

        for (int i = 0; i < display.ingredients().size() && i < sacrificialBowlPosition.size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);
            this.parentScreen.renderItemStack(guiGraphics, pos.getX(), pos.getY(), mouseX, mouseY, this.sacrificialBowl);
            this.parentScreen.renderIngredient(guiGraphics, pos.getX(), pos.getY() - 5, mouseX, mouseY, display.ingredients().get(i));
        }

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 110, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 105, mouseX, mouseY, display.output().create());
        this.parentScreen.renderItemStack(guiGraphics, recipeX - 10, recipeY - 5, mouseX, mouseY, display.ritualDummy().create());

        display.summonEntityDrops().ifPresent(slotDisplay -> {
            var summonDrops = slotDisplay.resolveForStacks(context);
            if (!summonDrops.isEmpty()) {
                this.parentScreen.renderItemStacks(guiGraphics, recipeX + 85, recipeBaseY + 90, mouseX, mouseY, summonDrops);
            }
        });

        display.randomEntityDrops().ifPresent(slotDisplay -> {
            var randomDrops = slotDisplay.resolveForStacks(context);
            if (!randomDrops.isEmpty()) {
                this.parentScreen.renderItemStacks(guiGraphics, recipeX + 85, recipeBaseY + 90, mouseX, mouseY, randomDrops);
            }
        });

        guiGraphics.pose().pushMatrix();

        String pentacleName = I18n.get(Util.makeDescriptionId("multiblock", display.pentacleId()));
        int pentacleTextY = recipeY - 1;
        int pentacleTextX = recipeX;
        int pentacleMaxWidth = BookEntryScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10;
        float pentacleScale = Math.min(1.0f, (float) pentacleMaxWidth / (float) this.font.width(pentacleName));
        if (pentacleScale < 1) {
            guiGraphics.pose().translate(pentacleTextX - pentacleTextX * pentacleScale, pentacleTextY - pentacleTextY * pentacleScale);
            guiGraphics.pose().scale(pentacleScale, pentacleScale);
        }

        this.drawScaledStringNoShadow(guiGraphics, pentacleName, pentacleTextX + RITUAL_DUMMY_OFFSET, pentacleTextY, 0x3366CC, pentacleScale);
        guiGraphics.pose().popMatrix();

        display.itemToUse().ifPresent(slotDisplay -> {
            var itemToUseStacks = slotDisplay.resolveForStacks(context);
            if (!itemToUseStacks.isEmpty()) {
                this.parentScreen.renderItemStacks(guiGraphics, recipeX + 50, recipeBaseY + 21, mouseX, mouseY, itemToUseStacks);
            }
            guiGraphics.text(this.font, I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_ITEM_USE), recipeX - 15, recipeBaseY + 25, 0x000000, false);
        });

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 30, recipeY + 70, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderItemStacks(guiGraphics, recipeX + 30, recipeY + 65, mouseX, mouseY,
                display.activationItem().resolveForStacks(context));

        if (display.summonText().isPresent()) {
            guiGraphics.pose().pushMatrix();

            Component text = display.summonText().get();
            int y = recipeY + 120;
            int x = recipeX - 15;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10;
            float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale);
                guiGraphics.pose().scale(scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);
            guiGraphics.pose().popMatrix();
        }

        if (display.jobText().isPresent()) {
            guiGraphics.pose().pushMatrix();

            Component text = display.jobText().get();
            int y = recipeY + 130;
            int x = recipeX - 15;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH - 15;
            float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale);
                guiGraphics.pose().scale(scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x3366CC, scale);
            guiGraphics.pose().popMatrix();
        }

        if (display.sacrificeText().isPresent()) {
            guiGraphics.pose().pushMatrix();

            Component text = display.sacrificeText().get();
            int y = recipeY + 15;
            int x = recipeX - 15;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH;
            float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale);
                guiGraphics.pose().scale(scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);
            guiGraphics.pose().popMatrix();
        }

        if (display.conditionText().isPresent()) {
            guiGraphics.pose().pushMatrix();

            int y = recipeY + 24;
            int x = recipeX - 15;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH;
            Component text = display.conditionText().get();
            float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale);
                guiGraphics.pose().scale(scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);

            guiGraphics.pose().popMatrix();
        }
    }

    private void drawScaledStringNoShadow(GuiGraphicsExtractor guiGraphics, String text, int x, int y, int color, float scale) {
        guiGraphics.text(this.font, text, x, (int) (y + (this.font.lineHeight * (1 - scale))), color, false);
    }

    private void drawScaledStringNoShadow(GuiGraphicsExtractor guiGraphics, Component text, int x, int y, int color, float scale) {
        guiGraphics.text(this.font, text.getVisualOrderText(), x, (int) (y + (this.font.lineHeight * (1 - scale))), color, false);
    }
}
