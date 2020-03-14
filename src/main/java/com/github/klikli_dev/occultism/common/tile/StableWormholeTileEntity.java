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
import com.github.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.github.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import com.github.klikli_dev.occultism.util.TileEntityUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StableWormholeTileEntity extends NetworkedTileEntity implements IStorageControllerProxy, INamedContainerProvider {

    //region Fields
    protected GlobalBlockPos linkedStorageControllerPosition;
    //endregion Fields

    //region Initialization
    public StableWormholeTileEntity() {
        super(OccultismTiles.STORAGE_CONTROLLER.get());
    }
    //endregion Initialization

    //region Overrides
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(this.getType().getRegistryName().getPath());
    }

    @Override
    public IStorageController getLinkedStorageController() {
        if (this.linkedStorageControllerPosition != null) {
            TileEntity tileEntity = TileEntityUtil.get(this.world,
                    this.linkedStorageControllerPosition);
            if (tileEntity instanceof IStorageController)
                return (IStorageController) tileEntity;
            else {
                this.linkedStorageControllerPosition = null;
                this.world.setBlockState(this.pos, this.getBlockState().with(StableWormholeBlock.LINKED, false), 2);
            }
        }
        return null;
    }

    @Override
    public GlobalBlockPos getLinkedStorageControllerPosition() {
        return this.linkedStorageControllerPosition;
    }

    @Override
    public void setLinkedStorageControllerPosition(GlobalBlockPos blockPos) {
        this.linkedStorageControllerPosition = blockPos;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (this.getLinkedStorageController() != null) {
            return ((TileEntity) this.getLinkedStorageController()).getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void readNetwork(CompoundNBT compound) {
        if (compound.contains("linkedStorageControllerPosition"))
            this.linkedStorageControllerPosition = GlobalBlockPos.from(compound.getCompound(
                    "linkedStorageControllerPosition"));
        super.readNetwork(compound);
    }

    @Override
    public CompoundNBT writeNetwork(CompoundNBT compound) {
        if (this.linkedStorageControllerPosition != null)
            compound.put("linkedStorageControllerPosition", this.linkedStorageControllerPosition.serializeNBT());
        return super.writeNetwork(compound);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new StableWormholeContainer(id, playerInventory, this);
    }
    //endregion Overrides

    //region Methods
    //endregion Methods
}
