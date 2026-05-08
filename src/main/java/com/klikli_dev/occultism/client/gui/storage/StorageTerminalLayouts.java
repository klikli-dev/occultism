/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventorySection;

final class StorageTerminalLayouts {
    private static final PlayerInventorySection PLAYER_INVENTORY = PlayerInventorySection.standard();
    private static final int SLOT_SIZE = 18;
    private static final int GUI_WIDTH = 260;
    private static final int TOP_BAR_HEIGHT = 21;
    private static final int MAIN_PANEL_TOP = 12;
    private static final int ITEM_AREA_LEFT = 32;
    private static final int ITEM_AREA_TOP = TOP_BAR_HEIGHT + 3;
    private static final int SEARCH_BAR_LEFT = ITEM_AREA_LEFT + 1;
    private static final int SEARCH_BAR_TOP = 7;
    private static final int SEARCH_FIELD_LEFT = SEARCH_BAR_LEFT - 3;
    private static final int SEARCH_FIELD_TOP = SEARCH_BAR_TOP - 3;
    private static final int STORAGE_INFO_LABEL_LEFT = 186;
    private static final int CONTROL_BUTTON_TOP = SEARCH_BAR_TOP - 2;
    private static final int CONTROL_BUTTON_LEFT = SEARCH_BAR_LEFT + 98;
    private static final int CONTROL_BUTTON_SIZE = 12;
    private static final int INVENTORY_PANEL_TOP_OFFSET = 66;
    private static final int INVENTORY_PANEL_LEFT = 43;
    private static final int ORDER_AREA_OFFSET = 48;
    private static final int CRAFTING_GRID_LEFT = 37 + ORDER_AREA_OFFSET;
    private static final int CRAFTING_GRID_TOP = 4;
    private static final int CRAFTING_OUTPUT_LEFT = 133 + ORDER_AREA_OFFSET;
    private static final int CRAFTING_OUTPUT_TOP = CRAFTING_GRID_TOP + 18;
    private static final int CRAFTING_ARROW_LEFT = 103 + ORDER_AREA_OFFSET - 5;
    private static final int CRAFTING_ARROW_TOP = CRAFTING_OUTPUT_TOP + 1;
    private static final int ORDER_SLOT_LEFT = -10;
    private static final int ORDER_SLOT_TOP = -61;
    private static final int TAB_WIDTH = 34;
    private static final int TAB_HEIGHT = 29;
    private static final int TAB_LEFT_SHIFT = 5;
    private static final int TAB_HIDDEN_OVERLAP = 3;
    private static final int TAB_TOP_OFFSET = 0;

    private StorageTerminalLayouts() {
    }

    public static LayoutSpec create(int visibleRows) {
        return LayoutSpec.create(root -> {
            root.group("frame", frame -> {
                frame.group("top_bar", topBar -> {
                    int mainPanelWidth = mainPanelWidth();
                    int topBarLeft = (GUI_WIDTH - mainPanelWidth) / 2 - 3;
                    topBar.node("background").at(topBarLeft, 0).size(mainPanelWidth + 6, TOP_BAR_HEIGHT);
                    topBar.node("title").at(0, 5).size(GUI_WIDTH, 8);
                    topBar.group("search", search -> {
                        search.at(SEARCH_FIELD_LEFT, SEARCH_FIELD_TOP + 1);
                        search.node("background").at(0, 0).size(96, CONTROL_BUTTON_SIZE);
                        search.node("input").at(SEARCH_BAR_LEFT - SEARCH_FIELD_LEFT, SEARCH_BAR_TOP - SEARCH_FIELD_TOP)
                                .size(90, 9);
                    });
                    topBar.group("controls", controls -> {
                        controls.at(CONTROL_BUTTON_LEFT, CONTROL_BUTTON_TOP);
                        for (int index = 0; index < 4; index++) {
                            controls.node("button_" + index).at(index * (CONTROL_BUTTON_SIZE + 3), 0)
                                    .size(CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
                        }
                    });
                });

                frame.group("main", main -> {
                    main.at(0, MAIN_PANEL_TOP);
                    main.node("panel").at((GUI_WIDTH - mainPanelWidth()) / 2, 0).size(mainPanelWidth(), mainPanelHeight(visibleRows));
                    main.node("item_area_background").at(ITEM_AREA_LEFT - 1, ITEM_AREA_TOP - MAIN_PANEL_TOP - 1)
                            .size(itemAreaWidth(), itemAreaHeight(visibleRows));
                    main.group("item_area", itemArea -> {
                        itemArea.at(ITEM_AREA_LEFT, ITEM_AREA_TOP - MAIN_PANEL_TOP);
                        defineGrid(itemArea, visibleRows, visibleColumns());
                    });
                    main.group("tabs", tabs -> {
                        tabs.at(tabLeft(), ITEM_AREA_TOP + SLOT_SIZE * visibleRows - MAIN_PANEL_TOP);
                        tabs.node("inventory").at(0, 0).size(TAB_WIDTH, TAB_HEIGHT);
                        tabs.node("autocrafting").at(0, TAB_HEIGHT).size(TAB_WIDTH, TAB_HEIGHT);
                    });
                });

                frame.group("menu", menu -> {
                    int menuTop = ITEM_AREA_TOP + SLOT_SIZE * visibleRows;
                    menu.at(0, menuTop);
                    menu.group("player_inventory", inventory -> {
                        inventory.at(3 + ORDER_AREA_OFFSET, 71);
                        PLAYER_INVENTORY.define(inventory);
                    });
                    menu.node("crafting_arrow").at(CRAFTING_ARROW_LEFT, CRAFTING_ARROW_TOP);
                    menu.node("storage_space_label").at(STORAGE_INFO_LABEL_LEFT, 6);
                    menu.node("storage_types_label").at(CRAFTING_OUTPUT_LEFT + SLOT_SIZE / 2, 47);
                    menu.node("clear_recipe_button").at(93 + ORDER_AREA_OFFSET, CRAFTING_GRID_TOP - 1)
                            .size(CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
                    menu.group("crafting", crafting -> {
                        crafting.node("output").at(CRAFTING_OUTPUT_LEFT, CRAFTING_OUTPUT_TOP).size(SLOT_SIZE, SLOT_SIZE);
                        crafting.group("grid", grid -> {
                            grid.at(CRAFTING_GRID_LEFT, CRAFTING_GRID_TOP);
                            defineGrid(grid, 3, 3);
                        });
                    });
                    menu.group("order", order -> {
                        order.node("slot_background").at(ORDER_SLOT_LEFT - 5, ORDER_SLOT_TOP - 5).size(28, 28);
                        order.node("slot").at(ORDER_SLOT_LEFT, ORDER_SLOT_TOP).size(SLOT_SIZE, SLOT_SIZE);
                    });
                });
            });
        });
    }

    private static int visibleColumns() {
        return 11;
    }

    private static int itemAreaWidth() {
        return visibleColumns() * SLOT_SIZE;
    }

    private static int itemAreaHeight(int visibleRows) {
        return visibleRows * SLOT_SIZE;
    }

    private static int mainPanelWidth() {
        return visibleColumns() * SLOT_SIZE + 14;
    }

    private static int mainPanelHeight(int visibleRows) {
        return SLOT_SIZE * visibleRows + 75;
    }

    private static int tabLeft() {
        int mainPanelLeft = (GUI_WIDTH - mainPanelWidth()) / 2;
        return mainPanelLeft - (TAB_WIDTH - TAB_HIDDEN_OVERLAP) + TAB_LEFT_SHIFT;
    }

    private static void defineGrid(LayoutGroupBuilder group, int rows, int columns) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                group.node("slot_" + (row * columns + column)).at(column * SLOT_SIZE, row * SLOT_SIZE).size(SLOT_SIZE, SLOT_SIZE);
            }
        }
    }
}
