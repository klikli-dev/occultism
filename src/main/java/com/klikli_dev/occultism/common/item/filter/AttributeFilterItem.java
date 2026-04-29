// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.common.item.filter;

import com.klikli_dev.occultism.common.container.filter.AttributeFilterMenu;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class AttributeFilterItem extends com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterItem {
    public AttributeFilterItem(Properties properties) {
        super(properties);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory, InteractionHand hand) {
        return new AttributeFilterMenu(containerId, inventory, hand);
    }
}
