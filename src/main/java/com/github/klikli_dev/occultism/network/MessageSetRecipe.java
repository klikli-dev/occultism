/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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
package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.github.klikli_dev.occultism.api.common.tile.IStorageController;
import com.github.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.github.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.HashMap;
import java.util.Map;

public class MessageSetRecipe extends MessageBase {

    //region Fields
    private CompoundNBT nbt;
    private int index = 0;
    //endregion Fields

    //region Initialization
    public MessageSetRecipe(PacketBuffer buf) {
        this.decode(buf);
    }

    public MessageSetRecipe(CompoundNBT nbt) {
        this.nbt = nbt;
    }
    //endregion Initialization

    //region Overrides


    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayerEntity player,
                                 NetworkEvent.Context context) {
        if (!(player.openContainer instanceof IStorageControllerContainer)) {
            return;
        }
        IStorageControllerContainer container = (IStorageControllerContainer) player.openContainer;
        IStorageController storageController = container.getStorageController();
        if (storageController == null) {
            return;
        }
        //clear the current crafting matrix
        StorageUtil.clearOpenCraftingMatrix(player, false);

        CraftingInventory craftMatrix = container.getCraftMatrix();
        String[] oreDictKeys;
        for (int slot = 0; slot < 9; slot++) {
            Map<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();

            //parse the slots
            ListNBT invList = this.nbt.getList("s" + slot, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < invList.size(); i++) {
                ItemStack s = ItemStack.read(invList.getCompound(i));
                map.put(i, s);
            }

            //region fill in recipe
            for (int i = 0; i < map.size(); i++) {

                ItemStack stack = map.get(i);
                if (stack == null || stack.isEmpty()) {
                    continue;
                }

                ItemStackComparator comparator = new ItemStackComparator(stack, true);

                //attempt to get the desired stack from the player inventory
                ItemStack extractedStack = StorageUtil
                                                   .extractItem(new PlayerMainInvWrapper(player.inventory), comparator,
                                                           1, true);
                if (extractedStack != null && !extractedStack.isEmpty() && craftMatrix.getStackInSlot(slot).isEmpty()) {
                    //if we found the desired stack, extract it for real and place it in the matrix
                    StorageUtil.extractItem(new PlayerMainInvWrapper(player.inventory), comparator, 1, false);
                    craftMatrix.setInventorySlotContents(slot, extractedStack);
                    break;
                }

                //if we did not find anything in the player inventory, get it from the network now
                stack = storageController.getItemStack(!stack.isEmpty() ? comparator : null, 1, false);
                if (!stack.isEmpty() && craftMatrix.getStackInSlot(slot).isEmpty()) {
                    //if extraction was successful, place it in the matrix
                    craftMatrix.setInventorySlotContents(slot, stack);
                    break;
                }
            }
            //endregion fill in recipe
        }
        //sync to client
        container.updateCraftingSlots(true);
        //finally update controller content for client
        OccultismPackets.sendTo(player, storageController.getMessageUpdateStacks());
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeCompoundTag(this.nbt);
        buf.writeInt(this.index);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.nbt = buf.readCompoundTag();
        this.index = buf.readInt();
    }
    //endregion Overrides
}
