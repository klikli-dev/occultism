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

import com.github.klikli_dev.occultism.config.value.CachedBoolean;
import com.github.klikli_dev.occultism.config.value.CachedFloat;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import com.github.klikli_dev.occultism.config.value.CachedObject;
import com.google.common.collect.ImmutableList;
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

    public class RitualSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedBoolean enableClearWeatherRitual;

        //endregion Fields

        //region Initialization
        public RitualSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Ritual Settings").push("rituals");
            this.enableClearWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to clear rainy weather.")
                            .define("enableClearWeatherRitual", true));
            builder.pop();
        }
        //endregion Initialization
    }

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

            public final OreSettings otherstoneNatural;
            public final OreSettings copperOre;
            public final OreSettings silverOre;
            public final OreSettings platinumOre;

            //endregion Fields

            //region Initialization
            public OreGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Ore Gen Settings").push("oregen");
                List<String> whitelist = Stream.of("overworld").collect(Collectors.toList());

                this.otherstoneNatural =
                        new OreSettings("otherstone_natural", whitelist, 7,
                                5, 10, 80, this, builder);

                this.copperOre =
                        new OreSettings("copper_ore", whitelist, 9,
                                20, 20, 64, this, builder);
                this.silverOre =
                        new OreSettings("silver_ore", whitelist, 7,
                                5, 0, 30, this, builder);
                this.platinumOre =
                        new OreSettings("platinum_ore", whitelist, 3,
                                1, 0, 30, this, builder);
                builder.pop();
            }
            //endregion Initialization

            public class OreSettings extends ConfigCategoryBase {
                //region Fields

                public final CachedObject<List<String>> dimensionTypeWhitelist;
                public final CachedInt oreSize;
                public final CachedInt oreChance;
                public final CachedInt oreMin;
                public final CachedInt oreMax;

                //endregion Fields

                //region Initialization
                public OreSettings(String oreName, List<String> dimensionTypes, int size, int chance, int min, int max,
                                   IConfigCache parent, ForgeConfigSpec.Builder builder) {
                    super(parent, builder);
                    builder.comment("Ore Settings").push(oreName);

                    this.dimensionTypeWhitelist = CachedObject.cache(this,
                            builder.comment("The dimensions this ore will spawn in.")
                                    .define("dimensionWhitelist", dimensionTypes));
                    this.oreSize = CachedInt.cache(this,
                            builder.comment("The size of veins for this ore.")
                                    .defineInRange("oreSize", size, 0, Byte.MAX_VALUE));
                    this.oreChance = CachedInt.cache(this,
                            builder.comment("The chance (amount of rolls) for this ore to spawn.")
                                    .defineInRange("oreChance", chance, 0, Byte.MAX_VALUE));
                    this.oreMin = CachedInt.cache(this,
                            builder.comment("The minimum height for this ore veins to spawn.")
                                    .define("oreMin", min));
                    this.oreMax = CachedInt.cache(this,
                            builder.comment("The maximum height for this ore veins to spawn.")
                                    .define("oreMax", max));
                    builder.pop();
                }
                //endregion Initialization
            }
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
                        builder.comment(
                                "The chance for a grove to spawn in a chunk (generates 1/groveSpawnChance chunks on average).")
                                .define("groveSpawnChance", 400));
                this.groveSpawnMin = CachedInt.cache(this,
                        builder.comment(
                                "The min height for a grove to spawn (applied to the center of the grove, not the floor).")
                                .define("groveSpawnMin", 25));
                this.groveSpawnMax = CachedInt.cache(this,
                        builder.comment(
                                "The max height for a grove to spawn (applied to the center of the grove, not the ceiling).")
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
