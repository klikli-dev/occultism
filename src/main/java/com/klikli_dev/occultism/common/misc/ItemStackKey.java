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
        return obj instanceof ItemStackKey key && ItemStack.isSameItemSameComponents(this.stack, key.stack);
    }

    @Override
    public int hashCode() {
        int result = this.stack.getItem().hashCode();

        if (!this.stack.getComponents().isEmpty()) {
            var compound = new CompoundTag();
            for (var entry : this.stack.getComponents()) {
                var tag = entry.encodeValue(NbtOps.INSTANCE).getOrThrow();
                var key = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(entry.type());

                compound.put(key.toString(), tag);
            }

            result = this.hashCode(compound, result);
        }

        return result;
    }

    private int hashCode(Tag tag, int result) {
        if (tag instanceof CompoundTag) {
            result = this.hashCode((CompoundTag) tag, result);
        } else if (tag instanceof ListTag) {
            result = this.hashCode((ListTag) tag, result);
        } else {
            result = 31 * result + tag.hashCode();
        }

        return result;
    }

    private int hashCode(CompoundTag tag, int result) {
        for (String key : tag.getAllKeys()) {
            result = 31 * result + key.hashCode();
            result = this.hashCode(tag.get(key), result);
        }

        return result;
    }

    private int hashCode(ListTag tag, int result) {
        for (Tag tagItem : tag) {
            result = this.hashCode(tagItem, result);
        }

        return result;
    }
}
