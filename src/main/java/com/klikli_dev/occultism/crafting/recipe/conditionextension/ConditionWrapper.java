package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface ConditionWrapper<T extends ICondition> {
    MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context);

    T condition();
}
