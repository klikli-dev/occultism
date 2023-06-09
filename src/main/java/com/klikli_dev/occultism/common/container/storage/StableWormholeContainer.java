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

package com.klikli_dev.occultism.common.container.storage;

import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import com.klikli_dev.occultism.common.blockentity.StableWormholeBlockEntity;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.common.misc.StorageControllerCraftingInventory;
import com.klikli_dev.occultism.network.MessageUpdateLinkedMachines;
import com.klikli_dev.occultism.network.OccultismPackets;
import com.klikli_dev.occultism.registry.OccultismContainers;
import com.klikli_dev.occultism.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class StableWormholeContainer extends StorageControllerContainerBase {

    protected StorageControllerBlockEntity storageController;
    protected StableWormholeBlockEntity stableWormhole;

    public StableWormholeContainer(int id, Inventory playerInventory,
                                   StableWormholeBlockEntity stableWormhole) {
        super(OccultismContainers.STABLE_WORMHOLE.get(), id, playerInventory);
        this.stableWormhole = stableWormhole;
        this.storageController = (StorageControllerBlockEntity) stableWormhole.getLinkedStorageController();
        this.matrix = new StorageControllerCraftingInventory(this, stableWormhole.getMatrix());
        this.orderInventory.setItem(0, this.stableWormhole.getOrderStack());

        this.setupCraftingOutput(); //output is slot 0

        this.setupCraftingGrid();
        this.setupOrderInventorySlot();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();

        this.slotsChanged(this.matrix);
    }

    //region Getter / Setter
    public StableWormholeBlockEntity getStableWormhole() {
        return this.stableWormhole;
    }
    //endregion Getter / Setter

    @Override
    protected void setupPlayerHotbar() {
        int hotbarTop = 232;
        int hotbarLeft = 8 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
    }

    @Override
    public StorageControllerBlockEntity getStorageController() {
        return this.storageController;
    }

    @Override
    public GlobalBlockPos getStorageControllerGlobalBlockPos() {
        return this.stableWormhole.getLinkedStorageControllerPosition();
    }

    @Override
    public boolean isContainerItem() {
        return false;
    }

    @Override
    public void updateCraftingSlots(boolean force) {
        for (int i = 0; i < 9; i++) {
            this.stableWormhole.getMatrix().put(i, this.matrix.getItem(i));
        }
        if (force)
            this.stableWormhole.markNetworkDirty();
    }

    @Override
    public void updateOrderSlot(boolean force) {
        this.stableWormhole.setOrderStack(this.orderInventory.getItem(0));
        if (force)
            this.stableWormhole.markNetworkDirty();
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.result && super.canTakeItemForPickAll(stack, slot);
    }


    @Override
    public boolean stillValid(Player player) {
        if (this.storageController == null || this.stableWormhole == null)
            return false;

        Level level = this.stableWormhole.getLevel();
        BlockPos wormholePosition = this.stableWormhole.getBlockPos();

        //close container if wormhole block is destroyed
        if (level.getBlockEntity(wormholePosition) != this.stableWormhole)
            return false;

        //close container if storage controller block is destroyed
        if (BlockEntityUtil.get(level, this.stableWormhole.getLinkedStorageControllerPosition()) != this.storageController)
            return false;

        //send stack updates on a slow tick while interacting
        if (!level.isClientSide && level.getGameTime() % 40 == 0) {
            OccultismPackets.sendTo((ServerPlayer) player, this.storageController.getMessageUpdateStacks());
            OccultismPackets.sendTo((ServerPlayer) player,
                    new MessageUpdateLinkedMachines(this.storageController.getLinkedMachines()));
        }

        //prevent player from interacting with the container if the controller is not in range
        return player.distanceToSqr(wormholePosition.getX() + 0.5D, wormholePosition.getY() + 0.5D,
                wormholePosition.getZ() + 0.5D) <= 64.0D;
    }

}
