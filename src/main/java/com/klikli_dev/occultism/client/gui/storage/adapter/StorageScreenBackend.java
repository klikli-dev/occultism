/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.adapter;

import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import net.minecraft.core.BlockPos;

public interface StorageScreenBackend {
    boolean isValid();

    BlockPos actionPosition();

    SortDirection sortDirection();

    void setSortDirection(SortDirection sortDirection);

    SortType sortType();

    void setSortType(SortType sortType);
}
