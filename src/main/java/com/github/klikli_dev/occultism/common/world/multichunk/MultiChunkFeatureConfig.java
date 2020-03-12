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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class MultiChunkFeatureConfig implements IFeatureConfig {
    //region Fields

    /**
     * The maximum amount of chunks from the root position to still generate this feature.
     */
    public final int maxChunksToRoot;
    public final int chanceToGenerate;
    public final int minGenerationHeight;
    public final int maxGenerationHeight;
    public final int featureSeedSalt;
    public final List<DimensionType> allowedDimensionTypes;
    //endregion Fields

    //region Initialization
    public MultiChunkFeatureConfig(int maxChunksToRoot, int chanceToGenerate, int minGenerationHeight,
                                   int maxGenerationHeight, int featureSeedSalt) {
        this(maxChunksToRoot, chanceToGenerate, minGenerationHeight, maxGenerationHeight, featureSeedSalt,
                ImmutableList.of(DimensionType.OVERWORLD));
    }

    public MultiChunkFeatureConfig(int maxChunksToRoot, int chanceToGenerate, int minGenerationHeight,
                                   int maxGenerationHeight, int featureSeedSalt,
                                   List<DimensionType> allowedDimensionTypes) {
        this.maxChunksToRoot = maxChunksToRoot;
        this.chanceToGenerate = chanceToGenerate;
        this.featureSeedSalt = featureSeedSalt;
        this.minGenerationHeight = minGenerationHeight;
        this.maxGenerationHeight = maxGenerationHeight;
        this.allowedDimensionTypes = allowedDimensionTypes;
    }
    //endregion Initialization

    //region Overrides
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<T>(ops, ops.createMap(
                ImmutableMap.<T, T>builder()
                        .put(ops.createString("maxChunksToRoot"), ops.createInt(this.maxChunksToRoot))
                        .put(ops.createString("chanceToGenerate"), ops.createInt(this.chanceToGenerate))
                        .put(ops.createString("minGenerationHeight"), ops.createInt(this.minGenerationHeight))
                        .put(ops.createString("maxGenerationHeight"), ops.createInt(this.maxGenerationHeight))
                        .put(ops.createString("featureSeedSalt"), ops.createInt(this.featureSeedSalt))
                        .put(ops.createString("chanceToGenerate"), ops.createInt(this.chanceToGenerate))
                        .put(ops.createString("allowedDimensionTypes"),
                                ops.createList(this.allowedDimensionTypes.stream().map(type -> type.serialize(ops))))
                        .build()
        )
        );
    }
    //endregion Overrides

    //region Static Methods
    public static MultiChunkFeatureConfig deserialize(Dynamic<?> in) {
        int maxChunksToRoot = in.get("maxChunksToRoot").asInt(0);
        int chanceToGenerate = in.get("chanceToGenerate").asInt(0);
        int minGenerationHeight = in.get("minGenerationHeight").asInt(0);
        int maxGenerationHeight = in.get("maxGenerationHeight").asInt(0);
        int featureSeedSalt = in.get("featureSeedSalt").asInt(0);
        List<DimensionType> allowedDimensionTypes = in.get("allowedDimensionTypes").asList(DimensionType::deserialize);
        return new MultiChunkFeatureConfig(maxChunksToRoot, chanceToGenerate, minGenerationHeight, maxGenerationHeight,
                featureSeedSalt,
                allowedDimensionTypes);
    }
    //endregion Static Methods
}
