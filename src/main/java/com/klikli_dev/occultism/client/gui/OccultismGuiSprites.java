// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.gui.texture.GuiSprite;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiSprites {
    public static final GuiSprite STORAGE_CONTROLLER_SEARCH_FIELD = sprite("storage_controller/search_field", 96, 14);
    public static final GuiSprite STORAGE_CONTROLLER_ORDER_PANEL = sprite("storage_controller/order_panel", 42, 42);
    public static final GuiSprite STORAGE_CONTROLLER_ITEM_AREA_BACKGROUND = sprite("storage_controller/storage_controller_item_area_bg", 10, 10);

    public static final GuiSprite STORAGE_CONTROLLER_TAB_INVENTORY_ACTIVE = sprite("storage_controller/tab/inventory_active", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_INVENTORY_INACTIVE = sprite("storage_controller/tab/inventory_inactive", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_CRAFTING_ACTIVE = sprite("storage_controller/tab/crafting_active", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_CRAFTING_INACTIVE = sprite("storage_controller/tab/crafting_inactive", 24, 29);

    private OccultismGuiSprites() {
    }

    private static GuiSprite sprite(String path, int width, int height) {
        return new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID, path), width, height);
    }
}
