package com.klikli_dev.occultism.datagen.recipe.builders;

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
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RitualRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final Ingredient activationIngredient;
    private final ItemStack output;
    private final ResourceLocation ritualType;
    private final ItemStack ritualDummy;
    private final int duration;
    private final NonNullList<Ingredient> ingredients;
    private final ResourceLocation pentacleId;

    @Nullable
    private ResourceLocation spiritJobType;
    @Nullable
    private TagKey<EntityType<?>> entityToSacrifice;
    @Nullable
    private EntityType<?> entityToSummon;
    @Nullable
    private TagKey<EntityType<?>> entityTagToSummon;
    @Nullable
    private CompoundTag entityNbt;
    @Nullable
    private Ingredient itemToUse;
    @Nullable
    private Integer spiritMaxAge;
    @Nullable
    private Integer summonNumber;
    @Nullable
    private String entityToSacrificeDisplayName;
    @Nullable
    private String command;
    @Nullable
    private ICondition condition;

    public RitualRecipeBuilder(Ingredient activationIngredient, NonNullList<Ingredient> ingredients, ItemStack output, ItemStack ritualDummy, int duration, ResourceLocation ritualType, ResourceLocation pentacleId) {
        this.activationIngredient = activationIngredient;
        this.output = output;
        this.ritualDummy = ritualDummy;
        this.duration = duration;
        this.ritualType = ritualType;
        this.ingredients = ingredients;
        this.pentacleId = pentacleId;
    }

    public static RitualRecipeBuilder ritualRecipeBuilder(Ingredient activationIngredient, ItemStack output, ItemStack ritualDummy, int duration, ResourceLocation ritualType, ResourceLocation pentacleId, Ingredient... ingredients) {
        NonNullList<Ingredient> ingredientsList = NonNullList.create();
        Collections.addAll(ingredientsList, ingredients);
        return new RitualRecipeBuilder(activationIngredient, ingredientsList, output, ritualDummy, duration, ritualType, pentacleId);
    }

    @Override
    public @NotNull RitualRecipeBuilder unlockedBy(@NotNull String s, @NotNull Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public @NotNull RitualRecipeBuilder group(@Nullable String s) {
        //NOOP
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.output.getItem();
    }

    public RitualRecipeBuilder spiritJobType(ResourceLocation spiritJobType) {
        this.spiritJobType = spiritJobType;
        return this;
    }

    public RitualRecipeBuilder entityToSacrifice(TagKey<EntityType<?>> entityToSacrifice) {
        this.entityToSacrifice = entityToSacrifice;
        return this;
    }

    public RitualRecipeBuilder entityToSummon(EntityType<?> entityToSummon) {
        this.entityToSummon = entityToSummon;
        return this;
    }

    public RitualRecipeBuilder entityTagToSummon(TagKey<EntityType<?>> entityTagToSummon) {
        this.entityTagToSummon = entityTagToSummon;
        return this;
    }

    public RitualRecipeBuilder entityNbt(CompoundTag entityNbt) {
        this.entityNbt = entityNbt;
        return this;
    }

    public RitualRecipeBuilder itemToUse(Ingredient itemToUse) {
        this.itemToUse = itemToUse;
        return this;
    }

    public RitualRecipeBuilder spiritMaxAge(int spiritMaxAge) {
        this.spiritMaxAge = spiritMaxAge;
        return this;
    }

    public RitualRecipeBuilder summonNumber(int summonNumber) {
        this.summonNumber = summonNumber;
        return this;
    }

    public RitualRecipeBuilder entityToSacrificeDisplayName(String entityToSacrificeDisplayName) {
        this.entityToSacrificeDisplayName = entityToSacrificeDisplayName;
        return this;
    }

    public RitualRecipeBuilder command(String command) {
        this.command = command;
        return this;
    }

    /**
     * The ritual start condition - this is different from the recipe load condition neoforge adds!
     */
    public RitualRecipeBuilder condition(ICondition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, @NotNull ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);

        var recipe = new RitualRecipe(this.ritualType,
                new RitualRecipe.RitualRequirementSettings(this.pentacleId, this.ingredients, this.activationIngredient, this.duration, this.duration / (float) (this.ingredients.size() + 1)),
                new RitualRecipe.RitualStartSettings(this.entityToSacrifice == null ? null : new RitualRecipe.EntityToSacrifice(this.entityToSacrifice, this.entityToSacrificeDisplayName), this.itemToUse, this.condition),
                new RitualRecipe.EntityToSummonSettings(this.entityToSummon, this.entityTagToSummon, this.entityNbt, this.spiritJobType,this.spiritMaxAge == null ? -1 : this.spiritMaxAge, this.summonNumber == null ? 1 : this.summonNumber),
                this.ritualDummy, this.output, this.command);

        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.withPrefix("recipes/ritual/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
