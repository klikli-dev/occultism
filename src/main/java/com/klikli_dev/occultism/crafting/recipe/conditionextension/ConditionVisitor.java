package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeWithTagCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionTypeCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.wrapper.*;
import net.minecraft.network.chat.MutableComponent;

public interface ConditionVisitor {
    MutableComponent visit(AndConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(OrConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(TrueConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(FalseConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(NotConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(ItemExistsConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(ModLoadedConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(TagEmptyConditionWrapper condition, OccultismConditionContext context);

    MutableComponent visit(IsInBiomeCondition condition, OccultismConditionContext context);

    MutableComponent visit(IsInBiomeWithTagCondition condition, OccultismConditionContext context);

    MutableComponent visit(IsInDimensionCondition condition, OccultismConditionContext context);

    MutableComponent visit(IsInDimensionTypeCondition condition, OccultismConditionContext context);
}
