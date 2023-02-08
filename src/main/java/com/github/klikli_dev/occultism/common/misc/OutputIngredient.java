package com.github.klikli_dev.occultism.common.misc;

import com.github.klikli_dev.occultism.integration.almostunified.AlmostUnifiedIntegration;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A class that makes it easier to use ingredients as output and handles AlmostUnified Integration
 */
public class OutputIngredient {

    protected Ingredient ingredient;

    //While we take the item from the ingredient, we take the count and nbt form this
    protected ItemStack outputStackInfo;
    protected ItemStack cachedOutputStack;


    public OutputIngredient(Ingredient ingredient) {
        this(ingredient, new ItemStack(Items.DIRT, 1));
        //have to use dirt, because EMPTY/AIR will cause output count to be 0
    }

    /**
     * Creates a new OutputIngredient
     * @param ingredient the ingredient to source the output item from
     * @param outputStackInfo the additional required stack info (count, nbt) will be read from this
     */
    public OutputIngredient(Ingredient ingredient, ItemStack outputStackInfo) {
        this.ingredient = ingredient;
        this.outputStackInfo = outputStackInfo;
    }

    public ItemStack getStack() {
        //copied from Ingredient.dissolve, but modified to handle tag ingredient preferred items
        if (this.cachedOutputStack == null) {

            var itemStacks = Arrays.stream(this.ingredient.values).flatMap((value) -> {
                if (value instanceof Ingredient.TagValue tagValue) {
                    var item = AlmostUnifiedIntegration.getPreferredItemForTag(tagValue.tag);

                    if(item == null)
                        item = BuiltInRegistries.ITEM.getTag(tagValue.tag)
                                .map(HolderSet.ListBacked::stream)
                                .flatMap(Stream::findFirst)
                                .map(Holder::value)
                                .orElse(null);

                    if(item != null) {
                        return Stream.of(new ItemStack(item));
                    }

                    //copied from Ingredient.TagValue.getItems to handle empty tags
                    return Stream.of(new ItemStack(Blocks.BARRIER).setHoverName(Component.literal("Empty Tag: " + tagValue.tag.location())));
                }
                return value.getItems().stream();
            }).distinct().toArray(ItemStack[]::new);

            var outputStack = itemStacks[0].copy();
            outputStack.setCount(this.outputStackInfo.getCount());
            outputStack.setTag(this.outputStackInfo.getTag());
            this.cachedOutputStack = outputStack;
        }
        return this.cachedOutputStack;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }
}
