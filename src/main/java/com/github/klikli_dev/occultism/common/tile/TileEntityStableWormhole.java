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

package com.github.klikli_dev.occultism.common.tile;

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.api.common.tile.IStorageControllerProxy;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityStableWormhole extends TileEntityBase implements IStorageControllerProxy {

    //region Fields
    protected GlobalBlockPos linkedStorageControllerPosition;
    protected IStorageController linkedStorageController;
    //endregion Fields

    //region Overrides
    @Override
    public IStorageController getLinkedStorageController() {
        if (this.linkedStorageController == null) {
            this.linkedStorageController = (IStorageController) TileEntityUtil.get(this.world,
                    this.linkedStorageControllerPosition);
        }
        return this.linkedStorageController;
    }

    @Override
    public GlobalBlockPos getLinkedStorageControllerPosition() {
        return this.linkedStorageControllerPosition;
    }

    @Override
    public void setLinkedStorageControllerPosition(GlobalBlockPos blockPos) {
        this.linkedStorageControllerPosition = blockPos;
        this.linkedStorageController = null;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction facing) {
        if (this.getLinkedStorageController() != null) {
            return ((TileEntity) this.getLinkedStorageController()).hasCapability(capability, facing);
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, Direction facing) {
        if (this.getLinkedStorageController() != null) {
            return ((TileEntity) this.getLinkedStorageController()).getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNetworkNBT(CompoundNBT compound) {
        if (compound.hasKey("linkedStorageControllerPosition"))
            this.linkedStorageControllerPosition = GlobalBlockPos.fromNbt(
                    compound.getCompoundTag("linkedStorageControllerPosition"));
        super.readFromNetworkNBT(compound);
    }

    @Override
    public CompoundNBT writeToNetworkNBT(CompoundNBT compound) {
        if (this.linkedStorageControllerPosition != null)
            compound.setTag("linkedStorageControllerPosition",
                    this.linkedStorageControllerPosition.writeToNBT(new CompoundNBT()));
        return super.writeToNetworkNBT(compound);
    }
    //endregion Overrides

    //region Methods
    //endregion Methods
}
