package com.klikli_dev.occultism.datagen.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MinerRecipeBuilder implements RecipeBuilder {

    private final Advancement.Builder advancement = Advancement.Builder.advancement();
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
            return new MinerRecipeBuilder(ingredient, null, ((Ingredient.TagValue) output.values[0]).tag.location().toString(), weight);
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
    public MinerRecipeBuilder unlockedBy(String s, CriterionTriggerInstance criterionTriggerInstance) {
        advancement.addCriterion(s,criterionTriggerInstance);
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
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(RequirementsStrategy.OR);
        consumer.accept(new MinerRecipeBuilder.Result(resourceLocation,this.advancement, new ResourceLocation(resourceLocation.getNamespace(),"recipes/miner/"+resourceLocation.getPath()), this.group ==null ? "": this.group, this.serializer, this.ingredient, this.outputItem, this.outputTag, this.weight,this.allowEmpty, this.itemExists));
    }

    public void save(Consumer<FinishedRecipe> p_176499_) {
       throw new IllegalStateException("Recipe must be saved with a unique ID");
    }


    public void save(Consumer<FinishedRecipe> p_176501_, String p_176502_) {
        throw new IllegalStateException("Recipe must be saved with a unique ID");
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        @Nullable
        private String group;
        private final RecipeSerializer<MinerRecipe> serializer;

        private final Ingredient ingredient;
        @Nullable
        private final ResourceLocation outputItem;
        @Nullable
        private final String outputTag;
        private final int weight;

        private final boolean allowEmpty;
        private final boolean itemExists;

        public Result(ResourceLocation id,Advancement.Builder advancement,ResourceLocation advancementId, @Nullable String group, RecipeSerializer<MinerRecipe> serializer, Ingredient ingredient, ResourceLocation outputItem, String outputTag, int weight, boolean allowEmpty, boolean itemExists) {
            this.id=id;
            this.advancement = advancement;
            this.advancementId=advancementId;
            this.group = group;
            this.serializer = serializer;
            this.ingredient = ingredient;
            this.outputItem = outputItem;
            this.outputTag = outputTag;
            this.weight = weight;
            this.allowEmpty=allowEmpty;
            this.itemExists=itemExists;
        }
        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.add("ingredient", this.ingredient.toJson());
            var output=new JsonObject();
            if(outputTag!=null) {
                output.addProperty("tag", outputTag);
                jsonObject.add("result", output);
            }
            else {
                var item = new JsonObject();
                item.addProperty("item", outputItem.toString());

                jsonObject.add("result", item);
            }

            if(!this.allowEmpty){
                var conditions=new JsonArray();
                if(this.outputTag!=null){
                    conditions.add(makeTagNotEmptyCondition(this.outputTag));
                }
                // conditions.add(makeTagNotEmptyCondition(this.ingredient.values[0].));
                jsonObject.add("conditions", conditions);
            }
            if(this.itemExists) {
                var conditions=new JsonArray();

                conditions.add(makeItemExistsCondition(this.outputItem));
                jsonObject.add("conditions", conditions);
            }

            jsonObject.addProperty("weight", this.weight);

        }

        public JsonObject makeItemExistsCondition(ResourceLocation item) {
            var condition = new JsonObject();
            condition.addProperty("type", "forge:item_exists");
            condition.addProperty("item", item.toString());
            return condition;
        }

        public JsonObject makeTagNotEmptyCondition(String tag) {
            var condition = new JsonObject();
            condition.addProperty("type", "forge:not");
            var value = new JsonObject();
            value.addProperty("type", "forge:tag_empty");
            value.addProperty("tag", tag);
            condition.add("value", value);
            return condition;
        }
        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
