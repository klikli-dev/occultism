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
import com.github.klikli_dev.occultism.config.OccultismCommonConfig;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class OccultismPlacements {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Occultism.MODID);

    public static RegistryObject<PlacedFeature> SILVER_ORE = PLACED_FEATURES.register("silver_ore",
            () -> new PlacedFeature((Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)OccultismFeatures.SILVER_ORE, commonOrePlacement(3,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(200)))));
    public static Holder<PlacedFeature> SILVER_ORE_DEEPSLATE;
    public static Holder<PlacedFeature> IESNIUM_ORE;

    public static Holder<PlacedFeature> UNDERGROUND_GROVE;

    public static Holder<PlacedFeature> OTHERWORLD_TREE_NATURAL;
    public static Holder<PlacedFeature> OTHERWORLD_TREE;

    public static void registerPlacedFeatures() {
        OccultismCommonConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.COMMON_CONFIG.worldGen.oreGen;
        OccultismCommonConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen;

        VerticalAnchor silverMaxAnchor = oreGen.silverOre.maximum.get() > 0 ?
                VerticalAnchor.absolute(oreGen.silverOre.maximum.get()) :
                VerticalAnchor.belowTop(-oreGen.silverOre.maximum.get());
        SILVER_ORE = PlacementUtils.register("silver_ore", OccultismFeatures.SILVER_ORE,
                PlacementUtils.filteredByBlockSurvival()
                commonOrePlacement(oreGen.silverOre.count.get(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(oreGen.silverOre.minimum.get()), silverMaxAnchor)));


        VerticalAnchor silverDeepslateMaxAnchor = oreGen.silverOreDeepslate.maximum.get() > 0 ?
                VerticalAnchor.absolute(oreGen.silverOreDeepslate.maximum.get()) :
                VerticalAnchor.belowTop(-oreGen.silverOreDeepslate.maximum.get());

        SILVER_ORE_DEEPSLATE = PlacementUtils.register("silver_ore_deepslate", OccultismFeatures.SILVER_ORE_DEEPSLATE,
                commonOrePlacement(oreGen.silverOreDeepslate.count.get(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(oreGen.silverOreDeepslate.minimum.get()), silverMaxAnchor)));


        VerticalAnchor iesniumMaxAnchor = oreGen.iesniumOre.maximum.get() > 0 ?
                VerticalAnchor.absolute(oreGen.iesniumOre.maximum.get()) :
                VerticalAnchor.belowTop(-oreGen.iesniumOre.maximum.get());

        IESNIUM_ORE = PlacementUtils.register("iesnium_ore", OccultismFeatures.IESNIUM_ORE,
                commonOrePlacement(oreGen.iesniumOre.count.get(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(oreGen.iesniumOre.minimum.get()), iesniumMaxAnchor)));

        UNDERGROUND_GROVE = PlacementUtils.register("underground_grove", OccultismFeatures.UNDERGROUND_GROVE);

        OTHERWORLD_TREE_NATURAL = PlacementUtils.register("otherworld_tree_natural",
                OccultismFeatures.OTHERWORLD_TREE_NATURAL,
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()));

        OTHERWORLD_TREE = PlacementUtils.register("otherworld_tree",
                OccultismFeatures.OTHERWORLD_TREE,
                PlacementUtils.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get()));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier1, PlacementModifier modifier2) {
        return List.of(modifier1, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), modifier);
    }
}

