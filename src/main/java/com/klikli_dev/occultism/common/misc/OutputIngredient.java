package com.klikli_dev.occultism.common.misc;

import com.klikli_dev.modonomicon.util.Codecs;
import com.klikli_dev.occultism.integration.almostunified.AlmostUnifiedIntegration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class that makes it easier to use ingredients as output and handles AlmostUnified Integration
 */
public class OutputIngredient {

    protected Ingredient ingredient;
    //While we take the item from the ingredient, we take the count and nbt form this
    protected OutputStackInfo outputStackInfo;
    protected ItemStack cachedOutputStack;
    public OutputIngredient(Ingredient ingredient) {
        this(ingredient, new OutputStackInfo(1, DataComponentPatch.EMPTY));
        //have to use dirt, because EMPTY/AIR will cause output count to be 0
    }


    /**
     * Creates a new OutputIngredient
     *
     * @param ingredient      the ingredient to source the output item from
     * @param outputStackInfo the additional required stack info (count, nbt) will be read from this
     */
    public OutputIngredient(Ingredient ingredient, OutputStackInfo outputStackInfo) {
        this.ingredient = ingredient;
        this.outputStackInfo = outputStackInfo;
    }

    public ItemStack getStack() {
        //copied from Ingredient.dissolve, but modified to handle tag ingredient preferred items
        if (this.cachedOutputStack == null) {

            var itemStacks = Arrays.stream(this.ingredient.getValues()).flatMap((value) -> {
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
                    return Stream.of(new ItemStack(Blocks.BARRIER).set(DataComponents.CUSTOM_NAME, Component.literal("Empty Tag: " + tagValue.tag().location())));
                }
                return value.getItems().stream();
            }).distinct().toArray(ItemStack[]::new);

            var outputStack = itemStacks[0].copy();
            outputStack.setCount(this.outputStackInfo.count());
            outputStack.applyComponents(this.outputStackInfo.components());
            this.cachedOutputStack = outputStack;
        }
        return this.cachedOutputStack;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public OutputStackInfo getOutputStackInfo() {
        return this.outputStackInfo;
    }

    public record OutputStackInfo(int count, DataComponentPatch components) {
        public static final Codec<OutputStackInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(OutputStackInfo::count),
                DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(OutputStackInfo::components)
        ).apply(instance, OutputStackInfo::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, OutputStackInfo> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                OutputStackInfo::count,
                DataComponentPatch.STREAM_CODEC,
                OutputStackInfo::components,
                OutputStackInfo::new
        );
    }
}
