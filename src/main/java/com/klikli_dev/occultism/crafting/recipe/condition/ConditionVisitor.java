package com.klikli_dev.occultism.crafting.recipe.condition;

import com.klikli_dev.occultism.crafting.recipe.condition.wrapper.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface ConditionVisitor {
    MutableComponent visit(AndConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(OrConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(TrueConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(FalseConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(NotConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(ItemExistsConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(ModLoadedConditionWrapper condition, ICondition.IContext context);

    MutableComponent visit(TagEmptyConditionWrapper condition, ICondition.IContext context);
}
