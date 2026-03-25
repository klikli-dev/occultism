package com.klikli_dev.occultism.datagen.recipe.builders;


import com.klikli_dev.occultism.crafting.recipe.CrystallizeRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.TagRecipeResult;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrystallizeRecipeBuilder implements RecipeBuilder {

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final RecipeSerializer<CrystallizeRecipe> serializer;
    private final Ingredient ingredient;
    private final int crystallizeTime;
    private RecipeResult result;
    @Nullable
    private String group;
    private boolean ignoreCrystallizeMultiplier;
    private int minTier;
    private int maxTier;
    private boolean allowEmpty;
    private final HolderLookup.Provider registries;

    public CrystallizeRecipeBuilder(Ingredient ingredient, RecipeResult result, int crystallizeTime, HolderLookup.Provider registries) {
        this.serializer = OccultismRecipes.CRYSTALLIZE.get();
        this.ingredient = ingredient;
        this.allowEmpty = false;
        this.crystallizeTime = crystallizeTime;
        this.result = result;
        this.minTier = -1;
        this.maxTier = -1;
        this.registries = registries;
    }

    public static CrystallizeRecipeBuilder crystallizeRecipe(TagKey<Item> ingredient, ItemLike result, int crystallizeTime, HolderLookup.Provider registries) {
        return crystallizeRecipe(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), result, crystallizeTime, registries);
    }

    public static CrystallizeRecipeBuilder crystallizeRecipe(Ingredient ingredient, ItemLike result, int crystallizeTime, HolderLookup.Provider registries) {
        return new CrystallizeRecipeBuilder(ingredient, RecipeResult.of(new ItemStack(result)), crystallizeTime, registries);
    }

    public static CrystallizeRecipeBuilder crystallizeRecipe(Item item, TagKey<Item> result, int crystallizeTime, HolderLookup.Provider registries) {
        return new CrystallizeRecipeBuilder(Ingredient.of(item), TagRecipeResult.of(result), crystallizeTime, registries);
    }

    public static CrystallizeRecipeBuilder crystallizeRecipe(Item item, ItemLike result, int crystallizeTime, HolderLookup.Provider registries) {
        return new CrystallizeRecipeBuilder(Ingredient.of(item), RecipeResult.of(new ItemStack(result)), crystallizeTime, registries);
    }

    public static CrystallizeRecipeBuilder crystallizeRecipe(TagKey<Item> ingredient, TagKey<Item> result, int crystallizeTime, HolderLookup.Provider registries) {
        return new CrystallizeRecipeBuilder(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ingredient)), TagRecipeResult.of(result), crystallizeTime, registries);
    }

    @Override
    public @NotNull CrystallizeRecipeBuilder unlockedBy(@NotNull String s, @NotNull Criterion<?> criterionTriggerInstance) {
        this.criteria.put(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public @NotNull CrystallizeRecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        if (this.result.getStacks().length == 1)
            return this.result.getStack().getItem();
        return Items.AIR;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("crafting"));
    }

    public CrystallizeRecipeBuilder allowEmpty() {
        this.allowEmpty = true;
        return this;
    }

    public boolean isAllowEmpty() {
        return this.allowEmpty;
    }

    public CrystallizeRecipeBuilder setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        return this;
    }

    public CrystallizeRecipeBuilder setResultAmount(int outputAmount) {
        this.result = this.result.copyWithCount(outputAmount);
        return this;
    }

    public int getCrystallizeTime() {
        return this.crystallizeTime;
    }

    public int getMinTier() {
        return this.minTier;
    }

    public CrystallizeRecipeBuilder setMinTier(int minTier) {
        this.minTier = minTier;
        return this;
    }

    public int getMaxTier() {
        return this.maxTier;
    }

    public CrystallizeRecipeBuilder setMaxTier(int maxTier) {
        this.maxTier = maxTier;
        return this;
    }

    public boolean getIgnoreCrystallizeMultiplier() {
        return this.ignoreCrystallizeMultiplier;
    }

    public CrystallizeRecipeBuilder setIgnoreCrystallizeMultiplier(boolean ignoreCrystallizeMultiplier) {
        this.ignoreCrystallizeMultiplier = ignoreCrystallizeMultiplier;
        return this;
    }


    @Override
    public void save(@NotNull RecipeOutput pRecipeOutput, @NotNull ResourceKey<Recipe<?>> pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId.location()))
                .rewards(AdvancementRewards.Builder.recipe(pId.location()))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ICondition[] conditions = this.getConditions(this.allowEmpty, this.ingredient, this.result);

        CrystallizeRecipe recipe = new CrystallizeRecipe(this.ingredient, this.result, this.minTier, this.maxTier, this.crystallizeTime, this.ignoreCrystallizeMultiplier);
        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.location().withPrefix("recipes/crystallize/")), conditions);
    }

    protected ICondition[] getConditions(boolean allowEmpty, Ingredient ingredient, RecipeResult result) {
        List<ICondition> conditions = new ArrayList<>();
        if (!allowEmpty) {
            ICondition notCondition = this.getNoTagCondition(ingredient);
            if (notCondition != null)
                conditions.add(notCondition);
            notCondition = this.getNoTagCondition(result);
            if (notCondition != null)
                conditions.add(notCondition);
        }
        return conditions.toArray(new ICondition[0]);
    }

    protected ICondition getNoTagCondition(Ingredient ingredient) {
        if (ingredient.getValues().length == 1 && ingredient.getValues()[0] instanceof Ingredient.TagValue tagValue) {
            return new NotCondition(new TagEmptyCondition(tagValue.tag()));
        }
        return null;
    }

    protected ICondition getNoTagCondition(RecipeResult result) {
        if (result instanceof TagRecipeResult tagResult) {
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