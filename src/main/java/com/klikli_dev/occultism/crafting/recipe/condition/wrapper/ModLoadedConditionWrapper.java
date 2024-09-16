package com.klikli_dev.occultism.crafting.recipe.condition.wrapper;

import com.klikli_dev.occultism.crafting.recipe.condition.ConditionVisitor;
import com.klikli_dev.occultism.crafting.recipe.condition.ConditionWrapper;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class ModLoadedConditionWrapper implements ConditionWrapper<ModLoadedCondition> {
    private final ModLoadedCondition condition;

    public ModLoadedConditionWrapper(ModLoadedCondition condition) {
        this.condition = condition;
    }

    @Override
    public MutableComponent accept(ConditionVisitor visitor, ICondition.IContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public ModLoadedCondition condition() {
        return condition;
    }
}
