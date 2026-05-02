package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ConditionContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class RitualRecipeConditionContext implements OccultismConditionContext {

    protected ConditionContext neoConditionContext;
    protected GoldenSacrificialBowlBlockEntity bowl;

    protected RitualRecipeConditionContext(GoldenSacrificialBowlBlockEntity bowl) {
        var level = (ServerLevel) Objects.requireNonNull(bowl.getLevel());
        this.neoConditionContext = new ConditionContext(List.of(), level.registryAccess(), level.enabledFeatures());
        this.bowl = bowl;

    }

    public static RitualRecipeConditionContext of(GoldenSacrificialBowlBlockEntity bowl) {
        return new RitualRecipeConditionContext(bowl);
    }

    @Override
    public ServerLevel level() {
        return (ServerLevel) this.bowl.getLevel();
    }

    @Override
    public Holder<Biome> biome() {
        return this.level().getBiome(this.bowl.getBlockPos());
    }

    @Override
    public ResourceKey<Level> dimension() {
        return this.level().dimension();
    }

    @Override
    public Holder<DimensionType> dimensionType() {
        return this.level().dimensionTypeRegistration();
    }

    @Override
    public <T> boolean isTagLoaded(@NotNull TagKey<T> key) {
        return this.neoConditionContext.isTagLoaded(key);
    }
}

