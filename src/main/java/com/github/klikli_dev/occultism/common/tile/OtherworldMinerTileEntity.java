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

import com.github.klikli_dev.occultism.common.container.OtherworldMinerContainer;
import com.github.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.github.klikli_dev.occultism.registry.OccultismTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OtherworldMinerTileEntity extends NetworkedTileEntity implements ITickableTileEntity, INamedContainerProvider {

    //region Fields
    public LazyOptional<ItemStackHandler> inputHandler = LazyOptional.of(() -> new ItemStackHandler(1));
    public LazyOptional<ItemStackHandler> outputHandler = LazyOptional.of(() -> new ItemStackHandler(9));
    public LazyOptional<CombinedInvWrapper> combinedHandler =
            LazyOptional.of(() -> new CombinedInvWrapper(this.inputHandler.orElseThrow(ItemHandlerMissingException::new),
                            this.outputHandler.orElseThrow(ItemHandlerMissingException::new)));

    public int miningTime;
    public int maxMiningTime = 400;
    //endregion Fields

    //region Initialization
    public OtherworldMinerTileEntity() {
        super(OccultismTiles.OTHERWORLD_MINER.get());
    }

    //endregion Initialization

    //region Overrides
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(this.getType().getRegistryName().getPath());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (direction == null) {
                //null is full access for machines or similar.
                return this.combinedHandler.cast();
            }
            else if (direction == Direction.UP){
                return this.inputHandler.cast();
            }
            else {
                return this.outputHandler.cast();
            }
        }
        return super.getCapability(cap, direction);
    }


    @Override
    public void readNetwork(CompoundNBT compound) {
        this.inputHandler.ifPresent((handler) -> handler.deserializeNBT(compound.getCompound("inputHandler")));
        this.outputHandler.ifPresent((handler) -> handler.deserializeNBT(compound.getCompound("outputHandler")));
        this.miningTime = compound.getInt("miningTime");
    }

    @Override
    public CompoundNBT writeNetwork(CompoundNBT compound) {
        this.inputHandler.ifPresent(handler -> compound.put("inputHandler", handler.serializeNBT()));
        this.outputHandler.ifPresent(handler -> compound.put("outputHandler", handler.serializeNBT()));
        compound.putInt("miningTime", this.miningTime);
        return compound;
    }

    @Override
    public void remove() {
        this.inputHandler.invalidate();
        this.outputHandler.invalidate();
        super.remove();
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (this.miningTime > 0){
            this.miningTime--;
            dirty = true;
        }


        if (this.miningTime <= 0){
            this.miningTime = this.maxMiningTime;
            dirty = true;
        }

        if(dirty){
            this.markNetworkDirty();
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new OtherworldMinerContainer(id, playerInventory, this);
    }

    //endregion Overrides
}
