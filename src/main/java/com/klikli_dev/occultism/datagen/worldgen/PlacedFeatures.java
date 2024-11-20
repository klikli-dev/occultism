package com.klikli_dev.occultism.datagen.worldgen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_SILVER = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_silver"));


    public static final ResourceKey<PlacedFeature> ORE_SILVER_DEEPSLATE = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_silver_deepslate"));

    public static final ResourceKey<PlacedFeature> ORE_IESNIUM = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_iesnium"));

    public static final ResourceKey<PlacedFeature> TREE_OTHERWORLD = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld"));

    public static final ResourceKey<PlacedFeature> TREE_OTHERWORLD_NATURAL = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld_natural"));

    public static final ResourceKey<PlacedFeature> GROVE_UNDERGROUND = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "grove_underground"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context, ORE_SILVER, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_SILVER),
                commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200))));

        PlacementUtils.register(context, ORE_SILVER_DEEPSLATE, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_SILVER_DEEPSLATE),
                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50))));

        PlacementUtils.register(context, ORE_IESNIUM, configuredFeatures.getOrThrow(ConfiguredFeatures.ORE_IESNIUM),
                commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));

        PlacementUtils.register(context, TREE_OTHERWORLD, configuredFeatures.getOrThrow(ConfiguredFeatures.TREE_OTHERWORLD),
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get()));

        PlacementUtils.register(context, TREE_OTHERWORLD_NATURAL, configuredFeatures.getOrThrow(ConfiguredFeatures.TREE_OTHERWORLD_NATURAL),
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()));

        PlacementUtils.register(context, GROVE_UNDERGROUND, configuredFeatures.getOrThrow(ConfiguredFeatures.GROVE_UNDERGROUND),
                List.of());
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier rarity, PlacementModifier height) {
        return List.of(rarity, InSquarePlacement.spread(), height, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
