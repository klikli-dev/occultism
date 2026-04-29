// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.client.gui.filter;

import com.klikli_dev.occultism.common.container.filter.ListFilterMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ListFilterScreen extends com.klikli_dev.codedefinedgui.gui.filter.ListFilterScreen<ListFilterMenu> {
    public ListFilterScreen(ListFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
