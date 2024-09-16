package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface OccultismConditionContext extends ICondition.IContext {
    ServerLevel level();

    Holder<Biome> biome();

    ResourceKey<Level> dimension();

    Holder<DimensionType> dimensionType();
}
