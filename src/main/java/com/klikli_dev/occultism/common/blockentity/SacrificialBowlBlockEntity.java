/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.occultism.common.blockentity;

import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SacrificialBowlBlockEntity extends NetworkedBlockEntity {

    public long lastChangeTime;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(
                int slot) {
            if (!SacrificialBowlBlockEntity.this.level.isClientSide) {
                SacrificialBowlBlockEntity.this.lastChangeTime = SacrificialBowlBlockEntity.this.level
                        .getGameTime();
                SacrificialBowlBlockEntity.this.markNetworkDirty();
            }
        }

    };
    protected boolean initialized = false;

    public SacrificialBowlBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.SACRIFICIAL_BOWL.get(), worldPos, state);
    }

    public SacrificialBowlBlockEntity(BlockEntityType<?> BlockEntityTypeIn, BlockPos worldPos, BlockState state) {
        super(BlockEntityTypeIn, worldPos, state);
    }


    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        this.itemStackHandler.deserializeNBT(provider, compound.getCompound("inventory"));
        this.lastChangeTime = compound.getLong("lastChangeTime");
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        compound.put("inventory", this.itemStackHandler.serializeNBT(provider));
        compound.putLong("lastChangeTime", this.lastChangeTime);
        return compound;
    }
}
