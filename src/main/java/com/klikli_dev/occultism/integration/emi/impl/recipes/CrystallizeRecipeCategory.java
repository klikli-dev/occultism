package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.CrystallizeRecipe;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import com.klikli_dev.occultism.integration.emi.impl.render.SpiritWidget;
import com.klikli_dev.occultism.registry.OccultismEntities;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrystallizeRecipeCategory implements EmiRecipe {
    private static final List<EmiIngredient> tiers = List.of(
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_marid_crystallizer"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_crystallizer"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_crystallizer"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    Identifier.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_crystallizer")))))
    );
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final Integer min;
    private final Integer max;
    private final Boolean multiplyOutput;

    public CrystallizeRecipeCategory(RecipeHolder<CrystallizeRecipe> recipe) {
        this.id = recipe.id();
        this.min = recipe.value().getMinTier();
        this.max = recipe.value().getMaxTier();
        this.multiplyOutput = !recipe.value().getIgnoreCrystallizeMultiplier();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess())));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.CRYSTALLIZE_CATEGORY;
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

    public Integer getMin() {
        return this.min;
    }

    public Integer getMax() {
        return this.max;
    }

    public Boolean getIfMultiplyOutput() {
        return this.multiplyOutput;
    }

    @Override
    public int getDisplayWidth() {
        return 110;
    }

    @Override
    public int getDisplayHeight() {
        return 30;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addSlot(this.input.get(0), 0, 7);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 18, 7);
        int y = 0;
        int s = 12;
        EntityType spiritType;
        if (this.getMin() <= 1) {
            y = 10;
            s = 16;
            spiritType = OccultismEntities.FOLIOT.get();
        } else if (this.getMin() == 2) {
            spiritType = OccultismEntities.DJINNI.get();
        } else if (this.getMin() == 3) {
            spiritType = OccultismEntities.AFRIT.get();
        } else {
            spiritType = OccultismEntities.MARID.get();
        }
        SpiritWidget widget = new SpiritWidget(53, y, spiritType, s).tooltip((mouseX, mouseY) ->
        {
            List<ClientTooltipComponent> tooltip = new ArrayList<>();
            if (this.getMin() >= 1) {
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.crystallize.min_tier", this.getMin()).getVisualOrderText()));
            }
            if (this.getMax() >= 1) {
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.crystallize.max_tier", this.getMax()).getVisualOrderText()));
            }
            if (this.getIfMultiplyOutput()) {
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.crystallize.multiply_output").getVisualOrderText()));
            }
            return tooltip;
        });

        widgetHolder.add(widget);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 64, 7);
        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(this.output.get(0), 90, 7).recipeContext(this);
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return tiers;
    }
}