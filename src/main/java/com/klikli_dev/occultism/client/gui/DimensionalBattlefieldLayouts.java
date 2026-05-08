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
            frame.node("machine_panel").at(0, 0).size(176, 108);
            frame.group("progress", progress -> {
                progress.node("background").at(18, 80).size(35, 6);
                progress.node("fill").at(18, 81).size(35, 4);
            });
            frame.group("machine", machine -> {
                machine.group("output", output -> {
                    output.at(80, 16);
                    defineGrid(output, 5, 5);
                });
                machine.node("input_soul").at(27, 37).size(SLOT_SIZE, SLOT_SIZE);
                machine.node("input_fuel").at(40, 59).size(SLOT_SIZE, SLOT_SIZE);
                machine.node("input_weapon").at(14, 59).size(SLOT_SIZE, SLOT_SIZE);
            });
            frame.group("player_inventory", inventory -> {
                inventory.at(8, 121);
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
