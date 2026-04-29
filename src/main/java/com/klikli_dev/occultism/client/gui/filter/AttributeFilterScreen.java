// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import com.klikli_dev.occultism.common.container.filter.AttributeFilterMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterScreen extends com.klikli_dev.codedefinedgui.gui.filter.AttributeFilterScreen<AttributeFilterMenu> {
    public AttributeFilterScreen(AttributeFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
