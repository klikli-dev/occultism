package com.klikli_dev.occultism.crafting.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerRecipeInput implements RecipeInput {

    protected final ResourceHandler<ItemResource> inv;

    public ItemHandlerRecipeInput(ResourceHandler<ItemResource> inv) {
        this.inv = inv;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return this.inv.getResource(slot).toStack(this.inv.getAmountAsInt(slot));
    }

    @Override
    public int size() {
        return this.inv.size();
    }
}
