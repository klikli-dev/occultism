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

package com.klikli_dev.occultism.common.level.tree;

import com.klikli_dev.occultism.datagen.worldgen.ConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class OtherworldNaturalTreeGrower extends TreeGrower {

    public OtherworldNaturalTreeGrower() {
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222910_, boolean p_222911_) {
        return ConfiguredFeatures.TREE_OTHERWORLD_NATURAL;
    }

    @Override
    public boolean growTree(ServerLevel level, ChunkGenerator p_222906_, BlockPos p_222907_, BlockState p_222908_, RandomSource p_222909_) {
        Holder<? extends ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(ConfiguredFeatures.TREE_OTHERWORLD_NATURAL);

        if (holder == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredfeature = holder.value();
            BlockState blockstate = level.getFluidState(p_222907_).createLegacyBlock();
            level.setBlock(p_222907_, blockstate, 4);
            if (configuredfeature.place(level, p_222906_, p_222909_, p_222907_)) {
                if (level.getBlockState(p_222907_) == blockstate) {
                    level.sendBlockUpdated(p_222907_, p_222908_, blockstate, 2);
                }

                return true;
            } else {
                level.setBlock(p_222907_, p_222908_, 4);
                return false;
            }
        }
    }

}
