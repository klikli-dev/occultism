package com.klikli_dev.occultism.common.misc;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.NotNull;

public interface IMapItemResourceHandler extends ResourceHandler<ItemResource> {

    int get(ItemStackKey key);

    int get(ItemStack stack);

    void setStackInSlot(int slot, @NotNull ItemStack stack);

    void setResourceInSlot(int slot, @NotNull ItemResource resource, int amount);

    int getSlots();

    @NotNull
    ItemStack getStackInSlot(int slot);

    @NotNull
    ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate);

    @NotNull
    ItemStack insertItem(@NotNull ItemStack stack, boolean simulate);

    @NotNull
    ItemStack extractItem(int slot, int amount, boolean simulate);

    @NotNull
    ItemStack extractItem(@NotNull ItemStackKey key, int amount, boolean simulate);

    @NotNull
    ItemStack extractItem(@NotNull ItemStack stack, int amount, boolean simulate);

    @NotNull
    ItemStack extractItemIgnoreComponents(@NotNull ItemStack stack, int amount, boolean simulate);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, @NotNull ItemStack stack);

    boolean isItemValid(int slot, @NotNull ItemStackKey key);
}
