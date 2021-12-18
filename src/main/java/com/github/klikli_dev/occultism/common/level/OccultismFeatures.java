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
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
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

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismFeatures {
    public static ConfiguredFeature<?, ?> SILVER_ORE;
    public static ConfiguredFeature<?, ?> IESNIUM_ORE;

    public static ConfiguredFeature<?, ?> UNDERGROUND_GROVE;

    public static ConfiguredFeature<TreeConfiguration, ?> OTHERWORLD_TREE_NATURAL;
    public static ConfiguredFeature<TreeConfiguration, ?> OTHERWORLD_TREE;

    public static void registerConfiguredFeatures() {
        //Register the features with default setting here.
        OccultismCommonConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.COMMON_CONFIG.worldGen.oreGen;
        OccultismCommonConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen;


        SILVER_ORE = Feature.ORE.configured(
                new OreConfiguration(
                        new TagMatchTest(oreGen.silverOre.getFillerBlockTag()),
                        OccultismBlocks.SILVER_ORE.get().defaultBlockState(), oreGen.silverOre.size.get()));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("silver_ore"), SILVER_ORE);

        IESNIUM_ORE = Feature.ORE.configured(
                new OreConfiguration(
                        new TagMatchTest(oreGen.iesniumOre.getFillerBlockTag()),
                        OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), oreGen.iesniumOre.size.get()));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("iesnium_ore_natural"), IESNIUM_ORE);

        UNDERGROUND_GROVE =
                OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get().configured(
                        new MultiChunkFeatureConfig(
                                7,
                                groveGen.groveSpawnChance.get(),
                                groveGen.groveSpawnMin.get(),
                                groveGen.groveSpawnMax.get(),
                                14653667,
                                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.biomeTypeBlacklist.get().stream()
                                        .map(BiomeDictionary.Type::getType)
                                        .collect(Collectors.toList())));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("underground_grove"), UNDERGROUND_GROVE);

        OTHERWORLD_TREE_NATURAL = Feature.TREE.configured((
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("otherworld_tree_natural"),
                OTHERWORLD_TREE_NATURAL);

        OTHERWORLD_TREE = Feature.TREE.configured((
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("otherworld"), OTHERWORLD_TREE);
    }
}
