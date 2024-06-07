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

import com.klikli_dev.occultism.api.common.blockentity.IStorageAccessor;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.blockentity.IStorageControllerProxy;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.common.block.storage.StableWormholeBlock;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.registry.OccultismBlockEntities;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StableWormholeBlockEntity extends NetworkedBlockEntity implements IStorageControllerProxy, MenuProvider, IStorageAccessor {

    protected GlobalBlockPos linkedStorageControllerPosition;
    protected Map<Integer, ItemStack> matrix = new HashMap<>();
    protected ItemStack orderStack = ItemStack.EMPTY;
    protected SortDirection sortDirection = SortDirection.DOWN;
    protected SortType sortType = SortType.AMOUNT;

    public StableWormholeBlockEntity(BlockPos worldPos, BlockState state) {
        super(OccultismBlockEntities.STABLE_WORMHOLE.get(), worldPos, state);
    }

    //region Getter / Setter
    @Override
    public SortDirection getSortDirection() {
        return this.sortDirection;
    }

    @Override
    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public SortType getSortType() {
        return this.sortType;
    }

    @Override
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public Map<Integer, ItemStack> getMatrix() {
        return this.matrix;
    }

    @Override
    public ItemStack getOrderStack() {
        return this.orderStack;
    }

    @Override
    public void setOrderStack(@Nonnull ItemStack orderStack) {
        this.orderStack = orderStack;
    }
    //endregion Getter / Setter

    @Override
    public Component getDisplayName() {
        return Component.literal(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).getPath());
    }

    @Override
    public IStorageController getLinkedStorageController() {
        if (this.linkedStorageControllerPosition != null) {
            if (!BlockEntityUtil.isLoaded(this.level, this.linkedStorageControllerPosition)) {
                //if the target pos is not loaded we exit early to prevent accidentally clearing the linked storage controller position
                return null;
            }


            BlockEntity blockEntity = BlockEntityUtil.get(this.level,
                    this.linkedStorageControllerPosition);
            if (blockEntity instanceof IStorageController controller)
                return controller;
            else if (!this.level.isClientSide) {
                //only reset the storage controller position if we are on logical server -> that means the position is not accessible.
                //if we are on logical client it simply means we are out of render range, so we do not reset the pos
                //resetting it would cause issues with e.g. stable wormhole
                this.linkedStorageControllerPosition = null;
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(StableWormholeBlock.LINKED, false), 2);
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

    @Override
    public void loadNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        if (compound.contains("linkedStorageControllerPosition"))
            this.linkedStorageControllerPosition = GlobalBlockPos.from(compound.getCompound("linkedStorageControllerPosition"));

        this.setSortDirection(SortDirection.BY_ID.apply(compound.getInt("sortDirection")));
        this.setSortType(SortType.BY_ID.apply(compound.getInt("sortType")));

        this.matrix = new HashMap<>();
        if (compound.contains("matrix")) {
            ListTag matrixNbt = compound.getList("matrix", Tag.TAG_COMPOUND);
            for (int i = 0; i < matrixNbt.size(); i++) {
                CompoundTag stackTag = matrixNbt.getCompound(i);
                int slot = stackTag.getByte("slot");
                ItemStack s = ItemStack.parseOptional(provider, stackTag);
                this.matrix.put(slot, s);
            }
        }

        if (compound.contains("orderStack"))
            this.orderStack = ItemStack.parseOptional(provider, compound.getCompound("orderStack"));

        super.loadNetwork(compound, provider);
    }

    @Override
    public CompoundTag saveNetwork(CompoundTag compound, HolderLookup.Provider provider) {
        if (this.linkedStorageControllerPosition != null)
            compound.put("linkedStorageControllerPosition", this.linkedStorageControllerPosition.serializeNBT(provider));

        compound.putInt("sortDirection", this.getSortDirection().ordinal());
        compound.putInt("sortType", this.getSortType().ordinal());

        ListTag matrixNbt = new ListTag();
        for (int i = 0; i < this.matrix.size(); i++) {
            if (this.matrix.get(i) != null && !this.matrix.get(i).isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("slot", (byte) i);
                this.matrix.get(i).save(provider, stackTag);
                matrixNbt.add(stackTag);
            }
        }
        compound.put("matrix", matrixNbt);

        if (!this.orderStack.isEmpty())
            compound.put("orderStack", this.orderStack.save(provider, new CompoundTag()));

        return super.saveNetwork(compound, provider);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new StableWormholeContainer(id, playerInventory, this);
    }

}
