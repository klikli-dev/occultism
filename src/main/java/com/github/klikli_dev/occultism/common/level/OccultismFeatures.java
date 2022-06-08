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

package com.github.klikli_dev.occultism.common.level;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.level.multichunk.MultiChunkFeatureConfig;
import com.github.klikli_dev.occultism.config.OccultismCommonConfig;
import com.github.klikli_dev.occultism.registry.OccultismBiomeFeatures;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
import net.minecraftforge.common.BiomeDictionary;

import java.util.stream.Collectors;

public class OccultismFeatures {
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> SILVER_ORE;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> SILVER_ORE_DEEPSLATE;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> IESNIUM_ORE;

    public static Holder<ConfiguredFeature<MultiChunkFeatureConfig, ?>> UNDERGROUND_GROVE;

    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> OTHERWORLD_TREE_NATURAL;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> OTHERWORLD_TREE;

    public static void registerConfiguredFeatures() {
        //Register the features with default setting here.
        OccultismCommonConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.COMMON_CONFIG.worldGen.oreGen;
        OccultismCommonConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen;


        SILVER_ORE = FeatureUtils.register("silver_ore", Feature.ORE, new OreConfiguration(
                new TagMatchTest(oreGen.silverOre.getFillerBlockTag()),
                OccultismBlocks.SILVER_ORE.get().defaultBlockState(), oreGen.silverOre.size.get()));

        SILVER_ORE_DEEPSLATE = FeatureUtils.register("silver_ore_deepslate", Feature.ORE, new OreConfiguration(
                new TagMatchTest(oreGen.silverOreDeepslate.getFillerBlockTag()),
                OccultismBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), oreGen.silverOreDeepslate.size.get()));

        IESNIUM_ORE = FeatureUtils.register("iesnium_ore", Feature.ORE, new OreConfiguration(
                new TagMatchTest(oreGen.iesniumOre.getFillerBlockTag()),
                OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), oreGen.iesniumOre.size.get()));


        UNDERGROUND_GROVE = FeatureUtils.register("underground_grove", OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get(),
                new MultiChunkFeatureConfig(
                        7,
                        groveGen.groveSpawnChance.get(),
                        groveGen.groveSpawnMin.get(),
                        groveGen.groveSpawnMax.get(),
                        14653667,
                        Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.biomeTypeBlacklist.get().stream()
                                .map(BiomeDictionary.Type::getType)
                                .collect(Collectors.toList())));

        OTHERWORLD_TREE_NATURAL = FeatureUtils.register("otherworld_tree_natural", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        OTHERWORLD_TREE = FeatureUtils.register("otherworld_tree", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    }
}
