// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.crafting.recipe.result;

import com.klikli_dev.occultism.registry.OccultismRecipeResults;
import com.klikli_dev.theurgy.util.TagUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

/**
 * A tag result for recipes that use tags as output.
 */
public class TagRecipeResult extends RecipeResult {

    public static final MapCodec<TagRecipeResult> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
            TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(t -> t.tag),
            Codec.INT.fieldOf("count").forGetter(t -> t.count),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(t -> t.patch)
    ).apply(builder, TagRecipeResult::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TagRecipeResult> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM)),
            t -> t.tag,
            ByteBufCodecs.INT,
            t -> t.count,
            DataComponentPatch.STREAM_CODEC,
            t -> t.patch,
            TagRecipeResult::new
    );

    private final TagKey<Item> tag;
    private final int count;
    private final DataComponentPatch patch;

    @Nullable
    protected ItemStack cachedOutputStack;

    @Nullable
    private ItemStack[] cachedStacks;

    public TagRecipeResult(TagKey<Item> tag, int count) {
        this(tag, count, DataComponentPatch.EMPTY);
    }


    public TagRecipeResult(TagKey<Item> tag, int count, DataComponentPatch patch) {
        this.tag = tag;
        this.count = count;
        this.patch = patch;
    }

    public TagKey<Item> tag() {
        return this.tag;
    }

    public int count() {
        return this.count;
    }

    public DataComponentPatch patch() {
        return this.patch;
    }

    @Override
    public ItemStack getStack() {
        if (this.cachedOutputStack == null) {
            var item = TagUtil.getItemStackForTag(this.tag).copy();
            item.setCount(this.count);
            item.applyComponents(this.patch);

            if (item.isEmpty()) {
                item = new ItemStack(Blocks.BARRIER);
                item.set(DataComponents.CUSTOM_NAME, Component.literal("Empty Tag: " + this.tag.location()));
            }

            this.cachedOutputStack = item;
        }
        return this.cachedOutputStack;
    }

    @Override
    public ItemStack[] getStacks() {
        if (this.cachedStacks == null) {
            //get all items in tag
            this.cachedStacks = BuiltInRegistries.ITEM.getTag(this.tag)
                    .map(tag -> tag.stream().map(ItemStack::new).toArray(ItemStack[]::new))
                    .orElse(new ItemStack[0]);
        }
        return this.cachedStacks;
    }

    @Override
    public RecipeResultType<?> getType() {
        return OccultismRecipeResults.TAG.get();
    }

    @Override
    public RecipeResult copyWithCount(int count) {
        return new TagRecipeResult(this.tag, count, this.patch);
    }
}
