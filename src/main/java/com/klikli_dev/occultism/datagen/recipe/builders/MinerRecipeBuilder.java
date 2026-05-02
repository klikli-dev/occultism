package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.RegisteredCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MinerRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final Ingredient ingredient;
    private final WeightedRecipeResult result;
    private final Provider registries;
    @Nullable
    private String group;
    private boolean allowEmpty;
    private boolean addResultItemExistsCondition;

    public MinerRecipeBuilder(Ingredient ingredient, WeightedRecipeResult result, Provider registries) {
        this.ingredient = ingredient;
        this.result = result;
        this.allowEmpty = false;
        this.addResultItemExistsCondition = false;
        this.registries = registries;
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, ItemLike output, int weight, Provider registries) {
        return new MinerRecipeBuilder(ingredient, WeightedItemRecipeResult.of(new ItemStackTemplate(output.asItem()), weight), registries);
    }

    public static MinerRecipeBuilder minerRecipe(ItemLike ingredient, ItemLike output, int weight, Provider registries) {
        return minerRecipe(Ingredient.of(ingredient), output, weight, registries);
    }

    public static MinerRecipeBuilder minerRecipe(TagKey<Item> ingredient, ItemLike output, int weight, Provider registries) {
        return minerRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output, weight, registries);
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, TagKey<Item> output, int weight, Provider registries) {
        return new MinerRecipeBuilder(ingredient, WeightedTagRecipeResult.of(output, 1, weight), registries);
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, TagKey<Item> output, int weight, int count, Provider registries) {
        return new MinerRecipeBuilder(ingredient, WeightedTagRecipeResult.of(output, count, weight), registries);
    }

    public static MinerRecipeBuilder minerRecipe(TagKey<Item> ingredient, TagKey<Item> output, int weight, Provider registries) {
        return minerRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output, weight, registries);
    }

    public static MinerRecipeBuilder minerRecipe(TagKey<Item> ingredient, TagKey<Item> output, int weight, int count, Provider registries) {
        return minerRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), output, weight, count, registries);
    }


    @Override
    public MinerRecipeBuilder unlockedBy(String s, Criterion<?> criterionTriggerInstance) {
        this.criteria.put(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    public Item getResult() {
//            if(output.getItems().length==1)
//                return output.getItems()[0].getItem();
        return null;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("crafting"));
    }

    public MinerRecipeBuilder allowEmpty() {
        this.allowEmpty = true;
        return this;
    }

    public MinerRecipeBuilder addResultItemExistsCondition() {
        this.addResultItemExistsCondition = true;
        return this;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceKey<Recipe<?>> pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(Builder.recipe(pId))
                .requirements(Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);

        MinerRecipe recipe = new MinerRecipe(this.ingredient, this.result);
        ICondition[] conditions = this.getConditions(this.allowEmpty, this.addResultItemExistsCondition, this.ingredient, this.result);
        RecipeOutput output = conditions.length > 0 ? pRecipeOutput.withConditions(conditions) : pRecipeOutput;
        output.accept(pId, recipe, advancement$builder.build(pId.identifier().withPrefix("recipes/miner/")));
    }

    public void save(RecipeOutput p_176499_) {
        throw new IllegalStateException("Recipe must be saved with a unique ID");
    }


    public void save(RecipeOutput p_176501_, String p_176502_) {
        throw new IllegalStateException("Recipe must be saved with a unique ID");
    }

    protected ICondition[] getConditions(boolean allowEmpty, boolean addItemExistsCondition, Ingredient ingredient, RecipeResult result) {
        List<ICondition> conditions = new ArrayList<>();
        if (!allowEmpty) {
            ICondition notCondition = this.getNoTagCondition(ingredient);
            if (notCondition != null)
                conditions.add(notCondition);
            notCondition = this.getNoTagCondition(result);
            if (notCondition != null)
                conditions.add(notCondition);
        }
        if (addItemExistsCondition) {
            ICondition notCondition = this.getItemExistsCondition(result);
            if (notCondition != null)
                conditions.add(notCondition);
        }
        return conditions.toArray(new ICondition[0]);
    }

    protected ICondition getNoTagCondition(Ingredient ingredient) {
        if (!ingredient.isCustom()) {
            return ingredient.getValues().unwrapKey().<ICondition>map(tag -> new NotCondition(new TagEmptyCondition(tag))).orElse(null);
        }
        return null;
    }

    protected ICondition getItemExistsCondition(RecipeResult ingredient) {
        if (ingredient instanceof ItemRecipeResult itemResult) {
            return new RegisteredCondition<>(ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(itemResult.getStack().getItem())));
        }
        if (ingredient instanceof WeightedItemRecipeResult itemResult) {
            return new RegisteredCondition<>(ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(itemResult.getStack().getItem())));
        }
        return null;
    }

    protected ICondition getNoTagCondition(RecipeResult result) {
        if (result instanceof TagRecipeResult tagResult) {
            return new NotCondition(new TagEmptyCondition(tagResult.tag()));
        }
        if (result instanceof WeightedTagRecipeResult tagResult) {
            return new NotCondition(new TagEmptyCondition(tagResult.tag()));
        }
        return null;
    }

    private void ensureValid(ResourceKey<Recipe<?>> pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
