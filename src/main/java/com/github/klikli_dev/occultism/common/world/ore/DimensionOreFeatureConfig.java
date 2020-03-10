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

package com.github.klikli_dev.occultism.common.world.ore;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class DimensionOreFeatureConfig extends OreFeatureConfig {
    //region Fields
    List<DimensionType> allowedDimensionTypes;
    //endregion Fields

    //region Initialization
    public DimensionOreFeatureConfig(List<DimensionType> allowedDimensionTypes, FillerBlockType target,
                                     BlockState state, int size) {
        super(target, state, size);
        this.allowedDimensionTypes = allowedDimensionTypes;
    }
    //endregion Initialization

    //region Overrides
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<T>(ops,
                ops.createMap(ImmutableMap.of(ops.createString("size"),
                        ops.createInt(this.size), ops.createString("target"),
                        ops.createString(this.target.func_214737_a()),
                        ops.createString("state"),
                        BlockState.serialize(ops, this.state).getValue(),
                        ops.createString("allowedDimensionTypes"),
                        ops.createList(this.allowedDimensionTypes.stream().map(type -> type.serialize(ops)))
                )));
    }
    //endregion Overrides

    //region Static Methods
    public static DimensionOreFeatureConfig deserialize(Dynamic<?> in) {
        int size = in.get("size").asInt(0);
        OreFeatureConfig.FillerBlockType filler =
                OreFeatureConfig.FillerBlockType.func_214736_a(in.get("target").asString(""));
        BlockState blockstate = in.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        List<DimensionType> allowedDimensionTypes = in.get("allowedDimensionTypes").asList(DimensionType::deserialize);
        return new DimensionOreFeatureConfig(allowedDimensionTypes, filler, blockstate, size);
    }
    //endregion Static Methods
}
