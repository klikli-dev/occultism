package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeWithTagCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionCondition;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInDimensionTypeCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class OccultismConditionCodecs {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Occultism.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<IsInBiomeCondition>> IS_IN_BIOME = CONDITION_CODECS.register("is_in_biome", () -> IsInBiomeCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<IsInBiomeWithTagCondition>> IS_IN_BIOME_WITH_TAG = CONDITION_CODECS.register("is_in_biome_with_tag", () -> IsInBiomeWithTagCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<IsInDimensionCondition>> IS_IN_DIMENSION = CONDITION_CODECS.register("is_in_dimension", () -> IsInDimensionCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<IsInDimensionTypeCondition>> IS_IN_DIMENSION_TYPE = CONDITION_CODECS.register("is_in_dimension_type", () -> IsInDimensionTypeCondition.CODEC);
}
