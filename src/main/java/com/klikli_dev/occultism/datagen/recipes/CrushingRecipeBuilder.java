package com.klikli_dev.occultism.datagen.recipes;


import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrushingRecipeBuilder implements RecipeBuilder {

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<CrushingRecipe> serializer;
    private final Ingredient ingredient;
    private final Ingredient output;
    private int outputAmount;
    private final int crushingTime;
    private boolean ignoreCrushingMultiplier;
    private int minTier;
    private boolean allowEmpty;

    public CrushingRecipeBuilder(Ingredient ingredient, Ingredient output, int crushingTime) {
        this.serializer = OccultismRecipes.CRUSHING.get();
        this.ingredient=ingredient;
        this.allowEmpty=false;
        this.crushingTime=crushingTime;
        this.outputAmount=1;
        this.output = output;
        minTier=-1;
    }

    public static CrushingRecipeBuilder crushingRecipe(Ingredient ingredient, Ingredient output, int crushingTime) {
        return new CrushingRecipeBuilder(ingredient, output,crushingTime);
    }
    public static CrushingRecipeBuilder crushingRecipe(TagKey<Item> ingredient, TagKey<Item>  output, int crushingTime) {
        return new CrushingRecipeBuilder(Ingredient.of(ingredient), Ingredient.of(output), crushingTime);
    }

    @Override
    public @NotNull CrushingRecipeBuilder unlockedBy(@NotNull String s, @NotNull Criterion<?> criterionTriggerInstance) {
        this.criteria.put(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public @NotNull CrushingRecipeBuilder group(@Nullable String s) {
        this.group=s;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        if(output.getItems().length==1)
            return output.getItems()[0].getItem();
        return Items.AIR;
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


    @Override
    public void save(@NotNull RecipeOutput pRecipeOutput, @NotNull ResourceLocation pId) {
    this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ICondition[] conditions=getConditions(allowEmpty,ingredient,output);
        OutputIngredient ingredient1=OutputIngredient.of(output, outputAmount);

        CrushingRecipe recipe = new CrushingRecipe(this.ingredient, ingredient1,this.minTier,this.crushingTime,this.ignoreCrushingMultiplier);
        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.withPrefix("recipes/crushing/")),conditions);
}

    protected ICondition[] getConditions(boolean allowEmpty, Ingredient ingredient, Ingredient output) {
        List<ICondition> conditions=new ArrayList<>();
        if(!allowEmpty) {
            ICondition notCondition = getNoTagCondition(ingredient);
            if(notCondition!=null)
                conditions.add(notCondition);
            notCondition = getNoTagCondition(output);
            if(notCondition!=null)
                conditions.add(notCondition);
        }
        return conditions.toArray(new ICondition[0]);
    }

    protected ICondition getNoTagCondition(Ingredient ingredient) {
        if(ingredient.values.length==1 && ingredient.values[0] instanceof Ingredient.TagValue tagValue) {
            return new NotCondition(new TagEmptyCondition(tagValue.tag()));
        }
        return null;
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}