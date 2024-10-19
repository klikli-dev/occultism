package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public interface OccultismConditionContext extends ICondition.IContext {
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
        public <T> @NotNull Map<ResourceLocation, Collection<Holder<T>>> getAllTags(@NotNull ResourceKey<? extends Registry<T>> registry) {
            throw new UnsupportedOperationException("This context does not support accessing information on the context.");
        }
    };
}
