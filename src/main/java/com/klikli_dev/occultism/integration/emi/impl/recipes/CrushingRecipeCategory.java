package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrushingRecipeCategory implements EmiRecipe {
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    private final Integer min;
    private final Integer max;

    public CrushingRecipeCategory(RecipeHolder<CrushingRecipe> recipe) {
        id=recipe.id();
        this.min = recipe.value().getMinTier();
        this.max = recipe.value().getMaxTier();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess())));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.CRUSHING_CATEGORY;
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

    public Integer getMin() {
        return this.min;
    }

    public Integer getMax() {
        return this.max;
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
        widgetHolder.addSlot(input.get(0), 0, 7);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW,18,7);
        int y = 0;
        int s = 12;
        EntityType spiritType;
        if(getMin() <= 1) {
            y = 10;
            s = 16;
            spiritType = OccultismEntities.FOLIOT.get();
        } else if(getMin() == 2){
            spiritType = OccultismEntities.DJINNI.get();
        } else if(getMin() == 3){
            spiritType = OccultismEntities.AFRIT.get();
        } else {
            spiritType = OccultismEntities.MARID.get();
        }
        SpiritWidget widget = new SpiritWidget(53, y, spiritType,s).tooltip((mouseX, mouseY) ->
        {
            List<ClientTooltipComponent> tooltip = new ArrayList<>();
            if(getMin() >= 1) {
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.crushing.min_tier", getMin()).getVisualOrderText()));
            }
            if(getMax() >= 1) {
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.crushing.max_tier", getMax()).getVisualOrderText()));
            }
            return tooltip;
        });

        widgetHolder.add(widget);
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW,64,7);
        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(output.get(0), 90, 7).recipeContext(this);
    }

    private static final List<EmiIngredient> tiers = List.of(
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_marid_crusher"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_afrit_crusher"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_djinni_crusher"))))),
            EmiIngredient.of(Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ritual_dummy/summon_foliot_crusher")))))
    );

    @Override
    public List<EmiIngredient> getCatalysts() {
        return tiers;
    }
}