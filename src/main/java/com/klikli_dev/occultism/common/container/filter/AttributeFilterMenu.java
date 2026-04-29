// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.common.container.filter;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;

public class AttributeFilterMenu extends OccultismAttributeFilterMenuBase {
    public AttributeFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(containerId, inventory, hand);
    }

    public static AttributeFilterMenu create(int containerId, Inventory inventory, RegistryFriendlyByteBuf extraData) {
        return new AttributeFilterMenu(containerId, inventory, extraData.readEnum(InteractionHand.class));
    }
}
