// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.common.container.filter;

import com.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;

class OccultismAttributeFilterMenuBase extends com.klikli_dev.codedefinedgui.filter.attribute.AttributeFilterMenu {
    protected OccultismAttributeFilterMenuBase(int containerId, Inventory inventory, InteractionHand hand) {
        super(OccultismContainers.ATTRIBUTE_FILTER.get(), containerId, inventory, hand);
    }
}
