package com.klikli_dev.occultism.datagen.recipe.builders;

import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe.EntityToSacrifice;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe.EntityToSummonSettings;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe.RitualRequirementSettings;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe.RitualStartSettings;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RitualRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final Ingredient activationIngredient;
    private final ItemStackTemplate output;
    private final Identifier ritualType;
    private final ItemStackTemplate ritualDummy;
    private final int duration;
    private final NonNullList<Ingredient> ingredients;
    private final Identifier pentacleId;
    private final Provider registries;

    @Nullable
    private Identifier spiritJobType;
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

    public RitualRecipeBuilder(Ingredient activationIngredient, NonNullList<Ingredient> ingredients, ItemStackTemplate output, ItemStackTemplate ritualDummy, int duration, Identifier ritualType, Identifier pentacleId, Provider registries) {
        this.activationIngredient = activationIngredient;
        this.output = output;
        this.ritualDummy = ritualDummy;
        this.duration = duration;
        this.ritualType = ritualType;
        this.ingredients = ingredients;
        this.pentacleId = pentacleId;
        this.registries = registries;
    }

    public static RitualRecipeBuilder ritualRecipeBuilder(Ingredient activationIngredient, ItemStackTemplate output, ItemStackTemplate ritualDummy, int duration, Identifier ritualType, Identifier pentacleId, Provider registries, Ingredient... ingredients) {
        NonNullList<Ingredient> ingredientsList = NonNullList.create();
        Collections.addAll(ingredientsList, ingredients);
        return new RitualRecipeBuilder(activationIngredient, ingredientsList, output, ritualDummy, duration, ritualType, pentacleId, registries);
    }

    public static RitualRecipeBuilder ritualRecipeBuilder(Ingredient activationIngredient, ItemStackTemplate output, ItemStackTemplate ritualDummy, float duration, Identifier ritualType, Identifier pentacleId, Provider registries, Ingredient... ingredients) {
        NonNullList<Ingredient> ingredientsList = NonNullList.create();
        Collections.addAll(ingredientsList, ingredients);
        return new RitualRecipeBuilder(activationIngredient, ingredientsList, output, ritualDummy, (int) duration, ritualType, pentacleId, registries);
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
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace("crafting"));
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, @NotNull ResourceKey<Recipe<?>> pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(Builder.recipe(pId))
                .requirements(Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);

        var recipe = new RitualRecipe(this.ritualType,
                new RitualRequirementSettings(this.pentacleId, this.ingredients, this.activationIngredient, this.duration, this.duration / (float) (this.ingredients.size() + 1)),
                new RitualStartSettings(this.entityToSacrifice == null ? null : new EntityToSacrifice(this.entityToSacrifice, this.entityToSacrificeDisplayName), this.itemToUse, this.condition),
                new EntityToSummonSettings(this.entityToSummon, this.entityTagToSummon, this.entityNbt, this.spiritJobType, this.spiritMaxAge == null ? -1 : this.spiritMaxAge, this.summonNumber == null ? 1 : this.summonNumber),
                this.ritualDummy, this.output, this.command);

        pRecipeOutput.accept(pId, recipe, advancement$builder.build(pId.identifier().withPrefix("recipes/ritual/")));
    }

    public RitualRecipeBuilder spiritJobType(Identifier spiritJobType) {
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

    private void ensureValid(ResourceKey<Recipe<?>> pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}