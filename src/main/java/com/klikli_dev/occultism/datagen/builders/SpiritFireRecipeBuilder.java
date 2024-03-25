package com.klikli_dev.occultism.datagen.builders;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpiritFireRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<SpiritFireRecipe> serializer;
    private final Ingredient ingredient;
    private final ItemStack output;

    public SpiritFireRecipeBuilder(Ingredient ingredient, ItemStack output) {
        this.serializer = SpiritFireRecipe.SERIALIZER;
        this.ingredient = ingredient;
        this.output = output;
    }
    public static SpiritFireRecipeBuilder spiritFireRecipe(Ingredient ingredient, ItemStack output) {
        return new SpiritFireRecipeBuilder(ingredient, output);
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, Criterion<?> pCriterionTrigger) {
        this.criteria.put(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.output.getItem();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        SpiritFireRecipe recipe=new SpiritFireRecipe(ingredient,output);
        pRecipeOutput.accept(pId, recipe,advancement$builder.build(pId.withPrefix("recipes/spirit_fire/")));

    }

    }