package com.klikli_dev.occultism.datagen.worldgen;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_silver"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_DEEPSLATE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_silver_deepslate"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IESNIUM = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "ore_iesnium"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_OTHERWORLD = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_OTHERWORLD_NATURAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "tree_otherworld_natural"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_UNDERGROUND = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "grove_underground"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        FeatureUtils.register(context, ORE_SILVER, Feature.ORE, new OreConfiguration(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 5));

        FeatureUtils.register(context, ORE_SILVER_DEEPSLATE, Feature.ORE, new OreConfiguration(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 10));

        FeatureUtils.register(context, ORE_IESNIUM, Feature.ORE, new OreConfiguration(new TagMatchTest(BlockTags.BASE_STONE_NETHER), OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), 3));

        FeatureUtils.register(context, TREE_OTHERWORLD_NATURAL, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        FeatureUtils.register(context, TREE_OTHERWORLD, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        var placedTreeOtherworldNatural = placedFeatures.getOrThrow(PlacedFeatures.TREE_OTHERWORLD_NATURAL);
        FeatureUtils.register(context, GROVE_UNDERGROUND, OccultismFeatures.UNDERGROUND_GROVE_FEATURE.get(),
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
                        placedTreeOtherworldNatural));


    }

}
