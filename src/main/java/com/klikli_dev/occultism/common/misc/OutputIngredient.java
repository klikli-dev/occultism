package com.klikli_dev.occultism.common.misc;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.integration.almostunified.AlmostUnifiedIntegration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.CraftingHelper;
import net.neoforged.neoforge.common.crafting.NBTIngredient;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class that makes it easier to use ingredients as output and handles AlmostUnified Integration
 */
public class OutputIngredient extends Ingredient {
    public static final Codec<OutputIngredient> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(
                            NeoForgeExtraCodecs.singularOrPluralCodec(Value.CODEC, "item").forGetter(OutputIngredient::getContainedValues),
                            CompoundTag.CODEC.optionalFieldOf("nbt",new CompoundTag()).forGetter(OutputIngredient::nbt),
                            ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(OutputIngredient::count))
                        .apply(builder, OutputIngredient::new));

    public static final Codec<OutputIngredient> CODEC_NONEMPTY = RecordCodecBuilder.create(
            builder -> builder
                    .group(
                            NeoForgeExtraCodecs.singularOrPluralCodecNotEmpty(Value.CODEC, "item").forGetter(OutputIngredient::getContainedValues),
                            CompoundTag.CODEC.optionalFieldOf("nbt",new CompoundTag()).forGetter(OutputIngredient::nbt),
                            ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(OutputIngredient::count))
                    .apply(builder, OutputIngredient::new));

    //While we take the item from the ingredient, we take the count and nbt form this

    protected ItemStack cachedOutputStack;
    private int count;
    private CompoundTag nbt;


    public Set<Value> getContainedValues() {
    return Arrays.stream(getValues()).collect(Collectors.toSet());
}

    public OutputIngredient(Set<? extends Ingredient.Value> ingredients, CompoundTag nbt, int count) {
        super(ingredients.stream());

        this.count=count;
        this.nbt=nbt;
    }
    public static OutputIngredient of(ItemStack item) {
        return new OutputIngredient(Set.of(new Ingredient.ItemValue(item)), item.getTag(), item.getCount());
    }
    public static OutputIngredient of(Ingredient ingredient){
        return of(ingredient,new CompoundTag(),1);
    }
    public static OutputIngredient of(Ingredient ingredient,int count){
        return of(ingredient,new CompoundTag(),count);
    }
    public static OutputIngredient of(Ingredient ingredient,CompoundTag nbt,int count){
        Set<? extends Ingredient.Value> values = Arrays.stream(ingredient.getValues()).collect(Collectors.toSet());
        return new OutputIngredient(values, nbt, count);
    }

    public int count() {
        return this.count;
    }
    public CompoundTag nbt() {
        return this.nbt;
    }

    public ItemStack getStack() {
        //copied from Ingredient.dissolve, but modified to handle tag ingredient preferred items
        if (this.cachedOutputStack == null) {

            var itemStacks = Arrays.stream(this.values).flatMap((value) -> {
                if (value instanceof Ingredient.TagValue tagValue) {
                    var item = AlmostUnifiedIntegration.get().getPreferredItemForTag(tagValue.tag());

                    if (item == null)
                        item = BuiltInRegistries.ITEM.getTag(tagValue.tag())
                                .map(HolderSet.ListBacked::stream)
                                .flatMap(Stream::findFirst)
                                .map(Holder::value)
                                .orElse(null);

                    if (item != null) {
                        return Stream.of(new ItemStack(item));
                    }

                    //copied from Ingredient.TagValue.getItems to handle empty tags
                    Occultism.LOGGER.error("Empty Tag: {}", tagValue.tag().location());
                    return Stream.of(new ItemStack(Blocks.BARRIER).setHoverName(Component.literal("Empty Tag: " + tagValue.tag().location())));
                }
                return value.getItems().stream();
            }).distinct().toArray(ItemStack[]::new);

            var outputStack = itemStacks[0].copy();
            outputStack.setCount(this.count());
            outputStack.setTag(this.nbt());
            this.cachedOutputStack = outputStack;
        }
        return this.cachedOutputStack;
    }

    public Ingredient getIngredient() {
        return this;
    }
}
