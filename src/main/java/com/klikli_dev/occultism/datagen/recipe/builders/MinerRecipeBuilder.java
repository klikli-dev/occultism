package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MinerRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final Ingredient ingredient;
    @Nullable
    private String group;
    private WeightedRecipeResult result;
    private boolean allowEmpty;
    private boolean addResultItemExistsCondition;

    public MinerRecipeBuilder(Ingredient ingredient, WeightedRecipeResult result) {
        this.ingredient = ingredient;
        this.result = result;
        this.allowEmpty = false;
        this.addResultItemExistsCondition = false;
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, ItemLike output, int weight) {
        return new MinerRecipeBuilder(ingredient, WeightedItemRecipeResult.of(new ItemStack(output), weight));
    }

    public static MinerRecipeBuilder minerRecipe(ItemLike ingredient, ItemLike output, int weight) {
        return minerRecipe(Ingredient.of(ingredient), output, weight);
    }

    public static MinerRecipeBuilder minerRecipe(TagKey<Item> ingredient, ItemLike output, int weight) {
        return minerRecipe(Ingredient.of(ingredient), output, weight);
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, TagKey<Item> output, int weight) {
        return new MinerRecipeBuilder(ingredient, WeightedTagRecipeResult.of(output, 1, weight));
    }

    public static MinerRecipeBuilder minerRecipe(TagKey<Item> ingredient, TagKey<Item> output, int weight) {
        return minerRecipe(Ingredient.of(ingredient), output, weight);
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

    @Override
    public Item getResult() {
//            if(output.getItems().length==1)
//                return output.getItems()[0].getItem();
        return null;
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
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ICondition[] conditions = this.getConditions(this.allowEmpty, this.addResultItemExistsCondition, this.ingredient, this.result);

        MinerRecipe recipe = new MinerRecipe(this.ingredient, this.result);
        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.withPrefix("recipes/miner/")), conditions);
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
        if(addItemExistsCondition) {
            ICondition notCondition = getItemExistsCondition(result);
            if(notCondition!=null)
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

    protected ICondition getItemExistsCondition(RecipeResult ingredient) {
        if(ingredient instanceof ItemRecipeResult itemResult) {
            return new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(itemResult.getStack().getItem()));
        }
        if(ingredient instanceof WeightedItemRecipeResult itemResult) {
            return new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(itemResult.getStack().getItem()));
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

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}