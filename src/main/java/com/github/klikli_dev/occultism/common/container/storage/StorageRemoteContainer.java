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

package com.github.klikli_dev.occultism.common.container.storage;

import com.github.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import com.github.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.github.klikli_dev.occultism.common.misc.StorageControllerCraftingInventory;
import com.github.klikli_dev.occultism.network.MessageUpdateLinkedMachines;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import com.github.klikli_dev.occultism.registry.OccultismContainers;
import com.github.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageRemoteContainer extends StorageControllerContainerBase {
    //region Fields
    protected int selectedSlot;
    //endregion Fields

    //region Initialization
    public StorageRemoteContainer(int id, PlayerInventory playerInventory, int selectedSlot) {
        super(OccultismContainers.STORAGE_REMOTE.get(), id, playerInventory);

        this.playerInventory = playerInventory;
        this.selectedSlot = selectedSlot;

        this.matrix = new StorageControllerCraftingInventory(this,
                this.getCraftingMatrixFromItemStack(this.getStorageRemote()));
        this.orderInventory.setInventorySlotContents(0, this.getOrderStackFromItemStack(this.getStorageRemote()));

        this.setupCraftingOutput();
        this.setupCraftingGrid();
        this.setupOrderInventorySlot();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();

        this.onCraftMatrixChanged(this.matrix);
    }
    //endregion Initialization

    //region Getter / Setter
    public ItemStack getStorageRemote() {
        if (this.selectedSlot == -1) {
            return CuriosUtil.getStorageRemote(this.player);
        }
        if (this.selectedSlot < 0 || this.selectedSlot >= this.player.inventory.getSizeInventory())
            return ItemStack.EMPTY;
        return this.player.inventory.getStackInSlot(this.selectedSlot);
    }
    //endregion Getter / Setter


    //region Overrides

    @Override
    public GlobalBlockPos getStorageControllerGlobalBlockPos() {
        ItemStack storageRemote = this.getStorageRemote();
        return storageRemote != ItemStack.EMPTY ? GlobalBlockPos.from(this.getStorageRemote().getTag().getCompound("linkedStorageController")) : null;
    }

    @Override
    protected void setupPlayerHotbar() {
        int hotbarTop = 232;
        int hotbarLeft = 8 + StorageControllerGuiBase.ORDER_AREA_OFFSET;
        for (int i = 0; i < 9; i++) {
            if (i == this.selectedSlot) {
                this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop) {
                    //region Overrides
                    @Override
                    public boolean isItemValid(ItemStack stack) {
                        return false;
                    }

                    @Override
                    public boolean getHasStack() {
                        return false;
                    }

                    @Override
                    public boolean canTakeStack(PlayerEntity playerIn) {
                        return false;
                    }
                    //endregion Overrides

                });
            } else {
                this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
            }
        }
    }

    @Override
    public void setAll(List<ItemStack> stacks) {
        this.matrix.disableEvents = true;
        super.setAll(stacks);
        this.matrix.disableEvents = false;
    }

    @Override
    public boolean canInteractWith(PlayerEntity entityPlayer) {
        IStorageController storageController = this.getStorageController();
        //canInteractWith is constantly called, so we use it to send
        //stack updates every 40 ticks.
        if (storageController != null && !entityPlayer.world.isRemote &&
                entityPlayer.world.getGameTime() % 40 == 0) {
            OccultismPackets.sendTo((ServerPlayerEntity) this.player, this.getStorageController().getMessageUpdateStacks());
            OccultismPackets.sendTo((ServerPlayerEntity) this.player,
                    new MessageUpdateLinkedMachines(this.getStorageController().getLinkedMachines()));
        }

        return this.getStorageRemote() != ItemStack.EMPTY;
    }

    @Override
    public IStorageController getStorageController() {
        return StorageRemoteItem.getStorageController(this.getStorageRemote(), this.playerInventory.player.world);
    }

    @Override
    public boolean isContainerItem() {
        return true;
    }

    @Override
    public void updateCraftingSlots(boolean force) {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
            nbtTagList.add(this.matrix.getStackInSlot(i).serializeNBT());
        }
        ItemStack storageRemote = this.getStorageRemote();
        if (storageRemote != ItemStack.EMPTY)
            storageRemote.getOrCreateTag().put("craftingMatrix", nbtTagList);
    }

    @Override
    public void updateOrderSlot(boolean force) {
        ItemStack storageRemote = this.getStorageRemote();
        if (storageRemote != ItemStack.EMPTY)
            storageRemote.getOrCreateTag().put("orderStack", this.orderInventory.getStackInSlot(0).serializeNBT());
    }
    //endregion Overrides

    //region Methods
    protected List<ItemStack> getCraftingMatrixFromItemStack(ItemStack stack) {
        List<ItemStack> craftingMatrix = new ArrayList<>(Collections.nCopies(9, ItemStack.EMPTY));
        if (!stack.getOrCreateTag().contains("craftingMatrix"))
            return craftingMatrix;

        ListNBT nbtTagList = stack.getTag().getList("craftingMatrix", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < nbtTagList.size(); i++) {
            craftingMatrix.set(i, ItemStack.read(nbtTagList.getCompound(i)));
        }

        return craftingMatrix;
    }

    protected ItemStack getOrderStackFromItemStack(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("orderStack"))
            return ItemStack.EMPTY;

        return ItemStack.read(stack.getTag().getCompound("orderStack"));
    }
    //endregion Methods
}
