/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.github.klikli_dev.occultism.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class OccultismCommonConfig {
    public final WorldGenSettings worldGen;
    public final ForgeConfigSpec spec;

    //region Initialization
    public OccultismCommonConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.worldGen = new WorldGenSettings(builder);
        this.spec = builder.build();
    }

    public static class WorldGenSettings {
        public final UndergroundGroveGenSettings undergroundGroveGen;

        public WorldGenSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("WorldGen Settings").push("worldgen");
            this.undergroundGroveGen = new UndergroundGroveGenSettings(builder);
            builder.pop();
        }


        public static class UndergroundGroveGenSettings {
            public final DoubleValue grassChance;
            public final DoubleValue treeChance;
            public final DoubleValue vineChance;
            public final DoubleValue ceilingLightChance;

            public UndergroundGroveGenSettings(ForgeConfigSpec.Builder builder) {
                builder.comment("Underground Grove Settings").push("underground_grove");


                this.grassChance =
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .defineInRange("grassChance", 0.6, 0.0f, 1.0f);
                this.treeChance =
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .defineInRange("treeChance", 0.1, 0.0f, 1.0f);
                this.vineChance =
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .defineInRange("vineChance", 0.3, 0.0f, 1.0f);
                this.ceilingLightChance =
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .defineInRange("ceilingLightChance", 0.1, 0.0f, 1.0f);


                builder.pop();
            }
        }
    }
}
