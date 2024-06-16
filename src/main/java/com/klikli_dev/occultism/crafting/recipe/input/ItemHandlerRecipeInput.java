package com.klikli_dev.occultism.crafting.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerRecipeInput implements RecipeInput {

    protected final IItemHandlerModifiable inv;

    public ItemHandlerRecipeInput(IItemHandlerModifiable inv) {
        this.inv = inv;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return this.inv.getStackInSlot(slot);
    }

    @Override
    public int size() {
        return this.inv.getSlots();
    }
}