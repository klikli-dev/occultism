package com.klikli_dev.occultism.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CrushingRecipeBuilder implements RecipeBuilder {

    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<CrushingRecipe> serializer;
    private final Ingredient ingredient;
    @Nullable
    private ResourceLocation outputTag;
    @Nullable
    private Item outputItem;
    private int outputAmount;
    private final int crushingTime;
    private boolean ignoreCrushingMultiplier;
    private int minTier;
    private boolean allowEmpty;

    public CrushingRecipeBuilder(Ingredient ingredient, int crushingTime) {
        this.serializer = OccultismRecipes.CRUSHING.get();
        this.ingredient=ingredient;
        this.allowEmpty=false;
        this.crushingTime=crushingTime;
        this.outputAmount=1;
        minTier=-1;
    }

    public static CrushingRecipeBuilder crushingRecipe(Ingredient ingredient, int crushingTime) {
        return new CrushingRecipeBuilder(ingredient, crushingTime);
    }
    public static CrushingRecipeBuilder crushingRecipe(TagKey<Item> ingredient, int crushingTime) {
        return new CrushingRecipeBuilder(Ingredient.of(ingredient), crushingTime);
    }

    @Override
    public CrushingRecipeBuilder unlockedBy(String s, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public CrushingRecipeBuilder group(@Nullable String s) {
        this.group=s;
        return this;
    }

    @Override
    public Item getResult() {
        if(outputItem!=null)
            return outputItem;
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("tag", outputTag.toString());
        Ingredient ingredient = Ingredient.fromJson(jsonobject);
        var output=new OutputIngredient(ingredient);
        return output.getStack().getItem();
    }

    public CrushingRecipeBuilder allowEmpty() {
        this.allowEmpty=true;
        return this;
    }

    public boolean isAllowEmpty() {
        return this.allowEmpty;
    }

    public CrushingRecipeBuilder setOutputAmount(int outputAmount) {
        this.outputAmount=outputAmount;
        return this;
    }


    public CrushingRecipeBuilder setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty=allowEmpty;
        return this;
    }
    public int getCrushingTime() {
        return this.crushingTime;
    }
    public int getMinTier() {
        return this.minTier;
    }
    public CrushingRecipeBuilder setMinTier(int minTier) {
        this.minTier=minTier;
        return this;
    }
    public boolean getIgnoreCrushingMultiplier() {
        return this.ignoreCrushingMultiplier;
    }
    public CrushingRecipeBuilder setIgnoreCrushingMultiplier(boolean ignoreCrushingMultiplier) {
        this.ignoreCrushingMultiplier=ignoreCrushingMultiplier;
        return this;
    }
    public CrushingRecipeBuilder setOutputItem(Item item) {
        this.outputItem=item;
        return this;
    }
    public CrushingRecipeBuilder setOutputTag(ResourceLocation item) {
        this.outputTag=item;
        return this;
    }


    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(resourceLocation, this.group==null?"":this.group, this.serializer, this.advancement,new ResourceLocation(resourceLocation.getNamespace(),"recipes/crushing/"+resourceLocation.getPath()), this.ingredient, this.outputItem,this.outputTag, this.crushingTime, this.ignoreCrushingMultiplier, this.minTier, this.allowEmpty,this.outputAmount));
    }
    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final RecipeSerializer<CrushingRecipe> serializer;
        private final Advancement.Builder advancement;
        private final Ingredient ingredient;
        @Nullable
        private final Item outputItem;
        @Nullable
        private final ResourceLocation outputTag;
        private final int crushingTime;
        private final boolean ignoreCrushingMultiplier;
        private final int minTier;
        private final boolean allowEmpty;
        private final ResourceLocation advancementId;
        @Nullable
        private final int outputAmount;
        public Result(ResourceLocation id, String group, RecipeSerializer<CrushingRecipe> serializer,Advancement.Builder advancement,ResourceLocation advancementId, Ingredient ingredient, @Nullable Item outputItem,@Nullable ResourceLocation outputTag, int crushingTime, boolean ignoreCrushingMultiplier, int minTier, boolean allowEmpty, int outputAmount) {
            this.id = id;
            this.group = group;
            this.serializer=serializer;
            this.advancement=advancement;
            this.ingredient=ingredient;
            this.outputItem=outputItem;
            this.outputTag=outputTag;
            this.crushingTime=crushingTime;
            this.ignoreCrushingMultiplier=ignoreCrushingMultiplier;
            this.minTier=minTier;
            this.allowEmpty=allowEmpty;
            this.advancementId=advancementId;
            this.outputAmount=outputAmount;
        }

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.add("ingredient", this.ingredient.toJson());
            var output=new JsonObject();
            if(this.outputTag!=null) {
                output.addProperty("tag", this.outputTag.toString());
            } else {
                output.addProperty("item", this.outputItem.builtInRegistryHolder().key().toString());
            }
            if(!this.allowEmpty){
                var conditions=new JsonArray();
                conditions.add(makeTagNotEmptyCondition(this.outputTag.toString()));
                if(ingredient.values.length==1 && ingredient.values[0] instanceof Ingredient.TagValue){
                    conditions.add(makeTagNotEmptyCondition(((Ingredient.TagValue)ingredient.values[0]).tag.location().toString()));
                }
               // conditions.add(makeTagNotEmptyCondition(this.ingredient.values[0].));
                jsonObject.add("conditions", conditions);
            }
            output.addProperty("count", outputAmount);
            jsonObject.add("result", output);
            jsonObject.addProperty("crushing_time", this.crushingTime);
            jsonObject.addProperty("ignore_crushing_multiplier", this.ignoreCrushingMultiplier);
            if(minTier!=-1)
                jsonObject.addProperty("min_tier", this.minTier);


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

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
