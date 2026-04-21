package com.klikli_dev.occultism.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public final class ItemTransferUtil {

    private ItemTransferUtil() {
    }

    public static ItemStack insertItem(@Nullable ResourceHandler<ItemResource> handler, ItemStack stack, boolean simulate) {
        if (handler == null || stack.isEmpty()) {
            return stack;
        }

        return ItemUtil.insertItemReturnRemaining(handler, stack, simulate, null);
    }

    public static ItemStack insertItem(@Nullable ResourceHandler<ItemResource> handler, int index, ItemStack stack, boolean simulate) {
        if (handler == null || stack.isEmpty()) {
            return stack;
        }

        return ItemUtil.insertItemReturnRemaining(handler, index, stack, simulate, null);
    }

    public static ItemStack insertItemStacked(@Nullable ResourceHandler<ItemResource> handler, ItemStack stack, boolean simulate) {
        if (handler == null || stack.isEmpty()) {
            return stack;
        }

        try (var tx = Transaction.openRoot()) {
            int inserted = ResourceHandlerUtil.insertStacking(handler, ItemResource.of(stack), stack.getCount(), tx);
            if (!simulate) {
                tx.commit();
            }

            int remainder = stack.getCount() - inserted;
            return remainder <= 0 ? ItemStack.EMPTY : stack.copyWithCount(remainder);
        }
    }

    public static int getFirstFilledSlot(ResourceHandler<ItemResource> handler) {
        for (int i = 0; i < handler.size(); i++) {
            if (!handler.getResource(i).isEmpty())
                return i;
        }
        return -1;
    }

    public static int getFirstMatchingSlot(ResourceHandler<ItemResource> handler, TagKey<Item> tag) {
        for (int i = 0; i < handler.size(); i++) {
            ItemResource resource = handler.getResource(i);
            if (!resource.isEmpty() && resource.toStack().is(tag)) {
                return i;
            }
        }
        return -1;
    }

    public static ItemStack extractItem(@Nullable ResourceHandler<ItemResource> handler, int index, int amount, boolean simulate) {

        if (handler == null || amount <= 0 || index < 0 || index >= handler.size()) {
            return ItemStack.EMPTY;
        }

        var resource = handler.getResource(index);
        if (resource.isEmpty()) {
            return ItemStack.EMPTY;
        }

        try (var tx = Transaction.openRoot()) {
            int extracted = handler.extract(index, resource, amount, tx);
            if (!simulate) {
                tx.commit();
            }

            return extracted <= 0 ? ItemStack.EMPTY : resource.toStack(extracted);
        }
    }

    public static ItemStack extractItem(@Nullable ResourceHandler<ItemResource> handler, ItemResource resource, int amount, boolean simulate) {
        if (handler == null || resource.isEmpty() || amount <= 0) {
            return ItemStack.EMPTY;
        }

        try (var tx = Transaction.openRoot()) {
            int extracted = handler.extract(resource, amount, tx);
            if (!simulate) {
                tx.commit();
            }

            return extracted <= 0 ? ItemStack.EMPTY : resource.toStack(extracted);
        }
    }

    public static void giveItemToPlayer(Player player, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        var handler = player.getCapability(Capabilities.Item.ENTITY);
        if (handler != null) {
            stack = insertItemStacked(handler, stack, false);
        }

        if (!stack.isEmpty()) {
            player.getInventory().placeItemBackInInventory(stack);
        }
    }
}
