package com.klikli_dev.occultism.datagen.worldgen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.level.ConfigEnabledFilter;
import com.klikli_dev.occultism.common.level.ConfigEnabledFilter.Setting;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_SILVER = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_silver"));


    public static final ResourceKey<PlacedFeature> ORE_SILVER_DEEPSLATE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_silver_deepslate"));

    public static final ResourceKey<PlacedFeature> ORE_IESNIUM = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_iesnium"));

    public static final ResourceKey<PlacedFeature> TREE_OTHERWORLD = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld"));

    public static final ResourceKey<PlacedFeature> TREE_OTHERWORLD_NATURAL = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld_natural"));

    public static final ResourceKey<PlacedFeature> GROVE_UNDERGROUND = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "grove_underground"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context, ORE_SILVER, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_SILVER),
                commonOrePlacement(Setting.SILVER_ORE, 7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-8), VerticalAnchor.absolute(92))));

        PlacementUtils.register(context, ORE_SILVER_DEEPSLATE, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_SILVER_DEEPSLATE),
                commonOrePlacement(Setting.DEEPSLATE_SILVER_ORE, 10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-56), VerticalAnchor.absolute(8))));

        PlacementUtils.register(context, ORE_IESNIUM, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_IESNIUM),
                commonOrePlacement(Setting.IESNIUM_ORE, 3, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));

        PlacementUtils.register(context, TREE_OTHERWORLD, configuredFeatures.getOrThrow(ConfiguredFeatures.TREE_OTHERWORLD),
                ConfigEnabledFilter.of(Setting.OTHERWORLD_TREES),
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get()));

        PlacementUtils.register(context, TREE_OTHERWORLD_NATURAL, configuredFeatures.getOrThrow(ConfiguredFeatures.TREE_OTHERWORLD_NATURAL),
                ConfigEnabledFilter.of(Setting.OTHERWORLD_TREES),
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()));

        PlacementUtils.register(context, GROVE_UNDERGROUND, configuredFeatures.getOrThrow(ConfiguredFeatures.GROVE_UNDERGROUND),
                List.of(ConfigEnabledFilter.of(Setting.UNDERGROUND_GROVE)));
    }

    private static List<PlacementModifier> orePlacement(Setting setting, PlacementModifier rarity, PlacementModifier height) {
        return List.of(ConfigEnabledFilter.of(setting), rarity, InSquarePlacement.spread(), height, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(Setting setting, int pCount, PlacementModifier pHeightRange) {
        return orePlacement(setting, CountPlacement.of(pCount), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(Setting setting, int pChance, PlacementModifier pHeightRange) {
        return orePlacement(setting, RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
