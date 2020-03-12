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

package com.github.klikli_dev.occultism.config;

import com.github.klikli_dev.occultism.config.value.CachedFloat;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import com.github.klikli_dev.occultism.config.value.CachedObject;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storage;
    public final WorldGenSettings worldGen;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storage = new StorageSettings(this, builder);
        this.worldGen = new WorldGenSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

    public class StorageSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedInt stabilizerTier1Slots;
        public final CachedInt stabilizerTier2Slots;
        public final CachedInt stabilizerTier3Slots;
        public final CachedInt stabilizerTier4Slots;
        public final CachedInt controllerBaseSlots;
        //endregion Fields

        //region Initialization
        public StorageSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Storage Settings").push("storage");
            this.stabilizerTier1Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 128));
            this.stabilizerTier2Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 256));
            this.stabilizerTier3Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 512));
            this.stabilizerTier4Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 1024));
            this.controllerBaseSlots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage actuator provides.")
                            .define("controllerBaseSlots", 128));
            builder.pop();
        }
        //endregion Initialization
    }

    public class WorldGenSettings extends ConfigCategoryBase {
        //region Fields
        public final OreGenSettings oreGen;
        public final UndergroundGroveGenSettings undergroundGroveGen;
        //endregion Fields

        //region Initialization
        public WorldGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("WorldGen Settings").push("worldgen");
            this.oreGen = new OreGenSettings(this, builder);
            this.undergroundGroveGen = new UndergroundGroveGenSettings(this, builder);
            builder.pop();
        }
        //endregion Initialization

        public class OreGenSettings extends ConfigCategoryBase {
            //region Fields

            public final CachedObject<List<String>> dimensionTypeWhitelist;
            public final CachedInt otherstoneOreSize;
            public final CachedInt otherstoneOreChance;
            public final CachedInt otherstoneOreMin;
            public final CachedInt otherstoneOreMax;

            //endregion Fields

            //region Initialization
            public OreGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Ore Gen Settings").push("oregen");

                this.dimensionTypeWhitelist = CachedObject.cache(this,
                        builder.comment("The dimensions whitelisted for Occultism Oregen.")
                                .define("dimensionWhitelist", Stream.of("overworld").collect(Collectors.toList())));
                this.otherstoneOreSize = CachedInt.cache(this,
                        builder.comment("The size of otherstone ore veins.")
                                .defineInRange("otherstoneOreSize", 7, 0, Byte.MAX_VALUE));
                this.otherstoneOreChance = CachedInt.cache(this,
                        builder.comment("The chance (amount of rolls) for otherstone ore to spawn.")
                                .defineInRange("otherstoneOreChance", 5, 0, Byte.MAX_VALUE));
                this.otherstoneOreMin = CachedInt.cache(this,
                        builder.comment("The minimum height for otherstone ore veins to spawn.")
                                .define("otherstoneOreMin", 10));
                this.otherstoneOreMax = CachedInt.cache(this,
                        builder.comment("The maximum height for otherstone ore veins to spawn.")
                                .define("otherstoneOreMax", 80));
                builder.pop();
            }
            //endregion Initialization
        }

        public class UndergroundGroveGenSettings extends ConfigCategoryBase {
            //region Fields
            public CachedObject<List<String>> dimensionTypeWhitelist;
            public CachedObject<List<String>> validBiomes;
            public CachedInt groveSpawnChance;
            public CachedInt groveSpawnMin;
            public CachedInt groveSpawnMax;
            public CachedFloat grassChance;
            public CachedFloat treeChance;
            public CachedFloat vineChance;
            public CachedFloat ceilingLightChance;
            //endregion Fields

            //region Initialization
            public UndergroundGroveGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Underground Grove Settings").push("underground_grove");

                this.dimensionTypeWhitelist = CachedObject.cache(this,
                        builder.comment("The dimensions whitelisted for underground grove generation.")
                                .define("dimensionWhitelist", Stream.of("overworld").collect(Collectors.toList())));
                List<String> defaultValidBiomes = Arrays.stream(
                        new BiomeDictionary.Type[]{BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MAGICAL})
                                                          .map(BiomeDictionary.Type::getName)
                                                          .collect(Collectors.toList());
                this.validBiomes = CachedObject.cache(this,
                        builder.comment("The biome types to spawn underground groves in.")
                                .define("validBiomes", defaultValidBiomes));
                this.groveSpawnChance = CachedInt.cache(this,
                        builder.comment("The chance for a grove to spawn in a chunk (generates 1/groveSpawnChance chunks on average).")
                                .define("groveSpawnChance", 400));
                this.groveSpawnMin = CachedInt.cache(this,
                        builder.comment("The min height for a grove to spawn (applied to the center of the grove, not the floor).")
                                .define("groveSpawnMin", 25));
                this.groveSpawnMax = CachedInt.cache(this,
                        builder.comment("The max height for a grove to spawn (applied to the center of the grove, not the ceiling).")
                                .define("groveSpawnMax", 60));

                this.grassChance = CachedFloat.cache(this,
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .define("grassChance", 0.6));
                this.treeChance = CachedFloat.cache(this,
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .define("treeChance", 0.1));
                this.vineChance = CachedFloat.cache(this,
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .define("vineChance", 0.3));
                this.ceilingLightChance = CachedFloat.cache(this,
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .define("ceilingLightChance", 0.1));

                builder.pop();
            }
            //endregion Initialization
        }
    }
}
