package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinerRecipeCategory implements EmiRecipe {
    public static Map<TagKey<Item>, Long> totalWeights = new HashMap<>();
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final double chances;

    public MinerRecipeCategory(RecipeHolder<MinerRecipe> recipe) {
        this.id = recipe.id();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(0)));

        var stack = EmiStack.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess()));
        if (recipe.value().getIngredients().get(0).getValues().length == 1) {
            if (recipe.value().getIngredients().get(0).getValues()[0] instanceof Ingredient.TagValue) {
                double chance = (double) recipe.value().getWeightedResult().getWeight().asInt() / totalWeights.get(((Ingredient.TagValue) recipe.value().getIngredients().get(0).getValues()[0]).tag());
                stack.setChance((float) chance);
            }
        }
        this.chances = (double) recipe.value().getWeightedResult().weight() / 100;
        this.output = List.of(stack);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.MINER_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return this.input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return this.output;
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 28;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgetHolder) {
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);
        widgetHolder.addSlot(this.input.get(0), 0, 0);
        widgetHolder.addText(Component.translatable("occultism.jei.miner.chance", chances),getDisplayWidth() / 2, getDisplayHeight() - 8,0,false).horizontalAlign(TextWidget.Alignment.CENTER);
        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(this.output.get(0), 58, 0).recipeContext(this);
    }
}