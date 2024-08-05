package com.klikli_dev.occultism.common.misc;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public interface IMapItemHandlerModifiable extends IItemHandlerModifiable {


    /**
     * Gets the amount of items in the slot that match the given key (exact match including nbt/components!)
     */
    int get(ItemStackKey key);

    /**
     * Gets the amount of items in the slot that match the given stack (exact match/components!)
     */
    int get(ItemStack stack);

    /**
     * <p>
     * Inserts an ItemStack into the fitting slot and return the remainder.
     * The ItemStack <em>should not</em> be modified in this function!
     * </p>
     * Note: This behaviour is subtly different from {@link IFluidHandler#fill(FluidStack, IFluidHandler.FluidAction)}
     *
     * @param stack    ItemStack to insert. This must not be modified by the item handler.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return an empty ItemStack).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     * The returned ItemStack can be safely modified after.
     **/
    @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate);

    /**
     * Extracts an ItemStack that matches the given key.
     * <p>
     * The returned value must be empty if nothing is extracted,
     * otherwise its stack size must be less than or equal to {@code amount} and {@link ItemStack#getMaxStackSize()}.
     * </p>
     *
     * @param key     The ItemStackKey to find and extract.
     * @param amount   Amount to extract (may be greater than the current stack's max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be empty if nothing can be extracted.
     *         The returned ItemStack can be safely modified after, so item handlers should return a new or copied stack.
     **/
    @NotNull ItemStack extractItem(@NotNull ItemStackKey key, int amount, boolean simulate);

    /**
     * Extracts an ItemStack that matches the given stack.
     * <p>
     * The returned value must be empty if nothing is extracted,
     * otherwise its stack size must be less than or equal to {@code amount} and {@link ItemStack#getMaxStackSize()}.
     * </p>
     *
     * @param stack     The ItemStack to find and extract.
     * @param amount   Amount to extract (may be greater than the current stack's max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be empty if nothing can be extracted.
     *         The returned ItemStack can be safely modified after, so item handlers should return a new or copied stack.
     **/
    @NotNull ItemStack extractItem(@NotNull ItemStack stack, int amount, boolean simulate);

    boolean isItemValid(int slot, @NotNull ItemStackKey key);
}
