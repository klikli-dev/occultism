/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.adapter;

import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageUpdateStorageSettings;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.BlockPos;

public class RemoteScreenBackend implements StorageScreenBackend {
    private final StorageRemoteContainer container;

    public RemoteScreenBackend(StorageRemoteContainer container) {
        this.container = container;
    }

    @Override
    public boolean isValid() {
        return !this.container.getStorageRemote().isEmpty();
    }

    @Override
    public BlockPos actionPosition() {
        return this.container.playerInventory.player.blockPosition();
    }

    @Override
    public SortDirection sortDirection() {
        return this.container.getStorageRemote().getOrDefault(OccultismDataComponents.SORT_DIRECTION, SortDirection.DOWN);
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.container.getStorageRemote().set(OccultismDataComponents.SORT_DIRECTION, sortDirection);
        Networking.sendToServer(new MessageUpdateStorageSettings(sortDirection, this.sortType()));
    }

    @Override
    public SortType sortType() {
        return this.container.getStorageRemote().getOrDefault(OccultismDataComponents.SORT_TYPE, SortType.AMOUNT);
    }

    @Override
    public void setSortType(SortType sortType) {
        this.container.getStorageRemote().set(OccultismDataComponents.SORT_TYPE, sortType);
        Networking.sendToServer(new MessageUpdateStorageSettings(this.sortDirection(), sortType));
    }
}
