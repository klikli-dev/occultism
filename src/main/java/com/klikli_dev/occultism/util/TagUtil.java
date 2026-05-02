// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.integration.almostunified.AlmostUnifiedIntegration;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.stream.StreamSupport;


public class TagUtil {

    @Nullable
    public static Item getItemForTag(TagKey<Item> tag) {
        var item = AlmostUnifiedIntegration.get().getPreferredItemForTag(tag);

        return item != null ? item :
                StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(tag).spliterator(), false)
                        .map(Holder::value).findFirst().orElse(null);
    }

    public static ItemStack getItemStackForTag(TagKey<Item> tag) {
        var item = getItemForTag(tag);
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @Nullable
    public static Block getBlockForTag(TagKey<Block> tag) {
        return StreamSupport.stream(BuiltInRegistries.BLOCK.getTagOrEmpty(tag).spliterator(), false)
                .map(Holder::value).findFirst().orElse(null);
    }

    public static ItemStack getItemStackForBlockTag(TagKey<Block> tag) {
        var item = getBlockForTag(tag);
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }
}
