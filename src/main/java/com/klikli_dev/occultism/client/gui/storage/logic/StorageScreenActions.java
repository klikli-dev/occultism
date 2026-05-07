/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage.logic;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.api.common.data.SortDirection;
import com.klikli_dev.occultism.api.common.data.SortType;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageClearCraftingMatrix;
import com.klikli_dev.occultism.network.messages.MessageInsertMouseHeldItem;
import com.klikli_dev.occultism.network.messages.MessageRequestOrder;
import com.klikli_dev.occultism.network.messages.MessageRequestStacks;
import com.klikli_dev.occultism.network.messages.MessageSortItems;
import com.klikli_dev.occultism.network.messages.MessageTakeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StorageScreenActions {
    public void requestStacks() {
        Networking.sendToServer(new MessageRequestStacks());
    }

    public void clearCraftingMatrixAndRefresh(Runnable reinit) {
        Networking.sendToServer(new MessageClearCraftingMatrix());
        this.requestStacks();
        reinit.run();
    }

    public void takeStack(ItemStack stack, int mouseButton, Runnable markInteraction) {
        Networking.sendToServer(new MessageTakeItem(stack, mouseButton, Minecraft.getInstance().hasShiftDown(),
                Minecraft.getInstance().hasControlDown()));
        markInteraction.run();
    }

    public void insertCarriedItem(int mouseButton, Runnable markInteraction) {
        Networking.sendToServer(new MessageInsertMouseHeldItem(mouseButton));
        markInteraction.run();
    }

    public void requestMachineOrder(Supplier<GlobalBlockPos> storageControllerPosition, MachineReference machine,
                                    Supplier<ItemStack> orderStack, Consumer<Component> warn, Runnable onSuccess) {
        GlobalBlockPos controllerPos = storageControllerPosition.get();
        ItemStack stack = orderStack.get();
        if (stack.isEmpty()) {
            return;
        }

        if (controllerPos == null) {
            warn.accept(Component.literal("Linked Storage Controller Position null."));
            return;
        }

        Networking.sendToServer(new MessageRequestOrder(controllerPos, machine.insertGlobalPos, stack));
        onSuccess.run();
    }

    public void highlightMachine(MachineReference machine) {
        long time = System.currentTimeMillis() + 5000;
        Occultism.SELECTED_BLOCK_RENDERER.selectBlock(machine.insertGlobalPos.getPos(), time, Color.GREEN);
        Occultism.SELECTED_BLOCK_RENDERER.selectBlock(machine.extractGlobalPos.getPos(), time, Color.YELLOW);
    }

    public void syncSort(BlockPos position, SortDirection sortDirection, SortType sortType) {
        Networking.sendToServer(new MessageSortItems(position, sortDirection, sortType));
    }
}
