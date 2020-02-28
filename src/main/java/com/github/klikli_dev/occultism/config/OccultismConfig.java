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

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;

public class OccultismConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storageSettings;
    public final WorldGenSettings worldGenSettings;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storageSettings = new StorageSettings(this, builder);
        this.worldGenSettings = new WorldGenSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

    public class StorageSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedValue<Integer> stabilizerTier1Slots;
        public final CachedValue<Integer> stabilizerTier2Slots;
        public final CachedValue<Integer> stabilizerTier3Slots;
        public final CachedValue<Integer> stabilizerTier4Slots;
        public final CachedValue<Integer> controllerBaseSlots;
        //endregion Fields

        //region Initialization
        public StorageSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Storage Settings").push("storage");
            stabilizerTier1Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 128));
            stabilizerTier2Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 256));
            stabilizerTier3Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 512));
            stabilizerTier4Slots = CachedValue.wrap(this,
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 1024));
            controllerBaseSlots = CachedValue.wrap(this,
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

            public final CachedValue<Integer[]> dimensionWhitelist;
            public final CachedValue<Integer> otherstoneOreSize;
            public final CachedValue<Integer> otherstoneOreChance;
            public final CachedValue<Integer> otherstoneOreMin;
            public final CachedValue<Integer> otherstoneOreMax;

            //endregion Fields

            //region Initialization
            public OreGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Ore Gen Settings").push("oregen");

                dimensionWhitelist = CachedValue.wrap(this,
                        builder.comment("The dimensions whitelisted for Occultism Oregen.")
                                .define("dimensionWhitelist", new Integer[]{0}));
                otherstoneOreSize = CachedValue.wrap(this,
                        builder.comment("The size of otherstone ore veins.")
                                .defineInRange("otherstoneOreSize", 7, 0, Byte.MAX_VALUE));
                otherstoneOreChance = CachedValue.wrap(this,
                        builder.comment("The chance (amount of rolls) for otherstone ore to spawn.")
                                .defineInRange("otherstoneOreChance", 5, 0, Byte.MAX_VALUE));
                otherstoneOreMin = CachedValue.wrap(this,
                        builder.comment("The minimum height for otherstone ore veins to spawn.")
                                .define("otherstoneOreMin", 10));
                otherstoneOreMax = CachedValue.wrap(this,
                        builder.comment("The maximum height for otherstone ore veins to spawn.")
                                .define("otherstoneOreMax", 80));
                builder.pop();
            }
            //endregion Initialization
        }

        public class UndergroundGroveGenSettings extends ConfigCategoryBase {
            //region Fields
            public CachedValue<Integer[]> dimensionWhitelist;
            public CachedValue<String[]> validBiomes;
            public CachedValue<Integer> groveSpawnRarity;
            public CachedValue<Float> minGroveDistance;
            public CachedValue<Float> grassChance;
            public CachedValue<Float> treeChance;
            public CachedValue<Float> vineChance;
            public CachedValue<Float> ceilingLightChance;
            //endregion Fields

            //region Initialization
            public UndergroundGroveGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Underground Grove Settings").push("underground_grove");

                dimensionWhitelist = CachedValue.wrap(this,
                        builder.comment("The dimensions whitelisted for underground grove generation.")
                                .define("dimensionWhitelist", new Integer[]{0}));
                String[] defaultValidBiomes = Arrays.stream(
                        new BiomeDictionary.Type[]{BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MAGICAL})
                                                      .map(BiomeDictionary.Type::getName).toArray(String[]::new);
                validBiomes = CachedValue.wrap(this,
                        builder.comment("The biome types to spawn underground groves in.")
                                .define("validBiomes", defaultValidBiomes));
                groveSpawnRarity = CachedValue.wrap(this,
                        builder.comment("The rarity of groves.")
                                .define("groveSpawnRarity", 180));

                minGroveDistance = CachedValue.wrap(this,
                        builder.comment("The minimum distance between groves.")
                                .define("minGroveDistance", 300.0f));
                grassChance = CachedValue.wrap(this,
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .define("grassChance", 0.1f));
                treeChance = CachedValue.wrap(this,
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .define("treeChance", 0.7f));
                vineChance = CachedValue.wrap(this,
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .define("vineChance", 0.125f));
                ceilingLightChance = CachedValue.wrap(this,
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .define("ceilingLightChance", 0.7f));

                builder.pop();
            }
            //endregion Initialization
        }
    }
}
