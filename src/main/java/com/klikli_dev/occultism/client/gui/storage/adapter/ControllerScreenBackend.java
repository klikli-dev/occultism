/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.adapter;

import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageUpdateStorageSettings;
import net.minecraft.core.BlockPos;

public class ControllerScreenBackend implements StorageScreenBackend {
    private final StorageControllerBlockEntity storageController;

    public ControllerScreenBackend(StorageControllerBlockEntity storageController) {
        this.storageController = storageController;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public BlockPos actionPosition() {
        return this.storageController.getBlockPos();
    }

    @Override
    public SortDirection sortDirection() {
        return this.storageController.getSortDirection();
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.storageController.setSortDirection(sortDirection);
        Networking.sendToServer(new MessageUpdateStorageSettings(sortDirection, this.sortType()));
    }

    @Override
    public SortType sortType() {
        return this.storageController.getSortType();
    }

    @Override
    public void setSortType(SortType sortType) {
        this.storageController.setSortType(sortType);
        Networking.sendToServer(new MessageUpdateStorageSettings(this.sortDirection(), sortType));
    }
}
