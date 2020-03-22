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

package com.github.klikli_dev.occultism.common.container;

import com.github.klikli_dev.occultism.common.tile.OtherworldMinerTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class OtherworldMinerContainer extends Container {

    //region Fields
    public OtherworldMinerTileEntity otherworldMiner;
    public PlayerInventory playerInventory;
    //endregion Fields

    //region Initialization
    public OtherworldMinerContainer(int id, PlayerInventory playerInventory,
                                       OtherworldMinerTileEntity otherworldMiner) {
        super(OccultismContainers.OTHERWORLD_MINER.get(), id);
        this.playerInventory = playerInventory;
        this.otherworldMiner = otherworldMiner;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return player.getDistanceSq(this.otherworldMiner.getPos().getX() + 0.5D, this.otherworldMiner.getPos().getY() + 0.5D,
                this.otherworldMiner.getPos().getZ() + 0.5D) <= 64.0D;
    }
    //endregion Overrides
}
