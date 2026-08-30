// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.api.texture.GuiSprite;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiSprites {
    public static final GuiSprite CRAFTING_PROGRESS_BAR_BACKGROUND = sprite("crafting/crafting_progress_bar", 19, 6);
    public static final GuiSprite OTHERWORLD_BUTCHER_PROGRESS_FILL = sprite("crafting/otherworld_butcher_progress_fill", 35, 4);
    public static final GuiSprite OTHERWORLD_BUTCHER_SOUL_SLOT_HINT = sprite("crafting/otherworld_butcher_soul_slot_hint", 16, 16);
    public static final GuiSprite OTHERWORLD_BUTCHER_WEAPON_SLOT_HINT = sprite("crafting/otherworld_butcher_weapon_slot_hint", 16, 16);
    public static final GuiSprite OTHERWORLD_BUTCHER_FUEL_SLOT_HINT = sprite("crafting/otherworld_butcher_fuel_slot_hint", 16, 16);
    public static final GuiSprite OTHERWORLD_MINER_PROGRESS_FILL = sprite("crafting/otherworld_miner_progress_fill", 19, 4);
    public static final GuiSprite OTHERWORLD_MINER_INPUT_SLOT_HINT = sprite("crafting/otherworld_miner_input_slot_hint", 16, 16);
    public static final GuiSprite JEI_EYE = sprite("jei/eye", 16, 16);
    public static final GuiSprite JEI_GOLDEN_EYE = sprite("jei/golden_eye", 16, 16);

    public static final GuiSprite STORAGE_CONTROLLER_ANVIL_IMPACT = sprite("storage_controller/anvil_impact", 22, 22);
    public static final GuiSprite STORAGE_CONTROLLER_SEARCH_FIELD = sprite("storage_controller/search_field", 96, 14);
    public static final GuiSprite STORAGE_CONTROLLER_ORDER_PANEL = sprite("storage_controller/order_panel", 42, 42);
    public static final GuiSprite STORAGE_CONTROLLER_ITEM_AREA_BACKGROUND = sprite("storage_controller/storage_controller_item_area_bg", 10, 10);

    public static final GuiSprite STORAGE_CONTROLLER_TAB_INVENTORY_ACTIVE = sprite("storage_controller/tab/inventory_active", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_INVENTORY_INACTIVE = sprite("storage_controller/tab/inventory_inactive", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_CRAFTING_ACTIVE = sprite("storage_controller/tab/crafting_active", 24, 29);
    public static final GuiSprite STORAGE_CONTROLLER_TAB_CRAFTING_INACTIVE = sprite("storage_controller/tab/crafting_inactive", 24, 29);

    public static final GuiSprite TELEPORT_TABLET_BACKGROUND = sprite("tablet/teleport/background", 320, 320);

    private OccultismGuiSprites() {
    }

    private static GuiSprite sprite(String path, int width, int height) {
        return new GuiSprite(Identifier.fromNamespaceAndPath(Occultism.MODID, path), width, height);
    }
}
