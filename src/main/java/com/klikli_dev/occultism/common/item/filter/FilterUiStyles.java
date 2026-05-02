// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.common.item.filter;

import com.klikli_dev.codedefinedgui.gui.style.GuiStyleKey;
import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;

public final class FilterUiStyles {
    public static final GuiStyleKey OCCULTISM_LIST = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/list"));
    public static final GuiStyleKey OCCULTISM_ATTRIBUTE = GuiStyleKey.of(Identifier.fromNamespaceAndPath(Occultism.MODID, "filter/attribute"));

    private FilterUiStyles() {
    }
}
