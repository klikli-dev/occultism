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
import com.github.klikli_dev.occultism.registry.OccultismBiomeFeatures;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldGenHandler {

    //region Fields
    public static ConfiguredFeature<?, ?> ORE_COPPER;
    public static ConfiguredFeature<?, ?> ORE_SILVER;
    public static ConfiguredFeature<?, ?> ORE_IESNIUM;

    public static ConfiguredFeature<?, ?> UNDERGROUND_GROVE;
    //endregion Fields

    //region Static Methods
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_COPPER);
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_SILVER);
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_IESNIUM);

        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UNDERGROUND_GROVE);
    }

    public static void registerConfiguredFeatures() {
        //Register the features with default setting here.
        ORE_COPPER = Feature.ORE
                             .withConfiguration(new OreFeatureConfig(
                                     OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                     OccultismBlocks.COPPER_ORE.get().getDefaultState(), 9))
                             .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 0, 64)))
                             .func_242731_b(20); //count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("ore_copper"), ORE_COPPER);

        ORE_SILVER = Feature.ORE
                             .withConfiguration(new OreFeatureConfig(
                                     OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                     OccultismBlocks.SILVER_ORE.get().getDefaultState(), 7))
                             .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(5, 0, 30)))
                             .func_242731_b(3); // func_242731_b = count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("ore_silver"), ORE_SILVER);

        ORE_IESNIUM = Feature.ORE
                              .withConfiguration(new OreFeatureConfig(
                                      OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER,
                                      OccultismBlocks.IESNIUM_ORE.get().getDefaultState(), 3))
                              .withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT)
                              .func_242731_b(10); // func_242731_b = count decorator
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("ore_iesnium"), ORE_IESNIUM);

        UNDERGROUND_GROVE =
                OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get()
                        .withConfiguration(new MultiChunkFeatureConfig(
                                7, 400, 25, 60, 14653667))
                        .withPlacement(Placement.NOPE.configure(new NoPlacementConfig()));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("underground_grove"), UNDERGROUND_GROVE);

    }
    //endregion Static Methods
}
