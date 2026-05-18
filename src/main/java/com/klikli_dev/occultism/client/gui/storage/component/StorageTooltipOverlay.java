/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.google.common.collect.Lists;
import com.klikli_dev.occultism.api.common.data.StorageControllerGuiMode;
import com.klikli_dev.occultism.integration.jei.JeiSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class StorageTooltipOverlay {
    private final String translationKeyBase;
    private final int topControlTooltipOffsetY;

    public StorageTooltipOverlay(String translationKeyBase, int topControlTooltipOffsetY) {
        this.translationKeyBase = translationKeyBase;
        this.topControlTooltipOffsetY = topControlTooltipOffsetY;
    }

    public void drawChromeTooltips(GuiGraphicsExtractor guiGraphics, Font font, int mouseX, int mouseY, int screenHeight,
                                   StorageControllerGuiMode mode, boolean searchBarHovered,
                                   AbstractWidget clearSearchButton, AbstractWidget clearRecipeButton,
                                   AbstractWidget sortTypeButton, String sortTypeSerializedName,
                                   AbstractWidget sortDirectionButton, String sortDirectionSerializedName,
                                   AbstractWidget rowsCountButton, AbstractWidget jeiSyncButton, boolean orderSlotHovered,
                                   AbstractWidget inventoryModeButton, AbstractWidget autocraftingModeButton,
                                   boolean spaceTextHovered, long usedTotalItemCount, long maxTotalItemCount,
                                   boolean typesTextHovered, int usedItemTypes, int maxItemTypes) {
        int topControlTooltipY = this.topControlTooltipY(screenHeight, mouseY);

        if (searchBarHovered) {
            List<Component> tooltip = new ArrayList<>();
            if (!Minecraft.getInstance().hasShiftDown()) {
                tooltip.add(Component.translatable(this.translationKeyBase + ".shift"));
            } else {
                switch (mode) {
                    case INVENTORY -> {
                        tooltip.add(Component.translatable(this.translationKeyBase + ".search.tooltip@"));
                        tooltip.add(Component.translatable(this.translationKeyBase + ".search.tooltip#"));
                        tooltip.add(Component.translatable(this.translationKeyBase + ".search.tooltip$"));
                    }
                    case AUTOCRAFTING -> tooltip.add(Component.translatable(this.translationKeyBase + ".search.machines.tooltip@"));
                }
                tooltip.add(Component.translatable(this.translationKeyBase + ".search.tooltip_rightclick"));
            }
            guiGraphics.setComponentTooltipForNextFrame(font, tooltip, mouseX, topControlTooltipY);
        }
        if (this.isHovered(clearSearchButton, mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(font,
                    Lists.newArrayList(Component.translatable(this.translationKeyBase + ".search.tooltip_clear")),
                    mouseX, topControlTooltipY);
        }
        if (this.isHovered(clearRecipeButton, mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(font,
                    Lists.newArrayList(Component.translatable(this.translationKeyBase + ".crafting.tooltip_clear")),
                    mouseX, mouseY);
        }
        if (this.isHovered(sortTypeButton, mouseX, mouseY)) {
            String translationKey = switch (mode) {
                case INVENTORY -> this.translationKeyBase + ".search.tooltip_sort_type_" + sortTypeSerializedName;
                case AUTOCRAFTING -> this.translationKeyBase + ".search.machines.tooltip_sort_type_" + sortTypeSerializedName;
            };
            guiGraphics.setTooltipForNextFrame(font, Component.translatable(translationKey), mouseX, topControlTooltipY);
        }
        if (this.isHovered(sortDirectionButton, mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(font,
                    Component.translatable(this.translationKeyBase + ".search.tooltip_sort_direction_" + sortDirectionSerializedName),
                    mouseX, topControlTooltipY);
        }
        if (this.isHovered(rowsCountButton, mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(font,
                    Component.translatable(this.translationKeyBase + ".display.rows"),
                    mouseX, topControlTooltipY);
        }
        if (this.isHovered(jeiSyncButton, mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(font,
                    Component.translatable(this.translationKeyBase + ".search.tooltip_jei_" +
                            (JeiSettings.isJeiSearchSynced() ? "on" : "off")),
                    mouseX, topControlTooltipY);
        }
        if (orderSlotHovered) {
            guiGraphics.setComponentTooltipForNextFrame(font,
                    List.of(Component.translatable(this.translationKeyBase + ".order_slot.tooltip")), mouseX, mouseY);
        }
        if (this.isHovered(inventoryModeButton, mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(font,
                    List.of(Component.translatable(this.translationKeyBase + ".mode.inventory.tooltip")), mouseX, mouseY);
        }
        if (this.isHovered(autocraftingModeButton, mouseX, mouseY)) {
            guiGraphics.setComponentTooltipForNextFrame(font,
                    List.of(Component.translatable(this.translationKeyBase + ".mode.autocrafting.tooltip")), mouseX, mouseY);
        }
        if (spaceTextHovered) {
            guiGraphics.setTooltipForNextFrame(font,
                    Component.literal(usedTotalItemCount + " / " + maxTotalItemCount), mouseX, mouseY);
        }
        if (typesTextHovered) {
            guiGraphics.setTooltipForNextFrame(font,
                    Component.literal(usedItemTypes + " / " + maxItemTypes), mouseX, mouseY);
        }
    }

    private int topControlTooltipY(int screenHeight, int mouseY) {
        return Math.min(screenHeight - 8, mouseY + this.topControlTooltipOffsetY);
    }

    private boolean isHovered(AbstractWidget widget, int mouseX, int mouseY) {
        return widget != null && widget.isMouseOver(mouseX, mouseY);
    }
}
