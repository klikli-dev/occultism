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
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldGenHandler {

    //region Fields
    protected static final List<BiomeDictionary.Type> UNDERGROUND_GROVE_BIOMES =
            Occultism.CONFIG.worldGen.undergroundGroveGen.validBiomes.get().stream()
                    .map(s -> BiomeDictionary.Type.getType(s))
                    .collect(Collectors.toList());

    //endregion Fields

    //region Static Methods
    //    public static void setupOreGeneration() {
    //        for (Biome biome : ForgeRegistries.BIOMES) {
    //            addOreFeature(biome,
    //                    OccultismBlocks.OTHERSTONE_NATURAL.get().getDefaultState(),
    //                    Occultism.CONFIG.worldGen.oreGen.otherstoneNatural);
    //
    //            addOreFeature(biome,
    //                    OccultismBlocks.COPPER_ORE.get().getDefaultState(),
    //                    Occultism.CONFIG.worldGen.oreGen.copperOre);
    //
    //            addOreFeature(biome,
    //                    OccultismBlocks.SILVER_ORE.get().getDefaultState(),
    //                    Occultism.CONFIG.worldGen.oreGen.silverOre);
    //
    //            addOreFeature(biome,
    //                    OccultismBlocks.IESNIUM_ORE_NATURAL.get().getDefaultState(),
    //                    Occultism.CONFIG.worldGen.oreGen.iesniumOre);
    //        }
    //    }

    //    public static void setupUndergroundGroveGeneration() {
    //        for (Map.Entry<RegistryKey<Biome>, Biome> biome : ForgeRegistries.BIOMES.getEntries()) {
    //            if (BiomeUtil.containsType(biome.getKey(), UNDERGROUND_GROVE_BIOMES)) {
    //                biome.getValue().addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES,
    //                        OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get()
    //                                .withConfiguration(new MultiChunkFeatureConfig(7,
    //                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnChance.get(),
    //                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnMin.get(),
    //                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnMax.get(),
    //                                        14653667, UNDERGROUND_GROVE_DIMENSIONS)));
    //            }
    //        }
    //    }
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        //TODO: figure out if we can just get the registered feaeture from the registry or if we need to register it manually
        Optional<ConfiguredFeature<?, ?>> oreCopper =
                WorldGenRegistries.CONFIGURED_FEATURE.getOptional(modLoc("ore_copper"));
        //TODO: use blacklists for ores for biomes?
        if (oreCopper.isPresent())
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, oreCopper.get());
        //TODO: register other ores
        //TODO: register grove generation
        //TODO: use whitelist/blacklist for underground groves?
    }
    //endregion Static Methods
    //region Methods
    //public static ConfiguredFeature<?,?> ORE_COPPER;

    //TODO: Figure out if we need to do this
    //    public static void registerConfiguredFeatures() {
    //        ORE_COPPER = Feature.ORE
    //                             .withConfiguration(new OreFeatureConfig(
    //                                     OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
    //                                     OccultismBlocks.COPPER_ORE.get().getDefaultState(), 9))
    //                             .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 0, 64)))
    //                             .func_242731_b(20); //count decorator
    //        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc("ore_copper"), ORE_COPPER);
    //    }
    //endregion Methods
}
