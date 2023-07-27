/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, MrRiegel, Sam Bassett
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
package com.klikli_dev.occultism.network;

import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.container.IStorageControllerContainer;
import com.klikli_dev.occultism.common.misc.ItemStackComparator;
import com.klikli_dev.occultism.util.StorageUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on https://github.com/Lothrazar/Storage-Network
 */
public class MessageSetRecipe extends MessageBase {

    //region Fields
    private CompoundTag nbt;
    private int index = 0;
    //endregion Fields

    //region Initialization
    public MessageSetRecipe(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageSetRecipe(CompoundTag nbt) {
        this.nbt = nbt;
    }
    //endregion Initialization

    //region Overrides


    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player,
                                 NetworkEvent.Context context) {
        if (!(player.containerMenu instanceof IStorageControllerContainer container)) {
            return;
        }
        IStorageController storageController = container.getStorageController();
        if (storageController == null) {
            return;
        }
        //clear the current crafting matrix
        StorageUtil.clearOpenCraftingMatrix(player, false);

        CraftingContainer craftMatrix = container.getCraftMatrix();

        for (int slot = 0; slot < 9; slot++) {
            Map<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();

            //parse the slots
            ListTag invList = this.nbt.getList("s" + slot, Tag.TAG_COMPOUND);
            for (int i = 0; i < invList.size(); i++) {
                ItemStack s = ItemStack.of(invList.getCompound(i));
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
                        .extractItem(new PlayerMainInvWrapper(player.getInventory()), comparator,
                                1, true);
                if (extractedStack != null && !extractedStack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                    //if we found the desired stack, extract it for real and place it in the matrix
                    StorageUtil.extractItem(new PlayerMainInvWrapper(player.getInventory()), comparator, 1, false);
                    craftMatrix.setItem(slot, extractedStack);
                    break;
                }

                //if we did not find anything in the player inventory, get it from the network now
                stack = storageController.getItemStack(!stack.isEmpty() ? comparator : null, 1, false);
                if (!stack.isEmpty() && craftMatrix.getItem(slot).isEmpty()) {
                    //if extraction was successful, place it in the matrix
                    craftMatrix.setItem(slot, stack);
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
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.nbt);
        buf.writeInt(this.index);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
        this.index = buf.readInt();
    }
    //endregion Overrides
}
