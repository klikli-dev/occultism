package com.klikli_dev.occultism.common.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public record ItemStackKey(ItemStack stack) {

    public static final Codec<ItemStackKey> CODEC = ItemStack.STRICT_CODEC.xmap(ItemStackKey::new, ItemStackKey::stack);

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
