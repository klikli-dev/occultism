// SPDX-FileCopyrightText: 2026 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.common.container.filter;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;

public class ListFilterMenu extends OccultismListFilterMenuBase {
    public ListFilterMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(containerId, inventory, hand);
    }

    public static ListFilterMenu create(int containerId, Inventory inventory, RegistryFriendlyByteBuf extraData) {
        return new ListFilterMenu(containerId, inventory, extraData.readEnum(InteractionHand.class));
    }
}
