// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.gui.style.GuiStyle;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleProperties;
import com.klikli_dev.codedefinedgui.gui.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.gui.texture.GuiSprites;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiStyles {
    private static final int BACKGROUND_TINT = 0xFF4B5563;
    private static final int TOP_BAR_TINT = 0xFF9C0393;
    private static final int SLOT_TINT = 0xFF697586;
    private static final int BUTTON_TINT = 0xFF5D6878;
    private static final int BUTTON_HOVER_TINT = 0xFF707C8D;
    private static final int BLACK = 0xFF000000;

    public static final GuiStyleKey FILTER_LIST = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/list"));
    public static final GuiStyleKey FILTER_ATTRIBUTE = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/attribute"));
    public static final GuiStyleKey SPIRIT_TRANSPORTER = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit/transporter"));

    private OccultismGuiStyles() {
    }

    private static GuiStyle.Builder filterBaseStyle() {
        return GuiStyle.builder()
                .set(OccultismGuiParts.FILTER_PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.FILTER_PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(OccultismGuiParts.FILTER_PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(OccultismGuiParts.FILTER_PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.FILTER_FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.FILTER_FILTER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(OccultismGuiParts.FILTER_FILTER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(OccultismGuiParts.FILTER_BUTTON, GuiStyleProperties.SPRITE, GuiSprites.FILTER_BUTTON.tinted(BUTTON_TINT))
                .set(OccultismGuiParts.FILTER_BUTTON, GuiStyleProperties.PRESSED_SPRITE, GuiSprites.FILTER_BUTTON_DOWN.tinted(BUTTON_TINT))
                .set(OccultismGuiParts.FILTER_BUTTON, GuiStyleProperties.HOVER_SPRITE, GuiSprites.FILTER_BUTTON_HOVER.tinted(BUTTON_HOVER_TINT));
    }

    private static GuiStyle.Builder transporterBaseStyle() {
        return GuiStyle.builder()
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_SLOT, GuiStyleProperties.OFFSET_Y, 1)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PLAYER_INVENTORY_BACKGROUND, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT, GuiStyleProperties.OFFSET_X, 1)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_FILTER_SLOT, GuiStyleProperties.OFFSET_Y, 1);
    }

    public static void register() {
        GuiStyleRegistry.register(FILTER_LIST, filterBaseStyle()
                .set(OccultismGuiParts.FILTER_LIST_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.FILTER_LIST_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.FILTER_LIST_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.FILTER_LIST_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.FILTER_LIST_TITLE, GuiStyleProperties.TEXT_COLOR, 0x303030)
                .build());

        GuiStyleRegistry.register(FILTER_ATTRIBUTE, filterBaseStyle()
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_TITLE, GuiStyleProperties.TEXT_COLOR, 0x592424)
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_SELECTION, GuiStyleProperties.SPRITE, GuiSprites.ATTRIBUTE_FILTER_SELECTION.tinted(SLOT_TINT))
                .set(OccultismGuiParts.FILTER_ATTRIBUTE_SUMMARY, GuiStyleProperties.SPRITE, GuiSprites.INVENTORY_SLOT.tinted(SLOT_TINT).sized(24, 24))
                .build());

        GuiStyleRegistry.register(SPIRIT_TRANSPORTER, transporterBaseStyle()
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_PANEL, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(BACKGROUND_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_TOP_BAR, GuiStyleProperties.SPRITE, GuiSprites.GUI_BACKGROUND.tinted(TOP_BAR_TINT))
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_HORIZONTAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_VERTICAL_SEPARATOR, GuiStyleProperties.COLOR, BLACK)
                .set(OccultismGuiParts.SPIRIT_TRANSPORTER_TITLE, GuiStyleProperties.TEXT_COLOR, 0x303030)
                .build());
    }
}
