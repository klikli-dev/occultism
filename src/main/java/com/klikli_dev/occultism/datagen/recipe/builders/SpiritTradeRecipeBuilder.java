package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedItemRecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedTagRecipeResult;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpiritTradeRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private final Ingredient ingredient;
    private final WeightedRecipeResult output;
    //private final ItemStack output;
    private final String trader;
    private final Provider registries;

    public SpiritTradeRecipeBuilder(@Nullable Ingredient ingredient, WeightedRecipeResult output, String trader, Provider registries) {
        this.ingredient = ingredient;
        this.output = output;
        this.trader = trader;
        this.registries = registries;
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(Ingredient ingredient, ItemStack output, int weight, String trader, Provider registries) {
        return new SpiritTradeRecipeBuilder(ingredient, WeightedItemRecipeResult.of(output, weight), trader, registries);
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(Ingredient ingredient, ItemStackTemplate output, int weight, String trader, Provider registries) {
        return new SpiritTradeRecipeBuilder(ingredient, WeightedRecipeResult.of(output, weight), trader, registries);
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(TagKey<Item> ingredient, TagKey<Item> output, int weight, String trader, Provider registries) {
        return new SpiritTradeRecipeBuilder(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), WeightedTagRecipeResult.of(output, 1, weight), trader, registries);
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(TagKey<Item> ingredient, ItemStack output, int weight, String trader, Provider registries) {
        return spiritTradeRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output, weight, trader, registries);
    }

    public static SpiritTradeRecipeBuilder spiritTradeRecipe(TagKey<Item> ingredient, ItemStackTemplate output, int weight, String trader, Provider registries) {
        return spiritTradeRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output, weight, trader, registries);
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

    public Item getResult() {
//        return this.output.getItem();
        return null;
    }

    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("crafting"));
    }

    public void save(RecipeOutput pRecipeOutput, @NotNull ResourceKey<Recipe<?>> pId) {
        this.ensureValid(pId);
        var advancementId = pId.identifier().withPrefix("recipes/spirit_trade/");
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(Builder.recipe(pId))
                .requirements(Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        SpiritTradeRecipe recipe = new SpiritTradeRecipe(this.ingredient, this.output, this.trader);

        pRecipeOutput.accept(pId, recipe, advancement$builder.build(advancementId));
    }

    private void ensureValid(ResourceKey<Recipe<?>> pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
