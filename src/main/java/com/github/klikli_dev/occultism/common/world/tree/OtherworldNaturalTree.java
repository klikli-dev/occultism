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

package com.github.klikli_dev.occultism.common.world.tree;

import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.util.NonNullLazy;

import javax.annotation.Nullable;
import java.util.Random;

public class OtherworldNaturalTree extends Tree {
    //region Fields
    protected static final NonNullLazy<BlockState> OTHERWORLD_LOG_NATURAL =
            NonNullLazy.of(() -> OccultismBlocks.OTHERWORLD_LOG_NATURAL.get().getDefaultState());
    protected static final NonNullLazy<BlockState> OTHERWORLD_LEAVES_NATURAL =
            NonNullLazy.of(() -> OccultismBlocks.OTHERWORLD_LEAVES_NATURAL.get().getDefaultState());
    //TODO: update tree placement to 1.16
//    public static final NonNullLazy<BaseTreeFeatureConfig> OTHERWORLD_TREE_NATURAL_CONFIG =
//            NonNullLazy
//                    .of(() -> new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OTHERWORLD_LOG_NATURAL.get()),
//                            new SimpleBlockStateProvider(OTHERWORLD_LEAVES_NATURAL.get()),
//                            new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 16)) //func_242252_a = creates
//                                      .baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
//                                      .setSapling(OccultismBlocks.OTHERWORLD_SAPLING_NATURAL.get()).build());
    //endregion Fields

    //region Initialization
    public OtherworldNaturalTree() {
    }
    //endregion Initialization

    //region Overrides
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean moreBeehives) {
        //return Feature.TREE.withConfiguration(OTHERWORLD_TREE_NATURAL_CONFIG.get());
        //TODO: return proper tree configs
        return null;
    }
    //endregion Overrides
}
