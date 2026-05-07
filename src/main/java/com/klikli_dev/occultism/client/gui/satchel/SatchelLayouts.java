/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.satchel;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

final class SatchelLayouts {
    private static final int SLOT_SIZE = 18;
    private static final int INVENTORY_MAIN_ROWS = 3;
    private static final int INVENTORY_COLUMNS = 9;
    private static final int INVENTORY_HOTBAR_Y = 58;
    private static final int TITLE_HEIGHT = 8;
    private static final int TOP_BAR_HEIGHT = 16;

    private SatchelLayouts() {
    }

    public static LayoutSpec storage() {
        return LayoutSpec.create(root -> {
            root.group("frame", frame -> {
                frame.group("top_bar", topBar -> {
                    topBar.node("background").at(0, 4).size(320, TOP_BAR_HEIGHT);
                    topBar.node("title").at(0, 9).size(320, TITLE_HEIGHT);
                });
                frame.node("panel").at(3, 16).size(315, 138);
                definePlayerInventory(frame, 69, 158, 176, 90, 77, 171);
            });

            root.group("content", content -> defineGrid(content.group("satchel", satchel -> satchel.at(8, 23)), 17, 7));
        });
    }

    public static LayoutSpec ritual() {
        return LayoutSpec.create(root -> {
            root.group("frame", frame -> {
                frame.group("top_bar", topBar -> {
                    topBar.node("background").at(0, -1).size(182, TOP_BAR_HEIGHT);
                    topBar.node("title").at(0, 9).size(182, TITLE_HEIGHT);
                });
                frame.node("panel").at(3, 9).size(176, 88);
                definePlayerInventory(frame, 3, 103, 176, 90, 11, 116);
            });

            root.group("content", content -> defineGrid(content.group("satchel", satchel -> satchel.at(11, 19)), 9, 4));
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
}
