/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

final class SpiritLayouts {
    private static final int GUI_WIDTH = 182;
    private static final int TOP_BAR_HEIGHT = 18;
    private static final int TITLE_Y = 6;
    private static final int TITLE_HEIGHT = 8;
    private static final int MAIN_TOP = 15;
    private static final int MAIN_PANEL_LEFT = 3;
    private static final int MAIN_PANEL_WIDTH = 176;
    private static final int MAIN_PANEL_HEIGHT = 63;
    private static final int NAME_LABEL_X = 11;
    private static final int NAME_LABEL_Y = 21;
    private static final int ENTITY_PREVIEW_X = 8;
    private static final int ENTITY_PREVIEW_Y = 29;
    private static final int ENTITY_PREVIEW_WIDTH = 50;
    private static final int ENTITY_PREVIEW_HEIGHT = 50;
    private static final int VERTICAL_SEPARATOR_X = 140;
    private static final int VERTICAL_SEPARATOR_Y = 18;
    private static final int VERTICAL_SEPARATOR_HEIGHT = 56;
    private static final int ENTITY_SLOT_X = 153;
    private static final int STANDARD_ENTITY_SLOT_Y = 39;
    private static final int TRANSPORTER_ENTITY_SLOT_Y = 27;
    private static final int FILTER_SLOT_Y = 51;
    private static final int AGE_BAR_Y = 74;
    private static final int AGE_BAR_HEIGHT = 16;
    private static final int PLAYER_INVENTORY_X = 11;
    private static final int PLAYER_INVENTORY_Y = 97;
    private static final int PLAYER_INVENTORY_BACKGROUND_INSET = 8;
    private static final int PLAYER_INVENTORY_BACKGROUND_WIDTH = 176;
    private static final int PLAYER_INVENTORY_BACKGROUND_HEIGHT = 90;
    private static final int PLAYER_INVENTORY_LABEL_Y = 5;
    private static final int INVENTORY_HOTBAR_Y = 58;
    private static final int SLOT_SIZE = 18;
    private static final int INVENTORY_COLUMNS = 9;
    private static final int INVENTORY_MAIN_ROWS = 3;
    private static final int AGED_INVENTORY_OFFSET = 13;

    private SpiritLayouts() {
    }

    public static LayoutSpec standard(boolean hasAgeBar) {
        int inventoryOffset = hasAgeBar ? AGED_INVENTORY_OFFSET : 0;
        return LayoutSpec.create(root -> {
            defineCommonFrame(root, inventoryOffset);
            root.group("frame", frame -> frame.group("main", main -> {
                main.at(0, MAIN_TOP);
                main.node("inventory_slot").at(ENTITY_SLOT_X, STANDARD_ENTITY_SLOT_Y - MAIN_TOP).size(SLOT_SIZE, SLOT_SIZE);
                if (hasAgeBar) {
                    main.node("age_bar").at(0, AGE_BAR_Y - MAIN_TOP).size(GUI_WIDTH, AGE_BAR_HEIGHT);
                }
            }));
        });
    }

    public static LayoutSpec transporter() {
        return LayoutSpec.create(root -> {
            defineCommonFrame(root, 0);
            root.group("frame", frame -> frame.group("main", main -> {
                main.at(0, MAIN_TOP);
                main.node("inventory_slot").at(ENTITY_SLOT_X, TRANSPORTER_ENTITY_SLOT_Y - MAIN_TOP).size(SLOT_SIZE, SLOT_SIZE);
                main.node("filter_slot").at(ENTITY_SLOT_X, FILTER_SLOT_Y - MAIN_TOP).size(SLOT_SIZE, SLOT_SIZE);
            }));
        });
    }

    private static void defineCommonFrame(LayoutGroupBuilder root, int inventoryOffset) {
        root.group("frame", frame -> {
            frame.group("top_bar", topBar -> {
                topBar.node("background").at(0, 0).size(GUI_WIDTH, TOP_BAR_HEIGHT);
                topBar.node("title").at(0, TITLE_Y).size(GUI_WIDTH, TITLE_HEIGHT);
            });
            frame.group("main", main -> {
                main.at(0, MAIN_TOP);
                main.node("panel").at(MAIN_PANEL_LEFT, 0).size(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
                main.node("name_label").at(NAME_LABEL_X, NAME_LABEL_Y - MAIN_TOP);
                main.node("entity_preview").at(ENTITY_PREVIEW_X, ENTITY_PREVIEW_Y - MAIN_TOP).size(ENTITY_PREVIEW_WIDTH, ENTITY_PREVIEW_HEIGHT);
                main.node("vertical_separator").at(VERTICAL_SEPARATOR_X, VERTICAL_SEPARATOR_Y - MAIN_TOP).size(1, VERTICAL_SEPARATOR_HEIGHT);
            });
            frame.group("player_inventory", inventory -> {
                inventory.at(PLAYER_INVENTORY_X, PLAYER_INVENTORY_Y + inventoryOffset);
                inventory.node("background").at(-PLAYER_INVENTORY_BACKGROUND_INSET, -PLAYER_INVENTORY_BACKGROUND_INSET)
                        .size(PLAYER_INVENTORY_BACKGROUND_WIDTH, PLAYER_INVENTORY_BACKGROUND_HEIGHT);
                inventory.node("label").at(0, PLAYER_INVENTORY_LABEL_Y);
                defineInventoryGrid(inventory.group("main", main -> main.at(0, 0)));
                defineHotbar(inventory.group("hotbar", hotbar -> hotbar.at(0, INVENTORY_HOTBAR_Y)));
            });
        });
    }

    private static LayoutGroupBuilder defineInventoryGrid(LayoutGroupBuilder group) {
        for (int row = 0; row < INVENTORY_MAIN_ROWS; row++) {
            for (int column = 0; column < INVENTORY_COLUMNS; column++) {
                group.node("slot_" + (row * INVENTORY_COLUMNS + column)).at(column * SLOT_SIZE, row * SLOT_SIZE).size(SLOT_SIZE, SLOT_SIZE);
            }
        }
        return group;
    }

    private static LayoutGroupBuilder defineHotbar(LayoutGroupBuilder group) {
        for (int column = 0; column < INVENTORY_COLUMNS; column++) {
            group.node("slot_" + column).at(column * SLOT_SIZE, 0).size(SLOT_SIZE, SLOT_SIZE);
        }
        return group;
    }
}
