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

package com.github.klikli_dev.occultism.client.gui;

import com.github.klikli_dev.occultism.api.common.data.SortDirection;
import com.github.klikli_dev.occultism.api.common.data.SortType;
import com.github.klikli_dev.occultism.common.container.ContainerStorageRemote;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.util.math.BlockPos;

public class GuiStorageRemote extends GuiStorageControllerBase {

    //region Fields
    protected ContainerStorageRemote container;
    //endregion Fields

    //region Initialization
    public GuiStorageRemote(ContainerStorageRemote container) {
        super(container);
        this.container = container;
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected boolean isGuiValid() {
        return !this.container.getStorageRemote().isEmpty();
    }

    @Override
    protected BlockPos getEntityPosition() {
        return this.container.playerInventory.player.getPosition();
    }

    @Override
    public SortDirection getSortDirection() {
        return SortDirection.get(ItemNBTUtil.getInteger(this.container.getStorageRemote(), "sortDirection"));
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        ItemNBTUtil.setInteger(this.container.getStorageRemote(), "sortDirection", sortDirection.getValue());
    }

    @Override
    public SortType getSortType() {
        return SortType.get(ItemNBTUtil.getInteger(this.container.getStorageRemote(), "sortType"));
    }

    @Override
    public void setSortType(SortType sortType) {
        ItemNBTUtil.setInteger(this.container.getStorageRemote(), "sortType", sortType.getValue());
    }
    //endregion Overrides
}
