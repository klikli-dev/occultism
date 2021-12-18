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
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismPlacements {

    public static PlacedFeature SILVER_ORE;
    public static PlacedFeature IESNIUM_ORE;

    public static PlacedFeature UNDERGROUND_GROVE;

    public static PlacedFeature OTHERWORLD_TREE_NATURAL;
    public static PlacedFeature OTHERWORLD_TREE;

    public static void registerPlacedFeatures() {
        OccultismCommonConfig.WorldGenSettings.OreGenSettings oreGen = Occultism.COMMON_CONFIG.worldGen.oreGen;
        OccultismCommonConfig.WorldGenSettings.UndergroundGroveGenSettings groveGen =
                Occultism.COMMON_CONFIG.worldGen.undergroundGroveGen;

        VerticalAnchor silverMaxAnchor = oreGen.silverOre.maximum.get() > 0 ?
                VerticalAnchor.absolute(oreGen.silverOre.maximum.get()) :
                VerticalAnchor.belowTop(-oreGen.silverOre.maximum.get());
        SILVER_ORE = OccultismFeatures.SILVER_ORE.placed(commonOrePlacement(oreGen.silverOre.count.get(),
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(oreGen.silverOre.bottomOffset.get()), silverMaxAnchor)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, modLoc("silver_ore"), SILVER_ORE);

        VerticalAnchor iesniumMaxAnchor = oreGen.iesniumOre.maximum.get() > 0 ?
                VerticalAnchor.absolute(oreGen.iesniumOre.maximum.get()) :
                VerticalAnchor.belowTop(-oreGen.iesniumOre.maximum.get());
        IESNIUM_ORE = OccultismFeatures.IESNIUM_ORE.placed(commonOrePlacement(oreGen.iesniumOre.count.get(),
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(oreGen.iesniumOre.bottomOffset.get()), iesniumMaxAnchor)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, modLoc("iesnium_ore_natural"), IESNIUM_ORE);

        UNDERGROUND_GROVE = OccultismFeatures.UNDERGROUND_GROVE.placed();
        Registry.register(BuiltinRegistries.PLACED_FEATURE, modLoc("underground_grove"), UNDERGROUND_GROVE);

        OTHERWORLD_TREE_NATURAL = OccultismFeatures.OTHERWORLD_TREE_NATURAL.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get());
        Registry.register(BuiltinRegistries.PLACED_FEATURE, modLoc("otherworld_tree_natural"), OTHERWORLD_TREE_NATURAL);

        OTHERWORLD_TREE = OccultismFeatures.OTHERWORLD_TREE.filteredByBlockSurvival(OccultismBlocks.OTHERWORLD_SAPLING.get());
        Registry.register(BuiltinRegistries.PLACED_FEATURE, modLoc("otherworld_tree"), OTHERWORLD_TREE);
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

