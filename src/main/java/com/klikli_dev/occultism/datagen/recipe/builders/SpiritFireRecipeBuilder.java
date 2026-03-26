package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpiritFireRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<SpiritFireRecipe> serializer;
    private final Ingredient ingredient;
    private final ItemStackTemplate output;

    public SpiritFireRecipeBuilder(Ingredient ingredient, ItemStackTemplate output) {
        this.serializer = OccultismRecipes.SPIRIT_FIRE.get();
        this.ingredient = ingredient;
        this.output = output;
    }
    public static SpiritFireRecipeBuilder spiritFireRecipe(Ingredient ingredient, ItemStackTemplate output) {
        return new SpiritFireRecipeBuilder(ingredient, output);
    }

    public static SpiritFireRecipeBuilder spiritFireRecipe(Ingredient ingredient, ItemStack output) {
        return new SpiritFireRecipeBuilder(ingredient, ItemStackTemplate.fromNonEmptyStack(output));
    }

    public static SpiritFireRecipeBuilder spiritFireRecipe(TagKey<Item> ingredient, ItemStack output, HolderLookup.Provider registries) {
        return spiritFireRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output);
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, Criterion<?> pCriterionTrigger) {
        this.criteria.put(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public Item getResult() {
        return this.output.item().value();
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("crafting"));
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceKey<Recipe<?>> pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        SpiritFireRecipe recipe = new SpiritFireRecipe(ingredient, output);
        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.identifier().withPrefix("recipes/spirit_fire/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
