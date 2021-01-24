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

package com.github.klikli_dev.occultism.common.world;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.world.multichunk.MultiChunkFeatureConfig;
import com.github.klikli_dev.occultism.config.OccultismServerConfig;
import com.github.klikli_dev.occultism.registry.OccultismBiomeFeatures;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Collectors;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldGenHandler {

    //region Fields
    public static ConfiguredFeature<?, ?> COPPER_ORE;
    public static ConfiguredFeature<?, ?> SILVER_ORE;
    public static ConfiguredFeature<?, ?> IESNIUM_ORE;

    public static ConfiguredFeature<?, ?> UNDERGROUND_GROVE;

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OTHERWORLD_TREE_NATURAL;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OTHERWORLD_TREE;
    //endregion Fields

    //region Static Methods
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (Occultism.SERVER_CONFIG.worldGen.oreGen.copperOre.generateOre.get())
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, COPPER_ORE);
        if (Occultism.SERVER_CONFIG.worldGen.oreGen.silverOre.generateOre.get())
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, SILVER_ORE);
        if (Occultism.SERVER_CONFIG.worldGen.oreGen.iesniumOre.generateOre.get())
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IESNIUM_ORE);
        if (Occultism.SERVER_CONFIG.worldGen.undergroundGroveGen.generateUndergroundGroves.get())
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UNDERGROUND_GROVE);
    }

    public static void registerConfiguredFeatures() {
        //Register the features with default setting here.
        OccultismServerConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.SERVER_CONFIG.worldGen.oreGen;
        OccultismServerConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.SERVER_CONFIG.worldGen.undergroundGroveGen;

        COPPER_ORE = Feature.ORE.withConfiguration(
                new OreFeatureConfig(
                        new TagMatchRuleTest(oreGen.copperOre.getFillerBlockTag()),
                        OccultismBlocks.COPPER_ORE.get().getDefaultState(), oreGen.copperOre.size.get()))
                             .withPlacement(Placement.RANGE.configure(
                                     new TopSolidRangeConfig(
                                             oreGen.copperOre.bottomOffset.get(),
                                             oreGen.copperOre.topOffset.get(),
                                             oreGen.copperOre.maximum.get())))
                             .square().func_242731_b(oreGen.copperOre.count.get()); // func_242731_b =count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("copper_ore"), COPPER_ORE);

        SILVER_ORE = Feature.ORE.withConfiguration(
                new OreFeatureConfig(
                        new TagMatchRuleTest(oreGen.silverOre.getFillerBlockTag()),
                        OccultismBlocks.SILVER_ORE.get().getDefaultState(), oreGen.silverOre.size.get()))
                             .withPlacement(Placement.RANGE.configure(
                                     new TopSolidRangeConfig(
                                             oreGen.silverOre.bottomOffset.get(),
                                             oreGen.silverOre.topOffset.get(),
                                             oreGen.silverOre.maximum.get())))
                             .square().func_242731_b(oreGen.silverOre.count.get()); // func_242731_b =count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("silver_ore"), SILVER_ORE);

        IESNIUM_ORE = Feature.ORE.withConfiguration(
                new OreFeatureConfig(
                        new TagMatchRuleTest(oreGen.iesniumOre.getFillerBlockTag()),
                        OccultismBlocks.IESNIUM_ORE_NATURAL.get().getDefaultState(), oreGen.iesniumOre.size.get()))
                              .withPlacement(Placement.RANGE.configure(
                                      new TopSolidRangeConfig(
                                              oreGen.iesniumOre.bottomOffset.get(),
                                              oreGen.iesniumOre.topOffset.get(),
                                              oreGen.iesniumOre.maximum.get())))
                              .square().func_242731_b(oreGen.iesniumOre.count.get()); // func_242731_b =count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("iesnium_ore"), IESNIUM_ORE);

        UNDERGROUND_GROVE =
                OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get().withConfiguration(
                        new MultiChunkFeatureConfig(
                                7,
                                groveGen.groveSpawnChance.get(),
                                groveGen.groveSpawnMin.get(),
                                groveGen.groveSpawnMax.get(),
                                14653667,
                                Occultism.SERVER_CONFIG.worldGen.undergroundGroveGen.biomeTypeBlacklist.get().stream()
                                        .map(BiomeDictionary.Type::getType)
                                        .collect(Collectors.toList())))
                        .withPlacement(Placement.NOPE.configure(new NoPlacementConfig()));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("underground_grove"), UNDERGROUND_GROVE);

        OTHERWORLD_TREE_NATURAL = Feature.TREE.withConfiguration((
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().getDefaultState()),
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().getDefaultState()),
                        new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                        new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("otherworld_tree_natural"),
                OTHERWORLD_TREE_NATURAL);

        OTHERWORLD_TREE = Feature.TREE.withConfiguration((
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LOG.get().getDefaultState()),
                        new SimpleBlockStateProvider(OccultismBlocks.OTHERWORLD_LEAVES.get().getDefaultState()),
                        new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                        new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("otherworld"), OTHERWORLD_TREE);
    }
    //endregion Static Methods
}
