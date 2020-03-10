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
import com.github.klikli_dev.occultism.registry.OccultismBiomeFeatures;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.util.BiomeUtil;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class WorldGenHandler {

    //region Fields
    protected final List<BiomeDictionary.Type> undergroundGroveBiomes =
            Occultism.CONFIG.worldGen.undergroundGroveGen.validBiomes.get().stream()
                    .map(s -> BiomeDictionary.Type.getType(s))
                    .collect(Collectors.toList());
    //endregion Fields


    //region Methods
    public void setupOreGeneration() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            //TODO: restrict to dimensions -> requires a subclass of ore feature
            if (Occultism.CONFIG.worldGen.oreGen.otherstoneOreChance.get() > 0) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE
                                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                        OccultismBlocks.OTHERSTONE_NATURAL.get().getDefaultState(),
                                        Occultism.CONFIG.worldGen.oreGen.otherstoneOreSize.get()))
                                .withPlacement(Placement.COUNT_RANGE.configure(
                                        new CountRangeConfig(
                                                Occultism.CONFIG.worldGen.oreGen.otherstoneOreChance.get(),
                                                Occultism.CONFIG.worldGen.oreGen.otherstoneOreMin.get(), 0,
                                                Occultism.CONFIG.worldGen.oreGen.otherstoneOreMax.get()))));
            }
        }
    }

    public void setupUndergroundGroveGeneration() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (BiomeUtil.containsType(biome, this.undergroundGroveBiomes)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES,
                        OccultismBiomeFeatures.UNDERGROUND_GROVE_FEATURE.get()
                                .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            }
        }
    }
    //endregion Methods

}
