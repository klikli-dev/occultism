package com.klikli_dev.occultism.datagen.builders;

import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class RitualRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
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
    private CompoundTag entityNbt;
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
    public RitualRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s,criterion);
        return this;
    }

    @Override
    public RitualRecipeBuilder group(@Nullable String s) {
        this.group=s;
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
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        RitualRecipe recipe = new RitualRecipe(this.pentacleId,this.ritualType,this.ritualDummy,this.output,entityToSummon,this.entityNbt,this.activationIngredient,this.ingredients,this.duration,this.spiritMaxAge==null?-1:this.spiritMaxAge,this.spiritJobType,this.entityToSacrifice==null?null:new RitualRecipe.EntityToSacrifice(this.entityToSacrifice,this.entityToSacrificeDisplayName),this.itemToUse,this.command);
        pRecipeOutput.accept(pId, recipe,advancement$builder.build(pId.withPrefix("recipes/ritual/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
