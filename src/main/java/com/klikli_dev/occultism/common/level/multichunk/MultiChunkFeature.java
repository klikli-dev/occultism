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

package com.klikli_dev.occultism.common.level.multichunk;

import com.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.ArrayList;
import java.util.List;

public class MultiChunkFeature implements Feature {

    public static final MapCodec<MultiChunkFeature> CODEC = MultiChunkFeatureConfig.CODEC.fieldOf("config").xmap(
            config -> new MultiChunkFeature(null, config),
            feature -> feature.config
    );

    private final IMultiChunkSubFeature subFeature;
    private final MultiChunkFeatureConfig config;

    public MultiChunkFeature(IMultiChunkSubFeature subFeature, MultiChunkFeatureConfig config) {
        this.subFeature = subFeature;
        this.config = config;
    }

    @Override
    public MapCodec<MultiChunkFeature> codec() {
        return CODEC;
    }

    public static long getLargeFeatureWithSaltSeed(long pLevelSeed, int pRegionX, int pRegionZ, int pSalt) {
        return (long) pRegionX * 341873128712L + (long) pRegionZ * 132897987541L + pLevelSeed + (long) pSalt;
    }

    protected List<BlockPos> getRootPositions(WorldGenLevel reader, ChunkGenerator generator, RandomSource random,
                                              ChunkPos generatingChunk) {
        ArrayList<BlockPos> result = new ArrayList<>(1);
        for (int i = -this.config.maxChunksToRoot / 2; i < this.config.maxChunksToRoot / 2; i++) {
            for (int j = -this.config.maxChunksToRoot / 2; j < this.config.maxChunksToRoot / 2; j++) {

                ChunkPos currentChunk = new ChunkPos(generatingChunk.x() + i, generatingChunk.z() + j);

                //Seed random for this chunk, this way we get the same result no matter how often this is called.
                var seed = getLargeFeatureWithSaltSeed(reader.getSeed(), currentChunk.x(), currentChunk.z(), this.config.featureSeedSalt);
                random.setSeed(seed);

                if (random.nextInt(this.config.chanceToGenerate) == 0) {
                    //this chunk contains a root, so we generate a random
                    result.add(currentChunk.getWorldPosition().offset(
                            random.nextInt(15),
                            Math.min(generator.getGenDepth(),
                                    this.config.minGenerationHeight + random.nextInt(
                                            Math.max(0, this.config.maxGenerationHeight - this.config.minGenerationHeight))),
                            random.nextInt(15))
                    );
                }
            }
        }
        return result;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        if (this.subFeature == null) {
            return false;
        }

        if (level.getChunkSource() instanceof ServerChunkCache chunkSource) {
            ChunkPos generatingChunk = ChunkPos.containing(origin);

            //we create our own random here so that subsequent features are not affected by our custom seed gen.
            //we also hand that to our sub feature so that that also doesn't modify the seed of the world random.
            var localRandom = new XoroshiroRandomSource(random.nextLong());

            List<BlockPos> rootPositions =
                    this.getRootPositions(level, chunkGenerator, localRandom, generatingChunk);

            //If no root position was found in range, we exit
            if (rootPositions.isEmpty()) {
                return false;
            }
            boolean generatedAny = false;
            for (BlockPos rootPosition : rootPositions) {
                if (this.subFeature.place(level, chunkGenerator, localRandom, rootPosition,
                        Math3DUtil.bounds(generatingChunk, chunkGenerator.getGenDepth()), this.config))
                    generatedAny = true;
            }
            return generatedAny;
        }

        return false;
    }
}
