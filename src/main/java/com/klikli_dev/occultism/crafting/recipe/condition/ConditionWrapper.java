package com.klikli_dev.occultism.crafting.recipe.condition;

import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface ConditionWrapper<T extends ICondition> {
    MutableComponent accept(ConditionVisitor visitor, ICondition.IContext context);

    T condition();
}
