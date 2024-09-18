package com.klikli_dev.occultism.crafting.recipe.conditionextension;

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.conditions.ConditionContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class RitualRecipeConditionContext implements OccultismConditionContext {

    protected ConditionContext neoConditionContext;
    protected GoldenSacrificialBowlBlockEntity bowl;

    protected RitualRecipeConditionContext(GoldenSacrificialBowlBlockEntity bowl) {
        this.neoConditionContext = new ConditionContext(new TagManager(Objects.requireNonNull(bowl.getLevel()).registryAccess()));
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
    public <T> @NotNull Map<ResourceLocation, Collection<Holder<T>>> getAllTags(@NotNull ResourceKey<? extends Registry<T>> registry) {
        return this.neoConditionContext.getAllTags(registry);
    }
}
