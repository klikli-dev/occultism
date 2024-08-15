package com.klikli_dev.occultism.integration.emi.recipes;

import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.integration.emi.OccultismEmiPlugin;
import com.klikli_dev.occultism.registry.OccultismTags;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinerRecipeCategory implements EmiRecipe {
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    public static Map<TagKey<Item>,Long> totalWeights=new HashMap<>();

    public MinerRecipeCategory(@NotNull MinerRecipe recipe) {
        id=recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)));
        var stack=EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        if(recipe.getIngredients().get(0).values.length==1) {
            if(recipe.getIngredients().get(0).values[0] instanceof Ingredient.TagValue) {
                double chance = (double)((double)recipe.getWeightedOutput().getWeight().asInt()/totalWeights.get(((Ingredient.TagValue)recipe.getIngredients().get(0).values[0]).tag));
                stack.setChance((float) chance);
            }
        }
        this.output = List.of(stack);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.MINER_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
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
        return 18;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgetHolder) {
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);
        widgetHolder.addSlot(input.get(0), 0, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(output.get(0), 58, 0).recipeContext(this);
    }
}
