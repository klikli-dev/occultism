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
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.util.NonNullLazy;

import javax.annotation.Nullable;
import java.util.Random;

public class OtherworldTree extends Tree {
    //region Fields
    protected static final NonNullLazy<BlockState> OTHERWORLD_LOG =
            NonNullLazy.of(() -> OccultismBlocks.OTHERWORLD_LOG.get().getDefaultState());
    protected static final NonNullLazy<BlockState> OTHERWORLD_LEAVES =
            NonNullLazy.of(() -> OccultismBlocks.OTHERWORLD_LEAVES.get().getDefaultState());
    public static final NonNullLazy<TreeFeatureConfig> OTHERWORLD_TREE_CONFIG =
            NonNullLazy.of(() -> new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(OTHERWORLD_LOG.get()),
                    new SimpleBlockStateProvider(OTHERWORLD_LEAVES.get()),
                    new BlobFoliagePlacer(2, 0))
                                         .baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
                                         .setSapling(OccultismBlocks.OTHERWORLD_SAPLING.get())
                                         .build());
    //endregion Fields

    //region Initialization
    public OtherworldTree() {
    }
    //endregion Initialization

    //region Overrides
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random rand, boolean moreBeehives) {
        return Feature.NORMAL_TREE.withConfiguration(OTHERWORLD_TREE_CONFIG.get());
    }
    //endregion Overrides
}
