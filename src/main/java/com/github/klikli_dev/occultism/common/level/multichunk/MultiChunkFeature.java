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

package com.github.klikli_dev.occultism.common.level.multichunk;

import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

public class MultiChunkFeature extends Feature<MultiChunkFeatureConfig> {

    public final IMultiChunkSubFeature subFeature;

    public MultiChunkFeature(Codec<MultiChunkFeatureConfig> codec, IMultiChunkSubFeature subFeature) {
        super(codec);
        this.subFeature = subFeature;
    }


    protected List<BlockPos> getRootPositions(WorldGenLevel reader, ChunkGenerator generator, WorldgenRandom random,
                                              ChunkPos generatingChunk,
                                              MultiChunkFeatureConfig config) {
        ArrayList<BlockPos> result = new ArrayList<>(1);
        for (int i = -config.maxChunksToRoot; i < config.maxChunksToRoot; i++) {
            for (int j = -config.maxChunksToRoot; j < config.maxChunksToRoot; j++) {

                ChunkPos currentChunk = new ChunkPos(generatingChunk.x + i, generatingChunk.z + j);

                //Seed random for this chunk, this way we get the same result no matter how often this is called.
                random.setLargeFeatureWithSalt(reader.getSeed(), currentChunk.x, currentChunk.z,
                        config.featureSeedSalt);

                if (random.nextInt(config.chanceToGenerate) == 0) {
                    //this chunk contains a root, so we generate a random
                    result.add(currentChunk.getWorldPosition().offset(
                            random.nextInt(15),
                            Math.min(generator.getGenDepth(),
                                    config.minGenerationHeight + random.nextInt(
                                            Math.max(0, config.maxGenerationHeight - config.minGenerationHeight))),
                            random.nextInt(15))
                    );
                }
            }
        }
        return result;
    }

    @Override
    public boolean place(FeaturePlaceContext<MultiChunkFeatureConfig> context) {
        BlockPos pos = context.origin();

        if (context.level().getChunkSource() instanceof ServerChunkCache chunkSource) {
            ChunkPos generatingChunk = new ChunkPos(pos);
            List<BlockPos> rootPositions =
                    this.getRootPositions(context.level(), context.chunkGenerator(), (WorldgenRandom) context.random(), generatingChunk, context.config());
            //If no root position was found in range, we exit
            if (rootPositions.isEmpty()) {
                return false;
            }
            boolean generatedAny = false;
            for (BlockPos rootPosition : rootPositions) {
                if (this.subFeature.place(context.level(), context.chunkGenerator(), context.random(), rootPosition,
                        Math3DUtil.bounds(generatingChunk, context.chunkGenerator().getGenDepth()), context.config()))
                    generatedAny = true;
            }
            return generatedAny;
        }

        return false;
    }
}
