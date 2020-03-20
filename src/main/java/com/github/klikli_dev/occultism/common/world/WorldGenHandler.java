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
import com.github.klikli_dev.occultism.common.world.ore.DimensionOreFeatureConfig;
import com.github.klikli_dev.occultism.config.OccultismConfig;
import com.github.klikli_dev.occultism.registry.OccultismBiomeFeatures;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.util.BiomeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class WorldGenHandler {

    //region Fields
    protected static final List<BiomeDictionary.Type> UNDERGROUND_GROVE_BIOMES =
            Occultism.CONFIG.worldGen.undergroundGroveGen.validBiomes.get().stream()
                    .map(s -> BiomeDictionary.Type.getType(s))
                    .collect(Collectors.toList());

    protected static final List<DimensionType> UNDERGROUND_GROVE_DIMENSIONS =
            Occultism.CONFIG.worldGen.undergroundGroveGen.dimensionTypeWhitelist.get().stream()
                    .map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());

    protected static final List<DimensionType> OTHERSTONE_DIMENSION_WHITELIST =
            Occultism.CONFIG.worldGen.oreGen.otherstoneNatural.dimensionTypeWhitelist.get().stream()
                    .map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());
    protected static final List<DimensionType> COPPER_DIMENSION_WHITELIST =
            Occultism.CONFIG.worldGen.oreGen.copperOre.dimensionTypeWhitelist.get().stream()
                    .map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());
    protected static final List<DimensionType> SILVER_DIMENSION_WHITELIST =
            Occultism.CONFIG.worldGen.oreGen.silverOre.dimensionTypeWhitelist.get().stream()
                    .map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());
    protected static final List<DimensionType> IESNIUM_DIMENSION_WHITELIST =
            Occultism.CONFIG.worldGen.oreGen.iesniumOre.dimensionTypeWhitelist.get().stream()
                    .map(s -> DimensionType.byName(new ResourceLocation(s))).collect(
                    Collectors.toList());
    //endregion Fields

    //region Static Methods
    public static void setupOreGeneration() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            addOreFeature(biome,
                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    OccultismBlocks.OTHERSTONE_NATURAL.get().getDefaultState(),
                    Occultism.CONFIG.worldGen.oreGen.otherstoneNatural);

            addOreFeature(biome,
                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    OccultismBlocks.COPPER_ORE.get().getDefaultState(),
                    Occultism.CONFIG.worldGen.oreGen.copperOre);

            addOreFeature(biome,
                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    OccultismBlocks.SILVER_ORE.get().getDefaultState(),
                    Occultism.CONFIG.worldGen.oreGen.silverOre);

            //TODO: use otherworld block here that in turn shows iesnium only under third eye
            addOreFeature(biome,
                    OreFeatureConfig.FillerBlockType.NETHERRACK,
                    OccultismBlocks.IESNIUM_ORE.get().getDefaultState(),
                    Occultism.CONFIG.worldGen.oreGen.iesniumOre);
        }
    }

    public static void addOreFeature(Biome biome, OreFeatureConfig.FillerBlockType fillerBlockType,
                                     BlockState blockState,
                                     OccultismConfig.WorldGenSettings.OreGenSettings.OreSettings settings) {

        List<DimensionType> dimensionWhitelist = settings.dimensionTypeWhitelist.get().stream()
                                                         .map(s -> DimensionType.byName(new ResourceLocation(s)))
                                                         .collect(Collectors.toList());

        if (settings.oreChance.get() > 0) {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    OccultismBiomeFeatures.DIMENSION_ORE_FEATURE.get()
                            .withConfiguration(new DimensionOreFeatureConfig(dimensionWhitelist,
                                    fillerBlockType, blockState, settings.oreSize.get()))
                            .withPlacement(Placement.COUNT_RANGE.configure(
                                    new CountRangeConfig(settings.oreChance.get(), settings.oreMin.get(),
                                            0, settings.oreMax.get()))));
        }
    }

    public static void setupUndergroundGroveGeneration() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (BiomeUtil.containsType(biome, UNDERGROUND_GROVE_BIOMES)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES,
                        OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get()
                                .withConfiguration(new MultiChunkFeatureConfig(7,
                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnChance.get(),
                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnMin.get(),
                                        Occultism.CONFIG.worldGen.undergroundGroveGen.groveSpawnMax.get(),
                                        14653667, UNDERGROUND_GROVE_DIMENSIONS)));
            }
        }
    }
    //endregion Static Methods

    //region Methods
    //endregion Methods

}
