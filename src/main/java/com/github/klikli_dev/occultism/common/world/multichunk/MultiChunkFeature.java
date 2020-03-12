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

package com.github.klikli_dev.occultism.common.world.multichunk;

import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class MultiChunkFeature<T extends MultiChunkFeatureConfig> extends Feature<T> {

    //region Fields
    public final IMultiChunkSubFeature<T> subFeature;
    //endregion Fields

    //region Initialization
    public MultiChunkFeature(Function<Dynamic<?>, ? extends T> configFactoryIn, IMultiChunkSubFeature<T> subFeature) {
        super(configFactoryIn);
        this.subFeature = subFeature;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
                         BlockPos pos, T config) {
        //If dimensions are restricted and this dimension is not allowed, exit
        if (!config.allowedDimensionTypes.contains(worldIn.getDimension().getType()) &&
            !config.allowedDimensionTypes.isEmpty()) {
            return false;
        }
        ChunkPos generatingChunk = new ChunkPos(pos);
        List<BlockPos> rootPositions = this.getRootPositions(generator, (SharedSeedRandom) rand, generatingChunk, config);
        //If no root position was found in range, we exit
        if (rootPositions.isEmpty()) {
            return false;
        }

        boolean generatedAny = false;
        for (BlockPos rootPosition : rootPositions) {
            if(this.subFeature.place(worldIn, generator, rand, pos, rootPosition,
                    Math3DUtil.bounds(generatingChunk, generator.getMaxHeight()), config))
                generatedAny = true;
        }
        return generatedAny;
    }
    //endregion Overrides

    //region Methods
    protected List<BlockPos> getRootPositions(ChunkGenerator<?> generator, SharedSeedRandom random, ChunkPos generatingChunk,
                                              T config) {
        ArrayList<BlockPos> result = new ArrayList<>(1);
        for (int i = -config.maxChunksToRoot; i < config.maxChunksToRoot; i++) {
            for (int j = -config.maxChunksToRoot; j < config.maxChunksToRoot; j++) {

                ChunkPos currentChunk = new ChunkPos(generatingChunk.x + i, generatingChunk.z + j);
                //Seed random for this chunk, this way we get the same result no matter how often this is called.
                random.setLargeFeatureSeedWithSalt(generator.getSeed(), currentChunk.x, currentChunk.z, config.featureSeedSalt);

                if (random.nextInt(config.chanceToGenerate) == 0) {
                    //this chunk contains a root, so we generate a random
                    result.add(currentChunk.getBlock(
                            random.nextInt(15),
                            Math.min(generator.getMaxHeight(),
                                    config.minGenerationHeight +
                                    Math.max(0, random.nextInt(config.maxGenerationHeight - config.minGenerationHeight))),
                            random.nextInt(15))
                    );
                }
            }
        }
        return result;
    }
    //endregion Methods

}
