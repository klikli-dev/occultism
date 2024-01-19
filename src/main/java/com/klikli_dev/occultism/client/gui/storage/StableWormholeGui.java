/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.client.gui.storage;

import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.network.messages.MessageUpdateStorageSettings;
import com.klikli_dev.occultism.network.Networking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StableWormholeGui extends StorageControllerGuiBase<StableWormholeContainer> {

    protected StorageControllerBlockEntity storageController;
    protected StableWormholeContainer container;

    public StableWormholeGui(StableWormholeContainer container, Inventory playerInventory,
                             Component name) {
        super(container, playerInventory, name);
        this.container = container;
        this.storageController = container.getStorageController();
    }

    @Override
    protected boolean isGuiValid() {
        return true;
    }

    @Override
    protected BlockPos getEntityPosition() {
        return this.container.getStableWormhole().getBlockPos();
    }

    @Override
    public SortDirection getSortDirection() {
        return this.container.getStableWormhole().getSortDirection();
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.container.getStableWormhole().setSortDirection(sortDirection);
        Networking.sendToServer(new MessageUpdateStorageSettings(sortDirection, this.getSortType()));
    }

    @Override
    public SortType getSortType() {
        return this.container.getStableWormhole().getSortType();
    }

    @Override
    public void setSortType(SortType sortType) {
        this.container.getStableWormhole().setSortType(sortType);
        Networking.sendToServer(new MessageUpdateStorageSettings(this.getSortDirection(), sortType));
    }

}
