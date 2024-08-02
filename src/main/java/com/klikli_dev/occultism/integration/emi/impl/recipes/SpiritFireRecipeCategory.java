package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import com.klikli_dev.occultism.registry.OccultismItems;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritFireRecipeCategory implements EmiRecipe {

    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    public SpiritFireRecipeCategory(RecipeHolder<SpiritFireRecipe> recipe) {
        id=recipe.id();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess())));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.SPIRIT_FIRE_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76+25;
    }

    @Override
    public int getDisplayHeight() {
        return 18+16;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {

        // Add an arrow texture to indicate processing
        widgetHolder.addTexture(new EmiTexture(OccultismEmiPlugin.EMI_WIDGETS,16,0,16,16),26 , 0);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW,20+27,16);

        //widgetHolder.addTexture(new EmiTexture(OccultismEmiPlugin.EMI_WIDGETS,0,0,16,16), 26, 16);
        //widgetHolder.addAnimatedTexture(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/block/spirit_fire_0.png"), 0, 0, 16, 16, 0, 0,20,false,true,true);
        widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(OccultismItems.SPIRIT_FIRE.get())), 24, 16);
        // Adds an input slot on the left
        widgetHolder.addSlot(input.get(0), 0, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(output.get(0), 58+20, 16).recipeContext(this);
    }
}