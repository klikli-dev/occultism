/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;

final class DimensionalMineshaftLayouts {
    private static final int SLOT_SIZE = 18;

    private DimensionalMineshaftLayouts() {
    }

    public static LayoutSpec create() {
        return LayoutSpec.create(root -> root.group("frame", frame -> {
            frame.node("machine_panel").at(0, 0).size(176, 80);
            frame.group("progress", progress -> {
                progress.node("background").at(61, 40).size(19, 6);
                progress.node("fill").at(61, 41).size(19, 4);
            });
            frame.group("machine", machine -> {
                machine.group("output", output -> {
                    output.at(98, 17);
                    defineGrid(output, 3, 3);
                });
                machine.node("input").at(26, 35).size(SLOT_SIZE, SLOT_SIZE);
            });
            frame.group("player_inventory", inventory -> {
                inventory.node("background").at(0, 80).size(176, 86);
                inventory.group("main", main -> {
                    main.at(8, 84);
                    defineGrid(main, 9, 3);
                });
                inventory.group("hotbar", hotbar -> {
                    hotbar.at(8, 142);
                    defineGrid(hotbar, 9, 1);
                });
            });
        }));
    }

    private static void defineGrid(LayoutGroupBuilder group, int columns, int rows) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                group.node("slot_" + (row * columns + column)).at(column * SLOT_SIZE, row * SLOT_SIZE)
                        .size(SLOT_SIZE, SLOT_SIZE);
            }
        }
    }
}
