// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui;

import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class OccultismGuiStyles {
    public static final GuiStyleKey FILTER_LIST = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/list"));
    public static final GuiStyleKey FILTER_ATTRIBUTE = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/attribute"));
    public static final GuiStyleKey SPIRIT_TRANSPORTER = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "spirit/transporter"));

    private OccultismGuiStyles() {
    }
}
