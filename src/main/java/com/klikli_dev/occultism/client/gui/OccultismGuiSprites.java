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

    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_CLEAR = sprite("storage_controller/button/clear_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_CLEAR_HOVER = sprite("storage_controller/button/clear_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_AMOUNT = sprite("storage_controller/button/sort_amount_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_AMOUNT_HOVER = sprite("storage_controller/button/sort_amount_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_NAME = sprite("storage_controller/button/sort_name_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_NAME_HOVER = sprite("storage_controller/button/sort_name_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_MOD = sprite("storage_controller/button/sort_mod_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_MOD_HOVER = sprite("storage_controller/button/sort_mod_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_DIRECTION_DOWN = sprite("storage_controller/button/sort_direction_down_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_DIRECTION_DOWN_HOVER = sprite("storage_controller/button/sort_direction_down_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_DIRECTION_UP = sprite("storage_controller/button/sort_direction_up_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_SORT_DIRECTION_UP_HOVER = sprite("storage_controller/button/sort_direction_up_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_JEI_ON = sprite("storage_controller/button/jei_on_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_JEI_ON_HOVER = sprite("storage_controller/button/jei_on_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_JEI_OFF = sprite("storage_controller/button/jei_off_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_JEI_OFF_HOVER = sprite("storage_controller/button/jei_off_hover", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_ROWS = sprite("storage_controller/button/rows_normal", 12, 12);
    public static final GuiSprite STORAGE_CONTROLLER_BUTTON_ROWS_HOVER = sprite("storage_controller/button/rows_hover", 12, 12);

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
