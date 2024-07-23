package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpiritTradeRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private final Ingredient ingredient;
    private final ItemStack output;

    public SpiritTradeRecipeBuilder(@Nullable Ingredient ingredient, ItemStack output) {
        this.ingredient = ingredient;
        this.output = output;
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(Ingredient ingredient, ItemStack output) {
        return new SpiritTradeRecipeBuilder(ingredient, output);
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String pCriterionName, @NotNull Criterion<?> pCriterionTrigger) {
        this.criteria.put(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.output.getItem();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, @NotNull ResourceLocation pId) {
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        SpiritTradeRecipe recipe = new SpiritTradeRecipe(this.ingredient, this.output);

        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.withPrefix("recipes/spirit_trade/")));

    }
}
