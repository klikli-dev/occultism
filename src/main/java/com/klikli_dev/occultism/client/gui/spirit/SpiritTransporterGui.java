/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SpiritTransporterGui extends FilterableSpiritGui<SpiritTransporterContainer> {
    public SpiritTransporterGui(SpiritTransporterContainer container,
                                Inventory playerInventory,
                                Component titleIn) {
        super(container, playerInventory, titleIn);
    }
}
