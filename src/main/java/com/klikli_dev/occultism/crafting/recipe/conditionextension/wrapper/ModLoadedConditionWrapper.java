package com.klikli_dev.occultism.crafting.recipe.conditionextension.wrapper;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapper;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public record ModLoadedConditionWrapper(ModLoadedCondition condition) implements ConditionWrapper<ModLoadedCondition> {

    @Override
    public MutableComponent accept(ConditionVisitor visitor, OccultismConditionContext context) {
        return visitor.visit(this, context);
    }
}
