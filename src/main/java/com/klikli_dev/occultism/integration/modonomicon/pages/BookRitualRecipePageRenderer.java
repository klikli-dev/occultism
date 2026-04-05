/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.modonomicon.pages;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookRecipePageRenderer;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapperFactory;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionDescriptionVisitor;
import com.klikli_dev.occultism.integration.modonomicon.OccultismModonomiconConstants;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookRitualRecipePageRenderer extends BookRecipePageRenderer<RitualRecipe, BookRitualRecipePage> {
    public static final int RITUAL_DUMMY_OFFSET = 10;
    private static final AtomicBoolean LOGGED_CLIENT_RENDER_DIAGNOSTICS = new AtomicBoolean(false);

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
        this.logClientDiagnostics();

        RitualRecipe recipe = this.getRecipe();
        if (recipe != null) {
            this.drawRitualRecipe(guiGraphics, recipe, X, Y, mouseX, mouseY, false);
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

        RitualRecipe recipe = this.getRecipe();
        if (recipe == null || recipe.getPentacleId() == null) {
            return textStyle;
        }

        int pentacleNameX = X + RITUAL_DUMMY_OFFSET;
        int pentacleNameY = Y + 8;
        String pentacleName = I18n.get(Util.makeDescriptionId("multiblock", recipe.getPentacleId()));
        int nameWidth = this.font.width(pentacleName);

        int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10;
        float scale = Math.min(1.0f, (float) maxWidth / (float) nameWidth);
        if (scale < 1) {
            nameWidth = (int) (nameWidth * scale);
        }

        if (mouseX > pentacleNameX && mouseX < pentacleNameX + nameWidth && mouseY > pentacleNameY && mouseY < pentacleNameY + this.font.lineHeight) {
            String goToText = "book.occultism.dictionary_of_spirits.pentacles." + recipe.getPentacleId().getPath() + ".name";
            Component hoverComponent = Component.translatable(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_GO_TO_PENTACLE,
                    Component.translatable(goToText));
            return Style.EMPTY
                    .withClickEvent(new ClickEvent.OpenFile("entry://occultism:dictionary_of_spirits/pentacles/" + recipe.getPentacleId().getPath()))
                    .withHoverEvent(new HoverEvent.ShowText(hoverComponent));
        }

        return textStyle;
    }

    @Override
    protected void drawRecipe(GuiGraphicsExtractor guiGraphics, RecipeDisplayEntry entry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        RitualRecipe recipe = this.getRecipe();
        if (recipe != null) {
            this.drawRitualRecipe(guiGraphics, recipe, recipeX, recipeY, mouseX, mouseY, second);
        }
    }

    @Nullable
    private RitualRecipe getRecipe() {
        if (this.parentScreen == null) {
            return null;
        }

        if (this.page.getRitualRecipe() != null) {
            return this.page.getRitualRecipe();
        }

        return null;
    }

    private void logClientDiagnostics() {
        if (!LOGGED_CLIENT_RENDER_DIAGNOSTICS.compareAndSet(false, true)) {
            return;
        }

        var requested = this.page.getRecipeKey1() != null ? this.page.getRecipeKey1().identifier().toString() : "<null>";
        var display = this.page.getRecipeDisplayEntry1() != null ? this.page.getRecipeDisplayEntry1().display() : null;

        Occultism.LOGGER.info("[Modonomicon Ritual Diagnostics][Client] requestedRecipe={}, pageDisplayEntryPresent={}, pageDisplayType={}, pageSyncedRecipePresent={}, clientRecipeLookupAvailable={}",
                requested,
                this.page.getRecipeDisplayEntry1() != null,
                display != null ? display.type() : "<null>",
                this.page.getRitualRecipe() != null,
                false);
    }

    private void drawRitualRecipe(GuiGraphicsExtractor guiGraphics, RitualRecipe recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        recipeY += 10;

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

        for (int i = 0; i < recipe.getIngredients().size() && i < sacrificialBowlPosition.size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);
            this.parentScreen.renderItemStack(guiGraphics, pos.getX(), pos.getY(), mouseX, mouseY, this.sacrificialBowl);
            this.parentScreen.renderIngredient(guiGraphics, pos.getX(), pos.getY() - 5, mouseX, mouseY, recipe.getIngredients().get(i));
        }

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 110, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderItemStack(guiGraphics, recipeX + 85, recipeY + 105, mouseX, mouseY, recipe.getResult());
        this.parentScreen.renderItemStack(guiGraphics, recipeX - 10, recipeY - 5, mouseX, mouseY, recipe.getRitualDummy());

        if (recipe.getEntityToSummon() != null) {
            String mob = recipe.getEntityToSummon().getDefaultLootTable().map(key -> key.identifier().toString()).orElse("")
                    .replace("occultism:entities/", "")
                    .replace("minecraft:entities/", "")
                    .replace("c:entities/", "")
                    .replace(":entities/", "_");
            Ingredient ingredient = ingredientFromTag(OccultismTags.makeItemTag("occultism:drop_from/" + mob));
            if (ingredient != null && !ingredient.isEmpty()) {
                this.parentScreen.renderIngredient(guiGraphics, recipeX + 85, recipeY + 90, mouseX, mouseY, ingredient);
            }
        }

        if (recipe.getEntityTagToSummon() != null) {
            String mob = recipe.getEntityTagToSummon().location().toString()
                    .replace("random_animals_", "")
                    .replace("occultism:", "")
                    .replace("minecraft:", "")
                    .replace("c:", "")
                    .replace(":", "_");
            Ingredient ingredient = ingredientFromTag(OccultismTags.makeItemTag("occultism:random_spawn_from/" + mob));
            if (ingredient != null && !ingredient.isEmpty()) {
                this.parentScreen.renderIngredient(guiGraphics, recipeX + 85, recipeY + 90, mouseX, mouseY, ingredient);
            }
        }

        if (recipe.getPentacle() != null) {
            guiGraphics.pose().pushMatrix();

            String pentacleName = I18n.get(Util.makeDescriptionId("multiblock", recipe.getPentacleId()));
            int y = recipeY - 1;
            int x = recipeX;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH - RITUAL_DUMMY_OFFSET - 10;
            float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(pentacleName));
            if (scale < 1) {
                guiGraphics.pose().translate(x - x * scale, y - y * scale);
                guiGraphics.pose().scale(scale, scale);
            }

            this.drawScaledStringNoShadow(guiGraphics, pentacleName, x + RITUAL_DUMMY_OFFSET, y, 0x3366CC, scale);
            guiGraphics.pose().popMatrix();
        }

        if (recipe.requiresItemUse()) {
            this.parentScreen.renderIngredient(guiGraphics, recipeX + 50, recipeY + 21, mouseX, mouseY, recipe.getItemToUse());
            guiGraphics.text(this.font, I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_ITEM_USE), recipeX - 15, recipeY + 25, 0x000000, false);
        }

        this.parentScreen.renderItemStack(guiGraphics, recipeX + 30, recipeY + 70, mouseX, mouseY, this.goldenSacrificialBowl);
        this.parentScreen.renderIngredient(guiGraphics, recipeX + 30, recipeY + 65, mouseX, mouseY, recipe.getActivationItem());

        if (recipe.getEntityToSummon() != null) {
            guiGraphics.pose().pushMatrix();

            String text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SUMMON,
                    I18n.get(recipe.getEntityToSummon().getDescriptionId()));
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

        if (recipe.getSpiritJobType() != null) {
            guiGraphics.pose().pushMatrix();

            String text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_JOB,
                    I18n.get("job." + recipe.getSpiritJobType().toString().replace(":", ".")));
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

        if (recipe.requiresSacrifice()) {
            guiGraphics.pose().pushMatrix();

            String text = I18n.get(OccultismModonomiconConstants.I18n.RITUAL_RECIPE_SACRIFICE,
                    I18n.get(recipe.getEntityToSacrificeDisplayName()));
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

        if (recipe.getCondition() != null) {
            guiGraphics.pose().pushMatrix();

            int y = recipeY + 24;
            int x = recipeX - 15;
            int maxWidth = BookEntryScreen.MAX_TITLE_WIDTH;
            var visitor = new RitualRecipeConditionDescriptionVisitor();
            var condition = ConditionWrapperFactory.wrap(recipe.getCondition());
            if (condition != null) {
                Component text = condition.accept(visitor, OccultismConditionContext.EMPTY);
                float scale = Math.min(1.0f, (float) maxWidth / (float) this.font.width(text));
                if (scale < 1) {
                    guiGraphics.pose().translate(x - x * scale, y - y * scale);
                    guiGraphics.pose().scale(scale, scale);
                }

                this.drawScaledStringNoShadow(guiGraphics, text, x, y, 0x000000, scale);
            }

            guiGraphics.pose().popMatrix();
        }
    }

    private void drawScaledStringNoShadow(GuiGraphicsExtractor guiGraphics, String text, int x, int y, int color, float scale) {
        guiGraphics.text(this.font, text, x, (int) (y + (this.font.lineHeight * (1 - scale))), color, false);
    }

    private void drawScaledStringNoShadow(GuiGraphicsExtractor guiGraphics, Component text, int x, int y, int color, float scale) {
        guiGraphics.text(this.font, text.getVisualOrderText(), x, (int) (y + (this.font.lineHeight * (1 - scale))), color, false);
    }

    @Nullable
    private Ingredient ingredientFromTag(net.minecraft.tags.TagKey<net.minecraft.world.item.Item> tag) {
        return BuiltInRegistries.ITEM.get(tag)
                .filter(holderSet -> holderSet.size() > 0)
                .map(Ingredient::of)
                .orElse(null);
    }
}
