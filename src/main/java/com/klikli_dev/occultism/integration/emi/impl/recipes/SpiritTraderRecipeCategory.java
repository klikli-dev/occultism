package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.crafting.recipe.TraderRecipeInput;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiritTraderRecipeCategory implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final float chances;
    private final Component trader;

    public SpiritTraderRecipeCategory(RecipeHolder<SpiritTradeRecipe> recipe) {
        this.id = recipe.id();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess())));
        var recipes = Minecraft.getInstance().level.getRecipeManager().getRecipesFor(OccultismRecipes.SPIRIT_TRADE_TYPE.get(),
                new TraderRecipeInput(recipe.value().getIngredients().getFirst().getItems()[0], recipe.value().getTrader()),
                Minecraft.getInstance().level);
        AtomicInteger all = new AtomicInteger();
        recipes.forEach(rs -> all.addAndGet(rs.value().getWeightedResult().weight()));
        this.chances = (float) 100 * recipe.value().getWeightedResult().weight() / all.get();
        this.trader = Component.translatable("job." + recipe.value().getTrader().replace(":","."));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.TRADER_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
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
        return 134;
    }

    @Override
    public int getDisplayHeight() {
        return 38;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgetHolder) {
        widgetHolder.addText(this.trader,getDisplayWidth() / 2, 0,0,false).horizontalAlign(TextWidget.Alignment.CENTER);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, (getDisplayWidth() / 2) - 12, 11);
        widgetHolder.addSlot(this.input.get(0), (getDisplayWidth() / 2) - 32, 10);
        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(this.output.get(0), (getDisplayWidth() / 2) + 16, 10).recipeContext(this);
        widgetHolder.addText(Component.translatable("occultism.jei.spirit_trader.chance", String.format(Locale.US, "%.2f", this.chances)),
                getDisplayWidth() / 2, getDisplayHeight() - 8,0,false).horizontalAlign(TextWidget.Alignment.CENTER);
    }

    private static final List<EmiIngredient> tiers = List.of(
            EmiIngredient.of(Ingredient.of(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_SAPLING_TRADER.get()))),
            EmiIngredient.of(Ingredient.of(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERSTONE_TRADER.get()))),
            EmiIngredient.of(Ingredient.of(new ItemStack(OccultismItems.RITUAL_DUMMY_SUMMON_FOLIOT_OTHERROCK_TRADER.get())))
    );

    @Override
    public List<EmiIngredient> getCatalysts() {
        return tiers;
    }
}