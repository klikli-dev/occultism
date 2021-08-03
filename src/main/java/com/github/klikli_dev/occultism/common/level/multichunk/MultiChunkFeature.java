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

import com.github.klikli_dev.occultism.util.BiomeUtil;
import com.github.klikli_dev.occultism.util.Math3DUtil;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.level.gen.feature.Feature;
import net.minecraft.util.ResourceKey;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiChunkFeature extends Feature<MultiChunkFeatureConfig> {

    //region Fields
    public final IMultiChunkSubFeature subFeature;
    //endregion Fields

    //region Initialization
    public MultiChunkFeature(Codec<MultiChunkFeatureConfig> codec, IMultiChunkSubFeature subFeature) {
        //IMultiChunkSubFeature<T> subFeature
        super(codec);
        this.subFeature = subFeature;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public boolean generate(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos,
                            MultiChunkFeatureConfig config) {

        //check biome type blacklist
        for(Biome biome : generator.getBiomeProvider().getBiomes(pos.getX(), pos.getY(), pos.getZ(), 1)){
            ResourceKey<Biome> biomeKey = ResourceKey.getOrCreateKey(Registry.BIOME_KEY, biome.getRegistryName());
            if(BiomeUtil.containsType(biomeKey, config.biomeTypeBlacklist)){
                return false;
            }
        }

        ChunkPos generatingChunk = new ChunkPos(pos);
        List<BlockPos> rootPositions =
                this.getRootPositions(reader, generator, (SharedSeedRandom) rand, generatingChunk, config);
        //If no root position was found in range, we exit
        if (rootPositions.isEmpty()) {
            return false;
        }
        boolean generatedAny = false;
        for (BlockPos rootPosition : rootPositions) {
            if (this.subFeature.generate(reader, generator, rand, rootPosition,
                    Math3DUtil.bounds(generatingChunk, generator.getMaxBuildHeight()), config))
                generatedAny = true;
        }
        return generatedAny;
    }
    //endregion Overrides

    //region Methods
    protected List<BlockPos> getRootPositions(WorldGenLevel reader, ChunkGenerator generator, SharedSeedRandom random,
                                              ChunkPos generatingChunk,
                                              MultiChunkFeatureConfig config) {
        ArrayList<BlockPos> result = new ArrayList<>(1);
        for (int i = -config.maxChunksToRoot; i < config.maxChunksToRoot; i++) {
            for (int j = -config.maxChunksToRoot; j < config.maxChunksToRoot; j++) {

                ChunkPos currentChunk = new ChunkPos(generatingChunk.x + i, generatingChunk.z + j);

                //Seed random for this chunk, this way we get the same result no matter how often this is called.
                random.setLargeFeatureSeedWithSalt(reader.getSeed(), currentChunk.x, currentChunk.z,
                        config.featureSeedSalt);

                if (random.nextInt(config.chanceToGenerate) == 0) {
                    //this chunk contains a root, so we generate a random
                    result.add(currentChunk.asBlockPos().add(
                            random.nextInt(15),
                            Math.min(generator.getMaxBuildHeight(),
                                    config.minGenerationHeight + random.nextInt(
                                            Math.max(0, config.maxGenerationHeight - config.minGenerationHeight))),
                            random.nextInt(15))
                    );
                }
            }
        }
        return result;
    }
    //endregion Methods

}
