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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.client.gui.GuiStorageControllerBase;
import com.github.klikli_dev.occultism.common.item.ItemStorageRemote;
import com.github.klikli_dev.occultism.common.misc.InventoryCraftingCached;
import com.github.klikli_dev.occultism.network.MessageUpdateLinkedMachines;
import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContainerStorageRemote extends ContainerStorageControllerBase {
    //region Fields
    protected ItemStack storageRemote;
    //endregion Fields

    //region Initialization
    public ContainerStorageRemote(InventoryPlayer playerInventory, EnumHand hand) {
        super();
        this.playerInventory = playerInventory;
        this.storageRemote = playerInventory.player.getHeldItem(hand);

        this.matrix = new InventoryCraftingCached(this, this.getCraftingMatrixFromItemStack(this.getStorageRemote()));
        this.orderInventory.setInventorySlotContents(0, this.getOrderStackFromItemStack(this.getStorageRemote()));

        this.setupCraftingGrid();
        this.setupCraftingOutput();
        this.setupOrderInventorySlot();
        this.setupPlayerInventorySlots();
        this.setupPlayerHotbar();

        this.onCraftMatrixChanged(this.matrix);
    }
    //endregion Initialization

    //region Getter / Setter
    public ItemStack getStorageRemote() {
        return this.storageRemote.getItem() instanceof ItemStorageRemote ? this.storageRemote : ItemStack.EMPTY;
    }
    //endregion Getter / Setter


    //region Overrides
    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.findRecipeForMatrix();
    }

    @Override
    public void setAll(List<ItemStack> stacks) {
        this.matrix.disableEvents = true;
        super.setAll(stacks);
        this.matrix.disableEvents = false;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        IStorageController storageController = this.getStorageController();
        //canInteractWith is constantly called, so we use it to send
        //stack updates every 40 ticks.
        if (storageController != null && !entityPlayer.world.isRemote &&
            entityPlayer.world.getTotalWorldTime() % 40 == 0) {
            Occultism.network.sendTo(storageController.getMessageUpdateStacks(), (EntityPlayerMP) entityPlayer);
            Occultism.network.sendTo(new MessageUpdateLinkedMachines(storageController.getLinkedMachines()),
                    (EntityPlayerMP) entityPlayer);
        }

        return entityPlayer.inventory.getCurrentItem().getItem() == ItemRegistry.STORAGE_REMOTE;
    }

    @Override
    public IStorageController getStorageController() {
        return ItemStorageRemote.getStorageController(this.getStorageRemote(), this.playerInventory.player.world);
    }

    @Override
    public boolean isContainerItem() {
        return true;
    }

    @Override
    public void updateCraftingSlots(boolean force) {
        NBTTagCompound compound = ItemNBTUtil.getTagCompound(this.storageRemote);

        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.matrix.getSizeInventory(); i++) {
            nbtTagList.appendTag(this.matrix.getStackInSlot(i).writeToNBT(new NBTTagCompound()));
        }

        this.storageRemote.setTagCompound(compound);
    }

    @Override
    public void updateOrderSlot(boolean force) {
        NBTTagCompound compound = ItemNBTUtil.getTagCompound(this.storageRemote);
        compound.setTag("orderStack", this.orderInventory.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        this.storageRemote.setTagCompound(compound);
    }

    @Override
    protected void setupPlayerHotbar() {
        int hotbarTop = 232;
        int hotbarLeft = 8 + GuiStorageControllerBase.ORDER_AREA_OFFSET;
        for (int i = 0; i < 9; i++) {
            //special treatment for the remote itself to avoid duping, etc.
            if (i == this.playerInventory.currentItem) {
                this.addSlotToContainer(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop) {
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
                    public boolean canTakeStack(EntityPlayer playerIn) {
                        return false;
                    }
                    //endregion Overrides

                });
            }

            this.addSlotToContainer(new Slot(this.playerInventory, i, hotbarLeft + i * 18, hotbarTop));
        }
    }
    //endregion Overrides

    //region Methods
    protected List<ItemStack> getCraftingMatrixFromItemStack(ItemStack stack) {
        List<ItemStack> craftingMatrix = new ArrayList<>(Collections.nCopies(9, ItemStack.EMPTY));
        if (!stack.hasTagCompound() || stack.getTagCompound().hasKey("craftingMatrix"))
            return craftingMatrix;

        NBTTagList nbtTagList = stack.getTagCompound().getTagList("craftingMatrix", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            craftingMatrix.set(i, new ItemStack(nbtTagList.getCompoundTagAt(i)));
        }

        return craftingMatrix;
    }

    protected ItemStack getOrderStackFromItemStack(ItemStack stack) {
        if (!stack.hasTagCompound() || stack.getTagCompound().hasKey("orderStack"))
            return ItemStack.EMPTY;

        return new ItemStack(stack.getTagCompound().getCompoundTag("orderStack"));
    }
    //endregion Methods
}
