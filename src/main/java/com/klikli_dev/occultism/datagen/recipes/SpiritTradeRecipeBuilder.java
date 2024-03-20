package com.klikli_dev.occultism.datagen.recipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpiritTradeRecipeBuilder implements RecipeBuilder {
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<SpiritTradeRecipe> serializer;
    private final Ingredient ingredient;
    private final ItemStack output;
    public SpiritTradeRecipeBuilder(Ingredient ingredient, ItemStack output) {
        this.serializer = SpiritTradeRecipe.SERIALIZER;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Contract("_, _ -> new")
    public static @NotNull SpiritTradeRecipeBuilder spiritTradeRecipe(Ingredient ingredient, ItemStack output) {
        return new SpiritTradeRecipeBuilder(ingredient, output);
    }
    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group= pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId))
                .requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/spirit_trade/"+ pRecipeId.getPath()), this.group==null ? "": this.group, this.serializer, this.ingredient, this.output));
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
        private final String group;
        private final RecipeSerializer<SpiritTradeRecipe> serializer;
        private final Ingredient ingredient;
        private final ItemStack output;

        public Result(ResourceLocation id,Advancement.Builder advancement, ResourceLocation advancementId,String group, RecipeSerializer<SpiritTradeRecipe> serializer, Ingredient ingredient, ItemStack output) {
            this.id = id;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.group = group;
            this.serializer = serializer;
            this.ingredient = ingredient;
            this.output = output;

        }
        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }
            var ingredientArray = new JsonArray();
            ingredientArray.add(ingredient.toJson());
            pJson.add("ingredients", ingredientArray);
            var output = new JsonObject();
            output.addProperty("item", this.output.getItem().builtInRegistryHolder().key().location().toString());
            if(this.output.getCount() > 1)
                output.addProperty("count", this.output.getCount());
            pJson.add("result", output);
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
