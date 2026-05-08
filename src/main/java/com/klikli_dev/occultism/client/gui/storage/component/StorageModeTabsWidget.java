/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public record StorageModeTabsWidget(
        AbstractWidget inventoryModeButton,
        AbstractWidget autocraftingModeButton) {

    public static StorageModeTabsWidget create(
            int inventoryTabX,
            int inventoryTabY,
            int autocraftingTabX,
            int autocraftingTabY,
            int tabWidth,
            int tabHeight,
            int tabIconOffsetX,
            Runnable onInventoryTab,
            Runnable onAutocraftingTab,
            Component inventoryTooltip,
            Component autocraftingTooltip) {
        AbstractWidget inventoryModeButton = createTabButton(inventoryTabX, inventoryTabY, tabWidth, tabHeight,
                onInventoryTab, inventoryTooltip, tabIconOffsetX, Blocks.CHEST.asItem());
        AbstractWidget autocraftingModeButton = createTabButton(autocraftingTabX, autocraftingTabY, tabWidth, tabHeight,
                onAutocraftingTab, autocraftingTooltip, tabIconOffsetX, Blocks.FURNACE.asItem());

        return new StorageModeTabsWidget(inventoryModeButton, autocraftingModeButton);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.inventoryModeButton);
        adder.accept(this.autocraftingModeButton);
    }

    private static AbstractWidget createTabButton(int x, int y, int width, int height, Runnable onPress,
                                                  Component tooltip, int tabIconOffsetX, Item icon) {
        return new SpriteButtonWidget(x, y, width, height,
                tooltip, onPress, (button, graphics) -> {
                }, tabIconRenderer(new ItemStack(icon), tabIconOffsetX));
    }

    private static BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> tabIconRenderer(ItemStack icon, int tabIconOffsetX) {
        return (button, graphics) -> {
            int x = button.getX() + (button.getWidth() - 16) / 2 + tabIconOffsetX;
            int y = button.getY() + (button.getHeight() - 16) / 2;
            graphics.fakeItem(icon, x, y);
        };
    }
}
