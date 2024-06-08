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
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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

        //read stored crafting matrix
        if (compound.contains("matrix")) {
            this.matrix = StorageControllerBlockEntity.loadMatrix(compound.getCompound("matrix"), provider);
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

        //write stored crafting matrix
        compound.put("matrix", StorageControllerBlockEntity.saveMatrix(this.matrix, provider));

        if (!this.orderStack.isEmpty())
            compound.put("orderStack", this.orderStack.saveOptional(provider));

        return super.saveNetwork(compound, provider);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput pComponentInput) {
        super.applyImplicitComponents(pComponentInput);

        if (pComponentInput.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER) != null)
            this.linkedStorageControllerPosition = pComponentInput.get(OccultismDataComponents.LINKED_STORAGE_CONTROLLER);

        if (pComponentInput.get(OccultismDataComponents.SORT_DIRECTION) != null)
            this.sortDirection = pComponentInput.get(OccultismDataComponents.SORT_DIRECTION);
        if (pComponentInput.get(OccultismDataComponents.SORT_TYPE) != null)
            this.sortType = pComponentInput.get(OccultismDataComponents.SORT_TYPE);

        if (pComponentInput.get(OccultismDataComponents.CRAFTING_MATRIX) != null) {
            this.matrix = StorageControllerBlockEntity.loadMatrix(pComponentInput.get(OccultismDataComponents.CRAFTING_MATRIX).getUnsafe(), this.level.registryAccess());
        }
        if (pComponentInput.get(OccultismDataComponents.ORDER_STACK) != null)
            this.orderStack = ItemStack.parseOptional(this.level.registryAccess(), pComponentInput.get(OccultismDataComponents.ORDER_STACK).getUnsafe());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);

        pComponents.set(OccultismDataComponents.LINKED_STORAGE_CONTROLLER, this.linkedStorageControllerPosition);

        pComponents.set(OccultismDataComponents.SORT_DIRECTION, this.sortDirection);
        pComponents.set(OccultismDataComponents.SORT_TYPE, this.sortType);

        pComponents.set(OccultismDataComponents.CRAFTING_MATRIX, CustomData.of(StorageControllerBlockEntity.saveMatrix(this.matrix, this.level.registryAccess())));

        pComponents.set(OccultismDataComponents.ORDER_STACK, CustomData.of((CompoundTag) this.orderStack.saveOptional(this.level.registryAccess())));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag pTag) {
        //this causes stuff to get lost. Not sure why / how it is used in vanilla shulker boxes
//        pTag.remove("items");
//        pTag.remove("matrix");
//        pTag.remove("orderStack");
//        pTag.remove("sortDirection");
//        pTag.remove("sortType");
//        pTag.remove("linkedMachines");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new StableWormholeContainer(id, playerInventory, this);
    }

}
