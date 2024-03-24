package com.klikli_dev.occultism.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.common.misc.WeightedOutputIngredient;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MinerRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<MinerRecipe> serializer;

    private final Ingredient ingredient;
    @Nullable
    private final ResourceLocation outputItem;
    @Nullable
    private final String outputTag;
    private final int weight;
    private boolean allowEmpty;
    private boolean itemExists;

    public MinerRecipeBuilder(Ingredient ingredient, @Nullable ResourceLocation outputItem, @Nullable String outputTag, int weight) {
        this.serializer = MinerRecipe.SERIALIZER;
        this.ingredient = ingredient;
        this.outputItem = outputItem;
        this.outputTag = outputTag;
        this.weight = weight;
        this.allowEmpty=false;
        this.itemExists=false;
    }
    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, Ingredient output, int weight) {
        if(output.values.length==1 && output.values[0] instanceof Ingredient.ItemValue) {
            var item=output.getItems()[0].getItem().builtInRegistryHolder().key().location();
            return new MinerRecipeBuilder(ingredient, item, null, weight);
        } else if(output.values.length==1 && output.values[0] instanceof Ingredient.TagValue) {
            return new MinerRecipeBuilder(ingredient, null, ((Ingredient.TagValue) output.values[0]).tag().location().toString(), weight);
        }
        return null;
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, ResourceLocation output, int weight) {
        return new MinerRecipeBuilder(ingredient, output, null, weight);
    }

    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, String outputTag, int weight) {
        return new MinerRecipeBuilder(ingredient, null, outputTag, weight);
    }

    @Override
    public MinerRecipeBuilder unlockedBy(String s, Criterion<?> criterionTriggerInstance) {
        this.criteria.put(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        this.group=s;
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

    public MinerRecipeBuilder itemExists() {
        this.itemExists = true;
        return this;
    }
    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ICondition[] conditions=getConditions(allowEmpty,itemExists,ingredient,this.outputTag);
        Ingredient output;
        JsonObject json = new JsonObject();
        if(this.outputItem!=null) {
            json.addProperty("item", this.outputItem.toString());
        } else {
            json.addProperty("tag", this.outputTag);
        }
        output=Ingredient.fromJson(json,false);
        MinerRecipe recipe=new MinerRecipe(ingredient,new WeightedOutputIngredient(output,this.weight));
        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.withPrefix("recipes/crushing/")),conditions);

    }

    public void save(RecipeOutput p_176499_) {
        throw new IllegalStateException("Recipe must be saved with a unique ID");
    }


    public void save(RecipeOutput p_176501_, String p_176502_) {
        throw new IllegalStateException("Recipe must be saved with a unique ID");
    }

    protected ICondition[] getConditions(boolean allowEmpty,boolean itemExists, Ingredient ingredient, String output) {
        List<ICondition> conditions=new ArrayList<>();
        if(!allowEmpty && output!=null) {

            ICondition notCondition = getNoTagCondition(output);
            if(notCondition!=null)
                conditions.add(notCondition);
        }
        if(itemExists) {
            ICondition notCondition = getItemExistsCondition(ingredient);
            if(notCondition!=null)
                conditions.add(notCondition);
        }
        return conditions.toArray(new ICondition[0]);
    }

    protected ICondition getNoTagCondition(String ingredient) {
        return new NotCondition(new TagEmptyCondition(ingredient));

    }
    protected ICondition getItemExistsCondition(Ingredient ingredient) {
        if(ingredient.values.length==1 && ingredient.values[0] instanceof Ingredient.ItemValue itemValue) {
            return new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(itemValue.item().getItem()));
        }
        return null;
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}