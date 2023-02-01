package com.github.klikli_dev.occultism.common.misc;

import com.github.klikli_dev.occultism.integration.almostunified.AlmostUnifiedIntegration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A class that makes it easier to use ingredients as output and handles AlmostUnified Integration
 */
public class OutputIngredient {

    protected Ingredient ingredient;
    protected ItemStack[] itemStacks;

    public OutputIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public ItemStack getStack() {
        //copied from Ingredient.dissolve, but modified to handle tag ingredient preferred items
        if (this.itemStacks == null) {
            this.itemStacks = Arrays.stream(this.ingredient.values).flatMap((value) -> {
                if (value instanceof Ingredient.TagValue tagValue) {
                    if (AlmostUnifiedIntegration.isLoaded()) {
                        return Stream.of(AlmostUnifiedIntegration.getPreferredItemForTag(tagValue.tag));
                    }
                }
                return value.getItems().stream();
            }).distinct().toArray(ItemStack[]::new);
        }
        return this.itemStacks[0];
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }
}
