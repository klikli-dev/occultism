/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.level.cave.SphericalCaveSubFeature;
import com.github.klikli_dev.occultism.common.level.cave.UndergroundGroveDecorator;
import com.github.klikli_dev.occultism.common.level.modifiers.OreBiomeModifier;
import com.github.klikli_dev.occultism.common.level.multichunk.MultiChunkFeature;
import com.github.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Stream;

public class OccultismFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Occultism.MODID);

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Occultism.MODID);

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Occultism.MODID);

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Occultism.MODID);

    //Features

    public static final RegistryObject<MultiChunkFeature> UNDERGROUND_GROVE_FEATURE =
            FEATURES.register("underground_grove",
                    () -> new MultiChunkFeature(
                            MultiChunkFeatureConfig.CODEC,
                            new SphericalCaveSubFeature(new UndergroundGroveDecorator(), 25, 25)));

    // Configured Features

    public static RegistryObject<ConfiguredFeature<?, ?>> SILVER_ORE_CONFIGURED = CONFIGURED_FEATURES.register("silver_ore", () ->
            new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                    new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                    OccultismBlocks.SILVER_ORE.get().defaultBlockState(), 7)));
    public static RegistryObject<ConfiguredFeature<?, ?>> SILVER_ORE_DEEPSLATE_CONFIGURED = CONFIGURED_FEATURES.register("silver_ore_deepslate", () ->
            new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                    new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                    OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), 7)));
    public static RegistryObject<ConfiguredFeature<?, ?>> IESNIUM_ORE_CONFIGURED = CONFIGURED_FEATURES.register("iesnium_ore", () ->
            new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                    new TagMatchTest(BlockTags.BASE_STONE_NETHER),
                    OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), 3)));

    public static RegistryObject<ConfiguredFeature<?, ?>> UNDERGROUND_GROVE_CONFIGURED = CONFIGURED_FEATURES.register("underground_grove", () ->
            new ConfiguredFeature<>(UNDERGROUND_GROVE_FEATURE.get(),
                    new MultiChunkFeatureConfig(
                            7,
                            400,
                            25,
                            60,
                            14653667,
                            Stream.of(BiomeTags.IS_NETHER, BiomeTags.IS_END).toList())));
    public static RegistryObject<ConfiguredFeature<?, ?>> OTHERWORLD_TREE_NATURAL_CONFIGURED = CONFIGURED_FEATURES.register("otherworld_tree_natural", () ->
            new ConfiguredFeature<>(Feature.TREE,
                    new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                            new StraightTrunkPlacer(4, 2, 0),
                            BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
    public static RegistryObject<ConfiguredFeature<?, ?>> OTHERWORLD_TREE_CONFIGURED = CONFIGURED_FEATURES.register("otherworld_tree", () ->
            new ConfiguredFeature<>(Feature.TREE,
                    new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                            new StraightTrunkPlacer(4, 2, 0),
                            BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));


    // Placed Features

    public static RegistryObject<PlacedFeature> SILVER_ORE_PLACED = PLACED_FEATURES.register("silver_ore",
            () -> new PlacedFeature(SILVER_ORE_CONFIGURED.getHolder().orElseThrow(),
                    commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200)))));

    public static RegistryObject<PlacedFeature> SILVER_ORE_DEEPSLATE_PLACED = PLACED_FEATURES.register("silver_ore_deepslate",
            () -> new PlacedFeature(SILVER_ORE_DEEPSLATE_CONFIGURED.getHolder().orElseThrow(),
                    commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)))));

    public static RegistryObject<PlacedFeature> IESNIUM_ORE_PLACED = PLACED_FEATURES.register("iesnium_ore",
            () -> new PlacedFeature(IESNIUM_ORE_CONFIGURED.getHolder().orElseThrow(),
                    commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)))));

    public static RegistryObject<PlacedFeature> UNDERGROUND_GROVE_PLACED = PLACED_FEATURES.register("underground_grove",
            () -> new PlacedFeature(UNDERGROUND_GROVE_CONFIGURED.getHolder().orElseThrow(), List.of()));

    public static RegistryObject<PlacedFeature> OTHERWORLD_TREE_NATURAL_PLACED = PLACED_FEATURES.register("otherworld_tree_natural",
            () -> new PlacedFeature(OTHERWORLD_TREE_NATURAL_CONFIGURED.getHolder().orElseThrow(), List.of(
                    PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()))));

    public static RegistryObject<PlacedFeature> OTHERWORLD_TREE_PLACED = PLACED_FEATURES.register("otherworld_tree",
            () -> new PlacedFeature(OTHERWORLD_TREE_CONFIGURED.getHolder().orElseThrow(), List.of(
                    PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get()))));

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier1, PlacementModifier modifier2) {
        return List.of(modifier1, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), modifier);
    }

    // Biome Modifiers

    public static final RegistryObject<Codec<? extends BiomeModifier>> ORE = BIOME_MODIFIERS.register("ore", OreBiomeModifier::makeCodec);

}
