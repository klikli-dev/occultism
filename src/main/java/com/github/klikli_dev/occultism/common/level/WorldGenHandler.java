/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
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
import net.minecraft.level.gen.GenerationStage;
import net.minecraft.level.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.level.gen.feature.*;
import net.minecraft.level.gen.feature.template.TagMatchTest;
import net.minecraft.level.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.level.gen.placement.NoPlacementConfig;
import net.minecraft.level.gen.placement.Placement;
import net.minecraft.level.gen.placement.RangeDecoratorConfiguration;
import net.minecraft.level.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.RangeDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.level.BiomeLoadingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.w3c.dom.ranges.Range;

import java.util.stream.Collectors;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldGenHandler {

    //region Fields
    public static ConfiguredFeature<?, ?> COPPER_ORE;
    public static ConfiguredFeature<?, ?> SILVER_ORE;
    public static ConfiguredFeature<?, ?> IESNIUM_ORE;

    public static ConfiguredFeature<?, ?> UNDERGROUND_GROVE;

    public static ConfiguredFeature<TreeConfiguration, ?> OTHERWORLD_TREE_NATURAL;
    public static ConfiguredFeature<TreeConfiguration, ?> OTHERWORLD_TREE;
    //endregion Fields

    //region Static Methods
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (Occultism.COMMON_CONFIG.worldGen.oreGen.copperOre.generateOre.get())
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COPPER_ORE);
        if (Occultism.COMMON_CONFIG.worldGen.oreGen.silverOre.generateOre.get())
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SILVER_ORE);
        if (Occultism.COMMON_CONFIG.worldGen.oreGen.iesniumOre.generateOre.get())
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IESNIUM_ORE);
        if (Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen.generateUndergroundGroves.get())
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, UNDERGROUND_GROVE);
    }

    public static void registerConfiguredFeatures() {
        //Register the features with default setting here.
        OccultismCommonConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.COMMON_CONFIG.worldGen.oreGen;
        OccultismCommonConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen;

        //   public static final ConfiguredFeature<?, ?> ORE_IRON
        //   = register("ore_iron", Feature.ORE.configured(
        //      new OreConfiguration(ORE_IRON_TARGET_LIST, 9))
        //          .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63))
        //          .squared().count(20));
        COPPER_ORE = Feature.ORE.configured(
                new OreConfiguration(
                        new TagMatchTest(oreGen.copperOre.getFillerBlockTag()),
                        OccultismBlocks.COPPER_ORE.get().defaultBlockState(), oreGen.copperOre.size.get()))
                             .decorated(FeatureDecorator.RANGE.configured(
                                     new RangeDecoratorConfiguration(
                                             oreGen.copperOre.bottomOffset.get(),
                                             oreGen.copperOre.topOffset.get(),
                                             oreGen.copperOre.maximum.get())))
                             .square().count(oreGen.copperOre.count.get());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("copper_ore"), COPPER_ORE);

        SILVER_ORE = Feature.ORE.configured(
                new OreConfiguration(
                        new TagMatchTest(oreGen.silverOre.getFillerBlockTag()),
                        OccultismBlocks.SILVER_ORE.get().defaultBlockState(), oreGen.silverOre.size.get()))
                             .decorated(FeatureDecorator.RANGE.configured(
                                     new RangeDecoratorConfiguration(
                                             oreGen.silverOre.bottomOffset.get(),
                                             oreGen.silverOre.topOffset.get(),
                                             oreGen.silverOre.maximum.get())))
                             .square().count(oreGen.silverOre.count.get());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("silver_ore"), SILVER_ORE);

        IESNIUM_ORE = Feature.ORE.configured(
                new OreConfiguration(
                        new TagMatchTest(oreGen.iesniumOre.getFillerBlockTag()),
                        OccultismBlocks.IESNIUM_ORE_NATURAL.get().defaultBlockState(), oreGen.iesniumOre.size.get()))
                              .decorated(FeatureDecorator.RANGE.configured(
                                      new RangeDecoratorConfiguration(
                                              oreGen.iesniumOre.bottomOffset.get(),
                                              oreGen.iesniumOre.topOffset.get(),
                                              oreGen.iesniumOre.maximum.get())))
                              .square().count(oreGen.iesniumOre.count.get());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("iesnium_ore"), IESNIUM_ORE);

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
                                        .collect(Collectors.toList())))
                        .decorated(FeatureDecorator.NOPE.configured(new NoneDecoratorConfiguration()));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("underground_grove"), UNDERGROUND_GROVE);

        OTHERWORLD_TREE_NATURAL = Feature.TREE.configured((
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().defaultBlockState()),
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().defaultBlockState()),
                        new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
                        new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("otherworld_tree_natural"),
                OTHERWORLD_TREE_NATURAL);

        OTHERWORLD_TREE = Feature.TREE.configured((
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LOG.get().defaultBlockState()),
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
                        new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, modLoc("otherworld"), OTHERWORLD_TREE);
    }
    //endregion Static Methods
}
