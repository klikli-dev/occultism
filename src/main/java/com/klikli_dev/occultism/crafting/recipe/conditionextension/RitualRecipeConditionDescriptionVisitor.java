package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeWithTagCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionTypeCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.wrapper.*;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class RitualRecipeConditionDescriptionVisitor implements ConditionVisitor{
    @Override
    public MutableComponent visit(AndConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(OrConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(TrueConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(FalseConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(NotConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(ItemExistsConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(ModLoadedConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(TagEmptyConditionWrapper condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(IsInBiomeCondition condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(IsInBiomeWithTagCondition condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(IsInDimensionCondition condition, OccultismConditionContext context) {
        return null;
    }

    @Override
    public MutableComponent visit(IsInDimensionTypeCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("dimension_type", condition.dimensionType().unwrapKey().orElseThrow().location()))
        );
    }
}
