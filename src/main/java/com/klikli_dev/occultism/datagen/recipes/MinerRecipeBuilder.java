package com.klikli_dev.occultism.datagen.recipes;

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
    private final Ingredient output;
    private final int weight;
    private boolean allowEmpty;

    public MinerRecipeBuilder(Ingredient ingredient, Ingredient output, int weight) {
        this.serializer = MinerRecipe.SERIALIZER;
        this.ingredient = ingredient;
        this.output = output;
        this.weight = weight;
        this.allowEmpty=false;
    }
    public static MinerRecipeBuilder minerRecipe(Ingredient ingredient, Ingredient output, int weight) {
        return new MinerRecipeBuilder(ingredient, output, weight);
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
            if(output.getItems().length==1)
                return output.getItems()[0].getItem();
            return null;
    }

    public MinerRecipeBuilder allowEmpty() {
        this.allowEmpty = true;
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(RequirementsStrategy.OR);
        consumer.accept(new MinerRecipeBuilder.Result(resourceLocation,this.advancement, new ResourceLocation(resourceLocation.getNamespace(),"recipes/miner/"+resourceLocation.getPath()), this.group ==null ? "": this.group, this.serializer, this.ingredient, this.output, this.weight,this.allowEmpty));
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
        private final Ingredient output;
        private final int weight;

        private final boolean allowEmpty;

        public Result(ResourceLocation id,Advancement.Builder advancement,ResourceLocation advancementId, @Nullable String group, RecipeSerializer<MinerRecipe> serializer, Ingredient ingredient, Ingredient output, int weight, boolean allowEmpty) {
            this.id=id;
            this.advancement = advancement;
            this.advancementId=advancementId;
            this.group = group;
            this.serializer = serializer;
            this.ingredient = ingredient;
            this.output = output;
            this.weight = weight;
            this.allowEmpty=allowEmpty;
        }
        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.add("ingredient", this.ingredient.toJson());
            var output=new JsonObject();
            if(this.output.values.length==1 && this.output.values[0] instanceof Ingredient.TagValue) {
                output.addProperty("tag", ((Ingredient.TagValue) this.output.values[0]).tag.location().toString());
                jsonObject.add("result", output);
            }
            else
                jsonObject.add("result", ((Ingredient.ItemValue)this.output.values[0]).serialize());

            if(!this.allowEmpty){
                var conditions=new JsonArray();
                if(this.output.values.length==1 && this.output.values[0] instanceof Ingredient.TagValue){
                    conditions.add(makeTagNotEmptyCondition(((Ingredient.TagValue)this.output.values[0]).tag.location().toString()));
                }
                // conditions.add(makeTagNotEmptyCondition(this.ingredient.values[0].));
                jsonObject.add("conditions", conditions);
            }

            jsonObject.addProperty("weight", this.weight);

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
