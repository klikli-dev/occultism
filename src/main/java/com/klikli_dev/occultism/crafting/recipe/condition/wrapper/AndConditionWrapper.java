package com.klikli_dev.occultism.crafting.recipe.condition.wrapper;

import com.klikli_dev.occultism.crafting.recipe.condition.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.condition.ConditionWrapper;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.AndCondition;
import net.neoforged.neoforge.common.conditions.ICondition;

public class AndConditionWrapper implements ConditionWrapper<AndCondition> {
    private final AndCondition condition;

    public AndConditionWrapper(AndCondition condition) {
        this.condition = condition;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, ICondition.IContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public AndCondition condition() {
        return condition;
    }
}
