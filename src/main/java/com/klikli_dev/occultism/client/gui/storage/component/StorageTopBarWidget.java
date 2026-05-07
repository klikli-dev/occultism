/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.occultism.client.gui.widget.SpriteButtonWidget;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public record StorageTopBarWidget(
        ScaledSearchFieldWidget searchBar,
        AbstractWidget clearSearchButton,
        AbstractWidget sortTypeButton,
        AbstractWidget sortDirectionButton,
        AbstractWidget jeiSyncButton) {

    public static StorageTopBarWidget create(
            Font font,
            int searchBarX,
            int searchBarY,
            int searchBarRenderedWidth,
            int searchBarRenderedHeight,
            float searchScale,
            String searchText,
            boolean focused,
            int controlSize,
            IconButtonBackgroundSprites buttonSprites,
            int clearButtonX,
            int clearButtonY,
            Runnable onClearSearch,
            int sortTypeButtonX,
            int sortTypeButtonY,
            Runnable onSortType,
            BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> sortTypeRenderer,
            int sortDirectionButtonX,
            int sortDirectionButtonY,
            Runnable onSortDirection,
            BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> sortDirectionRenderer,
            boolean showJeiSyncButton,
            int jeiSyncButtonX,
            int jeiSyncButtonY,
            Runnable onJeiSync,
            BiConsumer<SpriteButtonWidget, GuiGraphicsExtractor> jeiSyncRenderer,
            String translationKeyBase) {

        ScaledSearchFieldWidget searchBar = new ScaledSearchFieldWidget(font, searchBarX, searchBarY,
                searchBarRenderedWidth, searchBarRenderedHeight, Component.literal("search"), searchScale);
        searchBar.setMaxLength(30);
        searchBar.setBordered(false);
        searchBar.setVisible(true);
        searchBar.setTextColor(0xFFFFFFFF);
        searchBar.setFocused(focused);
        searchBar.setValue(searchText);

        AbstractWidget clearSearchButton = new SpriteButtonWidget(clearButtonX, clearButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".search.clear"),
                onClearSearch,
                SpriteButtonWidget.offsetText("X", 0.5F, -0.5F));

        AbstractWidget sortTypeButton = new SpriteButtonWidget(sortTypeButtonX, sortTypeButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".sort_type"),
                onSortType,
                sortTypeRenderer);

        AbstractWidget sortDirectionButton = new SpriteButtonWidget(sortDirectionButtonX, sortDirectionButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".sort_direction"),
                onSortDirection,
                sortDirectionRenderer);

        AbstractWidget jeiSyncButton = showJeiSyncButton
                ? new SpriteButtonWidget(jeiSyncButtonX, jeiSyncButtonY,
                controlSize, controlSize, buttonSprites,
                Component.translatable(translationKeyBase + ".search.jei"),
                onJeiSync,
                jeiSyncRenderer)
                : null;

        return new StorageTopBarWidget(searchBar, clearSearchButton, sortTypeButton, sortDirectionButton, jeiSyncButton);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.searchBar);
        adder.accept(this.clearSearchButton);
        adder.accept(this.sortTypeButton);
        adder.accept(this.sortDirectionButton);
        if (this.jeiSyncButton != null) {
            adder.accept(this.jeiSyncButton);
        }
    }
}
