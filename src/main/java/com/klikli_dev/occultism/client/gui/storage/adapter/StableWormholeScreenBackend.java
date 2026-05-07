/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.adapter;

import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageUpdateStorageSettings;
import net.minecraft.core.BlockPos;

public class StableWormholeScreenBackend implements StorageScreenBackend {
    private final StableWormholeContainer container;

    public StableWormholeScreenBackend(StableWormholeContainer container) {
        this.container = container;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public BlockPos actionPosition() {
        return this.container.getStableWormhole().getBlockPos();
    }

    @Override
    public SortDirection sortDirection() {
        return this.container.getStableWormhole().getSortDirection();
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.container.getStableWormhole().setSortDirection(sortDirection);
        Networking.sendToServer(new MessageUpdateStorageSettings(sortDirection, this.sortType()));
    }

    @Override
    public SortType sortType() {
        return this.container.getStableWormhole().getSortType();
    }

    @Override
    public void setSortType(SortType sortType) {
        this.container.getStableWormhole().setSortType(sortType);
        Networking.sendToServer(new MessageUpdateStorageSettings(this.sortDirection(), sortType));
    }
}
