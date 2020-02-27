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

package com.github.klikli_dev.occultism;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Config;

import java.util.Arrays;

@Config(modid = Occultism.MODID)
public class OccultismConfig {

    //region Fields
    @Config.Comment("Storage settings")
    public static final StorageSettings storage = new StorageSettings();
    @Config.Comment("Worldgen settings")
    public static final WorldGenSettings worldGen = new WorldGenSettings();

    public static class StorageSettings {
        //region Fields
        @Config.Comment("The amount of slots the storage stabilizer tier 1 provides.")
        public int stabilizerTier1Slots = 128;
        @Config.Comment("The amount of slots the storage stabilizer tier 2 provides.")
        public int stabilizerTier2Slots = 256;
        @Config.Comment("The amount of slots the storage stabilizer tier 3 provides.")
        public int stabilizerTier3Slots = 512;
        @Config.Comment("The amount of slots the storage stabilizer tier 4 provides.")
        public int stabilizerTier4Slots = 1024;
        @Config.Comment("The amount of slots the storage actuator provides.")
        public int controllerBaseSlots = 128;
        //endregion Fields
    }

    public static class WorldGenSettings {
        //region Fields
        @Config.Comment("OreGen settings")
        public final OreGenSettings oreGen = new OreGenSettings();
        @Config.Comment("Underground grove gen settings")
        public final UndergroundGroveGenSettings undergroundGroveGen = new UndergroundGroveGenSettings();
        //endregion Fields

        public static class OreGenSettings {
            //region Fields

            @Config.Comment("The dimensions whitelisted for Occultism Oregen")
            public Integer[] dimensionWhitelist = {0};

            @Config.Comment("The size of otherstone ore veins.")
            @Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
            public int otherstoneOreSize = 7;

            @Config.Comment("The chance for otherstone ore veins to spawn. 0 to disable.")
            @Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
            public int otherstoneOreChance = 5;

            @Config.Comment("The minimum height for otherstone ore veins to spawn.")
            @Config.RangeInt(min = 0, max = 255)
            public int otherstoneOreMin = 10;

            @Config.Comment("The maximum height for otherstone ore veins to spawn.")
            @Config.RangeInt(min = 0, max = 255)
            public int otherstoneOreMax = 80;
            //endregion Fields
        }

        public static class UndergroundGroveGenSettings {
            //region Fields
            @Config.Comment("The dimensions whitelisted for Occultism Underground Grove generation")
            public Integer[] dimensionWhitelist = {0};
            public String[] validBiomes = Arrays.stream(
                    new BiomeDictionary.Type[]{BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MAGICAL})
                                                  .map(BiomeDictionary.Type::getName).toArray(String[]::new);
            @Config.Comment("The rarity of groves")
            public int groveSpawnRarity = 180;
            @Config.Comment("The minimum distance between groves")
            public float minGroveDistance = 300;
            @Config.Comment("The chance grass will spawn in the underground grove.")
            public float grassChance = 0.1f;
            @Config.Comment("The chance small trees will spawn in the underground grove.")
            public float treeChance = 0.7f;
            @Config.Comment("The chance vines will spawn in the underground grove.")
            public float vineChance = 0.125f;
            @Config.Comment("The chance glowstone will spawn in the ceiling of the underground grove.")
            public float ceilingLightChance = 0.7f;
            //endregion Fields
        }
    }
}
