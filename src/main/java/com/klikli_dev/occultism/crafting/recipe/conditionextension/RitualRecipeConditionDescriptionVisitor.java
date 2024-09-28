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

import java.util.Arrays;

public class RitualRecipeConditionDescriptionVisitor implements ConditionVisitor{
    @Override
    public MutableComponent visit(AndConditionWrapper condition, OccultismConditionContext context) {
        var contained = Component.empty();

        var children = condition.condition().children();
        for (int i = 0; i < children.size(); i++) {
            var c = ConditionWrapperFactory.wrap(children.get(i)).accept(this, context);
            if (i == 0) {
                contained.append("\n\n").append("(").append(c).append(")");
            } else {
                contained.append("\n AND \n").append("(").append(c).append(")");
            }
        }

        return Component.translatable(TranslationKeys.Condition.Ritual.AND_DESCRIPTION, contained);
    }

    @Override
    public MutableComponent visit(OrConditionWrapper condition, OccultismConditionContext context) {
        var contained = Component.empty();

        var children = condition.condition().values();
        for (int i = 0; i < children.size(); i++) {
            var c = ConditionWrapperFactory.wrap(children.get(i)).accept(this, context);
            if (i == 0) {
                contained.append("\n\n").append("(").append(c).append(")");
            } else {
                contained.append("\n OR \n").append("(").append(c).append(")");
            }
        }

        return Component.translatable(TranslationKeys.Condition.Ritual.OR_DESCRIPTION, contained);
    }

    @Override
    public MutableComponent visit(TrueConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(TranslationKeys.Condition.Ritual.TRUE_DESCRIPTION);
    }

    @Override
    public MutableComponent visit(FalseConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(TranslationKeys.Condition.Ritual.FALSE_DESCRIPTION);
    }

    @Override
    public MutableComponent visit(NotConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.NOT_DESCRIPTION,
                //get the description of the wrapped condition
                ConditionWrapperFactory.wrap(condition.condition().value()).accept(this, context)
        );
    }

    @Override
    public MutableComponent visit(ItemExistsConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.ITEM_EXISTS_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("item", condition.condition().getItem()))
        );
    }

    @Override
    public MutableComponent visit(ModLoadedConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.MOD_LOADED_DESCRIPTION,
                condition.condition().modid()
        );
    }

    @Override
    public MutableComponent visit(TagEmptyConditionWrapper condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.TAG_EMPTY_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("tag", condition.condition().tag().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInBiomeCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_BIOME_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("biome", condition.biome().unwrapKey().orElseThrow().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInBiomeWithTagCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_BIOME_WITH_TAG_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("tag", condition.tag().location()))
        );
    }

    @Override
    public MutableComponent visit(IsInDimensionCondition condition, OccultismConditionContext context) {
       return Component.translatable(
               TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_DESCRIPTION,
               Component.translatable(Util.makeDescriptionId("dimension", condition.dimension().location()))
       );
    }

    @Override
    public MutableComponent visit(IsInDimensionTypeCondition condition, OccultismConditionContext context) {
        return Component.translatable(
                TranslationKeys.Condition.Ritual.IS_IN_DIMENSION_TYPE_DESCRIPTION,
                Component.translatable(Util.makeDescriptionId("dimension_type", condition.dimensionType().unwrapKey().orElseThrow().location()))
        );
    }
}
