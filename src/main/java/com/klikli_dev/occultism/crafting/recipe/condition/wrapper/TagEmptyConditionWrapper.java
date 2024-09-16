package com.klikli_dev.occultism.crafting.recipe.condition.wrapper;

import com.klikli_dev.occultism.crafting.recipe.condition.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.condition.ConditionWrapper;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;

public class TagEmptyConditionWrapper implements ConditionWrapper<TagEmptyCondition> {
    private final TagEmptyCondition condition;

    public TagEmptyConditionWrapper(TagEmptyCondition condition) {
        this.condition = condition;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, ICondition.IContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public TagEmptyCondition condition() {
        return condition;
    }
}
