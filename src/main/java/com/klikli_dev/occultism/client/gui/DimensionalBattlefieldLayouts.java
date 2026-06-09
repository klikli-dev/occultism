/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.api.layout.LayoutGroupBuilder;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.premade.filter.core.layout.inventory.PlayerInventorySection;

final class DimensionalBattlefieldLayouts {
    private static final PlayerInventorySection PLAYER_INVENTORY = PlayerInventorySection.standard();
    private static final int SLOT_SIZE = 18;

    private DimensionalBattlefieldLayouts() {
    }

    public static LayoutSpec create() {
        return LayoutSpec.create(root -> root.group("frame", frame -> {
            frame.group("top_bar", topBar -> {
                topBar.node("background").at(-3, 1).size(182, 18);
                topBar.node("title").at(0, 5).size(176, 8);
            });
            frame.node("machine_panel").at(0, 15).size(176, 108);
            frame.group("progress", progress -> {
                progress.node("background").at(18, 95).size(35, 6);
                progress.node("fill").at(18, 96).size(35, 4);
            });
            frame.group("machine", machine -> {
                machine.group("output", output -> {
                    output.at(80, 27);
                    defineGrid(output, 5, 5);
                });
                machine.node("input_soul").at(27, 52).size(SLOT_SIZE, SLOT_SIZE);
                machine.node("input_fuel").at(40, 74).size(SLOT_SIZE, SLOT_SIZE);
                machine.node("input_weapon").at(14, 74).size(SLOT_SIZE, SLOT_SIZE);
            });
            frame.group("player_inventory", inventory -> {
                inventory.at(8, 136);
                PLAYER_INVENTORY.define(inventory);
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
