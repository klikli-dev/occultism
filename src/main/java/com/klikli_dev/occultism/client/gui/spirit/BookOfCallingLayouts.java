/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

final class BookOfCallingLayouts {
    private static final int GUI_WIDTH = 241;
    private static final int TOP_BAR_Y = -4;
    private static final int TOP_BAR_HEIGHT = 16;
    private static final int PANEL_LEFT = 3;
    private static final int PANEL_TOP = 9;
    private static final int PANEL_HORIZONTAL_MARGIN = 6;
    private static final int TITLE_Y = 0;
    private static final int TITLE_HEIGHT = 8;
    private static final int LABEL_AREA_LEFT = 0;
    private static final int LABEL_AREA_WIDTH = 66;
    private static final int LABEL_HEIGHT = 8;
    private static final int SELECTION_LEFT = 72;
    private static final int SELECTION_WIDTH = 120;
    private static final int SELECTION_HEIGHT = 18;
    private static final int CONFIRM_BUTTON_LEFT = 208;
    private static final int CONFIRM_BUTTON_SIZE = 18;
    private static final int SEPARATOR_LEFT = PANEL_LEFT;
    private static final int SEPARATOR_WIDTH = GUI_WIDTH - PANEL_HORIZONTAL_MARGIN;
    private static final int DIVIDER_X = 202;
    private static final int DIVIDER_WIDTH = 1;
    private static final int FIELD_BACKGROUND_INSET = 2;

    private BookOfCallingLayouts() {
    }

    public static LayoutSpec standard(int imageHeight) {
        return LayoutSpec.create(root -> {
            addFrame(root, imageHeight);
            root.group("content", content -> {
                addSelectionRow(content, "mode", 22);
                content.node("divider_horizontal").at(SEPARATOR_LEFT, 47).size(SEPARATOR_WIDTH, 1);
                content.node("divider_vertical").at(DIVIDER_X, 47).size(DIVIDER_WIDTH, 38);
                addSelectionRow(content, "work_area", 54);
                content.node("confirm_button").at(CONFIRM_BUTTON_LEFT, 54).size(CONFIRM_BUTTON_SIZE, CONFIRM_BUTTON_SIZE);
            });
        });
    }

    public static LayoutSpec managedMachine(int imageHeight) {
        return LayoutSpec.create(root -> {
            addFrame(root, imageHeight);
            root.group("content", content -> {
                addSelectionRow(content, "insert", 22);
                content.node("divider_horizontal_top").at(SEPARATOR_LEFT, 47).size(SEPARATOR_WIDTH, 1);
                addSelectionRow(content, "extract", 54);
                content.node("divider_horizontal_bottom").at(SEPARATOR_LEFT, 72).size(SEPARATOR_WIDTH, 1);
                content.node("divider_vertical").at(DIVIDER_X, 72).size(DIVIDER_WIDTH, 36);
                content.group("name", name -> {
                    name.node("label").at(LABEL_AREA_LEFT, 84).size(LABEL_AREA_WIDTH, LABEL_HEIGHT);
                    name.node("field_background").at(SELECTION_LEFT - FIELD_BACKGROUND_INSET, 79 - FIELD_BACKGROUND_INSET)
                            .size(SELECTION_WIDTH + FIELD_BACKGROUND_INSET * 2, SELECTION_HEIGHT + FIELD_BACKGROUND_INSET * 2);
                    name.node("field").at(SELECTION_LEFT, 79).size(SELECTION_WIDTH, SELECTION_HEIGHT);
                });
                content.node("confirm_button").at(CONFIRM_BUTTON_LEFT, 85).size(CONFIRM_BUTTON_SIZE, CONFIRM_BUTTON_SIZE);
            });
        });
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

    private static void addSelectionRow(com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder parent, String id, int selectionTop) {
        parent.group(id, row -> {
            row.node("label").at(LABEL_AREA_LEFT, selectionTop + 5).size(LABEL_AREA_WIDTH, LABEL_HEIGHT);
            row.node("selection").at(SELECTION_LEFT, selectionTop).size(SELECTION_WIDTH, SELECTION_HEIGHT);
        });
    }
}
