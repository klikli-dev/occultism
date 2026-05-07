// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.premade.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.api.style.BuiltinGuiParts;
import com.klikli_dev.codedefinedgui.api.style.GuiStyle;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleKey;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiStyles {
    private static final int BACKGROUND_TINT = 0xFF4B5563;
    private static final int TOP_BAR_TINT = 0xFF9C0393;
    private static final int AGE_BAR_TINT = 0xFFBB7DB8;
    private static final int SLOT_TINT = 0xFF697586;
    private static final int BUTTON_TINT = 0xFF5D6878;
    private static final int BUTTON_HOVER_TINT = 0xFF707C8D;
    private static final int FIELD_TINT = 0xFF596474;
    private static final int SELECTION_TINT = 0xFF667487;
    private static final int BLACK = 0xFF000000;

    public static final GuiStyleKey FILTER_LIST = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/list"));
    public static final GuiStyleKey FILTER_ATTRIBUTE = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/attribute"));
    public static final GuiStyleKey BOOK_OF_CALLING = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "book_of_calling/default"));
    public static final GuiStyleKey SATCHEL = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "satchel/default"));
    public static final GuiStyleKey SPIRIT = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit/default"));
    public static final GuiStyleKey SPIRIT_TRANSPORTER = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit/transporter"));
    public static final GuiStyleKey STORAGE_CONTROLLER = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "storage_controller/default"));

    private OccultismGuiStyles() {
    }

    private static GuiStyle.Builder filterBaseStyle() {
        return GuiStyle.builder()
                .set(BuiltinGuiParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(BUTTON_HOVER_TINT));
    }

    private static GuiStyle.Builder transporterBaseStyle() {
        return GuiStyle.builder()
                .set(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT));
    }

    private static GuiStyle.Builder spiritBaseStyle() {
        return GuiStyle.builder()
                .set(BuiltinGuiParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SPIRIT_PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_INVENTORY_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT));
    }

    private static GuiStyle.Builder storageControllerBaseStyle() {
        return GuiStyle.builder()
                .set(OccultismGuiParts.STORAGE_CONTROLLER_PLAYER_SLOT, GuiStyleProperties.SPRITE,
                        GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_STORAGE_SLOT, GuiStyleProperties.SPRITE,
                        GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_CRAFTING_SLOT, GuiStyleProperties.SPRITE,
                        GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_ORDER_SLOT, GuiStyleProperties.SPRITE,
                        GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_TOP_BAR, GuiStyleProperties.SPRITE,
                        GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, GuiStyleProperties.SPRITE,
                        GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.STORAGE_CONTROLLER_MAIN_PANEL, GuiStyleProperties.COLOR, BACKGROUND_TINT)
                .set(OccultismGuiParts.STORAGE_CONTROLLER_INVENTORY_PANEL, GuiStyleProperties.SPRITE,
                        GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT));
    }

    private static GuiStyle.Builder satchelBaseStyle() {
        return GuiStyle.builder()
                .set(OccultismGuiParts.SATCHEL_PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SATCHEL_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SATCHEL_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SATCHEL_PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SATCHEL_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.SATCHEL_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SATCHEL_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK);
    }

    private static GuiStyle.Builder bookOfCallingBaseStyle() {
        return GuiStyle.builder()
                .set(OccultismGuiParts.BOOK_OF_CALLING_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.BOOK_OF_CALLING_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.BOOK_OF_CALLING_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK)
                .set(OccultismGuiParts.BOOK_OF_CALLING_LABEL, GuiStyleProperties.TEXT_COLOR, 0xFFF3EBDE)
                .set(OccultismGuiParts.BOOK_OF_CALLING_FIELD, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(FIELD_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_SELECTION, GuiStyleProperties.SPRITE, GuiSprites.ATTRIBUTE_FILTER_SELECTION.tinted(SELECTION_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_CONFIRM_BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(BUTTON_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_CONFIRM_BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(BUTTON_TINT))
                .set(OccultismGuiParts.BOOK_OF_CALLING_CONFIRM_BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(BUTTON_HOVER_TINT));
    }

    public static void register() {
        GuiStyleRegistry.register(BOOK_OF_CALLING, bookOfCallingBaseStyle().build());
        GuiStyleRegistry.register(SATCHEL, satchelBaseStyle().build());

        GuiStyleRegistry.register(SPIRIT, spiritBaseStyle()
                .set(OccultismGuiParts.SPIRIT_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.SPIRIT_AGE_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(AGE_BAR_TINT))
                .set(OccultismGuiParts.SPIRIT_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK)
                .build());

        GuiStyleRegistry.register(FILTER_LIST, filterBaseStyle()
                .set(BuiltinFilterParts.LIST_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.LIST_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(BuiltinFilterParts.LIST_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.LIST_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.LIST_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK)
                .build());

        GuiStyleRegistry.register(FILTER_ATTRIBUTE, filterBaseStyle()
                .set(BuiltinFilterParts.ATTRIBUTE_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.ATTRIBUTE_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.ATTRIBUTE_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK)
                .set(BuiltinFilterParts.ATTRIBUTE_SELECTION, GuiStyleProperties.SPRITE, GuiSprites.ATTRIBUTE_FILTER_SELECTION.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_SUMMARY, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT).sized(24, 24))
                .build());

        GuiStyleRegistry.register(SPIRIT_TRANSPORTER, transporterBaseStyle()
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_TITLE, GuiStyleProperties.TEXT_COLOR, BLACK)
                .build());

        GuiStyleRegistry.register(STORAGE_CONTROLLER, storageControllerBaseStyle().build());
    }
}
