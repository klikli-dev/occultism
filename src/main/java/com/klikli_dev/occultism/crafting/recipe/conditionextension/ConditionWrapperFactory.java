package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.wrapper.*;
import net.neoforged.neoforge.common.conditions.*;

public class ConditionWrapperFactory {
    public static ConditionWrapper<?> wrap(ICondition condition) {
        if (condition instanceof ConditionWrapper<?> conditionWrapper)
            return conditionWrapper; //for our own conditions that implement ConditionWrapper themselves

        if (condition instanceof AndCondition andCondition) {
            return new AndConditionWrapper(andCondition);
        }

        if (condition instanceof OrCondition orCondition) {
            return new OrConditionWrapper(orCondition);
        }

        if (condition instanceof AlwaysCondition alwaysCondition) {
            return new TrueConditionWrapper(alwaysCondition);
        }

        if (condition instanceof NeverCondition neverCondition) {
            return new FalseConditionWrapper(neverCondition);
        }

        if (condition instanceof NotCondition notCondition) {
            return new NotConditionWrapper(notCondition);
        }

        if (condition instanceof RegisteredCondition<?> registeredCondition) {
            return new ItemExistsConditionWrapper(registeredCondition);
        }

        if (condition instanceof ModLoadedCondition modLoadedCondition) {
            return new ModLoadedConditionWrapper(modLoadedCondition);
        }

        if (condition instanceof TagEmptyCondition tagEmptyCondition) {
            return new TagEmptyConditionWrapper(tagEmptyCondition);
        }

        throw new IllegalArgumentException("Unsupported condition type");
    }
}