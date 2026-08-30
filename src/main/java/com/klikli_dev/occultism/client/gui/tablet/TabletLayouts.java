/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.tablet;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

final class TabletLayouts {private static final int GUI_WIDTH = 241;

    private static final int TOP_BAR_Y = -4;
    private static final int TOP_BAR_HEIGHT = 16;
    private static final int PANEL_LEFT = 3;
    private static final int PANEL_TOP = 9;
    private static final int PANEL_HORIZONTAL_MARGIN = 6;
    private static final int TITLE_Y = 1;
    private static final int TITLE_HEIGHT = 8;
    private static final int LABEL_AREA_LEFT = 0;
    private static final int LABEL_AREA_WIDTH = 66;
    private static final int LABEL_HEIGHT = 8;
    private static final int SLOT_SIZE = 18;
    private static final int INVENTORY_MAIN_ROWS = 3;
    private static final int INVENTORY_COLUMNS = 9;
    private static final int INVENTORY_HOTBAR_Y = 58;
    private static final int CONFIRM_BUTTON_SIZE = 18;
    private static final int SELECTION_WIDTH = 120;
    private static final int SELECTION_HEIGHT = 18;

    private TabletLayouts() {
    }

    public static LayoutSpec familiar(int imageHeight) {
        return LayoutSpec.create(root -> {
            addFrame(root, imageHeight);
            root.node("content.familiar_list").at(9, 16);
            root.node("content.familiar_preview").at(190, 16).size(48, 48);
            root.node("content.familiar_name").at(40, 16).size(GUI_WIDTH, TOP_BAR_HEIGHT);
            root.node("config.status").at(40, 36).size(SELECTION_WIDTH, SELECTION_HEIGHT);
            root.node("config.mob_effect").at(40, 52).size(SELECTION_WIDTH, SELECTION_HEIGHT);
            root.node("config.effect_level").at(40, 72).size(SELECTION_WIDTH, SELECTION_HEIGHT);
            root.node("confirm_button").size(CONFIRM_BUTTON_SIZE, CONFIRM_BUTTON_SIZE);
            });
    }

    public static LayoutSpec teleport() {
        return LayoutSpec.create(root -> {
            root.group("frame", frame -> {
                frame.node("background").at(3, -20).size(176, 176);
                frame.node("panel").at(11, -16).size(160, 160);
                definePlayerInventory(frame, 3, 158, 176, 90, 11, 171);
            });

            root.group("content", content -> content.group("tablet", tablet -> {
                tablet.at(11, 23);
                defineCircle(tablet, 18*4, 8, 18*4, 18*2);
            }));
        });
    }

    private static void definePlayerInventory(LayoutGroupBuilder frame, int backgroundX, int backgroundY, int backgroundWidth,
                                              int backgroundHeight, int labelX, int labelY) {
        frame.group("player_inventory", inventory -> {
            inventory.node("background").at(backgroundX, backgroundY).size(backgroundWidth, backgroundHeight);
            inventory.node("label").at(labelX, labelY);
            inventory.group("main", main -> {
                main.at(labelX, labelY - 5);
                defineGrid(main, INVENTORY_COLUMNS, INVENTORY_MAIN_ROWS);
            });
            inventory.group("hotbar", hotbar -> {
                hotbar.at(labelX, labelY - 5 + INVENTORY_HOTBAR_Y);
                defineGrid(hotbar, INVENTORY_COLUMNS, 1);
            });
        });
    }

    private static void defineGrid(LayoutGroupBuilder group, int columns, int rows) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                group.node("slot_" + (row * columns + column)).at(column * SLOT_SIZE, row * SLOT_SIZE).size(SLOT_SIZE, SLOT_SIZE);
            }
        }
    }

    private static void defineCircle(LayoutGroupBuilder group, int radius, int points, int centerX, int centerY) {
        group.node("slot_0").at(centerX, centerY).size(SLOT_SIZE, SLOT_SIZE);
        for (int i = 1; i <= points; i++) {
            double angle = 2 * Math.PI * (i-1) / points - Math.PI/2;
            int x = centerX + (int) Math.round(radius * Math.cos(angle));
            int y = centerY + (int) Math.round(radius * Math.sin(angle));

            group.node("slot_" + (i)).at(x, y).size(SLOT_SIZE, SLOT_SIZE);
        }
    }

    private static void addFrame(com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder root, int imageHeight) {
        root.group("frame", frame -> {
            frame.group("top_bar", topBar -> {
                topBar.node("background").at(0, TOP_BAR_Y).size(GUI_WIDTH, TOP_BAR_HEIGHT);
                topBar.node("title").at(0, TITLE_Y).size(GUI_WIDTH, TITLE_HEIGHT);
            });
            frame.node("panel").at(PANEL_LEFT, PANEL_TOP).size(GUI_WIDTH - PANEL_HORIZONTAL_MARGIN, imageHeight - PANEL_TOP);
        });
    }
}
