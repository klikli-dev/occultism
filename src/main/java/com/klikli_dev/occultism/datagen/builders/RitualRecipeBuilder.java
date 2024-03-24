package com.klikli_dev.occultism.datagen.builders;

import com.google.gson.*;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.function.Consumer;

public class RitualRecipeBuilder implements RecipeBuilder {
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<RitualRecipe> serializer;
    private final Ingredient activationIngredient;
    private final ItemStack output;
    private final ResourceLocation ritualType;
    private final ItemStack ritualDummy;
    private final int duration;
    private final NonNullList<Ingredient> ingredients;
    private final ResourceLocation pentacleId;

    @Nullable
    private String group;
    @Nullable
    private  ResourceLocation spiritJobType;
    @Nullable
    private TagKey<EntityType<?>> entityToSacrifice;
    @Nullable
    private  EntityType<?> entityToSummon;
    @Nullable
    private  CompoundTag entityNbt;
    @Nullable
    private  Ingredient itemToUse;
    @Nullable
    private Integer spiritMaxAge;
    @Nullable
    private String entityToSacrificeDisplayName;
    @Nullable
    private String command;

    public RitualRecipeBuilder(Ingredient activationIngredient, NonNullList<Ingredient> ingredients, ItemStack output,ItemStack ritualDummy,int duration,ResourceLocation ritualType, ResourceLocation pentacleId) {
        this.serializer = RitualRecipe.SERIALIZER;
        this.activationIngredient = activationIngredient;
        this.output = output;
        this.ritualDummy=ritualDummy;
        this.duration=duration;
        this.ritualType=ritualType;
        this.ingredients=ingredients;
        this.pentacleId=pentacleId;
    }
    public static RitualRecipeBuilder ritualRecipeBuilder(Ingredient activationIngredient, ItemStack output,ItemStack ritualDummy,int duration,ResourceLocation ritualType, ResourceLocation pentacleId,Ingredient... ingredients)
    {
        NonNullList<Ingredient> ingredientsList = NonNullList.create();
        Collections.addAll(ingredientsList, ingredients);
        return new RitualRecipeBuilder(activationIngredient,ingredientsList,output,ritualDummy,duration,ritualType,pentacleId);
    }

    @Override
    public RitualRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RitualRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    public RitualRecipeBuilder spiritJobType(ResourceLocation spiritJobType){
        this.spiritJobType=spiritJobType;
        return this;
    }
    public RitualRecipeBuilder entityToSacrifice(TagKey<EntityType<?>> entityToSacrifice){
        this.entityToSacrifice=entityToSacrifice;
        return this;
    }

    public RitualRecipeBuilder entityToSummon(EntityType<?> entityToSummon){
        this.entityToSummon=entityToSummon;
        return this;
    }

    public RitualRecipeBuilder entityNbt(CompoundTag entityNbt){
        this.entityNbt=entityNbt;
        return this;
    }

    public RitualRecipeBuilder itemToUse(Ingredient itemToUse){
        this.itemToUse=itemToUse;
        return this;
    }

    public RitualRecipeBuilder spiritMaxAge(int spiritMaxAge){
        this.spiritMaxAge=spiritMaxAge;
        return this;
    }

    public RitualRecipeBuilder entityToSacrificeDisplayName(String entityToSacrificeDisplayName){
        this.entityToSacrificeDisplayName=entityToSacrificeDisplayName;
        return this;
    }

    public RitualRecipeBuilder command(String command){
        this.command=command;
        return this;
    }



    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId))
                .requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, new ResourceLocation(pRecipeId.getNamespace(),"recipes/ritual/"+pRecipeId.getPath()),this.advancement, this.group==null?"":this.group, this.serializer, this.activationIngredient, this.output, this.pentacleId, this.ritualType, this.spiritJobType, this.ritualDummy, this.entityToSacrifice, this.entityToSummon, this.entityNbt, this.itemToUse, this.duration, this.spiritMaxAge, this.entityToSacrificeDisplayName, this.command, this.ingredients));
    }
    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ResourceLocation advancementId;
        private final Advancement.Builder advancement;
        private final String group;
        private final RecipeSerializer<RitualRecipe> serializer;
        private final Ingredient activationIngredient;
        private final ItemStack output;

        private final ResourceLocation pentacleId;
        private  final ResourceLocation ritualType;
        private  final ResourceLocation spiritJobType;
        private  final ItemStack ritualDummy;
        private final TagKey<EntityType<?>> entityToSacrifice;
        private  final EntityType<?> entityToSummon;

        private  final CompoundTag entityNbt;
        private  final Ingredient itemToUse;
        private final int duration;
        private final Integer spiritMaxAge;
        private final String entityToSacrificeDisplayName;
        private final String command;

        private final NonNullList<Ingredient> ingredients;

        public Result(ResourceLocation id, ResourceLocation advancementId, Advancement.Builder advancement, String group, RecipeSerializer<RitualRecipe> serializer, Ingredient activationIngredient, ItemStack output, ResourceLocation pentacleId, ResourceLocation ritualType, ResourceLocation spiritJobType, ItemStack ritualDummy, TagKey<EntityType<?>> entityToSacrifice, EntityType<?> entityToSummon, CompoundTag entityNbt, Ingredient itemToUse, int duration, Integer spiritMaxAge, String entityToSacrificeDisplayName, String command, NonNullList<Ingredient> ingredients) {
            this.id = id;
            this.advancementId = advancementId;
            this.advancement = advancement;
            this.group = group;
            this.serializer = serializer;
            this.activationIngredient = activationIngredient;
            this.output = output;
            this.pentacleId = pentacleId;
            this.ritualType = ritualType;
            this.spiritJobType = spiritJobType;
            this.ritualDummy = ritualDummy;
            this.entityToSacrifice = entityToSacrifice;
            this.entityToSummon = entityToSummon;
            this.entityNbt = entityNbt;
            this.itemToUse = itemToUse;
            this.duration = duration;
            this.spiritMaxAge = spiritMaxAge;
            this.entityToSacrificeDisplayName = entityToSacrificeDisplayName;
            this.command = command;
            this.ingredients = ingredients;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray ingredientsArray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                ingredientsArray.add(ingredient.toJson());
            }
            pJson.add("ingredients", ingredientsArray);

            pJson.add("result", RecipeNBTHelper.serializeItemStack(this.output));

            pJson.addProperty("ritual_type",this.ritualType.toString());
            if(this.entityToSummon!=null) {
                pJson.addProperty("entity_to_summon", this.entityToSummon.builtInRegistryHolder().key().location().toString());
            }

            if(entityNbt!=null) {
                CompoundTag.CODEC.encodeStart(JsonOps.INSTANCE, this.entityNbt).result().ifPresent((jsonElement -> {
                    pJson.add("entity_nbt", jsonElement);
                }));
            }
            pJson.add("activation_item",this.activationIngredient.toJson());
            pJson.addProperty("pentacle_id",this.pentacleId.toString());
            pJson.addProperty("duration",this.duration);
            if(this.spiritMaxAge!=null)
                pJson.addProperty("spirit_max_age",this.spiritMaxAge);
            if(this.spiritJobType!=null)
                pJson.addProperty("spirit_job_type",this.spiritJobType.toString());
            if(this.ritualDummy!=null){
                pJson.add("ritual_dummy", RecipeNBTHelper.serializeItemStack(this.ritualDummy));
            }
            if(this.entityToSacrifice!=null) {
                var entityToSacrificeJson = new JsonObject();
                entityToSacrificeJson.addProperty("display_name", this.entityToSacrificeDisplayName);
                entityToSacrificeJson.addProperty("tag", this.entityToSacrifice.location().toString());
                pJson.add("entity_to_sacrifice", entityToSacrificeJson);
            }
            if(this.itemToUse!=null) {
                pJson.add("item_to_use", this.itemToUse.toJson());
            }
            if(this.command!=null) {
                pJson.addProperty("command", this.command);
            }

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
            return advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
