/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.container.storage.layout;

public final class StorageMenuLayouts {
    private static final int ORDER_AREA_OFFSET = 48;
    private static final int CRAFTING_GRID_TOP = 4;
    private static final int CRAFTING_OUTPUT_TOP = CRAFTING_GRID_TOP + 18;
    private static final int ORDER_INPUT_SLOT_LEFT = -10;
    private static final int ORDER_INPUT_SLOT_TOP = -61;

    private static final StorageMenuLayout STANDARD = new StorageMenuLayout(
            3 + ORDER_AREA_OFFSET,
            71,
            3 + ORDER_AREA_OFFSET,
            129,
            37 + ORDER_AREA_OFFSET,
            CRAFTING_GRID_TOP,
            133 + ORDER_AREA_OFFSET,
            CRAFTING_OUTPUT_TOP,
            ORDER_INPUT_SLOT_LEFT,
            ORDER_INPUT_SLOT_TOP
    );

    private static final StorageMenuLayout REMOTE = new StorageMenuLayout(
            8 + ORDER_AREA_OFFSET,
            18 * 3 + 6,
            8 + ORDER_AREA_OFFSET,
            18 * 3 + 6 + 18 * 3 + 4,
            37 + ORDER_AREA_OFFSET,
            CRAFTING_GRID_TOP,
            133 + ORDER_AREA_OFFSET,
            CRAFTING_OUTPUT_TOP,
            ORDER_INPUT_SLOT_LEFT,
            ORDER_INPUT_SLOT_TOP
    );

    private StorageMenuLayouts() {
    }

    public static StorageMenuLayout layout(StorageMenuVariant variant) {
        return switch (variant) {
            case CONTROLLER, STABLE_WORMHOLE -> STANDARD;
            case REMOTE -> REMOTE;
        };
    }
}
