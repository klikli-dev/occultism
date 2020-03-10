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
import com.github.klikli_dev.occultism.common.world.cave.SphericalCaveFeature;
import com.github.klikli_dev.occultism.common.world.cave.UndergroundGroveDecorator;
import com.github.klikli_dev.occultism.common.world.cave.UndergroundGroveFeature;
import com.github.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismBiomeFeatures {
    //region Fields
    public static final DeferredRegister<Feature<?>> FEATURES =
            new DeferredRegister<>(ForgeRegistries.FEATURES, Occultism.MODID);

    protected static final BlockState OTHERWORLD_LOG = OccultismBlocks.OTHERWORLD_LOG.get().getDefaultState();
    protected static final BlockState OTHERWORLD_LOG_NATURAL =
            OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().getDefaultState();
    protected static final BlockState OTHERWORLD_LEAVES = OccultismBlocks.OTHERWORLD_LEAVES.get().getDefaultState();
    public static final TreeFeatureConfig OTHERWORLD_TREE_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(OTHERWORLD_LOG),
                    new SimpleBlockStateProvider(OTHERWORLD_LEAVES),
                    new BlobFoliagePlacer(2, 0))
                    .baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
                    .setSapling((net.minecraftforge.common.IPlantable) OccultismBlocks.OTHERWORLD_SAPLING.get())
                    .build();
    protected static final BlockState OTHERWORLD_LEAVES_NATURAL =
            OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().getDefaultState();
    public static final TreeFeatureConfig OTHERWORLD_TREE_NATURAL_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(OTHERWORLD_LOG_NATURAL),
                    new SimpleBlockStateProvider(OTHERWORLD_LEAVES_NATURAL),
                    new BlobFoliagePlacer(2, 0))
                    .baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
                    .setSapling((net.minecraftforge.common.IPlantable) OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get())
                    .build();

    public static final RegistryObject<UndergroundGroveFeature> UNDERGROUND_GROVE_FEATURE = FEATURES.register("underground_grove",
            () -> new UndergroundGroveFeature(25, 25));

    //endregion Fields

}
