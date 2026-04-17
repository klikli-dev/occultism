package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;

public interface OccultismConditionContext extends IContext {
    ServerLevel level();

    Holder<Biome> biome();

    ResourceKey<Level> dimension();

    Holder<DimensionType> dimensionType();

    OccultismConditionContext EMPTY = new OccultismConditionContext() {
        @Override
        public ServerLevel level() {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }

        @Override
        public Holder<Biome> biome() {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }

        @Override
        public ResourceKey<Level> dimension() {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }

        @Override
        public Holder<DimensionType> dimensionType() {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }

        @Override
        public <T> boolean isTagLoaded(TagKey<T> key) {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }
    };
}

