// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import com.klikli_dev.codedefinedgui.filter.core.layout.BuiltinFilterParts;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.occultism.common.item.filter.FilterUiStyles;

public final class FilterStyles {
    private static final int BACKGROUND_TINT = 0xFF4B5563;
    private static final int TOP_BAR_TINT = 0xFF9C0393;
    private static final int SLOT_TINT = 0xFF697586;
    private static final int BUTTON_TINT = 0xFF5D6878;
    private static final int BUTTON_HOVER_TINT = 0xFF707C8D;
    private static final int BLACK = 0xFF000000;

    private FilterStyles() {
    }

    public static void register() {
        GuiStyleRegistry.register(FilterUiStyles.OCCULTISM_LIST, GuiStyle.builder()
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(BUTTON_HOVER_TINT))
                .set(BuiltinFilterParts.LIST_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.LIST_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(BuiltinFilterParts.LIST_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.LIST_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.LIST_TITLE, GuiStyleProperties.TEXT_COLOR, 0x303030)
                .build());

        GuiStyleRegistry.register(FilterUiStyles.OCCULTISM_ATTRIBUTE, GuiStyle.builder()
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(BuiltinFilterParts.PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(BuiltinFilterParts.PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(BuiltinFilterParts.FILTER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(BUTTON_TINT))
                .set(BuiltinFilterParts.BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(BUTTON_HOVER_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.ATTRIBUTE_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(BuiltinFilterParts.ATTRIBUTE_TITLE, GuiStyleProperties.TEXT_COLOR, 0x592424)
                .set(BuiltinFilterParts.ATTRIBUTE_SELECTION, GuiStyleProperties.SPRITE, GuiSprites.ATTRIBUTE_FILTER_SELECTION.tinted(SLOT_TINT))
                .set(BuiltinFilterParts.ATTRIBUTE_SUMMARY, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT).sized(24, 24))
                .build());
    }
}
