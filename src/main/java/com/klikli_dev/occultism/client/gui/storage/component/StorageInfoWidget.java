/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.component;

import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public record StorageInfoWidget(
        LabelWidget storageSpaceLabel,
        LabelWidget storageTypesLabel) {

    private static final int HOVER_WIDTH = 64;
    private static final int HOVER_X_OFFSET = 32;
    private static final int HOVER_Y_OFFSET = 2;

    public static StorageInfoWidget create(int storageSpaceX,
                                           int storageSpaceY,
                                           Component storageSpaceText,
                                           int storageTypesX,
                                           int storageTypesY,
                                           Component storageTypesText) {
        LabelWidget storageSpaceLabel = new LabelWidget(storageSpaceX, storageSpaceY, true, -1, 2, 0xFFFFFF);
        storageSpaceLabel.addLine(storageSpaceText);

        LabelWidget storageTypesLabel = new LabelWidget(storageTypesX, storageTypesY, true, -1, 2, 0xFFFFFF);
        storageTypesLabel.addLine(storageTypesText);

        return new StorageInfoWidget(storageSpaceLabel, storageTypesLabel);
    }

    public void addTo(Consumer<AbstractWidget> adder) {
        adder.accept(this.storageSpaceLabel);
        adder.accept(this.storageTypesLabel);
    }

    public boolean isStorageSpaceTextHovered(HoverChecker hoverChecker,
                                             int leftPos,
                                             int topPos,
                                             int lineHeight,
                                             double mouseX,
                                             double mouseY) {
        return this.isHovered(this.storageSpaceLabel, hoverChecker, leftPos, topPos, lineHeight, mouseX, mouseY);
    }

    public boolean isStorageTypesTextHovered(HoverChecker hoverChecker,
                                             int leftPos,
                                             int topPos,
                                             int lineHeight,
                                             double mouseX,
                                             double mouseY) {
        return this.isHovered(this.storageTypesLabel, hoverChecker, leftPos, topPos, lineHeight, mouseX, mouseY);
    }

    private boolean isHovered(LabelWidget label,
                              HoverChecker hoverChecker,
                              int leftPos,
                              int topPos,
                              int lineHeight,
                              double mouseX,
                              double mouseY) {
        return label != null && hoverChecker.isHovering(
                label.getX() - leftPos - HOVER_X_OFFSET,
                label.getY() - topPos - HOVER_Y_OFFSET,
                HOVER_WIDTH,
                lineHeight + HOVER_Y_OFFSET,
                mouseX,
                mouseY
        );
    }

    @FunctionalInterface
    public interface HoverChecker {
        boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight, double mouseX, double mouseY);
    }
}
