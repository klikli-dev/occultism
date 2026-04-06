package com.klikli_dev.occultism.common.misc;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;

public record ItemStackKey(ItemStack stack) {

    public static final Codec<ItemStackKey> CODEC = ItemStack.CODEC.xmap(ItemStackKey::new, ItemStackKey::stack);

    public static ItemStackKey of(ItemStack stack) {
        return new ItemStackKey(stack.copyWithCount(1));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemStackKey key &&
                this.stack.isEmpty() == key.stack.isEmpty() &&
                ItemStack.isSameItemSameComponents(this.stack, key.stack);
    }

    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(this.stack);
    }
}
