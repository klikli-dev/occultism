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

import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.client.gui.storage.StorageControllerGuiBase;
import com.klikli_dev.occultism.common.item.storage.StorageRemoteItem;
import com.klikli_dev.occultism.common.misc.StorageControllerCraftingInventory;
import com.klikli_dev.occultism.network.messages.MessageUpdateLinkedMachines;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.registry.OccultismContainers;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageRemoteContainer extends StorageControllerContainerBase {

    protected int selectedSlot;

    public StorageRemoteContainer(int id, Inventory playerInventory, int selectedSlot) {
        super(OccultismContainers.STORAGE_REMOTE.get(), id, playerInventory);

        this.playerInventory = playerInventory;
        this.selectedSlot = selectedSlot;

        this.matrix = new StorageControllerCraftingInventory(this,
                this.getCraftingMatrixFromItemStack(this.getStorageRemote()));
        this.orderInventory.setItem(0, this.getOrderStackFromItemStack(this.getStorageRemote()));

        this.setupCraftingOutput();
        this.setupCraftingGrid();
        this.setupOrderInventorySlot();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();

        this.slotsChanged(this.matrix);
    }

    //region Getter / Setter
    public ItemStack getStorageRemote() {
        if (this.selectedSlot == -1) {
            return CuriosUtil.getStorageRemoteCurio(this.player);
        }
        if (this.selectedSlot < 0 || this.selectedSlot >= this.player.getInventory().getContainerSize())
            return ItemStack.EMPTY;
        ItemStack selected = this.player.getInventory().getItem(this.selectedSlot);
        return selected.getItem() instanceof StorageRemoteItem ? selected : ItemStack.EMPTY;
    }
    //endregion Getter / Setter

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

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }

                    @Override
                    public boolean hasItem() {
                        return false;
                    }

                    @Override
                    public boolean mayPickup(Player playerIn) {
                        return false;
                    }

                });
            } else {
                this.addSlot(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
            }
        }
    }

    @Override
    protected void setupPlayerInventorySlots() {
        int playerInventoryTop = 174;
        int playerInventoryLeft = 8 + StorageControllerGuiBase.ORDER_AREA_OFFSET;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (j + i * 9 + 9 == this.selectedSlot) {
                    this.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                            playerInventoryTop + i * 18) {

                        @Override
                        public boolean mayPlace(ItemStack stack) {
                            return false;
                        }

                        @Override
                        public boolean hasItem() {
                            return false;
                        }

                        @Override
                        public boolean mayPickup(Player playerIn) {
                            return false;
                        }

                    });
                } else {
                    this.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, playerInventoryLeft + j * 18,
                            playerInventoryTop + i * 18));
                }
            }
        }


    }

    @Override
    public boolean stillValid(Player entityPlayer) {
        IStorageController storageController = this.getStorageController();

        //stillValid is constantly called, so we use it to send
        //stack updates every 40 ticks.
        if (storageController != null && !entityPlayer.level().isClientSide &&
                entityPlayer.level().getGameTime() % 40 == 0) {
            Networking.sendTo((ServerPlayer) this.player, this.getStorageController().getMessageUpdateStacks());
            Networking.sendTo((ServerPlayer) this.player,
                    new MessageUpdateLinkedMachines(this.getStorageController().getLinkedMachines()));
        }

        //close container if storage controller block is destroyed ( -> if it is null here, this check is a bit different from the wormhole, because getStorageController() already handles that)
        return storageController != null && this.getStorageRemote() != ItemStack.EMPTY;
    }

    @Override
    public IStorageController getStorageController() {
        return StorageRemoteItem.getStorageController(this.getStorageRemote(), this.playerInventory.player.level());
    }

    @Override
    public boolean isContainerItem() {
        return true;
    }

    @Override
    public void updateCraftingSlots(boolean force) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < this.matrix.getContainerSize(); i++) {
            nbtTagList.add(this.matrix.getItem(i).serializeNBT());
        }
        ItemStack storageRemote = this.getStorageRemote();
        if (storageRemote != ItemStack.EMPTY)
            storageRemote.getOrCreateTag().put("craftingMatrix", nbtTagList);
    }

    @Override
    public void updateOrderSlot(boolean force) {
        ItemStack storageRemote = this.getStorageRemote();
        if (storageRemote != ItemStack.EMPTY)
            storageRemote.getOrCreateTag().put("orderStack", this.orderInventory.getItem(0).serializeNBT());
    }

    protected List<ItemStack> getCraftingMatrixFromItemStack(ItemStack stack) {
        List<ItemStack> craftingMatrix = new ArrayList<>(Collections.nCopies(9, ItemStack.EMPTY));
        if (!stack.getOrCreateTag().contains("craftingMatrix"))
            return craftingMatrix;

        ListTag nbtTagList = stack.getTag().getList("craftingMatrix", Tag.TAG_COMPOUND);

        for (int i = 0; i < nbtTagList.size(); i++) {
            craftingMatrix.set(i, ItemStack.of(nbtTagList.getCompound(i)));
        }

        return craftingMatrix;
    }

    protected ItemStack getOrderStackFromItemStack(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("orderStack"))
            return ItemStack.EMPTY;

        return ItemStack.of(stack.getTag().getCompound("orderStack"));
    }

}
