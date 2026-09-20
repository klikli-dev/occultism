package com.klikli_dev.occultism.datagen.worldgen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.level.cave.SphericalCaveSubFeature;
import com.klikli_dev.occultism.common.level.cave.UndergroundGroveDecorator;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeature;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ConfiguredFeatures {
    public static final ResourceKey<Feature> ORE_SILVER = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_silver"));

    public static final ResourceKey<Feature> ORE_SILVER_DEEPSLATE = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_silver_deepslate"));

    public static final ResourceKey<Feature> ORE_IESNIUM = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "ore_iesnium"));

    public static final ResourceKey<Feature> TREE_OTHERWORLD = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld"));

    public static final ResourceKey<Feature> TREE_OTHERWORLD_NATURAL = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld_natural"));

    public static final ResourceKey<Feature> GROVE_UNDERGROUND = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Occultism.MODID, "grove_underground"));

    public static void bootstrap(BootstrapContext<Feature> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
        var belowTrunkProvider = blockStateProviders.getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);

        context.register(ORE_SILVER, new OreFeature(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 5));

        context.register(ORE_SILVER_DEEPSLATE, new OreFeature(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 10));

        context.register(ORE_IESNIUM, new OreFeature(new TagMatchTest(BlockTags.BASE_STONE_NETHER), OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), 3));

        context.register(TREE_OTHERWORLD_NATURAL,
                new TreeFeature.Builder(
                        BlockStateProvider.of(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.of(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1),
                        belowTrunkProvider)
                        .ignoreVines().build());

        context.register(TREE_OTHERWORLD,
                new TreeFeature.Builder(
                        BlockStateProvider.of(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.of(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1),
                        belowTrunkProvider)
                        .ignoreVines().build());

        var placedTreeOtherworldNatural = placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD_NATURAL);
        context.register(GROVE_UNDERGROUND, new MultiChunkFeature(
                new SphericalCaveSubFeature(new UndergroundGroveDecorator(), 40, 20),
                new MultiChunkFeatureConfig(
                        9,
                        300,
                        9,
                        48,
                        14653667,
                        0.3f,
                        0.2f,
                        0.1f,
                        0.3f,
                        0.1f,
                        placedTreeOtherworldNatural)));


    }

}
