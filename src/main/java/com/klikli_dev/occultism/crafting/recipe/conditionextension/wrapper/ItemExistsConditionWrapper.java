package com.klikli_dev.occultism.crafting.recipe.conditionextension.wrapper;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapper;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.RegisteredCondition;

public record ItemExistsConditionWrapper(
        RegisteredCondition<?> condition) implements ConditionWrapper<RegisteredCondition<?>> {

    @Override
    public MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context) {
        return visitor.visit(this, context);
    }
}
