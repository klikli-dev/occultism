/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public record StorageMenuControlsWidget(
        AbstractWidget clearRecipeButton,
        AbstractWidget inventoryModeButton,
        AbstractWidget autocraftingModeButton) {

    public static StorageMenuControlsWidget create(
            int controlSize,
            IconButtonBackgroundSprites buttonSprites,
            int clearRecipeButtonX,
            int clearRecipeButtonY,
            Runnable onClearRecipe,
            int inventoryTabX,
            int inventoryTabY,
            int tabWidth,
            int tabHeight,
            Runnable onInventoryTab,
            Component inventoryTooltip,
            int autocraftingTabX,
            int autocraftingTabY,
            Runnable onAutocraftingTab,
            Component autocraftingTooltip,
            int tabIconOffsetX) {

        AbstractWidget clearRecipeButton = new SpriteButtonWidget(clearRecipeButtonX, clearRecipeButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable("gui.occultism.storage_controller.crafting.clear"),
                onClearRecipe,
                SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));

        AbstractWidget inventoryModeButton = createTabButton(inventoryTabX, inventoryTabY, tabWidth, tabHeight,
                onInventoryTab, inventoryTooltip, tabIconOffsetX, Blocks.CHEST.asItem());
        AbstractWidget autocraftingModeButton = createTabButton(autocraftingTabX, autocraftingTabY, tabWidth, tabHeight,
                onAutocraftingTab, autocraftingTooltip, tabIconOffsetX, Blocks.FURNACE.asItem());

        return new StorageMenuControlsWidget(clearRecipeButton, inventoryModeButton, autocraftingModeButton);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.clearRecipeButton);
        adder.accept(this.inventoryModeButton);
        adder.accept(this.autocraftingModeButton);
    }

    private static AbstractWidget createTabButton(int x, int y, int width, int height, Runnable onPress,
                                                  Component tooltip, int tabIconOffsetX,
                                                  net.minecraft.world.item.Item icon) {
        return new SpriteButtonWidget(x, y, width, height,
                tooltip, onPress, (button, graphics) -> {
                }, tabIconRenderer(new net.minecraft.world.item.ItemStack(icon), tabIconOffsetX));
    }

    private static java.util.function.BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> tabIconRenderer(
            net.minecraft.world.item.ItemStack icon, int tabIconOffsetX) {
        return (button, graphics) -> {
            int x = button.getX() + (button.getWidth() - 16) / 2 + tabIconOffsetX;
            int y = button.getY() + (button.getHeight() - 16) / 2;
            graphics.fakeItem(icon, x, y);
        };
    }
}
