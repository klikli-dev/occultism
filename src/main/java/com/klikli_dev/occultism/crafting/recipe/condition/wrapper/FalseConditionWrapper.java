package com.klikli_dev.occultism.crafting.recipe.condition.wrapper;

import com.klikli_dev.occultism.crafting.recipe.condition.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.condition.ConditionWrapper;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.FalseCondition;
import net.neoforged.neoforge.common.conditions.ICondition;

public class FalseConditionWrapper implements ConditionWrapper<FalseCondition> {
    private final FalseCondition condition;

    public FalseConditionWrapper(FalseCondition condition) {
        this.condition = condition;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, ICondition.IContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public FalseCondition condition() {
        return condition;
    }
}
