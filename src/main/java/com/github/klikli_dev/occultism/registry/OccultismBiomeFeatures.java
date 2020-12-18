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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.world.cave.SphericalCaveSubFeature;
import com.github.klikli_dev.occultism.common.world.cave.UndergroundGroveDecorator;
import com.github.klikli_dev.occultism.common.world.multichunk.MultiChunkFeature;
import com.github.klikli_dev.occultism.common.world.multichunk.MultiChunkFeatureConfig;
import com.github.klikli_dev.occultism.common.world.ore.DimensionOreFeature;
import com.github.klikli_dev.occultism.common.world.ore.DimensionOreFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismBiomeFeatures {
    //region Fields
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Occultism.MODID);

    public static final RegistryObject<DimensionOreFeature> DIMENSION_ORE_FEATURE = FEATURES.register("dimension_ore",
            () -> new DimensionOreFeature(DimensionOreFeatureConfig::deserialize));

    public static final RegistryObject<MultiChunkFeature<MultiChunkFeatureConfig>> UNDERGROUND_GROVE_FEATURE =
            FEATURES.register("underground_grove",
                    () -> new MultiChunkFeature<>(
                            MultiChunkFeatureConfig::deserialize,
                            new SphericalCaveSubFeature<>(new UndergroundGroveDecorator(), 25,
                                    25)));

    //endregion Fields

}
