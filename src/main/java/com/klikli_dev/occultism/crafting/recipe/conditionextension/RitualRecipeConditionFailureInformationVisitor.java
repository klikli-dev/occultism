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

public class RitualRecipeConditionFailureInformationVisitor implements ConditionVisitor {
    @Override
    public MutableComponent visit(AndConditionWrapper condition, OccultismConditionContext context) {
        var contained = Component.empty();

        var children = condition.condition().children();
        boolean isFirst = true;
        for (var child : children) {
            //ignore conditions that are fulfilled
            if (condition.condition().test(context))
                continue;

            var c = ConditionWrapperFactory.wrap(child).accept(this, context);
            if (isFirst) {
                isFirst = false;
                contained.append("\n\n").append("(").append(c).append(")");
            } else {
                contained.append("\n AND \n").append("(").append(c).append(")");
            }
        }

        return Component.translatable(TranslationKeys.Condition.Ritual.AND_NOT_FULFILLED, contained);
    }

    @Override
    public MutableComponent visit(OrConditionWrapper condition, OccultismConditionContext context) {
        var contained = Component.empty();

        var children = condition.condition().values();
        boolean isFirst = true;
        for (var child : children) {
            //ignore conditions that are fulfilled
            if (condition.condition().test(context))
                continue;

            var c = ConditionWrapperFactory.wrap(child).accept(this, context);
            if (isFirst) {
                isFirst = false;
                contained.append("\n\n").append("(").append(c).append(")");
            } else {
                contained.append("\n OR \n").append("(").append(c).append(")");
            }
        }

        return Component.translatable(TranslationKeys.Condition.Ritual.OR_NOT_FULFILLED, contained);
    }

    @Override
    public MutableComponent visit(TrueConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(TranslationKeys.Condition.Ritual.TRUE_NOT_FULFILLED);
    }

    @Override
    public MutableComponent visit(FalseConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(TranslationKeys.Condition.Ritual.FALSE_NOT_FULFILLED);
    }

    @Override
    public MutableComponent visit(NotConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.NOT_NOT_FULFILLED,
                //get the description of the wrapped condition
                ConditionWrapperFactory.wrap(condition.condition().value()).accept(this, context)
        );
    }

    @Override
    public MutableComponent visit(ItemExistsConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.ITEM_EXISTS_NOT_FULFILLED,
                Component.translatable(Util.makeDescriptionId("item", condition.condition().getItem()))
        );
    }

    @Override
    public MutableComponent visit(ModLoadedConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.MOD_LOADED_NOT_FULFILLED,
                condition.condition().modid()
        );
    }

    @Override
    public MutableComponent visit(TagEmptyConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.TAG_EMPTY_NOT_FULFILLED,
                Component.translatable(Util.makeDescriptionId("tag", condition.condition().tag().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInBiomeCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_BIOME_NOT_FULFILLED,
                Component.translatable(Util.makeDescriptionId("biome", condition.biome().unwrapKey().orElseThrow().location())),
                Component.translatable(Util.makeDescriptionId("biome", context.biome().unwrapKey().orElseThrow().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInBiomeWithTagCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_NOT_FULFILLED,
                Component.translatable(Util.makeDescriptionId("tag", condition.tag().location())),
                Component.translatable(Util.makeDescriptionId("biome", context.biome().unwrapKey().orElseThrow().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInDimensionCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("dimension", condition.dimension().location())),
                Component.translatable(Util.makeDescriptionId("dimension", context.dimension().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInDimensionTypeCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_NOT_FULFILLED,
                Component.translatable(Util.makeDescriptionId("dimension_type", condition.dimensionType().unwrapKey().orElseThrow().location())),
                Component.translatable(Util.makeDescriptionId("dimension_type", context.dimensionType().unwrapKey().orElseThrow().location()))
        );
    }
}
