// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.api.style.GuiPartKey;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiParts {
    public static final GuiPartKey SATCHEL_PLAYER_SLOT = key("satchel/player_slot");
    public static final GuiPartKey SATCHEL_SLOT = key("satchel/slot");
    public static final GuiPartKey SATCHEL_PANEL = key("satchel/panel");
    public static final GuiPartKey SATCHEL_PLAYER_INVENTORY_BACKGROUND = key("satchel/player_inventory_background");
    public static final GuiPartKey SATCHEL_TOP_BAR = key("satchel/top_bar");
    public static final GuiPartKey SATCHEL_HORIZONTAL_SEPARATOR = key("satchel/horizontal_separator");
    public static final GuiPartKey SATCHEL_TITLE = key("satchel/title");
    public static final GuiPartKey SPIRIT_PLAYER_SLOT = key("spirit/player_slot");
    public static final GuiPartKey SPIRIT_PLAYER_INVENTORY_BACKGROUND = key("spirit/player_inventory_background");
    public static final GuiPartKey SPIRIT_INVENTORY_SLOT = key("spirit/inventory_slot");
    public static final GuiPartKey SPIRIT_PANEL = key("spirit/panel");
    public static final GuiPartKey SPIRIT_TOP_BAR = key("spirit/top_bar");
    public static final GuiPartKey SPIRIT_AGE_BAR = key("spirit/age_bar");
    public static final GuiPartKey SPIRIT_HORIZONTAL_SEPARATOR = key("spirit/horizontal_separator");
    public static final GuiPartKey SPIRIT_VERTICAL_SEPARATOR = key("spirit/vertical_separator");
    public static final GuiPartKey SPIRIT_TITLE = key("spirit/title");
    public static final GuiPartKey BOOK_OF_CALLING_PANEL = key("book_of_calling/panel");
    public static final GuiPartKey BOOK_OF_CALLING_TOP_BAR = key("book_of_calling/top_bar");
    public static final GuiPartKey BOOK_OF_CALLING_HORIZONTAL_SEPARATOR = key("book_of_calling/horizontal_separator");
    public static final GuiPartKey BOOK_OF_CALLING_VERTICAL_SEPARATOR = key("book_of_calling/vertical_separator");
    public static final GuiPartKey BOOK_OF_CALLING_TITLE = key("book_of_calling/title");
    public static final GuiPartKey BOOK_OF_CALLING_LABEL = key("book_of_calling/label");
    public static final GuiPartKey BOOK_OF_CALLING_FIELD = key("book_of_calling/field");
    public static final GuiPartKey BOOK_OF_CALLING_SELECTION = key("book_of_calling/selection");
    public static final GuiPartKey BOOK_OF_CALLING_CONFIRM_BUTTON = key("book_of_calling/confirm_button");
    public static final GuiPartKey FILTER_PLAYER_SLOT = key("filter/player_slot");
    public static final GuiPartKey FILTER_PLAYER_INVENTORY_BACKGROUND = key("filter/player_inventory_background");
    public static final GuiPartKey FILTER_FILTER_SLOT = key("filter/filter_slot");
    public static final GuiPartKey FILTER_BUTTON = key("filter/button");
    public static final GuiPartKey FILTER_LIST_PANEL = key("filter/list/panel");
    public static final GuiPartKey FILTER_LIST_TOP_BAR = key("filter/list/top_bar");
    public static final GuiPartKey FILTER_LIST_HORIZONTAL_SEPARATOR = key("filter/list/horizontal_separator");
    public static final GuiPartKey FILTER_LIST_VERTICAL_SEPARATOR = key("filter/list/vertical_separator");
    public static final GuiPartKey FILTER_LIST_TITLE = key("filter/list/title");
    public static final GuiPartKey FILTER_ATTRIBUTE_PANEL = key("filter/attribute/panel");
    public static final GuiPartKey FILTER_ATTRIBUTE_TOP_BAR = key("filter/attribute/top_bar");
    public static final GuiPartKey FILTER_ATTRIBUTE_HORIZONTAL_SEPARATOR = key("filter/attribute/horizontal_separator");
    public static final GuiPartKey FILTER_ATTRIBUTE_VERTICAL_SEPARATOR = key("filter/attribute/vertical_separator");
    public static final GuiPartKey FILTER_ATTRIBUTE_TITLE = key("filter/attribute/title");
    public static final GuiPartKey FILTER_ATTRIBUTE_SELECTION = key("filter/attribute/selection");
    public static final GuiPartKey FILTER_ATTRIBUTE_SUMMARY = key("filter/attribute/summary");
    public static final GuiPartKey SPIRIT_TRANSPORTER_PLAYER_SLOT = key("spirit/transporter/player_slot");
    public static final GuiPartKey SPIRIT_TRANSPORTER_PLAYER_INVENTORY_BACKGROUND = key("spirit/transporter/player_inventory_background");
    public static final GuiPartKey SPIRIT_TRANSPORTER_FILTER_SLOT = key("spirit/transporter/filter_slot");
    public static final GuiPartKey SPIRIT_TRANSPORTER_PANEL = key("spirit/transporter/panel");
    public static final GuiPartKey SPIRIT_TRANSPORTER_TOP_BAR = key("spirit/transporter/top_bar");
    public static final GuiPartKey SPIRIT_TRANSPORTER_HORIZONTAL_SEPARATOR = key("spirit/transporter/horizontal_separator");
    public static final GuiPartKey SPIRIT_TRANSPORTER_VERTICAL_SEPARATOR = key("spirit/transporter/vertical_separator");
    public static final GuiPartKey SPIRIT_TRANSPORTER_TITLE = key("spirit/transporter/title");
    public static final GuiPartKey STORAGE_CONTROLLER_PLAYER_SLOT = key("storage_controller/player_slot");
    public static final GuiPartKey STORAGE_CONTROLLER_STORAGE_SLOT = key("storage_controller/storage_slot");
    public static final GuiPartKey STORAGE_CONTROLLER_CRAFTING_SLOT = key("storage_controller/crafting_slot");
    public static final GuiPartKey STORAGE_CONTROLLER_ORDER_SLOT = key("storage_controller/order_slot");
    public static final GuiPartKey STORAGE_CONTROLLER_TOP_BAR = key("storage_controller/top_bar");
    public static final GuiPartKey STORAGE_CONTROLLER_MAIN_PANEL = key("storage_controller/main_panel");
    public static final GuiPartKey STORAGE_CONTROLLER_INVENTORY_PANEL = key("storage_controller/inventory_panel");
    public static final GuiPartKey STORAGE_CONTROLLER_HORIZONTAL_SEPARATOR = key("storage_controller/horizontal_separator");
    public static final GuiPartKey DIMENSIONAL_MACHINE_PLAYER_SLOT = key("dimensional_machine/player_slot");
    public static final GuiPartKey DIMENSIONAL_MACHINE_SLOT = key("dimensional_machine/slot");
    public static final GuiPartKey DIMENSIONAL_MACHINE_PANEL = key("dimensional_machine/panel");
    public static final GuiPartKey DIMENSIONAL_MACHINE_PLAYER_INVENTORY_BACKGROUND = key("dimensional_machine/player_inventory_background");
    public static final GuiPartKey DIMENSIONAL_MACHINE_PROGRESS_BACKGROUND = key("dimensional_machine/progress_background");

    private OccultismGuiParts() {
    }

    private static GuiPartKey key(String path) {
        return GuiPartKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, path));
    }
}
