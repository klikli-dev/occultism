package com.klikli_dev.occultism.common.misc;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.transfer.ItemAccessResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class LavaResourceHandler extends ItemAccessResourceHandler<FluidResource> {
    private final ItemStack stack;
    public LavaResourceHandler(ItemStack itemStack, ItemAccess itemAccess) {
        super(itemAccess, 1);
        this.stack = itemStack;
    }

    @Override
    protected @NonNull FluidResource getResourceFrom(@NonNull ItemResource accessResource, int index) {
        return FluidResource.of(Fluids.LAVA);
    }

    @Override
    protected int getAmountFrom(ItemResource accessResource, int index) {
        int durability = accessResource.toStack().getMaxDamage() - accessResource.toStack().getDamageValue();
        return durability*125;
    }

    @Override
    protected ItemResource update(ItemResource accessResource, int index, @NonNull FluidResource newResource, int newAmount) {
        ItemStack stack = accessResource.toStack();
        if (stack.isDamageableItem()) {
            double i = stack.getMaxDamage() * (1-((double) newAmount / getCapacity(index, newResource)));
            stack.setDamageValue((int) i);
        }
        return ItemResource.of(stack);
    }

    @Override
    protected int getCapacity(int index, @NonNull FluidResource resource) {
        Objects.checkIndex(index, size());
        return this.stack.isDamageableItem() ? 125 * stack.getMaxDamage() : Integer.MAX_VALUE;
    }
}